# 环境 #
1. jdk
2. mysql
3. tomcat


----------


## jdk ##
1. 查看系统是否自带jdk : java -version
	* 卸载自带的:  
		* 查看:rpm -qa | grep java
		* 卸载:rpm -e --nodeps 卸载的包名
2, 上传
	* 自己下载
		* https://www.oracle.com/index.html
		* http://www.oracle.com/technetwork/java/index.html
	* 然后上传到服务器
		* FileZilla上传ftp软件,root目录下
		* crt 自带ftp上传
	* /usr/local 共享目录下,创建一个java目录,
		* cd /usr/local 
		* mkdir jdk
	* 把root下的jdk cp到java目录下面	
		*  cp /root/jdk-9_linux-x64_bin.tar.gz ./jdk/

3. 安装
	* 解压
		* tar -xvf j
	* 配置环境变量cd j
		* vi /etc/profile  最后追加:
			######set java environment  ;;pwd jdk path
			JAVA_HOME=/usr/local/src/jdk/jdk-9
			CLASSPATH=.:$JAVA_HOME/lib.tools.jar
			PATH=$JAVA_HOME/bin:$PATH
			export JAVA_HOME CLASSPATH PATH
		* 没有效果 java -version
			-bash: java: command not found
		* 使配置文件生效: source /etc/profile
			* 如果:-bash: /usr/local/java/jdk1.7.0_72/bin/java: /lib/ld-linux.so.2: bad ELF interpreter: 没有那个文件或目录
				* 安装jdk依赖软件
					* yum -y install glibc.i686

	* 测试成功 
		[root@ly jdk1.7.0_72]# java -version
		java version "1.7.0_72"
		Java(TM) SE Runtime Environment (build 1.7.0_72-b14)
		Java HotSpot(TM) Client VM (build 24.72-b04, mixed mode)


----------

