package cn.pbq.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.stereotype.Component;

import cn.pbq.constant.Constant;
import cn.pbq.entity.RolePrivilege;
import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;
import cn.pbq.service.UserService;
import cn.pbq.service.impl.UserServiceImpl;



/**
 * 这个是相当一个工具类。
 * 但是为什么方法不用静态呢？
 * 是因为有userService实例对象的注入。
 * @author panbingqi;
 * 2017年5月7日
 *
 */

@Component
public class PrivilegeCheck {

	//下面要调用到这个。
	@Resource
	private UserService userService ;
	


/*	因为tomcat服务器 匹配uri的路径规则，这种解析uri的思路方法不可行。
	public static String parseURIToModel(String uri){
		
//		可能出现这种http://localhost:8080/Business//////////////fw//////user_listUI.action。
//		可能出现这种http://localhost:8080/Business/////11/////////fw///11///user_listUI.action。
		
		
		//因为tomcat服务器 匹配uri的路径规则，这种解析uri的思路方法不可行。
		List<String> every = new ArrayList<String>();
		String[] split = uri.split("/");		
//		所以要把null 与""  的去掉
		for(String string:split){
			System.out.println("当前请求的子系统模块名称（名称空间)"+string);
//			抽取非空的字符串元素
			if(string!=null && !("".equals(string))){
				every.add(string);
			}
		}
		for(String string:every){
			System.out.println(string);
		}				
		return every.get(1);	
	}
*/	
	
	/**
	 * uri.contains(string) 的方式验证。
	 */
	public static String uriContainPrivilege(String uri){
		
//		可能出现这种http://localhost:8080/Business/////11/////////fw////11////user_listUI.action。
//		可能出现这种http://localhost:8080/Business/sys/user_fwframe.action。 ---- 出现 fwframe包含了"fw"。
		uri=uri.substring(0, uri.lastIndexOf("/"));
		
		for(String string: Constant.PRIVILEGE_ARRAY){
			if(uri.contains(string)){
				return string;
			}
		}
		
		return null;//uri路径不包含  需要权限的 关键词。该uri请求，不需呀权限效验。
	}
	
	
	
	/**
	 * 检测该请求访问是否需要权限.
	 * 
	 * 如果当前解析的uri，与当前constant类中定义的权限有一样样的。说明请求的路径是要权限，返回true
	 * 问题点:这个方法能不能返回正确 关键在于    uri的解析。
	 */
	public static boolean needPrivilege(String uri){
		
		String model = uriContainPrivilege(uri);
//		System.out.println("null就是该模块不需要权限也可访问!!!!!!!!!!!!!!!!"+model);
		if(model==null){
			return false;
		}else {
			return true;
		}
		
	}
	
	
	
	
	
	
	/**
	 * 	检测该用户是否具有访问权限
	 * @param uri
	 * @param user
	 * @return
	 */
	public  boolean canVisit(String uri,User user){
		
		String model = uriContainPrivilege(uri);
		//为了程序健壮性。如果uri中不包含 权限的请求。直接返回true放行。
		if(model==null){
			return true;
		}
		
		
		
		List<User_Role> user_RoleList = user.getUser_Roles();
		//为了程序健壮性，如果发现session保存的  user_RoleList===null，再去数据库找一次。确认数据库的。
		/**
		 * 如果该类不是由Spring容器来创建。
		 * 自己手动new PrivilegeCheck()创建对象，是无法实现Spring的注入的。
		 * 			new PrivilegeCheck().canVisit()。这里打印userService=null;说明注入不了。
		 */
//		System.out.println("`````````````````"+userService);
		if(user_RoleList==null){
			user_RoleList = userService.findUser_RolByUserId(user.getId());
		}
		
		//表表之间不断联系的麻烦点。搞不好取错了数据。
		//先取到中间表 user_Role
		for(User_Role user_Role:user_RoleList){			
			//通过中间表找到  role表；再通过role表找到   中间表RolePrivilege
			Set rolePrivileges = user_Role.getRole().getRolePrivileges();
			//遍历中间表。反正最终得到“code”的值。
			for (Iterator iterator = rolePrivileges.iterator(); iterator.hasNext();) {
				RolePrivilege object = (RolePrivilege) iterator.next();
				String code = object.getId().getCode();
//				System.out.println("sa!!!!!!!!!!!!!!!!!"+code);
				if(model.equals(code)){
					return true;
				}	
			}
		 }	
		return false;
	}
}
