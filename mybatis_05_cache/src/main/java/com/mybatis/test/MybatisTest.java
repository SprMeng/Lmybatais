package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.bean.Employee;
import com.mybatis.dao.EmployeeMapper;

public class MybatisTest {
	
	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sessionFactory;
	}
	
	
	
	/**
	 * 两级缓存：
	 * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
	 * 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
	 * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
	 * 
	 * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
	 * 		1、sqlSession不同。
	 * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
	 * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	 * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
	 *
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void testFirstLevelCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = mapper.getById(1);
			System.out.println(emp01);
			
			//xxxxx
			//1、sqlSession不同。
			//SqlSession openSession2 = sqlSessionFactory.openSession();
			//EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			//2、sqlSession相同，查询条件不同
			
			//3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
			//mapper.addEmp(new Employee(null, "testCache", "cache", "1"));
			//System.out.println("数据添加成功");
			
			//4、sqlSession相同，手动清除了一级缓存（缓存清空）
			//openSession.clearCache();
			
			Employee emp02 = mapper.getById(1);
			//Employee emp03 = mapper.getEmpById(3);
			System.out.println(emp02);
			//System.out.println(emp03);
			System.out.println(emp01==emp02);
			
			//openSession2.close();
		}finally{
			openSession.close();
		}
	}
	
}
