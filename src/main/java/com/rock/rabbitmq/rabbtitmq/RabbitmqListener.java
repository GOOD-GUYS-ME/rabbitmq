package com.rock.rabbitmq.rabbtitmq;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RabbitListener(queues = "hello")
public class RabbitmqListener {
    @RabbitHandler
    public void process(String hello){
        System.out.println("正在收听："+hello);
    }

}
