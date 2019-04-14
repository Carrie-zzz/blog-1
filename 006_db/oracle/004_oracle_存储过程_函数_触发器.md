# 存储过程 ,存储函数 #
1. 存储在数据库中提供所有用户程序调用的**子程序**叫存储过程或存储函数
2. 存储函数有返回值 return


# 存储过程 procedure #
1. 语法
```
create [or replace ] procedure 过程名(变量名 in|out 类型 , ...)  //参数列表
is或者as
-- 定义变量

-- plsql子程序体
begin
	...
	
end  过程名;
```

2. 调用存储过程
	1. exec 过程名(参数列表) ;
	2. 在另外plsql程序调用
```
begin
	过程名(参数列表) ;
end;
```

```

create or replace raiseSalary(eno in number)
as
	psal emp.sal%type;
begin
	select sal into psal from emp where empno=eno;
	
	-- 一般不在存储过程或存储函数 提交事务,谁调用谁提交事务
	...
end;
/  -- / 有的tool需要/,有的不需要/

```

----------
# 存储函数 function #
1. 介绍
	1. 函数function 为一个命名的存储程序,可以有参数,并返回一个计算值.
	2. 和存储过程结构类似,必须有return
	3. 函数说明 指定幻术名字,返回结果值类的类型,以及参数列表
2. 创建语法
```
create or replace function 函数名(参数列表)
return 函数值类型
as
-- plsql子程序体
begin
	...
	
end  过程名;
```

```
	create or replace queryEmoIncome(empno in number)
	return number
	as
		psal emp.sal%type;
		pcomm emp.comm%type;
	begin
		select sal,comm into psal,pcomm from emp where no=empno
		return psal*12+nvl(pcomm,0);
	end;
```
3. 调用
```
	begin
		-- :变量名  **绑定变量**,可以不用事先申明 变量和变量类型
		-- eno=>:eno  等价于 eno=>123  等价于 eno:=123  等价于 (123)
		:result :=queryEmoIncome(eno=>:eno)
	end;
```


----------
# in 和 out #
1. 一般来讲,过程和函数的区别在于 函数可以有一个返回值,过程没有返回值
2. 过程和函数 都可以通过 out指定一个或者多个输出参数, 实现返回多个值
3. 使用原则
	1. 如果只有一个返回结果 ,使用存储函数
	2. 如果有多个返回结果,使用存储过程

```
	create or replace procedure queryInfo(eno in number, 
										pename out varchar2,
										psal out number
					)
	as
	begin
		select ename,sal into pname,psal from emp where empno=eno;
	end;

```

```
	begin
		queryInfo(en=>7839,pename=>:pename,psal=>:psal)
	end;
```

----------

# 包结构 #
1. 包头和包体

## 包头 负责申明 ##
1. 创建
```
	create or replace package myPackage as
		-- 自定义类型
		type empCursor is ref cursor;
		procedure queryList(dno in number , empList out empCursor);
	end myPackage
```

## 包体 负责实现 ##
1. 创建
```
	create or replace package body mypackage as
		procedure queryList(dno in number , empList out empCursor) as
		begin
			open empList for select * from emp where deptno=dno;
			-- 
		end queryList;
	end mypackage;
```

# java调用 #
1. 存储过程
```
	String sql ="{call queryIn(?,?,?)}";
	Connection stmt = JdbcUtil.getConn();
	CallableStatement call = conn.prepareCall(sql);
	// 对于 in 需要赋值 ,对out参数只要申明
	call.setInt(1,123); //第一个?参数
	//
	call.registerOutParameter(2,OracleTypes.VARCHAR);
	call.registerOutParameter(3,OracleTypes.NUMBER);

	call.execute();
	call.getString(2);
	call.getDouble(3);
```
2. 存储函数
```
	String sql ="{?=call querySalByNo(?)}";

	CallableStatement call = conn.prepareCall(sql);
	call.registerOutParameter(1,OracleTypes.NUMBER);
	call.setInt(2,123); //第一个?参数
	//
	call.execute();
	call.getDouble(1);
```

3. 调用包
```
	//open empList for select * from emp where deptno=dno;
	String sql ="{call MYPACKAGE.queryList(?,?)}";
	CallableStatement call = conn.prepareCall(sql);
	call.setInt(1,123);
	call.registerOutParameter(2,OracleTypes.CURSOR);

	call.execute();
	ResultSet rs = ((OracleCallableStatement)call).getCursor(2);

	
```


