package com.chen.springHibernate.bean;

import java.io.Serializable;

/**  
* @author Chen Benwen 
* @E-mail:  
* @version V 1.0.0
* @创建时间：Dec 30, 2015 2:30:17 PM  
* @类说明 :
*/
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name="";
	private long createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
}
