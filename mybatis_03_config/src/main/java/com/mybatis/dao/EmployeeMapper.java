package com.mybatis.dao;

import com.mybatis.bean.Employee;

public interface EmployeeMapper {

	public Employee getById(int id);
	
	public void addEmp(Employee employee);
	
	public void updateEmp(Employee employee);
	
	public void deleteEmp(Integer id); 
}
