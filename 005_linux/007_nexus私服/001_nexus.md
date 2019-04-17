**先前环境**

# 问题 #
1. ifconfig没有指令
	1. CentOS-7-x86_64-Minimal-1810.iso
	2. 没有 ifconfig
		1. 虚拟机中以最小化方式安装centos7，ifconfig命令无效，而且在sbin目录中没有ifconfig文件。
		2. 已经用**ip**命令代替
			1. ip addr 即查看分配网卡情况
			2. ens33 inet 192.168.58.1/xxx 若果没有，就要激活网卡

2. 不能上网，网卡没激活
	1. 激活网卡：在文件 /etc/sysconfig/network-scripts/ifcfg-enp0s3 中 
			vi //etc/sysconfig/network-scripts/ifcfg-ens33
				ONBOOT=no 改为 ONBOOT=yes
			保存后重启网卡： service network restart 
			
# scrt 配置 #
1. options -> global options -> Default Sessiom
	1. Appearance -> color
	2. Appearance -> Character encoding


----------
**安装**
```
No suitable Java Virtual Machine could be found on your system.
The version of the JVM **must be** 1.8.
Please define INSTALL4J_JAVA_HOME to point to a suitable JVM.
```
# 安装jdk 必须1.8！ #

# 创建用户 #
	useradd nexus
	passwd nexus
	chown -R mysql:mysql ./   

	ps -aux | grep nexus
# 安装nexus #
1.  [下载开源版 Nexus OSS：]( https://www.sonatype.com/download-oss-sonatype ) 
2.  unix.tar.gz版本，上传
3.   tar -xvf nexus-3.16.1-02-unix.tar.gz
4.   启动 bin/
	1.  ./nexus start

https://download.oracle.com/otn/java/jdk/8u211-b12/478a62b7d4e34b78b671c754eaaf38ab/jdk-8u211-linux-x64.tar.gz

# 关闭防护墙 #
1.  systemctl stop firewalld
2.  开放端口	
	firewall-cmd --zone=public --add-port=8081/tcp --permanent
	systemctl restart firewalld.service
# 访问 #
http://192.168.58.128:8081/

# 配置 #
1. 安装目录下面 ./etc/nexis-default.properties
	1. 端口 application-port=8081
	2. 路径	nexus-context-path=/
2. 

# 去掉 root 启动warning #
	去掉上面WARNING的办法：
	 vim /etc/profile
	......
	export RUN_AS_USER=root
	 source /etc/profile

# 开机重启 #
	https://www.jianshu.com/p/086fd186c44c
1. 配置 	
	1. vi /usr/lib/systemd/system/nexus.service
	2. vi /etc/systemd/system/nexus.service
		1. 2个选一个

[Unit]
Description=nexus service
After=network.target
  
[Service]
Type=forking
LimitNOFILE=65536
ExecStart=/opt/nexus/nexus-3.16.1-02/bin/nexus start
ExecStop=/opt/nexus/nexus-3.16.1-02/bin/nexus stop
ExecReload=/opt/nexus/nexus-3.16.1-02/bin/nexus restart
User=nexus
Restart=on-abort
  
[Install]
WantedBy=multi-user.target

2. 启动	
	    systemctl daemon-reload
		systemctl stop nexus
		systemctl stop nexus
		systemctl restart nexus
		systemctl enable nexus.service
		
		ps -aux | grep nexus

3. 日志
	 	 tail -f /opt/nexus/sonatype-work/nexus3/log/nexus.log



----------
# 系统使用 #
## Repositories  ##
	仓库分为三种：Proxy、hosted、group
1. Proxy	代理
	1. 代理中央Maven仓库
	2. PC访问中央库的时候，先通过Proxy下载到Nexus仓库，然后再从Nexus仓库下载到PC本地
2. hosted	宿主机
	1. 用于将第三方的Jar或者我们自己的jar放到私服上
	2. 三中方式
		1. Releases	一般是已经发布的Jar包
		2. SNAPSHOT	未发布的版本
		3. Mixed	混合的
3. group 组合
	1. 能把多个仓库合成一个仓库来使用

## Security ##
1. 包含用户、角色、权限的配置。

## Support ##
1. 包含日志及数据分析。

## System ##
1. 包含API（Nexus这个API文档貌似是用swagger做的）、邮件服务器，设置调度任务等。
