package com.vinfotech.model;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class Login {
	private static final String PREF_NAME = "LoginPref";
	public String result, posId, posName, storeId, storeName, storeImage, postImage, deviceId, apiToken;

	public Login() {

	}

	public Login(String result, String posId, String posName, String storeId, String storeName, String storeImage, String posImage,
			String deviceId, String apiToken) {
		super();
		this.result = result;
		this.posId = posId;
		this.posName = posName;
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeImage = storeImage;
		this.postImage = posImage;
		this.deviceId = deviceId;
		this.apiToken = apiToken;
	}

	public Login(JSONObject jsonObject) {
		if (null != jsonObject) {
			this.result = jsonObject.optString("result", "");
			this.posId = jsonObject.optString("pos_id", "");
			this.posName = jsonObject.optString("pos_name", "");
			this.storeId = jsonObject.optString("store_id", "");
			this.storeName = jsonObject.optString("store_name", "");
			this.storeImage = jsonObject.optString("store_image", "");
			this.postImage = jsonObject.optString("pos_image", "");
			this.deviceId = jsonObject.optString("device_id", "");
			this.apiToken = jsonObject.optString("api_token", "");
		}
	}

	public Login(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

		this.result = sharedPreferences.getString("result", "");
		this.posId = sharedPreferences.getString("posId", "");
		this.posName = sharedPreferences.getString("posName", "");
		this.storeId = sharedPreferences.getString("storeId", "");
		this.storeName = sharedPreferences.getString("storeName", "");
		this.storeImage = sharedPreferences.getString("storeImage", "");
		this.postImage = sharedPreferences.getString("postImage", "");
		this.deviceId = sharedPreferences.getString("deviceId", "");
		this.apiToken = sharedPreferences.getString("apiToken", "");
	}

	public void clear(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

		prefsEditor.clear();

		prefsEditor.commit();

		this.result = "";
		this.posId = "";
		this.posName = "";
		this.storeId = "";
		this.storeName = "";
		this.storeImage = "";
		this.postImage = "";
		this.deviceId = "";
		this.apiToken = "";
	}

	public void persist(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

		prefsEditor.putString("result", result);
		prefsEditor.putString("posId", posId);
		prefsEditor.putString("posName", posName);
		prefsEditor.putString("storeId", storeId);
		prefsEditor.putString("storeName", storeName);
		prefsEditor.putString("storeImage", storeImage);
		prefsEditor.putString("postImage", postImage);
		prefsEditor.putString("deviceId", deviceId);
		prefsEditor.putString("apiToken", apiToken);

		prefsEditor.commit();
	}

	public boolean isEmpty() {
		if (isEmpty(result) || isEmpty(posId)) {
			return true;
		}
		return false;
	}

	private boolean isEmpty(String name) {
		if (null == name || name.length() < 1) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Login [result=" + result + ", posId=" + posId + ", posName=" + posName + ", storeId=" + storeId + ", storeName="
				+ storeName + ", storeImage=" + storeImage + ", postImage=" + postImage + ", deviceId=" + deviceId + ", apiToken="
				+ apiToken + "]";
	}
}
