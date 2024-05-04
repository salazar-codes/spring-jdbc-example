package com.jimmysalazar.springjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringJdbcApplication implements ApplicationRunner {

	@Autowired
	private JdbcTemplate template;
	public static final Logger log = LoggerFactory.getLogger(SpringJdbcApplication.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Método que se ejecuta luego del main y fuera del contexto estático

		Double maxSalary = template.queryForObject("select MAX(salary) from employee", Double.class);
		log.info("max salary {}", maxSalary);
	}

	public static void main(String[] args) {

		// Al iniciar la app de Spring, puedo almacenar la ejecución que me dará paso al contexto
		// esto me permitirá acceder a los beans/objetos administrados por Spring
		//ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcApplication.class, args);
		//DataSource dataSource = context.getBean(DataSource.class);

		// Comprobamos que el Pool de conecciones por default es HikariCp
		//log.info("Datasource implementation {}",dataSource.getClass().getName());

		//JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

		SpringApplication.run(SpringJdbcApplication.class, args);
	}
}
