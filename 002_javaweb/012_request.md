# 回顾response #


# request #
1. 浏览器->服务器
2. 浏览器发送过来的数据

## 操作 ##
1. 请求行 : 格式:请求方式	访问的资源 协议/版本
2. 请求头 : 格式:key:value1,value2
3. 空行
4. 请求体 : 只有post有(请求参数)



# 服务器获取请求方法 #
## 行: GET http://localhost/web1/1.html http/1.1##
1. getMethod()  
2. getRequestURL()  url 
3. getProtocol()
4. getRemoteAddr()
5. getContextPath() 重点:项目路径

## 头 ##
格式:key:value
1. getHeader("name"); 
2. getIntHeader
3. getDateHeader

### 重要头 ###
|	key	 |	value	| 作用 |
| -----------| :-----:  | :--------: |
| User-Agent | Chrome   	| 客户端设备 |
| Referer | url   	| 防盗链 |
| Cookie | ..   	| .. | 



## 体(参数) ##
username=root&passwor123&aihao=zuqiu&aihao=lanqiu

1. getParameter("key")
2. getParameterValues("key")
3. getParameterMap();->Map<String, String[]>



# 登入:请求中文乱码 #
## get乱码 ##
1. 浏览器utf-8码表
2. tomcat:iso-8859-1码表
3. 编码,解码


## post乱码 ##
参数是放在请求体中,服务器获取请求体的时候使用iso-8859-1解码,也会出现乱码

## 中文_文件下载 ##



	
	中文文件在不同的浏览器中编码方式不同：
	IE			：URL编码
	Firefox		:Base64编码
	
	if(agent.contains("Firefox")){
				// 火狐浏览器
				filename = base64EncodeFileName(filename);
			}else{
				// IE，其他浏览器
				filename = URLEncoder.encode(filename, "UTF-8");
			}
	
	public static String base64EncodeFileName(String fileName) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			try {
				return "=?UTF-8?B?"
						+ new String(base64Encoder.encode(fileName
								.getBytes("UTF-8"))) + "?=";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}


# 跳转(请求转发) #
		request.getRequestDispatcher("/Servlet06_Request_forward2")
				.forward(request, response);

		request.getRequestDispatcher("/WEB-INF/xx.html").forward(request,
				response);



# request域 #
1. 创建
2. 销毁
3. 存一次请求数据




# 302重定向和转发的区别 #

|	描述		 |	302重定向	|转发 |
| -----------| :-----:  | :----: |
| 请求次数     | 2 		| 1     |
| 浏览器url  | 改变   	|   不变   |
| request | 不能重用    	|   可以   |
| 站外 		| 能    	|   不能   |
| 对象 | response    	|  request   |




# 练习 #
1. 注册界面
2. 提交表单
3. 获取提交信息->封装到User类里面
4. 返回结果2种情况
5. 成功 转发到内部的 Web-Inf/success.html
6. 失败 转发到内部的 Web-Inf/fail.html





