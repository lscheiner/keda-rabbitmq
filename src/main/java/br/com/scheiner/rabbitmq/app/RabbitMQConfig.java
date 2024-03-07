package br.com.scheiner.rabbitmq.app;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_MESSAGES = "scheiner-messages-exchange";
    public static final String EXCHANGE_MESSAGES_DLX = "scheiner-messages-exchange.dlx";

    @Bean
    TopicExchange messagesExchange() {
        return new TopicExchange(EXCHANGE_MESSAGES);
    }
    
    @Bean
    TopicExchange messagesExchangeDlx() {
        return new TopicExchange(EXCHANGE_MESSAGES_DLX);
    }
    
    
    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(5000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
    
    @Bean
    public RetryOperationsInterceptor rabbitSourceRetryInterceptor(CustomMessageRecoverer customMessageRecoverer) {
      return RetryInterceptorBuilder.stateless()
          .recoverer(customMessageRecoverer)
          .retryOperations(retryTemplate())
          .build();
    }
    
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory , RetryOperationsInterceptor retryOperationsInterceptor ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

		Advice[] adviceChain = { retryOperationsInterceptor };  // configuracao retry
		factory.setAdviceChain(adviceChain);

        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(20); // quantas mensagens o servidor pode enviar para um consumidor antes de esperar por uma confirmação de processamento. 
		factory.setContainerCustomizer(c -> c.setShutdownTimeout(60_000L)); // aguardar para o shutdown do consumer
		factory.setContainerCustomizer(c -> c.setDefaultRequeueRejected(false)); // não respostar a mensagem
        return factory;
    }

}
