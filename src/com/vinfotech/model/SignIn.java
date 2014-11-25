package com.vinfotech.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.vinfotech.server.HTTPRequest;

public class SignIn {
	public String LoginSessionKey;
	public String UserID;
	public int StatusID;
	public List<Role> roles;
	public boolean IsRecoveryActive;
	public List<String> HospitalIDs;
	public String FirstName;
	public String LastName;
	public String Email;
	public String MobileNumber;
	public String MobileNumberStatus;
	public String CountryCode;

	public SignIn(String loginSessionKey, String userID, int statusID, List<Role> roles, boolean isRecoveryActive,
			List<String> hospitalIDs, String firstName, String lastName, String email, String countryCode) {
		super();
		LoginSessionKey = loginSessionKey;
		UserID = userID;
		StatusID = statusID;
		this.roles = roles;
		IsRecoveryActive = isRecoveryActive;
		HospitalIDs = hospitalIDs;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		CountryCode = countryCode;
	}

	public SignIn(JSONObject jsonObject) {
		super();

		if (null != jsonObject) {
			LoginSessionKey = jsonObject.optString("LoginSessionKey");
			UserID = jsonObject.optString("UserID");
			StatusID = jsonObject.optInt("StatusID");
			this.roles = getRoles(jsonObject.optJSONArray("roles"));
			IsRecoveryActive = jsonObject.optBoolean("IsRecoveryActive");
			HospitalIDs = getHospitalIDs(jsonObject.optJSONArray("HospitalIDs"));
			FirstName = jsonObject.optString("FirstName");
			LastName = jsonObject.optString("LastName");
			Email = jsonObject.optString("Email");

			MobileNumber = jsonObject.optString("MobileNumber");
			MobileNumberStatus = jsonObject.optString("MobileNumberStatus");
			CountryCode = jsonObject.optString("CountryCode");

		}
	}

	// Create the SharedPreferance
	public SignIn(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("SignInPref", Context.MODE_PRIVATE);

		String signInJson = sharedPreferences.getString("SignIn", "{}");
		if (null != signInJson) {

			try {
				JSONObject jsonObject = new JSONObject(signInJson);
				SignIn signIn = new SignIn(jsonObject);
				LoginSessionKey = signIn.LoginSessionKey;
				UserID = signIn.UserID;
				StatusID = signIn.StatusID;
				this.roles = signIn.roles;
				IsRecoveryActive = signIn.IsRecoveryActive;
				HospitalIDs = signIn.HospitalIDs;
				FirstName = signIn.FirstName;
				LastName = signIn.LastName;
				Email = signIn.Email;
				MobileNumber = signIn.MobileNumber;
				MobileNumberStatus = signIn.MobileNumberStatus;
				CountryCode = signIn.CountryCode;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	// Edit Shared Preferance
	public void persist(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("SignInPref", Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

		prefsEditor.putString("SignIn", toJSONObject().toString());

		prefsEditor.commit();
	}

	// New Shared Preferance
	public boolean isEmptyPreferance(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences("SignInPref", Context.MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("SignIn", "");
		if (strSavedMem1.equals(""))
			return true;
		else
			return false;
	}

	// Remove Shared Preferance Valuese
	public void clearPreferance(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("SignInPref", Context.MODE_PRIVATE);
		if (sharedPreferences.contains("SignIn")) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.remove("SignIn");
			editor.commit();
		}

	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("LoginSessionKey", LoginSessionKey);
			jsonObject.put("UserID", UserID);
			jsonObject.put("StatusID", StatusID);
			jsonObject.put("roles", getRoles());
			jsonObject.put("IsRecoveryActive", IsRecoveryActive);
			jsonObject.put("HospitalIDs", getHospitalIds());
			jsonObject.put("FirstName", FirstName);
			jsonObject.put("LastName", LastName);
			jsonObject.put("Email", Email);

			jsonObject.put("MobileNumber", MobileNumber);
			jsonObject.put("MobileNumberStatus", MobileNumberStatus);
			jsonObject.put("CountryCode", CountryCode);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONArray getRoles() {
		JSONArray jsonArray = new JSONArray();
		if (null != roles) {
			for (Role role : roles) {
				jsonArray.put(role.toJSONObject());
			}
		}
		return jsonArray;
	}

	private JSONArray getHospitalIds() {
		JSONArray jsonArray = new JSONArray();
		if (null != HospitalIDs) {
			for (String string : HospitalIDs) {
				jsonArray.put(string);
			}
		}
		return jsonArray;
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

	public class Role {
		public int RoleID;
		public String URoles;

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

	/**
	 * Returns profile image url
	 * 
	 * @param size
	 *            0 - Small, 1 - Normal, 2 - Large<br/>
	 *            Default is Small
	 * @return
	 */
	public String getProfileImage(int size) {
		String url = HTTPRequest.IMAGE_URL + "GetProfilePicture/" + (1 == size ? "Normal" : 2 == size ? "Large" : "Small") + "/"
				+ HTTPRequest.USER_TYPE_ID + "/" + UserID + "/";

		Log.v("Image url : ", url);

		return url;
	}

}
