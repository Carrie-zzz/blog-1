# ajax  #

## 介绍 ##
	https://baike.baidu.com/item/ajax/8425?fr=aladdin
	1. AJAX即“Asynchronous Javascript And XML”（异步JavaScript和XML），是指一种创建交互式网页应用的网页开发技术
	2. AJAX 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术
	3. 后台与服务器进行少量数据交换，AJAX 可以使网页实现异步更新。这意 味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。
	4. 核心对象XMLHTTPRequest
	5. 浏览器与 Web 服务器之间使用异步数据传输（HTTP 请求），这样就可使网页从服务器请求少量的信息，而不是整个页面


	1. 局部刷新技术
	2. http请求:浏览器->服务器
 

# xmlhttp 和 核心对象#
	*属性
		onreadystatechange
		readyState
			0 请求未初始化（在调用 open() 之前） 
			1 请求已提出（调用 open(),调用 send() 之前） 
			2 请求已发送（调用 send(),这里通常可以从响应得到内容头部） 
			3 请求处理中（响应中通常有部分数据可用，但是服务器还没有完成响应） 
			4 请求已完成（可以访问服务器响应并使用它）

			0 创建xmlhttp,或者重置xmlhttp
			1 调用open方法 
			2 调用send方法 
			3 部分响应 
			4 全部响应 (重点) 
		responseText
		status :状态码  200
	*方法
		open(method,url,isAsync)
		send(parameter)->post参数   
		setRequestHeader("content-type","form表单enctype属性");//设置post请求的参数的类型 必须放在send方法之前.


## 步骤 ##
1. 核心对象XMLHttpRequest xmlhttp
2. 回调函数-> xmlhttp.onreadystatechange= fn
3. 请求方法,请求参宿- xmlhttp.open(method,url);
4. 发送请求 ->xmlhttp.send();


# 案例:入门  #
1. 点击按钮发送ajax,请求servlet
2. 显示回送的信息

		xmlhttp = null;
		function sendAjax() {
			//alert("xx");  
			//1. 创建XMLHttpRequest 核心对象     xmlhttp = new xx();
			xmlhttp = null;    
			if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
				xmlhttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//2. 创建回调函数
			xmlhttp.onreadystatechange=function(){
				alert(xmlhttp.readyState);
			} 
			//3. 打开通道  open 设置 请求路径 和参数  open(method, url, async, username, password)
			xmlhttp.open("GET","${pageContext.request.contextPath}/HellowServlet");
			//4. 发送 xmlhttp.send
			xmlhttp.send();
		}





----------

# 案例:携带请求参数,响应参数 #
1. 点击请求servlet
2. get请求,打印响应参数
3. post请求,打印响应参数(需要设置;setRequestHeader("application/x-www-form-urlencoded"))

		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			// 获取浏览器数据
			String username = request.getParameter("username");
			username = new String(username.getBytes("iso-8859-1"), "utf-8");
			System.out.println(username);
	
			response.setContentType("text/html;charset=utf-8");
			// 回送给浏览器数据
			response.getWriter().append("get parameter 用户名重复了!! ")
					.append(request.getContextPath());
		}
	
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			System.out.println(request.getMethod());
			String username = request.getParameter("username");
			System.out.println(username);
	
			response.setContentType("text/html;charset=utf-8");
			// 回送给浏览器数据
			response.getWriter().append("post parameter 用户名重复了!! ")
					.append(request.getContextPath());
		}


## get ##
	xmlhttp.open("get","${pageContext.request.contextPath}/ParameterServlet?username=张三");
	* 参数乱码
		* 请求参数
			* username = new String(username.getBytes("iso-8859-1"),"utf-8");
		* 响应参数
			* response.setContentType("text/html;charset=utf-8");
	
## post ##
	xmlhttp.open("post","${pageContext.request.contextPath}/ParameterServlet");
	//post 需要请求头  ☆☆☆
	xmlhttp.setRequestHeader("content-type","application/x-www-form-urlencoded");
	//4. 发送请求 ->xmlhttp.send();  
	xmlhttp.send("username=张三");
	* 参数乱码
		* 请求参数
			* request.setCharacterEncoding("utf-8");
		* 响应参数
			* response.setContentType("text/html;charset=utf-8")


----------

# 案例 #
 添加人员的时候,判断用账号是否存在
 
1. 当账号框失去焦点的时候,发送ajax请求  
2. servlet->service->dao层 
3. 查询数据库,返回结果  1可以使用 0不可以使用
4. 界面显示  

