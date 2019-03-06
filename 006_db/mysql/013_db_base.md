# 数据库-mysql安装 #

下载:
https://www.mysql.com/

安装版:
	download->Community->MySQL on Windows->MySQL Installer
					   ->MySQL Community Server
配置版本


绿色安装:
http://blog.csdn.net/u012689060/article/details/49591931

exe安装:
http://jingyan.baidu.com/article/642c9d34aa809a644a46f717.html


# 修改密码 #
	mysql>use  mysql 
	#新版本使用:
	mysql>update user set authentication_string=password('1234') where User='root';    //password(1234)  /('字符')
	#老版本使用:
	mysql>update user set password=password(1234) where User='liuyang';    //password(1234)  /('字符')
	#刷新权限
	flush privileges; 
	//重新加载权限表




# 服务贸易开启 #
1. services.msc
2. net start mysql


# 登入mysql #
1. mysql -uroot -p1234


----------
# 配置 #


## servlet ## 

## 工程配置 ##
workspace,jsp,save action,content assi






----------
# 数据存储方式 #
1. 本子
2. 电脑文档 文件,文件夹 excel
3. 数据管理服务软件,数据库项目管理软件


## 数据库服务器 ##
1. 安装->数据库管理软件的电脑->mysql软件
2. 服务->给外界提供数据服务
3. 技术->通过标准sql存储数据(Structured Query Language)


## web服务器 ##
1. 安装->web软件->tomcat
2. 服务->提供外界资源访问的这项服务
3. 技术->servlet


# 数据介绍 #
|	描述		 |	概念|
| -----------| :-----:  | 
| 库 database|  	文件夹	| 
| 表 table   |  文件   	|  
| row  		 |  一行数据  	|   
| 字段()  	 |  列名(属性)  	|
| 主键id 	 |  每个表中一行数据的唯一表示 |
| 字段类型  	 |   一列的存储类型 	|

# 数据库管理软件 #
1. 文件(表),文件夹(数据库)管理软件
2. 一行行(一条条)数据管理软件
3. 存放 实体 和实体之间的关系(二维表)


## 表的创建类型 ##
1. 存放实体和实体之间的关系
2. 数据库中每个实体对应着一个java实体类(JavaBean)


----------


# 数据的操作 #
 ** 增删改查**

## 文件夹,文件 ##
create drop alter show

## 文件里面的一行行的数据 ##
insert delete update **select** 



# 数据库操作(文件夹) #
1. 数据库增删改查 :create drop alter show

## 查看 ##
1. show databases;
2. show create database mytest;


## 增加(创建)数据库 ##
create database 数据库名字 ;

## 删除数据库 ##
drop 数据库名字;

## 修改update ##
alter database 数据库名 character set 字符集 collate 校对规则;




## 其他 ##
* use 库名
* 查看正在使用的数据库:select database();

----------

----------

# 数据库**表**的操作(文件) #
1. 增删改查 : create drop alter show
2. 首先要确认在哪个文件夹下面(某数据库中)

use 数据库;

### 查看 ###
1. show tables;  
2. show create table 表名;
3. desc 表名;  --description


## create ##
模拟创建java 实体过程

1. 创建表的同时必须同时创建表头 
2. create table 表名( 头名 头类型 [约束],头2名 头2类型  )

	create table hx_user
	(
	 id int,
	 username varchar(20),     -- 定长:长度20字符
	 pwd varchar(20)    -- 长度0-20字符
	);


## 数据类型 ##
	Java				MYSQL
	int					int
	float				float
	double				double(4,2)
	char/String			char/varchar(char固定长度字符串，varchar可变长度的字符串)
	Date				date,time,datetime,timestamp
	文件类型				BLOB、TEXT   TEXT指的是文本文件  BLOB二进制文件
	* Oracle的文件类型：BLOB  CLOB



## 删除表 ##
1. drop  table 表名;

## 修改表 ##
1. 修改表名 :ALTER	TABLE	<表名>	RENAME   TO <新表名>;
2. 添加字段 :ALTER	TABLE	<表名>	ADD	<列名>  <字段属性>;
3. 修改字段 :ALTER	TABLE	<表名>	CHANGE <旧列名> <新列名> <字段属性>;
4. 删除字段 :ALTER	TABLE	<表名>	DROP   COLUMN   <列名>;


1. alter table hx_user rename to users;
2. alter table users add sex2 varchar(10) not null;
3. alter table users change sex2 sex3 int;
4. alter table users drop  sex3;






----------

----------

----------
# 数据操作(行数据) #
1. 增删改查: insert delete update select
2. 告诉哪一个表(文件)里面插入;



## 增 insert ##
1. insert into	表名(columName ,columName, columName…) values(value1,value2,value3…);
2. 类型必须保持一致
3. 类型
	* 数字类型 不用引号
	* 字符串 日期 必须引号

## 删除 ##
1. delete from 表名	WHERE 条件





## 改 ##
1. update 	表名 	set	列名=新值,列名=新值,……
WHERE		<条件>;


