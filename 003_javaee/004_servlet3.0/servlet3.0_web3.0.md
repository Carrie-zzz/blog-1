# 3.0版本 #
1. jdk1.7以上
2. 注解开发,没有web.xml文件
3. 内嵌了文件上传功能
	* 不需要		
		* servlet3.0
		* commons-fileupload

# 特性 #
1. servlet
	* url , initParam , loadonstartup
	* 路径 / 开头
	1. @WebServlet(urlPatterns="/s1")
	2. @WebServlet(urlPatterns = { "/s1", "/s2" },loadOnStartup=2)
	3. value[] 和  urlPatterns[] 等价
2. filter
	* 如果多个filter,拦截,顺序:更具字段排序 
		@WebFilter(urlPatterns = "/*")
		public class TestFilter implements Filter 
3. listener
		@WebListener
		public class TestListener implements ServletContextListener

----------

# 文件上传 #
1. 浏览器:post提交 ,
2. 表单input type='file',enctype=multipart/form-data
	* 如果不写,,,提交只会是文件名字
	* 配置完后 request.getParameter("username"); 失效 ,,返回null
			<%-- <form action="${pageContext.request.contextPath }/x" method="post" > --%>
			<form action="${pageContext.request.contextPath }/x" method="post" enctype="multipart/form-data">
				账号<input type="text" name="username"/>  <br/>
				头像<input type="file" name="pic"/><br/>   
				
				<input type="submit"/><br/>
			</form>

3. servlet3.0中支持文件上传:
	1. 在servlet上面添加注解
		* @MultipartConfig
		* 添加后 
			* request.getParameter("username"); 普通组件字段生效,存在乱码问题
			* request.getParameter("pic");  文件失效
			* request.getPart("xx");  文件上传组件生效,乱码已经解决
			
	2. 文件参数request.getPart("xx");
		* part.getName()
			* 界面上,input 文件  name的值 
		* 获取文件名
			// Content-Disposition: form-data; name="pic"; filename="xx.txt"
			String header = part.getHeader("Content-Disposition");
			// form-data; name="pic";filename="xx.txt"
			System.out.println("获取值:" + header);//
			String replace = header.substring(header.lastIndexOf(";") + 11)
					.replace("\"", "");
			System.out.println("文件名:" + replace);

4. 文件上传中问题
	1.	同名文件将被覆盖
		* 随机名称
			uuid
			时间戳
	2. 文件目录,一个目录太多文件,会卡
		* 日期
		* 用户
		* 文件个数
		* 随机目录
			mkdirs