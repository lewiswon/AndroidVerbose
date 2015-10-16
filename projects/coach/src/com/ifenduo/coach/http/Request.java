package com.ifenduo.coach.http;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.SessionStringRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.App;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.util.CacheUtils;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.NetWorkUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Request<T> {
	private static RequestQueue requestQueue;

	public Request(Context context) {
		requestQueue = getQueue(context);

	}

	public static RequestQueue getQueue(Context context) {
		if (requestQueue == null) {
			return Volley.newRequestQueue(context);
		} else {
			return requestQueue;
		}
	}

	/**
	 * 获取单一对象
	 * 
	 * @param url
	 * @param params
	 * @param listener
	 */
	public void getData(final String url, final Map<String, String> params, final Type type, final OnDataResponse<T> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				requestQueue.cancelAll(url);
				handleResponse(response);
			}

			private void handleResponse(String response) {
				BaseDataResponse<T> baseResponse = null;
				try {
					Gson gson = new Gson();
					baseResponse = gson.fromJson(response, type);
				} catch (Exception e) {
					listener.onError(e.getMessage());
					Log.i("error from gson", e.getMessage() + "");
				}
				if (baseResponse != null) {
					switch (baseResponse.getCode()) {
						case 200 :
							listener.onSuccess(baseResponse.getData());
							break;

						default :
							listener.onError(baseResponse.getMsg());
							Log.i("error from message", baseResponse.getMsg());
							break;
					}
				}
			};
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError("网络错误");
				Log.i("error from volley", error + "");
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		addToRequestQueue(stringRequest, url);
	}

	/**
	 * 获取列表的请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数 Map类型
	 * @param type
	 *            实体类类型
	 * @param listener
	 *            返回回调
	 */
	public void getList(final String url, final Map<String, String> params, boolean isRefresh, TimeOutModel timeout, final Type type, final OnListResponse<T> listener) {
		final String cacheFile = convertUP(url, params);
		// String result = CacheUtils.getCache(cacheFile, timeout);
		// if (result != null) {
		// handleList(result, type, listener, cacheFile, false);
		// return;
		// }
		params.put("pageSize", Constant.PAGE_SIZE + "");
		final String tag = convertUP(url, params);
		StringRequest stringRequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.i("getList_response", response);
				requestQueue.cancelAll(tag);
				handleList(response, type, listener, cacheFile, true);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError(error.getMessage() + "");
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		addToRequestQueue(stringRequest, tag);
	}

	/**
	 * 解析返回的list
	 * 
	 * @param response
	 * @param type
	 * @param listener
	 */
	private void handleList(String response, Type type, OnListResponse<T> listener, String cacheFile, boolean cache) {
		BaseResponse<T> baseResponse = null;
		try {
			baseResponse = new HttpAnalyze().analyze(response, type);
		} catch (Exception e) {
			listener.onError(e.getMessage());
			e.printStackTrace();
		}
		if (baseResponse != null) {
			switch (baseResponse.getCode()) {
				case 200 :
					ArrayList<T> list = (ArrayList<T>) baseResponse.getData();
					listener.onSuccess(list, baseResponse.getTotal());
					if (cache) {
						CacheUtils.saveCache(cacheFile, response);
					}
					break;

				default :
					listener.onError(baseResponse.getMsg());
					break;
			}
		}
	}

	/**
	 * 只返回状态的网络请求
	 * 
	 * @param url
	 * @param params
	 * @param isCache
	 * @param listener
	 */
	public void stateRequest(String url, final Map<String, String> params, boolean isCache, final OnStateListener listener) {
		this.stateRequest(url, params, isCache, null, listener);
	}

	public void stateRequest(final String url, final Map<String, String> params, final boolean isCache, TimeOutModel timeout, final OnStateListener listener) {
		final String cacheFile = convertUP(url, params);
		if (isCache) {
			// String result = CacheUtils.getCache(cacheFile, timeout);
			// if (result != null) {
			// listener.onSuccess(result);
			// return;
			// }
		}
		requestQueue.getCache().clear();
		StringRequest stringRequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				LogUtil.i("stateresponse", response);
				Gson gson = new Gson();
				BaseData baseData = gson.fromJson(response, BaseData.class);
				switch (baseData.code) {
					case 200 :
						listener.onSuccess(response);
						Log.i("request", response);
						break;
					case 403 :
						listener.onError(baseData.msg);
						break;
					default :
						listener.onError(baseData.msg);
						break;
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError(error.getMessage() + "");
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		addToRequestQueue(stringRequest, url);
	}

	public void sessionRequest(String url, final Map<String, String> params, final OnSessionListener listener) {
		final SessionStringRequest sessionRequest = new SessionStringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				handleSessionResponse(response, listener);
				try {
					JSONObject object = new JSONObject(response);
					int code = object.getInt("code");
					if (code == 200) {
					}
				} catch (JSONException e) {
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		sessionRequest.setTag(url);
		if (NetWorkUtils.getConnectivity()) {
			requestQueue.add(sessionRequest);
			LogUtil.e(sessionRequest.getClass().getName(), requestQueue.toString());
		} else {
			Toast.makeText(App.getContext(), "网络未连接,请连接网络后再试", Toast.LENGTH_LONG).show();
		}
	}

	public void handleSessionResponse(String response, OnSessionListener listener) {

	}

	/**
	 * 发起网络请求
	 * 
	 * @param request
	 * @param url
	 */
	private void addToRequestQueue(StringRequest request, String url) {
		request.setTag(url);
		if (NetWorkUtils.getConnectivity()) {
			requestQueue.add(request);
			LogUtil.e(request.getClass().getName(), requestQueue.toString() + "=========" + request.getUrl() + ":" + request.getTimeoutMs());
		} else {
			Toast.makeText(App.getContext(), "网络未连接,请连接网络后再试", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 拼接参数和地址，用于缓存
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private String convertUP(String url, Map<String, String> params) {
		Object[] keys = params.keySet().toArray();
		String str = url + "?";
		for (int i = 0; i < keys.length; i++) {
			if (i == keys.length - 1) {
				str += keys[i] + "=" + params.get(keys[i]);
			} else {
				str += keys[i] + "=" + params.get(keys[i]) + "&";
			}

		}

		return str;
	}

	public interface OnDataResponse<T> {
		public void onSuccess(T data);

		public void onError(String error);
	}

	public interface OnListResponse<T> {
		public void onSuccess(ArrayList<T> list, int total);

		public void onError(String error);
	}

	public interface OnStateListener {
		public void onSuccess(String response);

		public void onError(String error);
	}

	public interface OnSessionListener {
		public void onSuccess(String session);

		public void onError(String error);
	}

	public class BaseData {
		public int code;
		public int size;
		public int total;
		public String msg;

	}
}
