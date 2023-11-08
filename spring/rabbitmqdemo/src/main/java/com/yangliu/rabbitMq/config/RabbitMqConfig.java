package com.yangliu.rabbitMq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YL
 * @date 2023/11/01
 **/
@Configuration
public class RabbitMqConfig {

    //声明交换机
    @Bean("exchangetopic")
    public TopicExchange topicExchange() {
        return new TopicExchange("exchangetopic");
    }

    //声明队列
    @Bean
    public Queue queueemail() {
        return new Queue("queueemail");
    }
    @Bean
    public Queue queuesms() {
        return new Queue("queuesms");
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingEmail(
            @Qualifier("queueemail") Queue queue,
            @Qualifier("exchangetopic")TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#.email.#");
    }

    @Bean
    public Binding bindingSms(
            @Qualifier("queuesms") Queue queue,
            @Qualifier("exchangetopic")TopicExchange exchange) {
        return BindingBuilder.bind(queuesms()).to(exchange).with("#.sms.#");
    }

    /**
     * 配置自定义序列化:每个项目都要单独配置
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
