# 回顾 #
## url ##
1. http://www.baidu.com:80/1.png
2. http:协议
3. www.baidu.com 主机名
4. 80 :端口号  从0到65535  2的16次方-1  0-1023系统占用(80留给http)
5. 1.png

## 请求过程(机制) ##
1. 请求响应机制
2. 一次请求,一次响应


## tomcat目录 ##
1. bin:startup.bat   shutdown.up
2. conf:配置文件: 端口
3. lib :tomcat运行用的jar  servlet-api.jar
4. logs:日志 ->
5. temp:临时文件
6. webapps:存放web项目的(自己写的java代码!!!)
7. work:存放_jsp,java  .class文件的



	webapps			 		 ：存放多个应用
		appName(项目名称)     :web2.5版本标准的目录结构
			|
			|---- html css js image等目录或者文件(静态web资源)
			|
			|---- META-INF	 ：存放一些meta information相关的文件
			|---- WEB-INF(特点:通过浏览器直接访问不到 目录)
			|	 	|
			|	 	|--- lib(项目的第三方jar包)
			|	 	|
			|	 	|--- classes(存放的是我们自定义的java文件生成的.class文件)
			|	 	|
			|	 	|--- web.xml(当前项目的核心配置文件)
			|	 	|
			|	 	|--- jsp 动态资源


## tomcat 和 eclipse整合 ##
1. eclipse 上的动态web项目 发布 tomcat,才能给外界访问
2.  项目文件夹:拷贝(映射)



# 内网穿透 #
https://www.ngrok.cc/


https://前置域名.ngrok.cc/Web001/index.html
127.0.0.1:80
http://stevenliu.ngrok.cc-> 127.0.0.1:80
http://stevenliu.ngrok.cc/Web001/index.html


----------

----------


# eclipse 开发环境统一 #
1. 菜单栏 : window-> preferences(偏好)
2. general->workspace->右边 
3. utf-8

## 插件 ##


# eclipse关联tomcat #

## 映射过程 ##



----------

----------


# HTTP #
1. 超文本传输协议（HTTP，HyperText Transfer Protocol)


# HTTP 请求,响应 #

## web通信机制 ##
1. 基于请求响应机制.
2. 一次请求一次响应,先有请求后有响应
3. 客户端请求(request)
4. 服务器响应(response)


# 请求(request) #
1. 客户端->服务器


## 组成 ##
1. 请求行
2. 请求头
3. 空行
4. 请求体


## 请求行(第一行) ##
1. 格式:请求方式	访问的资源 协议/版本
2. get : 
3. post:


|	区别	 |	get请求	| post |
| -----------| :-----:  | :----: |
| 客户端参数     | url后 		| 请求体里面     |
| 请求体     | 无 		| 有     |
| 大小  | 有限制   	|   无限制(大文件)   |
| 安全性 | 不安全    	|   安全   |
| 缓存 	|    可以缓存 	|  不能缓存   |



## 版本 ##
1. 1.0: 
2. 1.1: 可以保持一段时间的连接

## 请求头(第二行开始到空行) ##
1. 格式-> key:value1,value2


|	key	 |	value	| 作用 |
| -----------| :-----:  | :--------: |
| Accept   | text/html	| 支持数据类型    text/html  |
| Accept-Charset     | utf-8		| 字符集     |
| Accept-Encoding  | gzip   	|  支持压缩   |
| Accept-Language | zh-cn     	|  语言环境   |
| Accept-Language | zh-cn     	|  语言环境   |
| Host | localhost    	|  主机名   |
| Connection | keep-alive    	| 保持连接   |
| User-Agent | Chrome   	| 客户端设备 |
| Referer | url   	| 防盗链 |
| If-Modified-Since | 时间   	| 修改时间 |
| Cookie | ..   	| .. |


## 空行 ##

## 请求体(只有post有) ##
1. get: http://localhost/Web001/?username=123&pwd=123
2. post username=123&pwd=123



----------


# 响应(response) #
1. 服务器->浏览器


## 组成 ##
1. 响应行
2. 响应头
3. 空行
4. 响应体

## 响应行(第一行) ##
1. 格式->协议/版本 状态码 状态码说明

http://www.runoob.com/http/http-status-codes.html


