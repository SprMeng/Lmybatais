package com.mybatis.dao;

import com.mybatis.bean.Dept;

public interface DepartmentMapper {
	
	public Dept getDeptById(Integer id);
	
	public Dept getDeptByIdPlus(Integer id);
	
	public Dept getDeptByIdStep(Integer id);
}
