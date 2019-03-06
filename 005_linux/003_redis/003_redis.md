# 虚拟机 网络配置 #
1. 启动虚拟机
2. 菜单栏->编辑->编辑虚拟网络
	* 配置各自的 vmnet 0 1 8 的子网IP(网关)
	* 
3. 菜单栏->虚拟机->设置
	* 硬件->网络配置
		* 最好自定义,自己选定 vmnet*
4. 本机电脑 
	* 网络和共享中心
		* 更改适配器设置
		* 修改  vmnet 0 1 8 的 地址
------
1. 查看本机 vmnet ip,,,获得到 网段
2. 修改虚拟机ip,让两个在同一网段上	
	eth0 192.168.126.135






# 大纲 #
1. redis介绍
2. redis安装
3. redis操作
4. Jedis


# redis介绍 #
1. redis是一个nosql(not only sql)
2. 是一个非关系型数据库
	1. 关系型数据库:已二维表存储数据
	2. 非关系型数据库:已key value 形式
	3. 其他非关系型数据库
		1. MongoDB
		2. HBase
3. 意大利公司,c语言编写的开源,后vmware投资
4. 使用场景
	* 缓存(商品列表,列表信息)
	* 分布式集群框架中 session共享
		* 数据量大,并发量高
5. 数据存在内存中,存取数据块,所有广泛应用在互联网中
	* 速度(受限硬件):
		* 读 30万次/s
		* 写 10万次/s
	* 不能持久化,所以不能单独使用
		* 配合传统数据库 

# redis安装 #
1. 下载
	* [官网](https://redis.io/)
	* [其他](http://download.redis.io/releases/)
2. 源码安装
	* 上传到linux
	* 依赖(需要编译c代码)
		* gcc -v  测试是否有
		* yum install gcc-c++
	* 解压
		* tar -xvf redis-4.0.6.tar.gz
	* 编译(进入到解压目录,时间有点长)
		* make
	* 安装 
		* make install
			* 默认安装到了redis中src 文件件中
			make PREFIX=/root/redis/redis-install install
		* make PREFIX=/usr/local/redis install
			[root@ly2 redis-4.0.6]# make install
			cd src && make install
			make[1]: Entering directory `/root/redis/redis-4.0.6/src'
			    CC Makefile.dep
			make[1]: Leaving directory `/root/redis/redis-4.0.6/src'
			make[1]: Entering directory `/root/redis/redis-4.0.6/src'
			
			Hint: It's a good idea to run 'make test' ;)
			
			    INSTALL install
			    INSTALL install
			    INSTALL install
			    INSTALL install
			    INSTALL install
			make[1]: Leaving directory `/root/redis/redis-4.0.6/src'
	* 目录
		-rwxr-xr-x. 1 root root 5599574 12月  9 22:25 redis-benchmark	并发测试/性能测试
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-check-aof	测试aop
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-check-rdb	测试rdb
		-rwxr-xr-x. 1 root root 5738722 12月  9 22:25 redis-cli			客户端工具
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-sentinel	
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-server		服务端工具
		-rwxrwxr-x. 1 root root   60843 12月  5 01:01 redis-trib.rb
![](003_redis_dir.png)

4. 使用
	1. 前台启动
		* 服务器
			1. ./redis-server	
		* 客户端 
			1. 打开新端口
			2. ./redis-cli
			3. set key1 value1 
			4. get key1
			5. 区分大小写
	2. 后台启动
		1. redis.conf配置文件拷贝,和启动同一个文件夹中
		2. 修改配置文件
			* daemonize yes
		3. 启动
			 ./redis-server	 redis.conf

				[root@ly2 src]# ./redis-server  redis.conf 
				5633:C 09 Dec 23:51:40.391 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
				5633:C 09 Dec 23:51:40.392 # Redis version=4.0.6, bits=64, commit=00000000, modified=0, pid=5633, just started
				5633:C 09 Dec 23:51:40.392 # Configuration loaded
				[root@ly2 src]# 
		4. 查看
			ps aux | grep redis

	3. 关闭
		1. ./redis-cli shutdown


4. 源码redis.conf配置
	* 第92行,端口号,集群时候需要修改  
		port 6379
	* 第136行,是否可以在后台运行
		daemonize no   #不能再后台
	* 69
		bind 127.0.0.1
	* 88
		protected-mode yes 
Redis客户端下载链接：https://redisdesktop.com/download

----------
# Jeddis #
	6379端口放行 
		/sbin/iptables -I INPUT -p tcp --dport 6379 -j ACCEPT
	将该设置添加到防火墙的规则中
		/etc/rc.d/init.d/iptables save
1. 问题
	1. Caused by: java.net.ConnectException: Connection refused: connect
		1. bind 127.0.0.1 注释
	2. 拒绝redis在保护模式下运行的，没有绑定IP地址，没有授权密码
			redis.clients.jedis.exceptions.JedisDataException: DENIED Redis is running in protected mode because protected mode is enabled, no bind address was specified, no authentication password is requested to clients. 
			In this mode connections are only accepted from the loopback interface. If you want to connect from external computers to Redis you may adopt one of the following solutions:
				1) Just disable protected mode sending the command 'CONFIG SET protected-mode no' from the loopback interface by connecting to Redis from the same host the server is running, however MAKE SURE Redis is not publicly accessible from internet if you do so. Use CONFIG REWRITE to make this change permanent. 
				2) Alternatively you can just disable the protected mode by editing the Redis configuration file, and setting the protected mode option to 'no', and then restarting the server. 3) If you started the server manually just for testing, restart it with the '--protected-mode no' option. 4) Setup a bind address or an authentication password. NOTE: You only need to do one of the above things in order for the server to start accepting connections from the outside.
  		protected-mode yes  改成 no




----------
# 数据类型 #
1. 字符串String
	1. sds 二进制安全的
	2. 操作
		1. set
		2. get
		3. del
2. 列表list
	redis中使用的是双向循环链表来实现的list,在redis中更像栈
	1. 操作
		1. lpush list:1 1 2 3
		2. rpush list:1 4 5 6
		3. lrange list:1 0 2
			1. 3,2,1  
		4.  llen list:1 
3. 散列Hash
	1. 操作
		1. hset user age 18
		2. hget user age
		3. hmset
		4. hmget
		5. hgetall  
		6. hdel 
		7. hincrby user age 2 增加字段
	2. 区分 
		1.  hset user:1 age 18
		2.  HGET items:1 age
4. 集合set
	1. 无序,唯一,双向列表
	2. 操作
		1. sadd
		2. sdiff A B    	//差集
		3. sinter setA setB //交集
		4. sunion setA setB //并集
5. 有序集合zset 
	1. 无序,唯一,散列表
	2. 有序集合要比列表类型更耗内存
	3. 操作
		1. zadd key 分数 值 分数 值 
		2. zrange 
		3. zrange key 0 2  //排名前3

# 有效时间 #
1. EXPIRE key 5    //5s失效
2. ttl key
3. -1 永久
4. -2 已经删除


# 持久化 #
1. rdb
	1. 条件触发生成快照到磁盘
	2. 可能丢失部分数据
2. AOF(append only file)
	1. appendonly yes
	2. 每次操作都会保存

