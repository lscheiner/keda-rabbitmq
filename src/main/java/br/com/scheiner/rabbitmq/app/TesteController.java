package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping
public class TesteController {

    private final AmqpTemplate queueSender;

    @GetMapping("/mensagem/{quantidade}/teste")
    public String send(@PathVariable Integer quantidade){

    	for (int i = 0; i < quantidade ; i++) {
    		
    		String mensagem = "testando mensagem";

            MessageProperties messageProperties = new MessageProperties();
    		messageProperties.setHeader("header-teste", "testando header");
    		MessageConverter messageConverter = new SimpleMessageConverter();
    		Message message = messageConverter.toMessage(mensagem, messageProperties);
            queueSender.convertAndSend("teste-exchange", "teste-routing-key", message);
    	}
    	
        return "sucesso";
    }
    
    
    @GetMapping("/mensagem/{quantidade}/teste1")
    public String send1(@PathVariable Integer quantidade){

    	for (int i = 0; i < quantidade ; i++) {
    		
    		String mensagem = "testando mensagem";

            MessageProperties messageProperties = new MessageProperties();
    		messageProperties.setHeader("header-teste", "testando header");
    		MessageConverter messageConverter = new SimpleMessageConverter();
    		Message message = messageConverter.toMessage(mensagem, messageProperties);
            queueSender.convertAndSend("teste-exchange", "teste1-routing-key", message);
    	}
    	
        return "sucesso";
    }

}
