# jdbc #
1. JDBC（Java Data Base Connectivity,java数据库连接）
2. java语言通过api操作数据库
3. 一套接口规范,操作不同数据库(多态)
	
	画张图(客户端,服务器,mysql) ppt上
		接口,驱动


## 作用 ##
1. 连接数据库(Connection)
2. 发送sql,sql执行者(Statement)
3. 接受数据库处理结果(int,ResultSet)
4. 关闭连接(resultSet,stmt,conn)

[下载mysql驱动](https://www.mysql.com/products/connector/)



# jdbc:java接口 #
1. Driver
2. DriverManager
3. Connection
4. Statement  PreparedStatement
5. ResultSet



1. Driver
2. DriverManager
	* static void registerDriver(Driver driver)  
	* static Connection getConnection(String url, String user, String password)  
3. Connection
	*  PreparedStatement prepareStatement(String sql) 
 	*  void rollback()  
 	*  void commit()  
4. Statement  
	* PreparedStatement
		* setXXX(int parameterIndex, int x) ;从1开始
		*  ResultSet executeQuery() 
		*   int executeUpdate() 
5. ResultSet
	*  boolean next() 
	*  getXXX("列明")
		*  getString("name")
	*  getXXX(第几列)
		*  *  getString(2)    

# jdbc #
1. 添加mysql驱动
2. 连接mysql
3. 操作发送sql
4. 返回结果

## 添加驱动 ##
1. 新建文件夹libs
2. 添加 依赖jar包 mysql-connector
3. 右键jar,build path -> add path

## 注册驱动 ##
1. 代码告知使用mysql(可能添加了mysql.jar 可能添加了sqlserver.jar)
		* Class.forName("com.mysql.jdbc.Driver");// 包名+类名  
2. 静态代码块调用时机
	* 第一次字节码.class文件加载到内存中
	* 一旦用了,就将字节码.class文件加载到内存->ClassLoader
	
		public class Class1 {
			public Class1() {
				System.out.println("class constructor");
			}
			static {
				System.out.println("class1");
			}
		}
		//内存没有,就先加载到内存,然后创建;如果有就创建
		 Class1 class1 = new Class1();
		 System.out.println("~~~");
		 Class1 class111 = new Class1();

		// 装在到内存,不创建对象
		Class.forName("com.huaxin.jdbc.load.Class1");
		Class.forName("com.huaxin.jdbc.load.Class1");
		Class.forName("com.huaxin.jdbc.load.Class1");
	
## 两次加载问题 ##
1. 手动
	DriverManager.registerDriver(new  com.mysql.jdbc.Driver());
2. Driver中
	try {  
    // 注册驱动,注册两次 : com.mysql.jdbc.Driver的静态代码块里调用相同注册方法  
	    java.sql.DriverManager.registerDriver(new com.mysql.jdbc.Driver());  
	} catch (SQLException e) {  
	    e.printStackTrace();  
	}   
3. 最后
	Class.forName("com.mysql.jdbc.Driver");// 包名+类名  




# 连接mysql测试 #
1. 注册驱动
2. 连接mysql
	@Test
	public void testConnect() {
	    // System.out.println("~");
	    try {
	        /** 第一步：注册驱动 **/
	        // 加载 Driver类 :加载这个类:自动调用
	        // java.sql.DriverManager.registerDriver(java.sql.Driver) :
	        Class.forName("com.mysql.jdbc.Driver");
	        // Object object = new Object(); //O.java
	
	        /** 第二步： 连接 **/
	        // 地址 http://www.baidu.com:80/index.html
	        String url = "jdbc:mysql://localhost:3306/qq_1701";
	        String user = "root";
	        String password = "1234";
	        Connection connection = DriverManager.getConnection(url, user,
	                password);
	        System.out.println(connection);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}




# 数据测试 #

## 创表表 ##
	create table hx_student(
		id int primary key auto_increment,
		username varchar(20),
		password varchar(20),
		name varchar(20),
		birthday date,
		sex varchar(4),
		classId int,
	    foreign key(classId) references hx_class(id)
	);

	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','小红花',
	'1997-04-01','女',1);

	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','茉莉花',
	'1997-04-02','男',2);
	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','白百何',
	'1997-04-03','男',1);

## 创建类 ##
	int id;
	String username;
	String password;
	String name;
	Date birthday;
	String sex;
	int classId;


----------

测试 增删改查

## 注册测试 ##
1. 注册驱动
2. 获取连接mysql
3. 编写sql
4. 创建预编译执行者
5. 填写参数
6. 执行
7. 获取结果
8. 释放资源
	查看官方文档 搜索 .close() 在最后一个

		public void doBusinessOp() throws SQLException {
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
			try{} catch (SQLException sqlEx){}
			 finally {
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException sqlEx) {
	                    // You'd probably want to log this...
	                }
	            }
	
	            if (stmt != null) {
	                try {
	                    stmt.close();
	                } catch (SQLException sqlEx) {
	                    // You'd probably want to log this as well...
	                }
	            }
	
	            if (conn != null) {
	                try {
	                    // If we got here, and conn is not null, the
	                    // transaction should be rolled back, as not
	                    // all work has been done
	
	                    try {
	                        conn.rollback();
	                    } finally {
	                        conn.close();
	                    }
	                } catch (SQLException sqlEx) {
	                    //
	                    // If we got an exception here, something
	                    // pretty serious is going on, so we better
	                    // pass it up the stack, rather than just
	                    // logging it...
	
	                    throw sqlEx;
	                }
	            }

## 删除测试 ##

## 修改测试 ##

## 登入测试 ##


## 转账案例 ##


## JdbcUtil ##
		static String url;
		static String user;
		static String password;
		static {
			try {
				/** 第一步： 文件流 **/
				InputStream iStream = TestProperty.class.getClassLoader()
						.getResourceAsStream("jdbc.property");
				/** 第二步： 关联起来 **/
				Properties properties = new Properties();
				properties.load(iStream);
				/** 第三步： 获取资源 **/
				url = properties.getProperty("url");
				user = properties.getProperty("user");
				password = properties.getProperty("password"); // ctrl +1
				/** ----------------- **/
				/** 加载,加载驱动 **/
				Class.forName(properties.getProperty("driver"));
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public static Connection getConnection() throws Exception {
			Connection connection = DriverManager.getConnection(url, user,
					password);
			return connection;
	
		}
	
		public static void close(ResultSet resultSet, Statement stmt,
				Connection conn) {
			/** 最后：关闭资源 **/
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException sqlEx) {
				} // ignore
				resultSet = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlEx) {
				} // ignore
				conn = null;
			}
	
		}




### 硬编码转变配置文件 ###
	InputStream inStream = JdbcUtil.class.getClassLoader()
			.getResourceAsStream("jdbc.properties");
	Properties properties = new Properties();
	properties.load(inStream);
	String property = properties.getProperty("url");





