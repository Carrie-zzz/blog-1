# 学习资料 #
1. http://blog.csdn.net/weixin_39082031/article/category/7370960
# solr #
1. [下载官网](http://lucene.apache.org/)
		最后地址 http://mirrors.hust.edu.cn/apache/lucene/solr/
2. tomcat集成
		http://blog.csdn.net/wxyora/article/details/78390661
		
		1 由于开发环境是Windows环境，所以官网下载对应windows的安装包solr-7.1.0.zip。
		
		2 在tomcat-webapps文件夹下新建文件夹solr并将solr-7.1.0\server\solr-webapp\webapp文件夹下的内容一并拷贝到solr文件夹下。
		
		3 在\solr\WEB-INF下新建文件夹classes并将\solr-7.1.0\example\resources下的log4j.properties文件拷贝到此文件夹
		
		4 将\solr-7.1.0\server\lib和\solr-7.1.0\server\lib\ext文件夹下的所有jar包增加到tomcat下\solr\WEB-INF\lib文件夹下
		
		5 在tomcat根目录新建文件夹solr_home并将\solr-7.1.0\server\solr下的文件全部拷贝到solr_home下，此文件夹下内容为solr的一个实例。
		
		6 修改solr服务器目录中web.xml文件指定solr_home所在位置 
		solr/home 
		F:/apache-tomcat-8.0.41/solr_home
		7 启动tomcat，solr会借助jetty自动启动solr服务实例，最后访问solr服务地址即可看到solr主页： http://localhost:8080/solr/index.html。

3. 问题 c:\solr.log
	* HTTP Status 403 - Access to the requested resource has been denied
		* 注释solr下web.xml里面的
			http://blog.csdn.net/qq_37230967/article/details/79138421
	* SolrCore Initialization Failures
		 	error:${error},什么错误信息都没有!!!tomcat不兼容(测试使用的是tomcat7,不行),改成9
4. 添加solr_core
	* Error CREATEing SolrCore 'new_core': Unable to create core [new_core] 
		* Caused by: Can't find resource 'solrconfig.xml' in classpath or 'C:\Users\Administrator\Desktop\soft\tomcat\apache-tomcat-9.0.1-windows-x64\apache-tomcat-9.0.1\solr_home\new_core'
	* 在solr_home下面新建的core里面复制一份 config(在solr_home\configsets\sample_techproducts_configs\conf)

5. 界面

# 配置信息 #

1. managed-schema : 存放 声明的 field
2. solrconfig.xml : 配置solr服务






----------
# 业务域定义 #
1. IKAnalyzer上传到solr项目  lib下面
2. IK 配置,扩展,停用字典 文件 添加到solr项目 class
3. cd solr_home/collection1实例/conf/managed-schema
4. 定义fieldType,field