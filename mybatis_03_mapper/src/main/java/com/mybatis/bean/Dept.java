package com.mybatis.bean;

public class Dept {
	
	private Integer id;
	private String deptName;
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
}
