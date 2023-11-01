package com.yangliu.rabbitMq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;

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
        //Listene异步获取听消费者ack，前提开启channel confirm机制
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("tagId:"+l);
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("tagId:"+l);
            }
        });
        //send message
        byte[] msg = "Hello World!".getBytes();
        //如果需要消息持久化，需要设置BasicProperties,将delivery_mode设置成2说明消息的持久化
        channel.basicPublish("myExchange","myRoutingKey",true,
                new AMQP.BasicProperties().builder()
                        .deliveryMode(2)//消息持久化设置
                        .contentType("text/plain")
                        .build()
                ,msg);
        //设置exchange到queue没法路由的listener
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2,
                                     AMQP.BasicProperties basicProperties,
                                     byte[] bytes) throws IOException {
                System.out.println("exchange到queue路由失败："+new String(bytes));
            }
        });
        channel.addReturnListener(new ReturnCallback() {
            @SneakyThrows
            @Override
            public void handle(Return aReturn) {
                System.out.println("exchange到queue路由失败："+new ObjectMapper().writeValueAsString(aReturn));
            }
        });

        //消费者消费消息
        channel.basicConsume("myQueue",false,"myConsumer",new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收到了消息--"+new String(body));
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("deliveryTag:"+ deliveryTag);
                channel.basicAck(deliveryTag,true);
            }
        });


    }
}
