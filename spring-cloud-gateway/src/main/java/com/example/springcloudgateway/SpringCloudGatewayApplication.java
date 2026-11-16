package com.example.springcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("r1",r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
                .route("r2",r->r.path("/products/**").uri("lb://INVENTORY-SERVICE"))
                .route("r3",r->r.path("/fullBill/**").uri("lb://BILLING-SERVICE"))
                .build();

    }
    @Bean
    RouteLocator staticRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/muslim/**")
                        .filters(f->f
                                .addRequestHeader("x-rapidapi-host" , "muslimsalat.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi-key" , "34eb18ff7emsh2842d91ecf0c255p190f43jsn4ac2d46f")
                                .rewritePath("/muslim/(?<segment>.*)" , "/${segment}")
                                .circuitBreaker(c->c.setName("muslimsalat")
                                        .setFallbackUri("forward:/defaultMuslim")))
                        .uri("http://muslimsalat.com"))
                .build();
    }
}
