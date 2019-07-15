package com.rock.rabbitmq.rabbtitmq;

import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;
    public void send(){
        System.out.println("aaa");
        for (int i = 0; i <100 ; i++) {
            System.out.println("正在发送:第"+i+"条消息");
            String s="第"+i+"条消息";
            rabbitTemplate.convertAndSend("hello",s);
        }
        System.out.println("environment:"+environment.getProperty("spring.rabbitmq.password"));
    }
}
