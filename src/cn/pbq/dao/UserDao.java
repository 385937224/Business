package cn.pbq.dao;

import java.io.Serializable;
import java.util.List;

import com.sun.media.jfxmedia.control.VideoDataBuffer;

import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;

public interface UserDao extends BaseDao<User> {

	void saveUser_Role(User_Role user_Role);
	
	void deleteUser_RoleByUserId(Serializable id);
	
	List<User_Role> findUser_RolByUserId(Serializable userId);
	 
	//根据用户名和密码找用户。
	List<User> findUserByUsernameAndPassword(String username,String password);
	
}
