# 大纲 #
	1. Spring框架的概述
	2. SpringIOC的快速入门
	3. IoC容器XML的方式
	4. 在web项目中集成Spring


----------

----------

----------

# Spring介绍 #


## spring是什么 ##
  1. [官网](http://spring.io/)
  2. [百度百科](https://baike.baidu.com/item/spring/85061?fr=aladdin )
			关键字  企业

			* Spring是一个开源框架
			* Spring是于2003 年兴起的一个**轻量级**的Java开发框架，由Rod Johnson在其著作Expert One-On-One J2EE Development and Design中阐述的部分理念和原型衍生而来。
			* 它是为了解决企业应用开发的 复杂性 而创建的。框架的主要优势之一就是其分层架构，分层架构允许使用者选择使用哪一个组件，同时为 J2EE 应用程序开发提供集成的框架。
			* Spring使用基本的JavaBean来完成以前只可能由 EJB 完成的事情。然而，Spring的用途不仅限于服务器端的开发。从简单性、可测试性和松耦合的角度而言，任何Java应用都可以	从Spring中受益。
			* Spring的**核心是控制反转（IoC）和面向切面（AOP）**。简单来说，Spring是一个分层的JavaSE/java EE   full-stack(一站式) 轻量级开源框架。



## 一站式开发 ##
* Web开发分成三层结构
	* WEB层		-- Spring MVC
	* 业务层		-- Bean管理:(IOC)
	* 持久层		-- Spring的JDBC模板.ORM模板用于整合其他的持久层框架

spring 整合



## 框架特征,优点 ##
	1. 轻量:
			
	2. IoC:Inverce of Control(控制反转)
			创建对象的权利,从程序员手里反转到spring->程序员不需要new对象,以后都让给spring!
	3. AOP:aspect oriented programming(面向切面编程)
			不需要程序员去关心:审计（auditing）,事务（transaction）,日志(log)管理,专心去完成业务逻辑就可以
	4. 容器:
			Spring容器,Spring就是一个大工厂(new对象)，可以将所有对象创建,销毁和依赖关系维护(生命周期)，交给Spring管理
	5. 框架:
			整合各种优秀的开源框架
	6. MVC:
			Spring Mvc
	7. 注解：
			通过注解,达到快速开发、测试
	

----------

----------

----------

# Spring IoC #

## 什么是IoC ##
	1. IoC:Inversion of Control(控制反转)
			创建对象的权利,从程序员手里反转到spring->程序员不需要new对象,以后都让给spring!
	2. 解决的程序耦合性高的问题

	3. 对项目中,所有创建对象监控// 代理类 BeanPostProcessor

![](./002_IOC.bmp)



1. 当web 获取 service的时候，发现service需要依赖dao，spring会自动注入-> 依赖注入
2. spring容器-工厂：spring提供：需要spring jar 包
3. spring容器-xml :需要自己编写。哪些类、哪些资源交给spring，需要你自己做决定，配了帮你创建，不配不创建


![ioc](img/spring01.png)


## spring 开发步骤(入门)  ##
1. 下载
	* [官网](http://spring.io/)
		* projects->spring-framework->github->Downloading Artifacts->repo->
			[抵达](https://repo.spring.io/webapp/#/home)->(第二个)Artifact Repository Browser
			->libs-release-local->org->springframework->spring
	* [下载教程](http://jingyan.baidu.com/article/90808022f060c5fd90c80f62.html)
	* [官网下载地址](https://repo.spring.io/webapp/#/home)
	* [下载地址](http://repo.springsource.org/libs-release-local/org/springframework/spring/)
			* docs		-- API和开发规范
			* schema	-- xml约束
			* dist		-- 所有：docs，schema，lib

2. 导包

![Spring的体系结构](http://img.blog.csdn.net/20140601165854859?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGFuZXIwNTE1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

		* 三个三个一组：使用包、文档包、源码包
		* 核心包
				* Beans
				* Core
				* Context
				* Expression Languag
			* Caused by: java.lang.ClassNotFoundException: org.apache.commons.logging.LogFactory
		----------------------------------------------------------
		*日志包
				* org.apache.commons\com.springsource.org.apache.commons.logging
				* log4j
					
				
		----------------------------------------------------------
		*日志
				*介绍：http://blog.csdn.net/geekun/article/details/51398621


3. 编写web三层
		* UserService			-- 接口
		* UserServiceImpl		-- 具体的实现类

4. 将service交给spring 容器
		* 创建XML配置文件
			* 在src目录下创建**applicationContext.xml**的配置文件（名称是可以任意的，但是一般都会使用默认名称）

		* 编写配置文件约束
			* doc/spring-framework-reference/html/xsd-configuration.html (最后)
			
			<?xml version="1.0" encoding="UTF-8"?>
			<beans xmlns="http://www.springframework.org/schema/beans"
			    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			    xsi:schemaLocation="
			        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
			     
			</beans>		

		*在beans 标签里面
			<bean id="userService"  class="com.spring.t2.ioc.UserServiceImpl" />


5. 使用工厂给web层中service赋值
			@Override
			public void register() {
				System.out.println("register业务逻辑~~");
			}
		

		/** 第一步： spring工厂 applicationContext **/
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		/** 第二步： 工厂获取对象 userService **/
		UserService userService = (UserService) applicationContext
				.getBean("userService");
		/** 第三步： 调用 **/
		userService.register();
 



----------

----------

----------

# spring 工厂 #

## ApplicationContext 接口 ##
		* 该接口下有两个具体的实现类
			* ClassPathXmlApplicationContext			-- 加载类路径下的Spring配置文件
			* FileSystemXmlApplicationContext			-- 加载本地磁盘下的Spring配置文件

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
## BeanFactory工厂(老工厂，过时) ##
		*使用
			/** 第一步： spring工厂 applicationContext **/
			BeanFactory factory = new XmlBeanFactory(
					new ClassPathResource("applicationContext.xml"));
			/** 第二步： 工厂获取对象 userService **/
			UserService userService = (UserService) factory.getBean("userService");
			/** 第三步： 调用 **/
			userService.login();

## 新，老工厂 区别 ##
1. BeanFactory延迟加载：第一次getBean()的时候，创建
2. ApplicationContext：程序一启动就创建
3. ApplicationContext更加强大：
		* 事件传递
		* Bean自动装配
		* 各种不同应用层的Context实现


----------

----------

----------

# 没有网，spring xml提示配置#
	**配置Spring框架编写XML的提示**
		1. 步骤一：先复制地址， http://www.springframework.org/schema/beans/spring-beans.xsd	
		2. 步骤二：preferences:搜索XML Catalog，点击Add按钮
		3. 步骤三：先选择Location的schema的约束地址
			* \spring-framework-4.3.10.RELEASE-schema\beans\spring-beans-4.3.xsd
		4. 步骤四：注意：Key type要选择：Schema location
		5. 步骤五：Key把http://www.springframework.org/schema/beans/spring-beans.xsd复制上

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


# 配置文件中<bean>标签 #
	1. id属性和name属性的区别
		* id		-- Bean起个名字，在约束中采用ID的约束，唯一
			* 取值要求：必须以字母开始，可以使用字母、数字、连字符、下划线、句话、冒号	id:不能出现特殊字符
		
		* name		-- Bean起个名字，没有采用ID的约束（了解）
			* 取值要求：name:出现特殊字符.如果<bean>没有id的话 , name可以当做id使用
			* Spring框架在整合Struts1的框架的时候，Struts1的框架的访问路径是以/开头的，例如：/bookAction
	
	2. class属性			-- Bean对象的全路径
	3. scope属性			-- scope属性代表Bean的作用范围
		* singleton			-- 单例（默认值）
			* ***一个应用只有一个对象的实例。它的作用范围就是整个引用。
			***生命周期：
				*对象出生：当应用加载，创建容器时，对象就被创建了。
				*对象活着：只要容器在，对象一直活着。
				*对象死亡：当应用卸载，销毁容器时，对象就被销毁了。
		* prototype			-- 多例，在Spring框架整合Struts2框架的时候，Action类也需要交给Spring做管理，配置把Action类配置成多例！！ 因为action 参数是成员变量!
			***每次访问对象时，都会重新创建对象实例。
			***生命周期：
				*对象出生：当使用对象时，创建新的对象实例。
				*对象活着：只要对象在使用中，就一直活着。
				*对象死亡：当对象长时间不用时，被 java 的垃圾回收器回收了。		
		* request			-- 应用在Web项目中,每次HTTP请求都会创建一个新的Bean
		* session			-- 应用在Web项目中,同一个HTTP Session 共享一个Bean
		* globalsession		-- 应用在Web项目中,多服务器间的session




	
	4. Bean对象的创建和销毁的两个属性配置（了解）
		* 说明：Spring初始化bean或销毁bean时，有时需要作一些处理工作，因此spring可以在创建和拆卸bean的时候调用bean的两个生命周期方法
		* init-method		-- 当bean被载入到容器的时候调用init-method属性指定的方法
		* destroy-method	-- 当bean从容器中删除的时候调用destroy-method属性指定的方法
			* 想查看destroy-method的效果，有如下条件
				* scope= singleton有效
				* web容器中会自动调用，但是main函数或测试用例需要手动调用（需要使用ClassPathXmlApplicationContext的close()方法）
	5. factory-bean属性  factory-method
		1. 可以指定 那个 自定义工厂创建   factory-bean="refBeans"
		2. factory-method指定  工厂ref引用中的 创建方法
	6. 静态工厂
		1. id
		2. class="xx.AFactory"
		3. factory-method

	
# bean 后处理器  BeanPostProcessor #
1. 实现 BeanPostProcessor接口, 编写 
	1. postProcessBeforeInitialzation	// bean初始化之前
	2. postProcessAfterInitialization	//
2.  注册, 不需要id,直接写class

----------

----------

----------

# DI:Dependency Injection(依赖注入) #
## 什么是DI ##
1. Dependency Injection，依赖注入，在Spring框架负责创建Bean对象时，动态的将依赖对象注入到Bean组件中！！


## 注入方式 ##
	1. 对于类成员变量，常用的注入方式有两种
		* 构造函数注入
		* 属性setter方法注入
		
	2. 构造函数注入
		* 编写Java的类，提供构造方法
			public class User {
				private String username;
				private int age;
				public Car(String username, int age) {
					this.username = username;
					this.age = age;
				}
				@Override
				public String toString() {
					return "User [username=" + username + ", age=" + age + "]";
				}
			}
		
		* 编写配置文件
			<bean id="user" class="com.spring.bean.User" >
				<constructor-arg name="username" value="小红花" />
				<constructor-arg name="age" value="15" />  
			</bean>
		*注意：
			1. name属性： 对应着 javaBean里面的字段名称(全局变量名)
			2. value属性：8大基本数据类型+八大基本类型的应用类型+String ，使用value赋值
			3. 配置文件几个参数，实体javaBean里面必需提供对应的构造方法
			
	3. 属性的setter方法的注入方式
			* 编写Java的类，提供属性和对应的set方法即可
			* 编写配置文件
				<bean id="user2" class="com.spring.bean.User2" >
					<property name="username" value="大红花"/>
					<property name="age" value="22"></property>
				</bean>
	
	4. 怎么注入类类型(应用类型)？
		* <property name="name" rel="具体的Bean的ID或者name的值"/>
		* 例如：
			<bean id="person" class="com.itheima.demo4.Person">
				<property name="pname" value="美美"/>
				<property name="car2" ref="car2"/>
			</bean>


xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	
# 技术分析之Spring的2.5版本中提供了一种:p名称空间的注入（了解） #
	
	1. 步骤一：需要先引入 p 名称空间
		* 在schema的名称空间中加入该行：xmlns:p="http://www.springframework.org/schema/p"
	
	2. 步骤二：使用p名称空间的语法
		* p:属性名 = ""
		* p:属性名-ref = ""
	
	3. 步骤三：测试
		* <bean id="person" class="com.xx.Person" p:pname="老王" p:car2-ref="car2"/>
	
----------
	
#技术分析之Spring的3.0提供了一种:SpEL注入方式（了解）#
	
	1. SpEL：Spring Expression Language是Spring的表达式语言，有一些自己的语法
	2. 语法
		* #{SpEL}
	
	3. 例如如下的代码
		<!-- SpEL的方式 -->
		<bean id="person" class="com.itheima.demo4.Person">
			<property name="pname" value="#{'小风'}"/>
			<property name="car2" value="#{car2}"/>
		</bean>
	
	4. 还支持调用类中的属性或者方法
		* 定义类和方法，例如
			public class CarInfo {
				public String getCarname(){
					return "奇瑞QQ";
				}
			}

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## 注入类型 ##
	
	1. 8大基本数据类型+八大基本类型的应用类型+String ，使用value赋值
		<bean id="user2" class="com.spring.bean.User2" >
			<property name="username" value="大红花"/>
			<property name="age" value="22"></property>
		</bean>

	2. Class类型，使用ref引用bean的id值
		<bean id="hxClass" class="com.spring.bean.HxClass" >
			<property name="className" value="华信1701"/>
		</bean>
		<bean id="user3" class="com.spring.bean.User3" >
			<property name="hxClass" ref="hxClass"/>
		</bean>

	3. 数组或者List集合，在<property>标签里面，编写<list>子标签,赋值使用 value或ref
		<bean id="" class="">
			<property name="names">
				<list>
					<value>大红花</value>
					<value>小红花</value>
					<bean>a</bean>  //???
					<ref bean="emp1"/>
				</list>
			</property>
		</bean>
	
	4. Set集合，：在<property>标签里面，编写<set>子标签,赋值使用 value或ref
		<property name="sets">
			<set>
				<value>哈哈</value>
				<value>呵呵</value>
			</set>
		</property>
	
	5. Map集合，在<property>标签里面，编写<map>子标签,里面<entry>标签,赋值使用 value或ref
		<property name="map">
			<map>
				<entry key="老大" value="38"/>
				<entry key="老二" value="28"/>
				<entry key="老三" value="18"/>
				<entry key="老三" value-ref=""/>
			</map>
		</property>
	
	6. properties，注入的配置如下：
		<property name="pro">
			<props>
				<prop key="username">root</prop>
				<prop key="password">123</prop>
			</props>
		</property>



----------

----------

---------- 

# 思路 #
1. web层
	* 每次请求都会创建一个工厂类,服务器端的资源就浪费了,一般情况下一个工程只有一个Spring的工厂类就好了
	* 将工厂在服务器启动的时候创建好,将这个工厂ServletContext域中.每次获取工厂从ServletContext域中进行获取
		* ServletContextLinstener	:监听ServletContext对象的创建和销毁.
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
				CustomerService customerService = (CustomerService) applicationContext.getBean("customerService");

# web  整合 #
1. 导入web整合jar包
	spring-web-4.3.10.RELEASE
2. 编写三层:
	1. jsp servlet  service dao
3. 配置:
	1. service依赖dao
4. web层从spring容器获取service
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		UserService userService = (UserService) webApplicationContext
				.getBean("userService");
		userService.login();

5. 希望程序一运行就启动工厂,加载配置文件	
	1. 程序一启动,会创建servletContext(app)对象
	2. 使用ServletContextListener 监听器
	3. spring提供好了监听器	ContextLoaderListener
	4. 告诉listener配置文件在哪,给应用提供初始化参数
		      <listener>
				<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>  
			  </listener>
			  
			  <context-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>classpath:applicationContext.xml</param-value>  
			  </context-param>