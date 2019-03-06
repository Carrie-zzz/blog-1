# 使用 #
1. jar
	<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
	</dependency>
2. get
	1. 创建HttClientp对象
	2. 创建HttpGet,设置url(参数设置-累加)
	3. 执行请求
	4. 接受响应结果,HttpEntity
	5. 处理结果
	6. 关闭

		public static void main(String[] args) throws Exception {
			try {
				// 1. 创建HttClientp对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				// 2. 创建HttpGet,设置url
				String url = "http://www.baidu.com";
				HttpGet httpGet = new HttpGet(url);
				// 3. 执行请求,接受响应结果
				CloseableHttpResponse response = httpClient.execute(httpGet);
				// 4. 获取HttpEntity
				HttpEntity entity = response.getEntity();
				// 5. 处理结果
				String result = EntityUtils.toString(entity);
				System.out.println(result);
				// 6. 关闭
				response.close();
				httpClient.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
			}
		}
3. post 请求
	1. 创建HttClientp对象
	2. 创建HttpPost,设置url(参数设置-StringEntity)
		2.1. 创建表单参数List<NameValuePair>
		2.2. 把表单参数,包装到Entity中, StringEntity
	3. 执行请求
	4. 接受响应结果,HttpEntity
	5. 处理结果
	6. 关闭
			public static void main(String[] args) throws Exception {
			try {
				// 1. 创建HttClientp对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				// 2. 创建HttpPost,设置url(参数设置-StringEntity)
				HttpPost httpPost = new HttpPost("http://www.baicu.com");
				// 2.1. 创建表单参数List<NameValuePair>
				ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
				arrayList.add(new BasicNameValuePair("username", "root"));
				arrayList.add(new BasicNameValuePair("password", "1234"));
				// 2.2. 把表单参数,包装到Entity中, StringEntity
				StringEntity entity = new UrlEncodedFormEntity(arrayList,"utf-8");
				// 3. 执行请求
				httpPost.setEntity(entity);
				// 4. 接受响应结果,HttpEntity
				CloseableHttpResponse response = httpClient.execute(httpPost);
				// 5. 处理结果
				HttpEntity entity2 = response.getEntity();
				String string = EntityUtils.toString(entity2);
				System.out.println(string);
				// 6. 关闭
				response.close();
				httpClient.close();
	
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
	
			}
		}

----------
# contentType #
1. 表单的content-type：application/x-www-form-urlencoded
2. Json的content-type：application/json


----------

----------

----------
# 工具类提取 #
		import java.io.IOException;
		import java.net.URI;
		import java.util.ArrayList;
		import java.util.List;
		import java.util.Map;
		
		import org.apache.http.NameValuePair;
		import org.apache.http.client.entity.UrlEncodedFormEntity;
		import org.apache.http.client.methods.CloseableHttpResponse;
		import org.apache.http.client.methods.HttpGet;
		import org.apache.http.client.methods.HttpPost;
		import org.apache.http.client.utils.URIBuilder;
		import org.apache.http.entity.ContentType;
		import org.apache.http.entity.StringEntity;
		import org.apache.http.impl.client.CloseableHttpClient;
		import org.apache.http.impl.client.HttpClients;
		import org.apache.http.message.BasicNameValuePair;
		import org.apache.http.util.EntityUtils;
		
		public class HttpClientUtil {
		
			public static String doGet(String url, Map<String, String> param) {
		
				// 创建Httpclient对象
				CloseableHttpClient httpclient = HttpClients.createDefault();
		
				String resultString = "";
				CloseableHttpResponse response = null;
				try {
					// 创建uri
					URIBuilder builder = new URIBuilder(url);
					if (param != null) {
						for (String key : param.keySet()) {
							builder.addParameter(key, param.get(key));
						}
					}
					URI uri = builder.build();
		
					// 创建http GET请求
					HttpGet httpGet = new HttpGet(uri);
		
					// 执行请求
					response = httpclient.execute(httpGet);
					// 判断返回状态是否为200
					if (response.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (response != null) {
							response.close();
						}
						httpclient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return resultString;
			}
		
			public static String doGet(String url) {
				return doGet(url, null);
			}
		
			public static String doPost(String url, Map<String, String> param) {
				// 创建Httpclient对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = null;
				String resultString = "";
				try {
					// 创建Http Post请求
					HttpPost httpPost = new HttpPost(url);
					// 创建参数列表
					if (param != null) {
						List<NameValuePair> paramList = new ArrayList<>();
						for (String key : param.keySet()) {
							paramList.add(new BasicNameValuePair(key, param.get(key)));
						}
						// 模拟表单
						UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
						httpPost.setEntity(entity);
					}
					// 执行http请求
					response = httpClient.execute(httpPost);
					resultString = EntityUtils.toString(response.getEntity(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						response.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
				return resultString;
			}
		
			public static String doPost(String url) {
				return doPost(url, null);
			}
			
			public static String doPostJson(String url, String json) {
				// 创建Httpclient对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = null;
				String resultString = "";
				try {
					// 创建Http Post请求
					HttpPost httpPost = new HttpPost(url);
					// 创建请求内容
					StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
					httpPost.setEntity(entity);
					// 执行http请求
					response = httpClient.execute(httpPost);
					resultString = EntityUtils.toString(response.getEntity(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						response.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
				return resultString;
			}
		}
