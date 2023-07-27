package com.rigoberto.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestConsoleApplication {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgresql:13.1-alpine");
    }

    public static void main(String[] args) {
        SpringApplication.from(ConsoleApplication::main).with(TestConsoleApplication.class).run(args);
    }

}