|	状态码		 |	解释	|
| -----------| :-----:  | 
| 200        | 成功		| 
| 302        |  重定向   | 
| 304		 | 读缓存   	|
| 404 		 | 不存在 	| 
| 500 		 | 服务器内部异常|  


## 响应头(第二行开始到空行) ##
|	key		 |	value	| 解释 |
| -----------| :-----:  | :----: |
| Location   | www.baidu.com | 跳转方向 和302一起使用的   |
| Server     | Apache |   服务器型号   |
| Content-Encoding | gzip   	|  数据压缩  |
| Content-Length |  80   	|  数据长度|
| Content-Language | zh-cn   	|  语言环境  |
| Content-Type |  text/html; charset=utf-8 	|  数据类型 |
| Last-Modified | Tue, 11 Jul 2000 18:23:51   	| 最后修改时间  |
|Refresh | 1;url=www   	|   定时刷新 |
|Content-Disposition | attachment; filename=aaa.zip   	|   下载 |
|Expires |  -1 	|  缓存 |
|Cache-Control | no-cache  	|  缓存 |
|Pragma | no-cache  	|  缓存|
|Cache-Control | no-cache  	|  缓存 |
|Pragma | no-cache  	|  缓存 |
|Connection | Keep-Alive  	|  连接 |

## 响应空行 ##

## 响应体 ##




----------

----------

# 登入操作分析 #

## 步骤 ##
1. 客户端 form表单收集账号密码
2. 服务器 接受账号密码
3. 服务器 查询数据库有误账号
4. 服务器 响应登入结果
5. 客户端 显示结果

## 图 ##


## form ##
1. action
2. method
3. input  name
4. input  submit




# Servlet #
1. Servlet全名为Server applet，服务端小应用。



## servlet接口 实现步骤 ##
1. 新建一个类,实现servlet,重写父类的抽象方法
2. 配置路径:web.xml 
3. 注册:<servlet> 节点,元素
4. 映射外界(浏览器url的)路径:<servlet-mapping> 


## 抽象方法 ##
1. init : 初始化
2. service : 服务方法
3. destroy : 销毁
4. getServletConfig  : 配置
5. getServletInfo  : 信息(完全用不到)

## 关联源码 ##


## 获取方式 ##
1. form提交 绝对路径


## 路径 ##
1. 相对路径:
		./或者什么都不写 当前路径
		上一级路径  ../
2. 绝对路径:
		带协议和主机的绝对路径
		不带协议和主机的绝对路径
			/项目名/资源
3. 内部路径:
		代表当前的项目:
			请求转发 静态包含 动态包含



#  生命周期 #
1. init :第一次创建servlet
2. service:每次请求 
3. destroy:程序正常退出(tomcat停止),程序被移除


1. 什么方法:
2. 执行者:
3. 执行次数:
4. 执行时机:



任务: 模拟登入

# 参数 #
## 不能通过构造方法传递参数 ##
1. 服务器自动加载类->通过默认无参的构造方法
## 配置 ServletConfig##
1. 在web.xml中的servlet节点
2. <init-param>    key:value



# 全局应用 #
## ServletContext:全局容器 ##
1. 代表整个应用程序 application


# 单实例,多线程 #
##  servlet init多少次 ##
1. 一次new ,new thread 




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



----------

----------


# Servlet体系 #
## 目标 ##
1. 公共代码的提取
2. 后期:解决一个servlet只能处理一个请求业务的操作

## 实现方式  ##
### Servlet接口 ###
1. void init(ServletConfig config) : 初始化
2. void service(ServletRequest req, ServletResponse res) : 服务方法
3. void destroy()  : 销毁
4. void init(ServletConfig config)  : 配置
5. String getServletInfo()   : 信息(完全用不到)

### HxGenericServlet:通用的servlet  ###
#### 没有必要重写 #### 
1. 	 destroy 
2. 	 init
3. 	 getServletConfig
4. 	 getServletInfo
#### service重写 ####


### HttpServlet ###
请求方式:get post put delete

1. 增:post
2. 删:delete
3. 改:put
4. 查:get
5. .....




# 登入案例 #
1. httpservlet
2. 登入失败后,提示"用户名密码不匹配",3秒以后跳转到登录页面


