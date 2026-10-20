package com.lld.playground.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter {
    private final int maxAllowedSize;
    private final long windowSizeInMillis;

    private final ConcurrentHashMap<String, windowState> userSate;

    private static class windowState{
        private final long window;
        private final AtomicInteger reqCount;

        windowState(long windowValue, AtomicInteger count){
            this.window = windowValue;
            this.reqCount = count;
        }
    }

    FixedWindowRateLimiter(int maxAllowedSize, long windowSizeInMillis){
        this.maxAllowedSize = maxAllowedSize;
        this.windowSizeInMillis = windowSizeInMillis;

        userSate = new ConcurrentHashMap<>();
    }

    public boolean isRequestAllowed(String userId){
        long currentTimeinMillis = System.currentTimeMillis();

        long windowValue = currentTimeinMillis/this.windowSizeInMillis;

        // if windowValue is not there add it
        if(!userSate.containsKey(userId)){
            userSate.put(userId,new windowState(windowValue, new AtomicInteger(0)));
            return true;
        }

        long currentWindow = userSate.get(userId).window;

        if(currentWindow != windowValue){
            userSate.get(userId).reqCount.incrementAndGet();
            return true;
        }

        int currentCount = userSate.get(userId).reqCount.incrementAndGet();

        return currentCount < this.maxAllowedSize;
    }
}
