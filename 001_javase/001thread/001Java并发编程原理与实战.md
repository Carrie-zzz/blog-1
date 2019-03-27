# 大纲 #

1. 锁
	1. 排他性
	2. 阻塞性
	3. 可重入性

分布式锁	
https://www.cnblogs.com/seesun2012/p/9214653.html

----------

----------

----------
# 内容 #
05. 线程状态
	* 初始化
		* 
	* 就绪 runnable
		* stop()->dead
	* 运行 running
		* sleep()->sleeping
		* wait()->waiting
		* blocks for io, enter synchronized code -> blocked
	* 死亡 dead

06 线程创建
1. 继承Thread
2. 实现Runnable接口
3. 匿名内部类
4. 带返回值的线程
	1. 实现Callable<T> 接口
	 		T call()
	2. task = new  FutureTask<T>  ( Callable ) 
	3. t = Thread(FutureTask);  t.start();
	4. T result = task.get();
5. 定时器Timer  TimerTask impl Runnable   , quartz
	Timer timer =  new Timer();
	timer.schedule(timerTask,delay,period);
	abstract TimerTask implements Runnable

6. 线程池
	Executor threadPool = Executors.
				newCachedThreadPool		自动回收,扩容线程池	
				newFiexedThreadPool		固定容量
	threadPool.execute(runnable);
	threadPool.shutdown();
7. Lambda 

8. spring
	@EnableAsync   @Async 注解  

11. 线程问题
	1. 线程安全性问题
		1. 产生条件
			1. 多个线程
			2. 同一个资源
			3. 资源读写操作(非原子操作,一条字节码以上)
				* 多个线程,读写同一个变量( i 自动增长案例)
				* return value++;
		2. 解决
			1. 添加 synchronized,(串行,新能不好)
			2. 锁lock 读写锁,condition
			3. xkey
			4. atomic
	2. 活跃性问题
		1.  死锁: 一人一根筷子 (jconsole 检测死锁 工具,cmd打开)
		2.  饥饿: 线程优先级,有的人饿死
			* 高优先级抢占低优先级的cpu时间片(设置合理优先级)
			* 同步代码块,低优先级永久阻塞在等待进入代码块的状态(永久等待)(使用锁代替,synchronized)
			* 等待wait线程,永远不会唤醒

			* 设置合理优先级
			* 使用锁代替 synchronized
		3.  活锁: 过独木桥互相谦让
	3. 性能问题
		1. 多线程并不一定快,不一定多核
		2. 线程切换,切换上下文(浪费cpu资源),相当于翻译

13.  查看**字节码**
	1.  javap -verbose xx.class 
	2.  类实例放在堆中,线程共享
	3.  程序计数器线程独享
	
	1. return value++ 
		1. 线程读取内存value值
		2. 线程缓存++
		3. 更新内存value值


		1. 多个线程
		2. 同一个资源
		3. 资源读写操作(非原子操作,一条字节码以上)


14. synchronized原理
	1. 内置锁:java每一个对象都可以用作同步锁,这样的锁称之为内置锁
	2. 互斥锁:一个线程进去,另外线程不能进去
	3. 修饰
		1. 修饰普通方法:锁是当前类的对象(实例)
		2. 修饰静态方法:锁是当前类的class字节码对象
		3. 代码块

15. synchronized深层次原理
	1. 字节码
		1. 进入: monitorEnter
		2. synchronized
		2. 出去: monitorExit
	2. javap xx.class 查看字节码
	3. 对象头(存放锁信息)
		1. 每个对象的锁信息,存在对象头中
		2. 头信息
			1. markWord : 
				1. hash值,
				2. 锁信息,
					1. 线程id,
					2. epoch
					3. 对象的分代年龄
					4. 是否是偏向锁
					5. 锁标志位
			2. class metadata address
				1. 对象的类型 指向地址
			3. array length(数组独有)
	4.类型
		1. 偏向锁(单个线程,一直把持锁)
			1. 每次获取和释放会浪费资源,
			2. 很多情况下,竞争锁不是由于多个线程,而是由一个线程在使用
			3. markword记录
				1. 线程id
				2. epoch
				3. 对象的分代年龄
				4. 是否是偏向锁
				5. 锁标志位
			4.  过程
				1. 当线程第一次进入,偏向锁记录线程id,获取锁,线程出去不释放锁
				2. 当线程再次进入,检查线程id,如果一致就不用去获取,线程不用获取,释放了
				3. 偏向锁等待其他竞争线程出现,获取锁对象的线程才会释放线程
		2. 轻量级锁
			1. 多个线程,同时能获取锁(提前获取markword信息)
				2. 执行同步代码之前,jvm会在当前线程的栈堆中创建存储锁记录的空间
				1. 虚拟机栈(线程独有的),存储栈针,每个方法执行都会创建栈针,存储方法执行信息,方法进栈出栈
				3. 对象头中的markword复制到锁记录空间中,开始竞争锁,
				4. 竞争成功,修改 锁标志位  轻量级锁,然后开始执行同步代码,
				5. 其他线程修改锁标识位失败,自旋重复执行,会升级成重量级锁
				6. 第一个线程执行完毕,释放锁,唤醒第二个线程执行
		3. 重量级锁
			1. 一个1.6之前的synchronized,一个线程进去,其他等待

16. 单例和线程安全问题
	1. 恶汉:直接new(在多线程下,不满足:共享资源非原子性操作),
		1. 没有线程安全问题,
		2. 不管用不用,加载的时候就实例化,堆内存开辟空间,内存,浪费
		3. 电脑程序,用的时候在打开
	2. 懒汉:使用时候new,有线程安全问题(非原子操作)
					if(instance == null) {  //读
						instance = new Singleton2();  // 写
					}
		1. 同步锁加载方法上面,其他线程多自旋(相当于while true 消耗资源)等待
		2. if sync if 判断,但是虚拟机可能 指令重排序(添加关键字 volatile),导致问题 ???
			1. 先执行1-3-2
					public class Singleton2 {
						private Singleton2() {}
						private static volatile Singleton2 instance;
						/**
						 * 双重检查加锁
						 * 
						 * @return
						 */
						public static Singleton2 getInstance () {
							// 自旋   while(true)
							if(instance == null) {			// 避免 每次加锁 , 效率问题
								synchronized (Singleton2.class) {
									if(instance == null) {	// 避免 外面syn等待的线程,唤醒后继续创建
										instance = new Singleton2();  // 指令重排序
										// 申请一块内存空间   // 1
										// 在这块空间里实例化对象  // 2
										// instance的引用指向这块空间地址   // 3 instance!=null
									}
								}
							}
							return instance;
						}
						// 多线程的环境下
						// 必须有共享资源
						// 对资源进行非原子性操作
					}

	3. 构造私有
	4. 提供获取当前对象方法  getInstance


