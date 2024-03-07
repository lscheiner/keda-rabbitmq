package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class Producer {
	
    private final AmqpTemplate queueSender;
	
    @Retry(name = "retryMensagemTeste" , fallbackMethod = "fallbackAfterRetry")
	public void send(@PathVariable Integer quantidade, String routingKey){

    	log.info("INICIANDO POST");

    	
    	for (int i = 0; i < quantidade ; i++) {
    		
    		String mensagem = "testando mensagem";

            MessageProperties messageProperties = new MessageProperties();
    		messageProperties.setHeader("header-teste", "testando header");
    		MessageConverter messageConverter = new SimpleMessageConverter();
    		Message message = messageConverter.toMessage(mensagem, messageProperties);
			queueSender.convertAndSend(RabbitMQConfig.EXCHANGE_MESSAGES, routingKey, message);
    	}
    }
    
    
    public void fallbackAfterRetry(Exception ex) {
    	log.error("FALL BACK", ex);
    }
    

}
