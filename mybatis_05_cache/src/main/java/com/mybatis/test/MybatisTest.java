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
	 * �������棺
	 * һ�����棺�����ػ��棩��sqlSession����Ļ��档һ��������һֱ�����ģ�SqlSession�����һ��Map
	 * 		�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ����С�
	 * 		�Ժ������Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӻ������ã�û��Ҫ��ȥ��ѯ���ݿ⣻
	 * 
	 * 		һ������ʧЧ�����û��ʹ�õ���ǰһ������������Ч�����ǣ�����Ҫ�������ݿⷢ����ѯ����
	 * 		1��sqlSession��ͬ��
	 * 		2��sqlSession��ͬ����ѯ������ͬ.(��ǰһ�������л�û���������)
	 * 		3��sqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
	 * 		4��sqlSession��ͬ���ֶ������һ�����棨������գ�
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
			//1��sqlSession��ͬ��
			//SqlSession openSession2 = sqlSessionFactory.openSession();
			//EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			//2��sqlSession��ͬ����ѯ������ͬ
			
			//3��sqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
			//mapper.addEmp(new Employee(null, "testCache", "cache", "1"));
			//System.out.println("������ӳɹ�");
			
			//4��sqlSession��ͬ���ֶ������һ�����棨������գ�
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
