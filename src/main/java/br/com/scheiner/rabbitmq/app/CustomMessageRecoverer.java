package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageRecoverer extends RepublishMessageRecoverer {

	private static final String KEY = ".key";
	
	public CustomMessageRecoverer(RabbitTemplate errorTemplate) {
		super(errorTemplate , RabbitMQConfig.EXCHANGE_MESSAGES_DLX);
		super.setErrorRoutingKeyPrefix("");
	}

	@Override
	protected void doSend(String exchange, String routingKey, Message message) {

		if (!routingKey.endsWith(KEY)) {
			routingKey = routingKey.concat(KEY);
		}
		
		super.doSend(exchange, routingKey, message);
	}
}