1. html界面
		<form action="">
			<!-- 当账号框失去焦点的时候,发送ajax请求   -->
			用户名<input type="text" onblur="sendAjax(this)" /><span id="un"></span> <br/>
			密码<input type="password"/><br/>
			密码<input type="submit"  id="btnSubmit"/><br/>
		</form>
2. ajax
		xmlhttp = null;
		function sendAjax(dataUsername) {
			var v = dataUsername.value;
			//alert(v);
			//1. 核心对象XMLHttpRequest xmlhttp
			xmlhttp = null;  
			if (window.XMLHttpRequest) {// code for IE7, Firefox, Opera, etc.
				xmlhttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//2. 回调函数-> xmlhttp.onreadystatechange= fn
			xmlhttp.onreadystatechange = function() {
				//alert(xmlhttp.readyState);
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var result= xmlhttp.responseText;
					//alert(result);
					if(result==1){
						var domUn= document.getElementById("un");
						domUn.innerHTML="<font color='red'>被占用了</font>";
						//
						var btnSubmit= document.getElementById("btnSubmit");
						btnSubmit.disabled=true;
					}  
			}
			//3. 请求方法,请求参宿- xmlhttp.open(method,url);
			xmlhttp
					.open("post",
							"${pageContext.request.contextPath}/CheckUsernameServlet");
			
			//post 需要请求头
			xmlhttp.setRequestHeader("content-type","application/x-www-form-urlencoded");
			//4. 发送请求 ->xmlhttp.send();  
			xmlhttp.send("username="+v);
		}


----------

----------

----------

#  jquery  #
1. jquery 方式完成

## 方式 ##
	* 对象.load(url,data,function(date){..},type
		url:待装入 HTML 网页网址。
		data:发送至服务器的 key/value 数据。在jQuery 1.3中也可以接受一个字符串了。
		callback:载入成功时回调函数。

	* $.get(url,data,fn,type); 
		url:请求的路径
		data:请求的参数 参数为key\value的形式 key=value  {"":"","":""} (json格式)
		fn:回调函数 参数就是服务器发送回来的数据
		type:返回内容格式，xml, html, script, json, text, _default。    以后用"json"
	* $.post(url,prarm,fn,typ); 
		* var params ={"deptId":deptId};
	* $.ajax(url,[settings]);
		url:请求的路径  -> url:"http://,"
		settings:选项
			* type  请求参数
			* dataType   dataType:"json"
			* data 	  username=xx       {}
			* success 	success:function(){...}
			* error		error:function(){...}

## servlet ##
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getMethod());
		String username = request.getParameter("username");
		username = new String(username.getBytes("iso-8859-1"), "utf-8");
		System.out.println(username);

		response.setContentType("text/html;charset=utf-8");
		// 回送给浏览器数据
		response.getWriter().append("1");

	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println(request.getMethod());

		String username = request.getParameter("username");
		System.out.println(username);

		response.setContentType("text/html;charset=utf-8");
		// 回送给浏览器数据
		response.getWriter().append("1");
	}
## 界面 ##
	<input type="submit" value="点我"  id="
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>

## load ##
1. 自动更具参数,使用get或者post
2. 参数
	* get : var params = "username=张三"
	* post: var params = {"username":"张三"};  post不会乱码

	xmlhttp = null;
	$(function () {
		$("#btn").click(function(){
			//alert("jq");
			var url = "${pageContext.request.contextPath }/JqueryServlet";
			//var params = "username=张三";  //get
			var params = {"username":"张三"}; //post
			$(this).load(url,params,function(result){
				alert(result);
			});
		});
	});

## $.get ##
1. $.get(url,data,fn,type);
	$("#btn").click(function(){
		var url = "${pageContext.request.contextPath }/JqueryServlet";
		var params = {"username":"张三"};
		$.get(url,params,function(result){
			alert(result);
		},"text");
	});


## $.post ##
1. $.post(url,data,fn,type);
	$("#btn").click(function(){
		var url = "${pageContext.request.contextPath }/JqueryServlet";
		var params = {"username":"张三"};
		$.post(url,params,function(result){
			alert(result);
		},"text");
	});


## $.ajax ##
1. $.ajax(url,[settings]);

	xmlhttp = null;
	$(function () {
		$("#btn").click(function(){
			var urlPath = "${pageContext.request.contextPath }/JqueryServlet";
			//var params = "username=张三";
			var params = {"username":"张三"};
			$.ajax({
				url:urlPath,
				type:"get",
				data:params,
				success:function(d){
					alert(d);
				},
				error:function(){
					alert(2);
				},
				dataType:"text"
			});
		}); 
	});


----------

# 案例 #
 添加人员的时候,判断用账号是否存在
