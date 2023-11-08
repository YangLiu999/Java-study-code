package com.yangliu.rabbitMq.deadqueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YL
 * @date 2023/11/08
 **/
public class ProducerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/test/order")
    public void testsms() {
        //String exchange, String routingKey, Object object
        rabbitTemplate.convertAndSend("orderExchangeDelay",
                "orderdelay","ordertest");
    }
}
