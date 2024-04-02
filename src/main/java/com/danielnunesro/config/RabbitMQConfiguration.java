package com.danielnunesro.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	
	@Value("${rabbitmq.prototipo.exchange}")
	private String exchange;
	
	
	@Bean
	public Queue criarFilaPrototipoMsNotification() {
		return QueueBuilder.durable("prototipo-cuidador.ms-notificacao").build();
	}
	
	@Bean
	public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
		return event -> rabbitAdmin.initialize();
	}
	
	@Bean
	public FanoutExchange criarFanoutExchangePrototipoCuidador() {
		return ExchangeBuilder.fanoutExchange(exchange).build();
	}
	
	@Bean
	public Binding criarBindingPrototipoApiMsNotificacao() {
		return BindingBuilder.bind(criarFilaPrototipoMsNotification()).to(criarFanoutExchangePrototipoCuidador());
	}
	
	@Bean
	public MessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		
		return rabbitTemplate;
		
	}
	
	
	
}
