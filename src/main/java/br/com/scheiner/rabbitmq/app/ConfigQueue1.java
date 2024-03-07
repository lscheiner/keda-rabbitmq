package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigQueue1 {

	public static final String QUEUE_MESSAGES_KEY = "scheiner-messages-queue.key";
	public static final String QUEUE_MESSAGES_KEY_DQL = "scheiner-messages-queue.key";
	
    public static final String QUEUE_MESSAGES = "scheiner-messages-queue";
    public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES + ".dlq";
	
	@Bean
	Queue messagesQueue() {
	    return QueueBuilder.durable(QUEUE_MESSAGES)
	      .withArgument("x-dead-letter-exchange", RabbitMQConfig.EXCHANGE_MESSAGES_DLX)
	      .build();
	}
	 
	@Bean
	Queue messagesQueueDlq() {
	    return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
	}
	
    @Bean
    Binding bindingMessages(TopicExchange messagesExchange) {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange).with(QUEUE_MESSAGES_KEY);
    }
    
    @Bean
    Binding bindingMessagesDlq(TopicExchange messagesExchangeDlx) {
        return BindingBuilder.bind(messagesQueueDlq()).to(messagesExchangeDlx).with(QUEUE_MESSAGES_KEY_DQL);
    }
}
