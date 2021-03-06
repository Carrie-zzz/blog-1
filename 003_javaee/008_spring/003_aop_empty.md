# 大纲 #
	1. Spring aop的概述
	2. aop原理(代理模式)
	3. aspectJ动态代理




# AOP概述 #
	1. 什么是AOP的技术？
		* 在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程
		* AOP是一种编程范式，隶属于软工范畴，指导开发者如何组织程序结构
		* AOP最早由AOP联盟的组织提出的,制定了一套规范.Spring将AOP思想引入到框架中,必须遵守AOP联盟的规范
		* 通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术
		* AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型
		* 利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率

	2. AOP:面向切面编程.(思想.---解决OOP遇到一些问题)
	3. AOP采取横向抽取机制，取代了传统纵向继承体系重复性代码（性能监视、事务管理、安全检查、缓存）
	
	4. 为什么要学习AOP
		* 可以在不修改源代码的前提下，对程序进行增强！！	
	




----------

----------

----------

# aop原理 #
	1. 底层采用2种动态代理方式
		* 基于jdk动态
			* 必须接口
		* 基于cglib动态代理
			* 必须父类

## 静态代理 ##
[参考-web基础-项目优化-数据库连接池]

## JDK动态代理(Proxy) ##



## CGLIB ##
1. 介绍:
	1. CGLIB是一个功能强大，高性能的代码生成包。它为没有实现接口的类提供代理，为JDK的动态代理提供了很好的补充。通常可以使用Java的动态代理创建代理，但当要代理的类没有实现接口或者为了更好的性能，CGLIB是一个好的选择
2. 原理
	* 动态生成一个要代理类的子类，子类重写要代理的类的所有不是final的方法。在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。它比使用java反射的JDK动态代理要快
3. 底层
	* 使用字节码处理框架ASM，来转换字节码并生成新的类。不鼓励直接使用ASM，因为它要求你必须对JVM内部结构包括class文件的格式和指令集都很熟悉
4. 缺点 
	* 对于final方法，无法进行代理

### java案例 ###
	1. 导包
		* 在Spring框架 核心包 中已经引入了CGLIB的开发包了。所以直接引入Spring核心开发包即可！
		* 核心包中 org.springframework.cglib.proxy.Enhancer
	2. 代码
	



----------

----------

----------

# springAOP工作 #

## 任务 ##
1. 


## AspectJ 框架术语 ##
	1. Joinpoint(连接点)	-- 所谓连接点是指那些被拦截到的点。在spring中,这些点指的是方法,因为spring只支持方法类型的连接点
		*UserServiceImpl实现类中的所有方法,都可以被增强
	2. Pointcut(切入点)		-- 所谓切入点是指我们要对哪些Joinpoint进行拦截的定义
		* UserServiceImpl实现类中的某些已经被增强的方法
		* 需要配置,告诉哪些是的
	3. Advice(通知/增强)	-- 所谓通知是指拦截到Joinpoint之后所要做的事情就是通知.通知分为前置通知,后置通知,异常通知,最终通知,环绕通知(切面要完成的功能)
		* 一个类里面的一个增强方法,
		* 自己编写
		* 具体增强的代码:日志,事务
		* eg:需要在add方法执行前打印日志,需要有这个通知类对象
	4. Target(目标对象)		-- 代理的目标对象
	5. Weaving(织入)		-- 是指把增强应用到目标对象来创建新的代理对象的过程
		* 动态生成代理对象的过程
		* 过程中自动选择 jdk还是 cglib
	6. Proxy（代理）		-- 一个类被AOP织入增强后，就产生一个结果代理类
		* 生成的代理对象
	7. Aspect(切面)		-- 是切入点和通知的结合，以后咱们自己来编写和配置的
		* 切入点(配置)+通知(方法)
	8. Introduction(引介)	-- 引介是一种特殊的通知在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field

----------

----------

----------
# aop开发 #
	1. 创建工程,导入jar
		* spring核心+日志:  6个包
		* web整合:spring-web.jar 1个
		* 测试:spring-test 1个
		* springAop包
			* spring-aop.jar
			* aop联盟规范:aopalliance:org.aopalliance-1.0.0.jar
		* aspectJ开发包
			* spring-aspects-4.3.10.RELEASE
			* aspectj:org.aspectj.weaver-1.6.8.RELEASE.jar
	2. 配置文件,添加aop约束
 
	3. 编写目标类
 
	4. 将类交给spring(配置)
		
	5. 测试(准备:在不改变service源码的情况下,添加日志)

	6. 编写切面类
		// 切面类= 切入点 + 通知
		public class MyLogAspect {
			// 通知
			public void log() {
				System.out.println("日志");
			}
		}
	7. 切面类交个spring
		
	8. 配置:1.代理对象 2.增强的切入点



# 切入点配置表达式(复制一个applicationContext.xml) #
1. 格式:
	* execution(   [修饰符] 返回值类型 包名.类名.方法名(参数)   )
		 
# 通知类型 #
	1. 前置通知
		* 在目标类的方法执行之前执行。
		* 配置文件信息：<aop:before method="before" pointcut-ref="myPointcut"/>
		* 应用：可以对方法的参数来做校验
	
	2. 最终通知
		* 在目标类的方法执行之后执行，如果程序出现了异常，最终通知也会执行。
		* 在配置文件中编写具体的配置：<aop:after method="after" pointcut-ref="myPointcut"/>
		* 应用：例如像释放资源
	
	3. 后置通知
		* 方法正常执行后的通知。		
		* 在配置文件中编写具体的配置：<aop:after-returning method="afterReturning" pointcut-ref="myPointcut"/>
		* 应用：可以修改方法的返回值
	
	4. 异常抛出通知
		* 在抛出异常后通知
		* 在配置文件中编写具体的配置：<aop:after-throwing method="afterThorwing" pointcut-ref="myPointcut"/>	
		* 应用：包装异常的信息
	
	5. 环绕通知
		* 方法的执行前后执行。
		* 在配置文件中编写具体的配置：<aop:around method="around" pointcut-ref="myPointcut"/>
		* 要注意：
			* 目标的方法默认不执行，需要使用ProceedingJoinPoint对来让目标对象的方法执行。
			* 如果joinPoint.proceed(); 有返回值,必须返回

		/**
		 * 执行前,后的通知,,,默认情况下,目标类的方法不会被执行,需要手动放行
		 */
		public void logAround(ProceedingJoinPoint joinPoint) {
			System.out.println("logAround");
			// 放行
			try {
				joinPoint.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("logAround2");
		}



	 HttpServletRequest request = ((ServletRequestAttributes)org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()).getRequest();  
