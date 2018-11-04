package com.lurker.rabbit.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String[] LOG_LEVEL_ARR = {"dao.debug", "dao.info", "dao.error",
        "service.debug", "service.info", "service.error",
        "controller.debug", "controller.info", "controller.error"};

    public static void main(String [] args) throws IOException, TimeoutException {

        //ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.108.176.64");
        factory.setPort(5672);
        factory.setUsername("lurker");
        factory.setPassword("lurker_yaojiang");

        //Connection
        Connection connection = factory.newConnection();

        //Channel
        Channel channel = connection.createChannel();

        //绑定路由
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        for(String severity : LOG_LEVEL_ARR) {
            String message = "[Send] log-level:" + severity + "; log info:" + UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println("[Send] log-level:" + severity);
        }

        channel.close();
        connection.close();

    }

}
