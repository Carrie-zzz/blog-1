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
		1. 系统内部交互
		2. 不用http,直接使用接口
		3. webservice,也是面向接口,数据已xml传输,浪费宽带

2. json rpc
	1. jsonrpc4j
		1. https://github.com/briandilley/jsonrpc4j

3. 实现
	1. 提供方 server
		2. 添加依赖
		3. 添加接口, 注解 
			1. 接口注解	@JsonRpcService("/products")
			2. 实现类
				1. 注解	@JsonRpcServiceImpl   
				2. @Service //spring注解
				3. 实现 接口 
		4. 编写实现类
		5. 添加配置类
			1. @Bean  
			2. new AutoJsonRpcServiceImplExporter()
	2. 消费	cleint
		1. 添加接口依赖依赖
		2. 注入 rpc 接口
		3. 配置文件 application.yl
			1. rpc.manager.url: http://xx:808/manger/
		4. 添加配置类
			1. @Bean
			2. new AutoJsonRpcClientProxyCreator()
			3. 设置配置的url
				1. cretor.setBaseUrl(url)
			4. 指定扫描包
				1. creator.setScanPackage(X.class.getPackage().getName());
4. 问题
	1. 客户端,服务端编写配置类的时候,@Bean不能缺少!	
	2. 客户端,添加接口的时候,接口可能不被扫描(不在springboot默认扫描包和子包内)
		1. 在配置类上手动添加扫描
			1. @ComponentScan(basePackageClasses={X.class})
	3. rpc 客户端配置url的 时候,需要 / 结尾	
		1.  http://xx:808/manger/	正确的
	4. 服务端  @JsonRpcService  不能已 / 开头
		1. @JsonRpcService("/products")  错误的~~
	5. 请求参数 不能有 复杂类的引用....
		1. Class A{  Classb ..}  不行
	6. 响应结果 不能有 复杂类的引用....

5. 运行原理
	1. 	运行原理
	2. 	路径,参数限制
	3. 	简化 ,封装

	1. 客户端
		1. debu日志  logging.lever:packag=debug
		2. AutoJsonRpcClientProxyCreator
	2. 路径问题
		1. new Url(baseUrl,path).toString();
		2. 必须要求 baseUrl / 结尾	
		3. 必须要求 path 不能 / 开头
	3. 服务端
		1. AutoJsonRpcServiceImplExporter
			
7. JSONRPC 修改源码封装
	1. 默认使用类名做path地址
		1. 修改注解 JsonRpcService default
		2. 修改 AutoJsonRpcServiceImplExporter
			1. paths.add(jsonRpcServiceAnnotation.value); // 修改
			2. 
		3. AutoJsonRpcClientProxyCreator
			1. String path= ...
	2. 解决路径 参数限制
		1. AutoJsonRpcClientProxyCreator
			1. appendBasePath(String path) 方法
				1. return new URL(baseUrl,path).toString();
			2. 处理一下,就好了 
				1. 判断 baseUrl /结尾? 不是就加上
				2. path  /开头 , 是就去除
		2. 
	3. 接口参数 反序列化, 无法实例化
		1. jackson 反序列化
		2. 在接口上添加 ,一个注解 @JsonDeserialize(as=Xx.Class)

6. 自动配置
	1. 在util模块
	2. 新建一个 @Configuration  JsonRpcConfiguration 类
	3. 服务器配置  @Bean AutoJsonRpcServiceImplExporter
	4. 客户端配置  @Bean AutoJsonRpcClientProxyCreator
		1. 客户端,服务端互斥
		2. 在配置,属性后加载
			1. @ConditionOnProperty(value={"rpc.client.url","rpc.client.basePackage"})
	5. 提取配置
		1.  @ConfigurationProperty (prefix="rpc")
		2.  JsonRpcConfigurationProperty
	6. 将配置类 放入 Meta-Info/spring.factories
		1. EnableAutoConfiguration
		


# 第六章 hazelcast #
----------
1. 介绍
1. 缓存作用
	1. 请求平凡,变更少
	2. 减少数据库压力
	3. 提高响应速度
2. 缓存
	1. memcache		最早结构单一 k/v
	2. redis		数据结构多,持久化
	3. hazelcast	
		1. 数据结果更多,功能更多
		2. 集群方便,管理界面更好
		3. spring整合更方便
