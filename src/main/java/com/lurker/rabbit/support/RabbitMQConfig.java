package com.lurker.rabbit.support;

import com.lurker.rabbit.Route.RabbitMQService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.108.176.64");
        factory.setPort(5672);
        factory.setUsername("lurker");
        factory.setPassword("lurker_yaojiang");
        return factory;
    }

    @Bean
    public Connection connection() throws IOException, TimeoutException {
        return connectionFactory().newConnection();
    }

    @Bean
    public Channel channel() throws IOException, TimeoutException {
        return connection().createChannel();
    }

    @Bean
    public RabbitMQService rabbitMQService() throws IOException, TimeoutException {
        RabbitMQService rabbitMQService = new RabbitMQService();
        rabbitMQService.setChannel(channel());
        return rabbitMQService;
    }

}
