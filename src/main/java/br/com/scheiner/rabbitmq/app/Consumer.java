package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class Consumer {
	
   @RabbitListener(queues = ConfigQueue1.QUEUE_MESSAGES)
    public void receive(@Payload Message<String> message) {
        
   		log.info("Recebendo mensagem teste1");
	   
    	throw new RuntimeException("erro fila");
    }
    
    
    @RabbitListener(queues = ConfigQueue2.QUEUE_MESSAGES)
    public void receive1(@Payload Message<String> message) {
   		log.info("Recebendo mensagem teste 2");
 	   
    	throw new RuntimeException("erro fila 2");
    }

}