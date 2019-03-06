# eclipse 开发环境统一 #
1. 菜单栏 : window-> preferences(偏好)
2. general->workspace->右边 
3. utf-8





http://119.29.242.48:8080/manager/demo/demo.html


# web项目 #
1. web（World Wide Web）即全球广域网，也称为万维网，它是一种基于超文本和HTTP的、全球性的、动态交互的、跨平台的分布式图形信息系统。
2. 是建立在Internet上的一种网络服务，为浏览者在Internet上查找和浏览信息提供了图形化的、易于访问的直观界面，其中的文档及超级链接将Internet上的信息节点组织成一个互为关联的网状结构。




# web分类 #
1. 静态的web资源:html css txt img
2. 动态的web资源: b.html 




## 静态web开发技术 ##
1. html css ....



## 动态的web开发技术 ##
1. servlet
2. jsp
3. php
4. .net
5. Python
6. ruby




## web通信机制 ##
1. 基于请求响应机制.
2. 一次请求一次响应,先有请求后有响应
3. 客户端请求(request)
4. 服务器响应(response)




----------

----------
# qq客户端 #
1. 装了一个聊天客户端软件:qq

# 服务器 #
1. 装了一个服务器**软件**:



# 服务器软件 #
http://blog.csdn.net/for_china2012/article/details/9307075

## 下载 ##
http://tomcat.apache.org/
1. zip
2. tar.gz


## 启动 ##
1. 解压
2. tomcat/bin目录下
3. startup.bat
4. 需要配置环境变量JAVA_HOME,不然一闪而过
5. 闪退:如果pause()都不行,->Catalina_home配置了,然后tomcat改了地方!!!请删除catalina!


netstat -ano|find "8080"

nt kernel & system
80端口的烦恼：[3]清除NT Kernel占用80端口
http://jingyan.baidu.com/article/f96699bbca15a1894e3c1bc4.html


## 关闭:bin目录下 ##
1. shutdown.bat
2. x
3. ctrl+c





# Tomcat目录结构 ppt#
1. bin:startup.bat   shutdown.up
2. conf:配置文件: 端口
3. lib :tomcat运行用的jar  servlet-api.jar
4. logs:日志 ->
5. temp:临时文件
6. webapps:存放web项目的(自己写的java代码!!!)
7. work:存放_jsp,java  .class文件的

esc:退出焦点,继续执行

# 请求路径 #


## 模拟请求百度 ##
1. http://www.baidu.com:80/1.png
2. http:协议
3. www.baidu.com 主机名
4. 80 :端口号  从0到65535  2的16次方-1  0-1023系统占用(80留给http)
5. 1.png

## 请求自己的电脑 ##


## 端口号 ##
1. cmd  netstat -aon
2. pid : progress id 进程:正在运行的程序
3. 任务管理器 -> 查看 -> 选项列表 ->勾选pid


## 百度 ##
1. 本地找host文件,查找百度的ip :C:\Windows\System32\drivers\etc  ->#号注释
2. dns: domain  naming server 

----------

----------

# 发布自己的web项目 #
## my_web_app目录结构 ##

	webapps			 		 ：存放多个应用
		aProject(项目名称)  
		bProject
		cProject   :web2.5版本标准的目录结构
			|
			|----| html css js image等目录或者文件(静态web资源)
			|
			|----| META-INF	 ：存放一些meta information相关的文件
			|----| WEB-INF(特点:通过浏览器直接访问不到 目录)
			|	 	|
			|	 	|---| lib(项目的第三方jar包)
			|	 	|
			|	 	|---| classes(存放的是我们自定义的java文件生成的.class文件)
			|	 	|
			|	 	|--- web.xml(当前项目的核心配置文件)
			|	 	|
			|	 	|--- jsp 动态资源


## 手动创建工程 ##
### 方法1:将项目放到tomcat/webapps下 ###
1. 静态资源能访问
2. 动态资源不能访问到,,,曲线访问!!!

### 方法2:修改 tomcat/conf/server.xml ###
1. 在host标签下 
2. <Context path="/项目名" docBase="项目的磁盘目录"/>
3. path :/项目名称  -> 外界访问目录
4. docPath: C://aa   ->真正的项目路径
5. 重新启动

### 方法3 ###
1. 在tomcat/conf/引擎目录/主机目录下 新建一个xml文件
2. <Context docBase="G:\myweb"/>
					







----------

----------

## eclips发布web项目 ##

### eclipse 关联 tomcat ###
1. 查看server视图:菜单栏:window->show views->other->server

### 创建动态web项目 ###
1. dynamic web project