1. 当账号框失去焦点的时候,发送ajax请求  (将ajax->jquery)
2. servlet->service->dao层 
3. 查询数据库,返回结果  1可以使用 0不可以使用
4. 界面显示  

	xmlhttp = null;
	$(function(){
		$("input[name='username']").blur(function(){
			var v = $(this).val();
			alert(v);
			
			var url ="${pageContext.request.contextPath}/CheckUsernameServlet";
			$.get(url,"username="+v,function(d){
				if(d==1){
					var $un =$("#un");
					$un.html("<font color='red'>被占用了</font>");
					//
					$("#btnSubmit").attr("disabled",true);
				}  
			});
		})
	});





----------

----------

----------
# 案例:牛逼一下 #

1. 界面
	<script src="../js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			$("#keyword").keyup(function(){
				alert(this.value);
				var url ="";
				var param = "keyword="+this.value;
				$.get(url,param,function(d){
					alert(d);
				},"text");
			});
		});
	</script>
	<body>
		<center>
			<div>
				<span style="color: red; font-size: 30px;">百度一下</span>
			</div>
			<div>
				<input type="text" name="keyword" id="keyword" value="" /> <input
					type="submit" value="牛逼一下" />
			</div>
			<div id="sId"
				style="display:none; border: 1px red solid;  width: 161px; position: relative; left: -38px;"></div>
		</center>
	</body>

2. servlet
	response.getWriter().append("a,b,c,d,e,f")
				.append(request.getContextPath());


3. ajax
	$(function() {
		$("#sId").hide();
		$("#keyword").keyup(function() {
			$("#sId").html(""); //每次清空
			//alert(this.value);
			var url = "${pageContext.request.contextPath}/SearchServlet";
			var params = "username=" + this.value;
			$.get(url, params, function(d) {
				if(d!="")
				{
					var a=d.split(",");
					$(a).each(function(){
						$("#sId").append("<div>"+this+" <div>");
					});
					$("#sId").show();//显示搜索栏
				}
			}, "text");
		});
	});


----------

----------

----------
# json #
	https://baike.baidu.com/item/JSON/2462549?fr=aladdin
	[http://json.org/](http://json.org/ "官网")
1. JSON(JavaScript Object Notation, JS 对象标记) 
2. 是一种轻量级的数据交换格式。
3. 它基于 ECMAScript (w3c制定的js规范)的一个子集->js中能直接使用
4. 键:值对   


## 格式 ##
1. 对象 {}   {"key1":值,"key1":值,}  "key1":值  e1  e2
2. 数组 [e,e1,e2]   e可以是 对象,可以是数组


	对象表示为键值对  "key":value
	数据由逗号分隔	"key":value,"key2":value
	花括号保存对象    {"key":value}
	方括号保存数组    [{"key":value},{"key2":value2}]
	key 永远字符串   ""


## fastjson ##
1. 阿里开源项目
2. 功能强大,无依赖，不需要例外额外的jar，能够直接跑在JDK上。
	http://blog.csdn.net/zml_2015/article/details/52165317
3. 功能
	1. javaBean 转 json
	2. json		转 javaBean
4. 下载jar https://github.com/alibaba/fastjson
5. 方法
	1. String toJSONString(obj)
		* javaBean/List 转 json			
		* String s = JSON.toJSONString(student);
	2. parseObject 
		* json	转 javaBean		
		* Student student = JSON.parseObject(jsonStr,Student.class);
	3. 同一个引用,不做第二次转换,
		* 问题:同一引用不转换
				Dept dept = new Dept();
				dept.setId(1);
				ArrayList<Object> arrayList = new ArrayList<>();
				arrayList.add(dept);
				arrayList.add(dept); //统一引用
				String jsonString = JSON.toJSONString(arrayList);
				System.out.println(jsonString);//[{"id":1,"status":1},{"$ref":"$[0]"}]
			* 禁用
					String jsonString = JSON.toJSONString(arrayList,
					SerializerFeature.DisableCircularReferenceDetect);
					//[{"id":1,"status":1},{"id":1,"status":1}]
		* 新问题,死循环
				人(身份证)	身份证(人)  相互引用 -> stackOverflowError
			* 禁止某个字段转换json
				* @JSONField(serialize=false)
			
# json测试 #
	System.out.println(request.getMethod());
	String username = request.getParameter("username");
	username = new String(username.getBytes("iso-8859-1"), "utf-8");//get
	System.out.println(username);
	// 中文乱码
	response.setContentType("text/html;charset=utf-8");
	// 回送给浏览器数据
	String result = "{result:\"失败\",status:200}";
	response.getWriter().append(result);