package cn.pbq.dao;

import java.io.Serializable;

import cn.pbq.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	//保存用户的角色到  中间表 user_role
	public void deletePrivilegesByroleId(Serializable id);
	

}
