# 大纲 #

超链接： [官网](http://spring.io/)

图片  ： ![ioc](./img/spring01.png)

表格：

| ..	| ....	| ....	|
| ---	| :---: | :----:|
| ..	| .. 	| ..	|
| ..	| ..   	| ..	|
| ..	| ..   	| ..	|


----------

----------

----------
# 注解(Annotation) #
	为框架做准备

	* 已经接触到的注解
		@Override

[百度百科](https://baike.baidu.com/item/Java%20%E6%B3%A8%E8%A7%A3/4404368?fr=aladdin)
	1. 它是JDK1.5及以后版本引入的一个特性，与类、接口、枚举是在同一个层次
		* public class 类名
		* public interface 接口名
		* public enum 枚举类型
			public enum Color {
			     RED, GREEN, BLANK, YELLOW 
			}
		* public @interface 注解名


	2. 作用
		* 编译检查
			* @Override
		* 替代配置文件
			* servlet3.0以后没有webxml
		* 分析代码【使用反射】
			* 怎么执行代码
		* 定义注解(元注解)
			* 注解上的注解
			* 规定注解的使用:
				* 位置修饰在类上,修饰在方法上


	3. java内置注解
		1. @Override :子类重写父类该方法
			* 只能修饰方法
				 	@Override
					public String toString() {
						return super.toString();
					}
		2. @Deprecated :过时
			* 都可以使用:方法,类,字段,参数
					new Date().toLocaleString();
		3. @SuppressWarnings("all") : 抑制警告
			*  都可以使用:方法,类,字段,参数

				public void t2(@Deprecated String aStringb) {
					@SuppressWarnings("all")
					String aString;
				}

	4. 自定义注解
		1. 创建一个注解:
			public @interface MyAnnotation01 { }
		2. 反编译:
			* javap MyAnnotation01.class
					public interface com.huaxin.t2.MyAnnotation01 extends java.lang.annotation.Annoation {}
			* 其实注解本质就是一个接口
				* 在注解中:抽象方法 称之为 注解的属性
		3. 注解属性	
			* 类型:
				* 基本类型
				* String
				* Class
				* Annotation
				* enum
				* 以上一维数组
				
				public Date xx();
					only primitive type, String, Class, annotation, enumeration are permitted or 1-dimensional arrays thereof
		4. 枚举
			* 编写
				public enum Color {
					Red, Green, Blue
				}
			* 反编译
				public final class com.huaxin.t3.Color extends java.lang.Enum<com.huaxin.t3.Color> {
					  public static final com.huaxin.t3.Color Red;
					  public static final com.huaxin.t3.Color Green;
					  public static final com.huaxin.t3.Color Blue;
					  static {};
					  public static com.huaxin.t3.Color[] values();
					  public static com.huaxin.t3.Color valueOf(java.lang.String);
				}
			* 构造私有,不能new对象,只能用枚举里面定义好的对象
		5. 属性赋值
			* 一旦枚举有属性,必须给属性赋值(除非有默认值)
				* The annotation @MyAnnotation03 must define the attribute xx 
			* 格式
				* @注解名(属性名1=属性值,属性名2=属性值,...)
				* 数组:
					* ( strs = { "","" } )
					* ( strs="" )
				* 如果只有一个属性,并且属性名叫value的时候;属性名可以省略,直接赋值
					* String value();  @MyAnnotation03("")
					* String[] value();  @MyAnnotation03("")  或者 @MyAnnotation03({"",""})
		6. 元注解 : 约束注解的注解(写在注解上的注解)
			* @Retention  :约束注解保留到什么阶段(RetentionPolicy)
				 * SOURCE(默认)	:只在编写代码时候有用.java->.class 文件时候丢失
				 * CLASS	:编写代码和字节码有用, 虚拟机真正运行时候丢失
				 * RUNTIME	:编写代码,字节码文件,运行的时候都有用
	
			* @Target	  : 约束注解作用在哪里(ElementType)
				* TYPE	: 类
				* METHOD:一般方法
				* FIELD:字段上面
				* PATAMETER:参数
				* ...

# 类,方法,字段判断是否有改注解 #
	1. 类
		 boolean isHas =clazz.isAnnotationPresent(RepositoryDao.class);
	2. 方法
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			 boolean isHas = method.isAnnotationPresent(MyAnotation.class);
	3. 字段
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				boolean isHas = field.isAnnotationPresent(MyAnotation.class);
				if (isHas) {
				}
			}
	4. 注意
		* 注解必须保持到运行时候
		* @Retention(RetentionPolicy.RUNTIME)

# 获取注解上属性值 #
1. 类
		MyAnotation annotation = (MyAnotation) clazz
					.getAnnotation(MyAnotation.class);
		String xx = annotation.xx();
2. 属性,方法
		MyAnotation declaredAnnotation = field
						.getDeclaredAnnotation(MyAnotation.class);
		declaredAnnotation.xx();

		MyAnotation annotation = method
					.getAnnotation(MyAnotation.class);
		annotation.xx();

# 案例:注解模拟ioc  di #
1. 创建StudentDao,普通类(程序员编写)
		public class StudentDao {
			public void add() {
				System.out.println("学生添加dao");
			}
		}
2. 创建StudentService,普通类(程序员编写)
		* 需要一个dao属性
		public class StudentService {
			private StudentDao studentDao;
			private String xx;
			public void add() {
				studentDao.add();
			}
		}
3. 创建一个 dao层的注解(框架编写)
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.TYPE)
		public @interface MyRepositoryDao {
			String id();
			String className() default "";
		}

