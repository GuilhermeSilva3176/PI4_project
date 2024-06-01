package com.fitfinance.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import static com.fitfinance.util.Constants.BASE_URI;

@TestConfiguration
@Lazy
public class RestAssuredConfig {
    @LocalServerPort
    int port;

    @Bean(name = "requestSpecificationRegularUser")
    public RequestSpecification requestSpecificationRegularUser() {
        return RestAssured.given()
                .baseUri(BASE_URI + port)
                .body("""
                        {
                            "name": "John Doe",
                            "cpf": "123.456.789-00",
                            "email": "john_doe@gmail.com",
                            "password": "123456789",
                            "phone": "+55 11 91234-5678",
                            "birthdate": "1990-01-01",
                            "income": 3500,
                            "roles": "USER"
                        }
                        """);
    }

    @Bean(name = "requestSpecificationAdminUser")
    public RequestSpecification requestSpecificationAdminUser() {
        return RestAssured.given()
                .baseUri(BASE_URI + port)
                .body("""
                        {
                            "name": "John Doe",
                            "cpf": "000.000.000-00",
                            "email": "john_doe.admin@gmail.com",
                            "password": "123456789",
                            "phone": "+55 11 91234-5678",
                            "birthdate": "1990-01-01",
                            "income": 3500,
                            "roles": "ADMIN"
                        }
                        """);
    }
}