```
在IoDH中，我们在单例类中增加一个静态(static)内部类，在该内部类中创建单例对象，再将该单例对象通过getInstance()方法返回给外部使用
class Singleton {  
    private Singleton() {  
    }  

    private static class HolderClass {  
            private final static Singleton instance = new Singleton();  
    }  

    public static Singleton getInstance() {  
        return HolderClass.instance;  
    }  

    public static void main(String args[]) {  
        Singleton s1, s2;   
            s1 = Singleton.getInstance();  
        s2 = Singleton.getInstance();  
        System.out.println(s1==s2);  
    }  
}

由于静态单例对象没有作为Singleton的成员变量直接实例化，因此类加载时不会实例化Singleton，
第一次调用getInstance()时将加载内部类HolderClass，在该内部类中定义了一个static类型的变量instance，
此时会首先初始化这个成员变量，由Java虚拟机来保证其线程安全性，确保该成员变量只能初始化一次。
由于getInstance()方法没有任何线程锁定，因此其性能不会造成任何影响

通过使用IoDH，我们既可以实现延迟加载，又可以保证线程安全，不影响系统性能
```
17. 锁
	1. 非重入锁
		1. 只允许一个线程进入
	2. 重入锁:一个线程获取当前锁后,没有释放前又遇到当前锁,可以直接进入,synchronized  locak
			public synchronized void a () {
				System.out.println("a");
			}
			public synchronized void b() {
				System.out.println("b");
			}

	3. 自旋锁:空转cpu,等待执行,避免线程切换
		* 所有线程执行完毕后打印ok
			* 主线程自旋等待

				while(Thread.activeCount() != 1) {
				// 自旋
				}
				System.out.println("所有的线程执行完毕了...");
	4. 死锁 
		jconsole
	5. jconsole 查看
			private Object obj1 = new Object();
			private Object obj2 = new Object();
			public void a () {
				synchronized (obj1) {
						Thread.sleep(10);
					synchronized (obj2) {
						System.out.println("a");
					}
				}
			}
			
			public void b () {
				synchronized (obj2) {
						Thread.sleep(10);
					synchronized (obj1) {
						System.out.println("b");
					}
				}
			}
			
			public static void main(String[] args) {
				Demo3 d = new Demo3();
				new Thread(new Runnable() {
					public void run() {
						d.a();
					}
				}).start();
				new Thread(new Runnable() {
					public void run() {
						d.b();
					}
				}).start();
			}

18. volatile( 易变的，不稳定的)
	1. 功能
		1. 对象变量,类变量 可见性 : 每次更新对其他线程立马可见(,但不能包装原子操作)
			1. 每次访问变量时,不从缓存中获取,而是从内存中获取
			2. 并不能保证原子性(及时更新,容易导致另外线程跳不出循环,所以多线程计数必须使用锁)
		2. 操作不会造成阻塞
		2. 防止指令重排
	2. 轻量级锁,在线程之间可见
		* 可见(一个线程修改,另外线程能够读到修改后的值)
			* 多个线程
			* 同一把锁
			* 
		* synchronized线程互斥,也是线程可见的
	2. 底层实现
		1. 增加了一个lock执行:
			1. 多处理器,将当前处理器缓存的内容写到系统内存
			2. 写回到内存的操作,会使其他cpu缓存内容失效,需要重新获取,保证数据一致性
			3. 磁盘  内存  cpu缓存
	3. 硬盘->内存->cpu缓存
	4. sync比较
		1. volatile只能保证变量的可见性,不能保证原子性
		2. volatile使用简单的方法,eg:get set,更简洁,轻量一些
		3. synchronized可以替代volatile,反之不能

		1.volatile仅能使用在变量级别；
		   synchronized则可以使用在变量、方法、和类级别的
		2.volatile仅能实现变量的修改可见性，并不能保证原子性；
		   synchronized则可以保证变量的修改可见性和原子性
		3.volatile不会造成线程的阻塞；
		   synchronized可能会造成线程的阻塞。
		4.volatile标记的变量不会被编译器优化；
		   synchronized标记的变量可以被编译器优化
		5. synchronized 三大特性
			1. 原子性
			2. 可见性
			3. 有序性
			4. 阻塞

	
			volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取； synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
			volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的
			volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性
			volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。
			volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化


19. 原子类(**jdk5**出现) 
	1. 原子更新基本类型
	2. 原子更新数组
	3. 原子更新抽象类
	4. 原子更新字段

	1. 原子更新基本类型
		1. AtomicInteger(initValue)   
			1. i.getAndIncrement();      i++
			2. i.incrementAndGet();		++i
			3. i.getAndDecrement();		i--
			4. getAndAdd(int); 增加减少等操作
			5. getAndUpdate(int);
		2. 底层使用  native  unsafe
		3. pre 和 next 不相等(可能被其他线程修改) while 循环  ,相等者返回
				int pre  ,next ; 
				do{	
					pre = get();
					next = updateFunction.applyAsInt(pre);
				}while(!  compareAndSet(pre,next));
	2. 原子更新数组
		1. AtomicIntegerArray(int[])  
			1. is.getAndAdd(index,value);
	3. 原子更新抽象类
		1. AtomicReference<T>()
			1. 对T,原子操作,而不是T里面的属性原子
	4. 原子更新字段
		1. AtomicIntegerFieldUpdate<T> u =AtomicIntegerFieldUpdate.newUpdater(clazz,"filedName")
			1. u.getAndIncrement(t);
			2. t.getFiledName();
		2. 字段必须 volatile 修饰
