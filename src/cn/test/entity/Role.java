package cn.test.entity;
// Generated 2017-5-14 19:18:52 by Hibernate Tools 3.6.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

	private String roleId;
	private String name;
	private String state;
	private Set rolePrivileges = new HashSet(0);
	private Set userRoles = new HashSet(0);

	public Role() {
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

	public Role(String roleId, String name, String state, Set rolePrivileges, Set userRoles) {
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.rolePrivileges = rolePrivileges;
		this.userRoles = userRoles;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set getRolePrivileges() {
		return this.rolePrivileges;
	}

	public void setRolePrivileges(Set rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

	public Set getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}

}
