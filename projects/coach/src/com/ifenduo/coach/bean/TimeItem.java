package com.ifenduo.coach.bean;
/**
 * 
 * 
 * @Filename TimeItem.java
 * 
 * @Description 选择时间段的bean
 * 
 * @Version 1.0
 * 
 * @Author Lewis Luo
 * 
 * 
 * @History <li>Author: Lewis Luo</li> <li>Date: Jun 17, 2015</li> <li>Version:
 *          1.0</li> <li>Content: create</li>
 * 
 */
public class TimeItem {
	private String startTime;
	private String endTime;
	private int peoples;
	private boolean VIP;
	private int type;

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPeoples() {
		return peoples;
	}
	public void setPeoples(int peoples) {
		this.peoples = peoples;
	}
	public boolean isVIP() {
		return VIP;
	}
	public void setVIP(boolean vIP) {
		VIP = vIP;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
