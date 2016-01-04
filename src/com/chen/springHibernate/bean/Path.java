package com.chen.springHibernate.bean;

import java.io.Serializable;

public class Path implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name="";
	private long createTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	
	
}
