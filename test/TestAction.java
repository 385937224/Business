package cn.test;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.net.httpserver.Authenticator.Success;

public class TestAction extends ActionSupport {

	private TestService testService;
	public void setTestService(TestService testService) {
		this.testService = testService;
	}
	
	public String testAction(){
		testService.say();
		return "success";		
	}
}