3. hazelcast
	1. [官网](https://hazelcast.org/)
	2. 开源版 ,企业版(收费)
	3. 特点 
		* 271个分区	
		* 分布式数据结果 map queue 
2. 安装
	1. 依赖包嵌入(当前使用)  / server client 区分
	2. 添加依赖
	3. 管理中心

	1. 下载
		1. mavne依赖
		2. 管理中心 Management Center
	2. 拷贝jar默认配置文件,到resources下 hazelcast.xml
	3. 修改配置(基本不用改)
		1. group 名 密码
		2. manager-center  url  管理中心地址
		3. port auto-increment
		4.  广播集群
			1.  multicast  enable=true/false 启用
			2.  tcp-ip	enable=true/false 启用
	4. 管理中心
		1. window 直接点击  startManCenter.bat [port] [path]
		2. 默认  8080  mancenter
		3. 第一次配置 账户密码
		4. 连接
		5. 
3. 使用
	1. 注入 @Autowired HazelcastInstance hazelcastInstance;
	2. hazelcastInstance
		1. Map map = hazelcastInstance.getMap("x");

4. spring 缓存
	1. 添加依赖
	2. 配置
	3. @Cachable 开启缓存 @CachePut 放入缓存  @CacheEvict 清除缓存

	1. 添加依赖
		1. hazelcast-spring
	2. 开启注解
		1. 在springbootapplication 下 @EnableCaching
	3. 配置文件  
		1. spring.cache.type=hazelcast
	4. 在方法上 添加注解
		1. @Cacheable(cacheNames="xxx_product")
		2. 默认Map<请求参数 , 返回值 >
	5. 返回值 需要 实现 序列化 接口
			
			* @CachePut(cacheNames="xxx_product",key="#product.id")
			* @CacheEvict(cacheNames="xxx_product")  参数id作为 key
			
5. 缓存优化
	1. 系统启动就加载
		1. 实现 ApplicationListener 接口 监听启动
		2. 在事件里面 编写 查询所有数据,放入缓存中 
	2. findAll也是用缓存		
		1. 	先获取 Map map = hazelcastInstance.getMap("x");
		2. 	遍历Map  放入list中
		3. 	如果 map没有值,就去查找数据库

	3. 问题
		1. 同一个类中调用 缓存方法是,不适用缓存的,和spring原理一样,不是代理类没有方法增强

	4. 注解
		1. @Cachable 
			1. 
			1. value等价cacheNames
			2. condition 满足条件放入缓存 ,支持spring el 表达式
			3. key  : map中的key  
				1. 默认策略
					1. 如果方法 没有参数 , key =0
					2. 如果只有一个参数 , key  = 参数
					3. 如果只多于一个	, 使用所有参数的  key=hashCode 
				2. 自定义
					1. #参数名		#dto.id
					2. #p参数index	#p0.id
		2. CachePut
			1. 每次调用都会执行
		3. CacheEvict
			1. adllEnties  清空所有缓存
			2. beforeInvocation 清空前执行

6. 缓存维护
	1. 消息系统
		1. topic	广播
		2. queue	点对点
		3. 消费者分组
	2. 框架
		1. Hazelcast  不满足,spring整合不好
		2. kafka
		3. activemq

	3. 使用active
		1. 添加依赖
		2. 安装
			1. 账号密码默认 admin
		3. virtual topics
			1. 
		4. 使用
			1. 注入 JmsTemplate 
				1. template.convertAndSend(obj) ; // 发送消息
			2. 配置mq地址
				1. spring.activemq.broker-url=tcp://0.0.0.0:61616
				2. spring.jms.pub-sub-domain=true
				3. spring.activemq.package.trust-all=true
			3. 2
		5. 接受消息
			1. 方法添加注解
			2. @JmsListener(destination="Consumer.分组名.VirtualTopic.PRODUCT_STATUS")
		6. 




# 第七章 RSA 对账  jpa多数据源 #
----------
1. 安全介绍
	1. https
	2. rsa签名
		1. 介绍
			1. alice bob  公钥交换
			2. 公钥加密,私钥解密		密文,消耗性能
			3. 私钥签名,公钥验签		明文,是否认可
		2. 开发
			1. 依赖
				1. base64	commons-codec
			2. 添加工具类
		3. 延签时机
			1. 拦截器 
				1. post请求, 请求体 io流,拦截器读取了 controller可能读取不到了
			2. aop 
		4. 参数
			1. 在请求头中 添加 token , sign
			2. 在aop中通过token , 获取对应的 公钥,进行延签
2. 下单功能
	1. 日期jackson 注解 
	2. @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
3. 下单验签
	1. controller方法 请求头获取参数, @RequestHeader()
	2. 签名方法
		1. 所有dto(controller参数),实现 自定义接口 SignTest
		2. 接口上添加	 
			1.  @JsonInclude(JsonInclude.Include.NON_NULL) 
			2.  @JsonPropertyOrder(alphabetic= true)   		
		3. 定义 默认实现方法    
		4. default String toText
```
@JsonInclude(JsonInclude.Include.NON_NULL)  //包含空
@JsonPropertyOrder(alphabetic= true)  // 字典排序
public interface SignText{
	default String toText(){
		return JsonUtil.toJson(this);
	}

}
```
	3. aop
		1.  类上添加 @Aspect
		2.  前置通知 @Before 表达式
			1.  @Before(value="execution(* com.xx.sell.controller.*.*(..)) && args(token,sign,text,..)")
		3. 编写类 通过token,获取公钥, 注入到aop类中
		4. 验签

4. 对账
	1. 资金流
		1. 用户A
		2. 套壳公司
		3. 银行 (用户A,套壳公司,金融公司账户)
			1. 套壳公司,金融公司账户 都要受银行监管
		4. 金融公司
	2. 名词
		2. 对账
			1. 确保资金一致
			2. 套壳公司对账
				1. 账户系统 和 银行对账
				2. 账户系统 和 业务系统对账
				3. 业务系统 和 金融公司对账
		3. 轧差
			1. 
		4. 平账
			1. 处理差异,使对账两边 重新平衡
		5. 长款
			1. 金融公司有记入,套壳公司没有
			2. 创建时间差 相隔一天 产生的
			3. 已某一方时间为准
		6. 漏单
			1. 
	3. 对账流程
		1. 获取对账文件

5. 5
6. 1
7. 1
8. 




