20. lock接口 (**jdk5**出现)  
	1. synchronized
	2. volatile: 可见性ok,,,非原子性不能解决
	3. atomic
	4. 接口:(对synchronized简单分装)
		1. lock
		2. unlock
		3. trylock->boolean
		4. lockInterruptibly
	5. java.util.concurrent.locks.*
		1. ReentrantLock(boolean)	 实现类
		2. StampedLock 1.8
		3. ReadWriteLock
		4. Condition
		5. AbstractQueueSynchronizer  1.5
	6. 特点
		1. Lock  lock = new ReentrantLock();  //默认非公平
		2. lock.lock();
		3. lock.unlock();
	7. 特点
		1. 需要自己现实的获取和释放锁(繁琐,更加灵活)
		2. 简单实现公平性
		3. 对synchronized的包装,类似一个工具提供很多便捷操作
		4. 非阻塞的获取锁  trylock
		5. 获取锁,能被中断
		6. 能超时获取锁
	
21. 自己实现一个lock
	1. 实现Lock接口
	2. 实现lock方法
	3. 实现unlock方法

	不能到重入,a调用b,b在lock时候一直等待   
		1. 全局变量
			1. isLock = false;
		2. 实现lock方法
			1. 在方法上添加synchronized
			2. 判断变量isLock  while(isLock) wait (第二个线程)
			3. 改变isLock = true;
		3. 实现unlock方法 
			1. 在方法上添加synchronized
			2. 改变isLock = false;
			3. notify  
		

	重入方式
		1. 全局变量
			1. isLock
			2. lockTh当前线程
			3. lockCount 重入次数,方便unlock
		2. 实现lock方法
			1. 在方法上添加synchronized
			2. 获取当前线程  Thread cThread = Thread.currentThread();
			2. while 判断变量isLock==true 和 判断当前进入线程和全局变量的线程!=   cThread!= lockTh
				1.   wait
			3. 改变isLock=true
			4. 改变全局变量 当前线程
			5. 改变全局变量 重入次数  lockCount++;
		3. 实现unlock方法
			1. 获取当前线程  Thread cThread = Thread.currentThread();
			2. if当前进入线程==全局线程 cThread==lockTh 
				1. 全局 重入次数  lockCount--
				2. 当全局重入次数lockCount == 0
					1. 改变 isLock=false  
					2. notify`
22. AQS  Abstractcynchronizer  AbstractQueuedSynchronizer
	1. 描述
		1. 等待队列(FIFO)阻塞锁和相关同步器提供一个框架
		2. 单个原子int值来表示状态,大多数同步器有用
		3. 支持 独占和共享模式
			1. 独占模式,其他线程无法获得锁
			2. 共享模式,对个线程可能获取到锁,但不一定可以获取到
	2. 方法
		1. tryAcquire	独占 lock
		2. tryRelease	独占 unlock
		3. tryAcquireShared
	3. 计数器内部实现/引用 一个aqs的实现,然后调用
		1. ReentrantLock


23. AQS  锁实现
	1. getState	int值可以表示重入的次数
	2. currentThread
	3. 重入

24. 重入锁,公平锁
	1. 公平锁(锁获取)
		1. 如果一个锁是公平的,那么所的获取顺序就应该符合请求的绝对时间顺序
		2. 队列维护
	
25. 读写锁  
	1. 写锁:**排他锁**,效率低,只能有一个线程操作
	2. 读锁:**共享锁**share,效率稍高,多个线程共同进入

	1. **ReentrantReadWriteLock**
		1. Lock l = readLock	
		2. writeLock
			1. lock
			2. unlock

	1. 读写互斥
	2. 写写互斥
	3. 读读不互斥,很快
		2. a.await();
	
	1. get  set  sleep 
	2. 线程 读 读  , 读写  写写, 测试	

26. 读写锁实现原理
	1. ReentrantReadWriteLock
		1. 内部类  ReadLock,WriterLock  实现lock
		2. Sync 同步器 继承 AQS,  FairSync,NonFairSync 继承sync
			1. 读锁
				3. firstReader 线程,  
				4. firstReaderHoldCount++ 重入次数
				5. 
			 
	2. 问题
		1. 写锁的重入次数(只有一个),
			1.  state & (1<<16)-1    65536-1= Max_Count 最大值
			2.  int 低2位个数,保存了重入次数
			3.  重入次数不能大于 最大值 65535
			4.  低位写锁
			5.  setState( getState()+acquires )
		2. 读锁的重入次数
			1. state >>> 16  
			2. 高位读锁
			3. setState(c+ 1<<16)
		3. 读锁的个数(  好像反了~~   重入次数是 HoldCounter)
			1. 底层使用ThreadLocal<HoldCounter>子类 ,每个线程创建一个,保存在
				1. HoldCounter.count++  每个读线程的重入次数
			2. readHolds

	3. int 4byte  32位
		1. 1111 1111 1111 1111 -- 1111 1111 1111 1111
		1. 前16位表示一个锁的状态
		2. 后16位表示另外一个锁的状态
		3. 低位写锁,高位读锁
			1. &
			2. >>> 无符号右移动  读锁数目


27. 读写锁-锁降级(写->读),
	* 写锁降级为读锁
		* 写锁没有释放的时候,获取到的读锁,再释放写锁
		* 写锁里面嵌套读锁,嵌套在写完毕,释放写锁之前
``` 		
 w.lock
 ...
 r.lock //降级
 w.unlock
