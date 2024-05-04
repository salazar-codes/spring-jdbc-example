package com.jimmysalazar.springjdbc.mappers;

import com.jimmysalazar.springjdbc.models.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

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
}
