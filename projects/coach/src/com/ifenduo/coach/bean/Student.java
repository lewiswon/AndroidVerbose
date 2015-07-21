package com.ifenduo.coach.bean;

import java.io.Serializable;

public class Student implements Serializable {
	private String traineeId;
	private int times;
	private String num;
	private String name;
	private String headpic;
	private boolean VIP;
	public String getTraineeId() {
		return traineeId;
	}
	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public boolean isVIP() {
		return VIP;
	}
	public void setVIP(boolean vIP) {
		VIP = vIP;
	}

}
