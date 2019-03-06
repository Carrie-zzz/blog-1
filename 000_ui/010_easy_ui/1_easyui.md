# easyui #
1. 介绍
2. 下载
	* http://www.jeasyui.net/  ->下载->GPL 版本-> Freeware Edition
3. 导包
		<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.5.3/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.5.3/themes/icon.css"/>

		<script src="../jquery-easyui-1.5.3/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../jquery-easyui-1.5.3/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>

3. 使用(两种方式)
	* 元素 添加class属性  class="easyui-xxx"
	* js 方式 (复杂组件使用)

4. 属性
	* class方式
		* data-options="属性1:值,属性2:'值',属性3:[{值},{值}]"
		* <x  属性="值" >
	* js方式
		$("").xxx组件名({
			属性1:值,
			属性2:"值",
			属性3:'值',
			属性4:[{值},{值}],
		});
5. 方法
	* $标签.xxx组件名('方法名');
	* $标签.xxx组件名('方法名',{参数/属性:值,参数/属性:值});

		<button onclick="javascript:$('#p').panel('open')">显示按钮</button>
		<button onclick="javascript:$('#p').panel('close')">关闭按钮</button>
		<button onclick="javascript:$('#p').panel('destroy')">销毁按钮</button>
6. 事件 
	* class方式
		* <xx  事件="fn()">
	* js方式
		$("").xxx组件名({
			事件:function(){},
			
		});
	

# form #
1. 提交
	* ajax 提交	
		* 数据 $("input[name='userbane']").val();
		* 数据序列化 $("formId").serialize();
			* username=xx&password=xx
	* easyui form提交
		* 初始化
		* 提交
2. 验证 ValidateBox



# layout #
1. 东南西北中 -> 中间不能删除


# tabs #


# datagrid #
1. 请求参数
	* page
	* rows
	* order   desc  asc
	* sort
2. 响应参数
	* total
	* rows
	
