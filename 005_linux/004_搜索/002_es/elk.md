后台启动
./bin/elasticsearch -d

nohup ./bin/kibana &

fuser -n tcp 5601
kill -9  


http://blog.51cto.com/zero01/2082794

# 中文分词
scp -r /opt/elasticsearch-6.2.4/plugins/ik-analysis root@192.168.20.223:/opt/elasticsearch-6.2.4/plugins

chown es.es -R /opt/elasticsearch-6.2.4/

chown es.es -R /data/elk


# 证书 #
1. 节点证书	.p12
2. 节点密钥
3. CA证书


# elasticsearch #
1. 介绍
	1. 近实时

 [https://www.elastic.co/cn/#](https://www.elastic.co/cn/ "官网")
2. 新建用户
* useradd sonar
* passwd sonar

	http://192.168.20.123:9200/
	http://192.168.20.123:5601/app/kibana#/dev_tools/console?_g=()

3. 中文分词器下载
https://github.com/medcl/elasticsearch-analysis-ik/releases

unzip

# kibana #
1. 下载


# es操作 #
	
	GET _search
	{
		"query":{
			"match_all": {}
		}
	}
	
	POST _analyze
	{
	  "analyzer": "ik_max_word",
	  "text":     "我是中国人"
	}

## 一级 ##
1. query
	1. match_all
	2. match  
		1. 字段名 : 字段值
		2. 字段名
			1. query: 字段值
			2. operator(默认分词 or,可以指定字段and关系 ,operator)
			3. minimum_should_match : "75%"
	3. term
		1. 字段名 : 字段值
	4. range 
		1. 字段名
			1. gt
			2. gte
			3. lt
			4. lte
	5. multi_match
		1. fields : [字段名1,字段名2..]
		2. query : 字段值
	6. terms
		1. 字段名 : [字段值1,字段值2..]
	7. bool
		1. must（与）
		2. must_not（非）
		3. should
			* match
			* ...
			* filter
				* match,range
				* bool
	8. fuzzy
		1. 字段名 : 字段值
		2. 字段名 
			1. value :字段值
			2. fuzziness : 数值(偏移量)
	9. constant_score 没有查询条件，不希望进行评分
		filter


2. _source : [字段名1,字段名2..]
3. _source 
	1. includes:[字段名1,字段名2..]
	2. excludes:[字段名1,字段名2..]
4. sort  :[]
	1. 字段名
		1. order : desc asc

5. size : 0
6. aggs
	1. 聚合名字(桶)
		1. terms(划分桶的方式的方式之一)
			1. field : 字段名 
		2. aggs
			1. 聚合名字(度量)
				1. avg	度量的类型()
					1. field : 字段名 
			2. 聚合名字2(桶)
				1. terms(划分桶的方式的方式之一)
					1. field : 字段名 
		3. price(划分桶的方式的方式之一 阶梯分桶Histogram)
			1. histogram
				1. "field": "字段名",
				2. "interval": 5000,
				3. "min_doc_count": 1
				

##索引（indices）--------------------------------Databases 数据库
		新增
			put	索引库名
			{
			    "settings": {
			        "number_of_shards": 3,
			        "number_of_replicas": 2
			      }
			}
		查询
			http://192.168.20.123:9200/goods
			get	goods
			get	/
			
		delete	删除
		head	存在
##​类型（type）-----------------------------Table 数据表


##字段（Field）-------------------Columns 列
##映射(mappings)-------------------
					--字段的数据类型type、属性、是否索引index、是否存储store等特性
		新增
			PUT /索引库名/_mapping/类型名称
			{
			  "properties": {
			    "字段名": {
			      "type": "类型",
			      "index": true，
			      "store": true，
			      "analyzer": "分词器"
			    }
			  }
			}
				数据类型type:
					* text、keyword
					* byte、short、integer、long
					* date、object等
					* boolean
					* binary
					* integer_range
					
				index:是否索引，默认为true
					* false：字段不会被索引，不能用来搜索
				store:是否存储，默认为false
					false也是能查詢出來,原始数据备份到_source的属性中
					所以一般为false不需要再去开辟空间存储
				analyzer：分词器，这里的ik_max_word即使用ik分词器
		查看
			get /索引库名/_mapping


			put /scloud/_mapping/goods
			{
			  "properties":{
			    "name": {
			      "type": "text",
			      "analyzer": "ik_max_word"
			    },
			    "images": {
			      "type": "keyword",
			      "index": "false"
			    },
			    "price": {
			      "type": "float"
			    }
			  }
			}
##文档（Document）----------------Row 行
		新增
			POST /索引库名/类型名
			POST /索引库名/类型/id值
		修改(必须指定id)
			put /索引库名/类型/id值	
				* 存在修改,不存在新增
				* 必须全部属性
			put
		
		partial update
			post /索引库名/类型/id值/update
			{
				"doc":{
				}
			}		


		删除
			DELETE /索引库名/类型名/id值

		POST /scloud/goods/2
		{
		    "name":"小米手机",
		    "images":"http://image.leyou.com/12479122.jpg",
		    "price":2699.00
		}




# 查询 #
	基本查询
	_source过滤
	结果过滤
	高级查询
	排序
## 基本查询 ##
	GET /索引库名/_search
	{
	    "query":{
	        "查询类型":{
	            "查询条件":"查询条件值"
	        }
	    }
	}
- 查询类型
	-  例如：match_all， match，term ， range 等等
- 查询条件：查询条件会根据类型的不同，写法也有差异，后面详细讲解  


* match_all
	* 
			GET /scloud/_search
			{
					"query":{
						"match_all": {}
					}
			}
* match
	* or 会把查询条件进行分词，然后进行查询,多个词条之间是or的关系
			GET /scloud/_search
			{
			    "query":{
			        "match":{
			            "title":"小米电视"
			        }
			    }
			}
	* and

			GET /scloud/_search
			{
			    "query":{
			        "match":{
			            "title":{
							"query":"小米电视",
							"operator":"and"
						}
			        }
			    }
			}
	* 分数 minimum_should_match
		GET /scloud/_search
		{
		    "query":{
		        "match":{
		            "title":{
		                "query":"小米曲面电视",
		                "minimum_should_match": "75%"
		            }
		        }
		    }
		}
* multi_match 多字段 匹配
	
		GET /scloud/_search
		{
		    "query":{
		        "multi_match": {
		            "query":    "小米",
		            "fields":   [ "title", "subTitle" ]
		        }
		    }
		}
* term 项 精确匹配
	* 数字、时间、布尔或者那些未分词的字符串

			GET /scloud/_search
			{
			    "query":{
			        "term":{
			            "price":2699.00
			        }
			    }
			}
* terms 单项,多值
		GET /scloud/_search
		{
		    "query":{
		        "terms":{
		            "price":[2699.00,2899.00,3899.00]
		        }
		    }
		}


## 	结果(_source)过滤 ##

* 直接指定字段
		GET /scloud/_search
		{
		  "_source": ["title","price"],
		  "query": {
		    "term": {
		      "price": 2699
		    }
		  }
		}
* 指定includes和excludes
		GET /scloud/_search
		{
		  "_source": {
		     "excludes": ["images"]
		  },
		  "query": {
		    "term": {
		      "price": 2699
		    }
		  }
		}
	
## 高级查询 ##
* 布尔组合（bool)
		bool把各种其它查询通过must（与）、must_not（非）、should（或）的方式进行组合

	
	GET /scloud/_search
	{
	    "query":{
	        "bool":{
	            "must":     { "match": { "title": "大米" }},
	            "must_not": { "match": { "title":  "电视" }},
	            "should":   { "match": { "title": "手机" }}
	        }
	    }
	}

