package com.lurker.rabbit.exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 此处的交换器没有绑定队列，因此消息是无法传输上去的；消息到达交换器后被短路了
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "Logs";

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

        //exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType());

        //发送消息
        String message = "[x] Send logs to MQ 'Hello, Lurker!'";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        channel.close();
        connection.close();

    }

}
