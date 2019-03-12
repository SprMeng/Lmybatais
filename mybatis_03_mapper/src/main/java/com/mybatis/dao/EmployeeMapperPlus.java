package com.mybatis.dao;

import java.util.List;

import com.mybatis.bean.Employee;

public interface EmployeeMapperPlus {
	
	public Employee getEmpById(Integer id);
	
	public Employee getEmpAndDept(Integer id);
	
	public Employee getDeptByIdStep(Integer id);
	
	public List<Employee> getEmpsByDeptId(Integer id);
}
