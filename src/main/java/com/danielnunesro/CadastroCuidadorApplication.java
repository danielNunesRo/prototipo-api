package com.danielnunesro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CadastroCuidadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroCuidadorApplication.class, args);
	}

}
