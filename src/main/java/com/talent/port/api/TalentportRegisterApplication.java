package com.talent.port.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.talent.port.api.controllers.UserRestController;

@SpringBootApplication
@Import({ UserRestController.class })
public class TalentportRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentportRegisterApplication.class, args);
	}

}
