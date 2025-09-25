package com.orchid.s1orderservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.orchid.s1orderservice.repositories")
@EntityScan(basePackages = "com.orchid.s1orderservice.domain")
@BrowserLauncher(
        value = "http://localhost:8082/swagger-ui.html",
        healthCheckEndpoint = "http://localhost:8082/actuator/health",
        excludeProfiles = { "test", "docker"})
@EnableDiscoveryClient
public class OrderServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceMain.class, args);
    }

}
