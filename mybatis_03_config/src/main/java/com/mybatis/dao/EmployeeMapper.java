package com.mybatis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mybatis.bean.Employee;

public interface EmployeeMapper {

	public Employee getById(int id);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	public void addEmp(Employee employee);
	
	public boolean updateEmp(Employee employee);
	
	public void deleteEmp(Integer id); 
}
