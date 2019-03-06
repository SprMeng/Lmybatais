package com.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.mybatis.bean.Employee;

public interface EmployeeMapperAnnotation {
	
	@Select("select * from tbl_employee where id = #{id};")
	public Employee getById(int id);
}
