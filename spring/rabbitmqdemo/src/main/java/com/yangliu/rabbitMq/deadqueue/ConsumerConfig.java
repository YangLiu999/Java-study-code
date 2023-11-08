package com.yangliu.rabbitMq.deadqueue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YL
 * @date 2023/11/08
 **/
@Configuration
public class ConsumerConfig {
    //下单后订单消息发送到一个交换机
    @Bean
    public TopicExchange orderExchangeDelay() {
        return new TopicExchange("orderExchangeDelay");
    }

    //声明下单后投递的队列
    @Bean
    public Queue queueOrderDelay() {
        //队列设置属性
        Map<String, Object> argements = new HashMap<String, Object>();
        //过期时间和关联死信队列和死信路由
        argements.put("x-message-ttl", 10000);
        argements.put("x-dead-letter-exchange", "exchangeOrderDlx");
        argements.put("x-dead-letter-routing-key", "orderdlx");
        return new Queue("queueOrderDelay",true,false,false,argements);
    }
    //绑定订单交换机和队列
    @Bean
    public Binding orderDelayBinding() {
        return  BindingBuilder.bind(queueOrderDelay())
                .to(orderExchangeDelay()).with("#.orderdelay");
    }

    //死信队列交换机定义
    @Bean
    public TopicExchange orderExchangeDlx() {
        return new TopicExchange("exchangeOrderDlx");
    }

    //定义死信队列
    @Bean
    public Queue queueOrderDlx() {
        return new Queue("queueOrderDlx");
    }
    //绑定死信队列和死信交换机
    @Bean
    public Binding orderDlxBinding() {
        return  BindingBuilder.bind(queueOrderDlx())
                .to(orderExchangeDlx()).with("#.orderdlx");
    }
    //监听死信队列
    @RabbitListener(queues="queueOrderDlx")
    public void receivedlx(
            String msg,//消息体
            Message message,//完整消息对象
            Channel channel//信道
    ) throws IOException {
        System.out.println("-------------");
        System.out.println("判断是否支付");
        //消息确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("-------------");
    }
}
