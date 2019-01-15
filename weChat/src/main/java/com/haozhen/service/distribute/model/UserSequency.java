package com.haozhen.service.distribute.model;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class UserSequency implements Comparable<UserSequency>{

	/**
	  * 客服用户ID
	 */
	private String userid;
	
	/**
	  *客服连接数 
	 */
	private AtomicInteger num = new AtomicInteger(0);

	public int compareTo(UserSequency o) {
		return this.num.intValue()>o.num.intValue()?1:-1;
	}
	
	@Override
	public int hashCode() {
		return userid.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		UserSequency o = (UserSequency) obj;
		if(this.userid.equals(o.userid)) {
			return true;
		}
		return false;
	}
	
}
