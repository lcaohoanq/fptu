package com.orchid.s1accountservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.orchid.s1accountservice.repositories")
@EntityScan(basePackages = "com.orchid.s1accountservice.domain")
@BrowserLauncher(
        value = "http://localhost:8080/swagger-ui.html",
        healthCheckEndpoint = "http://localhost:8080/actuator/health",
        excludeProfiles = { "test", "docker"})
public class AccountServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceMain.class, args);
    }

}
