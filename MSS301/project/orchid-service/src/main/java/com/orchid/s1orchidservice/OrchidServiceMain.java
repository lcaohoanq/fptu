package com.orchid.s1orchidservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.orchid.s1orchidservice.repositories")
@EntityScan(basePackages = "com.orchid.s1orchidservice.domain")
@BrowserLauncher(
        value = "http://localhost:8081/swagger-ui.html",
        healthCheckEndpoint = "http://localhost:8081/actuator/health",
        excludeProfiles = { "test", "docker"})
@EnableDiscoveryClient
public class OrchidServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(OrchidServiceMain.class, args);
    }

}
