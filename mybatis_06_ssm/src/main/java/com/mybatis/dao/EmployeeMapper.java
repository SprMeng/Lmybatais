package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.mybatis.bean.Employee;


public interface EmployeeMapper {

	public Employee getById(int id);
	
	public List<Employee> getEmps();
}