4. 创建一个 service层的注解(框架编写)
		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.TYPE)
		public @interface MyService {
			String id();
			String className() default "";
		}
5. 分别给 dao,service 加上注解
		* id 为当前类的类名.首字母小写

6. IoC容器初始化
	1. 编写一个ioc容器(框架编写)
		* static Map<String, Object> iocContainer = new HashMap<>();
	2. 编写获取id值的方法(框架编写)
		public static String getId(Class clazz) {
			boolean isHas = clazz.isAnnotationPresent(MyRepositoryDao.class);
			if (isHas) {
				// 获取注解属性
				MyRepositoryDao repositoryDao = (MyRepositoryDao) clazz
						.getAnnotation(RepositoryDao.class);
				String id = repositoryDao.id();
				return id;
			}
	
			isHas = clazz.isAnnotationPresent(MyService.class);
			if (isHas) {
				// 获取注解属性
				MyService myService = (MyService) clazz
						.getAnnotation(MyService.class);
				String id = myService.id();
				return id;
			}
			return null;
		}
	3. 编写一个初始化容器方法(框架编写)
			public static void putToContainer(Class clazz) throws Exception {
				String id = getId(clazz);
				if (id != null)
					container.put(id, clazz.newInstance());
			}
	4. 测试容器有dao和service(框架编写)
			// 存放到容器中
			Class clazz = StudentDao.class;
			putToContainer(clazz);
			Class clazz2 = StudentService.class;
			putToContainer(clazz2);


7. DI（Dependency Injection，依赖注入）
	1. 给字段创建一个注解(框架编写)
		@Retention(RetentionPolicy.RUNTIME)
		public @interface MyResource {
			public String id();
		}
	2. 在service层中,给要来的dao上面添加字段注解
		@MyResource(id = "studentDao")
		private StudentDao studentDao;
	3. 提供一个自动注入字段方法
			public static void dependencyInject(Class clazz) throws Exception {
			String id = getId(clazz);
			if (id != null) {
				// 遍历字段,如果有属性注解,就从容器中获取出,然后通过反射技术设置上去
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					boolean isHas = field.isAnnotationPresent(MyResource.class);
					if (isHas) {
						MyResource annotation = field
								.getAnnotation(MyResource.class);
						String id2 = annotation.id();
	
						Object object = container.get(id);// service
						Object object2 = container.get(id2);// dao
	
						if (object != null && object2 != null) {
							field.setAccessible(true);
							field.set(object, object2);
						}
					}
				}
			}
		}
	4. 测试
		public static void main(String[] args) throws Exception {
			// 存放到容器中
			Class clazz = StudentDao.class;
			putToContainer(clazz);
			Class clazz2 = StudentService.class;
			putToContainer(clazz2);
			// 属性注入
			dependencyInject(clazz2);
	
			StudentService StudentService = (com.huaxin.t6.ioc.StudentService) container
					.get("studentService");
			StudentService.add(); // 不报空指针异常
		}