# PL/SQL #
1. PL/SQL( Procedure Language/Sql)  过程语言
2. 在sql命令语言中增加了过程处理语言(分支/循环),使sql语句哟过程处理能力

1. 打开输出开关 set serveroutput on
2. /   执行上一行sql或者pl/sql
3. 

# 基本语法 #
1. 运算符
	1. :=	等价于=
	2. =	等价于==
```
declare 
-- 说明部分(变量说明,游标申明,例外说明)
begin 
	--程序 DML
	dbms_output.put_line('x');
exception
    --例外处理语句
end;
/
```
2. 变量和常量说明 char varchar2 date number boolean long
	1. 说明变量名 类型(长度) ; 
	2. 引用型变量名 引用表.引用表字段%type;
	3. 记录标记变量名 表 ,代表一行数据

```
v1 char(15);			//变量名 类型(长度) ; 
v2 constant char(15);	//常量
married boolean :=true;	//初始值
my_name emp.ename%type;	//引用变量名 引用表.引用表字段%type;
emp_rec emp%rowtype;	//记录标记变量名 表  ,代表一行数据
```
```
declare
	pname emp.name%type;
	psal  emp.sal%type;
	emp_rec emp%rowtype;
begin
	select name,sal from emp where id=1;
	select name,sal into pname,psal  from emp where id=1;
	dbms_output.put_line(pname||'薪水是'||psal);

	select * into emp_rec  from emp where id=1;
	dbms_output.put_line(emp_rec.name);
end;

```

4. 分支
	1. IF
```
-- n 地址 ,保存输入的值的地址!
accept n prompt '请输入一个数n'
 
declare
	pnum number := &n;
	if 
		pnum=0 then dbms_output.put_line(pnum);
		elsif pnum=1 then dbms_output.put_line(pnum);
		else dbms_output.put_line(pnum);
	end if;
begin

end;
```
	2. 循环
```
while total<100
loop
	...
end loop;

Loop
EXIT when pnum>10
	...
end loop;

FOR I  IN 1..3
loop
	...
end loop;

```

----------
# 光标/游标 Cursor #
1. 语法
		cursor c1游标名字 [参数名 参数类型,参数名 参数类型 ,..] is sql语句;
2. 游标使用
	1. 打开游标:	open c1;
	2. 获取一行:	fetch c1 into p;
		1. p类型 必须和 c1集合字段类型一致
		2. 大多数为 引用变量: my_name emp.ename%type;
	3. 关闭游标:	close c1;
	4. 游标最后:	exit when c1%notfound;
3. 游标属性
	1. %isopen
	2. %rowcount	//影响行数
	3. %found
	4. %notfound

```
declare
	cursor c1 is select ename,sa; from emp;
	pname emp.ename%type;
	psal emp.sal%type;
begin
	open c1;
	loop	
		fetch c1 into pname,psal;
		exit when c1%notfound;
		dbms_output.put_line(pname||"x"||psal);
	end loop;
	close c1;
	commit;  -- 如果进行了dml操作,记得提交事务
end;

```

``` 
--带参游标
declare
	cursor c1(id number) is select ename from emp where deptId=id;
	pname emp.ename%type;
begin
	open c1(10);
	lopp
		fetch c1 into ename;
		exit when c1%notfound;
		...
	end loop;
end;
```

----------
# 异常处理 #
1. 只有 try catch ,没有finally
2. 分 系统例外 和 自定义 例外
	1. 系统例外
		1. no_data_found
		2. too_many_rows
		3. zeor_divide
		4. value_error
		5. timeout_on_resource
	2. 自定义例外
3. 

```
declare
	pn number;	
	-- 自定义 异常
	no_data exception;
begin
	pn:= 1/0;
	
	open c1;
	fetch c1 into xxx;
	if c1%notfound then 
		raise no_data;
	end if;
	-- 关闭游标 , 程序异常 pmon(process monitor 程序监控进程 会回收资源)
	close c1;1
exception
	when zeor_divide then 
		p1;
		p2;
	when value_error then  p1;
	when no_data then ...;
	when others then p1;
end;

```

----------















