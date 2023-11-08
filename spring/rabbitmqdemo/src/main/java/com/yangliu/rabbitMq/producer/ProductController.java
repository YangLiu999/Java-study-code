package com.yangliu.rabbitMq.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YL
 * @date 2023/11/01
 **/
@RestController
public class ProductController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/testemail")
    public void testemail() {
        //String exchange, String routingKey, final Object object
        rabbitTemplate.convertAndSend("exchangetopic", "email", "email-msg",
            //消息附加属性实现接口MessagePostProcessor
            new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setContentEncoding("utf-8");
                message.getMessageProperties().getHeaders().put("h1","v1");
                message.getMessageProperties().getHeaders().put("h2","v2");
                message.getMessageProperties().setDelay(10000);
                return message;
            }
        });
    }

    @RequestMapping("/testsms")
    public void testsms() {
        //String exchange, String routingKey, Object object
        rabbitTemplate.convertAndSend("exchangetopic", "sms","sms-msg");
    }

    @RequestMapping("/testeall")
    public void testall() {
        //String exchange, String routingKey, Object object
        rabbitTemplate.convertAndSend("exchangetopic", "sms.email","all-msg");
    }

}
