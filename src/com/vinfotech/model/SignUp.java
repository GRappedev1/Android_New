package com.vinfotech.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp {
	public String LoginSessionKey;
	public String UserID;
	public boolean MobileNumberStatus;
	public List<Role> roles;
	public List<String> HospitalIDs;
	public String FirstName;
	public String LastName;
	public String Email;

	public SignUp(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {

			LoginSessionKey = jsonObject.optString("LoginSessionKey");
			UserID = jsonObject.optString("UserID");
			this.roles = getRoles(jsonObject.optJSONArray("roles"));
			HospitalIDs = getHospitalIDs(jsonObject.optJSONArray("HospitalIDs"));
		}
	}

	private List<Role> getRoles(JSONArray jsonArray) {
		List<Role> Roles = new ArrayList<Role>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					Roles.add(new Role(jsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return Roles;
	}

	private List<String> getHospitalIDs(JSONArray jsonArray) {
		List<String> HospitalIDs = new ArrayList<String>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					HospitalIDs.add(jsonArray.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return HospitalIDs;
	}

	private class Role {
		public int RoleID;
		public String URoles;

		@SuppressWarnings("unused")
		public Role(int roleID, String uRoles) {
			super();
			RoleID = roleID;
			URoles = uRoles;
		}

		public Role(JSONObject jsonObject) {
			super();
			if (null != jsonObject) {
				RoleID = jsonObject.optInt("RoleID");
				URoles = jsonObject.optString("URoles");
			}
		}

		@SuppressWarnings("unused")
		public JSONObject toJSONObject() {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("RoleID", RoleID);
				jsonObject.put("URoles", URoles);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject;
		}

	}

}
