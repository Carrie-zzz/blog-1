# 架构细节 #
	(1)所有的redis节点彼此互联(PING-PONG机制),内部使用二进制协议优化传输速度和带宽.
	(2)节点的fail是通过集群中超过半数的节点检测失效时才生效.
	(3)客户端与redis节点直连,不需要中间proxy层.客户端不需要连接集群所有节点,连接集群中任何一个可用节点即可
	(4)redis-cluster把所有的物理节点映射到[0-16383]slot上,cluster 负责维护node<->slot<->value
		Redis 集群中内置了 16384 个哈希槽，当需要在 Redis 集群中放置一个 key-value 时，
		redis 先对 key 使用 crc16 算法算出一个结果，然后把结果对 16384 求余数，这样每个 key 都会对应一个编号在 0-16383 之间的哈希槽，
		redis 会根据节点数量大致均等的将哈希槽映射到不同的节点
# 搭建 #
[参考一](http://blog.csdn.net/aquester/article/details/50150163)

1. 细节
	1. 同一台电脑,端口必须修改	(redis.conf中 port )
	2. 不同电脑,端口可以相同,但是ip必须修改(redis.conf中)
2. 同一台电脑上(伪集群)
	1. mkdir redis_cluster
	2. 在文件夹中,准备一台redis	
		1. 如果dump.rdb存在,先删除
		2. 修改配置文件
			1. port	7001			//端口
			2. daemonize yes		//后端启动
			3. cluster-enabled  yes //开启集群  把注释#去掉  
	3. 复制5份,修改端口---从7001到7006
	4. 找到:在redis源码文件夹下的src目录下,ruby集群脚本 redis-trib.rb,
	5. 把redis-trib.rb文件复制到到redis-cluster目录下。
	6. 安装ruby环境
		* yum install ruby  -y
			 ruby --version
		* yum install rubygems  -y
		* 安装redis-trib.rb运行依赖的ruby的包
			* 下载 
				* https://rubygems.org/  不知道怎么找到下载1
				*  https://rubygems.org/gems/redis/versions/3.3.0  3.3.0 右边中文: 下载
				*  https://rubygems.org/gems/redis/versions/3.0.0
			 gem install redis-3.0.0.gem


	7. 启动所有的redis实例
		1. 错误脚本
			./redis1/redis-server	redis.conf
			./redis2/redis-server	redis.conf
			./redis3/redis-server	redis.conf
			./redis4/redis-server	redis.conf
			./redis5/redis-server	redis.conf
			./redis6/redis-server	redis.conf
			
				can't open config file 'redis.conf	
		2. ok (可能是editplus不行,必须加上;号,理论不需要加)

			cd redis1
			./redis-server redis.conf
			cd ..
			cd redis2
			./redis-server redis.conf
			cd ..
			cd redis3
			./redis-server redis.conf
			cd ..
			cd redis4
			./redis-server redis.conf
			cd ..
			cd redis5
			./redis-server redis.conf
			cd ..
			cd redis6
			./redis-server redis.conf
			cd ..


			ps -aux | grep redis
	8. 使用ruby脚,创建集群
		./redis-trib.rb create --replicas 1 192.168.126.137:7001 192.168.126.137:7002 192.168.126.137:7003 192.168.126.137:7004 192.168.126.137:7005  192.168.126.137:7006
		./redis-trib.rb create --replicas 1 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005  127.0.0.1:7006
		* 错误
			* 没安装ruby包
				/usr/lib/ruby/site_ruby/1.8/rubygems/custom_require.rb:31:in `gem_original_require': no such file to load -- redis (LoadError)
		        from /usr/lib/ruby/site_ruby/1.8/rubygems/custom_require.rb:31:in `require'
		        from ./redis-trib.rb:25
			*  将每个节点下aof、rdb、nodes.conf本地备份文件删除
				[ERR] Node 192.168.126.136:7001 is not empty. Either the node already knows other nodes (check with CLUSTER NODES) or contains some key in database 0.
	
				./redis1/redis-cli  -p 7001
				127.0.0.1:7001> flushdb  
				OK 
			* a
				[ERR] Sorry, can't connect to node 192.168.126.136:7001
		* 成功
				[root@os1708 redis_cluster]# ./redis-trib.rb create --replicas 1 192.168.126.136:7001 192.168.126.136:7002 192.168.126.136:7003 192.168.126.136:7004 92.168.126.136:7005  192.168.126.136:7006
				136:7005  192.168.126.136:7006
					>>> Creating cluster
					>>> Performing hash slots allocation on 6 nodes...
					Using 3 masters:
					192.168.126.136:7001
					192.168.126.136:7002
					192.168.126.136:7003
					Adding replica 192.168.126.136:7004 to 192.168.126.136:7001
					Adding replica 192.168.126.136:7005 to 192.168.126.136:7002
					Adding replica 192.168.126.136:7006 to 192.168.126.136:7003
					M: 1b597de1c2a046186b2b9700bb2145d95d16d5d3 192.168.126.136:7001
					   slots:0-5460 (5461 slots) master
					M: f1f9c77df23044ba8d94a21d398670d0f46fc84f 192.168.126.136:7002
					   slots:4184,5461-10922 (5463 slots) master
					M: b310a1b7bad52d8bc27bd0691622d8370909a63e 192.168.126.136:7003
					   slots:4184,10923-16383 (5462 slots) master
					S: 47d45df223132506085f55e19317750781089871 192.168.126.136:7004
					   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
					S: 404ff9e312ac415090e31db259bda8ceeb6a7d50 192.168.126.136:7005
					   replicates f1f9c77df23044ba8d94a21d398670d0f46fc84f
					S: 4c0582b39385ed041125b1ce8c3949a9f97aa631 192.168.126.136:7006
					   replicates b310a1b7bad52d8bc27bd0691622d8370909a63e
					Can I set the above configuration? (type 'yes' to accept):  !!!!!!一定要填 yes ,不能y或者其他
				
					>>> Nodes configuration updated
					>>> Assign a different config epoch to each node
					>>> Sending CLUSTER MEET messages to join the cluster
					Waiting for the cluster to join......
					>>> Performing Cluster Check (using node 192.168.126.137:7001)
					M: 5b5b2dee2f9c1d8ae3ff50249fa2255f12cd2b06 192.168.126.137:7001
					   slots:0-5460 (5461 slots) master
					   1 additional replica(s)
					S: 507e1050c0326052803a6e7f2946284753e99691 192.168.126.137:7005
					   slots: (0 slots) slave
					   replicates 57201c74bdfcea685631e2447c653ee1aa7d9aee
					S: 0f9196a0ea1a41d379eae888ad529fb0387c9e22 192.168.126.137:7006
					   slots: (0 slots) slave
					   replicates d0cc6bd0a7a39fc5cb29d40a2c5ac8d5792f5b20
					M: d0cc6bd0a7a39fc5cb29d40a2c5ac8d5792f5b20 192.168.126.137:7003
					   slots:10923-16383 (5461 slots) master
					   1 additional replica(s)
					S: 804dd8e4cbe11de5b04d35ab158e6ead7476ca9e 192.168.126.137:7004
					   slots: (0 slots) slave
					   replicates 5b5b2dee2f9c1d8ae3ff50249fa2255f12cd2b06
					M: 57201c74bdfcea685631e2447c653ee1aa7d9aee 192.168.126.137:7002
					   slots:5461-10922 (5462 slots) master
					   1 additional replica(s)
					[OK] All nodes agree about slots configuration.
					>>> Check for open slots...
					>>> Check slots coverage...
					[OK] All 16384 slots covered.

3. 测试
	* 连接人一台客户端
		./redis1/redis-cli  -p 7001
	* 设置值报错	
		127.0.0.1:7001> set key1 v1
			>>> Performing Cluster Check (using node 192.168.126.136:7001)
			M: 1b597de1c2a046186b2b9700bb2145d95d16d5d3 192.168.126.136:7001
			   slots:4184 (1 slots) master
			   5 additional replica(s)
			S: 4c0582b39385ed041125b1ce8c3949a9f97aa631 192.168.126.136:7006
			   slots: (0 slots) slave
			   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
			S: f1f9c77df23044ba8d94a21d398670d0f46fc84f 192.168.126.136:7002
			   slots: (0 slots) slave
			   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
			S: 404ff9e312ac415090e31db259bda8ceeb6a7d50 192.168.126.136:7005
			   slots: (0 slots) slave
			   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
			S: 47d45df223132506085f55e19317750781089871 192.168.126.136:7004
			   slots: (0 slots) slave
			   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
			S: b310a1b7bad52d8bc27bd0691622d8370909a63e 192.168.126.136:7003
			   slots: (0 slots) slave
			   replicates 1b597de1c2a046186b2b9700bb2145d95d16d5d3
			[OK] All nodes agree about slots configuration.
		1. http://www.cnblogs.com/guxiong/p/6266890.html 不好使
		
			./redis-trib.rb check 192.168.126.136:7001
			./redis1/redis-cli -c -p 7001 cluster meet 192.168.126.136 7002
		2. http://blog.csdn.net/vtopqx/article/details/50235891 不好使
			./redis-trib.rb check 192.168.126.136:7001
			 ./redis-trib.rb fix 192.168.126.136:7001	
			 ./redis-trib.rb reshard 192.168.126.136:7001
		3.  http://blog.csdn.net/u013820054/article/details/52180018	不好使	
			./redis-trib.rb check 192.168.126.136:7001
			 ./redis-trib.rb fix 192.168.126.136:7001	
			./redis1/redis-cli  -p 7002
			CLUSTER NODES  
			CLUSTER DELSLOTS 4184

	* 修改报错
		* The folowing uncovered slots have no keys across the cluster:
			http://blog.csdn.net/jaryle/article/details/51757874
			所以总是提示CLUSTERDOWN The cluster is down的错误，修改单机版redis配置文件，关闭cluster-enable后正常。

	* (error) CLUSTERDOWN Hash slot not served
			https://www.cnblogs.com/wolf-zt/p/6771710.html
			./redis1/redis-cli  -p 7001
			./redis1/redis-cli -c -p 7001 cluster meet 192.168.126.136 7002
			./redis1/redis-cli -c -p 7001 cluster meet 127.0.0.1 7002

			rm -rf ./redis1/dump.rdb
			rm -rf ./redis1/nodes.conf
			rm -rf ./redis2/dump.rdb
			rm -rf ./redis2/nodes.conf
			rm -rf ./redis3/dump.rdb
			rm -rf ./redis3/nodes.conf
			rm -rf ./redis4/dump.rdb
			rm -rf ./redis4/nodes.conf
			rm -rf ./redis5/dump.rdb
			rm -rf ./redis6/nodes.conf
			rm -rf ./redis7/dump.rdb
			rm -rf ./redis8/nodes.conf
	* 以上报错都是安装时候 ,没有yes,导致槽没有配置好!!!! 或者 使用的是3.30的gem ...应该是没有yes!
	* set值报错(预期)
			[root@ly3 redis]# ./redis1/redis-cli  -p 7001
			127.0.0.1:7001> set k1 vi
			(error) MOVED 12706 192.168.126.137:7003
		正解: -c
			[root@ly3 redis]# ./redis1/redis-cli  -p 7001 -c
			127.0.0.1:7001> set k1 v1
			-> Redirected to slot [12706] located at 192.168.126.137:7003
			OK
			
			-c（cluster）选项是连接Redis Cluster节点时需要使用的，-c选项可以防止moved和ask异常



----------

----------

----------
# java使用redis集群 #
1. jedis连接单机版和集群有区别
2. 集群测试
	1. 创建jedis集群对象(在系统中应该是单例),需要所有节点地址 
	2. 设置
	3. 获取
	4. 关闭
		@Test
		public void testCluster1() {
			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
			nodes.add(new HostAndPort("192.168.126.137", 7001));
			nodes.add(new HostAndPort("192.168.126.137", 7002));
			nodes.add(new HostAndPort("192.168.126.137", 7003));
			nodes.add(new HostAndPort("192.168.126.137", 7004));
			nodes.add(new HostAndPort("192.168.126.137", 7005));
			nodes.add(new HostAndPort("192.168.126.137", 7006));
			/** 第一步： 创建jedis集群对象(在系统中应该是单例),需要所有节点地址 **/
			JedisCluster jedisCluster = new JedisCluster(nodes); // 自带连接池
			/** 第二步： 设置 **/
			jedisCluster.set("k2", "v2中国");
			jedisCluster.set("k3", "111");
			/** 第三步： 获取 **/
			String string = jedisCluster.get("k1");
			String string2 = jedisCluster.get("k2");
			String string3 = jedisCluster.get("k3");
	
			System.out.println(string);
			System.out.println(string2);
			System.out.println(string3);
	
			/** 第四步： 最后系统关闭时候关闭 **/
			jedisCluster.close();
		}

	5. 错误
		1. redis.clients.jedis.exceptions.JedisConnectionException: no reachable node in cluster
			防火墙
				/sbin/iptables -I INPUT -p tcp --dport 7001 -j ACCEPT
				/sbin/iptables -I INPUT -p tcp --dport 7002 -j ACCEPT
				/sbin/iptables -I INPUT -p tcp --dport 7003 -j ACCEPT
				/sbin/iptables -I INPUT -p tcp --dport 7004 -j ACCEPT
				/sbin/iptables -I INPUT -p tcp --dport 7005 -j ACCEPT
				/sbin/iptables -I INPUT -p tcp --dport 7006 -j ACCEPT
		
		2. java.lang.NumberFormatException: For input string: "7002@17002"
			集群环境Redis使用的是4.0.1的版本
			报错之前使用的jedis版本为2.7.2
			之后版本替换为2.9.0完美解决问题


# 集群和spring整合 #
1. 为了支持单机和集群的redis,选择创建一个接口-2个实现类,使用接口,配置文件中注入实现类
	1. 一个接口
	2. 2个实现类

2. 接口
	public interface JedisClient {
			/**
			 * 
			 * @param key
			 * @param value
			 * @return OK
			 */
			public String set(String key, String value);
		
			public String get(String key);
		
			/**
			 * 
			 * @param key
			 * @param item
			 * @param value
			 * @return (integer) 1
			 */
			public Long hset(String key, String item, String value);
		
			public String hget(String key, String item);
		
			public Long incr(String key);
		
			public Long decr(String key);
		
			/**
			 * 过期时间
			 * 
			 * @param key
			 * @param second
			 * @return
			 */
			public Long expire(String key, int second);
			/**
			 * 
			 * @param key
			 * @return -1 永久 -2 过期没有
			 */
			public Long ttl(String key);
			//缓存同步使用
			long hdel(String hkey, String key);
	 }
3. 单机版实现类
		public class JedisClientSingle implements JedisClient {
		
			@Autowired
			private JedisPool jedisPool;
		
			@Override
			public String set(String key, String value) {
				Jedis jedis = jedisPool.getResource();
				String result = jedis.set(key, value);
				jedis.close();
				return result;
			}
		
			@Override
			public String get(String key) {
				Jedis jedis = jedisPool.getResource();
				String result = jedis.get(key);
				jedis.close();
				return result;
			}
		
			@Override
			public Long hset(String key, String item, String value) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.hset(key, item, value);
				jedis.close();
				return result;
			}
		
			@Override
			public String hget(String key, String item) {
				Jedis jedis = jedisPool.getResource();
				String result = jedis.hget(key, item);
				jedis.close();
				return result;
			}
		
			@Override
			public Long incr(String key) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.incr(key);
				jedis.close();
				return result;
			}
		
			@Override
			public Long decr(String key) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.decr(key);
				jedis.close();
				return result;
			}
		
			@Override
			public Long expire(String key, int second) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.expire(key, second);
				jedis.close();
				return result;
			}
		
			@Override
			public Long ttl(String key) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.ttl(key);
				jedis.close();
				return result;
			}
			
			@Override
			public long hdel(String hkey, String key) {
				Jedis jedis = jedisPool.getResource();
				Long result = jedis.hdel(hkey, key);
				jedis.close();
				return result;
			}
		}

