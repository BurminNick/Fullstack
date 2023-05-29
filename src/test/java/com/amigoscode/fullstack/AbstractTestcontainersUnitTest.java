package com.amigoscode.fullstack;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestcontainersUnitTest {
    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                container.getJdbcUrl(),
                "postgres",
                "bestuser"
        ).load();
        flyway.migrate();
    }
    @Container
    protected static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").
                    withDatabaseName("fullstack-dao-unit-test").
                    withUsername("postgres").
                    withPassword("bestuser");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry){
        registry.add(
                "spring.datasource.url",
                container::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                container::getUsername
        );
        registry.add(
                "spring.datasource.password",
                container::getPassword
        );
    }
}
