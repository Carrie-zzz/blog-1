# 大纲 #
1. 安排
2. 回顾
3. 分页
4. 增查
5. 删
6. 改
7. 内省(Introspector)
8. beanutils


----------

----------

----------

# 课程安排 #
1. ajax   ,layui弹窗
2. 文件上传,图片服务器
3. filter  权限问题
4. 动态代理(乱码问题)

5. 数据库事务
6. 数据库连接池  dataSource
7. listener
8. 项目部署
9. 分组,svn协同开发


# 回顾 #
1. web三层
	* web
		* CommonServlet
		* jsp
	* service
		* 
	* dao
		* dbutil
2. 后台
	* 添加
	* 查询
	* 





----------

# 分页 #

## 上一页 ##
			    <li class="
						  <c:if test="${pages.pageIndex==1}">
						  disabled
						  </c:if>
			 			 ">
				      <a href="${ctx}/back/SysUserServlet?method=toListManagerUI&pageIndex=${pages.pageIndex-1}" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
			    </li>
## 下一页 ##
				<li class="
						<c:if test="${pages.pageIndex==pages.totalPage}">
						  disabled
						  </c:if>
			 			 ">
			      		<a href="${ctx}/back/SysUserServlet?method=toListManagerUI&pageIndex=${pages.pageIndex+1}" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>


----------


# 删除 #
1. 弹窗插件(ajax,以后再做)
2. 



----------

# 修改 #




----------

# 内省(Introspector) #
1. 反射的一个特列,只操作(反射)javaBean的属性
2. 作用
	* 封装属性的
3.bean属性
	* 
4. 访问方式 



## bean属性 ##
1. 普通java类,分装数据的
2. private String name ;只能算是一个字段field
3. 属性由get set 方法决定
4. 只要有就算一个属性
	public int getBB() {
		return 0;
	}

5. 属性
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBB() {
		return 0;
	}


## api ##
1. Introspector
	* 获取所有的属性:static BeanInfo getBeanInfo(Class<?> beanClass)
	* 获取bean中去掉stop的属性:static BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass)    
2. BeanInfo
	* PropertyDescriptor[] getPropertyDescriptors() 
3. PropertyDescriptor
	* 获取写(set):getWriteMethod
	* get:getReadMethod
	* 类型:getPropertyType





## BeanUtil ##
1. apache 开源项目
2. 下载
3. 使用
	1. 导包
		*   commons-beanutils-1.9.2.jar
		*   commons-logging-1.2.jar
	2. 基本类型
		* BeanUtils.setProperty 内部转换 8大基本数据类型
		* BeanUtils.populate    map注册
	3. 日期类型
		* 需要注册转换器
		* ConvertUtils.register(new DateLocaleConverter(), Date.class);



----------

----------







