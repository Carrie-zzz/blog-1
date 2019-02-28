#线程#
1. 程序
2. 进程: 是程序执行流的最小单元，是系统独立调度和分配CPU（独立运行）的基本单位。
3. 线程:是资源分配的基本单位。一个进程包括多个线程。
			1.线程与资源分配无关，它属于某一个进程，并与进程内的其他线程一起共享进程的资源。
			2.每个进程都有自己一套独立的资源（数据），供其内的所有线程共享。
			3.不论是大小，开销线程要更“轻量级”
			4.一个进程内的线程通信比进程之间的通信更快速，有效。（因为共享变量）
4. 并发:当有多个线程在操作时,如果系统只有一个CPU,则它根本不可能真正同时进行一个以上的线程，它只能把CPU运行时间划分成若干个时间段,再将时间 段分配给各个线程执行，在一个时间段的线程代码运行时，其它线程处于挂起状。.这种方式我们称之为并发(Concurrent)。
5. 并行:当系统有一个以上CPU时,则线程的操作有可能非并发。当一个CPU执行一个线程时，另一个CPU可以执行另一个线程，两个线程互不抢占CPU资源，可以同时进行，这种方式我们称之为并行(Parallel)。




# 多线程 #

# Thread类 #

## 创建线程(构造方法) ##
1. 继承Thread ，重写里面 run 方法
2. 实现runnable 接口， 实现了runnable接口的对象，扔到thread的构造函数里面
3. 匿名内部类：两种写法

		new Thread() {
			// 重写了父类的run，Thread的子类，没有名字-》匿名内部类
			public void run() {
			};
		}.start();

    	new Thread(new Runnable() {
			// 实现接口的方法，接口的子类.没有名字-》匿名内部类
			public void run() {
			}
		}).start();

注意：如果是Thread start ，如果是Runnable 接口的，一定要和Thread

----------


## 线程常见方法 ##

### 类方法(静态)： ###
1. Thread.sleep(long mi);  //休眠
2. Thread.currentThread（）；  //当前线程
3. Thread.yield();

### 对象方法  ：  t = new Thread(); ###
1. t.start() ;   // 开启线程
2. t.getName();  //当前线程的名字
3. t.getPriority();  //优先级 【1，10】，，越高不认保证，必定先执行（默认值为5）
4. t.setDaemon(true)  ; //设置守护线程  GC:垃圾回收器，他是一个守护线程，一般的Thread 都是用户线程
5. t.join(); // t加入到默认线程

----------
# 线程项目 小球 #
## 1. 写个窗体父类  ##
    	public ParentFrame(int width, int height) {
			this(null, width, height, null);
		}
	
		public ParentFrame(String title, int width, int height,LayoutManager manager) {
			setTitle(title);
			setSize(width, height);
			setLocationRelativeTo(null);
			setLayout(manager);
			setUndecorated(true);
			// 添加组件，在父类里面写不了！！！必须在子类实现
			initView();
			// 先添加組件，然後visible
			setVisible(true);
			initListener();
		}
	
		/**
		 * 留给子类添加控件的方法
		 */
		public abstract void initView();
	
		/**
		 * 留给子类添加监听的
		 */
		public abstract void initListener();
	
## 2. 写一个小球线程类 ： 碰到墙，改变方向 ##

## 3. 解决闪屏问题！！怎么解决闪屏问题：花在一个图片上，然后在把这个图片 花在窗体上 ##
    	/** 第一步： 创建一张图片 **/
		java.awt.Image img = createImage(getWidth(), getHeight());
		/** 第二步： 图片上画圆，首先要拿到笔 **/
		Graphics img_g = img.getGraphics();
		img_g.fillOval(x, y, 100, 100);
		/** 第三步：把图片画到 窗体 ，窗体的笔 参数g **/
		g.drawImage(img, 0, 0, null);


----------


# Synchronized 同步 #
1. java.lang.IllegalThreadStateException
2. 线程 只能start一次;

## 线程安全问题产生原因 ##
1. 多个线程
2. 修改公共的资源,变量
3. 多行代码

## 解决办法 ##

### 同步代码块 ###
1. synchronized(**对象**){  同步代码块 } 
2. 每个**对象**有个标识位 1(开) 0(关) 
3. 参数**对象**起到了 锁的作用,我们通常叫他 对象锁
4.  {  ..}  代码块,,,在synchronized的代码块叫做,同步代码块

### 同步方法 ###

1. 在方法前面加:public synchronized void sell(){} 同步方法
2. 普通方法:同步方法的对象锁是  **this**: 代表当前对象
3. 静态方法:同步方法的对象锁是  当前类!eg:**Singleton.class**  == 对象.getClass()

## 同步的优缺点 ##
1. 耗时的,但是能保证线程安全

