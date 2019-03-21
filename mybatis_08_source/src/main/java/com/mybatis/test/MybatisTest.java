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
	
	/**
	 * 1����ȡsqlSessionFactory����:
	 * 		�����ļ���ÿһ����Ϣ������Configuration�У����ذ���Configuration��DefaultSqlSession��
	 * 		ע�⣺��MappedStatement��������һ����ɾ�Ĳ����ϸ��Ϣ
	 * 
	 * 2����ȡsqlSession����
	 * 		����һ��DefaultSQlSession���󣬰���Executor��Configuration;
	 * 		��һ���ᴴ��Executor����
	 * 
	 * 3����ȡ�ӿڵĴ������MapperProxy��
	 * 		getMapper��ʹ��MapperProxyFactory����һ��MapperProxy�Ĵ������
	 * 		���������������ˣ�DefaultSqlSession��Executor��
	 * 4��ִ����ɾ�Ĳ鷽��
	 * 
	 * �ܽ᣺
	 * 	1�����������ļ���ȫ�֣�sqlӳ�䣩��ʼ����Configuration����
	 * 	2������һ��DefaultSqlSession����
	 * 		���������Configuration�Լ�
	 * 		Executor������ȫ�������ļ��е�defaultExecutorType��������Ӧ��Executor��
	 *  3��DefaultSqlSession.getMapper�������õ�Mapper�ӿڶ�Ӧ��MapperProxy��
	 *  4��MapperProxy�����У�DefaultSqlSession����
	 *  5��ִ����ɾ�Ĳ鷽����
	 *  		1��������DefaultSqlSession����ɾ�Ĳ飨Executor����
	 *  		2�����ᴴ��һ��StatementHandler����
	 *  			��ͬʱҲ�ᴴ����ParameterHandler��ResultSetHandler��
	 *  		3��������StatementHandlerԤ��������Լ����ò���ֵ;
	 *  			ʹ��ParameterHandler����sql���ò���
	 *  		4��������StatementHandler����ɾ�Ĳ鷽����
	 *  		5����ResultSetHandler��װ���
	 *  ע�⣺
	 *  	�Ĵ����ÿ��������ʱ����һ��interceptorChain.pluginAll(parameterHandler);
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 1����ȡsqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2����ȡsqlSession����
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3����ȡ�ӿڵ�ʵ�������
			//��Ϊ�ӿ��Զ��Ĵ���һ��������󣬴������ȥִ����ɾ�Ĳ鷽��
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			System.out.println(mapper);
			Employee employee = mapper.getById(1);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
}
