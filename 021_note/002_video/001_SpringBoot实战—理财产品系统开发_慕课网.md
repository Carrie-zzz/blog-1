# 简介 #
**功能**
1. 用户管理系统
2. 账户管理系统
3. 产品管理系统 
4. 支付管理系统
5. 订单管理系统

**产品**
1. 小公司套壳大公司(有金融资质的金融公司)的壳

# 要求特性 #
1. 快速(拆分模块,swagger接口)
	1. 开发快
	2. 迭代快 : 发展快,法律监管变化快,要及时迭代
2. 高效(系统集群,数据库集群,缓存)
	1. 高并发
	2. 响应快
3. 安全(https,rsa,权限控制,节流控制,访问统计tyk)
	1. 数据加密
	2. 权限:接口功能

**分模块**
1. 销售端()
2. 管理端

** 技术 **
## 构建 ##
1. gradle

## 开发 ##
2. springboot
3. spring data jpa
4. 自动化测试
5. swagger

## 业务 ##
1. 对账业务
2. 对账文件生成解析
3. 对账,平账
4. 定时对账
5. jpa多数据源
6. jpa读写分离
7. 源码修改

## 高效优化 ##
1. jsonrpc
2. Hazelcast
	1. 内存数据网格
3. ActiveMq
4. 模块化分装

## 安全 ##
1. https
2. RSA
3. TYK : API网关


# 模块 #
**基础**
1. util
2. quartz
3. swagger

**抽象**
1. entity
2. api

**web**
1. manager
	1. crud
2. saller
	1. jsonrpc
	2. hazecast
	3. activemq
	4. moke测试
	5. tyk
	6. quartz
	

# 第二章 #
--------- 

**模块化开发**
1. 高内聚,低耦合
2. 并行开发,提高效率
3. 轮子重复使用

**划分**
1. 业务划分 service dao
2. 功能划分 管理,销售

**名字**
1. 项目
2. 模块
3. 工程
4. 应用:独立部署


**产品表**
1. 编号,名称,收益率,锁定时期,状态,起投,投资步长
2. 时间类型  
	1. datetime 时间更广(常用)
	2. timestamp 有时区信息	
3. 状态枚举
	1. 审核中 2. 销售中 3. 暂停销售 4. 已结束

**订单**
1. 订单编号, 渠道编号, 产品编号,用户编号,外部订单编号,类型,状态,金额
2. 订单类型
	1. 申购 2. 赎回
3. 订单状态
	1. 初始化 2. 处理中 3. 成功 4. 失败
4. @see  com.xxx.xxstatues

**toString**
1. 自动生成
2. commons包 里面 
	1.  ReflectionToStringBuilder.toString(obj,style)

# 第三章 #
----------
1. jpa  spring data
	* JpaRepository<T,T>
		* Bean
		* id类型
	* JpaSpecificationRepository
	* api
		* findOne(id)
		* find
	* @EntityScan(basePackage={""})

2. dto不用原始类型
	1. 原始类型默认值0,不能区分是自己设置的还是默认的 
	2. 一般在dto中使用 类类型,也不设置默认值
3. 日志
	1. controller
		1. info级别
	2. service
		1. debug级别
4. 错误处理
	1. spring boot document 搜索error
				https://docs.spring.io/spring-boot/docs
	2. 不同客户端
		1. machine clients
			1. JSON 
		2. browser clients
			1. 默认添加 accept:text/html
	3. 自定义错误
		1. 继承 BasicErrorController 
			1. 默认路径 /error
			2. produces 	
		2. 切面拦截 @ControllerAdvice
			1. @ControllerAdvice(basePackageClasses = AcmeController.class)
		3. Custom Error Pages 自定义错误页面
			1. 在public/template下面 添加error 文件夹
			
	4. jackson配置
		1. 文档搜索,找到很多配置的那
			spring.jackson.date-format= # Date format string or a fully-qualified date format class name. For instance, `yyyy-MM-dd HH:mm:ss`.   yyyy-MM-dd HH:mm:ss
			spring.jackson.time-zone= #  Time zone used when formatting dates. For instance, "America/Los_Angeles" or "GMT+10".						GMT+8   //东8区
	5. 错误自动配置
		1. BasicErrorController在 **ErrorMvcAutoConfiguration**
		2. 在spring boot autoconfigure
	6. 自定义: 
		1. 继承 BasicErrorController
			1. 三个桉树构造方法
			2. 重写getErrorAttriutes() 方法 // 错误信息
			3. @Configuration @Bean 注入自己写的类 
			4. 参数都是自动注入的,写在参数列表后面
		2. @ControllerAdvice
			1. 类添加@ControllerAdvice	
				1. 可以指定 那个包, basePackage
			2. 方法上面添加 
				1. @ExceptionHandler(value = MyRemoteServiceException.class)
				2. @ResponseBody
			3. 方法参数
				1. final Exception ex 
				2. final WebRequest request
		3. controller异常 -> controllerAdvice 异常 ->  BasicErrorController
			1. controller异常 ,先到 advice, advice没有异常就正常返回
			2. advice出现异常,就到BasicErrorController

	6. 错误类
		1. code , message , canRetry
	
