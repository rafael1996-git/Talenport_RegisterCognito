package com.talent.port.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.talent.port.api.controllers.UserRestController;
import com.talent.port.api.utils.SpringJdbcTemplateConfig;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@Import({ UserRestController.class })
public class TalentportRegisterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TalentportRegisterApplication.class, args);

		
		
	}
	
}
