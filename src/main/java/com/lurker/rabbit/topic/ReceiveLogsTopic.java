package com.lurker.rabbit.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String[] LOG_LEVEL_ARR = {"#", "dao.error", "*.error", "dao.*", "service.#", "*.controller.#"};

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());

        int rand = new Random().nextInt(5);
        final String severity = LOG_LEVEL_ARR[rand];

        System.out.println("severity is " + severity);

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, severity);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Receive] get message [" + message + "] from server, log-level " + severity);
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }

}
