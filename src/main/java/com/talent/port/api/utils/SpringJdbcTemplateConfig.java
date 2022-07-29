package com.talent.port.api.utils;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.baeldung.jdbc")
public class SpringJdbcTemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(SpringJdbcTemplateConfig.class);

	public static final void getConexion (String url,String username, String password) throws Exception{
		 log.info("dataos de conexion : "+" :"+url+" :"+username+" :"+ password );
	}
	 @Bean
	    public  DataSource mysqlDataSource() {
		 
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl("jdbc:mysql://ls-03a41d14fbe9f08d0be269834cd64968f057d407.ctn40dpeek0r.us-west-2.rds.amazonaws.com:3306/talentPort?useSSL=false");
	        dataSource.setUsername("dbmasteruser");
	        dataSource.setPassword("ISistemas123.,");

	        return dataSource;
	    
	}
	 
}
