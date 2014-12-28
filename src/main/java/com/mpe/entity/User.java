package com.mpe.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.evalua.entity.support.EntityBase;

@Entity
public class User extends EntityBase{
	
	public enum UserStatus{
		ACTIVE,BLOCKED,DELETED;
	}
	
	public enum UserRole{
		ADMIN,NORMAL;
	}
	
	private String name;
	private String email;
	private String password;	
	private UserStatus status=UserStatus.ACTIVE;
	private UserRole role=UserRole.NORMAL;
	private Integer lastSessionCount;
	
	public Integer getLastSessionCount() {
		return lastSessionCount;
	}
	public void setLastSessionCount(Integer lastSessionCount) {
		this.lastSessionCount = lastSessionCount;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
