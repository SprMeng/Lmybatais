package com.mybatis.dao;

import java.util.List;

import com.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {

	public List<Employee> getEmpsByConditionIf(Employee employee);
}
