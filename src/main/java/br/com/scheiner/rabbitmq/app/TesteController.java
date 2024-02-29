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

@RestController
@RequestMapping("/mensagem/{quantidade}")
public class TesteController {

    public TesteController(AmqpTemplate queueSender) {
        this.queueSender = queueSender;
    }

    private final AmqpTemplate queueSender;

    @GetMapping
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

}
