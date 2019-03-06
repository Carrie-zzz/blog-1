# jsp #
1. java server pages(java服务器页面)
2. 动态web技术
3. JSP本质就是servlet的子类httpservlet的子类
4. jsp:在html中嵌套java代码
5. jsp= html+java脚本代码+标签
6. tomcat转换到work文件夹中,jsp->httpservlet子类.java文件


F:\develpment\web\huaxin\tomcat\apache-tomcat-7.0.75\work\Catalina\localhost


# jsp->httpservlet #
1. init
2. service
3. destroy


# 运行过程 #
1. 第一次访问的时候,tomcat会自动把xx.jsp文件转成 xx_jsp.java
2.  xx_jsp.java(servlet)-> xx_jsp.class 
3.  核心方法_jspService


# 格式 #
1. <% ... %>

# jsp脚本 #
## 一:代码片段 ##
1. 格式 <% java一行行代码;  %> 
2. 原封不动的拷贝到servlet方法中
3. 可以有多个脚本片段，相互可以访问(顺序)
4. 可以使用内置对象

## 二:输出表达式 ##
1. <%=xx %> 
2. 不能有封号
3. 不能多行输出
4. 转换成->out.print(xx); 

## 三:全局声明 ##
1. <%!   属性或者方法  %>
2. 原封不动的拷贝到service外面
3. 不能使用内置对象
4. 基本用不上

## 四:注释 ##
1. <%-- --%>
2. 不会生成到java文件中.
3. html注释 <!-- -->,会发送给浏览器


----------

----------
# 会话技术 #
1. 客户端打开浏览器,到关闭浏览器叫做一次会话
2. 客户端域浏览器交互的过程中,各种产生数据
3. http协议是一个无状态的协议
4. Cookie,Session

## 模拟点餐 ##
1. 服务器我需要菜单
2. 客户端请自己记着菜单
3. 服务器我要10块钱的炸酱面
4. 客户端收到了,生成编号s001=炸酱面,请拿好你的编号
5. 服务器我的面呢?
6. 客户端啥面?你的编号带了吗?
7. 带了
8. 好了上面了~~~自己拿!




# Cookie #
1. Cookie	：客户端会话保存技术
2. 由服务器生成,通过response将cookie写回浏览器(set-cookie),保留在浏览器上
3. 每次访问浏览器根据一定的规则携带不同的cookie(通过request的头 cookie),我们服务器就可以接受cookie





## 产生过程(订餐过程) ##
1. 服务器创建cookie:  c=new Cookie("key","value");
2. 服务器response添加cookie: response.addCookie(Cookie c);;;通过set-cookie头,携带给浏览器 (订单)
3. 浏览器通cookie头,携带到服务器
4. 服务器通过request获取: Cookie[] request.getCookies();
5. 想要获取自己的cookie:通过c.getName():进行名字匹配



# 过程测试 #

# 清空 #
1. ctrl+ shift + del


# cookie 上次访问时间案例 #


# 方法测试 #
## cookie常用方法 ##
1. getName():获取cookie的key(名称)
2. getValue:获取指定cookie的值
3. setPath("xx"); 设置携带路径
4. setMaxAge(int);//多少秒~ 0 删除(路径一致)  


### 路径 ###
1. 当我们访问的路径中包含此cookie的path,则携带
2. 设置:/项目名字 开始,,,/结束

### age ###
1. -1默认浏览器同步
2. 0 删除(路径一致)  
3. n秒


# 保存用户名案例 #



# 浏览记录案例 #







# Session #
1. Session	：服务端会话保存技术
2. 银行卡去查询账户余额,不能存在客户端!!!秒变高富帅
3. 怎么保存数据->域对象
4. Session通过cookie的机制


## session域 ##
1. 创建:第一次:request.getSession()
2. 销毁:
3. 使用:xxxAttribute


### 销毁 ###
1. 服务器关闭
2. 超时(默认:30分钟 tomcat: web.xml有配置,时:手动配置时间setMaxInactiveInterval )
3. 手动:session.invalidate(); //注销功能!!!



# 方法 #

# 登入验证码,案例 #



# 登入注销 #





# 购物车案例 #


# 清空购物车 #