## 使用场景 ##
###  单列 ###
	
	public class Singleton {
		private Singleton() {
		}
	
		// 单例模式: 恶汉
		private static Singleton instance2 = new Singleton(); // 公共的资源
	
		public static Singleton getInstance2() {
			return instance2;
		}
	
		// 单例模式: 懒汉
		private static volatile Singleton instance;// volatile 防止指令重拍
	
		public static Singleton getInstance() {
			if (instance == null) {// 为了解决 效率问题!!!
				synchronized (Singleton.class) {// 解决线程安全问题
					if (instance == null) {
						instance = new Singleton();
					}
				}
			}
			return instance;
		}
	}


## 死锁 ##
1.原因:锁的嵌套!!!多个线程相互把持锁,互不相让
	String name;
	public Sell5(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		if ("Ly".equals(name)) {
			synchronized (Chopsticks.one) {
				System.out.println(name + "  拿到了第一个筷子");
				synchronized (Chopsticks.two) {// xx 1->0
					System.out.println(name + "  拿到了第二个筷子");
				}
			}
		} else {
			synchronized (Chopsticks.two) {
				System.out.println(name + "  拿到了第二个筷子");
				synchronized (Chopsticks.one) {// Ly 1->0
					System.out.println(name + "  拿到了第一个筷子");
				}
			}

		}
	}
	public static void main(String[] args) {
		Sell5 sell1 = new Sell5("Ly");//
		new Thread(sell1).start();

		Sell5 sell2 = new Sell5("Xx");//
		new Thread(sell2).start();
	}


----------

# wait notify  notifyAll #
1. wait 他会释放cpu,释放锁
2. notify  是唤醒一个等待线程,,进入可执行状态 
3. notifyAll  唤醒所有等待池中的线程...
4. 必须在同步里面!!  synchronized
5. 必须锁对象调用

## IllegalMonitorStateException  非法的监控异常 ##

# wait 和sleep #
|  描述 |  sleep    |  wait|
|  -----| :-----:  | :----: |
| 属于哪个类   | Thread |Object    |
| 是否让出cpu |   让出了   |   让出了   |
|   是否释放锁   |  没有释放     |  释放    |
| 时间   | 可以 |  可以   |
| 使用要求  | 没有 | 必须在同步里面,锁对象调用    |
| 中断InterruptedException | 线程终止 | 线程终止   |

----------

# 生产者消费者 #

 

----------
# 线程状态 #
## 阻塞,等待,锁  ##
1. BLOCKED 
          受阻塞并且正在等待监视器锁的某一线程的线程状态。 
2. NEW 
          至今尚未启动的线程的状态。 
3. RUNNABLE 
          可运行线程的线程状态。 
4. TERMINATED 
          已终止线程的线程状态。 
5. TIMED_WAITING 
          具有指定等待时间的某一等待线程的线程状态。 
6. WAITING  
 		  某一等待线程的线程状态。


# Lock代替   synchronized #
java.util.concurrent.locks.Lock

> 		lock = new ReentrantLock();
		condition_producer = lock.newCondition();
		condition_consumer = lock.newCondition();



synchronized   ->  lock.lock();


	public void set(String name) {
		lock.lock();
		try {
			"生产了
			condition_consumer.signal();
			// 消费了才出来生产
			condition_producer.await();		
		} finally {
			lock.unlock();// 必须执行!!!!!
		}
	}

	public void get() {
		lock.lock();
		try {
			 "消费了"
			condition_producer.notify();
			condition_consumer.wait();
		} finally {
			lock.unlock();
		}
	}



## Lock ##
1. void lock() 
          获取锁。 
2. void lockInterruptibly() 
          如果当前线程未被中断，则获取锁。 
3. Condition newCondition() 
          返回绑定到此 Lock 实例的新 Condition 实例。 
4. boolean tryLock() 
          仅在调用时锁为空闲状态才获取该锁。 
5. boolean tryLock(long time, TimeUnit unit) 
          如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁。 
6. void unlock() 
          释放锁。 


# 线程池 #

1. 线程池对象
          获取锁。  执行方法execute,结束方法shutdown
2. 工作线程对象
3. 任务
	public static void main(String[] args) {
		TestThreadPool pool = new TestThreadPool();
		// 使用JDK内置的线程池，创建具有3个内置线程的池
		ExecutorService exec = Executors.newFixedThreadPool(3);
		// 创建30个任务
		for (int index = 0; index < 30; index++) {
			MyTask2 st = pool.new MyTask2();
			// 开始执行
			exec.execute(st);
		}
		// 执行完任务后再关闭线程池，
		exec.shutdown();
	}

	class MyTask2 implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}

	}
4. 目的
	1.   假设一个服务器完成一项任务所需时间为：T1 创建线程时间，T2 在线程中执行任务的时间，T3 销毁线程时间。
	2.   则可以采用线程池，以提高服务器性能。

5. 原理