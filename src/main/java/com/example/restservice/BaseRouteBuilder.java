package com.example.restservice;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.http.MediaType;


import org.apache.camel.builder.RouteBuilder;

public class BaseRouteBuilder extends RouteBuilder
        {

    @Override
    public void configure() throws Exception {
        onException(BeanValidationException.class).handled(true).process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
                exchange.getMessage().setBody("{error:" + cause.getMessage() + "}");

            }
        });

/*        onException(InvalidRequestException.class).handled(true).process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
                exchange.getMessage().setBody("{error:" + cause.getMessage() + "}");

            }
        });*/

        onException(Exception.class).handled(true).process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
                exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
                exchange.getMessage().setBody("{error:" + cause.getMessage() + "}");

            }
        });
    }
}