# 定时任务 #
1. 作用
	1. 花呗,还钱
	2. 生日,过年,发邮件
2. java实现
	* java可以使用java.util.Timer结合java.util.TimerTask来完成这项工作，但时调度控制非常不方便，并且我们需要大量的代码 
2. quarter
	1. java开源任务(job)调度框架

# quarter  #
1. Job
	1. 代表具体执行的任务
2. JobSetail
	1. 执行者(执行程序)
	2. 用于执行job
	3. 设置执行的方案和策略
3. Trigger
	1. 触发器,设置参数,什么时候执行
4. Scheduler
	1. 任务总容器,里面开源包含多个任务(Job + JobDetail + Trigger)

# 使用 #
	只需写一个POJO(job)，其余的都是配置

1. 导包 quartz.jar
	<dependency>
	    <groupId>org.quartz-scheduler</groupId>
	    <artifactId>quartz</artifactId>
	    <version>2.3.0</version>
	</dependency>
2. 定义一个job类,编写方法
	public class MailJob1 {
		public void sendMail() {
			System.out.println("~~任务执行" + new Date());
		}
	}
3. spring配置
	1. 定义job类,交给spring
	2. 定义jobDetail
	3. 定义trigger
	4. 定义schedule

		<!-- 定义job类 -->
		<bean id="mailJob" class="com.quartz.MailJob1"> </bean>
		<!-- 定义MethodInvokingJobDetailFactoryBean -->
		<bean id="methodInvokingJobDetailFactoryBean" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
			<property name="targetObject" ref="mailJob"/>
			<property name="targetMethod" value="sendMail"></property>
		</bean>
		<!-- 指定时间 -->
		<bean id="cronTriggerFactoryBean" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
			<property name="jobDetail" ref="methodInvokingJobDetailFactoryBean"></property>
			<!-- 表达式 :十秒一次
				秒	  分	时    日      月		星期   年
				0/10 *  *  *   * 	?	*
				日如果 * ,星期必须?
			-->
			<property name="cronExpression" value="0/10 * * * * ? *"></property>
		</bean>
		
		<!-- 调度器 -->
		<bean id="scheduler"  class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
			<property name="triggers">
				<list>
					<ref bean="cronTriggerFactoryBean"/>
				</list>
			</property>
		</bean>
4. 测试
		@Test
		public void testName() throws Exception {
			ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
					"classpath:spring/applicationContext-job.xml");
			Thread.sleep(100000);
		}

# 时间表达式 #
详情参考:Quartz定时任务调度.docx

	1. Quartz Cron 表达式支持到七个域 
			名称	是否必须	允许值	特殊字符
			秒	是	0-59	, - * /
			分	是	0-59	, - * /
			时	是	0-23	, - * /
			日	是	1-31	, - * ? / L W C
			月	是	1-12 或 JAN-DEC	, - * /
			周	是	1-7 或 SUN-SAT	, - * ? / L C #
			年	否	空 或 1970-2099	, - * /
	2. 域之间有空格分隔
	3. 周: 1-7  周日-周六