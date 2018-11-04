package com.lurker.rabbit.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private final static String QUEUE_NAME = "Hello";

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

        //QueueDeclare
        AMQP.Queue.DeclareOk isOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] Waiting for messages");

        //创建消费者 -- 消息到达时的回调
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Receive message '" + message + "'");
            }
        };

        //进行消费
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
