package com.lurker.rabbit.Route;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMQService {

    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void receive(String exchange, final String severity) throws IOException {
        channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT.getType());
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchange, severity);
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Receive] get message [" + message + "] from server, log level " + severity);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

}
