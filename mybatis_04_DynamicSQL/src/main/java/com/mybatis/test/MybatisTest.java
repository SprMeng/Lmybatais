package com.mybatis.test;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	Mapper	====>  xxMapper.xml
 * 
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 					将sql抽取出来。	
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
			
			//测试if\where
//			List<Employee> results = mapperDynamicSQL.getEmpsByConditionIf(employee);
//			for (Employee result : results) {
//				System.out.println(result);
//			}
			
			//查询的时候如果某些条件没带可能sql拼装会有问题
			//1、给where后面加上1=1，以后的条件都and xxx.
			//2、mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉
				//where只会去掉第一个多出来的and或者or。
			//测试Trim
//			List<Employee> results = mapperDynamicSQL.getEmpsByConditionTrim(employee);
//			for (Employee result : results) {
//				System.out.println(result);
//			}
			
			//测试choose
//			List<Employee> list = mapperDynamicSQL.getEmpsByConditionChoose(employee);
//			for (Employee emp : list) {
//				System.out.println(emp);
//			}
			
			//测试set
//			employee = new Employee(1, "freeman", "zfmstc@gmail.com", null);
//			System.out.println(mapperDynamicSQL.updateEmp(employee));
//			sqlSession.commit();
			
			//测试foreach
//			List<Employee> list = mapperDynamicSQL.getEmpsByConditionForeach(Arrays.asList(1, 4, 5));
//			for (Employee emp : list) {
//				System.out.println(emp);
//			}
			
			//测试批量插入
//			List<Employee> employees = new ArrayList<>();
//			employees.add(new Employee("alex", "alex@gmail.com", 1, new Dept(1, "")));
//			employees.add(new Employee("allen", "allen@gmail.com", 0, new Dept(2, "")));
//			mapperDynamicSQL.addEmps(employees);
//			sqlSession.commit();
			
			//测试两个内置参数
			List<Employee> employees = mapperDynamicSQL.getEmpsTestInnerParameter(null);
			System.out.println(employees);
		} finally {
			sqlSession.close();
		}
	}
	

	/**
	 * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
	 * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。 
	 * 3、将sql映射文件注册在全局配置文件中
	 * 4、写代码：
	 * 		1）、根据全局配置文件得到SqlSessionFactory；
	 * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
	 * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
	 * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//获取 sqlsession 实例, 能执行已经映射的sql实例
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//param1: sql映射的唯一标识
			//param2: 执行SQL要用的参数
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
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2、获取sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
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
	 * 测试增删改
	 * 1、mybatis允许增删改直接定义以下类型返回值
	 * 		Integer、Long、Boolean、void
	 * 2、我们需要手动提交数据
	 * 		sqlSessionFactory.openSession();===》手动提交
	 * 		sqlSessionFactory.openSession(true);===》自动提交
	 * @throws IOException 
	 */
	@Test
	public void testCUD() throws Exception {
		String source = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = sessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			//测试添加
			Employee employee = new Employee(null, "kitty", "kitty@gmall.com", 0);
			mapper.addEmp(employee);
			System.out.println(employee.getId());
			
			//测试修改
//			Employee emp = new Employee(4, "kitty2", "kitty@gmall.com2", 0);
//			boolean flag = mapper.updateEmp(emp);
//			System.out.println(flag);
			
			//测试删除
			//mapper.deleteEmp(3);
			//2、手动提交数据
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
		//1、获取到的SqlSession不会自动提交数据
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
