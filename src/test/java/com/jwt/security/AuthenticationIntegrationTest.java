package com.jwt.security;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationIntegrationTest {
    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("schema.sql")
            .withAccessToHost(true);
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setup() {
        postgres.setPortBindings(List.of("1234:5432"));
        postgres.start();
    }

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        var test = this.restTemplate.execute("http://localhost:" + port + "/api/v1/demo-controller", HttpMethod.GET, request -> {} , response -> response);
        System.out.println(test);
    }

    @AfterAll
    static void tearDrop() {
        if(postgres != null){
            postgres.stop();
        }
    }
}
