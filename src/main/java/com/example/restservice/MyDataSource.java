package com.example.restservice;

import javax.sql.DataSource;

import org.apache.camel.language.Bean;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Configuration
//@ConfigurationProperties(prefix = "database")
//@Configuration("connectURI")
//@Configuration("username")
//@Configuration("password")

public class MyDataSource{
//    static final String connectURI = "jdbc:h2:tcp://localhost:8082/~/Downloads/H2Database/mydb;AUTO_SERVER=TRUE";

    private DataSource dataSource;

    @Autowired
    private DataSourceProperties dataSourceProperties;
/*    private String connectURI;
    private String driver;
    private String username;
    private String password;*/

/*    @Autowired
    public MyDataSource(@Value("${database.driver}") String driver,
                        @Value("${database.connectURI}") String connectURI,
                        @Value("${database.username}") String username,
                        @Value("${database.password}") String password)
    {
        this.driver=driver;
        this.connectURI=connectURI;
//        this.username=username;
        this.password=password;
    }*/

    private static BasicDataSource ds=new BasicDataSource();
    public DataSource getDataSource() {
        if(dataSource==null) dataSource=setupDataSource();
        return dataSource;
    }
    public DataSource setupDataSource() {
//        dataSourceProperties.logProperties();
        ds.setDriverClassName(dataSourceProperties.getDriver());
        ds.setUsername(dataSourceProperties.getUsername());
        ds.setPassword(dataSourceProperties.getPassword());
        ds.setUrl(dataSourceProperties.getConnectURI());
        return ds;
    }

/*    public MyDataSource()
    {

    }*/

/*    public String getPassword() {
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
    }*/
    
/*    @Bean(ref = "myDataSource")
    public DataSource myDataSource()
    {
        return dataSource;
    }*/

}

