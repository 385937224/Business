package cn.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 单独测试Spring框架。
 * 不用开服务器，只有Junit测试单元就可以，手动加载.xml配置文件。要跟web所指定的名字一样。
 * 测试对象TestService是否由IOC常建。
 * @author Administrator
 *
 */
public class TestSpring {
	private ClassPathXmlApplicationContext context;
	
	@Before
	public void loadCtx(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testSpring(){
		//junit测试单元，也可以抽取在其他前面加@before的注解。
		//context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		TestService testService= (TestService) context.getBean("testService");
		testService.say();
	}

}
