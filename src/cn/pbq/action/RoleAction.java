package cn.pbq.action;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;

import cn.pbq.constant.Constant;
import cn.pbq.entity.Info;
import cn.pbq.entity.Role;
import cn.pbq.entity.RolePrivilege;
import cn.pbq.entity.RolePrivilegeId;
import cn.pbq.entity.User;
import cn.pbq.service.RoleService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public class RoleAction extends BaseAction{
	@Resource
	private RoleService roleService;
	


	/***************************************************************/
	private Role role;
	private String[] hasPrivileges;
	private String[] selectedRow;


	//优化。条件查询、分页效果
	public String listUI() throws Exception {
		SqlUtil sqlUtil = new SqlUtil();
		sqlUtil.setFrom(Role.class.getName());
		if(role!=null){
			if(StringUtils.isNotBlank(role.getName())){
				sqlUtil.setWhere(" name like ? ", "%"+role.getName()+"%");
			}
		}
		// Role表中没这个字段所以不能按照这字段排序。想排序的是set集合中的顺序，去xml中设置《set  order-by=""》属性值
//		sqlUtil.setOrderBy("code",	 "asc");
		
		if(pageNumber<1)pageNumber=1;
		Page page = roleService.getPage(sqlUtil, pageNumber, 3);
		sum=page.getSum();
		pageTotal=page.getPageTotal();
		
		
		request.put("roleList", page.getList());
		request.put("map", Constant.PRIVILEGE_MAP);
		return "listUI";
	}
	
	public String addUI(){	
		request.put("privileges", Constant.PRIVILEGE_MAP);
		return "addUI";
	}
	
	public String add() throws Exception {
		Set rolePrivileges = role.getRolePrivileges();
//		System.out.println("rolePrivileges==============="+rolePrivileges);
		if(hasPrivileges!=null){
			for(String str:hasPrivileges){
				 RolePrivilegeId rolePrivilegeId = new RolePrivilegeId(role,str);
				 RolePrivilege rolePrivilege = new RolePrivilege(rolePrivilegeId);
				 rolePrivileges.add(rolePrivilege);
//				 System.out.println("rolePrivileges==============="+rolePrivileges);
			}	
		}
		roleService.save(role);
//		System.out.println(role.getRoleId()); //hibernate特效，操作后，数据会回填。所以这里才添加到数据库就能打印出id
		return "listAction";
	}
	
	
	//跳转到编辑页面
	public String editUI(){
		
		searchValue=role.getName();
		role = roleService.findById(role.getRoleId());
		request.put("map", Constant.PRIVILEGE_MAP);
		request.put("role", role);
		return "editUI";
	}
	
	//保存编辑
	public String edit()throws Exception{

//		System.out.println("Set<RolePrivileges>!!!!!!!!!!!!!!!!"+role.getRolePrivileges());
		
		//当你什么权限都不选，此时hasPrivileges为null
		if(hasPrivileges!=null){
			Set<RolePrivilege> tempSet = new HashSet<RolePrivilege>();
			for(String str:hasPrivileges){
				 RolePrivilegeId rolePrivilegeId = new RolePrivilegeId(role,str);
				 RolePrivilege rolePrivilege = new RolePrivilege(rolePrivilegeId);
				 tempSet.add(rolePrivilege);
			}	
			role.setRolePrivileges(tempSet);
		}
		
		roleService.update(role);	
		return "listAction";
	}
	
	//删除
	public String delete(){
		searchValue=role.getName();
		roleService.delete(role.getRoleId());
		return "listAction";
	}

	//批量删除
	public String deleteAll(){
		searchValue=role.getName();
		if(selectedRow!=null){
			for(String roleTmep: selectedRow)
			{
				roleService.delete(roleTmep);
			}	
		}

		return "listAction";
	}


	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getHasPrivileges() {
		return hasPrivileges;
	}
	public void setHasPrivileges(String[] hasPrivileges) {
		this.hasPrivileges = hasPrivileges;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	
}











/*
 *最原始的列表方法。直接列出所有记录
	public String listUI() throws Exception {
		List<Role> roleList = roleService.getAll();
		
		request.put("roleList", roleList);
		
		//为什么jsp页面不断刷新会出现排列顺序变化？Set<RolePrivilege>中每次取都不同的顺序
//		Set<RolePrivilege> rolePrivileges = roleList.get(0).getRolePrivileges();
	//	if(rolePrivileges!roleList=null){
	//		for(RolePrivilege aa : rolePrivileges)
	//		{
	//			System.out.println(aa.getId().getCode());			
	//		}
	//	}
		request.put("map", Constant.PRIVILEGE_MAP);
		return "listUI";
	}
*/