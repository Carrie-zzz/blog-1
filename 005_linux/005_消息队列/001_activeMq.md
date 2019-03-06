1. mq 消息 队列(消息中间件)
	1. activeMq
	2. rabbitMq
	3. kafka
2. 减少服务与服务之间的调用,降低耦合度


----------

1. activeMq  
	1. ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。
	2. 下载http://activemq.apache.org/ -> download
		1. linux
		2. windows 
 	3. 安装
	 	1. jdk
	 	2. 上传,解压
	 	3. 目录
		 	1.  active-all.jar 不推荐 :内部spring版本过低
		 	2.  bin
			 	1. ./activemq start|stop|restart
		 	3.  conf
 		 4. 访问 http:xx:8161/admin  账号密码 admin
	 		 1.   503 linux 机器名没映射