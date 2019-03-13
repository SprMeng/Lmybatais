package com.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {

	public List<Employee> getEmpsByConditionIf(Employee employee);
	
	public List<Employee> getEmpsByConditionTrim(Employee employee);
	
	public List<Employee> getEmpsByConditionChoose(Employee employee);
	
	public boolean updateEmp(Employee employee);
	
	public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);
}
