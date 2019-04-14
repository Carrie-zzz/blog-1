# oracle 登入 #
1. 密码
2. 主机认证
3. 全局认证


# 地址符 #
1. & 放入 select , from 

# 表 #
1. 标准表
2. 临时表
	1. 手动创建 临时表  create global temporary table
	2. 自动创建 排序,
	3. 删除时间: 当事务或者会话结束的时候,自动删除
3. 索引表

# sql类型 #
1. DML 可以回滚
	1. insert  delete  update
2. DDL 不可回滚
	1. database table index  view  sequence synonym (同义词)
	2. tuncate
3. DQL
	1. select
4. DCL
	1. grant revoke


# 导入导出数据 #
1. 导入
	1. @d:/temp/a.sql

----------

# 伪列 #
1. level	层级
2. rownum	行号
	1. 永远按照默认的顺序生成
	2. 只能     rownum<a  rownum<=a  不能 > >=	
		1. 因为 rownum永远从1开始
	
# 多表查询 #
1. 等值连接
	1. select * from a,b where a.id=b.aid
2. 不等值连接
3. 外连接
	1. 左外连接,在等号右边+
		1. where e.deptId=d.id(+) 
	2. 右外连接,在等号左边+
		1. where e.deptId(+)=d.id
4. 自连接
	1. select * from e e1,e e2 where e1.mgr=e2.id
5. 树递归(emp 员工 ,mgr 上司),层次查询
	1. select level 伪列,empno   from emp connect by prior empno=mgr start with mgr is null

6. 笛卡尔积,为了避免笛卡尔积全集,用n-1个**连接条件**

# 相关子查询 #
1. 先执行主查询, 把主查询中的值 作为参数传递给子查询
		select * from emp e where e.sal>(select avg(sal) from where deptno=e.deptno)

----------

# insert #
1. insert into
2. insert into e(f1,f2..)  select f1,f2... from e where ...
3. insert into e  select * from e where ...

# uodate #
1. update e set sal=() where..

# delete  DML #
1. delete from e  
	1. dml 语句, 可以回滚,不释放空间,可以返回flashback(提交了事务,可以撤销),
	2. 会产生碎片
		1. alter table 表名 move;
2. truncate
	1. ddl 语句, 不可以回滚,释放空间,不可以返回
	2. 不会产生碎片
	3. 速度慢(mysql速度快): undo数据(还原数据)

----------

# 事务 transaction #
1. 自动开启事务,需要手动提交
2. 开始标志: 第一条 dml语句
3. 结束标志: 
	1. 提交
		1. commit提交  或者 正常退出exit  或者 DDL/DCL(隐藏了提交事务操作)
	2. 回滚
		1. 非正常退出 掉电 宕机
		2. 手动 rollback
			1. rollback
			2. rollback to **savepoint a**
4. 隔离级别
	1. 支持 
		1. read_commited
		2. serializable
		3. read only (oracle特有)
			1. set transaction read only
	2. 默认 read_commited  

----------
# top n 问题 #
1. select rownum,empno from (select * from emp order by sal desc) where rownum<=3

# 分页 #
1. select * from rownum<=3 and **rownum>=0; **  // 查询不出来
2. 曲线
	from (select rowrum r , * from 
	(select * from emp order by sql)e1  wherw rowrum<=8)e2 where er.r>=5


----------
# DDL 数据库对象 #
1. 