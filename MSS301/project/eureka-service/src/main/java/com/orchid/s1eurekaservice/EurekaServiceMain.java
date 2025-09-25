package com.orchid.s1eurekaservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@BrowserLauncher(
        value = "http://localhost:8761",
        healthCheckEndpoint = "http://localhost:8761/actuator/health",
        excludeProfiles = { "test", "docker"})
@EnableEurekaServer
public class EurekaServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceMain.class, args);
    }

}


