package com.cloudpos.discovery.controller;

import com.cloudpos.discovery.util.DiscoveryConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/discovery")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        log.debug("Discovery service health endpoint requested");
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", DiscoveryConstants.SERVICE_DISPLAY_NAME
        ));
    }
}