* 范围range

		GET /scloud/_search
		{
		    "query":{
		        "range": {
		            "price": {
		                "gte":  1000.0,
		                "lt":   2800.00
		            }
		        }
		    }
		}

		操作符	说明
		gt	大于
		gte	大于等于
		lt	小于
		lte	小于等于

* 模糊查询(fuzzy)

## 过滤(filter) ##


## 排序 sort ##
* 


----------

# 聚合aggregations  #
聚合、排序、过滤的字段其处理方式比较特殊，因此不能被分词

## 桶 bucket ##
1. 分组,每组称为一桶
2. 只分组,不计算,需要度量

## 度量 metrics ##
1. 聚合运算,求平均值、最大、最小、求和等，这些在ES中称为度量





----------
# logstash #

	input {	# 定义日志源
	  syslog {
	    type => "system-syslog"	# 定义类型
	    port => 10514	 # 定义监听端口
	  }
	}
	output {	# 定义日志输出
	  elasticsearch {
	    hosts => ["192.168.20.123:9200"]  # 定义es服务器的ip
	    index => "system-syslog-%{+YYYY.MM}" # 定义索引
	  }
	}

检测文件
./bin/logstash -f /opt/logstash/logstash-6.5.4/io/syslog.conf --config.test_and_exit


 vi /etc/rsyslog.conf
#### RULES ####
*.* @@192.168.77.130:10514


systemctl restart rsyslog




