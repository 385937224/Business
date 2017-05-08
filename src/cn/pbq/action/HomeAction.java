package cn.pbq.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.pbq.constant.Constant;
import cn.pbq.entity.User;

public class HomeAction extends ActionSupport{
	


	//整个系统的首页
	public String home() {	

		User user =(User) ActionContext.getContext().getSession().get(Constant.USER);
		ActionContext.getContext().getContextMap().put("user", user);
		return "home";
	}
	
	
	//这几个是子系统的框架页面。
	public String fwframe() {		
		return "fwframe";
	}
	
	public String fwtop() {		
		return "fwtop";
	}
	
	public String fwleft() {		
		return "fwleft";
	}
}