# 回顾 #
1. 引入
2. 变量:原始类型,引用类型  ;特殊算符
3. 函数:
4. 事件:焦点,表单,页面
4. dom:



# DOM #
	当浏览器接受到html代码的时候,浏览器会将所有的代码装载到内存中,形成一棵树(document树)
	节点(Node)
		文档节点 document
		元素节点 element
		属性节点 attribute
		文本节点 text
## 关系 ##
1. document 找元素,创建元素,创建文本
2. element 可以追加孩子1.element2.text
3. element 可以给自己设置属性!!!
4. text



## 创建element,text ##
1. var aElement = document.createElement('a');
2. var aElement = document.createTextNode('中国');



## element添加 ##
1. element孩子 :aElement.appendChild(aTextNode)
2. text孩子 :aElement.appendChild(aTextNode)
3. 属性 : aElement.setAttribute("href","#");	

## 删除 ##
1. 删除孩子:parent.remove(child);


## dom  定时器 : 轮播图##
1. var id=setInterVal(code,毫秒数):每隔指定的毫秒数执行一次函数 周期
2. clearInterval(id);
3. img.src="../css/pic"+index+".jpg";
4. img.width+=10;  img.height+=10;


# 函数分发 #
1. document.getElementById("nextImg").onclick = nextImg;
2. 事件=函数名;

总结事件:1. 元素属性 2. 事件分发



----------

----------


# 五大BOM #
	DOM Window :整个窗体
	DOM Location:当前url地址
	DOM History  :历史url地址
	DOM Navigator :浏览器信息
	DOM Screen :窗口大小
	


# Window #
1. window可以省略
## 属性 ##
1. 其他四大对象

## 方法 ##
1. 消息框 :alert,confim,prompt
2. 定时器:setInterval,settimeout
3. 打开,关闭 open("url"); close();

# location #
## 属性 ##
1. href:设置,获取url


# history #

## 方法 ##
1. back()
2. forward();
3. go(n): -1 ,1


----------

----------


