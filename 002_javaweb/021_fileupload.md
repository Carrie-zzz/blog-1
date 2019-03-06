# 大纲 #
1. 页面
2. 服务器接受图片
3. 第二台tomcat服务器,作为图片服务器




----------
# 图片上传 #
1. 页面
	1. form表单必须是post
	2. form属性 enctype="multipart/form-data"
	3. 每个input需要 name属性

2. servlet接受 
	1. requst.getParameter... 所有都为空->beanutils不能使用
	2. Apache 开源 文件上传jar
		* 下载
			* common-fileupload
				*  http://commons.apache.org/proper/commons-fileupload/
			* common-io
				* http://commons.apache.org/proper/commons-io/index.html


3. common-fileupload
	* 创建磁盘文件项工程
		* DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	* 创建servlet文件上传对象
		* ServletFileUpload servletFileUpload = new ServletFileUpload(
				diskFileItemFactory);
	* 核心对象解析request,获取文件项集合
		* List<FileItem> parseRequest = servletFileUpload
					.parseRequest(request);
	* 遍历集合,根据文件项	
		* 判断是否是字段,还是文件
			for (FileItem fileItem : parseRequest) {
				//
				boolean formField = fileItem.isFormField();
				// true : 普通字段
				if (formField) {
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");

					System.out.println(name + "-----" + value);
				} else {
					// 文件
					String fileName = fileItem.getName();// 文件名称

					InputStream is = fileItem.getInputStream();// 输入流
					System.out.println(is);
					String realPath = request.getServletContext()
							.getRealPath("");
					System.out.println(realPath);
					//文件拷贝
					String realPath = servletContext.getRealPath("/");
					FileOutputStream out = new FileOutputStream(
							new File(realPath, fileName));
					IOUtils.copy(is, out);

				}
			}


4. 数据访问路径
	System.out.println("数据库存放路径/访问路径---http://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()
						+ "/" + fileName);


# 问题 #
1. 版本更新的时候,老项目将被移除,新项目部署
	* 文件丢失
2. 多个人上传同名的文件,导致文件覆盖
	*

# 文件名重复的问题  #
1. 不用原来名字,生成一个唯一文件名
2. 分文件夹


----------

# 项目部署丢失问题 #
1. 重新部署一台服务器,专门提供图片服务
2. 在同一台电脑上,部署第二台tomcat服务器

## 步骤 ##
1. 修改三个端口
2. webapps下创建文件夹


