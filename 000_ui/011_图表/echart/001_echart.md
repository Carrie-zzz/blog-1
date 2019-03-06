# 介绍 #
1. [官网](http://echarts.baidu.com/)
2. 基于Canvas的，纯Javascript 的图表库
3. ECharts 是指 Enterprise Charts（商业产品图表库）
4. 版本
	1. 完整压缩版:echarts.min.js
	2. 完整源码版:echarts.js
5.[github 全部](https://github.com/ecomfe/echarts/releases)
	


# 使用 #
1. 嵌套环形图
	1. 在官网,官方实例->饼图->嵌套环形图
	2. 赋值源码,嵌入到script里面 (准备了配置项和数据)
	3. 引入 ECharts.js
	4. 准备一个具备高宽的 DOM 容器
			< body>
			    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
			    <div id="main" style="width: 600px;height:400px;"></div>
			</body>
	5. 可以通过 echarts.init 方法初始化一个 echarts 实例并通过 setOption 方法生成一个简单的柱状图
		
			<script type="text/javascript">
				// 基于准备好的dom，初始化echarts实例
				var myChart = echarts.init(document.getElementById('main')); //必需在div加载之后!
					// 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
			</script>
	6.



# 校核 #
1. 数据加载
	1. myChart.showLoading();
	2. myChart.hideLoading(); 
2. ECharts组件的定位和布局
	1. left/right/top/bottom/width/height
			left：距离 DOM 容器左边界的距离。
			right：距离 DOM 容器右边界的距离。
			top：距离 DOM 容器上边界的距离。
			bottom：距离 DOM 容器下边界的距离。
			width：宽度。
			height：高度。  
	2. 值都可以是 :『绝对值』或者『百分比』或者『位置描述』
		1.  {left: 23, height: 400}  不用写单位,单位是浏览器像素（px）
		2.  {right: '30%', bottom: '40%'}
		3.  left: 'center'  //水平居中	, top 'middle'  //垂直居中

	3. radius:[内半径, 外半径]，
		1. 内,外半径可以是『绝对值』或者『百分比』
			1. radius: ['40%', '55%'],
3. 事件
	1. 添加
			myChart.on('click', function (params) {
			    consol.log(param);
			});
		
			{
			    // 当前点击的图形元素所属的组件名称，
			    // 其值如 'series'、'markLine'、'markPoint'、'timeLine' 等。
			    componentType: string,
			    // 系列类型。值可能为：'line'、'bar'、'pie' 等。当 componentType 为 'series' 时有意义。
			    seriesType: string,
			    // 系列在传入的 option.series 中的 index。当 componentType 为 'series' 时有意义。
			    seriesIndex: number,
			    // 系列名称。当 componentType 为 'series' 时有意义。
			    seriesName: string,
			    // 数据名，类目名
			    name: string,
			    // 数据在传入的 data 数组中的 index
			    dataIndex: number,
			    // 传入的原始数据项  ~~~~!!!!!!!!!
			    data: Object,
			    // sankey、graph 等图表同时含有 nodeData 和 edgeData 两种 data，
			    // dataType 的值会是 'node' 或者 'edge'，表示当前点击在 node 还是 edge 上。
			    // 其他大部分图表中只有一种 data，dataType 无意义。
			    dataType: string,
			    // 传入的数据值
			    value: number|Array
			    // 数据图形的颜色。当 componentType 为 'series' 时有意义。
			    color: string
			}
			

	2. 种类'click'、'dblclick'、'mousedown'、'mousemove'、'mouseup'、'mouseover'、'mouseout'

	