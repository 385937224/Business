package cn.pbq.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pbq.dao.RoleDao;
import cn.pbq.dao.impl.RoleDaoImpl;
import cn.pbq.entity.Role;
import cn.pbq.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role>implements RoleService {

	
	/**
	 * 注解@Resource 写的地方不同是有区别的
	 */

//	注入写这里，即此时注入了RoleDao对象。是在这个全局roleDao中。
//	后面的setRoleDao()就是相当于另外的一个函数了。 会导致这种抽取BaseServiceImpl父类 注入不了BaseDao依赖对象。
//	父类依赖对象 BaseDao没法注入，就是null。使用BaseDao对象就会产生 “空指针异常”。
//	@Resource  
	private RoleDao roleDao;
	
	@Resource   //此时RoleDao注入到的是这个(RoleDao roleDao)局部临时变量里。
	//xml配置的方式为什么要设置setter方法的原因，就是 这种情况。XML配置    注入对象到setter方法的局部变量 。
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao=roleDao;
	}

	

	@Override
	public void update(Role role) {
		//hibernate特性问题。要先删除中间表Privileges中  权限。再全部重新加进去。
		roleDao.deletePrivilegesByroleId(role.getRoleId());	
		roleDao.update(role);
	}





}
