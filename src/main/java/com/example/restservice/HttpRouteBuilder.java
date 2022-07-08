package com.example.restservice;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;


@Component
public class HttpRouteBuilder extends BaseRouteBuilder
{
    @Override
    public void configure() throws Exception {
        super.configure();

        CamelContext context = new DefaultCamelContext();
        // it tells Camel how to configure the REST service
        restConfiguration()
                // Use the 'servlet' component.
                // This tells Camel to create and use a Servlet to 'host' the RESTful API.
                // Since we're using Spring Boot, the default servlet container is Tomcat.
                .component("servlet")
                // Allow Camel to try to marshal/unmarshal between Java objects and JSON
                .bindingMode(RestBindingMode.auto);

        rest().get("/kyc").route()
//                .process("httpRequestProcessor")
                .to("log:?level=ERROR&showBody=true").endRest();

     //   context.start();

//        rest().post("/kyc").type(RequestObject.class).route().to("bean-validator:myvalidatorname")
 //               .process("httpRequestProcessor").to("log:?level=INFO&showBody=true");

    }

}