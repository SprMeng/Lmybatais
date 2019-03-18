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
	 * �������棺��ȫ�ֻ��棩������namespace����Ļ��棺һ��namespace��Ӧһ���������棺
	 * 		�������ƣ�
	 * 		1��һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ�������У�
	 * 		2������Ự�رգ�һ�������е����ݻᱻ���浽���������У��µĻỰ��ѯ��Ϣ���Ϳ��Բ��ն��������е����ݣ�
	 * 		3��sqlSession===EmployeeMapper==>Employee
	 * 						DepartmentMapper===>Department
	 * 			��ͬnamespace��������ݻ�����Լ���Ӧ�Ļ����У�map��
	 * 			Ч�������ݻ�Ӷ��������л�ȡ
	 * 				��������ݶ��ᱻĬ���ȷ���һ�������С�
	 * 				ֻ�лỰ�ύ���߹ر��Ժ�һ�������е����ݲŻ�ת�Ƶ�����������
	 * 		ʹ�ã�
	 * 			1��������ȫ�ֶ����������ã�<setting name="cacheEnabled" value="true"/>
	 * 			2����ȥmapper.xml������ʹ�ö������棺
	 * 				<cache></cache>
	 * 			3�������ǵ�POJO��Ҫʵ�����л��ӿ�
	 *
	 * �ͻ����йص�����/���ԣ�
	 * 			1����cacheEnabled=true��false���رջ��棨��������رգ�(һ������һֱ���õ�)
	 * 			2����ÿ��select��ǩ����useCache="true"��
	 * 					false����ʹ�û��棨һ��������Ȼʹ�ã��������治ʹ�ã�
	 * 			3������ÿ����ɾ�ı�ǩ�ģ�flushCache="true"����һ�����������������
	 * 					��ɾ��ִ����ɺ�ͻ�������棻
	 * 					���ԣ�flushCache="true"��һ�����������ˣ�����Ҳ�ᱻ�����
	 * 					��ѯ��ǩ��flushCache="false"��
	 * 						���flushCache=true;ÿ�β�ѯ֮�󶼻���ջ��棻������û�б�ʹ�õģ�
	 * 			4����sqlSession.clearCache();ֻ�������ǰsession��һ�����棻
	 * 			5����localCacheScope�����ػ��������򣺣�һ������SESSION������ǰ�Ự���������ݱ����ڻỰ�����У�
	 * 								STATEMENT�����Խ���һ�����棻		
	 * 				
	 *�������������ϣ�
	 *		1���������������������ɣ�
	 *		2����������������������ϵ���������ٷ��У�
	 *		3����mapper.xml��ʹ���Զ��建��
	 *		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
	 * @throws IOException 
	 * 
	 */
	
	@Test
	public void testSecondLevelCache() throws IOException{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sessionFactory.openSession();
		SqlSession sqlSession2 = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
			
			Employee employee = mapper.getById(1);
			System.out.println(employee);
			
			sqlSession.close();
			Employee employee2 = mapper2.getById(1);
			System.out.println(employee2);
			sqlSession2.close();
		} finally {
			
		}
	}
	
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
