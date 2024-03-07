package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigQueue2 {

	public static final String QUEUE_MESSAGES_KEY = "scheiner-messages-queue2.key";
	
    public static final String QUEUE_MESSAGES = "scheiner-messages-queue2";
    public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES + ".dlq";
	
	@Bean
	Queue messagesQueue2() {
	    return QueueBuilder.durable(QUEUE_MESSAGES)
	      .withArgument("x-dead-letter-exchange", RabbitMQConfig.EXCHANGE_MESSAGES_DLX)
	      .build();
	}
	 
	@Bean
	Queue messagesQueue2Dlq() {
	    return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
	}
	
    @Bean
    Binding bindingMessages2(TopicExchange messagesExchange) {
        return BindingBuilder.bind(messagesQueue2()).to(messagesExchange).with(QUEUE_MESSAGES_KEY);
    }
    
    @Bean
    Binding bindingMessagesDlq2(TopicExchange messagesExchangeDlx) {
        return BindingBuilder.bind(messagesQueue2Dlq()).to(messagesExchangeDlx).with(QUEUE_MESSAGES_KEY);
    }
}
