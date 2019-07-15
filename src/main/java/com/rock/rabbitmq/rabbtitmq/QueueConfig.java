package com.rock.rabbitmq.rabbtitmq;


import com.rabbitmq.client.impl.AMQImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class QueueConfig {
    private static  final Logger logger=LoggerFactory.getLogger(QueueConfig.class);
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;

    @Autowired
    private Environment env;
    @Bean
    public Queue queue(){
        return new Queue("hello");
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
               RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
               rabbitTemplate.setMandatory(true);
               rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                   @Override
                   public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                        logger.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
                   }
               });
               rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                   @Override
                   public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String route) {
                       logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,route,replyCode,replyText,message);
                   }
               });
               return rabbitTemplate;
           }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //用RabbitAdmin一定要配置这个，spring加载的是后就会加载这个类================特别重要
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.concurrency")));
        factory.setMaxConcurrentConsumers(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency")));
        factory.setPrefetchCount(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.prefetch")));
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory factory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
//并发配置
        container.setMaxConcurrentConsumers(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency")));
        container.setConcurrentConsumers(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.concurrency")));
        container.setPrefetchCount(Integer.parseInt(env.getProperty("spring.rabbitmq.listener.simple.prefetch")));

//消息确认机制
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }
}
