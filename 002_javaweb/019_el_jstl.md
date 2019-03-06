# EL表达式(Expression Language) #
1. 替代jsp找元素(常用web对象)
2. 替代jsp找元素的值
3. 替代jsp运算
4. 替代jsp中的,输出表达式<%= %>

		替代部分java代码


# 域中数据(重点) #
1. 格式: ${}

## 简单--四个域查找 ##

### 方法一 ###
1. ${xxScope.属性key}->如果没有不会返回null,而是""

		${pageScope|requestScope|sessionScope|applicationScope.属性名}

### 方法二 ###
1. ${属性key}
2. 从小域到大域依次查找属性
3. 顺序:pagecontext,request,session,application四个域
4. 若查找到了返回值,且结束该次查找;若查找不到,返回一个""
	pageContext.findAttribute("name")	 



## 复杂 ##
1. 数组:${名[index]}
2. list:${名[index]}
3. map:${名.key}
4. 特殊( user.name):若属性名中出现了"."|"+"|"-"等特殊符号,需要使用scope获取-> ${requestScope["user.name"] }




----------


# javaBean #
定义:
1. public 
2. 序列化接口
3. 字段都私有
4. 无参构造方法
5. 提供get set is 方法属性

提取方式:
${域中javabean名称.bean属性}



----------

# el运算 #

## 算数运算:+ ##
加法:+号 只能进行数字相加,不能拼接

## 逻辑元素:== > <  ##

## 三元运算 ##

## empty:函数 ##
1. 判断集合,对象是否null或者集合的长度0 是true 否 false
2. ${ empty lists } 或者 ${ empty(lists) }




----------

# el:11隐式对象 #
1. 四大域对象:pageScope/requestScope/sessionScope/applicationScope
2. 初始化参数:initParam
3. 请求头:header,headerValues
4. 请求参数:param ,paramvalues
5. 和jsp九大隐私对象关联:pageContext
6. cookie
		除了pagecontext其余对象获取的全是map集合
	
## 请求参数 param ##


## 请求头 ##


## 全局初始化参数 ##
1. ServletConfig

## cookie对象 ##


## pageContext  ##
1.动态获取项目名称: ${pageContext.request.contextPath}






----------

----------
# jstl #
1. apache  标签库语言
2. jsp 标准标签库语言:代替<% ... %> ,,,业务逻辑  

## 步骤 ##
1. 项目导入jar :jstl.jar和standard.jar
2. jsp页面指令导入:<%@taglib prefix="" uri=""%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   //核心类库

## 分类 ##
1. core 核心类库
2. fmt 格式化|国际化
3. function 函数:很少用



# core(重要) #

## 判断 c:if ##
<c:if test="${el表达式}"></c:if>

## 循环 c:forEach##
1. begin
2. end
3. var
4. step
5. varStatus : vs.count当前循环第几次(1开始)  vs.current 当前迭代项目(item)



## c:choose ##
	<c:choose>
		<c:when test="${num==1 }">
			周一
		</c:when>
		<c:when test="${num==2 }">
			周二
		</c:when>
		<c:when test="${num==3 }">
			周三
		</c:when>
		<c:otherwise>
			其他~~
		</c:otherwise>
	</c:choose>


## c:set ##
1. 域中设置值,bean设置值,map设置值
2. var
3. value
4. scope
5. target


### 设置map ###

### 设置bean ###


### 域设置 ###

#### 项目名 ####




----------

# 案例:后台用户列表 #








