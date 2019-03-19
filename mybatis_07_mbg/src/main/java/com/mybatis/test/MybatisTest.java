package com.mybatis.test;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.mybatis.bean.Employee;
import com.mybatis.bean.EmployeeExample;
import com.mybatis.bean.EmployeeExample.Criteria;
import com.mybatis.dao.EmployeeMapper;

public class MybatisTest {
	
	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sessionFactory;
	}
	
	@Test
	public void testMbg() throws Exception {
		 List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   File configFile = new File("mbg.xml");
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config = cp.parseConfiguration(configFile);
		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		   myBatisGenerator.generate(null);
	}
	
	/*@Test
	public void testSimple() throws Exception {
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			List<Employee> employees = mapper.selectAll();
			for (Employee employee : employees) {
				System.out.println(employee);
			}
		} finally {
			sqlSession.clearCache();
		}
	}*/
	
	@Test
	public void testMybatis3() throws Exception {
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			
			EmployeeExample example = new EmployeeExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdGreaterThan(2).andLastNameLike("%k%");

			Criteria criteria2 = example.or();
			criteria2.andIdEqualTo(5);
			
			Criteria criteria3 = example.createCriteria();
			criteria3.andGenderEqualTo("0");
			
			example.or(criteria3);
			
			List<Employee> employees = mapper.selectByExample(example);
			for (Employee employee : employees) {
				System.out.println(employee);
			}
		} finally {
			sqlSession.clearCache();
		}
	}
	
}