|说明	|关键字|
|--|--|
|多重条件	|And 、or|
|比较条件	|=、>、<、!=、!>、!<、not+比较条件|
|是否在某一范围|	In(<可选值集>)、not in(<可选值集>)|
|是否空值	|Is null：某列为null时为真，is not null：某列非空时为真|
|模糊匹配	|Like ‘%/_<字符串>’，%模糊匹配任意内容，例：a%b表示以a开头，以b结尾的任意长度的字符串。如acb，addgb，ab 等都满足该匹配串；_ 模糊匹配某一个字符，例：a_b表示以a开头，以b结尾的长度为3的任意字符串。如acb，afb等都满足该匹配串。当用户要查询的字符串本身就含有 % 或 _ 时，要使用ESCAPE '<换码字符>' 短语对通配符进行转义。|


----------
# 案例 #

# 查(单表) #
select * from 表名 where 条件  group by 分组字段 having 分组条件 order by 排序字段 asc|desc


## 基本查询 ##
1. 指定字段查询
2. 重复
3. 别名

	select name,job,gender from teacher;
	select distinct job from teacher;
	select distinct job as '开设课程'from teacher;

## 条件查询 ##
1. =><
2.模糊查询 

select * fromteacher where job='java';
select * fromteacher where job>'java';

select * fromteacher where job='java';



### =>< ###
1. int  age>18
2. string name='小红花'


### 模糊查询 ###
1. name like 小_虾;
2. name like '%红' --红结尾的数据
3. name like '小%' --小开头的数据


### 升降序 ###
1. asc
2. desc

1. java老师 id升序
2. java老师 id降序


## 聚合函数 ##
1. AVG
	求取平均值：select AVG(字段名) from 表名;
2. COUNT
	统计个数函数：select COUNT(字段名) from 表名;
3. MIN/MAX
	求最大最小值函数：select MIN(字段名),MAX(字段名) from 表名;
4. SUM
	求和函数：select SUM(字段名) from 表名;


1. java老师平均年龄 
		select avg(age) from teacher where job='java';
2. java老师人数
		select count(*) from teacher where job='java';
3. java老师最大,最小年龄,
		select max(age),min(age) from teacher where job='java';
4. java老师年龄总和
		select sum(age) from teacher where job='java';


## 分组函数 ##
 分组查询可将结果集分成指定的逻辑组，以生成类似报表方式的统计结果

1. group by 
2. 分组后,只能查询 分组字段,和聚合函数


1. 更具job 分组,统计每组人数
		select job,count(*) from teacher group by job;

### 分组后过滤 ###
		select job,count(*),avg(age) from teacher group by job having avg(age)>18;


where 条件 ,分组前,不能聚合函数

having条件:分组后,能聚合函数




# 案例 #

## 创建 ##
1、学号（id）不能存在相同的 ，int类型
2、名字（name）为非空，字符串不能超过10个字符
3、性别（gender）非空，字符串不能超过5个字符
4、年龄（age）为int，可空
5、工作（job）为字符串，可空
6、创建日期（createDate）为日期格式，非空

## 题目 ##
1. 查询所有全部信息 :
		select * from teacher
2. 查询ID为1的全部信息 :
		select * from teacher where id=1
3. 通过别名方式查询ID为1的老师name和job:
		select name as 姓名,job 工作 from treacher where id=1;
4. 查询job为null的老师姓名 
5. 查询性别为女和指定日期的老师全部信息:
		select * from teacher  where gender='女'  and createDate='2017-01-01'
6. 查询Sex为男或者ID大于5的老师
7. 查询name最后一个字符为指定字符的老师姓名全称:
		select * from teacher where name like '*红';
8. 查询name以指定字符开头的老师全部信息
9. 查询name中包含指定字符的老师全部信息
10. 查询全部信息，并按指定条件排序（ASC、DESC）
11. 多个排序条件：当第一个条件相同时，以第二个条件排序
12. 按性别分组，查询男女老师的人数（GROUP BY）
13. 按性别分组，查询出女老师人数的总数
14. 查询表的总记录数
15. 查询全部记录的前三条数据或者指定范围记录（分页limit）


create table teacher(
id int primary key,
name varchar(10) not null,
gender varchar(5) not null,
age int ,
job varchar(255),
createDate date
);

insert into teacher(id,name,gender,age,job,createDate)
values (1,'小红','女',21,'java','2017-01-01');

insert into teacher(id,name,gender,age,job,createDate)
values (2,'小橙','女',22,null,'2017-01-02');

insert into teacher(id,name,gender,age,job,createDate)
values (3,'小黄','男',23,'c','2017-01-03');

insert into teacher(id,name,gender,age,job,createDate)
values (4,'小绿','女',24,'c++','2017-01-04');

insert into teacher(id,name,gender,age,job,createDate)
values (5,'小青','女',25,'c#','2017-01-05');

insert into teacher(id,name,gender,age,job,createDate)
values (6,'小蓝','女',26,'java','2017-01-06');

insert into teacher(id,name,gender,age,job,createDate)
values (7,'小紫','女',27,'ruby','2017-01-07');

insert into teacher(id,name,gender,age,job,createDate)
values (8,'红','女',28,'ruby','2017-01-08');

insert into teacher(id,name,gender,age,job,createDate)
values (9,'小龙红','女',29,'java','2017-01-09');

insert into teacher(id,name,gender,age,job,createDate)
values (10,'小龙虾','男',30,'java','2017-01-10');

select * from teacher;














