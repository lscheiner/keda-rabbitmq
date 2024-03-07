package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageRecoverer extends RepublishMessageRecoverer {

	
	public CustomMessageRecoverer(RabbitTemplate errorTemplate) {
		super(errorTemplate , RabbitMQConfig.EXCHANGE_MESSAGES_DLX);
		super.setErrorRoutingKeyPrefix("");
	}

	@Override
	protected void doSend(String exchange, String routingKey, Message message) {

		if (!routingKey.endsWith(RabbitMQConfig.KEY)) {
			routingKey = routingKey.concat(RabbitMQConfig.KEY);
		}
		
		super.doSend(exchange, routingKey, message);
	}
}
