# 大纲 #
1. 数据库连接池
2. 事务


----------
# 连接池(数据源) #
1. 在dao层操作数据库时候,Connection对象在JDBC使用的时候.使用的时候就会去创建一个对象,使用结束以后就会将这个对象给销毁了.每次创建和销毁对象都是耗时操作.
2. 需要使用连接池对其进行优化
3. conn.close(); 不能真的关闭
4. sum公司提供了一套规范接口:
	* 所有连接池,必须遵循这套规范
	* DataSource
		* 获取  getConnection
		* 归还  conn.close();


[jdbc参考](http://blog.csdn.net/u012689060/article/details/69945491)


## 自定义连接池-性能对比 ##
1. 原理
	* 创建一个容器存放连接 LinkedList<Connection>
	* 连接池提供获取连接的方法
	* 连接池提供释放连接的方法
2. 实现
		static LinkedList<Connection> pool = new LinkedList<Connection>();
		static String url;
		static String username;
		static String pwd;
		static {
			try {
				// 配置
				// 配置文件
				Properties properties = new Properties();
				InputStream in = MyDataSource.class.getClassLoader()
						.getResourceAsStream("jdbc.properties");
				// 加载文件
				properties.load(in);
				/** 动态获取账密码 **/
				url = properties.getProperty("url");
				username = properties.getProperty("username");
				pwd = properties.getProperty("pwd");
				/** 注册驱动 **/
				Class.forName(properties.getProperty("className"));
				// 初始化数据库连接池
				pushPooled(3);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	
		// 扩容
		public static void pushPooled(int num) throws SQLException {
			for (int i = 0; i < 3; i++) {
				Connection connection = DriverManager.getConnection(url, username,
						pwd);
				pool.add(connection);
			}
		}
	
		// 获取
		public  Connection getConnection() throws SQLException {
			if (pool.size() <= 0) {
				pushPooled(3);
			}
			return pool.removeFirst();
		}
	
		// 归还
		public  void close(Connection conn) throws SQLException {
			pool.addLast(conn);
		}
3. 性能对比
	* 原生:大概耗时4000ms
		@Test
		public void test1() throws SQLException {
			long currentTimeMillis = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				Connection conn = JDBCUtil.getConn();
				System.out.println("num:" + i + " " + conn);
				conn.close();
			}
			System.out.println(
					"耗时" + (System.currentTimeMillis() - currentTimeMillis));
		}
	* 连接池:大概耗时260ms
		@Test
		public void test2() throws SQLException {
			long currentTimeMillis = System.currentTimeMillis();
			MyDataSource dataSource = new MyDataSource();
			for (int i = 0; i < 1000; i++) {
				Connection conn = dataSource.getConnection();
				System.out.println("num:" + i + " " + conn);
				dataSource.close(conn);
			}
			System.out.println(
					"耗时" + (System.currentTimeMillis() - currentTimeMillis));
		}

4. 问题
	* 查看jdk规范,没有看到归还api,只能是conn.close()
	* 如何修改Connection中的close()方法?让其不是真正的关闭,而是放回连接池容器里面去呢?
	* 方法增强
	

## 方法 ##
1. 继承/实现:connection子类,
	* 但是实现由厂商提供,没有源码!!!分不清是mysql还是oracle
2. 包装模式(静态代理)
3. 动态代理


----------

# 代理 #
## 静态代理 ##
1. 对象
	包装对象,被包装对象,
2. 方式
	* 包装对象和被包装对象必须由相同的父类,或者实现相同的接口
	* 包装对象里面必须要有被包装对象的引用
	* 对需要加强的方法加强
	* 不需要加强的方法,原封不动的调用被包装对象的方法
3. 案例: 
	* 房子接口 
		public interface House {
			public void wall();
			public void floor();
		} 
	* 一般房子 
		public class CommonHouse implements House {
			public void wall() {
				System.out.println("墙");
			}
			public void floor() {
				System.out.println("地板");
			}
		}
	* 房子包装
		public class HouseWrapper implements House {
			private CommonHouse house;
			public HouseWrapper(CommonHouse commonHouse) {
				this.house = commonHouse;
			}
			public void wall() {
				System.out.println("黄金地板墙");
			}
			public void floor() {
				house.floor();
			}
		}
	* 测试
		public class TestWrapper {
			public static void main(String[] args) {
				CommonHouse commonHouse = new CommonHouse();
				HouseWrapper houseWrapper = new HouseWrapper(commonHouse);
				houseWrapper.floor();
				houseWrapper.wall();
			}
		}
4. 通用性
	* 房子包装
		public class HouseWrapper implements House {
			private House house;
			public HouseWrapper(House commonHouse) {
				this.house = commonHouse;
			}
			public void wall() {
				System.out.println("黄金地板墙");
			}
			public void floor() {
				house.floor();
			}
		}
	* 测试
		public class TestWrapper {
			public static void main(String[] args) {
				House commonHouse = new CommonHouse();
				House house = new HouseWrapper(commonHouse);
				house.floor();
				house.wall();
			}
		}
5. 思路嫁接到conn的close方法
	* 创建MyConnWrapper,实现java.sql.Connection
	* 编写包装者引用
	* 对close功能加强
	* 对其他方法,原封不动的调用被包装对象的方法
			Connection conn;
			public MyConnWrapper(Connection conn) {
				this.conn = conn;
			}
			// 加强
			public void close() throws SQLException {
				// 添加到连接池中
				MyDataSource.close(conn);
			}
	* 了解,close()方法为什么会回到连接池容器中
	

[参考](http://blog.csdn.net/pnjlc/article/details/52701929)

## 动态代理 ##
----------
# 三方连接池 #
1. dbcp
2. c3p0
3. 德鲁伊 druid


# dbcp #
1. DBCP(DataBase connection pool),数据库连接池 
2. apache 开源项目
3. 单独使用dbcp需要2个包：commons-dbcp.jar,commons-pool.jar
4. 使用
	1. 下载
		* http://commons.apache.org/proper/commons-dbcp/download_dbcp.cgi
		* http://commons.apache.org/proper/commons-pool/download_pool.cgi
		* http://commons.apache.org/proper/commons-logging/download_logging.cgi
	2. 导包
		* dbcp.jar
		* pool.jar  依赖包
	3. 配置:连接四大参数
		* 硬编码写死在代码里,
			public static void main(String[] args) throws SQLException {
				long currentTimeMillis = System.currentTimeMillis();
				BasicDataSource dataSource = new BasicDataSource();
				String url = "jdbc:mysql://localhost:3306/hx_1701_manager";
				String driverClassName = "com.mysql.jdbc.Driver";
				String username = "root";
				String password = "1234";
		
				dataSource.setUrl(url);
				dataSource.setDriverClassName(driverClassName);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
				for (int i = 0; i < 1000; i++) {
					Connection connection = dataSource.getConnection();
					System.out.println("num:" + i + " " + connection);
					connection.close();
				}
				System.out.println(
						"耗时" + (System.currentTimeMillis() - currentTimeMillis));
			}
		* 配置文件形式
			* 编写配置文件 properties 文件 
			* 创建BasicDataSourceFactory.createDataSource(pro);
			public static void main(String[] args) throws Exception {
				Properties prop = new Properties();
				InputStream resourceAsStream = DbcpTest2.class.getClassLoader()
						.getResourceAsStream("dbcp.properties");
				prop.load(resourceAsStream);
				BasicDataSource dataSource = new BasicDataSourceFactory()
						.createDataSource(prop);
				long currentTimeMillis = System.currentTimeMillis();
				for (int i = 0; i < 1000; i++) {
					Connection connection = dataSource.getConnection();
					System.out.println("num:" + i + " " + connection);
					connection.close();
				}
				System.out.println(
						"耗时" + (System.currentTimeMillis() - currentTimeMillis));
			}
			
			url = jdbc:mysql://localhost:3306/hx_1701_manager
			username = root
			password = 1234
			driverClassName = com.mysql.jdbc.Driver
			
----------


# c3p0 数据库连接池#
1. 介绍 
		* https://baike.baidu.com/item/c3p0/3719378?fr=aladdin
		* 目前使用它的开源项目有Hibernate，Spring等。
		* c3p0与dbcp区别
			dbcp没有自动回收空闲连接的功能
			c3p0有自动回收空闲连接功能
2. 介绍 
		http://blog.csdn.net/u012689060/article/details/69945491
3. 使用
	1. 下载
		1. [官网](http://www.mchange.com/projects/c3p0/)
	2. 导包
		* c3p0.jar
		* mchange.jar
	3. 配置:连接四大参数
		* 硬编码写死在代码里
			*ComboPooledDataSource
			public static void main(String[] args) throws Exception {
				ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
				String jdbcUrl = "jdbc:mysql://localhost:3306/hx_1701_manager";
				String driverClass = "com.mysql.jdbc.Driver";
				String user = "root";
				String password = "1234";
				dataSource.setDriverClass(driverClass);
				dataSource.setJdbcUrl(jdbcUrl);
				dataSource.setUser(user);
				dataSource.setPassword(password);
				long currentTimeMillis = System.currentTimeMillis();
				for (int i = 0; i < 1000; i++) {
					Connection connection = dataSource.getConnection();
					System.out.println("num:" + i + " " + connection);
					connection.close();
				}
				System.out.println(
						"耗时" + (System.currentTimeMillis() - currentTimeMillis));
			}
		* 配置文件
			* c3p0.properties
			public static void main(String[] args) throws Exception {
				ComboPooledDataSource dataSource = new ComboPooledDataSource();
				long currentTimeMillis = System.currentTimeMillis();
				for (int i = 0; i < 1000; i++) {
					Connection connection = dataSource.getConnection();
					System.out.println("num:" + i + " " + connection);
					connection.close();
				}
				System.out.println(
						"耗时" + (System.currentTimeMillis() - currentTimeMillis));
			}
			
			c3p0.jdbcUrl = jdbc:mysql://localhost:3306/hx_1701_manager
			c3p0.user = root
			c3p0.password = 1234
			c3p0.driverClass = com.mysql.jdbc.Driver

----------


----------

# 事务 DTL#
1. 出现在增删改操作里面
2. 一个业务逻辑,多个单元操作
3. 整个业务里面的connection必须只有1个

## 转账 ##
1. 转出(A扣钱)
2. 转入(B加钱)

# Navicat 创建表 #
	create table account (
	id int,
	username varchar(20),
	password varchar(64),
	money double
	);


# ThreadLocal #










----------


----------



# web三层架构 #
1. web
2. 业务逻辑层:service
3. 数据库访问层:dao(data access object)


## web层 ##
1. 组成:前端界面(html,jsp)  + 控制层(servlet)
2. 分包

### 前端界面 ###
1. 展示
2. 收集表单


### servlet ###
1. 接收请求
2. 组装数据
3. 一定时调用下面一层service层的方法
4. 界面的跳转控制(转发)
5. 处理异常


## service层 ##
1. 接收上层传递的数据
2. 给上层提供服务的(登入,注册,转账)



## dao ##
1. 数据库访问层

1. 一个数据库对应一个项目
2. 数据库里面每个**实体表**对应着一个**javabean**
3. 每个bean都有一个或者多个的dao
4. 每个bean都有一个或者多个的service
5. service里面必定有底层dao的引用
6. servlet里面必定有底层service引用
7. 异常通常要try 在catch里面 throw
8. 最后的异常统一在servlet层处理







# web登入案例(后台) #



	web
		作用:
			展示数据 ----jsp
			
			
			-----servlet-------
			接受请求
			找到对应的service,调用方法 完成逻辑操作
			信息生成或者页面跳转
	service 业务层
		作用:
			完成业务操作
			调用dao
	dao(data access object 数据访问对象)
		作用:
			对数据库的curd操作