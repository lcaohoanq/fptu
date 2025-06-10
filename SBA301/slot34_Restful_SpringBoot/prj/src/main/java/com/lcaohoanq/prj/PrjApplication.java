package com.lcaohoanq.prj;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrjApplication {

    public static void main(String[] args) {

        SpringApplication.run(PrjApplication.class, args);

        JavaBrowserLauncher.openHomePage(List.of("http://localhost:8081/swagger-ui/index.html",
                                                 "http://localhost:8081/h2-console"));

    }

}
