# css回顾 #
1. 引用 内联(属性style) 内部 外部(<link>)
2. 找爷爷的工程(**重点**)->选择器 .类名 , #id,标签,标签属性,div span{ background:red}
3. css的属性和值(了解)
4. 浮动 float :left  -> clear:both ->display:none
5. 盒子模型 : padding , border ,margin-
6. *-top 上右下左   10px,20px


# body 盒子 #



----------

# 布局 position 属性#
## 值 ##
1. static(默认):等于没写
2. relative:相对自己
3. absolute:绝对,body标签
4. fixed:固定位置

## 影响属性 ##
1. top right bottom left
2. -10px
3. z-index :0   -1 



----------

----------
# 美迪 # 
	完整版 demo4.html
1. 网页分析
	1. 公共头
	2. 公共脚
	3. 内容面板:可以切换





## css分类 ##
1. 公共(任意网站都可以用)部分样式:css
2. 公共(当前网站的特色,头部,底部)部分样式:css
3. 每个内容:多个css
4. 网站就是很多很多网页
		网站 1:n 网页


# 开发步骤 #
1. 三个外部css(建议写在一个css中,不需要不停切换,注释好就行)
	1. common.css
	2. common_head.css
	3. common_foot.css
2. 在index页面先定义3个div
3. 在common.css中定义
	1. 浮动
	2. 清除浮动
	3. 内容居中
	4. 居中容器
			.reset {
				margin: 0;
				padding: 0;
			}
			.float-left {
				float: left;
			}
			
			.clear-float {
				clear: both;
			}
			.public-container {
				position: relative;
				margin: 0 auto;
				width: 950px;
			}
			a{
				text-decoration:none;
			}	
		
4. 结合common.css让head居中
5. 在common_*.css 定义头部css 高度(三个部分定义完毕)
		<div class="public-head">
		<div class="public-content1">
		<div class="public-foot">
		
		.pulbic-*{
			height: 500px;
			background: yellowgreen;
		}

6. 开始头部
			<!--顶部线-->
			<div class="head-line web-bg-color" />


			<!--logo行-->
			<div>
				<!--logo-->
				<div class="center-container">
					<!--logo需要往下走 margin-top:30px -->
					<div class="float-left head-logo">
						<img src="../img/top-logo.jpg" />
					</div>
					
					<!--描素-->
					<div class="float-left head-logo">
						<span class="head-desc ">
							国家二级资质施工企业<br/> 
							湖湘唯一荣获全国200项室内s设计大奖企业<br/>
							 官方唯一指定《中国高级家装标准》实施单位
						</span>
					</div>
					<!--微博-->
					<div class="float-left  head-logo" style="margin-left: 30px;">
						<div style="margin-left: 20px;">
							<img src="../img/weibo-logo.jpg" />
						</div>
						<div>
							<span class="head-link-">
								<a href="#">美迪官方微博</a>
							</span>
						</div>
					</div>
					<div class="float-left  head-logo" style="margin-left: 30px;">
						<div style="margin-left: 20px;">
							<img src="../img/weibo-logo.jpg" />
						</div>
						<div>
							<span class="head-link-">
								<a href="#">美迪官方微博</a>
							</span>
						</div>
					</div>
				<!--电话-->
				</div>
			</div>
7. 清空浮动
	1. <div class="clear-both"></div>
8. 菜单栏
			<!--超链行  菜单-->
			<div style="background: #173983; width: 100%; height: 50px;">
				<div class="center-container">
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
					<div class="float-left head-menu">
						<a href="#">首页</a>
					</div>
				</div>
			</div>
			
				.head-menu{
					height: 50px;
					width: 10%;
					text-align: center;
					border-right: 1px solid white;
				}
				.head-menu a{
					color: white;
					line-height: 50px;
					height: 50px;
					font-family: "仿宋";
					font-size: 20px;
				}

# 中间 #	
1. 广告条
		<!--中间内容-->
		<div class="content1">
			<!--大广告 banner-->
			<div class="center-container">
					<img src="../img/big-add.jpg"/>				
			</div>

		</div>


# 注意地方 #
1. a标签背景颜色
	1. 设置display: inline-block;
	2. 然后background
2. 颜色
	1. color: rgb(r,g,b);


## 特殊字符转义 ##
1. &
2. 名字
3. ;
4. &nbsp   &copy;







国家二级资质施工企业<br/> 湖湘唯一荣获全国200项室内s设计大奖企业
<br/> 官方唯一指定《中国高级家装标准》实施单位

美迪官方微博

82221078<br/> 82228608


首页
走进美迪
新闻资讯
精品案例
顶级团队
博格工艺
视频欣赏
客户评价
小区团购
美迪大宅

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
美迪简介&gt;<span style="color: #0000FF;">董事长致辞</span>&gt;获取荣誉&gt;

XCELLENT


南山-苏迪亚若 别墅
POSITIME 2013.07


# 超链接宽高设置 #
1. display: inline-block


# 去掉input边框 #
1. 边框0
2. 背景透明