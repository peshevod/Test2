package com.example.restservice;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "database")
@Configuration("dataSourceProperties")
public class DataSourceProperties {
    private String connectURI;
    private String driver;
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getConnectURI() {
        return connectURI;
    }

    public String getUsername() {
        return username;
    }

    public String getDriver() {
        return driver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnectURI(String connectURI) {
        this.connectURI = connectURI;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void logProperties()
    {
        Logger logger=Logger.getLogger(RestServiceApplication.class);
        ConsoleAppender c = new ConsoleAppender();
        c.setName("CONSOLE_ERR");
        c.setTarget("System.out");
        c.setThreshold(Priority.INFO);
        c.setLayout(new PatternLayout("[%d{ISO8601}][%-5p][%t][%c{1}] %m%n"));
        c.activateOptions();
        logger.addAppender(c);
        logger.info("user="+username+" connecturi="+connectURI);
    }

}
