package cn.pbq.service;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;

public interface UserService extends BaseService<User>{
	
	
	//导出所有用户
	void exportExcel(List<User> list,OutputStream out);
	
	//excel导入用户
	void importExcel(File userExcel, String userExcelFileName);
	
	//保存用户。同时保存该用户所具有的角色。
	public void saveUserAndRole(User user,String... roleIds);
	
	//删除用户、同时删除该用户的所有角色。
	void deleteUserAndRole(Serializable id);
	
	//根据用户id找出该用户具有的角色
	List<User_Role> findUser_RolByUserId(Serializable userId);
	
	//更新用户，同时更新他的角色。
	void updateUserAndRole(User user, String... roleId);
	
	
	//根据用户名和密码找用户。
	List<User> findUserByUsernameAndPassword(String username,String password);
}

