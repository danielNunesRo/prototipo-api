package com.danielnunesro.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.danielnunesro.dto.CuidadorDTO;

@Service
public class NotificacaoService {
	
	private RabbitTemplate rabbitTemplate;
	
	public NotificacaoService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void notificar(CuidadorDTO cuidador, String exchange) {
		rabbitTemplate.convertAndSend(exchange, "", cuidador);
	}
	
}
