https://www.layui.com/admin/
http://layer.layui.com/
# 主要用 #
1. 后台框架
2. form
3. layer
4. 样式



# 规范 #
1. 属性   :layui开头  -分割    功能
		layui-clear

layui-col-lg
layui-col-md
layui-col-sm
layui-col-xs
		

页面元素

# 栅格 #
0. 居中容器
	<div class="layui-container">
1. 行,列
	<div class="layui-row">
	<div class="layui-col-等级*">
2. 容器进行了 12 等分 
3. 预设了 4*12 种 CSS 排列类 
	* 格式
		1. layui-col-等级+占比
		2. layui-col-md3
			* 一行占比 四分之一(3/12)
4. 注意
	1. 一般包裹在自定义行中(添加行样式)
		1. <div class="layui-row"></div>
5. 等级
	1. layui-col-xs*	
	2. layui-col-sm*
	3. layui-col-md*
	4. layui-col-lg*
		超小屏幕(手机<768px)	
		小屏幕(平板≥768px)
		中等屏幕(桌面≥992px)
		大型屏幕(桌面≥1200px)

# 颜色 layui-bg- #
1. layui 内置了七种背景色，以便你用于各种元素中，如：徽章、分割线、导航等等

		赤色：class="layui-bg-red"
		橙色：class="layui-bg-orange"
		墨绿：class="layui-bg-green"
		藏青：class="layui-bg-cyan"
		蓝色：class="layui-bg-blue"
		雅黑：class="layui-bg-black"
		银灰：class="layui-bg-gray"

# 图标 layui-icon #
1. 阿里矢量图,图标看成普通的文本
	<i class="layui-icon">&#xe60c;</i>   
	<a class="layui-btn"> <i class="layui-icon">&#xe60c;</i>图标按钮</a>	
# 动画 layui-anim #
	<div id="b" onclick="b(this)" class="layui-anim layui-anim-up">
		xx
	</div>

	function b(v){
		v.removeAttribute('class') ;
		//需要等待一会
		setTimeout(function(){
			v.setAttribute('class', 'layui-anim layui-anim-up') ;
		},1);
	}

# 按钮 layui-btn #
1. layui-btn-primary
2. layui-btn-lg
3. layui-btn-radius
4. layui-btn-disabled
5. 图标按钮
	<button class="layui-btn layui-btn-sm layui-btn-primary">
	  <i class="layui-icon">&#x1002;</i>
	</button>

		名称	组合
		原始	class="layui-btn layui-btn-primary"
		默认	class="layui-btn"
		百搭	class="layui-btn layui-btn-normal"
		暖色	class="layui-btn layui-btn-warm"
		警告	class="layui-btn layui-btn-danger"
		禁用	class="layui-btn layui-btn-disabled"

		尺寸	组合
		大型	class="layui-btn layui-btn-lg"
		默认	class="layui-btn"
		小型	class="layui-btn layui-btn-sm"
		迷你	class="layui-btn layui-btn-xs"

		主题	组合(圆角)
		原始	class="layui-btn layui-btn-radius layui-btn-primary"
		默认	class="layui-btn layui-btn-radius"
		百搭	class="layui-btn layui-btn-radius layui-btn-normal"
		暖色	class="layui-btn layui-btn-radius layui-btn-warm"
		警告	class="layui-btn layui-btn-radius layui-btn-danger"
		禁用	class="layui-btn layui-btn-radius layui-btn-disabled"

# 表单(元素+模块) #
			    	验证:lay-verify：注册form模块需要验证的类型 
			    		1. lay-verify="required"
			    		2. lay-verify="required|title"
			    		3. lay-verify="required|number"
			    		4. 参数
			    			required
			    			title   	长度>4
			    			pass		6-12位
			    			number
			    			email
			    	弹出形式:
			    		1.不填(默认) 弹窗,左右摇晃
			    		2. lay-verType="tips"	input旁边提示信息
			    		3. lay-verType="alert"	alert弹窗



					<script src="../src/layui.js" type="text/javascript" charset="utf-8"></script>
					<script>
						layui.use('form', function(){
						  var form = layui.form;
						  //监听提交
						  form.on('submit(formDemo)', function(data){
						  	//alert(data);
						  	console.log(data);
						    layer.msg(JSON.stringify(data.field)); 
						    alert(JSON.stringify(data.field)); //json变成string
						    alert(data.field); //json对象
						    alert(data.field.password);
						    return false; //拦截
						  });
						});
					</script>

----------

# 弹框 #
1. 模块加载layer
		layui.use('layer', function() {
				var layer= layui.layer;
				layer.open({content: '',title:'xx'});
		});

2. layer.open(options)   options是一个js对象
	* {}
	* key:value形式
	* key不需要添加 引号
	* value 添加引号
		layer.open({content: '',title:'xx'})
	
3. 关闭
	layer.close(index); //如果设定了yes回调，需进行手工关闭


		<input type="button" onclick="abc()" value="xx"/>
		var layer  ; 
		function abc(){
			layer.msg("xx");
		}
		layui.use('layer', function() {
		 layer = layui.layer;});

# 日期选着  layui.laydate #
1. 