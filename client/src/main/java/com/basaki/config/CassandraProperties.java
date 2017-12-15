package com.basaki.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cassandra")
@Data
public class CassandraProperties {

    private String host;

    private String user;

    private String password;

}
