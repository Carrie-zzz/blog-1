# jquery表单校验 #
1. 可以jq派发事件 submit,麻烦
2. 通过validate插件来校验表单

# validate #
1. jquery的封装好的库,必须要有jquery

## 下载 ##
 https://jqueryvalidation.org/

## 导入 ##
1. jquery
2. 校验库
3. 国际化库(可选)


	<!--依赖的jQuery库-->
	<script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8" />
	<!--validate校验库-->
	<script src="../js/jquery.validate.js" type="text/javascript" charset="utf-8" />
	<!--国际化库，中文提示（可选）-->
	<script src="../js/messages_zh.js" type="text/javascript" charset="utf-8" />

## 使用 ##
1. 导入
2. 页面加载成功后,进行表单校验 $("选择器").validate()
3. 在validate中编写规则
		$("选择器").validate({
			rules:{},
			messages:{}
		});

|校验类型|	取值 |	描述|
| -----------| :-----:  | :----: |
|required	|  true/false |	必填字段|
|email	    | “@”或者”email” |邮件地址|
|url	|..	|路径|
|date	|..|	日期|
|dateISO	|字符串	|日期（YYYY-MM-dd）|
|number		|..	|数字（负数，小数）|
|digits		|..	|整数|
|minlength	|数字|	最小长度|
|maxlength	|数字	|最大长度|
|rangelength | [minL,maxL] |	长度范围|
|min		|..	|最小值|
|max		|..	|最大值|
|range	| [min,max] |	值范围|
|equalTo	|jQuery表达式	|两个值相同|
|remote|	url路径	|ajax校验|



----------

----------

# 响应式布局 #
1. 根据上网设备的不同自动调节显示的效果.
2. bootstrap:web前端的css框架,基于html,css,js,jquery
3. css  <a class="bootstrap_css" >xx</a>


# 步骤 #
1. 下载
2. 导入 jq.js ,bootstrap.css,bootstrap.js
3. 在头里面添加 meta 标签 视口 
	< meta name="viewport" content="width=device-width, initial-scale=1">
4. initial-scale 初始化缩放比, 1为屏幕一样
5. 在body中,放入布局容器中  类型container


	.container 类用于固定宽度并支持响应式布局的容器。
	
	<div class="container">
	  ...留白
	</div>
	.container-fluid 类用于 100% 宽度，占据全部视口（viewport）的容器。
	
	<div class="container-fluid">
	  ...
	</div>

# 栅格系统 #
1. 一行12份


## 屏幕分配 ##
1. 大屏幕:col-lg-    每个占多少
2. 中屏幕:col-md-
3. 小屏幕:col-sm-
4. 超小屏幕:col-xs


1. Large,Middle,Small,xSmall


案例-美迪


# 组成 #
1. css样式
2. 组件
3. js插件


# css样式 #
1. bg-success
2. btn-lg
3. img-circle
4. pull-
5. clear
6. hidden-lg

## 排版 ##
1. h标签:换行,留白

## 表格 ##


## 表单 ##
1. table
2. table-striped 条纹
3. table table-hover


## 颜色 ##
1. success 绿色
2. info    蓝色
3. warning 黄色
4. daner   红色
5. defualt 灰色
6. primary 深蓝色



## 按钮 ##
1. 激活: active
2. 不可用:disabled
3. 

## 图片 ##
1. img-circle
2. img-rounded


## 辅助类 ##
1. pull-left -> float: left !important;//重要的!
2. clearfix :清除浮动
3. hidden
4. show
5. hidden-xs

----------

# 组件 #






----------

----------

----------
# 表单 #




# bootstrapValidator 表单验证 #
1. 下载
2. 导入js就行
3. 在页面加载完成事件后
4. 选择器找到form然后验证

1. feedbockIcons
2. fields

## feedbackIcons ##
1. valid:''
2. invalid:''
3. validating:


## fields ##
		name:{
			validators:{
				校验器:{
					message:'',
					...,
					...
				}
			}
		}


## 验证器 ##
		notEmpty : { //非空验证
			message : '用户名不能为空'
		},
		stringLength : { // 长度验证
			min : 6,
			max : 20,
			message : '用户名长度需为6-20'
		},
		regexp : { // 正则式验证
			regexp : /^[0-9a-zA-Z_]+$/,
			message : '用户名只能为字母、数字、下划线'
		},
		different: { //不同验证
            field: 'password',
            message: '用户名不能与密码相同'
        },
        remote: {
        	type:'POST',
        	url:'${basePath}/checkUserName.do',
        	message:'该用户名已被占用'
        },
		emailAddress: {
		  message: 'The input is not a valid email address'
		},
		identical: {
            field: 'password',
            message: '两次密码不一致'
        },
		date: {
			format: 'YYYY/MM/DD',
			 message: 'The birthday is not valid'
			 }

[教程](http://blog.csdn.net/u013938465/article/details/53507109)

http:blog.csdn.net/u013938465/aticle/details/53507109



# 使用 #
		$("#registerForm").bootstrapValidator({
			fields : {
				inputNameValue:{
					validators:{
						notEmpty:{
							message:'不空'
						}
					}
				}
			}
		});

	重置
		$('#registerForm').data('bootstrapValidator').resetForm(true);
