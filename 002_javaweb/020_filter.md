# a 标签跳转属性  #
	<a href="../jsp/loginout.jsp" target="">退出</a>
	target=后可以有几个参数 
	例如target=_blank target=_parent target=_search target=_self target=_top 
	blank 浏览器会另开一个新窗口显示链接 
	
	_self，在同一框架或窗口中打开所链接的文档。此参数为默认值，通常不用指定。 
	
	_parent，将链接的文件载入含有该链接框架的父框架集或父窗口中。如果含有该链接的框架不是嵌套的，则在浏览器全屏窗口中载入链接的文件，就象_self参数一样。 
	
	_top，在当前的整个浏览器窗口中打开所链接的文档，因而会删除所有框架 
	
	_search 在浏览器的搜索区装载文档，注意，这个功能只在Internet Explorer 5 或者更高版本中适用。


----------

# filter:过滤器 #
	1. servlet表兄弟,filter
	2. servlet接受请求和响应
	2. filter过滤请求和响应

	画一个流程图 
		* 请求:http->filter->servlet
		* 响应:

## 作用 ##
		自动登录.
		统一编码.
		过滤关键字

## 步骤 ##
	1. 编写一个类,实现filter接口,重写方法
	2. web.xml中注册filter
	3. web.xml中绑定路径


## 方法 ##
	1. init(FilterConfig config):初始化操作
	2. doFilter(ServletRequest request, ServletResponse response, FilterChain chain):处理业务逻辑
	3. destroy() :销毁操作
## chain ##
1. 通过chain的dofilter方法,可以将请求放行到下一个过滤器,直到最后一个过滤器放行才可以访问到servlet|jsp


## FilterConfig ##	
1. 初始化参数


## 生命周期 ##
	1. filter单实例多线程
	2. filter在服务器启动的时候 服务器创建filter 调用init方法 实现初始化操作(和servlet一样可以给初始化参数)
	3. 请求来的时候,创建一个线程 根据路径调用dofilter 执行业务逻辑
	4. 当filter被移除的时候或者服务器正常关闭的时候 调用destory方法 执行销毁操作.




## 映射路径 ##
1. 完全匹配: a/b/cServlet
2. 目录匹配:a/b/*
3. 后缀匹配: *.do  , *.jsp  , 

### 多个匹配成功 ###
1. 顺序:按照web.xml中filter-mapping的顺序执行



## dispatcher:请求 ##
1. REQUEST:从浏览器发送过来的请求(默认)
2. FORWARD:转发过来的请求
3. ERROR:因服务器错误而发送过来的请求
4. INCLUDE:包含过来的请求


----------

----------


# 自动登入案例 #





----------

# 统一编码 #
	1. 以前乱码
		1. 获取参数方法
			* String getParameter(String name);
			* String[] getParameterValues(String name);
			* Map<String,String[]> getParameterMap();
		2. post乱码,只需要设置一句话->
				request.setCharacterEncoding("utf-8");
		3. get乱码,编码->解码
			new String(value.getBytes("iso8859-1"),"utf-8");
	2. filter解决
		1. 位置:最前面,路径: /*   
		2. CharacterEncodingFilter
		3. 在filter中重写getParameter(加强)
		4. 方式
			1.继承(获取构造器)
			2.装饰者模式(静态代理)
				* 要点
					1. 要求装饰者和被装饰者实现同一个接口或者继承同一个类
					2. 装饰者中要有被装饰者的引用
					3. 对需要加强方法进行加强
					4. 对不需要加强的方法调用原来的方法即可
				* 步骤
					1. 编写类, 继承 HttpServletRequestWrapper
					2. 构造方法传递被包装对象
					3. 重写getParameter(..) 方法
						* String getParameter(String name);// arr[0]
						* String[] getParameterValues(String name);// map.get(name)
						* Map<String,String[]> getParameterMap();
					4. 如果是post
					5. 如果是get
			3.动态代理

		


----------
	class MyRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request;
		private boolean flag=true;
		
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request=request;
		}
		
		@Override
		public String getParameter(String name) {  
			if(name==null || name.trim().length()==0){
				return null;
			}
			String[] values = getParameterValues(name);
			if(values==null || values.length==0){
				return null;
			}
			
			return values[0];
		}
		
		@Override
		/**
		 * hobby=[eat,drink]
		 */
		public String[] getParameterValues(String name) {
			if(name==null || name.trim().length()==0){
				return null;
			}
			Map<String, String[]> map = getParameterMap();
			if(map==null || map.size()==0){
				return null;
			}
			
			return map.get(name);
		}
		
		@Override
		/**
		 * map{ username=[tom],password=[123],hobby=[eat,drink]}
		 */
		public Map<String,String[]> getParameterMap() {  
			
			/**
			 * 首先判断请求方式
			 * 若为post  request.setchar...(utf-8)
			 * 若为get 将map中的值遍历编码就可以了
			 */
			String method = request.getMethod();
			if("post".equalsIgnoreCase(method)){
				try {
					request.setCharacterEncoding("utf-8");
					return request.getParameterMap();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if("get".equalsIgnoreCase(method)){
				Map<String,String[]> map = request.getParameterMap();
				if(flag){
					for (String key:map.keySet()) {
						String[] arr = map.get(key);
						//继续遍历数组
						for(int i=0;i<arr.length;i++){
							//编码
							try {
								arr[i]=new String(arr[i].getBytes("iso8859-1"),"utf-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
					flag=false;
				}
				//需要遍历map 修改value的每一个数据的编码
				
				return map;
			}
			
			return super.getParameterMap();
		}
		
	}