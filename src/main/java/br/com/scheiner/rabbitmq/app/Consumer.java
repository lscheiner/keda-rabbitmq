package br.com.scheiner.rabbitmq.app;

import java.net.InetAddress;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class Consumer {
	
   @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Message<String> message) {
        try {
        	
        	var maquina =  InetAddress.getLocalHost();
        	
        	log.info("Recebendo mensagem teste {} do host {}" , message.getPayload() , maquina);

			Thread.sleep(5000L);
			
        	log.info("processada a mensagem teste {} do host {}" , message.getPayload() , maquina);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    @RabbitListener(queues = {"${queue1.name}"})
    public void receive1(@Payload Message<String> message) {
        try {
        	
        	var maquina =  InetAddress.getLocalHost();
        	
        	log.info("Recebendo mensagem teste1 {} do host {}" , message.getPayload() , maquina);

			Thread.sleep(5000L);
			
        	log.info("processada a mensagem teste1 {} do host {}" , message.getPayload() , maquina);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}