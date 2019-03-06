# 数据分类 #
1. 结构化
	* 有固定格式或有限长度的数据
	* 如数据库，元数据
2. 非结构化
	* 不定长或无固定格式的数据
	* word文档等磁盘上的文件
	
# 查询方法 #
1. 顺序扫描法(Serial Scanning)	
	* 从头看到尾，如果此文档包含此字符串，则此文档为我们要找的文件
	* 如利用windows的搜索也可以搜索文件内容
	* 慢,效率低
2. 全文检索(Full-text Search)
	* 将非结构化数据中的一部分信息提取出来，重新组织，使其变得有一定结构(索引)
	* 然后对此有一定结构的数据进行搜索，从而达到搜索相对较快的目的
	* 例如：
		* 字典:字典前面的拼音表和部首检字表就相当于字典的索引
	* 先建立索引,然后搜索的过程,称之为全文检索

# 如何实现全文检索 #

# lucene #
1. Lucene是apache下的一个开放源代码的全文检索引擎工具包。(jar)
2. 可以使用Lucene实现全文检索
	1. 提供了完整的查询引擎和索引引擎，部分文本分析引擎
3. 使用场景
	1. 互联网全文搜索
	2. 站内全文搜索
	3. 优化数据库查询(数据库like,使用权标扫描,慢)

# Lucene结构 #
1. 文档document:一个文件(数据库一条记录)当成一个document
	* 每个文档都有一个唯一的编号，就是文档id。
	* Field(可以多个,多种形式) 
		* key	自己给值
		* value 文件真实信息
	* Filed是一个通用的数据结果 
2. 索引
	* Term: 单词
	* 组成: 文档的域名，另一部分是单词的内容


![](001_lucene_construct.png)



# 开发 #
1. [下载官网](http://lucene.apache.org/)
		最后地址 https://mirrors.tuna.tsinghua.edu.cn/apache/lucene/java/
2. 下载内容介绍
	* core				核心
	* analysis->common	分词
	* queryparser		查询器
3. 导包
	Lucene包：(在下载包里)
		lucene-core-.jar
		lucene-analyzers-common-.jar
		lucene-queryparser-.jar
		
	其它：
		commons-io-2.4.jar
		junit-4.9.jar
	
		<!-- 集中定义依赖版本号 -->
		<properties>
			<junit.version>4.12</junit.version>
			<solrj.version>4.10.3</solrj.version>
			<lucene.version>7.2.1</lucene.version>
		</properties>
	
	
		<dependencies>
			<dependency>
				<groupId>org.apache.lucene</groupId>
				<artifactId>lucene-core</artifactId>
				<version>${lucene.version}</version>
			</dependency>
			<dependency>
				<groupId>org.apache.lucene</groupId>
				<artifactId>lucene-queryparser</artifactId>
				<version>${lucene.version}</version>
			</dependency>
			<dependency>
				<groupId>org.apache.lucene</groupId>
				<artifactId>lucene-analyzers-common</artifactId>
				<version>${lucene.version}</version>
			</dependency>
	
			<!-- 单元测试 -->
			<dependency>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
				<version>${junit.version}</version>
				<scope>test</scope>
			</dependency>
			<dependency>
				<groupId>commons-io</groupId>
				<artifactId>commons-io</artifactId>
				<version>2.5</version>
			</dependency>
			
			<!-- solr客户端 -->
			<!-- <dependency> <groupId>org.apache.solr</groupId> <artifactId>solr-solrj</artifactId> 
				<version>${solrj.version}</version> </dependency> -->
		</dependencies>

4. 代码
	1. 写(增删改)
		/** 第一步： 创建IndexWriter,构造方法需要directory 和 config(分词器) **/			
		/** 第二步： 先加载读入文件,然后准备好将要写出的document **/
		
		/** 第三步： 将文档加入到索引和文档的写对象中(写出) **/

		/** 第四步： 提交,关闭 **/
	2. 读(查询)

5. 查看luke(下载慢)
	https://github.com/DmitryKey/luke/releases


----------
# Filed #
1. Filed属性
	1. 是否分词
		1. 是否对域value拆分,拆不拆都是为了方便建立索引
		2. 拆:文件内容
		3. 不拆:id(完整形式保存)
	2. 是否索引
		1. 可以对拆了和不拆的value,建立索引(和分词无关)
		2. 商品名称、商品简介分析后进行索引，订单号、身份证号不用分析但也要索引,
		3. 目的:查询
	3. 是否存储
		1. 是否将value单独存到新的磁盘上
		2. 目的:快速获取值
2. Field类型


# 分词器 #
# 自带分词器 #
1. StandardAnalyzer(自带)
	* 单字分词：就是按照中文一个字一个字地进行分词。如：“我爱中国”，效果：“我”、“爱”、“中”、“国”
2. CJKAnalyzer(自带)
	* 二分法分词：按两个字进行切分。如：“我是中国人”，效果：“我是”、“是中”、“中国”“国人”。
3. SmartChineseAnalyzer
	* 对中文支持较好，但扩展性差，扩展词库，禁用词库和同义词库等不好处理

# 第三方分词器 #
1. IK-analyzer
	* 下载https://code.google.com/p/ik-analyzer/
			<dependency>
			    <groupId>com.janeluo</groupId>
			    <artifactId>ikanalyzer</artifactId>
			    <version>2012_u6</version>
			</dependency>
	* 2012 最新版
	* stopword.dic 文件格式:utf-8 ,不能是 utf-8 bom格式



