package com.lld.playground.controller;

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

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<Map<String, Object>>> hello(
            @RequestParam(name = "name", defaultValue = "World") String name) {
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
