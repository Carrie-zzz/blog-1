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



指令大全 http://www.redis.cn/commands.html


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
			[root@ly2 redis-4.0.6]# make install		//文件夹不存在没关系
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
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-check-aof	测试aop,修复aop文件
				redis-check-aof --fix readonly.aof
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-check-rdb	测试rdb
		-rwxr-xr-x. 1 root root 5738722 12月  9 22:25 redis-cli			客户端工具
		-rwxr-xr-x. 1 root root 8314593 12月  9 22:25 redis-sentinel		启动哨兵服务工具
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
		1. 安装包中 redis.conf配置文件拷贝,和安装目录的启动同一个文件夹中
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
			ps -aux | grep redis
			ps -ef | grep redis

	3. 关闭
		1. ./redis-cli shutdown
		2. kill -9 1234


4. 源码redis.conf配置
	* 第92行,端口号,集群时候需要修改  
		port 6379
	* 第136行,是否可以在后台运行
		daemonize no   #不能再后台  , 请 改为yes
	* 69
		bind 127.0.0.1   #绑定的IP才能访问redis服务器 , 注释掉 或者 改成 0.0.0.0
	* 88
		protected-mode yes 	#是否开启保护模式，由yes该为no
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
		4. incr key			// key为 整型的时候
		5. incrby key num步长
		6. decr key
		7. setnx key value		//不存在才设置
		8. append key value	//追加
		9. strlen  key				// 字符串长度
		10. mset k1 v1 k2 v2 k3 v3	//  获取多个值
	2. 使用场景
		1. 商品编号、订单号采用INCR命令生成
			1. incr items:id
		2. 短信验证码

2. 列表list
	redis中使用的是双向循环链表来实现的list,在redis中更像栈
	1. 操作
		1. lpush list:1 1 2 3
		2. rpush list:1 4 5 6
		3. lrange key start stop
			1. lrange list:1 0 2
				 3,2,1  
			2. stop “-1”代表最后边的一个元素。
		4.  llen list:1 
			1.  获取列表中元素的个数
		5.  弹出元素
			1. LPOP key
				1.  lpop list:1 
					1.  3
			2. RPOP key
		6. LINDEX key index		获得指定索引的元素值
		7. LSET key index value		设置指定索引的元素值
	2. 应用
		1. 商品评论列表
		2. LPUSH items:comment:1001 '{"id":1,"name":"商品不错，很好！！","date":1430295077289}
3. 散列Hash
	1. 操作 // value 只能是 string/数值 类型
		1. hset user age 18  
			1. 不区分插入和更新操作，当执行插入操作时HSET命令返回1，当执行更新操作时返回0
			2. 一次只能设置一个字段值
		2. hget user age
		3. hmset
			1. 一次可以设置多个字段值
			2. hmset user:1 age 18 name zhangsan
		4. hmget
			1. 一次可以获取多个字段值
			2. hmget user:1 age name
		5. hgetall  
			1. 获取所有字段值
			2. hgetall user:1
		6. hdel 
			1. 可以删除一个或多个字段，返回值是被删除的字段个数 
			2. hdel user:1 age
			3. hdel user:1 age name
		7. hincrby key field increment   // 增加数字
			1. hincrby user:1 age 2
		8. hsetnx key field value
			1. 类似HSET,如果字段存在，该命令不执行任何操作
	2. 区分 
		1.  hset user:1 age 18
		2.  HGET items:1 age
4. 集合set
	1. 无序,不重复,唯一,双向列表
	2. 操作
		1. sadd	key v1 v2
		2. sdiff A B    	//差集
		3. sinter setA setB //交集
		4. sunion setA setB //并集
5. 有序集合zset 
	1. 无序,唯一,散列表
	2. 有序集合要比列表类型更耗内存
	3. 操作
		1. zadd key 分数 值 分数 值 
		2. zrange  key start stop [WITHSCORES]
		3. zrange key 0 2  //排名前3  从小到大的
		4. ZREVRANGE key start stop [WITHSCORES]		// 从大到小
		5. ZSCORE key member   // 4.5.2.3获取元素的分数 
	5. 应用
		1. 商品销售排行榜
			1. ZADD items:sellsort 件数 商品id 件数 商品id
