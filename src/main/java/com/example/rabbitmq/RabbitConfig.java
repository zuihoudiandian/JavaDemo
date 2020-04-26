package com.example.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
@Configuration
 public class RabbitConfig {
      @Bean
      public Queue helloQueue() {
             return new Queue("news");
      }

 }