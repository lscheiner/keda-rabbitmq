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

    private static final String NAME = "queue2";

    public static final String QUEUE_MESSAGES = RabbitMQConfig.BASE_QUEUE + NAME;
	public static final String QUEUE_MESSAGES_KEY = QUEUE_MESSAGES + RabbitMQConfig.KEY;
    public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES + RabbitMQConfig.DLQ;

	@Bean(QUEUE_MESSAGES)
	Queue messagesQueue() {
	    return QueueBuilder.durable(QUEUE_MESSAGES)
	      .withArgument("x-dead-letter-exchange", RabbitMQConfig.EXCHANGE_MESSAGES_DLX)
	      .build();
	}
	
	@Bean(QUEUE_MESSAGES_DLQ)
	Queue messagesQueueDlq() {
	    return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
	}
	
	@Bean(QUEUE_MESSAGES + RabbitMQConfig.BINDING)
    Binding bindingMessages(TopicExchange messagesExchange) {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange).with(QUEUE_MESSAGES_KEY);
    }
    
	@Bean(QUEUE_MESSAGES_DLQ + RabbitMQConfig.BINDING)
    Binding bindingMessagesDlq(TopicExchange messagesExchangeDlx) {
        return BindingBuilder.bind(messagesQueueDlq()).to(messagesExchangeDlx).with(QUEUE_MESSAGES_KEY);
    }

}
