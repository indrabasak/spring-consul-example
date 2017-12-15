package com.basaki.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class ConsulConfiguration {

    @Value("${cassandra.host}")
    private String cassandraHost;

    @Value("${cassandra.user}")
    private String userName;

    @Value("${cassandra.password}")
    private String password;


    @PostConstruct
    public void postConstruct() {
        System.out.println(
                "********** cassandra.host: " + cassandraHost);
        System.out.println(
                "********** cassandra.user: " + userName);
        System.out.println(
                "********** cassandra.password: " + password);
    }
}