# 有效时间 #
1. EXPIRE key seconds    //seconds失效
2. ttl key
	1. -1 永久
	2. -2 已经删除
3. 

# 持久化 #
1. rdb
	1. 条件触发生成快照到磁盘
		1. 自定义配置规则 ,可以多个配置条件(每个条件之间是“或”的关系)
			1. save 900 1	// 900秒/15分钟 1个key被修改
		2. 执行save或者bgsave命令
		3. 执行flushall命令
		4. 执行主从复制操作
	2. 可能丢失部分数据
	3. 流程
		1.redis使用fork函数复制一份当前进程的副本(子进程)
		2.父进程继续接收并处理客户端发来的命令，而子进程开始将内存中的数据写入硬盘中的临时文件。
		当子进程写入完所有数据后会用该临时文件替换旧的RDB文件，至此，一次快照操作完成。 

		1.redis在进行快照的过程中不会修改RDB文件，只有快照结束后才会将旧的文件替换成新的，也就是说任何时候RDB文件都是完整的。 
		2.这就使得我们可以通过定时备份RDB文件来实现redis数据库的备份， RDB文件是经过压缩的二进制文件，占用的空间会小于内存中的数据，更加利于传输。

2. AOF(append only file)
	1. 默认情况下Redis没有开启AOF 
		1. appendonly yes		//开启
		2. auto-aof-rewrite-percentage 100		//	
			1. 表示当前aof文件大小超过上一次aof文件大小的百分之多少的时候会进行重写。如果之前没有重写过，以启动时aof文件大小为准
		3.  auto-aof-rewrite-min-size 64mb		//	
			1.  限制允许重写最小aof文件大小，也就是文件大小小于64mb的时候，不需要进行优化
		4. appendfsync always
			1. 每次执行写入都会进行同步  ， 这个是最安全但是是效率比较低的方式
		5. appendfsync everysec
			1. 每一秒执行
		6. appendfsync no
			1. 不主动进行同步操作，由操作系统去执行，这个是最快但是最不安全的方式

	2. 每次操作都会保存,Redis就会将该命令写入硬盘中的AOF文件，这一过程显然会降低Redis的性能,但大部分情况下这个影响是能够接受的，另外使用较快的硬盘可以提高AOF的性能
	3. 流程
		Redis 可以在 AOF 文件体积变得过大时，自动地在后台对 AOF 进行重写
		重写后的新 AOF 文件包含了恢复当前数据集所需的最小命令集合。 
		整个重写操作是绝对安全的，因为 Redis 在创建新 AOF 文件的过程中，会继续将命令追加到现有的 AOF 文件里面，即使重写过程中发生停机，现有的 AOF 文件也不会丢失。 而一旦新 AOF 文件创建完毕，Redis 就会从旧 AOF 文件切换到新 AOF 文件，并开始对新 AOF 文件进行追加操作。
		AOF 文件有序地保存了对数据库执行的所有写入操作， 这些写入操作以 Redis 协议的格式保存， 因此 AOF 文件的内容非常容易被人读懂， 对文件进行分析（parse）也很轻松

3. 选择
	1. 一般来说,如果对数据的安全性要求非常高的话，应该同时使用两种持久化功能。
	1. 如果可以承受数分钟以内的数据丢失，那么可以只使用 RDB 持久化。
	1. 有很多用户都只使用 AOF 持久化， 但并不推荐这种方式： 因为定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 **RDB 恢复数据集的速度也要比 AOF 恢复的速度要快** 。
	1. 两种持久化策略可以同时使用，也可以使用其中一种。如果同时使用的话， 那么Redis重启时，会优先**使用AOF文件**来还原数据



