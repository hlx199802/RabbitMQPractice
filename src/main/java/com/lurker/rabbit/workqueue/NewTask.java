package com.lurker.rabbit.workqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

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

        for(int i = 0 ; i < 30 ; i++) {
            //发送消息
            String message = "Hello, Lurker:" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("[x] Send message '" + message + "'");

        }

        channel.close();
        connection.close();

    }

}
