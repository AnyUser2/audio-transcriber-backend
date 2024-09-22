package com.tera.transformer_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TransformerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformerManagerApplication.class, args);
	}

}
