package com.jimmysalazar.springjdbc;

import com.jimmysalazar.springjdbc.mappers.EmployeeRowMapper;
import com.jimmysalazar.springjdbc.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

		try {
			int rows = template.update("insert into address (street, number, pc, employee_id) values (?,?,?,?)", "Av. revol", "123A", "244", 15);
			log.info("Rows affected {}",rows);

		} catch(DataAccessException e){
			log.info("Exception {}", e.getClass()); // En Spring viene de DataAccessException (Usar esta)
			log.info("Exception cause {}", e.getCause()); // La causa es un SQLException propia de jdbcTemplate
		}

		// Forma tradicional
		//List<Employee> employeeList = template.query("SELECT * FROM employee", new EmployeeRowMapper());

		// Con función anónima (Lambdas)
		List<Employee> employeeList = template.query("SELECT * FROM employee", new RowMapper<Employee>() {
			// Función que se ejecuta en este momento y que implementa la interfaz RowMapper
			// Es equivalente a crear una clase separada
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee emp = new Employee();
				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setLastname(rs.getString(3));
				emp.setAge(rs.getInt(4));
				emp.setSalary(rs.getDouble(5));
				return emp;
			}
		});

		// Lambda - Para un query que sólo se utilizará una vez
		// si se utilizará en varios lugares, mejor crear una clase aparte
		List<Employee> employeeList2 = template.query("SELECT * FROM employee", (rs, rowNum) -> {
				Employee emp = new Employee();
				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setLastname(rs.getString(3));
				emp.setAge(rs.getInt(4));
				emp.setSalary(rs.getDouble(5));
				return emp;
			}
		);

		for (Employee employee: employeeList2) {
			log.info("Employee info Name {} lastname {} age{} salary{}", employee.getName(), employee.getLastname(), employee.getAge(), employee.getSalary());
		}
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
