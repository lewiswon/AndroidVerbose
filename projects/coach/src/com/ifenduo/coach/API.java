package com.ifenduo.coach;

/**
 * 
 * 
 * @Filename API.java
 * 
 * @Description 网络接口地址
 * 
 * @Version 1.0
 * 
 * @Author Lewis Luo
 * 
 * 
 * @History <li>Author: Lewis Luo</li> <li>Date: Jun 10, 2015</li> <li>Version:
 *          1.0</li> <li>Content: create</li>
 * 
 */
public class API {
	private static final String SERVER = "http://tom.iwoodleaf.com";
	// private static final String SERVER = "http://192.168.0.88:8090";
	public static final String IMG_SERVER = "http://tom.iwoodleaf.com/";
	// public static final String IMG_SERVER = "http://192.168.0.88:8090/";
	public static class User {
		/**
		 * params phone password
		 */
		public static String login = SERVER + "/api/trainerLogin.htm";
		/**
		 * params name password phone age
		 */
		public static String register = SERVER + "/api/trainerRregister.htm";
		// 教练个人信息
		public static String info = SERVER + "/api/trainerInfo.htm";

		public static String setRest = SERVER + "/api/setFreeTime.htm";
		// QR码匹配
		public static String urlQr = SERVER + "/api/qrCode.htm";

		// 忘记密码
		public static String urlForgot = SERVER + "/api/trainerForgetPWD.htm";

		// 获取短信验证码
		public static String urlVerify = SERVER + "/api/ptrainerSendCode.htm";
		// 验证验证码接口
		public static String urlVerifyCode = SERVER + "/api/checkCode.htm";
	}
	public static class Book {
		/**
		 * @params id 教练ID
		 * @params type 0-已处理 1- 未处理
		 */
		public static String key_id = "id";
		public static String key_type = "type";
		public static String handled = "0";
		public static String unhandle = "1";
		// 获取预约列表
		public static String url = SERVER + "/api/appointmentList.htm";
		// 获取时间段的空闲
		public static String urlTime = SERVER + "/api/getTimePeople.htm";
		// 确认时间段
		public static String urlCommit = SERVER + "/api/appointmentComfirm.htm";
		// 推送教学计划
		public static String urlPushPlan = SERVER + "/api/appointment.htm";
		// 获取学生对教练的评论
		public static String urlCommentFromStudent = SERVER + "/api/commentById.htm";
		// 确认报名
		public static String urlConfirmRegister = SERVER + "/api/setNumByTrainer.htm";
	}
	public static class Student {
		/**
		 * @params id 教练id
		 * 
		 */
		public static String urlList = SERVER + "/api/traineeList.htm";
		/**
		 * 获取学员的详细训练信息
		 * 
		 * @params id 学员ID
		 * @params type 1-只取一条训练记录 2-获取所有训练记录
		 */
		public static String urlDetail = SERVER + "/api/trainCommentList.htm";

	}
	/**
	 * 
	 * @Description 教练日程中心
	 * 
	 */
	public static class Calendar {
		/**
		 * @params id 教练ID
		 * @params time 选择的时间
		 */
		public static String url = SERVER + "/api/appointmentListByTime.htm";
	}
	public static class Comment {
		/**
		 * 获取评论标签
		 */
		public static String urlTag = SERVER + "/api/getTagList.htm";
		/**
		 * 获取所有待评论列表
		 */
		public static String urlCommentList = SERVER + "/api/getAllCommentList.htm";
		/**
		 * 确认评论
		 */
		public static String urlConfirmComment = SERVER + "/api/commentByTrainer.htm";
	}
}
