package com.mybatis.bean;

import java.util.List;

public class Dept {
	
	private Integer id;
	private String deptName;
	
	private List<Employee> employees;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptName=" + deptName + "]";
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
