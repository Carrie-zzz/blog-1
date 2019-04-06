# redis 缓存 #
	https://baijiahao.baidu.com/s?id=1619572269435584821&wfr=spider&for=pc
	1. 流程
			* 参数传入对象主键ID
			* 根据key从缓存中获取对象
			* 如果对象不为空，直接返回
			* 如果对象为空，进行数据库查询
			* 如果从数据库查询出的对象不为空，则放入缓存（设定过期时间）

	1. redis 缓存穿透
		1. 缓存穿透，是指查询一个数据库一定不存在的数据
		2. 如果传入的参数为-1，会是怎么样？这个-1，就是一定不存在的对象,就会每次都去查询数据库，而每次查询都是空，每次又都不会进行缓存
			1. 假如有恶意攻击，就可以利用这个漏洞，对数据库造成压力，甚至压垮数据库。即便是采用UUID，也是很容易找到一个不存在的KEY，进行攻击。
		3. 解决方案
			1. 数据库查询的空值也会,放入缓存,设定的缓存过期时间较短，比如设置为60秒。
			2. 对一定不存在的key进行过滤。可以把所有的可能存在的key放到一个大的Bitmap中，查询时通过该bitmap过滤。（布隆表达式） 
	2. redis 缓存雪崩
		1. 缓存雪崩，是指在某一个时间段，缓存**集中**过期失效。
			1. 比如在写本文的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商品时间比较集中的放入了缓存，假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波峰。
		2. 解决方案
			1. 分散失效时间,不同的key，设置不同的过期时间，让缓存失效的时间点尽量均匀。
			2. 在缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待
			3. 做二级缓存，A1为原始缓存，A2为拷贝缓存，A1失效时，可以访问A2，A1缓存失效时间设置为短期，A2设置为长期（此点为补充）
	3. redis 缓存击穿
		1. 指一个key非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库，就像在一个屏障上凿开了一个洞
		2. 爆款
		3. 解决方案
			1. 对主打商品都是早早的做好了准备，让缓存永不过期
			2. 使用redis的setnx互斥锁先进行判断，这样其他线程就处于等待状态，保证不会有大并发操作去操作数据库。

# Redis消息模式 #
1. 队列模式	
	1. 使用list类型的lpush和rpop实现消息队列
	2. rpop 改成 brpop
		1. brpop命令，它如果从队列中取不出来数据，会一直阻塞，在一定范围内没有取出则返回null、
2. 发布订阅模式
	1. 订阅
		1. subscribe kkb-channel
	2. 发布
		1. publish kkb-channel “我是灭霸”

# 缓存淘汰 #
1. 最大缓存
	1. 在 redis 中，允许用户设置最大使用内存大小maxmemory，默认为0，没有指定最大缓存，如果有新的数据添加，超过最大内存，则会使redis崩溃
2. 淘汰策略
	1. 配置
		1. redis淘汰策略配置：maxmemory-policy voltile-lru，支持热配置
	2. 6种数据淘汰策略
		1.voltile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
		2.volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰
		3.volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
		4.allkeys-lru：从数据集（server.db[i].dict）中挑选最近最少使用的数据淘汰
		5.allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
		6.no-enviction（驱逐）：禁止驱逐数据
3. lru（Least recently used，最近最少使用）
	1. 算法根据数据的历史访问记录来进行淘汰数据，其核心思想是“如果数据最近被访问过，那么将来被访问的几率也更高”
	2. 最常见的实现是使用一个链表保存缓存数据，详细算法实现如下：
		1. 新数据插入到链表头部；
		2. 每当缓存命中（即缓存数据被访问），则将数据移到链表头部；
		3. 当链表满的时候，将链表尾部的数据丢弃。



# 分布式锁 #
1. 多个应用进程之间的锁,不是多个线程之间的锁
2. 特性
	* 互斥性：在任意时刻，只有一个客户端能持有锁
	* 同一性：加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
	* 可重入性：即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。

1. 方法一 set 实现

```
	/**
	 * 使用redis的set命令实现获取分布式锁
	 * @param lockKey   	可以就是锁
	 * @param requestId		请求ID，保证同一性
	 * @param expireTime	过期时间，避免死锁
	 * @return
	 */
	public static boolean getLock(String lockKey,String requestId,int expireTime) {
		//NX:保证互斥性
		String result = jedis.set(lockKey, requestId, "NX", "EX", expireTime);
		if("OK".equals(result)) {
			return true;
		}
		
		return false;
	}

```
2. 方法二 使用setnx命令实现
	1.  setnx key value  ,仅当不存在时赋值
		redis> EXISTS job                # job 不存在
		(integer) 0
		redis> SETNX job "programmer"    # job 设置成功
		(integer) 1
		redis> SETNX job "code-farmer"   # 尝试覆盖 job ，失败
		(integer) 0
		redis> GET job                   # 没有被覆盖
		"programmer"
```
	public static boolean getLock(String lockKey,String requestId,int expireTime) {
		Long result = jedis.setnx(lockKey, requestId);
		if(result == 1) {
			jedis.expire(lockKey, expireTime);
			return true;
		}
		
		return false;
	}
```

1. 释放锁
```
	/**
	 * 释放分布式锁
	 * @param lockKey
	 * @param requestId
	 */
	public static void releaseLock(String lockKey,String requestId) {
	    if (requestId.equals(jedis.get(lockKey))) {
	        jedis.del(lockKey);
	    }
	}
```
2. 方式2（redis+lua脚本实现）--推荐

```
	public static boolean releaseLock(String lockKey, String requestId) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

		if (result.equals(1L)) {
			return true;
		}
		return false;
	}
```