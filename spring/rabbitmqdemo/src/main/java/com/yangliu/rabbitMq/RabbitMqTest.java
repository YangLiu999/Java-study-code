package com.yangliu.rabbitMq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author YL
 * @date 2023/10/30
 **/
public class RabbitMqTest {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //true就是exchange的持久化操作
        channel.exchangeDeclare("myExchange","direct",true);
        //创建queue,第二个true就是持久化操作
        channel.queueDeclare("myQueue",true,false,false,null);
        //绑定exchange和queue
        channel.queueBind("myQueue","myExchange","myRoutingKey");
        //send message
        byte[] msg = "Hello World!".getBytes();
        //如果需要消息持久化，需要设置BasicProperties,将delivery_mode设置成2说明消息的持久化
        channel.basicPublish("myExchange","myRoutingKey",false,
                new AMQP.BasicProperties().builder()
                        .deliveryMode(2)//消息持久化设置
                        .contentType("text/plain")
                        .build()
                ,msg);

    }
}
