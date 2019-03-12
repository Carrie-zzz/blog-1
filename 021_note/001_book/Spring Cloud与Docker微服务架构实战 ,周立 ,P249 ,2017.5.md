# 介绍 #
1. 单体应用
	1. 容易部署,测试
	2. 臃肿,维护性差,成本变高
2. 微服务架构模式
	1. 微服务架构风格是将一个单体应用程序,分割成一组小型服务的方法,每个服务单独运行,独立部署.服务之间采用轻量级同行机制(http),服务可以用不同语言,不同数据库技术
	2. 特点
		1. 每个服务独立运行在自己的进程
		2. 一组独立运行的微服务构建成整个系统
		3. 每个微服务为独立业务开发,一个微服务只关注特定的功能,入订单,用户
		4. 服务之间通过通信机制进行通信,restful api
		5. 可以使用不同语言,不同技术
		6. 全自动部署机制 
	3. 优点
		1. 易开发,易维护
		2. 技术不受限
		3. 按需伸缩,加节点
	4. 缺点
		1. 运维成本高
		2. 分布式复杂性: 系统容错,网络延迟,分布式事物
		3. 接口成本高
		4. 重复劳动
	5. 设计原则
		1. 单一职责 solid
			1. 
		2. 服务自治原则
			1. 每个微服务,不依赖别的服务
		3. 轻量级通信机制
			1. 轻量 + 跨平台
			2. rest amqp stomp mqtt
		4. 微服务粒度
			1. 划分规则很难
			 
3. spring cloud框架
	1. 特点
		1. 约定大于配置
		2. 大量注解,无xml配置
		3. 各个领域的组件比较多,功能齐全
			1. eureka,zuul,配置管理,服务管理,熔断器,网管
		4. 选型中立,,如服务发现支持,eureka zookeeper consul
	2. 版本
		1. 不同版本,基于不同spring boot构建
	


----------
1. 准备
	1. spring cloud 给予spring boot 构建的
2. 服务提供者,服务消费者

3. @SpringBootApplication
	1. 一个组合注解
		1. @SpringBootConfiguration
		2. @EnableAutoConfiguration 自动配置
		3. @ComponentScan	组件扫描
4. 配置文件
	1. yam
		1. 比properties 结果清晰,可读性,可维护性更强
	2. properties
		
4.  @Bean  RestTemplate 
	1.  getForObject("url",Class);

4. spring boot actuator  多监控端点
	1. status  up
	2. diskSpace
	3. db

