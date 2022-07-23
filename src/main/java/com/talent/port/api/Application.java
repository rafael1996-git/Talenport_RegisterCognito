package com.talent.port.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.talent.port.api.controllers.UserRestController;

@Configuration
@Import({ UserRestController.class })
public class Application {

}
