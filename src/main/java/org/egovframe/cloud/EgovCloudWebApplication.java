package org.egovframe.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EgovCloudWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(EgovCloudWebApplication.class, args);
    }
}
