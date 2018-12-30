package com.haozhen.service.distribute.model;

/**
  * 用户信息实体
 * @author haozhen
 */
public class ServerMessage {
	
	/**
	 * 用户ID
	 */
	public String userid;
	/**
	 * 用户信息
	 */
	private String message;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
