package com.vinfotech.model;

import org.json.JSONObject;

public class NewPassword {

	public String UserID;
	public String FirstName;
	public String LastName;

	public NewPassword(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {
			UserID = jsonObject.optString("UserID");
			FirstName = jsonObject.optString("FirstName");
			LastName = jsonObject.optString("LastName");
		}
	}

}
