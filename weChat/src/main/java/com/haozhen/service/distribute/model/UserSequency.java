package com.haozhen.service.distribute.model;

import java.util.concurrent.atomic.AtomicInteger;





public class UserSequency implements Comparable<UserSequency>{

	private String userid;
	
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
		if(this.userid.equals(o.userid) && this.num.equals(o.num)) {
			return true;
		}
		return false;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public AtomicInteger getNum() {
		return num;
	}

	public void setNum(AtomicInteger num) {
		this.num = num;
	}
	
	
}
