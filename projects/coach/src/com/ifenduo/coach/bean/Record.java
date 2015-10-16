package com.ifenduo.coach.bean;

public class Record {
	private String startTime;
	private String endTime;
	private String appointmentId;
	private int learnType;
	private String name;
	private String headpic;
	private String num;
	private String traineeId;

	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTraineeId() {
		return traineeId;
	}
	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
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
	public int getLearnType() {
		return learnType;
	}
	public void setLearnType(int learnType) {
		this.learnType = learnType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

}
