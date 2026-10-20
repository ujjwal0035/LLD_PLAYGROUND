package com.lld.playground;

import com.lld.playground.controller.HelloController;
import com.lld.playground.dto.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LldPlaygroundApplication.class)
class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    @Test
    void testHelloDefault() {
        ResponseEntity<ApiResponse<Map<String, Object>>> response = helloController.hello("World");
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Hello, World!", response.getBody().getData().get("greeting"));
        assertEquals("UP", response.getBody().getData().get("status"));
    }

    @Test
    void testHelloCustomName() {
        ResponseEntity<ApiResponse<Map<String, Object>>> response = helloController.hello("Developer");
        assertNotNull(response);
        assertEquals("Hello, Developer!", response.getBody().getData().get("greeting"));
    }
}
