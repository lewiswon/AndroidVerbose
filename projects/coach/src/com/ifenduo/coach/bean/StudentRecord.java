package com.ifenduo.coach.bean;

public class StudentRecord {
	private String id;
	private String appointmentId;
	private String startTime;
	private String endTime;
	private String comment;
	private int trainType;
	private String day;
	private String learnInfo;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getTrainType() {
		return trainType;
	}
	public void setTrainType(int trainType) {
		this.trainType = trainType;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getLearnInfo() {
		return learnInfo;
	}
	public void setLearnInfo(String learnInfo) {
		this.learnInfo = learnInfo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
