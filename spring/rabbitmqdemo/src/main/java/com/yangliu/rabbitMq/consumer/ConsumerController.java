package com.yangliu.rabbitMq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author YL
 * @date 2023/11/01
 **/
@RestController
public class ConsumerController {

    //指定监听的队列
    @RabbitListener(queues="queueemail")
    public void receiveEmail(
            String msg,//消息体
            Message message,//完整消息对象
            Channel channel//信道
    ) throws IOException {
        System.out.println("-------------");
        System.out.println("监听邮件队列");
        System.out.println("msg:"+msg);
        System.out.println("message:"+message);
        System.out.println("channel:"+channel);
        System.out.println("-------------");
    }

    @RabbitListener(queues="queuesms")
    public void receiveSms(
            String msg,//消息体
            Message message,//完整消息对象
            Channel channel//信道
    ) {
        System.out.println("-------------");
        System.out.println("监听短信队列");
        System.out.println("msg:"+msg);
        System.out.println("message:"+message);
        System.out.println("channel:"+channel);
        System.out.println("-------------");
    }

}
