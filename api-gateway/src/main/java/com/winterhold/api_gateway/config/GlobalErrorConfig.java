package com.winterhold.api_gateway.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
public class GlobalErrorConfig {

    @Bean
    @Order(-1)
    public ErrorWebExceptionHandler errorHandler(){
        return (exchange, ex) -> {
            if (exchange.getResponse().isCommitted()) {
                return Mono.error(ex);
            }

            ResponseStatusException responseEx = (ResponseStatusException) ex;

            if (responseEx.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                exchange.getResponse().getHeaders().setLocation(URI.create("/error/503"));
            }

            if(responseEx.getStatusCode() == HttpStatus.NOT_FOUND){
                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                exchange.getResponse().getHeaders().setLocation(URI.create("/error/404"));
            }

            return exchange.getResponse().setComplete();
        };
    }
}
