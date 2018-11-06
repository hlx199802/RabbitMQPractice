package com.lurker.rabbit.Route;

import com.lurker.rabbit.support.RabbitMQConfig;
import com.rabbitmq.client.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class ReceiveLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};

    public static void main(String [] args) throws IOException, TimeoutException {

        /*//ConnectionFactory
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

        int rand = new Random().nextInt(3);
        final String severity = LOG_LEVEL_ARR[rand];

        System.out.println("severity is " + severity);

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, severity);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Receive] get message [" + message + "] from server, log level " + severity);
            }
        };
        channel.basicConsume(queueName, true, consumer);*/
        int rand = new Random().nextInt(3);
        final String severity = LOG_LEVEL_ARR[rand];
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        RabbitMQService rabbitMQService = context.getBean(RabbitMQService.class);
        rabbitMQService.receive(EXCHANGE_NAME, severity);

    }

}
