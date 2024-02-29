package br.com.scheiner.rabbitmq.app;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

   @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Message<String> message) {
        System.out.println("Recebendo " + message.getPayload() + "  " + LocalDateTime.now());
       
        try {
        	
            System.out.println("GERANDO ATRASO NA MENSAGEM");

			Thread.sleep(10000L);
			
            System.out.println("PROCESSADA ATRASO NA MENSAGEM");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

}