package com.lld.playground.controller;

import com.lld.playground.RateLimiter.FixedWindowRateLimiter;
import com.lld.playground.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {
     FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(5, (long)1000); // Allow 5 requests per 1 seconds


    @GetMapping("/hello")
    public ResponseEntity<?> hello(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "name", defaultValue = "World") String name) {
        System.out.println(">>> HIT /api/hello with userId: " + userId + " at " + System.currentTimeMillis());

        if (!rateLimiter.isRequestAllowed(userId)) {
            System.out.println(">>>> 429 reject rate limiter");
            return ResponseEntity.status(429).body(ApiResponse.error("Rate limit exceeded"));
        }

        ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "Spring Boot Application is working properly!",
                Map.of(
                        "greeting", "Hello, " + name + "!",
                        "status", "UP"
                )
        );
        return ResponseEntity.ok(response);
    }
}
