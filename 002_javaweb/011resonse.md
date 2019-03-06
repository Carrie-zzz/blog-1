# 回顾 #
servlet

## ServltConfig ##
1. 在每一个servlet里面获取配置信息 :getInitParameter(String name) 
2. 获取整个应用app :getServletContext() 



## ServletContext ##
1. context 上下文 代表整个项目

### 功能: ###
1. 获取全局的初始化参数 :getInitParameter(String name) 
2. application**域**:创建,销毁的,怎么调用 xxxAttribute get set remove
3. 获取文件资源路径: getRealpath("/aa.txt");
4.  getMimeType();   .jpg -> image/jpeg



## servlet ##
1. 实现servlet接口:5大方法:service
2. GenericServlet implements Service,ServletConfig:service , servletConfig
3. HttpServlet : doGet doPost 

### web.xml配置servlet外界访问 ###
1. servlet :托付给服务器创建,销毁
2. servlet-mapping :曲线的给外键访问

### 生命周期 life circle ###
1. 创建,销毁的,怎么调用


----------


# request & response(会使用) #

# response (响应) #
1. 服务器->浏览器
2. 网浏览器写东西

## 操作 ##
1. 响应行 : 格式->协议/版本 状态码 状态码说明 :http/1.1 200 ok
2. 响应头 : 格式:key:value1,value2
3. 空行   :
4. 响应体 :

# 方法 #
## 响应行:状态码方法 ##

		状态码:
			1xx:已发送请求
			2xx:已完成响应
				200:正常响应
			3xx:还需浏览器进一步操作
				302:重定向 配合响应头:location
				304:读缓存
			4xx:用户操作错误
				404:用户操作错误.
				405:访问的方法不存在
			5xx:服务器错误
				500:内部异常
1. setStatus(int sc)  : 1xx 2xx 3xx :302
2. sendError(int sc, String msg) :404

404案例 ,302案例

## 响应头方法 ##
1. setHeader(String name, String value) ->如果有,替代
2. addHeader(String name, String value) ->如果有,后面追加

1. refresh 5,www.baiud.com  
2. 302 Location
3. content-type:mime类型
4. content-disposition:文件下载

|	key		 |	value	| 解释 |
| -----------| :-----:  | :----: |
|Refresh | 1;url=www   	|   定时刷新 |
| Location   | www.baidu.com | 跳转方向 和302一起使用的   |
| Content-Type |  text/html; charset=utf-8 	|  数据类型 |
|Content-Disposition | attachment;filename=aaa.zip   |   下载 |


## 体方法 ##
1. getOutputStream
2. getWriter ->字符串乱码
3. 在一次请求中,2个流互斥
4. 服务器自动关流




----------

----------

# 案例 #

# 行->状态码 #
1. sendError  404
2. setStatus  302

# 头-> 定时刷新:refresh #

登入失败 3秒跳转案例(定时刷新)
1. 常见的响应头-refresh格式:
		refresh:秒数;url=跳转的路径
2. 设置响应头:
		resp.setHeader(String key,String value);设置字符串形式的响应头
		resp.addHeader(String key,String value);追加响应头, 若之前设置设置过这个头,则追加;若没有设置过,则设置
3. 设置定时刷新:
		response.setHeader("refresh","3;url=/Web001/login.htm");

4. <meta http-equiv="refresh" content="3;index.html"/>
				<html>
					<head>
						<meta charset="UTF-8">
						<title></title>
						<meta http-equiv="refresh" content="3;index.html"/>
					</head>
					<body>
						<span id="d">
							3
						</span>后跳转
					</body>
					
					<script type="text/javascript">
						onload = function(){
							setInterval(function(){
								var d= document.getElementById("d");
								d.innerHTML = d.innerHTML-1;
							},1000);
						}
					</script>
				</html>
# 头->重定向 #
## 码+头 ##
	 resp.setStatus(302);
	resp.addHeader("location", "/WebTest/Servlet03_Location2");
## 头 ##
	resp.sendRedirect("/WebTest/Servlet03_Location2");


# 体->乱码 #
		// 1. 告诉浏览器一声 :有些浏览器不行
		// response.setCharacterEncoding("utf-8");
		// 2. 告诉服务器 告诉浏览器
		// response.setHeader("content-type", "text/html;charset=utf-8");
		// 3.常用
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("<a href='#'>今天很不错啊</a>");



## 头->文件下载案例 ##
1. 超链接下载: 通过a标签连接下,浏览器能够解析的统统都是展示
2. servlet下载

### 步骤 ###
1. 告诉浏览器 mimeType
2. 设置下载头
3. 文件路径,文件输入流
4. 浏览器输出流


# 体验证码案例 #
1. 创建缓存图片
2. 画笔
3. 背景颜色
4. 字母
5.  

		// 1. 动态生成一张图片,放回给浏览器
		// 2. 创建一个张图片
		int width = 100;
		int height = 30;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 3. 获取这个图片的画笔
		Graphics graphics = image.getGraphics();
		// 4. 画一个实行矩形框 背景黑色的
		graphics.setColor(new Color(200, 240, 200));
		graphics.fillRect(0, 0, width, height);
		// 5. 画验证码 , 4个字母组成
		Random random = new Random();

		graphics.setColor(Color.red);
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int nextInt = random.nextInt(string.length()); // [0,length);
			char charAt = string.charAt(nextInt);// q
			sBuffer.append(charAt + "");
			graphics.drawString(charAt + "", width / 4 * i + 5, 20);
		}
		// 存放session
		HttpSession session = request.getSession();
		session.setAttribute("validateCodeSession", sBuffer.toString());

		// 6. 发送给浏览器
		ImageIO.write(image, "png", response.getOutputStream());
