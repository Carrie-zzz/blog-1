# 数据库引擎 #
1. myIsam
	1. MyISAM只要简单的读出保存好的行数即可
	2. MyISAM是MySQL的默认存储引擎，基于传统的ISAM类型
	3. 支持全文搜索，但不是事务安全的，而且不支持外键。
	4. 每张MyISAM表存放在三个文件中：frm 文件存放表格定义；数据文件是MYD (MYData)；索引文件是MYI (MYIndex)
	5. 非聚簇索引
2. innodb
	1. innoDB 中不保存表的具体行数，也就是说，执行select count(*) from table时，InnoDB要扫描一遍整个表来计算有多少行。
	2. InnoDB是事务型引擎
	3. 支持回滚、崩溃恢复能力、多版本并发控制、ACID事务，支持行级锁定
	4. 聚簇索引

	核心区别
		MyISAM是非事务安全型的，而InnoDB是事务安全型的。
		MyISAM锁的粒度是表级，而InnoDB支持行级锁定。
		MyISAM支持全文类型索引，而InnoDB不支持全文索引。
		MyISAM相对简单，所以在效率上要优于InnoDB，小型应用可以考虑使用MyISAM。
		MyISAM表是保存成文件的形式，在跨平台的数据转移中使用MyISAM存储会省去不少的麻烦。
		InnoDB表比MyISAM表更安全，可以在保证数据不会丢失的情况下，切换非事务表到事务表（alter table tablename type=innodb）。	
	应用场景
		MyISAM管理非事务表。它提供高速存储和检索，以及全文搜索能力。如果应用中需要执行大量的SELECT查询，那么MyISAM是更好的选择。
		InnoDB用于事务处理应用程序，具有众多特性，包括ACID事务支持。如果应用中需要执行大量的INSERT或UPDATE操作，则应该使用InnoDB，这样可以提高多用户并发操作的性能。	


一种叫做聚簇索引（clustered index ），
	数据和主键B+树存储在一起，辅助键B+树只存储辅助键和主键，主键和非主键B+树几乎是两种类型的树。


一种叫做非聚簇索引（secondary index）
	主键B+树在叶子节点存储指向真正数据行的指针，而非主键

# 索引 #
数据库必须有索引，没有索引则检索过程变成了顺序查找，O(n)的时间复杂度几乎是不能忍受的。

https://www.cnblogs.com/whgk/p/6179612.html
https://blog.csdn.net/wxb880114/article/details/81019654
	1. MySQL中的索引的存储类型有两种：BTREE、HASH。 也就是用树或者Hash值来存储该字段
	2. 索引的分类
		　	MyISAM和InnoDB存储引擎：只支持BTREE索引， 也就是说默认使用BTREE，不能够更换
			MEMORY/HEAP存储引擎：支持HASH和BTREE索引
		* 单列索引
			* 普通索引	INDEX 			: MySQL中基本索引类型，没有什么限制，允许在定义索引的列中插入重复值和空值，纯粹为了查询数据更快一点。
			* 唯一索引	UNIQUE INDEX 	: 索引列中的值必须是唯一的，但是允许一个空值
			* 主键索引	PRIMARY KEY		: 一种特殊的唯一索引，不允许有空值
		* 组合索引	INDEX MultiIdx
			* 多个字段组合上创建的索引
			* 遵循**最左前缀**集合!
				* 最左边的列集来匹配行，这样的列集称为最左前缀
				* id，name，age	
					* (id，name，age)、(id，name)或者(id)  都会使用索引
					* （name，age）	不使用
		* 全文索引 　		FULLTEXT INDEX 
			* 只有在MyISAM引擎上才能使用
			* 只能在CHAR,VARCHAR,TEXT类型字段上使用全文索引
			* 需要借助MATCH函数
			* 搜索的关键字默认至少要4个字符
			* 搜索的关键字太短就会被忽略掉


		* 空间索引
			* 必须使用MyISAM引擎
			* 空间类型的字段必须为非空
			* 空间索引是对空间数据类型的字段建立的索引，MySQL中的空间数据类型有四种，GEOMETRY、POINT、LINESTRING、POLYGON
	


# sql优化 #
https://blog.csdn.net/wxb880114/article/details/81019654

