package com.mybatis.bean;

public class Dept {
	
	private Integer id;
	private String dept_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	@Override
	public String toString() {
		return "Dept [id=" + id + ", dept_name=" + dept_name + "]";
	}
}
