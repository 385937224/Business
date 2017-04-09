package cn.pbq.service;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import cn.pbq.entity.User;

public interface UserService {
	
	void save(User user);
	
	void delete(Serializable id);
	
	void update(User user);
	
	User findById(Serializable id);
	
	List<User> getAll();
	
	//导出所有用户
	void exportExcel(List<User> list,OutputStream out);
	
	//excel导入用户
	void importExcel(File userExcel, String userExcelFileName);
	
	
}
