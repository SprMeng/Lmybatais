package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.mybatis.bean.Employee;

public interface EmployeeMapper {

	public Employee getById(int id);
	
	//多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
		//@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("lastName")
	public Map<String, Employee> getEmpsByLastNameLikeReturnMap(String lastName);
	
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	public void addEmp(Employee employee);
	
	public boolean updateEmp(Employee employee);
	
	public void deleteEmp(Integer id); 
}
