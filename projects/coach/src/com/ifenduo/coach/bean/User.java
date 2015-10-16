package com.ifenduo.coach.bean;

import java.io.Serializable;

public class User implements Serializable {

	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -8902108317338654443L;
	private String id;
	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 名字/称呼
	 */
	private String name;

	/**
	 * 性别
	 */
	private int sex;
	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 头像
	 */
	private String headpic;

	/**
	 * 教龄
	 */
	private int teachYear;

	/**
	 * 驾龄
	 */
	private int driveYear;

	/**
	 * 教练号
	 */
	private String num;

	/**
	 * 车型（0为手动档、1为自动档）
	 */
	private int carType;

	/**
	 * 教学质量
	 */
	private String teachQuality;

	/**
	 * 学生总数
	 */
	private int stuTotal;

	/**
	 * 总教学学时
	 */
	private int hoursTotal;

	/**
	 * 评分
	 */
	private String star;
	private long birthDay;
	/**
	 * 场地
	 */

	/**
	 * 首页图片
	 */
	private String homePic;
	private boolean open;

	private String trainType;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public int getTeachYear() {
		return teachYear;
	}

	public void setTeachYear(int teachYear) {
		this.teachYear = teachYear;
	}

	public int getDriveYear() {
		return driveYear;
	}

	public void setDriveYear(int driveYear) {
		this.driveYear = driveYear;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public String getTeachQuality() {
		return teachQuality;
	}

	public void setTeachQuality(String teachQuality) {
		this.teachQuality = teachQuality;
	}

	public int getStuTotal() {
		return stuTotal;
	}

	public void setStuTotal(int stuTotal) {
		this.stuTotal = stuTotal;
	}

	public int getHoursTotal() {
		return hoursTotal;
	}

	public void setHoursTotal(int hoursTotal) {
		this.hoursTotal = hoursTotal;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getHomePic() {
		return homePic;
	}

	public void setHomePic(String homePic) {
		this.homePic = homePic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	public long getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(long birthDay) {
		this.birthDay = birthDay;
	}
}
