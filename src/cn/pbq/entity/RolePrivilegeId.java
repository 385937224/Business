package cn.pbq.entity;
// Generated 2017-5-8 15:11:03 by Hibernate Tools 5.1.0.Alpha1

/**
 * RolePrivilegeId generated by hbm2java
 */
public class RolePrivilegeId implements java.io.Serializable {

	private Role role;
	private String code;

	public RolePrivilegeId() {
	}

	public RolePrivilegeId(Role role, String code) {
		this.role = role;
		this.code = code;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolePrivilegeId))
			return false;
		RolePrivilegeId castOther = (RolePrivilegeId) other;

		return ((this.getRole() == castOther.getRole()) || (this.getRole() != null
				&& castOther.getRole() != null && this.getRole().equals(castOther.getRole())))
				&& ((this.getCode() == castOther.getCode()) || (this.getCode() != null && castOther.getCode() != null
						&& this.getCode().equals(castOther.getCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRole() == null ? 0 : this.getRole().hashCode());
		result = 37 * result + (getCode() == null ? 0 : this.getCode().hashCode());
		return result;
	}

}