6. 自动化测试
	1. 自动化测试
		1. 功能测试:模拟 http请求接口
		2. junit  @RunWith
				@BeforeClass
					@Before
					@Test
						Assert
					@After
				@AfterClass
	2. 测试
	

```java
@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnviroment=SpringBootTest.WebEnviroment.Random_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderBaseTest {
	private static RestTemplate = new RestTemplate();
	@Value("${local.server.port}")
	int port; // 随机端口
	@BeforeClass
	public void initData(){
		...
	}		
	@Test
	public void testSuccessCreate(){
		1. 准备数据,设置请求头,请求参数
		2. 断言 Assert
			1. notNull(param,message);
			2. isTrue(boolean param,message);
	}

}
```
7. 执行顺序
	1. @FixMethodOrder
		1. MethodSorters.NAME_ASCENDING	字段顺序
		2. MethodSorters.JVM			JVM返回顺序,每次看你不一样
		3. MethodSorters.DEFAULT		默认	,hashcode比较



# 第四章 swagger #
----------
1. 介绍
	1. 前后端分离,接口不好
	2. 需求变更,接口文档乱
	
[swagger文档](http://springfox.github.io/springfox/docs/current/#springfox-swagger-ui)

2. 开发步骤
	1. 添加依赖
		1. springfox-swagger2
		2. springfox-swagger-ui
	2. 添加配置类

3. 优化
	1. 选着显示/隐藏接口
		1. 包匹配
 			apis(RequestHandlerSelectors.basePackage(getBasePackage())
		2. 路径匹配
			paths(PathSelectors.any())
			paths(PathSelectors.ant("/order/*"))
	2. 注释说明
		1. 类上面说明 @Api(tags = "xx",description="描述")
		2. 方法上面 	@apiOperation(value,note)
		3. 字段上面	@ApiModelProperty()
			1. value
			2. name
			3. required
			4. hidden
			5. dataType 枚举指向~
	3. 中文显示(国际化配置)
		1. 在resource下新建 /META-INFO/resources 下创建swagger-ui.html
		2. 复制springfox-swagger-ui下面的swagger-ui.html源码
		3. 引入 
```
	 < script src='webjars/springfox-swagger-ui/lang/translator.js' type='text/javascript'></script>
	 < script src='webjars/springfox-swagger-ui/lang/zh-cn.js' type='text/javascript'></script>
```
	4. 分组,多个Docket对象
		1. groupName("1")  //不写就是默认分组
	

````       
 return new Docket(DocumentationType.SWAGGER_2)
		.groupName("1")
		.apiInfo(apiInfo())
        .enable(getEnableSwagger())
        .securitySchemes(Lists.newArrayList(apiKey()))
        .securityContexts(newArrayList(securityContext()))
		.select()
		// 选着包
        .apis(RequestHandlerSelectors.basePackage(getBasePackage()))
		// 匹配路径
        .paths(PathSelectors.any()).build().globalOperationParameters(pars);
```

4. swagger 模块
	1. 使用配置文件配置
		1.  @Import(MySwaggerConfiguration.class)  
	2. 组合注解
		1. 注解上有多个注解,
		2. 起到多个功能效果,起到简化作用
		3. @EnableSwagger
	3. @Enable*原理  自动配置
		* 在resources下新建 **spring.factorie**
		* EnableAutoConfiguration=com.xx.AaaConfiguraio
	4. 属性配置 			
		* @ConfigurationProperties(prefix="")
	* 配置参数  xxConfigurationProperties(prefix="")
		Docket ApiInfo
			* groupName
			* basePackage
			* antPath
			* title
			* description
			* licence
			* 
	* 在别的模块直接导入
		* SpringBootApplication
		* @Import(xx.class)  

		* @Import 改成注解配置
			* 自己写一个注解@EnableSwagger
			* 然后@Import
			* 别人用的时候,就使用该@EnableSwagger注解就好了
		
	* 别的模块,覆盖配置 prefix	

5. 工具
	1. swagger ui
		1. 给定url,显示swagger不同api文档
	2. swagger editor
		1. 编写接口,让别人实现
		2. 不同语言,生成mock接口(客户端,服务端)代码
	3. swagger codegen
		1. swagger editor生成代码一样
		

# 第五章  销售端#
----------
1. 介绍
	**功能**
		1. 查询
		2. 申购,赎回
		3. 对账
	**json rpc**
		1. 系统内部调用
		2. 不用http
		3. 比webservice xml 节省宽带

2. json rpc
	1. jsonrpc4j
		1. https://github.com/briandilley/jsonrpc4j