```			
	* 锁升级(ReentrantReadWriteLock 不支持)
		* 读锁升级为写锁
			* 在读锁没有释放的时候,获取写锁,在释放读锁
				* 获取不到
				* 

28. 总结
	1. 出现线程安全问题的条件
		1. 多线程
		2. 共享资源
		3. 非原子性操作
	2. 解决线程安全问题的方法
		1. synchronized
			1. 偏向锁
			2. 轻量级
			3. 重量级
			4. 重入锁
		2. volatile
			1. 线程可见性
			2. 不能保证原子性操作
		3. 原子类 atomic
		4. Lock
			1. aqs
			2. 共享锁,独占锁(排他锁) 
	3. 锁
			1. 偏向锁
			2. 轻量级
			3. 重量级
			4. 重入锁
			5. 自旋锁
			6. 共享锁
			7. 独占所
			8. 排它锁
			9. 读写锁
			10. 公平锁
			11. 非公平锁
			12. 死锁
			13. 活锁

----------

----------
 

29. 线程之间通行 wait,notify
	1. 解决多个线程之间协作 , 一个线程依赖另外一个线程计算结果/通知信号
	2. 耗性能
		1. 通过 volatile 变量,一个线程执行完,把变量改变,
		2. 另一个线程 while空转,等变量改变,然后执行
	3. 等待,唤醒方法
		1. 在syn(d)里面
		2. 谁syn,谁调用wait,notify
			1. d.wait
		3. notify 随机叫醒
		4. notifyAll 叫醒所有的
			1. 叫醒只是从 wait pool 到 lock pool ,
			2. 需要执行notify的线程释放或者其他线程释放锁资源后在竞争锁资源
			3. 不同对象 不同wait notify,不同对象叫醒各自的对象

30. 生产者消费者
	1. Tmall : 必须只有一个
		1. count 当前产品数目
		2. maxCount 最大数目
		3. 生产 push 方法  
			1. 加锁
			2. 最大值 ,wait
			3. count++
		4. 消费 take
			1. 加锁
			2. 小于0 wait
	2. Producer  implements
		1. tmall 构造方法应用
		2. while  push  sleep
		3. notify / notifyAll
	3. Comsumer
		1. tmall 构造方法应用
		2. while  take  sleep
	4. jconsole -> 看到线程 waiting状态 

31. condition
	1. 能够制定,叫醒条件
	2. api
		1. await
		2. signal  == notify
	3. 步骤
		1. 创建ReentrantLock
		2. lock.newCondition
		3. b.signal
		4. notify / notifyAll
	4. a b c 三个线程 顺序执行 
		1. wait/ notifiAll
			1. int 变量 ++  
			2. 所有线程 while !=判断 1/2/3  wait
			3. 然后 执行  notifyAll  变量++
			4. 最后 变量=1
		2. 三把锁
			1. Lock lock = new ReentrantLock();
			2. Condtion a= lock.newCondition();
			3. 每个方法 
				1. 加锁  
				2. while !=判断 1/2/3  wait改成a.await/b.await/c.await ...  
				3. b.signal(a执行完毕叫醒b) c.signal a.signal
				4. 变量++;
				4. 释放锁 

32. condition改生产者消费者
	1. synchronized -> lock.lock()
	2. wait->  condition.await
	3. notifyall -> condition.signal

 	1. 等待队列    wait pool
	2. 同步队列	  lock pool

	1. 实现有界队列 FIFO
		1. add
			1. 等待队列Z
		2. remove
33. condition源码
	1. ReentrantLock->newCondition-> new ConditionObject();
	2. ConditionObject impliment Condition
			* await
				* 加入等待队里 addConditionWaiter
				* 释放锁  fullyRelease
				* 判断是否在同步队列
				* 放入同步队列 acquiredQueued
				* 如果还有next节点 
			* signal
				* 是否是独占锁
					* 抛异常
				*第一个节点不空,就释放 doSignal
					* 
	4. 多个condition就有多个 等待队列 		

34. 数据库连接池
	1. LinkedList pool
	2. ConnectionUtil
		1. get
			1. 加锁
			1. while 判断是否pool size >0 等待 wait await
			2. removeFirst
			3. 释放
		2. release
			1. 加锁
			2. addLast
			3. 唤醒  notifiAll  signal
			4. 释放

35. join
	1. 线程加塞
	2. 线程a里面,遇到b.join 或者 join(millis), b先要执行完毕后a执行
	3. 实现
		1. 加锁 synchronized  ,锁的是主线程/其他非加塞线程
			while(this.isAlive())  //加塞线程!!!
				wait(0);    // 主线程/其他非加塞线程
			notifyAll  //主线程/其他非加塞线程

36. ThreadLocal<T> 线程局部变量
	1. 方法
		1. get
			
		2. set
		3. remove
		3. initialValue  // 初始值 
	2. get
		1. 获取当前线程
		2. 通过线程t,拿到TheadlocalMap
			1. t.threadLocals, 
			2. 当线程创建的时候,线程实例化了一个, ThreadLoacal.ThreadlLocalMap map =..
			3. TheadLocalMap是ThreadLocal 静态内部类
			4. Entry extends WeekReference<ThreadLocal> 是ThreadLocalMap的静态内部类
				1. key ThreadLocal
				2. value value
		3. 通过map获取 Entty e=  map.getEntry(this);
		4. 返回e
		5. 第一次会调用 initialValue	方法 ,创建 TheadlocalMap
	3. set
		1. 获取当前线程
		2. 过去map
		3. map.set t,value  /createMap
	4. remove
		1. 获取map
		2. m.remove	
			
37. CountDownLatch 使用  锁存器计数
	1. 介绍
		1. 同步辅助类,在一组线程完成操作之前,运行一个或多个线程一直等待
		2. 用给定的计数器初始化countDownLatch,调用countDown() --
		3. 当计数器达到0之前,await方法一直阻塞
		4. 之后释放所有等待线程,只能用一次.如果需要多次请使用CyclicBarrier
	2. api	
		1. await await(timeout,TimeUnit) 其锁存器!=0等待,除非线程中断	
		2. countDown()   锁存器值 -1,如果==0了,则释放所有的等待线程
		3. getCount()
	4. 当计数器==0的时候,唤醒所有await的线程
	
38. CyclicBarrier(int parties) /(parties,Runnable barrierAction)   使用 循环的 栅栏
	1. 介绍
		1. 开会,几个人到达等待await,某个点(时间点),然后执行
		2. 当达到 计数点count个数(人到齐) 的时候触发
		3. 同步辅助类,一组线程相互等待,直到达到某个公共屏障点 common barrier point
		4. 在达到 释放等待线程之后,还可以重用,所有事循环的
	2. 构造方法
		1.  CyclicBarrier(int parties)
			1.  当调用await的次数达到 parties后, 线程自动唤醒
			2.  如果一个线程异常,达不到屏障点,一直会等待
		2.  CyclicBarrier(parties,Runnable barrierAction)
			1.  达到屏障点时候,马上执行(在唤醒所有线程之前)
			2.  类似一个回调
	3. api
		1. await   
		2. reset 可以重置 

39. Semaphore	信号
	1. 指定信号个数,max线程个数
	2. api
		1. acquire  一般线程开始,进入等待,看
		2. release  一般线程结束,退出
	3. 类似线程池 , 固定大小fixd 


40. Exchanger<T>  2个线程 数据对比 
	 1. api
		 1. exchange(T t)->T t
	 2. 线程一 exchange  ,  线程二  T result t = exchange(t); 对比
	 3. 遗传算法,管道算法	


41. CountDownLatch,CyclicBarrier,Semaphore源码
	1. AbstractQueuedSynchronizer
		1. tryAcquire
		2. tryRealease
		3. tryAcquireShared
		4. tryReleaseShared
	1. CountDownLatch	共享锁 syn需要实现 tryAcquireShared
		1. await	
			1. syn.acquireInterruptible();
		2. getCount
		3. countDown
			1. syn.releaseShared(1)
	
		1. tryAcquireShared
			1. getState()==0?  1 :-1
				1. -1 需要等待
		2. tryReleaseShared
			1. getState=
				1. 如果=0  return false ; 不用释放
				2. !=0 ,  state-1 ,赋值  然后 return state==0; 
	2. CyclicBarrier
		1. await
			1. dowait
				1. 引用全局 lock
				2. lock.lock();
				3. 判断 generation里面的值 boolean 是否中断
					1. 中断 ,抛出异常
				4. 	-- count 等待数
				5. 	当等待数=0
					1. 如果 传递的runnable接口不为空,就 xx.run
					2. nextGeneration() ,  重置
						1. trip.signalAll 全部叫醒
						2. generation broken 设置成false
						3. count = parties;
					3. 返回 **return** false
					4. 如果出现异常 breakBarrier   Interrupt
						1. generation broken 设置成 true	//可以传递
							1. 如果一个线程true了,后面线程state!=0的就会中断
							2. 唤醒前面await的线程
							3. 如果还有线程进来,broken true 也会异常抛出

						3. trip.signalAll() 全部叫醒
						2. count = parties;
				6. trip.await //等待
				7. 如果等待中 中断
					1. breakBarrier
					2. 抛异常  
						
		2. reset
			1. 重置 count 
			2. 叫醒所有当前await线程
			3. 当rest后还有线程进来就,不能唤醒了!!!
			4. 如果还有线程进来,count--值不能达到0,可能永久不被唤醒
	3. semaphore
		1. permits  竞争资源
		2. 公平/不公平 FairSync
		3. 同事n个线程 , 共享锁


42. future 提前完成任务
	1. FutureTask<T><Callable>  implements RunnableFuture<T>
		1. get();  //获取结果
	2. Callable<T>
		1. call  :  ()->T
	3. Thread(FutureTask).start()
	4. task.get(); 

43. Future 简单实现
	1. Future f= new Future();
	2. f.set(T)
		1. sync
		2. notify
	3. f.get()
		1. syn
		2. wait

44. Future源码
	1. Callable<T>  接口, 功能接口 只有一个抽象方法
		1. call() throws Exception : ()->T
	2. FutureTask implements RunnableFuture extends
		1. 继承关系
			1. FutureTask<T> implements RunnableFuture<T>
			2. RunnableFuture<T> extends Runaable,Future<T>
		2. 构造方法
			1. FutureTask(Callable)
			2. FutureTask(Runable,T result)
				1. Runable,result 转成 callable对象
				2. 先调用 run,后返回result
		3. 成员变量
			1. callable
			2. state	
				1. new =0
				2. Completing
				3. nomal
				4. exceptional
				5. canceled
			2. outcome 
				1. 异常
				2. 结果 
		4. run方法
			1. 判断状态 new  ,
				1. 不为new 异常
			2. callable不空, 调用call方法,接受返回值
			3. ran=true
			4. 异常
				1. ran=false
				2.   
			5. ran成功 调用 set(result)方法	
				1. 结果赋值 outcome  = result
				2. 
		5. get方法
			1. while state <-= completing
				1. awaitDone 等待
					1. 创建WaitNode
						1. thread
						2. next
					2. node放入等待队列
					3. LockSupport.park()
					4. 知道state>completing 
						1.  return state
			2.  report
				1.  s== NORMAL 正常 ,返回outcome
				2.  异常,抛异常
		6. set
			1. outcome=v
			2. finnishCompletion
				1. while WaitNode !=null
					1. WaitNode next
					2. LockSupport.unpark
		7. 
	3. Thread


	* Runable run 是被线程调用的,在run方法是异步执行的
	* Callable call 不是异步执行的,是Future在异步run方法里面调用的,线程中的耗时操作


45. Fork/Join框架
	1. 简介
		1. 多程程目的
			1. 提高程序性能
			2. 充分利用cup性能
		2. 多cpu分块
		3. fork 分块求和  join 合并结果
	2. 类
		1. abstract class ForJoinTask<T>  implememts Future<T>: 返回值
			1. abstract class RecursiveTask<T>
				1. compute()  : ()->T
		2.  ForkJoinPool
			1.  submit(ForJoinTask); (ForJoinTask)->  Future<T>
	
```
pulic class Demo extends RecursiveTask<Integer>{

int begin =1;
int end =100;
@Override
public Integer compute compute(){
	int sum = 0;
	if(end-bebin<=2){
		for(int i =begin; i<=end;i++){
			sum+=i;
		}
		
	}
	else{
		//拆分
		d1 = new Demo(begin,(begin+end)/2)	
		d2 = new Demo((begin+end)/2+1 ,end)			
		d1.fork();
		d2.fork();		
		sum = d1.join+ d2.join

	}
	return  sum;
}


ForkJoinPool poll = new ForkJoinPool(); `` new ForkJoinPool(3) 大小3个线程
Future<Integer> futrue= pool.submit(new Demo);
future.get();

}
```		
	
46. 同步容器与并发容器
	1. 同步容器 : 以前所有的容器 vector 数组,集合
		1. 线程不安全		arraylist, HashMap
		2. 性能差   		vector , Hashtable
	2. 并发容器 : jdk1.5  CopyOnWriteArrayList  
		1. 解决并发
	
	1. Vector ArrayList
		1. add方法 
			1.  Vector 添加synchronized
		2. arraylist转成安全 
			* Collections.synchronizedList(list)
				* new SynchronizedList(list)
					* add方法 也是和vector一样 ,添加了 synchronized 锁
					* 方法锁  改到了 代码块锁
	2. Hashtable  HashMap
		1. put方法   
			1. Hashtable 添加synchronized
			2. HashMap	没有
		2. HashMap转成安全
			* Collections.synchronizedMap(map)  
				* new SynchronizedMap(map);
					* put方法  也是和 Hashtable 一样 ,添加了 synchronized 锁
					* 方法锁  改到了 代码块锁
	
	1. Vector -> ArrayList -> CopyOnWriteArrayList
		1. 读 没问题
		2. 写 copy源数据
	2.  Hashtable  HashMap-> ConcurrentHashMap
		1. Map 分段分去
		2. put 分段锁,不全部锁
		
47. 并发容器 CopyOnWriteArrayList
	1. 写操作
	2. 先拷贝 一份数组
	3. 然后操作新数组 ,在新数组添加
	4. 最后 改变原数组的指向,指向新数组

	1. add
		1. ReentrantLock lock.lock();  //全局的 ReentrantLock 排他锁
		2. Object[] newA=  Arrays.Copy(oldObjs,len+1);  //新数组 长度加1
		3. newA[len]=e;
		4. setArray(newA); // array = newA;
		5. return true;
	2. remove(index)
		1. 加锁
		2. 获取数组
		3. 移除  
			1. copy	 开始的
			2. copy 结束的 
	
	类似读写分离
	读效率高,写都需要copy内存,写多内存消耗大

48. 并发容器   非阻塞队列 ConcurrentLinkedQueue   ConcurrentHashMap
	1. 线程安全
		1. synchronized
		2. volatile
		3. cas
	1. 非阻塞队列 ConcurrentLinkedQueue  implements Queue
		1. head tail = new Node(null);  //第一个数据null
		1. 入队	offer
			1. p = t =tail ;
			2. q= p.next();
			3. q==null ->  p.next=newNode
			4. 奇数节点 ,tail是指向最后的,然后直接 
				1. p = t =tail ; 
				2. p.next=newNode
		2. 出队	poll
			1. p=h=head 
			2. q=p.next
	2. 2

49. 阻塞队列 BlockingQueue
	1. 使用 生产者,消费者模型
		1. 消费者没有的时候,等待
	2. BlockingQueue<T>接口
		1. ArrayBlockingQueue
		2. LinkedBlockingQueue
		3. ProrityBlockingQueue
		4. DelayQueue
			1. add(t)  满了抛出异常
			2. remove  没有抛出异常
			3. put		阻塞
			4. take		阻塞
			5. offer	有返回值,boolean
			6. poll		有返回值,t
	3. 底层
		1. put
			1. 锁上  ReentrantLock lock.lockInterruptibly();
			2. 如果 while count==items.length  notFull.await();  //count实际长度,items.length max大小
			3. enqueue(e)
				1. notEmpty.signal();
			4. finaly 释放锁
		2. take
			1. 锁上  ReentrantLock lock.lockInterruptibly();
			2. while count==0 notEmpty.await
			3. 	dequeue()  出队列
				1. 	notFull.signal();
			4. 	finaly 释放锁


50. 消息队列
	1. 生产者,消费者模型
	2. 模式
		1. 点对点 
			1. 
		2. 发布-订阅
			1. 


51. 并发容器    ConcurrentHashMap  
	1. 重复 49节

52. 线程池的原理与使用
	1. 线程池概述
		1. 什么是线程池
			1. 任务放入线程池中调度
			2. 不关心,调度
		2. 为什么要用线程池
		3. 好处
			1. 降低资源消耗,避免线程的创建和销毁造成的消耗
			2. 提高响应速度,没有创建过程,直接执行任务
			3. 提高线程的可管理性. 
					线程是稀缺资源,如果无限制的创建,不仅会消耗系统资源,
					还会降低系统稳定性,使用线程池可以进行统一的分配,调优和监控.但是
					做到合理利用线程池,必须对其原理了如指掌
	2. 案例
		1. ThreadPoolExecutor threadPoll = new ThreadPoolExecutor()
			1. ThreadPoolExecutor 继承了 AbstractExecutorService
				1. 构造参数
					1. corePoolSise
					2. ze	基本大小 线程池 
						1. 当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，
						2. 等到需要执行的任务数大于线程池基本大小时就不再创建
					2. maxPoolSize	最大线程数,如果无界队列queue,这个参数无效
					3. keepAliveTime 存活时间
						1. 线程工作空间后,报错的存活时间
						2. 如果任务很多,执行时间很短,可以调大这个时间,提高线程的利用率
						3. 等于0的时候,不进行销毁
					4. unit			存活单位	TimeUnit.Days
					5. workQueue	阻塞队列	 runnableTaskQueue
						1. ArrayBlokingQueue
						2. LinkedBlokingQueue
						3. SynchronousQueue
						4. PriorityQueue
						5. DelayedWorkQueue
					6. RejectedExecutionHandler 饱和策略
						1. 
					7. ThreadFacory
						1. 可以设置创建线程的工程,可以通过工厂给每个创建出来的线程设置名字,debug和定位问题的时候非常有帮助
				2. 提交线程 execute 或者 submit
					1. threadPool.execute(runnable..) 
				3. 策略	
					1. 饱和策略 RejectedExecutionHandler:
						1. 当 队列 和 线程池 都满了,说明线池处于饱和状态,那么必须采用一种策略处理提交的新任务
						2. 默认是 AbortPolicy ,无法处理新任务时抛出异常
							1. CallerRunsPolicy		只用调用者所在线程来运行任务
							2. DiscardOldestPolicy	丢弃队列里最近的一个任务,并执行 当前任务
								1. 
							3. DiscardPolicy		不处理,丢弃掉
				4. 内部类
					1. DiscardOldestPolicy implements RejectedExecutionHandler
						1.  rejectedExecution 
					2. Worker extends AbstarctQueueSynchronizer implements Runnable
						1. 
				5. 方法
					1. shutdown()
						1. 
					2. execute(runnable)   (Runnable)->void
						* 1. 如果运行的线程小于corePoolSize,则尝试使用用户定义的Runnalbe对象创建一个新的线程
						*     调用addWorker函数会原
						*     子性的检查runState和workCount，通过返回false来防止在不应
						*     该添加线程时添加了线程
						* 2. 如果一个任务能够成功入队列，在添加一个线城时仍需要进行双重检查（因为在前一次检查后
						*     该线程死亡了），或者当进入到此方法时，线程池已经shutdown了，所以需要再次检查状态，
						*    若有必要，当停止时还需要回滚入队列操作，或者当线程池没有线程时需要创建一个新线程
						* 3. 如果无法入队列，那么需要增加一个新线程，如果此操作失败，那么就意味着线程池已经shut
						*     down或者已经饱和了，所以拒绝任务
					3. addWorder
					4. submit(callable)    (Callable<T>)->Future<T>



				6. 属性
					1. AtomicInteger ct1 = new AtomicInteger(ctlOf(RUNNUG,0)); 
						1. 用来表示线程池的运行状态(整型高3位)
						2. 用来表示运行的worker数量(第29位)
					2. private static final int COUNT_BITS= Integer.SIZE -3; //32-3
						1. 29位的偏移量
					3.  private static final int capacity = (1<<COUNT_BITS)-1;
						1.  最大容量 2的29次幂 -1
					4. 状态  private static final int
						1. RUNNING = -1 << COUNT_BITS;	//接受新任务并且处理已经进入的阻塞队列的任务
						2. SHUTDOWN =  0 << COUNT_BITS; // 不接受新任务,但是处理已经进入阻塞队列的任务
						3. STOP = 1 << COUNT_BITS;		//shutdownNow() 不接受新任务,不处理已经进入阻塞队列的任务并且中断正在运行的任务
						4. TIDYING = 2 << COUNT_BITS;	// 所有的任务都已经终止,wokerCount=0,线程转换成TIDYINGz状态并且调用terminated钩子函数
						5. TERMINATED = 3 << COUNT_BITS; //terminated钩子函数已经运行完成
					5. workerQueue 阻塞队列
					6. 2

			2. AbstractExecutorService 实现了 ExecutorService
				1. 
			3. 接口ExecutorService 继承了 Executor 接口
				1. shutdown
				2. submit(Callable<T>)
			4. 接口Executor
				1. execute(Runnable command)
	3. 源码
		1. 

53. Executor 框架原理
	1. Executors    (...)-> ExecutorService 
		1. newCachedThreadPool()   (threadFactoty) 
			1.  new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,TimeUnitl.SECOND,new SynchronousQueue<Runnable>())
			2.  用的时候创建,不用的时候销毁
			3.  
		2. newFixedThreadPool() (nThreads) (nThreads,threadFactoty) 
			1. 固定大小
			2. new ThreadPoolExecutor(nThreads,nThreads,0,TimeUnitl.MILLSECONDS,new LinkedBlockringQueue<Runnable>())
			3. ThreadFactory 接口 的newThread方法   (Runnable)-> Thread()
				1. 调用execute(runnable) 的时候
				2. 回调 newThread 方法
				3. 默认 DefaultThreadFactory
					1. 设置 组,名字,
					2. 线程优先级(5)
			4.  饱和策略 RejectedExecutionHandler
				1. 默认 AbortPolicy() 无法处理新任务时抛出异常
		3. newScheduledThreadPool(corePoolSize)  ScheduledExecutorService
			1.  new ThreadPoolExecutor(corePoolSize,Integer.MAX_VALUE,0,NANOSECOND,new DelayedWorkQueue<Runnable>())
			2.  方法
				1. execute(run);
				2. scheduled
					1. runnable
					2. delay   
					3. delay time unit 	延迟单位
				3. scheduledAtFixedRate		
				4. scheduledWithFixedDelay  延迟
		4. newWorkStealingPool();  //1.8 
			1. new ForkJoinPool(System.getRuntime().availableProcessors().ForkJoinPool.defaultForkJoinWorkerThreadFactory,)
			2. 放回 ExecutorService
				1.  
		5. newSingleThreadPool();
			1. 单个线程执行, 可以保证单个线程任务挂掉,会重新启动新线程执行任务
			

54. 简易web服务器
	1.  ServerSocket
	2.  

55. 改造 简易web服务器
	1. 	线程池,创建 线程
	2. 	pool.execute(new ServerRunnable(socket));

56. JDK8 新增原子操作类 LongAdder
	1. 介绍
		1. LongAdder , DoubleAdder
		2. LongAdder 和 AtomicLong 功能一样的
		3. LongAdder 高并发 性能更强
	2. 方法
		1. add
			1. Cell[] ->
			2.  long value
			2. base
		2. sum
		3. intValue
		4. longValue
		5. longAccumulate
	3. 

57. StampedLock
	1. 介绍
		1. 对 ReentrantReadWriteLock 的增强
		2. 写线程会出现饥饿问题 ,  应为 写锁互斥的
		3. StampedLock 读写不互斥 
			1. 读的时候发生写操作,重新去读,而不是在写的时候阻塞读
			2. 乐观锁 读写不互斥
			3. 悲观锁 读写互斥
	2. api
		1. writeLock	()->long	// 阻塞等待
		2. tryWriteLock		// 立即返回,返回0,没有获取到
		3. readLock
		4. tryReadLock		//
		5. tryOptimisticRead	//获取乐观读锁 ,返回long类型的stamp
		6. validate    		// 更具 stamp 判断是否修改
		7. unlock			// 更具 stamp 释放
		8. tryConvertToWriteLock  // 更具 stamp  , 读锁转成写锁
		9. tryConvertToReadLock

	3. demo
		1. 直接new  lock
		2. 写
			1.  long stamp = lock.writeLock();
			2. 操作
			3. lock.unlockWrite(stamp);
		3. 读
			1.  long stamp = lock.readLock();
			2.  操作
			3.  lock.unlockRead(stamp);
		4. 乐观锁
			1. long stamp = lock.tryOptimisticRead();
			2. 操作,但是可能别的线程,进行了些操作,需要校验
			3. if(!lock.validate(stamp)) 如果校验不通过 需要重新读取
				1. long stampRead = lock.readLock();
				2. 操作 
			4. 释放锁
				1. lock.unlockRead(stamp 或者 stampRead);
		5. 条件读写互换
```
long stamp = lock.readLock();
while(stamp>0)
{
	long writeStamp = lock.tryConverToWriteLock(stamp);
	if(writeStamp!=0){
		// 获取成功
		stamp = writeStamp;
		//操作...
		break;
	}
	else{
		// 获取随便,,,先释放读锁,,然后直接获取写锁
		lock.unlockRead(stamp);
		lock.writeLock();
	}
}

