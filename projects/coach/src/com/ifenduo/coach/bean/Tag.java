package com.ifenduo.coach.bean;

public class Tag {
	public String name;
	public boolean check = false;
	public int checkedPosition = -1;
	public String content;
	public String id;
	public Tag() {
	}

	public Tag(String name) {
		this.content = name;
		this.name = name;
	}

	public Tag(String name, boolean check) {
		this.name = name;
		this.check = check;
	}
}
