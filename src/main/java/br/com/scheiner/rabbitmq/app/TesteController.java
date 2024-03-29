package br.com.scheiner.rabbitmq.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping
public class TesteController {

    private final Producer producer;

    @GetMapping("/mensagem/{quantidade}/teste")
    public String send(@PathVariable Integer quantidade){
    	
    	this.producer.send(quantidade, "teste-routing-key");
    
        return "sucesso";
    }
    
    
    @GetMapping("/mensagem/{quantidade}/teste1")
    public String send1(@PathVariable Integer quantidade){

    	this.producer.send(quantidade, "teste1-routing-key");
    	
        return "sucesso";
    }

}
