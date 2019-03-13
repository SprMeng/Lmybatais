package com.mybatis.test;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.bean.Dept;
import com.mybatis.bean.Employee;
import com.mybatis.dao.DepartmentMapper;
import com.mybatis.dao.EmployeeMapper;
import com.mybatis.dao.EmployeeMapperAnnotation;
import com.mybatis.dao.EmployeeMapperDynamicSQL;
import com.mybatis.dao.EmployeeMapperPlus;

/**
 * 1���ӿ�ʽ���
 * 	ԭ����		Dao		====>  DaoImpl
 * 	mybatis��	Mapper	====>  xxMapper.xml
 * 
 * 2��SqlSession���������ݿ��һ�λỰ���������رգ�
 * 3��SqlSession��connectionһ�������Ƿ��̰߳�ȫ��ÿ��ʹ�ö�Ӧ��ȥ��ȡ�µĶ���
 * 4��mapper�ӿ�û��ʵ���࣬����mybatis��Ϊ����ӿ�����һ����������
 * 		�����ӿں�xml���а󶨣�
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5��������Ҫ�������ļ���
 * 		mybatis��ȫ�������ļ����������ݿ����ӳ���Ϣ�������������Ϣ��...ϵͳ���л�����Ϣ
 * 		sqlӳ���ļ���������ÿһ��sql����ӳ����Ϣ��
 * 					��sql��ȡ������	
 */
public class MybatisTest {
	
	
	@Test
	public void testDynamicSQL() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSQL mapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, "%k%", "kitty@gmall.com", null);
			
			//����if\where
//			List<Employee> results = mapperDynamicSQL.getEmpsByConditionIf(employee);
//			for (Employee result : results) {
//				System.out.println(result);
//			}
			
			//��ѯ��ʱ�����ĳЩ����û������sqlƴװ��������
			//1����where�������1=1���Ժ��������and xxx.
			//2��mybatisʹ��where��ǩ�������еĲ�ѯ�����������ڡ�mybatis�ͻὫwhere��ǩ��ƴװ��sql���������and����orȥ��
				//whereֻ��ȥ����һ���������and����or��
			//����Trim
//			List<Employee> results = mapperDynamicSQL.getEmpsByConditionTrim(employee);
//			for (Employee result : results) {
//				System.out.println(result);
//			}
			
			//����choose
//			List<Employee> list = mapperDynamicSQL.getEmpsByConditionChoose(employee);
//			for (Employee emp : list) {
//				System.out.println(emp);
//			}
			
			//����set
//			employee = new Employee(1, "freeman", "zfmstc@gmail.com", null);
//			System.out.println(mapperDynamicSQL.updateEmp(employee));
//			sqlSession.commit();
			
			//����foreach
			List<Employee> list = mapperDynamicSQL.getEmpsByConditionForeach(Arrays.asList(1, 4, 5));
			for (Employee emp : list) {
				System.out.println(emp);
			}
		} finally {
			sqlSession.close();
		}
	}
	

	/**
	 * 1������xml�����ļ���ȫ�������ļ�������һ��SqlSessionFactory���� ������ԴһЩ���л�����Ϣ
	 * 2��sqlӳ���ļ���������ÿһ��sql���Լ�sql�ķ�װ����ȡ� 
	 * 3����sqlӳ���ļ�ע����ȫ�������ļ���
	 * 4��д���룺
	 * 		1��������ȫ�������ļ��õ�SqlSessionFactory��
	 * 		2����ʹ��sqlSession��������ȡ��sqlSession����ʹ������ִ����ɾ�Ĳ�
	 * 			һ��sqlSession���Ǵ��������ݿ��һ�λỰ������ر�
	 * 		3����ʹ��sql��Ψһ��־������MyBatisִ���ĸ�sql��sql���Ǳ�����sqlӳ���ļ��еġ�
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//��ȡ sqlsession ʵ��, ��ִ���Ѿ�ӳ���sqlʵ��
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//param1: sqlӳ���Ψһ��ʶ
			//param2: ִ��SQLҪ�õĲ���
			Employee employee = sqlSession.selectOne("com.mybatis.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	

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
			//��Ϊ�ӿ��Զ��Ĵ���һ���������󣬴�������ȥִ����ɾ�Ĳ鷽��
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			System.out.println(mapper);
			Employee employee = mapper.getById(1);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test03() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sessionFactory.openSession();
		
		try {
			EmployeeMapperAnnotation annotation = sqlSession.getMapper(EmployeeMapperAnnotation.class);
			System.out.println(annotation.getById(1));
		} finally {
			sqlSession.close();
		}
		
	}
	
	
	/**
	 * ������ɾ��
	 * 1��mybatis������ɾ��ֱ�Ӷ����������ͷ���ֵ
	 * 		Integer��Long��Boolean��void
	 * 2��������Ҫ�ֶ��ύ����
	 * 		sqlSessionFactory.openSession();===���ֶ��ύ
	 * 		sqlSessionFactory.openSession(true);===���Զ��ύ
	 * @throws IOException 
	 */
	@Test
	public void testCUD() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1����ȡ����SqlSession�����Զ��ύ����
		SqlSession sqlSession = sessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			//��������
			Employee employee = new Employee(null, "kitty", "kitty@gmall.com", 0);
			mapper.addEmp(employee);
			System.out.println(employee.getId());
			
			//�����޸�
//			Employee emp = new Employee(4, "kitty2", "kitty@gmall.com2", 0);
//			boolean flag = mapper.updateEmp(emp);
//			System.out.println(flag);
			
			//����ɾ��
			//mapper.deleteEmp(3);
			//2���ֶ��ύ����
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		
	}
	
	@Test
	public void test04() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1����ȡ����SqlSession�����Զ��ύ����
		SqlSession sqlSession = sessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//			Employee employee = mapper.getEmpByIdAndLastName(1, "freeman");
			
//			Map<String, Object> map = new HashMap<>();
//			map.put("id", 1);
//			map.put("lastName", "freeman");
//			map.put("tableName", "tbl_employee");
//			Employee employee = mapper.getEmpByMap(map);
//			System.out.println(employee);
			
//			List<Employee> like = mapper.getEmpsByLastNameLike("%k%");
//			System.out.println(like);
			
//			Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
//			System.out.println(map);
			
			Map<String, Employee> map = mapper.getEmpsByLastNameLikeReturnMap("%k%");
			System.out.println(map);
		} finally {
			sqlSession.close();
		}
		
	}
	
	@Test
	public void test05() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
//			System.out.println(mapper.getEmpById(1));
			
			//System.out.println(mapper.getEmpAndDept(1));
			
			Employee employee = mapper.getDeptByIdStep(1);
			System.out.println(employee.getLastName());
			System.out.println(employee.getDept());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test06() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
//			Dept dept = mapper.getDeptByIdPlus(1);
//			System.out.println(dept);
//			System.out.println(dept.getEmployees());
//			System.out.println(dept.getEmployees().get(0).getDept());
			
			Dept dept = mapper.getDeptByIdStep(1);
			System.out.println(dept.getDeptName());
			System.out.println(dept.getEmployees());
		} finally {
			sqlSession.close();
		}
	}
}