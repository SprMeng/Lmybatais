package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.mybatis.bean.Employee;

public interface EmployeeMapper {

	public Employee getById(int id);
	
	//������¼��װһ��map��Map<Integer,Employee>:����������¼��������ֵ�Ǽ�¼��װ���javaBean
		//@MapKey:����mybatis��װ���map��ʱ��ʹ���ĸ�������Ϊmap��key
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
