# 大纲 #
192.168.131.129


超链接： [官网](http://spring.io/)

图片  ： ![ioc](./img/spring01.png)

表格：

| ..	| ....	| ....	|
| ---	| :---: | :----:|
| ..	| .. 	| ..	|
| ..	| ..   	| ..	|
| ..	| ..   	| ..	|


----------

----------

----------
# xp系统 #
	1. 准备
		* 使用时候复制一份
		* 如果是一个镜像:Windows XP Professional.vmdk	
			盘符格式必须是 NTFS    不能是FAT32,要求单个文件不能超过4g
	2. 安装
		1. 自定义安装
		2. 硬件兼容选5.x   vmdk 是5.x制作的
		3. 稍后安装
			* 光盘(需要光盘+光驱,很少用)
			* iso文件(类似ghost)
			* 稍后安装:先配置虚拟机
		4. 版本  
			* windows -> Windows XP Professional
			* centOs    
			* centOS 64位
				* 默认32位
		5. 名称 路径
		6. 处理器	直接下一步
		7. 内存 : 建议 2g
		8. 网卡 : 使用
			1. 桥接	:需要经过路由器,才能和本机交互
			2. NAT	:需要经过路由器,才能和本机交互
			3. 仅主机模式网络(建议使用) :
				不管本机能不能上网,虚拟机都可以和本机交互
				本机和虚拟机,ip自定设置完成
		9. i/o,	直接下一步
		10. 磁盘类型
		11. 选择磁盘
			使用现有磁盘(使用拷贝一份)
	3. ip问题
			虚拟机1p:  192.168.131.128
		网卡 VMware Network Adapter VMnet1   192.168.131.1
			主机
	4. 文件共享(如果重新拷贝到虚拟机,太浪费空间了)
		1. 选择虚拟机 ->设置 ->选项 ->共享文件夹 -> 总是启用 ->添加
		2. 网络驱动
			虚拟机上 我的电脑->菜单栏的工具->映射网络驱动器->浏览->最下面的VMware 共享文件夹->展开->确定->多处一个z盘



----------
# 虚拟机修改名字 #
	菜单栏->虚拟机->设置->选项->虚拟机名称(右边)




----------

----------

----------
# centOS #
1. 下载
	http://mirrors.aliyun.com/
	http://vault.centos.org/
		isos/x86_64/minimal.iso

2. 安装
	1. 自定义安装
	2. 硬件兼容选
	3. 稍后安装
		* 光盘(需要光盘+光驱,很少用)
		* iso文件(类似ghost,**快速安装**,没有选项)
		* 稍后安装:先配置虚拟机
	4. 版本  
		* windows -> Windows XP Professional
		* centOs    
		* centOS 64位
			* 默认32位
	5. 名称 路径(安装路径)
	6. 处理器	直接下一步
	7. 内存 : 建议 2g
	8. 网卡 : 使用
		1. 桥接	:需要经过路由器,才能和本机交互,自动上网,
			1. 主机作为交换机
			2. 虚拟机和主机**网段**一致,每台虚拟就占用一个ip地址
					如果你的网络环境是ip资源很缺少或对ip管理比较严格的话，那桥接模式就不太适用了
			3. 虚拟机需要上网,必须 **网关与DNS**需要与主机网卡一致
		2. NAT(地址转换模式)	:需要经过路由器,才能和本机交互,  (不正确~~)  ip自动获取
			1. 主机网卡直接与虚拟NAT设备相连,然后虚拟NAT设备与虚拟DHCP服务器一起连接在虚拟交换机VMnet8上
			2. 新建一个虚拟网段,不需要分配公司网段
			3. 默认动态获取ip,可以设置静态ip
				1. vi /etc/sysconfig/network-scripts/ifcfg-eth0
		3. 仅主机模式网络(建议使用) :
			不管本机能不能上网,虚拟机都可以和本机交互
			本机和虚拟机,ip自定设置完成
	9. i/o,	直接下一步
	10. 磁盘类型
	11. 选择磁盘
		使用现有磁盘(使用拷贝一份)  适用于:vmdk类型文件
		创建新的磁盘				 使用与:iso类型  (选择)
	12. 选择完毕后,选择菜单栏->虚拟机->设置->硬件->CD/DVD->关联自己的iso
	13. 启动虚拟机

3. 安装教程
	1. ctrl + alt 出虚拟机
	2. install or update an existing system
	3. disc found  -> skip
	4. 语言(中文)  ,键盘 (美国英语试)
	5. 基本存储设备
	6. 确保安装文件夹为空(需要格式化)->是,忽略所有数据
	7. 主机名(随便起名)->配置网络->system(点击两下)->勾选自动连接
	8. 时区
	9. 密码(过于简单->无论如何适应)(密码是root 的密码
	10. 安装类型(随便)
	11. 设置写入磁盘
	12. 默认安装
		* desktop
		* basic server (没有桌面,视频安装)
		* minimal
	13. 等待安装

----------

----------

----------
# linux #

----------


# unix介绍 #
[介绍](https://baike.baidu.com/item/unix/219943?fr=aladdin)
	Unix是一个强大的多用户、多任务操作系统。 
	于1969年在AT&T的贝尔实验室开发。 
	UNIX的商标权由国际开放标准组织（The Open Group）所拥有。
	UNIX操作系统是商业版，需要收费，价格比Microsoft Windows正版要贵一些。

# linux介绍 #
[介绍](https://baike.baidu.com/item/linux)
	Linux是基于Unix的
	Linux是一种自由和开放源码的操作系统，存在着许多不同的Linux版本，但它们都使用了Linux内核。
	Linux可安装在各种计算机硬件设备中，比如手机、平板电脑、路由器、台式计算机
			1. centOS
			2. UBUNTU
			3. REDHAT
			4. SUCE
			5. 红旗

# linux版本 #
	Linux的版本分为两种：内核版本和发行版本； 
		* 内核版本是指在Linus领导下的内核小组开发维护的系统内核的版本号 ； 
		* 发行版本是一些组织和公司根据自己发行版的不同而自定的 ；


----------

# linux目录 #
	1. 就一个盘符  /  , 称根
	2. 不区分文件,文件夹(目录),linux所有东西称为文件(属性标识他是文件还是文件夹)
	3. linux给每个用户提供一个文件夹
	4. 根下面文件结构
		/---home --- 所有普通用户存放的文件夹
			|	  : user1 :具体用户存放的文件夹
			|	  : user2	 :
		/---root --- 超级用户存放文件夹
		/---etc  --- 存放配置文件
					 (etcetera)
		/---usr	 --- 存放共享的资源,所有用户可以获取	
					(unix shared resources)
		/---以上为重点
		/---bin --- 系统启动时需要的执行文件（二进制）
		/---sbin --- 存放二进制文件,只有root可以访问
					(super user binaries)
		/---boot --- 引导加载器所需文件，系统所需图片保存于此
		/---dev(device) --- 设备文件目录
		/---lib --- 存放系统运行需要的类库
		
		
# 指令 #
1. 切换文件夹
	* cd 文件夹
	* cd 文件夹/文件夹/文件夹/..
	* cd ..     上一层文件夹
	* cd /		根文件夹
	* cd ~		回家
	* cd -		上次目录
2. 小指令
	1. 当前路径
		* pwd
	2. 屏幕清空
		* ctrl +l
		* clear
3. 文件夹
	* mkdir [option] 文件夹名
		* mkdir test	创建一个文件夹(只能一层)
		* 参考参数  man mkdir
			* 空格翻页
			* 出去按:q
		* 如果不能使用man 没有请先安装(确保能上网):  yum install man
		* 如果不能使用bc命令也是执行 :yum install bc
		* mkdir -p a/b/c
	* rmdir [option] 文件夹名
		* rmdir 文件夹名	只能创建一个空目录
		* rmdir -p 文件夹名	递归删除,不推荐使用!
4. 展示目录
	* ls	
		* 显示所有课件文件(和文件夹)的名称
	* ls -a 
		* 显示所有文件(和文件夹)的名称,包含隐藏文件
		* 文件以点开头称之为隐藏文件  .xx
	* ls -l 
		* 显示详细信息
		* 缩写->  ll (重点)
	* ls -lh
		* 友好的显示详细信息
			* 大小多少K , M
		* 缩写-> ll -h 
	

----------
## 文件操作 ##
1. 查看文件
	* cat	文件名
		*　显示文件所有内容
		*　如果文件很长,前面部分看不到
	* more	文件名
		*　分页显示
		*　空格:下一页
		*　回车:下一行
		*　没有上一页
	* less	文件名
		*　分页显示
		*　pgup:上一页
		*　pgdown:下一页
		*　空格:下一页
		*　回车:下一行
		*　q退出
	* tail	[-行数] 文件名
		*　尾巴:查看文件某末尾内容

	* tail -f 文件名 
		*　动态内容查看:tomcat日志
		*　结束 :ctrl + c
	
2. 创建文件
	* touch 文件名(后缀名在linux下没有意义)
		* 创建空白文件

3. 移动文件
	* cp 源文件(夹)	目标文件(夹)
		* 拷贝
	* mv 源文件 	目标文件(夹)
		* 移动
		* 重命名(同一个文件下)

4. 删除文件
	* rm 文件名
		* 询问删除
	* rm -f 文件名
		* 不询问
	* rm -r 目录
		* 询问的递归删除
	* rm -rf
		* 不询问,递归删除(危险!!!)
5. 压缩,解压
	* 常用参数： 
		-c：创建一个新tar文件 
		-v：显示运行过程的信息 
		-f：指定文件名 
		-z：调用gzip压缩命令进行压缩  
		-t：查看压缩文件的内容 
		-x：解开tar文件
	* 压缩
		* 格式
			* tar -参数 压缩|解压的文件
		* tar -cvf  打包
		* tar -zcvf 打包,并压缩(格式gzip)
		* 
		*  
	* 解压
		* tar -参数 解压的文件  
			* 解压到当前文件夹下
		* tar -参数 解压的文件 -C 解压到的路径
		* tar-xvf 打开,解压


----------

## 编辑 vi ##
![](vi.png)
1. 模式
	* 命令行模式:等待输入指令
	* 插入模式:编辑内容
	* 底行模式:退出,保存操作

2. 命令
	* 转插入模式:
	    i 在当前位置生前插入
	    I 在当前行首插入
	    a 在当前位置后插入
	    A 在当前行尾插入
	    o 在当前行之后插入一行
	    O 在当前行之前插入一行
	* 转底行模式:
		:（冒号）
3. 插入
	* 转命令模式
		* esc

4. 底行
	* 转命令模式
		* esc
	* 退出
		* q! 强制退出
		* wq 先修改然后退出

5. 查找
	1. 底行情况下输入 /关键字
	2. 回车
	3. 按 n 下一个
	4. 按 shift n 上一个  

6. 复制一行
	* 在命令模式下 按 yyp


## grep ##
1. 内容中查找
2. 格式:
	* grep 关键词 文件
		* grep cate yum.conf --color
	* grep 关键词 文件 --color
	* grep 关键词 文件 --color -A2|B2
		* A行数 查看匹配后的后几行
		* B行数 查看匹配后的前几行 

## 管道 | ##
1. 一个输出作为另外一个的输入

	管道是Linux命令中重要的一个概念，其作用是将一个命令的输出用作另一个命令的输入。 示例
	ls --help | more  分页查询帮助信息
	ps –ef | grep java  查询名称中包含java的进程
	
	ifconfig | more
	cat index.html | more
	ps –ef | grep aio
		-a 代表 all
		-e 显示所有进程信息
		-o 参数控制输出
		-f 参数来查看格式化的信息列表

-------------------------

	
## wget ##
1. 下载
2. 格式
	* wget 路径
	* wget www.baidu.com

## 重定向输出>和>> ##
	> 重定向输出，覆盖原有内容； 
	>> 重定向输出，又追加功能； 示例：
	cat /etc/passwd > a.txt  将输出定向到a.txt中
	cat /etc/passwd >> a.txt  输出并且追加
	
	ifconfig > ifconfig.txt


## &&命令执行控制 ##
	命令之间使用 && 连接，实现逻辑与的功能。  
	只有在 && 左边的命令返回真（命令返回值 $? == 0），&& 右边的命令才会被执行。  
	只要有一个命令返回假（命令返回值 $? == 1），后面的命令就不会被执行。
	
	mkdir test && cd test



-------------------------
## 网络命令 ##
	ifconfig  显示或设置网络设备。
		ifconfig  显示网络设备
		ifconfig eth0 up 启用eth0网卡
		ifconfig eth0 down  停用eth0网卡 
	ping   探测网络是否通畅。
		ping 192.168.0.1 
	netstat 查看网络端口。
		netstat -an | grep 3306 查询3306端口占用情况
		windows下 netstat -ano

----------
## 系统命令 ##
1. date 显示或设置系统时间
	date  显示当前系统时间
	date -s “2018-01-01 10:10:10“  设置系统时间 
2. clear 清屏幕 
3. ps 正在运行的某个进程的状态
	ps –ef  查看所有进程
	ps –ef | grep ssh 查找某一进程
4. kill 杀掉某一进程
	kill 4433  杀掉4433编号的进程
	kill -9 4433  强制杀死进程

5. who 显示目前登入系统的用户信息。

6. uname 显示系统信息。
	uname -a 显示本机详细信息。 
	依次为：内核名称(类别)，主机名，内核版本号，内核版本，		 内核编译日期，					  硬件名，处理器类型，硬件平台类型，操作系统名称
			Linux 		t2 		2.6.32-642.el6.x86_64 #1 SMP Tue May 10 17:27:01 UTC 2016 x86_64 x86_64 x86_64 			GNU/Linux

----------

----------

----------
# 用户和组 #
1. 添加
	1. useradd 用户名
	2. 将会在home 下创建一个文件夹
2. 密码
	1. passwd 用户名
3. 切换
	1. su - 用户名   
	2. id 
		* 查看当前用户的UID和GID 
4. 删除
	1. userdel 用户名
		* 只会删除用户
		* /home 文件夹 需要手动删除 rm -rf tom
	2. userdel -r 用户名


# 组 #
1. 当在创建一个新用户user时，若没有指定他所属于的组，就建立一个和该用户同名的私有组 
2. 创建
	1. groupadd 组名
		1. useradd 用户名 -g 组名 
			* 指定用户组
2. 删除: 如果该组有用户成员，必须先删除用户才能删除组。
	1. groupdel 组名



# 权限 #
1. linux常见文件类型
	1. - :文件
	2. d :文件夹
	3. l : 链接
	4. 其他:字符设备文件（c） 块设备文件（s） 套接字（s） 命名管道（p）
	
2. 权限 rwx
![](001.png)
3. 修改权限
	chmod 变更文件或目录的权限。
		chmod 755 a.txt 
		chmod u=rwx,g=rx,o=rx a.txt (不推荐使用)
4. 修改拥有者
	chown 变更文件或目录改文件所属用户和组
		chown 用户名:组名 a.txt	：变更当前的目录或文件的所属用户和组
		chown -R 用户名:组名 dir	：变更目录中的所有的子目录及文件的所属用户和组

----------

# 远程工具 #
1. 使用虚拟机,进入,出来太麻烦
2. 工具类型
	1. scrt
	2. ssh
	3. ftp
3. 破解
4. 连接 sCrt
	1. 查看ip: ifconfig
		* 192.168.126.132
	2. 账号密码
	3. ll乱码
		* 菜单栏->options->session option
		* terminal->apperance->右边 character encoding ->utf-8
		* 乱开 , 连接

5. 关闭vmware,虚拟机在后台运行!




----------
过时:看002

# 环境 #
1. jdk
2. mysql
3. tomcat



## jdk ##
1. 查看系统是否自带jdk : java -version
	* 卸载自带的:  
		* 查看:rpm -qa | grep java
		* 卸载:rpm -e --nodeps 卸载的包名

2, 上传
	* 自己下载
		* 
	* 然后上传到服务器
		* FileZilla上传ftp软件,root目录下
		* crt  自带ftp传输
	* /usr/local 共享目录下,创建一个java目录,把root下的jdk cp到java目录下面	
		* cp /root/jdk /usr/loca/java/

3. 安装
	* 解压
		* tar -xvf jdk
	* 配置环境变量
		* vi /etc/profile  最后追加:
			######set java environment  ;;pwd jdk path
			JAVA_HOME=/root/jdk-9
			CLASSPATH=.:$JAVA_HOME/lib.tools.jar
			PATH=$JAVA_HOME/bin:$PATH
			export JAVA_HOME CLASSPATH PATH
		* 没有效果 java -version
			-bash: java: command not found
		* 使配置文件生效: source /etc/profile
			-bash: /usr/local/java/jdk1.7.0_72/bin/java: /lib/ld-linux.so.2: bad ELF interpreter: 没有那个文件或目录
	* 安装jdk依赖软件
		* yum install glibc.i686

	* 测试成功 
		[root@ly jdk1.7.0_72]# java -version
		java version "1.7.0_72"
		Java(TM) SE Runtime Environment (build 1.7.0_72-b14)
		Java HotSpot(TM) Client VM (build 24.72-b04, mixed mode)


## mysql ##
1. 检测
	* rpm -qa | grep mysql
	* rpm -e --nodeps   卸载的包名
2. 上传
 	* 自己下载,然后上传到服务器
	* FileZilla上传fto软件,root目录下
	* /usr/local 共享目录下,创建一个mysql目录,把root下的mysql cp到mysql目录下面	
		* cp /root/MySQL.rpm-bundle.tar  ./mysql
3. 安装
	* 解压(最好创建一个文件夹)
		* tar -xvf jdk
		* 几个rpm文件
			-rw-r--r--. 1 root root  184422400 10月  7 21:11 MySQL-5.5.49-1.linux2.6.i386.rpm-bundle.tar
			-rw-r--r--. 1 7155 31415  17323527 3月   3 2016 MySQL-client-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415   6357563 3月   3 2016 MySQL-devel-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415  63001528 3月   3 2016 MySQL-embedded-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415  49843416 3月   3 2016 MySQL-server-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415   1972313 3月   3 2016 MySQL-shared-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415   5366709 3月   3 2016 MySQL-shared-compat-5.5.49-1.linux2.6.i386.rpm
			-rw-r--r--. 1 7155 31415  40543917 3月   3 2016 MySQL-test-5.5.49-1.linux2.6.i386.rpm
	* 安装
		* rpm -ivh MYSQL-SERVER



10-7 工作总结  刘洋
1. 督查1701项目
2. linux部分课件

