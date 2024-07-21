package com.microserviceexample.orderservice.Config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Bean
    @LoadBalanced     // add the client side load balancing abilities to web client
    public WebClient.Builder webClientBuilder(){

        return  WebClient.builder();
    }
}
