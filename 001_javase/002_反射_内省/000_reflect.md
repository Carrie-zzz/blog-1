# 一个servlet中实现crud #
		response.setContentType("text/html;charset=utf-8");
		// 交给别人
		String method = request.getParameter("method");
		if ("add".equals(method)) {
			add(request, response);
		} else if ("delete".equals(method)) {
			delete(request, response);
		} else if ("update".equals(method)) {
			update(request, response);
		} else if ("select".equals(method)) {
			select(request, response);
		}



 

----------

----------
# 反射 #
1. 加载类
2. 解刨类的各个部分:字段,构造方法,方法



# 加载类class->内存中的字节码文件 #

## 获取字节码 ##
1. Class.forName("完整名称")
2. new User().getClass;
3. User.class; 




----------

# 构造方法Constructor #
1. public:
2. private:
		
## 方法 ##




## 共有 ##
1. 无参
2. 有参 

## 私有 ##
1. constructors.setAccessible(true);

## class ##
1. clazz.newInstance();// 调用无参构造方法!!!


----------

# 方法Method #


1, 没有参数的方法
2. 有参数的方法
3. 有参有换回值
4. 私有的方法
5. 静态的
6. main !!!



## 方法 ##

----------

# 字段Field #
1. 共有
2. 私有
3. 静态
4. 获取类型,设置

## 方法 ##



----------

----------
# servlet设计 #





----------

----------