lock.unlock(stamp);

```

58. 重排序
	1. 什么是重排序
		1. 编译器和处理器,为了提高程序运行性能,对指令重新排序  代码优化
		2. 单线程情况下,重排序要 保证数据正确性
	2. 数据依赖性 as if serial
		1. 如果存在 数据依赖性, 就不能重排序
			1. 写后读    
				1. int a,b,c; a=1/;/写  b=a; // 读
			2. 读后写
			3. 写后写
				1. a=1; a=2
	3. 指令重排序分类
		1. 编译器重排序
		2. 处理器重排序
	4. 为什么要进行指令重排序
		1. 提高程序性能
	5. 指令重拍序所带来的影响
		1. 单线程
			1. 提高执行性能
		2. 多线程
			1. 当两个数据没有数据依赖性,处理器会对这两行代码指令重排序
			2. 顺序可能调换
				1. a=1
				2. flag=true 
			2. 
	6. 竞争与同步
		1. 

59. happens-before
	1. 用来指定两个操作的执行顺序,提供跨现场的内存可见性
		1. 不能确定 a一定在b 前执行 ,只能保证 可见性
	2. java内存模型中,一个操作执行的结果需要另外一个操作可见,那么两个操作之间必然存在 happens-before关系
	3. 规则
		1. 程序顺序规则
			1. 单个线程中每个操作,总是前一个操作 happens-before于该线程中的任意后续操作
```
a=1;  //操作1		1 happens-before 2   传递性  1 happens-before 3 
b=2;  //操作2  		2 happens-before 3
c=a+b;//操作2 
```
		2. 监视器锁规则
			1. 对于同一个锁的解锁,总是happens-before于随后对这个锁的加锁
```
public void a(){
lock.lock()
lock.unclok		  //操作1		1 happens-before 2 
}
public void b(){
lock.lock() //操作2  		
lock.unclok
}
```
		3. volatile变化规则
			1. 对一个volatile域的写, happens-before于任意后续对这个volatile的读
		4. 传递性
		5. start规则
			1. a 线程 开启b 线程 ,a里面的代码 happens-before b里面的
		6. join规则 
			1. 一个线程a里面,另外一个线程 t.join了, t线程 happens-before a线程

60. 锁的内存语义	 释放,获取
	1. 锁的释放与获取所建立的happens-before管理
		1. 释放  在   获取之前
	2. 锁的释放与获取的内存语义
		1. 锁除了 让临界区互斥执行外, 还可以让释放锁的线程获取同一个锁的另外线程发送消息


61. volatile内存语义	读和写
	1. volatile 读写所建立的happens-before关系
		1. 写 在 读 之前
	2. volatile读写的内存语义
		1. 写的时候,java内存模型,会把本地内存的共享变量 刷新到 主内存中
		2. 读的时候,java内存模型,会把当前线程的本地内存中的共享变量置为无效,从新到主内存中读取
62. final域的内存语义
	1. 写final域的重排序规则
		1. 禁止把final域的写 重排序到构造方法之外
		2. 非静态 final 赋值 可以在 
			1. 声明的时候 ,
			2. 也可以 在代码块(比构造方法优先执行)
			3. 也可以在 构造方法里
			4. 如果在代码块里面赋值,构造方法就不能赋值了!
		3. java内存模型,禁止编译器把final域的写重排序到构造方法
		4. 内存屏障, 
			1. 编译器会在final域的写之后,在构造方法执行完毕前,插入一个内存屏障StoreStore,
			2. 保证处理器把final域的写操作在构造方法中执行

			1. LoadLoad		load1 loadload load2  1优先2
			2. StoreStore
			3. LoadStore
			4. StoreLoad
	2. 读final域的重排序规则
		1. 在一个线程中,初次读对象引用和初次读该对象所包含的final域,java内存模型禁止处理器重排序这两个操作
		2. 
	3. final域为静态类型
		1. static  final
		2. 没有写
		3. 
	4. final域为抽象类型
		1. 在构造方法内对一个final引用的对象的成员域的写入，
		2. 与随后在构造方法外把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。

63. 问题定位
	1. 工具  数据  经验
	2. 




1. 
2. 
3. 
4. CopyOnWriteArrayList 写少读多场景  ,写的时候加锁 lock了,否则copy出多个list
2. ReadWriteLock 读写锁, 写与写互斥,读写互斥,读与读不互斥 读与读之间可以并发,读多写少可以提高效率
3. ConcurrentHashMap 读写都加锁
4. 








