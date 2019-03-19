package com.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybatis.bean.Employee;
import com.mybatis.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("getEmps")
	public Object getEmps(Map<String, Object> map) {
		List<Employee> list = employeeService.getEmps();
		map.put("list", list);
		return "list";
	}
}
