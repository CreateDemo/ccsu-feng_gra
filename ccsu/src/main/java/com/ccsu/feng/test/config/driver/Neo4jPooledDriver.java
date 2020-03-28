package com.ccsu.feng.test.config.driver;

import org.neo4j.driver.v1.*;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @create 2020-01-06-15:51
 */
public class Neo4jPooledDriver {

    private String serverUrl;

    private String userName;

    private String password;

    private Driver driver;

    public Neo4jPooledDriver(String serverUrl, String userName, String password) {
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.password = password;
        this.init();
    }

    private void init() {
        this.driver = GraphDatabase.driver(serverUrl, AuthTokens.basic(userName, password), Config.build()
                .withMaxConnectionLifetime(30, TimeUnit.MINUTES)
                .withMaxConnectionPoolSize(50)
                .withConnectionAcquisitionTimeout(2, TimeUnit.MINUTES)
                .withConnectionTimeout(10, TimeUnit.SECONDS)
                .toConfig());
        addCloseHook();
    }

    /**
     * Session for CURD
     * @return Session
     */
    public Session newSession() {
        return driver.session();
    }

    /**
     * Session just for Query
     * @return Session
     */
    public Session newReadonlySession() {
        return driver.session(AccessMode.READ);
    }


    /**
     * add hook to pooled driver when JVM shutdown and release the resources
     */
    private void addCloseHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                if (driver != null) {
                    driver.close();
                    System.out.println("Neo4jPooledDriver is closed");
                }
            }
        });
    }
}
