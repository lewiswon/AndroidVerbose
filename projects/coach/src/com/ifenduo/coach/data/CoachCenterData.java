package com.ifenduo.coach.data;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.ifenduo.coach.API;
import com.ifenduo.coach.App;
import com.ifenduo.coach.bean.User;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnStateListener;
import com.ifenduo.coach.util.CacheUtils;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;

public class CoachCenterData {
	public <T> User getUser(App app, final OnGet<T> onget) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		new Request<String>(app).stateRequest(API.User.info, params, true, TimeOutModel.MODEL_FOREVER, new OnStateListener() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject object = new JSONObject(response);
					String result = object.getString("data");
					Gson gson = new Gson();
					User user = gson.fromJson(result, User.class);
					onget.get((T) user);
				} catch (JSONException e) {
				}
			}

			@Override
			public void onError(String error) {
			}
		});
		return null;
	}
	public interface OnGet<T> {
		public void get(T data);
	}
}
