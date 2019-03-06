# 介绍 #
1. KindEditor是一套开源的HTML可视化编辑器
2. KindEditor使用JavaScript编写，可以无缝的于Java、.NET、PHP、ASP等程序接合
3. 非常适合在CMS、商城、论坛、博客、Wiki、电子邮件等互联网应用上使用
4. 特点
		1. 体积小，加载速度快，但功能十分丰富。2. 内置自定义range，完美地支持span标记。
		3. 基于插件的方式设计，所有功能都是插件，增加自定义和扩展功能非常简单。
		4. 修改编辑器风格很容易，只需修改一个CSS文件。
		5. 支持大部分主流浏览器，比如IE、Firefox、Safari、Chrome、Opera。

# 使用 #
1. [下载](http://kindeditor.net/demo.php)
2. 目录
	1. asp , asp.net ,jsp php  测试用例
	2. lang
	3. plugins
	4. themes
	5. kindeditor-all.js
	6. kindeditor-all-min.js
3. 引入
	1. kindeditor-all-min.js
	2. 语言包
		<script charset="utf-8" src="../kindeditor-all-min.js"></script>
		<script charset="utf-8" src="../lang/zh-CN.js"></script>
4. 创建teatArea,作为富文本编辑器的数据源
5. js初始化kindEditor
		KindEditor.ready(function(K) {
				//保留editor到全局window对象里面!!!后面序列化等情况使用到(直接editor,window省略)
                window.editor = K.create('#editor_id');
        });

	1. selector	CSS选择器,匹配多个textarea时只在第一个元素上加载编辑器。
	2. option	参数
6. 表单提交前,需要表单同步到textarea
	editor1.sync();
	self.sync();
	

----------
# editor方法 #
	1. 取得HTML内容
		html = editor.html();
	2. 同步数据后可以直接取得textarea的value
		editor.sync();
	3. 设置HTML内容
		editor.html('HTML内容');



----------
# 文件上传 #


# 返回结果 #
1. 浏览器兼容问题,最好是返回string,而不是json
	1. 先对象jsonStrin,
	2. 然后return string ,
	3. 最后controller加上@responseBody
		//成功时
		{
		        "error" : 0,
		        "url" : "http://www.example.com/path/to/file.ext"
		}
		//失败时
		{
		        "error" : 1,
		        "message" : "错误信息"
		}

		public class PictureResult {
	
			/**
			 * 上传图片返回值，成功：0	失败：1	
			 */
			private Integer error;
			/**
			 * 回显图片使用的url
			 */
			private String url;
			/**
			 * 错误时的错误消息
			 */
			private String message;
		}