package com.fptu.gateway;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@BrowserLauncher(
        value = "http://localhost:8888/swagger-ui.html",
        healthCheckEndpoint = "http://localhost:8080/actuator/health",
        excludeProfiles = { "test", "docker"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
