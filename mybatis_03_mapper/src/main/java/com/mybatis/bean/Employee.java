package com.mybatis.bean;

import org.apache.ibatis.type.Alias;

@Alias("emp")
public class Employee {

	private Integer id;
	private String lastName;
	private String email;
	private Integer gender;
	private Dept dept;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(Integer id, String lastName, String email, Integer gender) {
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", dept="
				+ dept + "]";
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
}
