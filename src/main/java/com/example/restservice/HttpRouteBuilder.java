package com.example.restservice;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.Registry;
import org.apache.camel.support.DefaultRegistry;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
//import org.apache.camel.support.DefaultRegistry;
//import org.apache.camel.support.SimpleRegistry;
import javax.sql.DataSource;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.sql.*;

import java.util.HashMap;
import java.util.Map;

import static java.rmi.registry.LocateRegistry.getRegistry;


@Component
public class HttpRouteBuilder extends BaseRouteBuilder
{
    private Logger logger;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Bean
    public DataSource myDataSource()
    {
//        dataSourceProperties.logProperties();
        BasicDataSource ds=new BasicDataSource();
        ds.setDriverClassName(dataSourceProperties.getDriver());
        ds.setUsername(dataSourceProperties.getUsername());
        ds.setPassword(dataSourceProperties.getPassword());
        ds.setUrl(dataSourceProperties.getConnectURI());
        return ds;
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        restConfiguration()
                 .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest()
                .get("/ilya")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .endRest();
        rest()
                .post("/ilya")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .to("log:?level=INFO&showBody=true&showHeaders=true")
              .process(new Processor() {
                   @Override
                   public void process(Exchange exchange) throws Exception {
                       HashMap map=(HashMap) exchange.getIn().getBody(Map.class);
                       String sql1="INSERT INTO MYSCHEMA.NAMES (NAME,DESC) VALUES('"+map.get("name")+"','"+map.get("desc")+"')";
                       exchange.getIn().setBody(sql1);
                   }
               })
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .to("jdbc:myDataSource")
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        int result=(Integer)exchange.getIn().getHeader("CamelJdbcUpdateCount");
                        logger.info("result="+result);
                        if(result>=1) exchange.getIn().setBody(HttpStatus.OK);
                        else  exchange.getIn().setBody(HttpStatus.NOT_ACCEPTABLE);
                    }

                })
                .to("log:?level=INFO&showBody=true&showHeaders=true");
        CamelContext context = new DefaultCamelContext();
        logger= LogManager.getLogger(RestServiceApplication.class);
        ConsoleAppender c = new ConsoleAppender();
        c.setName("CONSOLE_ERR");
        c.setTarget("System.out");
        c.setThreshold(Priority.INFO);
        c.setLayout(new PatternLayout("%d{ISO8601}  %-5p[       %t]%c{1}  : %m%n"));
        c.activateOptions();
           logger.addAppender(c);
        logger.info("test");
//        dataSourceProperties.logProperties();
//        MyDataSource myDataSource=context.getRegistry(MyDataSource.class);
//        Logger.ROOT_LOGGER_NAME.
        String[] beans=appContext.getBeanDefinitionNames();
        for(String s:beans) if(s.indexOf("my")!=-1)
        {
            logger.info(s+" "+
            appContext.getBean(s).getClass().getCanonicalName());
        }
    }

}