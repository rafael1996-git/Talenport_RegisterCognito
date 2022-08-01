package com.talent.port.api.utils;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.gson.Gson;
import com.talent.port.api.SecretManager;
import com.talent.port.api.models.ConfigDB;


@Configuration
@ComponentScan("com.baeldung.jdbc")
public class SpringJdbcTemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(SpringJdbcTemplateConfig.class);
   

	 /**
	 * @return
	 */
	@Bean
	    public  DataSource mysqlDataSource() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();

		 try {
			 String dbSecret =SecretManager.getSecret("com/talentport/backend");
			 
			 	Gson gson=new Gson(); 
			 	ConfigDB configDB=gson.fromJson(dbSecret, ConfigDB.class);
		        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		        dataSource.setUrl(configDB.getHost());
		        dataSource.setUsername(configDB.getUsername());
		        dataSource.setPassword(configDB.getPassword());

		        
		} catch (Exception e) {
			e.printStackTrace();
			log.info("error get secret manager");
		}
		 return dataSource;
	        
	    
	}
	
	 
}
