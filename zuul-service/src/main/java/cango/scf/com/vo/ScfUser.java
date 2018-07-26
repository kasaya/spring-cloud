package cango.scf.com.vo;

import java.util.List;

public class ScfUser {
	/**
	 * 用户ID
	 **/
	private String userId;

	/**
	 * 用户名
	 **/
	private String userName;

	/**
	 * 角色
	 **/
	private List<MRoleEntity> role;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<MRoleEntity> getRole() {
		return role;
	}

	public void setRole(List<MRoleEntity> role) {
		this.role = role;
	}

}
