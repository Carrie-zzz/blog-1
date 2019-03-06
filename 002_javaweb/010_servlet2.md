# 回顾 #

## 请求 ##
1. 请求行 : 格式:请求方式	访问的资源 协议/版本
2. 请求头 : 格式:key:value1,value2
3. 空行
4. 请求体 : 只有post有

## 响应 ##
1. 响应行 : 格式->协议/版本 状态码 状态码说明
2. 响应头 : 格式:key:value1,value2
3. 空行   :
4. 响应体 :

## servlet ##
1. void init(ServletConfig config) : 初始化
2. void service(ServletRequest req, ServletResponse res) : 服务方法
3. void destroy()  : 销毁
4. void init(ServletConfig config)  : 配置
5. String getServletInfo()   : 信息(完全用不到)





## 系统调用 ##
1. 不能通过构造方法传递参数

### ServletConfig ###
	 String getInitParameter(String name) 
	 Enumeration getInitParameterNames() 
	 ServletContext getServletContext() 
	 String getServletName()  


----------

----------
# 路径映射 #
	url-pattern的配置:
		完全匹配  /a/b
		目录匹配  /a/b/*
		后缀名匹配 *.jsp
		优先级:完全匹配 >目录匹配 >后缀名匹配
	  一个路径对应一个servlet



#  servlet生命周期 #
1. init :第一次创建servlet
2. service:每次请求 
3. destroy:程序正常退出(tomcat停止),程序被移除


1. 什么方法:
2. 执行者:
3. 执行次数:
4. 执行时机:

# 开机启动 #
开启服务器就创建servlet
## load- ##
	<load-on-startup>
	1
	</load-on-startup>
## 参数1 ##
加载的优先:1 优先级最大 :数字越大,优先级越低(排队) 

# 默认servlet #
1. tomcat->conf->web.xml->defaultServlet
## 随便访问一个地址 404 ##
1. 自己的web.xml

	  <error-page>
		<error-code>404</error-code>
		<location>/404.html</location>  
	  </error-page>



# 单实例,多线程 #
## 您是第3位登入成功的用户 ##
1. 连个浏览器访问,并发问题


# 访问主页总次数 #


## ServletContext:全局容器 ##
	当项目启动的时候,服务器为每一个web项目创建一个servletcontext对象.
	当项目被移除的时候或者服务器关闭的时候servletcontext销毁


1. 代表整个应用程序 application

		setAttribute(String key,Object value);//设置值
		Object getAttribute(String key);//获取值
		removeAttribute(String key)://移除值

1. 获取全局的初始化参数
2. 共享资源(xxxAttribute)
3. 获取文件资源
4. 其他操作:getMimeType




----------

# Servlet体系 #
## 目标 ##
1. 公共代码的提取
2. 后期:解决一个servlet只能处理一个请求业务的操作


## HxGenericServlet:通用的servlet  ##
#### 没有必要重写 #### 
1. 	 destroy 
2. 	 init
3. 	 getServletConfig
4. 	 getServletInfo

### service重写 ###



## HttpServlet ##
请求方式:get post put delete

1. 增:post
2. 删:delete
3. 改:put
4. 查:get
5. .....