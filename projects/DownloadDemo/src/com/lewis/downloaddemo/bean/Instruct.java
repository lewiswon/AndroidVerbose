package com.lewis.downloaddemo.bean;

import java.io.Serializable;

public class Instruct implements Serializable {
	private static final long serialVersionUID = -8579183507741173666L;
	public String me_name;
	public String me_uid;
	public String me_context;
	public String me_source;

	public String getMe_name() {
		return me_name;
	}

	public void setMe_name(String me_name) {
		this.me_name = me_name;
	}

	public String getMe_uid() {
		return me_uid;
	}

	public void setMe_uid(String me_uid) {
		this.me_uid = me_uid;
	}

	public String getMe_context() {
		return me_context;
	}

	public void setMe_context(String me_context) {
		this.me_context = me_context;
	}

	public String getMe_source() {
		return me_source;
	}

	public void setMe_source(String me_source) {
		this.me_source = me_source;
	}

	@Override
	public String toString() {
		return "instruct [me_name=" + me_name + ", me_uid=" + me_uid + ", me_context=" + me_context + ", me_source=" + me_source + "]";
	}

}