1.  limit [offset,] rows
	1.  当offset很大的时候, sql 先跳过offset,然后查 rows条数据,效率很慢
		1.  扫描满足条件的 offset +rows 行，扔掉前面的 offset 行，返回最后的 rows 行，问题就在这里。
	2. 优化
		1. 通过id分页
			1. 我们发现如果id不是顺序的，也就是如果有数据删除过的话，那么这样分页数据就会不正确，这个是有缺陷的。
			2. select  id,my_sn from big_data where id>5000000 limit 10;
		1. 子查询优化法
			1.  先找出第一条数据，然后大于等于这条数据的id就是要获取的数据
			2.  数据必须是连续的，可以说不能有where条件，where条件会筛选数据，导致数据失去连续性实验下
				select * from mytbl where id >=
				  ( select id from mytbl order by id limit 100000,1)
				limit 10

				SELECT id,title,content FROM items WHERE id IN (SELECT id FROM items ORDER BY id limit 900000, 10);  

		2. 倒排表优化法
		   1. 倒排表法类似建立索引，用一张表来维护页数，然后通过高效的连接得到数据
  		   2. 缺点：只适合数据数固定的情况，数据不能删除，维护页表困难
		3. 反向查找优化法
			1. 当偏移超过一半记录数的时候，先用排序，这样偏移就反转了
			2. order by优化比较麻烦，要增加索引，索引影响数据的修改效率，并且要知道总记录数，偏移大于数据的一半
		4. limit限制优化法
			1.  把limit偏移量限制低于某个数。。超过这个数等于没数据，我记得alibaba的dba说过他们是这样做的


	分析：因为mysql分页查询是先把分页之前数据都查询出来了，然后截取后把不是分页的数据给扔掉后得到的结果这样，所以数据量太大了后分页缓慢是可以理解的。
	但是我们可以先把需要分页的id查询出来，因为id是主键id主键索引，查询起来还是快很多的，然后根据id连接查询对应的分页数据，可见并不是所有的连接查询都会比
	单查询要慢，要依情况而定。



# explain #
https://www.cnblogs.com/gomysql/p/3720123.html

1. 使用
	1. explain  select ... 
	2. explain  extended select ...
	3. explain partitions SELECT ……  用于分区表的EXPLAIN生成QEP的信息

2. show warings
	1. 运行SHOW WARNINGS 可得到被MySQL优化器优化后的查询语句

		explain select * from bbs_employee;
		SHOW WARNINGS ;	
3. 执行计划列
		+----+-------------+-------+-------+---------------+---------+---------+------+------+-------------+
		| id | select_type | table | type  | possible_keys | key     | key_len | ref  | rows | Extra       |
		+----+-------------+-------+-------+---------------+---------+---------+------+------+-------------+
	1. id	:包含一组数字，表示查询中执行select子句或操作表的顺序
		1. id相同,可以认为是一组，执行顺序由上至下
		2. 如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
	2. select_type	: 标示查询中每个select子句的类型（简单OR复杂）
		1. SIMPLE：查询中不包含子查询或者UNION
		2. PRIMARY :  查询中若包含任何复杂的子部分，最外层查询则被标记为PRIMARY
		3. SUBQUERY :  在SELECT或WHERE列表中包含了子查询，该子查询被标记为：SUBQUERY
		4. DERIVED（衍生） : 在FROM列表中包含的子查询被标记为DERIVED
		5. UNION	:若第二个SELECT出现在UNION之后
		6. UNION RESULT	: 从UNION表获取结果的SELECT被标记为：UNION RESULT

	3. table
	4. type	: 访问类型
		1. 性能最差 ALL, index,  range, ref, eq_ref, const, system, NULL	性能最好  (性能从最差到最好)
		2. ALL：Full Table Scan， MySQL将遍历全表以找到匹配的行
		3. index：Full Index Scan，index与ALL区别为index类型只遍历索引树
		4.  range:索引范围扫描，对索引的扫描开始于某一点，返回匹配值域的行。between或者where子句 IN()
		5.  ref：使用非唯一索引扫描或者唯一索引的前缀扫描
		6.  eq_ref：类似ref，区别就在使用的索引是唯一索引，对于每个索引键值
		7.   const、system：当MySQL对查询某部分进行优化，并转换为一个常量时，使用这些类型访问。
		8.   NULL：MySQL在优化过程中分解语句，执行时甚至不用访问表或索引，例如从一个索引列里选取最小值可以通过单独索引查找完成
	5. possible_keys
		1. 指出MySQL能使用哪个索引在表中找到记录，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询使用
	6. key
		1. 显示MySQL在查询中实际使用的索引，若没有使用索引，显示为NULL
	7. key_len
		1. 表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度
	8. ref
		1. 表示上述表的连接匹配条件，即哪些列或常量被用于查找索引列上的值
	9. rows
		1. 表示MySQL根据表统计信息及索引选用情况，估算的找到所需的记录所需要读取的行数
	10. extra
		1. 包含不适合在其他列中显示但十分重要的额外信息




