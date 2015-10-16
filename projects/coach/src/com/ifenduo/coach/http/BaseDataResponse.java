package com.ifenduo.coach.http;

import java.io.Serializable;
import java.util.List;

public class BaseDataResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 错误或提示消息(可用以登录注册界面的提示信息) */
	protected String msg;
	/** 状态码(200,为成功,403为失败，404为接口没找到，500为服务器异常 */
	protected int code;
	/** 本次请求返回的时间 */
	protected long time;
	/** 本次请求结果包含数据总记录数 */
	protected int size;
	/** 本次请求总共数据记录数（用于分页） */
	protected int total;
	/** 所有返回对象存放位置 */
	protected T data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
