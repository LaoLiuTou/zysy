package com.zysy.model.user;
import java.util.Date;
/**
 * @author LT
 */
public class User {

	/** 用户  */
	private  Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**  */
	private  String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**  */
	private  String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/** 昵称 */
	private  String nickname;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/** 备注 */
	private  String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**  */
	private  Long state;
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}



}
