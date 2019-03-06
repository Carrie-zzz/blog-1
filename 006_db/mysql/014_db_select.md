# 查询 #
select 字段 from 表名 where 条件 group by 分组字段 having 分组后条件 order by 排序字段 desc|asc


# 约束 #
1. 主键约束 primary ky
2. 非空 not null
3. 唯一 unique
4. 自动增长 :auto_increment
5. 外键约束: foreign key  

# 班级表 #
	create table hx_class(
	id int primary key auto_increment,
	name varchar(20) not null,
	createDate date
	);

insert into hx_class(id,name,createDate) values(1,'1701班','2017-01-01');

insert into hx_class(id,name,createDate) values(2,'1702班','2017-01-02');

## 主键约束 ##
1. 非空
2. 唯一
3. 一张表只能有一个主键,但是主键可以多个字段

	当前表数据的唯一标识，不能重复，不能为空，必须唯一


## 自增长 ##
1. 只能是主键
2. 必须能++  数值类型  int  bigint


## 外键 ##
1. 另一个表的主键，可以重复，可以为空，用来建立两张表的关系

1. 创建表的时候,在末尾添加
	foreign key(当前表外键字段) references 指向的表(指向的表字段);
2. 修改表的方式添加
   alter table tbl1_name add foreign key(当前表外键字段) references 指向的表(指向的表字段);

# 学生 #

	create table hx_student(
		id int primary key auto_increment,
		username varchar(20),
		password varchar(20),
		name varchar(20),
		birthday date,
		sex varchar(4),
		classId int,
	    foreign key(classId) references hx_class(id)
	);

	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','小红花',
	'1997-04-01','女',1);

	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','茉莉花',
	'1997-04-02','男',2);
	insert into hx_student
	(username,password,name,birthday,sex,classId) 
	values('root','1234','白百何',
	'1997-04-03','男',1);


----------

# 多表查询 #
1. 班级,学生表


# 查询 #
## 交叉查询 ##
	select * from A,B;
	select * from A join B;

## 内连接 ##
* 隐式内连接：
    * select * from A,B where 条件;
* 显示内连接：
    * select * from A [inner] join B on 条件;

## 外连接 ##
1. 假设班级3每一条学生,如果以上查询所有班级,


* 左外连接：left outer join
    * select * from A left outer join B on 条件;
* 右外连接：right outer join
    * select * from A right outer join B on 条件;


## 子查询 ##
子查询是指多个查询嵌套在一起的查询，这样可以将库中的多个表结合起来得到我们所要的某一查询结果

查询某个学生,的班级名

----------
	
	insert into hx_student
	(name,birthday,sex,cId) 
	values('小红',
	'1997-04-01','男',1);
	insert into hx_student(name,birthday,sex,cId) values('小橙','1997-04-02','女',1);
	insert into hx_student(name,birthday,sex,cId) values('小黄','1997-04-03','男',1);
	insert into hx_student(name,birthday,sex,cId) values('小绿','1997-04-04','女',1);
	
	
	insert into hx_student(name,birthday,sex,cId) values('大红','1997-04-05','男',2);
	insert into hx_student(name,birthday,sex,cId) values('大橙','1997-04-06','男',2);
	insert into hx_student(name,birthday,sex,cId) values('大黄','1997-04-07','男',2);
	insert into hx_student(name,birthday,sex,cId) values('大绿','1997-04-08','男',2);


insert into hx_student(name,birthday,sex) values('大黑','1997-04-08','男');




# navicat快捷键 #
	
		1.ctrl+q          打开查询窗口
		2.ctrl+/           注释sql语句
		3.ctrl+shift +/  解除注释
		4.ctrl+r          运行查询窗口的sql语句
		5.ctrl+shift+r   只运行选中的sql语句
		6.F6              打开一个mysql命令行窗口
		7.ctrl+l           删除一行
		8.ctrl+n          打开一个新的查询窗口
		9.ctrl+w         关闭一个查询窗口
		10.ctrl+d     在查询表数据界面打开一个该表结构的窗口