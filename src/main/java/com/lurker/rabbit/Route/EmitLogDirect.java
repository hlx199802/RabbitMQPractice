package com.lurker.rabbit.Route;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};

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

        //绑定交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT.getType());

        for(int i = 0 ; i < 30 ; i++) {
            int rand = new Random().nextInt(3);
            String severity = LOG_LEVEL_ARR[rand];
            String message = "[Send] log level " + LOG_LEVEL_ARR[rand] + " ,send a message to server";
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println("[Send] log level " + LOG_LEVEL_ARR[rand] + " ,send a message to server");
        }

        channel.close();
        connection.close();

    }

}
