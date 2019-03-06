# 监听器(了解) #
	1. 监听三大域对象 :ServletContext HttpSession ServletRequest 
	2. 创建,销毁(生命周期)
	3. 域里面 attribute  
	4. session中的javabean状态
		监听三个对象的创建和销毁
		监听三个对象属性的变化
		监听session中javabean的状态

# 接口 #
	1. 监听三个对象的创建和销毁
		ServletContextListener
		ServletRequestListener 
		HttpSessionListener
	2. 监听三个对象属性的变化
		ServletContextAttributeListener
		ServletRequestAttributeListener
		HttpSessionAttributeListener
	3. session域中bean的状态 
		HttpSessionActivationListener(钝化和活化)
			* 钝化:javabean从session序列化到磁盘上
			* 活化:
		HttpSessionBindingListener(绑定和解绑)
			* 绑定:bean对象放到session域中
			* 解绑:移除remove

# 步骤 #
1. 编写一个类 实现接口
2. 实现抽象方法
3. 编写配置文件(bean状态不需要)



----------
	1. 监听三个对象的创建和销毁
		ServletContextListener
			* 创建:服务器启动的时候,会为每一个项目都创建一个servletContext
			* 销毁:服务器关闭的时候,或者项目被移除的时候
			* 作用:
				* 以后用来加载配置文件,spring容器CcontextLoaderListener
				* 任务调度器,花呗每月定时定点还钱!
			public class MyServletContext implements ServletContextListener {
				@Override
				public void contextInitialized(ServletContextEvent sce) {
					System.out.println("加载配置文件,初始化数据");
				}
				@Override
				public void contextDestroyed(ServletContextEvent sce) {
					System.out.println("销毁,释放资源,收尾工作");
				}
			}
		  <listener>
		  	<listener-class>com.huaxin.web.listener.MyServletContext</listener-class>
		  </listener>

		ServletRequestListener 
			* 创建:每次请求来的时候
			* 销毁:每次响应生成的时候
			* 作用:
				* 页面访问量
			public class MyServletRequestListener implements ServletRequestListener {
			    public void requestInitialized(ServletRequestEvent sre)  { 
			    	System.out.println("ServletRequest被创建了...");
			    }
			    public void requestDestroyed(ServletRequestEvent sre)  { 
			    	System.out.println("ServletRequest被销毁了...");
			    }
			}
		  <listener>
		  	<listener-class>com.huaxin.web.listener.MyServletRequestListener</listener-class>
		  </listener>


		HttpSessionListener
			* 创建:
				java中第一次调用request.getSession的时候
				jsp访问的时候创建
			* 销毁:
				session超时
				手动销毁session
				服务器非正常关闭
			* 作用:
				* 统计当前在线的用户数 http://www.cnblogs.com/xdp-gacl/p/3965508.html
				* 
			public class MyHttpSessionListener implements HttpSessionListener {
				@Override
				public void sessionCreated(HttpSessionEvent se) {
					System.out.println("HttpSession对象被创建了...");
				}
				@Override
				public void sessionDestroyed(HttpSessionEvent se) {
					System.out.println("HttpSession对象被销毁了...");
				}
			}
			<listener>
			  	<listener-class>com.huaxin.web.listener.MyHttpSessionListener</listener-class>
			</listener>

	2. 监听三个对象属性的变化(添加 替换 删除)
		ServletContextAttributeListener
		ServletRequestAttributeListener
		HttpSessionAttributeListener

		Method Summary 
			 void attributeAdded(XxEvent scab) 
			 void attributeRemoved(XxEvent scab) 
			 void attributeReplaced(XxEvent scab) 
				XxEvent
					String getName() 
				 	Object getValue()
 
	3. session域中bean的状态
		注意:	
			* 接口由javaBean实现,自己感知自己状态
			* 不需要web.xml配置
		
		HttpSessionBindingListener(绑定和解绑)
			* 检测bean是否从session域中添加或移除
			* 绑定:bean对象放到session域中
				* void valueBound(HttpSessionBindingEvent event)
			* 解绑:移除remove
				* void valueUnbound(HttpSessionBindingEvent event) 
			* HttpSessionBindingEvent
				* getName()
				* getValue()
				* getSession()

		HttpSessionActivationListener(钝化和活化)
			* 钝化:javabean从session**序列化**到磁盘上
				* void sessionWillPassivate(HttpSessionEvent se)
			* 活化:
				* void sessionDidActivate(HttpSessionEvent se) 
		
		通过配置序列化session:
			context.xml
			* tomcat/conf/context.xml						:对tomcat中的所有虚拟主机和虚拟路径生效.
			* tomcat/conf/Catalina/localhost/context.xml	:对tomcat下的localhost虚拟主机中的所有路径生效.
			* 工程的META-INF/context.xml					:对当前的工程生效.
			
			<?xml version="1.0" encoding="UTF-8"?>
			<!--
				maxIdleSwap	:1分钟 如果session不使用就会序列化到硬盘.
				directory	:xx 序列化到硬盘的文件存放的位置.生成在tomcat下work文件夹中
			 -->
			<Context>
				<Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1">
					<Store className="org.apache.catalina.session.FileStore" directory="xx"/>
				</Manager>
			</Context>
			 
# 总结 #
	Servlet的监听器分成三类8个:
		* 一类：监听三个域对象的创建和销毁的监听器.
		* 二类：监听三个域对象的属性的变更.
		* 三类：监听HttpSession中JavaBean的状态的改变.


----------
# 任务 #
	1. 验证 HttpSessionListener
		1.访问html是否创建session对象?
		2.访问一个Servlet是否创建session对象?
		3.访问一个jsp是否创建session对象?


	2. ServletRequestListener
		1.访问html是否创建request对象?
		2.访问一个Servlet是否创建request对象?
		3.访问一个jsp是否创建request对象?
