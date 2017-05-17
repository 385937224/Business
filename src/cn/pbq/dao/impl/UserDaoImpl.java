package cn.pbq.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.pbq.dao.UserDao;
import cn.pbq.entity.Role;
import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


	
	/**
	 * 复写查询所以记录方法，把它改写成分页查询的结果。
	 */
//	@Override
//	public List<User> getAll() {
//		String hql = "FROM User";
//		Query query = getSession().createQuery(hql);
//		query.setMaxResults(3);
//		query.setFirstResult(1);
//		return query.list();
//		
//	}


	@Override
	public void saveUser_Role(User_Role user_Role) {
		getSession().save(user_Role);
	}
	

	@Override
	public void deleteUser_RoleByUserId(Serializable userId) {
		//User_Role类没有id的属性、对应表中也没有id的字段。属性是userId。表的字字段是userId。
//		String hql = "delete from User_Role where id=?";  
		String hql = "delete from User_Role where userId=?";
		

		Query query = getSession().createQuery(hql);
		query.setParameter(0, userId);
		query.executeUpdate();		
	}


	public List<User_Role> findUser_RolByUserId(Serializable userId)
	{
		/**
		 * hibernate的一个思考问题。同时列出几个实体类，得到结果query.list，返回的是哪个类的list集合？
		 * 到底把数据封装到哪个实体类呢？？？
		 */
//		String hql = "from User_Role,RolePrivilege WHRER userId=id.role.roleId AND  userId=?";
//		String hql = "from User_Role a,RolePrivilege b WHRER a.role.roleId=b.roleId AND a.userId=?";

		
		/**
		 * User_Role类  中有3个变量  userRoleID; userId; role;
		 * User_Role表  中有3个字段  userRoleID; userId; roleId;
		 * ——————》默认来说封装结果为：role对象中“只有”roleId是有值的，
		 * ——————》role对象的其他变量（name、rolePrivileges）都是没有值的。那么login.action中就无法通过User_Role取到role对象的code值。
		 * 
		 * 如我们向把实体类role变量中所有数据都 封装好，怎么办呢？怎么写呢？
		 * 		只需要在User_Role.xml映射文件中配置 中lazy=“false”  去掉懒加载。
		 * 		heibernate 会自动把这个User_Role类  “所有成员变量”中的“所有数据封装”好。
		 * 
		 * 这样在 login.action 中保存到域对象的 User_Role对象 取出的role对象是有code值。
		 */
		String hql = "from User_Role where userId=?";
		
		Query query = getSession().createQuery(hql);
		query.setParameter(0, userId);
		return query.list();
	}


	@Override
	public List<User> findUserByUsernameAndPassword(String username, String password) {
		String hql ="from User where userName=? and password=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, username);
		query.setParameter(1, password);
		
		return query.list();
	}



	
	
	
	
}
