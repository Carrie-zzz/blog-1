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

# Spring Jdbc模板 #
1. 一站式服务的组成部分
2. dao层封装,简易开发(不简易,为了演示事务)
3. Spring-jdbc.jar 提供了JdbcTemplate类


## 步骤 ##
1. 创建工程,导包
	1. spring核心包 +日志:6个
	2. AOP:4个
	3. 数据库:
		* mysql驱动:1个
		* spring-jdbc.jar:1个
		* spring-tx.jar(事务):1个
		* spring-test:1个
		* spring-web:1个
	4. 测试
2. 编写dao代码前需要数据库,表
	1. 创建数据库
	2. 创建表
		create database xxx;
		use xxx;
		create table tb_account(
			id int primary key auto_increment, #id
			name varchar(20),#姓名
			money double #余额
		);
3. 配置文件
	1. log4j
	2. applicationContext
4. 编写测试类(自己new)
		@Test
		public void t1() {
			//连接池
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			// 设置数据库四大属性
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql:///mytest");
			dataSource.setUsername("root");
			dataSource.setPassword("1234");
			//
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
			jdbcTemplate.update("insert into tb_account values (null,?,?)", "小红花",
					"300");
		}

----------

## 配置文件方式(交给spring IoC)##
1. 准备工作(上面1-3点)
2. 编写spring配置文件
	1. dataSource交给spring
		 <!-- 数据源 -->
        <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	        <!-- 四大参数 -->
	        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        	<property name="url" value="jdbc:mysql:///mytest"/>
        	<property name="username" value="root"/>
		    <property name="password" value="1234"/>
        </bean>
	2. jdbcTemplate交给spring
		 <!-- 模板类 -->     
        <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
      	  <property name="dataSource" ref="dataSource"/>
        </bean>
3. 测试
	@Resource
	JdbcTemplate jdbcTemplate;
	@Test
	public void t2() {
		jdbcTemplate.update("insert into tb_account values (null,?,?)", "大红花",
				"500");
	}

## 其他连接池 ##
1. dbcp
		    * 先引入DBCP的2个jar包
		        * com.springsource.org.apache.commons.dbcp-1.2.2.osgi.jar
		        * com.springsource.org.apache.commons.pool-1.5.3.jar
		
		    * 编写配置文件
		        <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
		            <property name="url" value="jdbc:mysql:///spring_day03"/>
		            <property name="username" value="root"/>
		            <property name="password" value="root"/>
		        </bean>

2. c3p0
	1. 导入jar包 
			* com.springsource.com.mchange.v2.c3p0-0.9.1.2.jar
			* 或者
				* c3p0-0.9.5.2.jar
				* mchange-commons-java-0.2.11.jar
	2. 配置
	    	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		        <!-- 四大参数 -->
		        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
	        	<property name="jdbcUrl" value="jdbc:mysql:///mytest"/>
	        	<property name="user" value="root"/>
			    <property name="password" value="1234"/>
	        </bean>

3. druid 德鲁伊
4. 模板类
	    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	  	  <property name="dataSource" ref="dataSource"/>
	    </bean>

## jdbcTemplate类 ##
1. 增删改:update(sql,...);
	@Resource
	JdbcTemplate jdbcTemplate;

	@Test
	public void t1() {
		jdbcTemplate.update("insert into tb_account values (null,?,?)", "红花郎",
				"500");
	}

	@Test
	public void t2() {
		jdbcTemplate.update("delete from tb_account where id= ?", 1);
	}

	@Test
	public void t3() {
		jdbcTemplate.update(
				"update tb_account set money=money+1000 where id=? ", 2);
	}

2. 查询  :queryForObject(sql, rowMapper, args);
	@Test
	public void t4() {  //单个查询
		Account queryForObject = jdbcTemplate.queryForObject(
				"select * from tb_account where id=?", new AccountRowMapper(),
				2);
		System.out.println(queryForObject);
	}

	@Test
	public void t5() {//多个查询
		List<Account> query = jdbcTemplate.query("select * from tb_account ",
				new AccountRowMapper());
		System.out.println(query);
	}

	class AccountRowMapper implements RowMapper<Account> {
		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.setId(rs.getInt("id"));
			account.setName(rs.getString("name"));
			account.setMomey(rs.getDouble("money"));
			return account;
		}
	}
 

