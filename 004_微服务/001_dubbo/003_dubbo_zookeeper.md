# dubbo  服务中间见#
1. http://dubbo.io/


# dubbole 监控中心 #
1. 源码中dubbo-admin
2. 执行mvn install -Dmaven.test.skip=true
[参考](http://blog.csdn.net/zx13525079024/article/details/52260141)
3. 发布到tomcat下
4. 修改dubbo.properties
		dubbo.registry.address=zookeeper://192.168.126.134:2181
		dubbo.admin.root.password=root
		dubbo.admin.guest.password=guest
5. war发布到linux上有问题,但是window可以


----------

# zookeeper #
1. Apache开源(java开发)
2. 分布式提供协调服务的软件(分布式协调技术)
3. 提供Java和C的接口
4. 目标:封装复杂服务，提供简便系统给用户。

# 使用 #

1. 下载[download](http://zookeeper.apache.org)
	http://mirrors.shuosc.org/apache/zookeeper/
2. 解压 tar -xvf z
	bin
		zkServer.cmd	windows
		zkServer.sh		linux
	config
		zoo_sample.cfg
			dataDir
			clientPort 默认2181
3. 在解压目录下面(bin同一层),创建data文件夹
	mkdir data
4. 修改配置文件
	1. 在conf个中将zoo_sample.cfg改名 zoo.cfg
		mv zoo_sample.cfg zoo.cfg
	2. 修改zoo.cfg中dataDir路径
		dataDir=/tmp/zookeeper

		dataDir=/root/zookeeper/zookeeper-3.4.10/data
		
5. 开启
	./zkServer.sh	start
	./zkServer.sh	status
			Mode: standalone
			Error contacting service. It is probably not running.
 	./zkServer.sh {start|start-foreground|stop|restart|status|upgrade|print-cmd}



# 准备生成 #
1. 类
	1. TbItemMapper  
	2. TbItemService
	3. TbItemServiceImpl
2. jar包(service,web)
	1. dubbo
	2. zookeeper
	3. zkclient
	4. 去除 dubbo jar 下面 
		1. spring低版本 依赖传递(必须去除)
		2. netty低版本 依赖传递

			<!-- dubbo -->
			<dependency>
				<groupId>com.alibaba</groupId>
				<artifactId>dubbo</artifactId>
				<exclusions>
					<exclusion>
						<groupId>org.springframework</groupId>
						<artifactId>spring</artifactId>
					</exclusion>
					<exclusion>
						<groupId>org.jboss.netty</groupId>
						<artifactId>netty</artifactId>
					</exclusion>
				</exclusions>
			</dependency>
			<dependency>
				<groupId>org.apache.zookeeper</groupId>
				<artifactId>zookeeper</artifactId>
			</dependency>
			<dependency>
				<groupId>com.github.sgroschupf</groupId>
				<artifactId>zkclient</artifactId>
			</dependency>

# eclipse 关联 #
1. 传统service配置
	<!-- 开启扫描  service -->
	<context:component-scan base-package="com.huaxin.mybatis.service.impl" />
		或者
	<bean id="xxService" class="com.huaxin.xx.XxServiceImpl"></bean>

2. dubbo
	1. dubbo基于spring的schema约束扩展
			xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
			http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
			手动配置约束
	2. 发布(service层)
	3. 引入(web层)(引入可以注入)

## service层发布dubbo服务 ##
1. 添加jar
2. 配置service(同上)
	<!-- 开启@Service扫描 -->
	<context:component-scan base-package="com.huaxin.mall.service.impl"></context:component-scan>
3. 发布service
	xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
	手动配置约束

		<!-- 发布dubbo服务 
			1. 配置发布信息
			2. 发布
		-->
		<!-- 发布者app信息，用于计算依赖关系 -->
		<dubbo:application name="Hx-manager" />
		<!-- 注册中心的地址 -->
		<dubbo:registry protocol="zookeeper" address="192.168.126.134:2181" />
		<!-- 用dubbo协议在20880端口暴露服务 -->
		<dubbo:protocol name="dubbo" port="20880" />
		
		<!-- 声明需要暴露的服务接口 -->
		<dubbo:service interface="com.huaxin.mall.service.TbItemService" ref="tbItemServiceImpl" timeout="300000"/>
		

		@Service
		public class TbItemServiceImpl implements TbItemService {
			@Autowired
			TbItemMapper tbItemMapper;
			@Override
			public TbItem geTbItemByPrimaryKey(Long id) {
		
				return tbItemMapper.selectByPrimaryKey(id);
			}
		}

## 引入(web层)(引入可以注入) ##
1. 添加jar
2. 引入service
	xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
		手动配置约束
	<dubbo:reference interface="com.service.XxService" id="xxServiceImpl" />
3. 注入
	<!-- 引用dubbo服务 -->
	<!-- 接受者app信息，用于计算依赖关系 -->
	<dubbo:application name="Hx-manager-web"/>
	<!-- 注册中心的地址 -->
	<dubbo:registry protocol="zookeeper" address="192.168.126.134:2181" />	

	<dubbo:reference interface="com.huaxin.mall.service.TbItemService" id="tbItemService" />
	

		@Controller
		@RequestMapping("/item")
		public class TbItemController {
			@Autowired
			TbItemService tbItemService;
			@RequestMapping("/{id}")
			@ResponseBody
			public TbItem getItemById(@PathVariable Long id) {
				return tbItemService.geTbItemByPrimaryKey(id);
			}
		}

## 测试 ##
1. 406 error
	1. jackson包 (正常是通过 common依赖传递过来的)
	2. 注解驱动没有配置
2. error 配置log4j放在src/main/resources 下面
3. not find 
	1.  dependencies.dependency.dubbo.version ,先要安装parent项目
	2.  类 TbItem,安装pojo
	3.  TbItemService
4. service
	1. java.lang.ClassNotFoundException: org.springframework.web.context.
		1. web.xml配置文件件有问题
	2. java.net.ConnectException: Connection timed out: no further information
		1. zookeeper没有开启
		2. 2181 端口没有开放
				3306端口放行 
					/sbin/iptables -I INPUT -p tcp --dport 2181 -j ACCEPT
				将该设置添加到防火墙的规则中
					/etc/rc.d/init.d/iptables save
	3. 如果 not find  mapper statement(xxMapper.xml)
		1. xml没有编译发布到java代码中
		2. 在项目dao中pom文件里添加
				<build>
					<resources>
						<resource>
							<directory>src/main/java</directory>
							<includes>
								<include>**/*.xml</include>
							</includes>
						</resource>
						<!-- <resource>
							<directory>src/main/resources</directory>
							<includes>
								<include>**/*.xml</include>
							</includes>
						</resource> -->
					</resources>
				</build>	
4. web
	1. java.lang.IllegalStateException: Serialized class com.huaxin.mall.pojo.TbItem must implement java.io.Serializable
		pojo 除了 Example 的pojo都可能 网络传输