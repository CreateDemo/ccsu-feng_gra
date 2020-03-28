package com.ccsu.feng.test.config;

import com.ccsu.feng.test.config.driver.Neo4jPooledDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(0)
@Configuration
@ComponentScan(basePackages = {"com.ccsu.feng.test.domain"})
@EnableNeo4jRepositories(basePackages = "com.ccsu.feng.test.repository")
@EnableTransactionManagement
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String databaseUrl;

    @Value("${spring.data.neo4j.username}")
    private String userName;

    @Value("${spring.data.neo4j.password}")
    private String password;


    @Bean
    public Neo4jPooledDriver buildPooledDriver() {
        return new Neo4jPooledDriver(databaseUrl, userName, password);
    }

}