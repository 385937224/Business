package cn.pbq.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class User implements Serializable{

	private String id;
	private String dept;
	private String nickName;
	private String userName;
	private String password;
	
	private String headImg;
	private String gender;
	//对应的 是中间表User_Role、不是直接写Roles表的类
	private List<User_Role> user_Roles;
	private String state;
	private String mobile;
	private String email;
	private Date birthday;
	private String memo;
	
	
//	public final String a="1";
	//用户状态	
	public final static String USER_STATE_VALID = "1";//有效
	public final static String USER_STATE_INVALID = "0";//无效
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<User_Role> getUser_Roles() {
		return user_Roles;
	}
	public void setUser_Roles(List<User_Role> user_Roles) {
		this.user_Roles = user_Roles;
	}

	
	
	
	
}