## mysql ##
[yum安装方式](http://www.cnblogs.com/xiaoluo501395377/archive/2013/04/07/3003278.html)
		
## mysql ##

1. 检测
	* rpm -qa | grep mysql
	* rpm -e --nodeps   卸载的包名
2. 上传
 	* 自己下载,然后上传到服务器
	 	* https://www.mysql.com/
		 	* Community ->  MySQL Community Server
		 	* red hat -> linux 6
	 	* https://dev.mysql.com/downloads/mysql/
	* FileZilla上传fto软件,root目录下
	* /usr/local 共享目录下,创建一个mysql目录,
		* mkdir mysql
	* 把root下的mysql cp到mysql目录下面	
		* cp /root/MySQL.rpm-bundle.tar  ./mysql
3. 安装
	* 解压	
		* tar -xvf 
		* 几个rpm文件
			-rw-r--r--. 1 root root  472432640 10月  9 00:27 mysql-5.7.19-1.el6.x86_64.rpm-bundle.tar
			-rw-r--r--. 1 7155 31415  23618024 6月  24 20:08 mysql-community-client-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415    336268 6月  24 20:08 mysql-community-common-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415   3747580 6月  24 20:08 mysql-community-devel-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415  39248236 6月  24 20:08 mysql-community-embedded-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415 136599912 6月  24 20:08 mysql-community-embedded-devel-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415   2177644 6月  24 20:09 mysql-community-libs-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415   1723316 6月  24 20:09 mysql-community-libs-compat-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415 159680896 6月  24 20:09 mysql-community-server-5.7.19-1.el6.x86_64.rpm
			-rw-r--r--. 1 7155 31415 105292128 6月  24 20:09 mysql-community-test-5.7.19-1.el6.x86_64.rpm
	* 安装服务端
		*  rpm -ivh mysql-community-server-5.7.19-1.el6.x86_64.rpm --nodeps --force
			
		* 如果不force,安装后需要依赖或升级
			* 依赖(高版本可以忽略)
		    warning: MySQL-server-5.5.49-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
			error: Failed dependencies:
		        libaio.so.1 is needed by MySQL-server-5.5.49-1.linux2.6.i386
			* yum -y install libaio.so.1 libgcc_s.so.1 libstdc++.so.6 
				* 时间比较长
		* 需要升级
			错误： Multilib version problems found. This often means that the ro
			Protected multilib versions: libstdc++-4.4.7-18.el6.i686 != **libstdc++-4.4.7-17.el6.x86_64**
			错误：Protected multilib versions: libgcc-4.4.7-18.el6.i686 != libgcc-4.4.7-17.el6.x86_64
		   * yum update libstdc++-4.4.7-17.el6.x86_64

		* 更新完毕,还需要重新安装服务吗???
			* 需要重新安装
	* 安装客户端
		* rpm -ivh mysql-community-client-5.7.19-1.el6.x86_64.rpm --nodeps --force
			
		* 依赖
			* warning: MySQL-client-5.5.49-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
				error: Failed dependencies:
		        **libncurses.so.5** is needed by MySQL-client-5.5.49-1.linux2.6.i386
			* yum -y install libncurses.so.5 libtinfo.so.5
				* 时间比较长
4. 运行mysql
	* 查看:service mysqld status	(老版本 mysql,新版mysql: 未被识别的服务)
		* mysqld 已停 (正常)
		*  ERROR! MySQL is not running
		* 如果遇到需要安装或者更新,请先操作
			libgcc_s.so.1 must be installed for pthread_cancel to work
			ERROR! MySQL is not running
			yum -y install libgcc_s.so.1
			yum -y update	xxx
	* 启动:service mysqld start
		* 初始化 MySQL 数据库： /usr/sbin/mysqld: error while loading shared libraries: libnuma.so.1: cannot open shared object file: No such file or directory
		[失败]
		* 还要安装一个依赖(官网说是其中老版本的一个bug)
			* yum -y install numactl
			* http://www.21yunwei.com/archives/5480
	* 然后执行
		* service mysqld start
			* 初始化 MySQL 数据库： [失败]
		* service mysqld start
			* 正在启动 mysqld： [确定]
		* service mysqld status
			mysqld (pid  2008) 正在运行...	
6. 登入
	* 默认情况没有密码
	* 如果ERROR 1045 (28000): Unknown error 1045
		* 关闭mysql服务 
			* service mysqld stop   |  service mysqld restart
		* vi /etc/my.cnf
			* 在[mysqld]项下添加skip-grant-tables
		* 启动mysql服务
		* 登入mysql
	* 修改密码
		* use mysql;
		* 老版本
			* update user set password = password('123') where user = 'root';
		* 新版本(user表里没有password字段了,改成authentication_string)
			* update user set authentication_string = password('123') where user = 'root';
			* UPDATE user SET authentication_string=PASSWORD('123') where USER='root' and host='root' or host='localhost';
		* flush privileges;# 刷新
	* 还原 mysql配置
		* vi /etc/my.cnf
		* 注释 #skip-grant-tables
	* 测试
		* service mysqld restart
		* mysql -uroot -p123
		* show databases;
			* 如果ERROR 1820 (HY000): Unknown error 1820
				* 执行:SET PASSWORD = PASSWORD('1234');		//需要重新设置一次密码

7. 连接
	1. 问题一: 防火墙被拦
		* Can't connect to mysql server on 192.168.xx.xx (10038,10060)
	2. 问题二:默认不能远程连接
		* 远程连接出现 Error 1130
	3. 账号密码有误
		* 1045
8. 防火墙,开放3306端口
	3306端口放行 
		/sbin/iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
	将该设置添加到防火墙的规则中
		/etc/rc.d/init.d/iptables save

9. 开启远程访问
	grant all privileges on *.* to 'root' @'%' identified by '1234';
	flush privileges;


----------
# tomcat #
1. 检测
	* rpm -qa | grep tomcat
	* rpm -e --nodeps   卸载的包名
2. 上传
 	* 自己下载,然后上传到服务器
	 	* http://tomcat.apache.org/
	 	* apache-tomcat-8.5.23.tar.gz
	* FileZilla上传fto软件,root目录下
	* /usr/local 共享目录下,创建一个tomcat目录,
		* cd /usr/local 
		* mkdir tomcat
	* 把root下的jdk cp到tomcat目录下面	
		* cp /root/apache-tomcat-8.5.23.tar.gz ./tomcat/
3. 安装
	* 解压
		* tar -xvf apache-tomcat

4. 启动
	1. 方式(关闭一样)
		* sh startup.sh
		* ./startup.sh
			* Tomcat started.
	2. 查看日志
		* tail -f ../logs/catalina.log
			* ctrl + c 退出
5. 访问
	192.168.126.134:8080
6. 防火墙,开放端口
	* 8080端口放行 
		/sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
	* 将该设置添加到防火墙的规则中
		/etc/rc.d/init.d/iptables save

7. 关闭
	1. kill -9 进程号
		* ps -aux | grep tomcat
	2.  ./shutdown.sh 

----------
# 项目发布 #
1. 数据库
2. 项目war发布
	1. windows,linux 都是
		jar -xvf xx.war
	 