4. 集群版
	public class JedisClientCluster implements JedisClient {
			@Autowired
			private JedisCluster jedisCluster;
		
			@Override
			public String set(String key, String value) {
				return jedisCluster.set(key, value);
			}
		
			@Override
			public String get(String key) {
				return jedisCluster.get(key);
			}
		
			@Override
			public Long hset(String key, String item, String value) {
				return jedisCluster.hset(key, item, value);
			}
		
			@Override
			public String hget(String key, String item) {
				return jedisCluster.hget(key, item);
			}
		
			@Override
			public Long incr(String key) {
				return jedisCluster.incr(key);
			}
		
			@Override
			public Long decr(String key) {
				return jedisCluster.decr(key);
			}
		
			@Override
			public Long expire(String key, int second) {
				return jedisCluster.expire(key, second);
			}
		
			@Override
			public Long ttl(String key) {
				return jedisCluster.ttl(key);
			}
			@Override
			public long hdel(String hkey, String key) {
		
				return jedisCluster.hdel(hkey, key);
			}
		}
5. 配置文件
	1. 单机
		<!-- 连接池配置 -->
		<bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
			<!-- 最大连接数 -->
			<property name="maxTotal" value="30" />
			<!-- 最大空闲连接数 -->
			<property name="maxIdle" value="10" />
			<!-- 每次释放连接的最大数目 -->
			<property name="numTestsPerEvictionRun" value="1024" />
			<!-- 释放连接的扫描间隔（毫秒） -->
			<property name="timeBetweenEvictionRunsMillis" value="30000" />
			<!-- 连接最小空闲时间 -->
			<property name="minEvictableIdleTimeMillis" value="1800000" />
			<!-- 连接空闲多久后释放, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放 -->
			<property name="softMinEvictableIdleTimeMillis" value="10000" />
			<!-- 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1 -->
			<property name="maxWaitMillis" value="1500" />
			<!-- 在获取连接的时候检查有效性, 默认false -->
			<property name="testOnBorrow" value="true" />
			<!-- 在空闲时检查有效性, 默认false -->
			<property name="testWhileIdle" value="true" />
			<!-- 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true -->
			<property name="blockWhenExhausted" value="false" />
		</bean>
		
		<!-- redis单机 通过连接池 -->
		<bean id="jedisPool" class="redis.clients.jedis.JedisPool" destroy-method="close">
			<constructor-arg name="poolConfig" ref="jedisPoolConfig"/>
			<constructor-arg name="host" value="192.168.126.137"/>
			<constructor-arg name="port" value="7001"/>
		</bean>


		<!-- 单机版实现类 -->
		<bean id="jedisClientSingle" class="com.huaxin.t.component.JedisClientSingle">
			<!-- 可以不用,配置pool,service开启了注解 扫描进去 -->		
		</bean>
	2. 测试
			@Test
			public void testInterface() throws IOException {
				ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
						"classpath:spring/*.xml");
				JedisClient bean = classPathXmlApplicationContext
						.getBean(JedisClient.class);
				String string = bean.get("key2");
				System.out.println(string);
			}
	3. 集群版
		<!-- 集群版  The fully qualified name of the bean's class, except if it serves only as a parent definition for child 
		 bean definitions.-->
			 <bean id="jedisCluster" class="redis.clients.jedis.JedisCluster">
			 	<constructor-arg>
					<set>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7001"/>
						</bean>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7002"/>
						</bean>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7003"/>
						</bean>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7004"/>
						</bean>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7005"/>
						</bean>
						<bean class="redis.clients.jedis.HostAndPort">
							<constructor-arg name="host" value="192.168.126.137"/>
							<constructor-arg name="port" value="7006"/>
						</bean>
					</set>
				</constructor-arg>
			 </bean>

			<bean id="jedisClientCluster" class="com.huaxin.t.component.JedisClientCluster" />
	4. 测试
			// 把点击注解,打开集群
			@Test
			public void testInterfaceCluster() throws IOException {
				ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
						"classpath:spring/*.xml");
				JedisClient bean = classPathXmlApplicationContext
						.getBean(JedisClient.class);
				String string = bean.get("key2");
				System.out.println(string);
				String hget = bean.hget("user", "name");
				System.out.println(hget);
			}
	5. 问题
		1. 为什么在xml中不需要给JedisClientCluster或者JedisClientSingle注入,而是让他自己扫描
			1. 因为在service.xml或者其他xml中 开启了扫描
			2. 扫描不仅让扫描包下面注入,而且让在配置文件中的bean也扫描注入

		2. 连接单机版的时候,为什么出现xx 7002 ,7003 等
			1. 用给set,get可能在别的redis中,但是单机只连接了7001,所有获取不到


----------
