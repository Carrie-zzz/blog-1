# 大纲 #

超链接： [官网](http://spring.io/)

图片  ： ![ioc](./img/spring01.png)

表格：

| ..	| ....	| ....	|
| ---	| :---: | :----:|
| ..	| .. 	| ..	|
| ..	| ..   	| ..	|
| ..	| ..   	| ..	|


----------

----------

----------
# 大纲 #
1. 传统方式
2. 接口方式
3. 思路
	* SqlSessionFactory对象应该放到spring容器中作为单例存在。
	* 传统dao的开发方式中，应该从spring容器中获得sqlsession对象。
	* Mapper代理形式中，应该从spring容器中直接获得mapper的代理对象。
	* 数据库的连接以及数据库连接池事务管理都交给spring容器来完成。

# 传统 #
	1. dao, dao.impl,service,service.impl
		 
	2. 导包
		* 数据库
				1. mysql
				2. c3p0 : 连接池
				3. spring-tx 事务 
				4. spring-jdbc(必须?)
		* mybatis
			1. mybatis
		* spring 
			* 核心
				1. spring-core
				2. spring-beans
				3. spring-context
				4. spring-expression
				5. log4j
				6. commons-loggling
			* aop
				1. spring-aop
				2. spring-aspect
				3. aopalliance
				4. aspectjweaver
			* 测试(test)
				1. junit
				2. spring-test
		* 整合包
			1. mybatis-spring


maven pom

		    <build>
				<plugins>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-compiler-plugin</artifactId>
						<configuration>
							<source>1.8</source>
							<target>1.8</target>
							<encoding>UTF-8</encoding>
						</configuration>
					</plugin>
				</plugins>
			</build>



	    <dependencies>
	 		  <!--  测试-->
	    	<dependency>
	    		<groupId>junit</groupId>
	    		<artifactId>junit</artifactId>
	    		<version>4.12</version>
	    		<scope>test</scope>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-test</artifactId>
	    		<version>4.1.4.RELEASE</version>
	    		<scope>test</scope>
	    	</dependency>
	    	<!-- 公共 -->
	    	<dependency>
	    		<groupId>log4j</groupId>
	    		<artifactId>log4j</artifactId>
	    		<version>1.2.17</version>
	    	</dependency>
	    	
	    	<!-- 数据库 -->
	    	<dependency>
	    		<groupId>mysql</groupId>
	    		<artifactId>mysql-connector-java</artifactId>
	    		<version>5.1.35</version>
	    		<scope>runtime</scope>
	    	</dependency>
	    	 <dependency>
	    		<groupId>c3p0</groupId>
	    		<artifactId>c3p0</artifactId>
	    		<version>0.9.1.2</version>
	    	</dependency>
	    	
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-jdbc</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<!-- mybatis  -->
	    	<dependency>
	    		<groupId>org.mybatis</groupId>
	    		<artifactId>mybatis</artifactId>
	    		<version>3.2.8</version>
	    	</dependency>
	    	
	    	<!-- spring-mybatis 整合 -->
	    	<dependency>
	    		<groupId>org.mybatis</groupId>
	    		<artifactId>mybatis-spring</artifactId>
	    		<version>1.2.2</version>
	    	</dependency>
	    
	    	<!-- spring:核心 -->
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-core</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-beans</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-context</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-expression</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<!-- spring:aop -->
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-aop</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-aspects</artifactId>
	    		<version>4.1.3.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.aspectj</groupId>
	    		<artifactId>aspectjweaver</artifactId>
	    		<version>1.8.4</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>aopalliance</groupId>
	    		<artifactId>aopalliance</artifactId>
	    		<version>1.0</version>
	    	</dependency>
	    	
	    </dependencies>
	    <dependencyManagement>
	    	<dependencies>
	    		<dependency>
	    			<groupId>commons-logging</groupId>
	    			<artifactId>commons-logging</artifactId>
	    			<version>1.2</version>
	    		</dependency>
	    	</dependencies>
	    </dependencyManagement>



	3. 配置文件
		* jdbc.properties
			jdbc.url = jdbc:mysql:///mytest
			jdbc.username = root
			jdbc.password = 1234
			jdbc.driver = com.mysql.jdbc.Driver
		* log4j.properties
			# Global logging configuration
			log4j.rootLogger=DEBUG, stdout
			# Console output...
			log4j.appender.stdout=org.apache.log4j.ConsoleAppender
			log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
			# priority  thread message /n
			log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n

		* mybatis
			* SqlMapConfig.xml
				<?xml version="1.0" encoding="UTF-8" ?>
				<!DOCTYPE configuration
				  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
				  "http://mybatis.org/dtd/mybatis-3-config.dtd">
				<configuration>
					<!-- 定义别名 -->
					 <typeAliases>
						<package name="com.huaxin.bean"/>		
					</typeAliases>
				
					<mappers>
						<mapper resource="HxUserMapper.xml"/>
						<!-- <package name="HxUserMapper.xml"/> -->
					</mappers> 
				</configuration>
		* spring	
			* applicationContext.xml
					<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
						xmlns:context="http://www.springframework.org/schema/context" 
						xmlns:p="http://www.springframework.org/schema/p"
						xmlns:aop="http://www.springframework.org/schema/aop" 
						xmlns:tx="http://www.springframework.org/schema/tx"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
						http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
						http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
						http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">
					</beans>
			
				dao:
						<!-- 加载配置文件 -->
						<context:property-placeholder location="classpath:jdbc.properties"/>
						<!-- 数据库连接池 -->
						<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
							<property name="driverClass" value="${jdbc.driver}"/>
							<property name="jdbcUrl" value="${jdbc.url}"/>
							<property name="user" value="${jdbc.username}"/>
							<property name="password" value="${jdbc.password}"/>
						</bean>
					 	<!-- mybatis的会话工厂 重点!!! -->
					 	<bean id="sqlSessionFactoty" class="org.mybatis.spring.SqlSessionFactoryBean">
					 		<!-- mybatis核心配置文件(SqlMapConfig.xml)位置 -->
					 		<property name="configLocation" value="classpath:SqlMapConfig.xml"/>
					 		<property name="dataSource" ref="dataSource"/>
					 	</bean>
					 	
					 	<!-- dao层bean -->
					 	<bean id="hxUserDao" class="com.huaxin.dao.impl.HxUserDaoImpl">
					 		<property name="sqlSessionFactory" ref="sqlSessionFactoty"/>
					 	</bean>

				service:
					 	<!-- service层bean -->
					 	<bean id="hxUserService" class="com.huaxin.service.impl.HxUserServiceImpl">
					 		<property name="hxUserDao" ref="hxUserDao"/>
					 	</bean>

		* web.xml
			 <filter>
			  	<filter-name>CharacterEncodingFilter</filter-name>
			  	<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
			  	<!-- 单独参数 -->
			  	<init-param>
					<param-name>encoding</param-name>
					<param-value>utf-8</param-value>  	
			  	</init-param>
			  </filter>
			  
			  <filter-mapping>
			  	<filter-name>CharacterEncodingFilter</filter-name>
			  	<url-pattern>/*</url-pattern>
			  </filter-mapping>
			  
			  <!-- 全局参数 -->
				<context-param>
					<param-name>contextConfigLocation</param-name>
					<param-value>classpath:applicationContext.xml</param-value>
				</context-param>
			
				<listener>
					<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
				</listener>
  

	4. 测试
		@RunWith(SpringJUnit4ClassRunner.class)
		@ContextConfiguration("classpath:applicationContext.xml")
		public class MergeTest {
			@Resource
			HxUserService hxUserService;
			@Test
			public void test() {
				HxUser selectById = hxUserService.selectById(1);
				System.out.println(selectById);
			}
		}


----------

# SqlSessionDaoSupport 优化dao #
1. 每个dao.impl都要中都要注入sqlSessionFactory,能否提取到父类里面
2. 整合包提供一个这样的父类SqlSessionDaoSupport,在父类里面提供setter,getter
		private boolean externalSqlSession;
		public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
			if (!this.externalSqlSession) {
			  this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
			}
		}
		public SqlSession getSqlSession() {
			return this.sqlSession;
		}
3. 提供父类后不能关闭session(交给了spring管理)
	Exception: Manual close is not allowed over a Spring managed SqlSession
	openSession.close();


----------

----------

----------
# 接口方式(动态代理方式)整合 #

3. 思路
	* dataSource交给spring
	* SqlSessionFactory对象应该放到spring容器中作为单例存在。
	* Mapper代理形式中，应该从spring容器中直接获得mapper的代理对象。
	* 数据库的连接以及数据库连接池事务管理都交给spring容器来完成。

写dao接口(Mapper)，而不需要写dao实现类，由mybatis根据dao接口和映射文件中statement的定义生成接口实现代理对象。可以调用代理对象方法。

**步骤:**
1. 编写mapper.xml->  XxxMapper.xml
2. 在SqlMapConfig中引入XxxMapper.xml文件
3. 开发接口 dao->XxxMapper.java：方法名和XxxMapper.xml中statement的id相同，输入参数、输出参数一致


**要求:**
1. 在XxxMapper.xml中将namespace设置为XxxMapper.java的全限定名:com.xxxproject.mapper.XxxMapper
2. 将mapper.java接口的方法名和mapper.xml中statement的id保持一致。
3. 将mapper.java接口的方法输入参数类型和mapper.xml中statement的parameterType保持一致
4. 将mapper.java接口的方法输出 结果类型和mapper.xml中statement的resultType保持一致



# 传统 #
	1. dao, dao.impl,service,service.impl
		 
	2. 导包
		* dao层	
			* 数据库
				1. mysql
				2. c3p0 : 连接池   /druid
			* mybatis 
				1. mybatis
		*  整合包
				*  mybatis- spring 
					* 包含 session工厂 SqlSessionFactoryBean

		* service层
			* spring-ioc
				1. spring-core
				2. spring-beans
				3. spring-context
				4. spring-expression
				5. log4j
				6. commons-loggling
			* aop
				1. spring-aop
				2. spring-aspect
				3. aopalliance
				4. aspectjweaver
			* 事务
				1. spring-tx 事务 
				2. spring-jdbc(必须?)
		* web
			* spring-web
			* servlet-api2.5
			* jsp-api2.2
			* jstl-api1.2
		* 测试(test)
				1. junit
				2. spring-test


maven pom

		    <build>
				<plugins>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-compiler-plugin</artifactId>
						<configuration>
							<source>1.8</source>
							<target>1.8</target>
							<encoding>UTF-8</encoding>
						</configuration>
					</plugin>
				</plugins>
			</build>



	    <dependencies>
	 		  <!--  测试-->
	    	<dependency>
	    		<groupId>junit</groupId>
	    		<artifactId>junit</artifactId>
	    		<version>4.12</version>
	    		<scope>test</scope>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-test</artifactId>
	    		<version>4.1.4.RELEASE</version>
	    		<scope>test</scope>
	    	</dependency>
	    	<!-- 公共 -->
	    	<dependency>
	    		<groupId>log4j</groupId>
	    		<artifactId>log4j</artifactId>
	    		<version>1.2.17</version>
	    	</dependency>
	    	
	    	<!-- 数据库 -->
	    	<dependency>
	    		<groupId>mysql</groupId>
	    		<artifactId>mysql-connector-java</artifactId>
	    		<version>5.1.35</version>
	    		<scope>runtime</scope>
	    	</dependency>
	    	 <dependency>
	    		<groupId>c3p0</groupId>
	    		<artifactId>c3p0</artifactId>
	    		<version>0.9.1.2</version>
	    	</dependency>
		    <dependency>
	    		<groupId>com.alibaba</groupId>
	    		<artifactId>druid</artifactId>
	    		<version>1.0.31</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-jdbc</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<!-- mybatis  -->
	    	<dependency>
	    		<groupId>org.mybatis</groupId>
	    		<artifactId>mybatis</artifactId>
	    		<version>3.2.8</version>
	    	</dependency>
	    	
	    	<!-- spring-mybatis 整合 -->
	    	<dependency>
	    		<groupId>org.mybatis</groupId>
	    		<artifactId>mybatis-spring</artifactId>
	    		<version>1.2.2</version>
	    	</dependency>
	    
	    	<!-- spring:核心 -->
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-core</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-beans</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-context</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-expression</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<!-- spring:aop -->
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-aop</artifactId>
	    		<version>4.1.5.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.springframework</groupId>
	    		<artifactId>spring-aspects</artifactId>
	    		<version>4.1.3.RELEASE</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>org.aspectj</groupId>
	    		<artifactId>aspectjweaver</artifactId>
	    		<version>1.8.4</version>
	    	</dependency>
	    	<dependency>
	    		<groupId>aopalliance</groupId>
	    		<artifactId>aopalliance</artifactId>
	    		<version>1.0</version>
	    	</dependency>
	    	
	    </dependencies>
	    <dependencyManagement>
	    	<dependencies>
	    		<dependency>
	    			<groupId>commons-logging</groupId>
	    			<artifactId>commons-logging</artifactId>
	    			<version>1.2</version>
	    		</dependency>
	    	</dependencies>
	    </dependencyManagement>



	3. 配置文件
		* jdbc.properties
			jdbc.url = jdbc:mysql:///mytest
			jdbc.username = root
			jdbc.password = 1234
			jdbc.driver = com.mysql.jdbc.Driver
		* log4j.properties
			# Global logging configuration
			log4j.rootLogger=DEBUG, stdout
			# Console output...
			log4j.appender.stdout=org.apache.log4j.ConsoleAppender
			log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
			# priority  thread message /n
			log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n

		* mybatis
			* SqlMapConfig.xml
				<?xml version="1.0" encoding="UTF-8" ?>
				<!DOCTYPE configuration
				  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
				  "http://mybatis.org/dtd/mybatis-3-config.dtd">
				<configuration>
					<!-- 定义别名 -->
					 <typeAliases>
						<package name="com.huaxin.bean"/>		
					</typeAliases>
				
					<mappers>
						<mapper resource="HxUserMapper.xml"/>
						<!-- <package name="HxUserMapper.xml"/> -->
					</mappers> 
				</configuration>
		* spring	
			* applicationContext.xml
					<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
						xmlns:context="http://www.springframework.org/schema/context" 
						xmlns:p="http://www.springframework.org/schema/p"
						xmlns:aop="http://www.springframework.org/schema/aop" 
						xmlns:tx="http://www.springframework.org/schema/tx"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
						http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
						http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
						http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">
					</beans>
			
				dao:
						<!-- 加载配置文件 -->
						<context:property-placeholder location="classpath:jdbc.properties"/>
						<!-- 数据库连接池 -->
						<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
							<property name="driverClass" value="${jdbc.driver}"/>
							<property name="jdbcUrl" value="${jdbc.url}"/>
							<property name="user" value="${jdbc.username}"/>
							<property name="password" value="${jdbc.password}"/>
						</bean>
					 	<!-- mybatis的会话工厂 重点!!! -->
					 	<bean id="sqlSessionFactoty" class="org.mybatis.spring.SqlSessionFactoryBean">
					 		<!-- mybatis核心配置文件(SqlMapConfig.xml)位置 -->
					 		<property name="configLocation" value="classpath:SqlMapConfig.xml"/>
					 		<property name="dataSource" ref="dataSource"/>
					 	</bean>
					 	
					 	<!-- dao层mapper ,不推荐,推荐扫描 MapperScanerConfigure-->
					 	<bean id="hxUserMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
					 		<property name="sqlSessionFactory" ref="sqlSessionFactoty"/>
					 		<!-- 配置mapper接口的全路径名称 -->
					 		<property name="mapperInterface" value="com.huaxin.mapper.HxUserMapper"/>
					 	</bean>


				service:
					 	<!-- service层bean -->
					 	<bean id="hxUserService" class="com.huaxin.service.impl.HxUserServiceImpl">
					 		<property name="hxUserDao" ref="hxUserDao"/>
					 	</bean>

		* web.xml
			 <filter>
			  	<filter-name>CharacterEncodingFilter</filter-name>
			  	<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
			  	<!-- 单独参数 -->
			  	<init-param>
					<param-name>encoding</param-name>
					<param-value>utf-8</param-value>  	
			  	</init-param>
			  </filter>
			  
			  <filter-mapping>
			  	<filter-name>CharacterEncodingFilter</filter-name>
			  	<url-pattern>/*</url-pattern>
			  </filter-mapping>
			  
			  <!-- 全局参数 -->
				<context-param>
					<param-name>contextConfigLocation</param-name>
					<param-value>classpath:applicationContext.xml</param-value>
				</context-param>
			
				<listener>
					<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
				</listener>
  

1. 整合包
	1. SqlSessionFactoryBean
		1. 会话工厂
						<bean id="sqlSessionFactoty" class="org.mybatis.spring.SqlSessionFactoryBean">
					 		<!-- mybatis核心配置文件(SqlMapConfig.xml)位置 -->
					 		<property name="configLocation" value="classpath:SqlMapConfig.xml"/>
					 		<property name="dataSource" ref="dataSource"/>
					 	</bean>

	2. 
		1. 单个mapper配置
		 	<bean id="hxUserMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
		 		<property name="sqlSessionFactory" ref="sqlSessionFactoty"/>
		 		<!-- 配置mapper接口的全路径名称 -->
		 		<property name="mapperInterface" value="com.huaxin.mapper.HxUserMapper"/>
		 	</bean>
	3. MapperScanerConfigure
		1. 批量扫描使用
		2. 在mybatis核心文件SqlMapConfig.xml 去除掉mapper导入
		 	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		 		<!-- 包路径   ,多个包用,分割 -->
		 		<property name="basePackage" value="com.huaxin.mapper"/>
		 	</bean>	

2. druid连接池
    	 <dependency>
    		<groupId>com.alibaba</groupId>
    		<artifactId>druid</artifactId>
    		<version>1.0.31</version>
    	</dependency>

		<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
			<property name="driverClassName" value="${jdbc.driver}"/>
			<property name="url" value="${jdbc.url}"/>
			<property name="username" value="${jdbc.username}"/>
			<property name="password" value="${jdbc.password}"/>
		</bean>


1. 还原环境
	1. 删除dao.impl
	2. dao改名mapper
	3. dao.xml中删除 daoBean
	4. service.xml中删除属性
		* <property name="hxUserDao" ref="hxUserDao"/>
	5. HxUserMapper.xml放在java中的mapper一起
	6. SqlMapConfig.xml删除mappers
2. 在 dao.xml文件中配置
	* daoMapperBean添加mapper接口代理实现
		 	<bean id="hxUserMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
		 		<property name="sqlSessionFactory" ref="sqlSessionFactoty"/>
		 		<!-- 配置mapper接口的全路径名称 -->
		 		<property name="mapperInterface" value="com.huaxin.mapper.HxUserMapper"/>
		 	</bean>
3. 在service.xml中修改注入名称
4. xxService提供mapper注入

## 批量扫描导入mapper ##
1. 如果在dao.xml配置文件,为每个xxMapper.java配置代理对象,十分繁琐
2. 优化
	* 注释掉上面dao 中bean
	* 配置扫描类
		 <!-- 配置扫描,不需要id -->
	 	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	 		<!-- 包路径   ,多个包用,分割 -->
	 		<property name="basePackage" value="com.huaxin.mapper"/>
	 	</bean>	
3. 注意:扫描相当于一次性加载所有的mapper,id=类名首字母小写
4. 删除SqlMapConfig.xml中 mappers



