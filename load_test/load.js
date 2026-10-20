import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Rate } from 'k6/metrics';

// Custom metrics to track rate limiter behavior
export const status200Count = new Counter('status_200_ok');
export const status429Count = new Counter('status_429_rate_limited');
export const rateLimitHitRate = new Rate('rate_limit_trigger_rate');

export const options = {
  scenarios: {
    // Scenario 1: Warm-up / Normal Traffic (Under the limit)
    baseline_traffic: {
      executor: 'constant-vus',
      vus: 2,
      duration: '10s',
      startTime: '0s',
    },
    // Scenario 2: Traffic Spike (Violates the limit)
    rate_limit_burst: {
      executor: 'ramping-arrival-rate',
      startRate: 5,
      timeUnit: '1s',
      preAllocatedVUs: 50,
      maxVUs: 100,
      stages: [
        { target: 50, duration: '10s' }, // Ramp up to 50 requests/sec
        { target: 100, duration: '15s' }, // Ramp up to 100 requests/sec (Burst)
        { target: 0, duration: '5s' },   // Cool down
      ],
      startTime: '10s',
    },
  },
  thresholds: {
    // Assert that the rate limiter actually activated
    status_429_rate_limited: ['count>0'],
  },
};

export default function () {
  // Target a single static user ID to fill that specific user's bucket
  const targetUserId = 'test_user_42';
  const url = `http://host.docker.internal:8080/api/hello?userId=${targetUserId}`;

  const params = {
    headers: {
      'Accept': 'application/json',
      // Some rate limiters throttle on X-Forwarded-For or API keys:
      'X-Forwarded-For': '198.51.100.1',
    },
  };

  const res = http.get(url, params);

  if (res.status === 200) {
    status200Count.add(1);
    rateLimitHitRate.add(0);
  } else if (res.status === 429) {
    status429Count.add(1);
    rateLimitHitRate.add(1);
  }

  // Validate that responses are strictly 200 (allowed) or 429 (throttled)
  check(res, {
    'status is 200 or 429': (r) => r.status === 200 || r.status === 429,
    'retry-after header present on 429': (r) => {
      if (r.status === 429) {
        return r.headers['Retry-After'] !== undefined || r.headers['retry-after'] !== undefined;
      }
      return true;
    },
  });

  // Short sleep to prevent immediate socket exhaustion
  sleep(0.05);
}