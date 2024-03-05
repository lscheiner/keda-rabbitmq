package br.com.scheiner.rabbitmq.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue testeQueue() {
        return new Queue("teste", true);
    }
    
    @Bean
    public Queue testeQueue1() {
        return new Queue("teste1", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("teste-exchange");
    }

    @Bean
    Binding testeBinding(Queue testeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(testeQueue).to(exchange).with("teste-routing-key");
    }
    
    @Bean
    Binding testeBinding1(Queue testeQueue1, DirectExchange exchange) {
        return BindingBuilder.bind(testeQueue1).to(exchange).with("teste1-routing-key");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(20); // quantas mensagens o servidor pode enviar para um consumidor antes de esperar por uma confirmação de processamento. 
		factory.setContainerCustomizer(c -> c.setShutdownTimeout(60_000L)); // aguardar para o shutdown do consumer
        return factory;
    }

}
