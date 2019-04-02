# 大纲 #
1. IOC注解
2. spring4JUnit


# 注解 #
	1. 取代传统xml配置,更简单
		* xml不利于团队开发
		* 臃肿
	2. 简化开发
		
## 开发步骤 ##		
	1. 创建工程,导包
		* 核心包:4个 + 依赖日志2个
		* web整合包:1个  spring-web
		* spring-aop.jar

	2. web三层
		* 接口+实现类
	3. 配置文件:
		* log4j
		* applicationContext.xml(需要保留,开启注解)
			*约束:(多一个context约束)
				<beans 
			       xmlns:context="http://www.springframework.org/schema/context"
			       xsi:schemaLocation="
					http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
				</beans>
	
			*开启扫描
				<!-- 
					component组件-scan扫描
					base-package:基本
				-->
				<context:component-scan base-package="" />

	4. 配置文件:web.xml
		* contextLoaderListener
		* contextparam
				<listener>
					<listener-class> org.springframework.web.context.ContextLoaderListener </listener-class>
				</listener>

			  <context-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>classpath:applicationContext.xml</param-value>  
			  </context-param>


	5. service,dao添加注解
		1. @Component(value = "userService")
		2. 等价 <bean id="userService" class="当前类全路径"/>

	6. 测试
	    public class TT {
	        @Test
	        public void run1(){
	            ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	            UserService userService = (UserService) ac.getBean("userService");
	            userService.save();
	        }
	    }


## 拆分applicationContext.xml配置文件 ##
	1. 更具web三层,对应三个application配置文件
		1. applicationContext-service.xml
		2. applicationContext-dao.xml
		3. applicationContext.xml
	2. 一个总配置文件引入
		*方式
			<!-- 拆分applicationContext.xml配置文件 -->
			<import resource="applicationContext-service.xml"/>	
	3. 对应层,扫描自己的
		1. applicationContext-service.xml
		2. <context:component-scan base-package="com.spring.service" />


## 常用注解 ##
	1. @Component:组件.(作用在类上)
	2. Spring中提供@Component的三个衍生注解:(功能目前来讲是一致的)
		* @Controller		-- 作用在WEB层
		* @Service			-- 作用在业务层
		* @Repository		-- 作用在持久层
		
		* 说明：这三个注解是为了让标注类本身的用途清晰，Spring在后续版本会对其增强
	
	3. 属性注入的注解(说明：使用注解注入的方式,可以不用提供set方法)
		* @Value			-- 用于注入普通类型
			* 如果是注入的普通类型，可以使用value注解
			* 如果导入配置文件,可以用el表达式直接获取值
				<context:property-placeholder location="jdbc.properties"/

				@Value("${jdbc.url}")
				String xx;
		* @Autowired		-- 默认按 类型byType 进行自动装配
			* 如果注入的是对象**类型**
			* 如果多个类型相同的实体
				* 优先找变量**名**相同的实体注入,
					* 如果没有id和变量名相同的,则报错 ,解决方法时，使用required=false
						Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.spring.dao.UserDao' available: expected single matching bean but found 2: userDao,userDao22	
					* 
			* @Qualifier	-- 强制使用名称注入
				* @Qualifier("userDao")
				* 必须要@Autowired一起使用		
			* 默认 不允许 null,
				* 默认不能注入 报错
				* 可以使用 里面属性  
					* required=false
		* @Resource			默认byName	-- 相当于@Autowired和@Qualifier一起使用
			* @Resource(name="userDao")
			* 强调：Java提供的注解,不是spring 注解
			* 属性使用name属性
			* 当没有name值,当前id值,默认是当前 类名/字段名??? 首字小写 ,当名字找不到时候,使用byType
				* UserDao ->   id="userDao"
			* name属性一旦指定，就只会按照名称进行装配

	4. bean作用范围
		* @scope	
			1. singleton :单例(默认)
			2. prototype :多列

![1](./img/bean_scope.jpg)

	5. 生命周期
		* @PostConstruct :创建init-method
		* @PreDestroy	 :销毁destroy-method

 


----------

----------

----------
# 整个junit #
1. 目的:简化junit测试
2. 步骤:
	1. 导包
		1. junit.ar
		2. spring-text.jar
	2. 添加注解
		1. @RunWith(SpringJUnit4ClassRunner.class)
		2. @ContextConfiguration("classpath:applicationContext.xml")
		3. @ContextConfiguration(classes=MyConfiguration.class)
		4. @ContextConfiguration(locations="classpath:applicationContext.xml")


----------
# 新注解 #
1. @Configuration
	1. 从Spring3.0，@Configuration用于定义配置类，可替换xml配置文件
	2. 相当于<beans>根标签
	3. 配置类内部包含有一个或多个被**@Bean注解**的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器

	value:用于指定配置类的字节码 
		@Configuration(X.class)

2. @Bean注解
	1. 标注在方法上(返回某个实例的方法)，等价于spring配置文件中的<bean>
	2. 作用为：注册bean对象
	3. 主要用来配置非自定义的bean，比如DruidDataSource、SqlSessionFactory
	4. @Bean 可以不给定 value 属性,默认与标注的**方法名**相同
	5. 默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域

```
		@Bean(value="userService")
		@Scope(“prototype”)
		public UserService userService(){
			return new UserServiceImpl(1,“张三”);
		}
```
3. @ComponentScan
	* 相当于context:component-scan 标签
	* 组件扫描器，扫描@Component、@Controller、@Service、@Repository 注解的类。
	* 该注解是编写在类上面的，一般配合@Configuration注解一起使用。

	* basePackages：用于指定要扫描的包。
	* value：和basePackages作用一样。
		@ComponentScan(basePackages="com.xx.spring.service")
4. @PropertySource
	***加载properties配置文件
	***编写在类上面
	***相当于context:property-placeholder标签

	value[]：用于指定properties文件路径，如果在类路径下，需要写上classpath
		@PropertySource(“classpath:jdbc.properties”)
5. @Import
	***用来组合多个配置类
	***相当于spring配置文件中的import标签
	***在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问题。

	value：用来指定其他配置类的字节码文件

6. @Aspect
	1. 标记 该类 是切面类
	2. 一般需要和 @Component 组合, 交给 spring 管理
	3. 需要开启aop自动代理
		<aop:as[ectj-autoproxy/>
		@EnableAspactjAutoProxy
7. @Before
	1. 切面类 中的 通知 注解
	2. @Before("webLog()")   // 切入点方法名() , ()不能少
	3. @Before("execution(* *..*.*ServiceImpl.*(..))")

8. @Pointcut
	1. 切入点表达式
	2.  @Pointcut("@annotation(org.infrastructure.common.aop.RequestProcess)") 
	3.  @Pointcut(""execution(* *..*.*ServiceImpl.*(..))") 

9. @EnableAspectJAutoProxy







