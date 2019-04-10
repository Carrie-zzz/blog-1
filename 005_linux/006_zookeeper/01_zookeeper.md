# zookeeper 介绍 #
1. Apache开源(java开发)
2. 分布式提供协调服务的软件(分布式协调技术)
	1. 提供一致性服务(通过 Paxos算法的ZAB协议完成的)
3. 提供Java和C的接口
4. 目标:封装复杂服务，提供简便系统给用户。
5. 主要功能
	1. 配置维护
	2. 域名服务
	3. 分布式同步
	4. 集群管理
		ZooKeeper is a centralized service for 
			* maintaining configuration information,
			* naming, 
			* providing distributed synchronization, 
			* and providing group services. 


# 功能简介 #
1. 配置维护  maintaining configuration information
	1. 在集群系统中,一个订单可能部署几十个节点,他们配置是一样的,如果配置需要修改,这就比较麻烦了
	2. zookeeper提供了 发布-订阅模型
		1. 发布者   将修改的配置文件发布到zookeeper的**文件系统**中,
		2. 订阅者马上就可以接受到通知,并主动去同步zookeeper最新配置文件
		3. zookeeper有同步的原子性,确保每个集群节点的配置文件都能被正确的更新

2. 域名服务 naming







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