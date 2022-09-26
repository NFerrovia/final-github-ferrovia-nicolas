package com.example.movieservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    @Value("${queue.series.name}")
    private String queueSerieName;


    @Bean
    public Queue queueSerie(){
        return new Queue(this.queueSerieName, true);
    }
}
