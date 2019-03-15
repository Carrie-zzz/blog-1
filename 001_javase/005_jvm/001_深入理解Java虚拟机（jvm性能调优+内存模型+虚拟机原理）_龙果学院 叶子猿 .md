1. 1
2. 2

3. jdk,jre,jvm
	1. [文档](https://docs.oracle.com/javase/8/docs/)
	2. jdk包含jre  ,jre包含 jvm
	3. jre = jvm + javase + deployment
	4. deployment(applet) 不常用
	4. 字节码,运行在jvm上	
![jvm](./img/001_jvm.png)
	
	1. RIA 富客户端 Rich Internet Applications
		1. 最初,网络原因 c/s 客户端应用
		2. 后来,网络提升迁移到浏览器 b/s ,
		3. 现在,ria , flash,javaFx类似swing
		
4. 内存溢出问题 分析解决
	1. java.lang.OutofMeonryError : java heap space
	2. 堆内存快照
		1. run  configuration -> 切换tab arguments  
		2. VM arguments 输入
			* -XX:+HeapDumpOnOutOfMemeryError -Xms20m -Xmx20m
				* Xms20m
				* Xmx20m
		3. 运行,生成dump文件 到当前项目文件下面, java_pid11.hprof
		4. [查看工具eclipse mat MemoryAnalyzer ](https://www.eclipse.org/mat/downloads.php)
		5. 打开工具, File->Open Heep Dump

	```
	while(true){
	list.add(new Demo());
	}
	```
5. jvm 可视化监控工具
	1. jdk->bin->jconsole(实际是tools.jar)
		1. 显示所有java进程  , jps 显示出来的
	2. jvm内存 五大块 之一 堆内存
		1. 新生代
			1. Eden Space
			2. Survivor Space1 存活
			3. Survivor Space2
		2. 
6. 杂谈
	
7. java发展历史
	https://www.cnblogs.com/guoqingyan/p/5667064.html
	
	1. Java 之父 James Gosling
	2. 最开始 oak 嵌入式系统语言,“Oak”（橡树），以他的办公室外的树而命名。 

	1. 1995.5 oak -> java1.0 write once run anywhere
	2. 1996.1 发布jdk1.0 jvm sun classic vm (被淘汰)
	3. 1996.9 首届javaOne大会
	4. 1997.2 jdk1.1 内部类 反射 jar文件格式 jdbc rmi
	5. 1999.6 j2 se,me,ee    Hotspot vm
	6. 2000.5 jdk1.3 timer , java2d
	7. 2002.2 jdk1.4 struts.hibernate,spring1.x
		1. 正则,Nio,日志,xml
	8. 2004.9 jdk1.5 tiger 自动装箱拆箱,泛型,注解,枚举,可变参数,增强for,spring2.x
	9. 2006 jdk1.6 java se,me,ee -> jdk6
		1. 提供脚本语言
		2. 提供编译api已经http服务器api
		3. 
	10. 2009 jdk1.7 Jigsaw OSGI  oracle 收购 sun
	11. 2011 javase 1.7
	12. 2014 javase 1.8

8. 历史
	1. 2009 jdk1.7
	2. 2011 javase 1.7

9. java技术体系
	1. java程序设计语言
	2. 各硬件平台的java虚拟机
	3. Class文件格式
	4. Java Api
	5. 第三方java类库
		
	1. code java ,c# ->编译器[n]->class字节码->jvm[n]

	1. java se standard  edition
	2. java me mobile edition
	3. java ee 

10. jdk8 新特性
	* 接口默认方法 和 静态方法
	* Lambda 表达式 和 函数式编程
		* 替代匿名内部类
		* 可读性好
		* 集合操作大大改善
	* Date Api
	* 重复注解
	* 更好的类型推断
	* Nashorn JavaScript 

11. lambda 表达式
	1. ()->...

12. java虚拟机 Sun Classic VM
	1. 虚拟机产品
		1. Sun Classic VM
		2. Exact VM
		3. Sun Hotsport(虚拟机之一)
		4. KVM kilobyte
		2. Bea JRockit
		3. IBM J9
		4. Azul VM
		5. Liquid VM
		6. Dalvik VM
		7. Microsoft JVM
	2. Sun Classic VM 
		1. 世界第一款 商用java 虚拟机	
		2. 只能是纯解释器的方式来执行java代码
13. Exact VM
	1. jdk 1.2 出现
	2. 准确式内存管理 exact memory management
		1. 虚拟机 能准确知道内存某个位置是什么类型的
		2. 引用类型 还是 值
	3. 遍历器和解释器混合工作以及两级即使编译器
	4. 只在solaris平台发布,然后被取代
14. Sun Hotsport
	1. 历史
		1. 
	2. 优势
		1. 热点探测
	3. 
15. KVM kilobyte
	1. 简单,轻量,高度可移植
	2. 手机平台运行
16. Bea JRockit
	1. bea 2008被 oracle 收购
	2. 世界上最快的java虚拟机
	3. 专注服务器端应用
	4. 优势
		1. 垃圾收集器
		2. MissionControl服务套件
			1. 诊断内存泄漏  
17. IBM J9
	1. IBM technology for java virtual Machine = IT4J
	2. 
18. Dalvik
	1. 不是java虚拟机, 没有遵循规范
	2. 基于寄存器架构	
	3. 执行 Dex  dalvik executable

19. Microsoft JVM
	1. 

20. Azul VM  / Liquid VM
	1. 高性能的java虚拟机
		1. 


----------


21.   
22.    
23.       


