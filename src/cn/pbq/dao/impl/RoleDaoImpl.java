package cn.pbq.dao.impl;


import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.media.jfxmedia.control.VideoDataBuffer;

import cn.pbq.dao.RoleDao;
import cn.pbq.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	public void deletePrivilegesByroleId(Serializable id) {		
		/**
		 * 这样写是最完整的hibernate的hql语句，写的是对象、变量。  “id.role.roleId”
		 * "delete from RolePrivilege where       id.role.roleId       =?    "
		 */
		Query query = getSession().createQuery("delete from RolePrivilege where roleId=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}
 

}
