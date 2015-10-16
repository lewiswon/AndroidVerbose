package com.ifenduo.coach.bean;

public class Book {
	/**
	 * id 唯一id
	 */
	private String id;
	/**
	 * 预约人姓名
	 */
	private String name;
	/**
	 * 预约日期
	 */
	private String day;
	/**
	 * 训练类型
	 */
	private int learnType;
	/**
	 * 预约类型 0 报名 2 等待教练确认 3 等待学员确认（教练推送的预约）
	 */
	private int appointmentType;
	private boolean VIP;
	private String headpic;

	public String getId() {
		return id;
	}
	public void setId(String id) {

		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getLearnType() {
		return learnType;
	}
	public void setLearnType(int learnType) {
		this.learnType = learnType;
	}
	public int getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(int appointmentType) {
		this.appointmentType = appointmentType;
	}
	public boolean isVip() {
		return VIP;
	}
	public void setVip(boolean vip) {
		this.VIP = vip;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

}
