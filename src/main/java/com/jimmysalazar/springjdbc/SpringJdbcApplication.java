package com.jimmysalazar.springjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringJdbcApplication {

	public static final Logger log = LoggerFactory.getLogger(SpringJdbcApplication.class);

	public static void main(String[] args) {

		// Al iniciar la app de Spring, puedo almacenar la ejecución que me dará paso al contexto
		// esto me permitirá acceder a los beans/objetos administrados por Spring
		ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcApplication.class, args);
		DataSource dataSource = context.getBean(DataSource.class);

		// Comprobamos que el Pool de conecciones por default es HikariCp
		log.info("Datasource implementation {}",dataSource.getClass().getName());
	}
}
