# 执行计划 #
1. explain plan for sql
2. select * from table( dbms_xplan.display);


# 数据库对象  DDL #
1. 表
2. 视图
3. 序列
4. 索引
5. 同义词
6. 存储过程
7. 存储函数
8. 触发器
9. 包
10. 包体


----------
# 表 #
1. 命名规则
	1. 必须字母开头
	2. 必须在1-30个字符之间
	3. 只能包含 字母 数字 _ $ #
	4. 不能重名
	5. 不能是保留字
	6. 默认存储都是大写
	7. 数据库名 只能是 1-8位,databaselink可以是128位,和其他一些特殊字符
2. 创建表的要求
	1. 创建权限
	2. 存储空间
3. 字段数据类型
	1. varchar2(size)
	2. char(size)
	3. number
	4. date
	5. long
	6. blob clob
	5. rowid		// 相当一个指针,指向 dbf 数据文件中一个位置,保存对应的行记录
4. 创建表
	1. 子查询创建表 subquery
	1. create table emp1 as select * from emp where 1=2
5. 修改表
	1. 列 crud
		1. 追加列
			1. alter table t add tel char(11);
		2. 删除列
			1. alter table t drop column tel;
		3. 修改列
			1. alter table t modify tel char(22);
		4. 重命名列
			1. alter table t rename column tel to mobile;
		5. 重命名表
			1. rename t to t1;
6. 删除
	1. drop table t
	2. 查看回收站 show recyclebin;
	3. 清空回收站 purge recyclebin;
	4. 管理员是没有回收站的
7. 查询
	1. select * from tab	// 查询表 和 回收站表
8. flashback闪回
	1. flashback table	xx to before drop ; // 返回到drop语句之前 
	2. flashback database
	3.  flashback table	xx to before drop rename to xx2
9. 约束
	1. 表级约束
		1. 联合主键
	2. 列级约束
		1. not null
		2. unique
		3. primary key
		4. foreign key
			1. 
		5. check

----------
# 视图 view#
1. 需要权限
	1. grant create view to scott;
2. 视图是一种虚表
	1. 不存数据,数据从 依赖表中获取
	2. 简化查询,但是不能调高性能
3. 创建视图
	1. create [or replace]  view  viewName as subQuery [ with check option with read only ]
		1. 
	2. 
4. 删除视图
	1. drop view viewName

5. 可以dml操作 ,但是 一般 是  with read only 

----------
# 序列 sequence#
1. auto_increment
2. 作用
	1. 自动提供唯一的数值
	2. 共享对象
	3. 主要用于提供主键
	4. 将序列值装入内存可以提高访问效率
3. 序列->数值[1,2,3..]->内存
4. 创建
	1. create sequence sequenceName [increment by n  start with n maxvalue n minvalue n ]
5. 获取
	1. sequenceName.nextval
6. 序列裂缝,不连续
	1. 回滚
	2. 系统异常
	3. 多个表同时使用同一个序列
7. 修改
	1. alter sequence..
8. 删除 
	1. drop sequence xx

----------
# 索引 #
1. 介绍
	1. 通过指针,提高查询速度
	2. 创建场景
		1. 列中数据值分布范围很广
		2. 经常在where自居或者连接条件出现的
		3. 表经常被访问而且数据类很大,访问的数据大概占数据总量2%-4%
		4. 大表
	3. 不适用创建索引
		1. 表很小
		2. 列不经常作为连接条件或出现在where子句中
		3. 查询的数据大于4%
		4. 表经常更新索引列
2. 创建
	1. create index x on emp(depNo);
3. 索引类型
	1. BTree balance tree 平衡树(默认)
	2. 位图索引 矩阵
4. 

# 同义词-别名 #
1. 可以给任何对象 创建同义词(别名)
2. 权限
	1. grant create synonym to scott'
2. 创建
	1. create [public] synonym   s  for object
3. 










