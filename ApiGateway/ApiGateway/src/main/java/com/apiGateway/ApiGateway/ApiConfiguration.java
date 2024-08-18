package com.apiGateway.ApiGateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/shortservice/**").uri("lb://SHORTSERVICE"))
                //customRoute
//                .route(p->p.path("/short/**")
//                        .filters(f->f.rewritePath("/short/**","/shortservice/**")).uri("lb://shortservice"))
                .build();
    }
}
