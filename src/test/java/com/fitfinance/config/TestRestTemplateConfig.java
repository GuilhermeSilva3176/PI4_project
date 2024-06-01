package com.fitfinance.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.util.DefaultUriBuilderFactory;

@TestConfiguration
@Lazy
public class TestRestTemplateConfig {
    @LocalServerPort
    int port;

    @Bean
    public TestRestTemplate restTemplate() {
        var uri = new DefaultUriBuilderFactory("http://localhost:" + port);
        var testRestTemplate = new TestRestTemplate();
        testRestTemplate.setUriTemplateHandler(uri);
        return testRestTemplate;
    }
}
