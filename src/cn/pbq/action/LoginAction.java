package cn.pbq.action;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.pbq.constant.Constant;
import cn.pbq.dao.UserDao;
import cn.pbq.entity.RolePrivilege;
import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;
import cn.pbq.service.UserService;

public class LoginAction extends ActionSupport {

	@Resource
	private UserService userService;

	/****封装前台数据****/
	private User user;
	
	public String loginUI() {
		
		return "loginUI";
	}
	
	public String login() {		
		if(StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(user.getUserName())){
			List<User> userlist = userService.findUserByUsernameAndPassword(user.getUserName(), user.getPassword());
			//有记录说明这用户和密码已经注册，在数据库能找到匹配。所以登录成功。
			if(userlist!=null&&userlist.size()>0){
				user=userlist.get(0);
				List<User_Role> User_RolList = userService.findUser_RolByUserId(user.getId());
				user.setUser_Roles(User_RolList);
				ActionContext.getContext().getSession().put(Constant.USER, user);
				
				
				/**
				 * 测试能否通过  每个User_Role对象    能否取到  变量code 的值。
				 * 切记:这里的User_role实体类的映射.xml 不能用懒加载哦。不然会取不到code值。因为懒加载导致没进行数据封装。
				 */
//				Set<RolePrivilege> set = User_RolList.get(0).getRole().getRolePrivileges();
//				for(RolePrivilege privilege: set){
//					System.out.println("登录的时候保存的 User_Rol，取出这对象的code值="+privilege.getId().getCode());
//				}
				
				
				return "homeAction";
			}else {
				//匹配不成功，说明用户、密码有错误。
				ActionContext.getContext().getContextMap().put("falseLogin", "用户或帐号有误！");
			}

		}
		return loginUI();
	}
	

	
	public String exit(){
//		Object object = ActionContext.getContext().getSession().get(Constant.USER);
//		System.out.println(object);
		//销毁session中保存的登录用户user的信息。
		ActionContext.getContext().getSession().remove(Constant.USER);
//		Object object1 = ActionContext.getContext().getSession().get(Constant.USER);
//		System.out.println(object1);//打印是null。销毁了。
		return loginUI();
	}
	
	
	
	
	
	/******************************************/
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
