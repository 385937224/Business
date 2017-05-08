package cn.pbq.entity;

import java.io.Serializable;

public class User_Role implements Serializable{

	private String userRoleID;
	private String userId;
	/**
	 * 我开始是这么写的。但是包错了。说Role类中的roleId的gettter方法保错。
	 * 		《many-to-one name=‘aa’ column=‘bb’》其实是 把aa作为个对象。 aa.getBB()；方法获取bb的值，放到表中作为bb字段记录来保存。
	 * 		《many-to-one name=‘roleId’ column=‘roleId’》把roleId作为对象，调用getRoleId()方法。
	 * 		roleId定义为字符串，根本没这方法，所以报错。所以这中间表《many-to-one》的 实际java变量 应该    要写关联的  类。
	 * 中间表要实现many-to-one。是整个实体、整个class去对应别人“one”。
	 * 所以java中   这写实体，不能写roleId变量。
	 * 这个实体在表中 只留下这个实体的roleId信息作为联系。。
	 */
//	private String roleId;
	private Role role;
	
	//必须要有默认构造器。我发现hibernate就是利用默认构造器来封装，sql查询到的记录到对象中的。
	public User_Role() {
	}
	
	public User_Role(String userId,Role role) {
		this.userId=userId;
		this.role=role;
	}
	
	
	
	
	
	
	public String getUserRoleID() {
		return userRoleID;
	}
	public void setUserRoleID(String userRoleID) {
		this.userRoleID = userRoleID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}


	
	
}
