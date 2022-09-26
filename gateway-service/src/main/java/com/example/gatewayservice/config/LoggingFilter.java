package com.example.gatewayservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config){
        return ((exchange, chain) ->{
            LOG.info("Path del request:" + exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                LOG.info("El request fue procesado");
            }));
        });
    }

    public static class Config {
    }
}