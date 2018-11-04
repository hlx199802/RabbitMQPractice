package com.lurker.rabbit.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Worker2 {

    public final static String QUEUE_NAME = "Hello";

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

        //创建消费者队列
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Worker2] Receive message '" + message + "'");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }

}
