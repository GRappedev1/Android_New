package com.vinfotech.server;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.vinfotech.main.RegistrationActivity;
import com.vinfotech.utility.JSONUtil;

/**
 * Utility class to generate HTTP request strings
 * 
 * @author gourav.jain
 * 
 */

// http://medlinx-dev.elasticbeanstalk.com/api/SignIn/

public class HTTPRequest {
	public static final String SERVICE_URL = "http://medlinx-dev.elasticbeanstalk.com/api/";
	public static final String UPLOAD_URL = "http://medlinx-dev.elasticbeanstalk.com/api/";
	public static final String IMAGE_URL = "http://medlinx-dev.elasticbeanstalk.com/api/";
	public static final String GET_ALL_DATA_SERVER = "http://103.15.66.183:81/ReusableMobileCMS/api/getAllDetails/";

	public static final String USER_TYPE_ID = "3";

	public static final int URLTYPE_SERVICE = 1;
	public static final int URLTYPE_UPLOAD = 2;
	public static final int URLTYPE_IMAGE = 3;
	public static final int URLTYPE_EXTERNAL = 4;

	public static final int REQ_CODE_SIGNUP /*								*/= 0;
	public static final int REQ_CODE_LOGIN /*								*/= 1;
	public static final int REQ_CODE_LOGOUT/*								*/= 2;
	public static final int REQ_CODE_CHANGE_PASSWORD/*						*/= 3;
	public static final int REQ_CODE_RECOVER_LOGIN/*						*/= 4;
	public static final int REQ_CODE_TAKE_PHOTO/*							*/= 5;
	public static final int REQ_CODE_SEND_DEVICE_TOKEN /*					*/= 6;
	public static final int REQ_CODE_LOGIN_WITH_DOXIMITY /*					*/= 7;
	public static final int REQ_CODE_SEND_VERIFICATIONCODE /*				*/= 8;
	public static final int REQ_CODE_RESEND_VERIFICATIONCODE /*				*/= 9;
	public static final int REQ_CODE_SAVE_MOBILE_NUMBER /*					*/= 10;
	public static final int REQ_CODE_UPLOAD_IMAGE /*							*/= 11;
	public static final int REQ_CODE_GET_ALL_DATA /*						*/= 12;

	private static String LoginSessionKey;

	public static Bitmap mbitmap;

	public static void setLoginSessionKey(String LoginSessionKey) {
		HTTPRequest.LoginSessionKey = LoginSessionKey;
	}

	/*
	 * public String getUploadProfileJSON( String DeviceTypeID,String ImageType,
	 * String ImageString) {
	 * 
	 * JSONObject requestObject = null; requestObject =
	 * JSONUtil.getJSONObject("DeviceTypeID", DeviceTypeID, "ImageType",
	 * ImageType,"LoginSessionKey",LoginSessionKey,"ImageString",ImageString);
	 * JSONObject requestJSObj =
	 * JSONUtil.getNamedJSONObject("UploadProfilePicture", requestObject);
	 * return null == requestJSObj ? "" : requestJSObj.toString(); }
	 */

	/**
	 * the constructor
	 */
	public HTTPRequest() {
	}

	public String registrationJSON(String Password, String FirstName,
			String LastName, String MobileNumber, String Email,
			String StatusID, String UserTypeID, String SourceID,
			String DeviceTypeID, String CountryCode, String IPAddress,
			String DeviceID) {
		/*
		 * "{ ""SignUp"": {"Password"": ""123456"", ""FirstName"": ""gautam"",
		 * ""LastName"": ""kumar"", ""MobileNumber"": ""9981823755"", ""Email"":
		 * ""friend4u.india@gmail.com"", ""StatusID"": 2, ""UserTypeID"": 3,
		 * ""SourceID"": 1, ""DeviceTypeID"": 2, ""CountryCode"": ""91"",
		 * ""IPAddress"": """", ""DeviceID"": ""123456"" } }"
		 */
		JSONObject paramObject = JSONUtil.getJSONObject("Password", Password,
				"FirstName", FirstName, "LastName", LastName, "MobileNumber",
				MobileNumber, "Email", Email, "StatusID", StatusID,
				"UserTypeID", UserTypeID, "SourceID", SourceID, "DeviceTypeID",
				DeviceTypeID, "CountryCode", CountryCode, "IPAddress",
				IPAddress, "DeviceID", DeviceID);

		/*
		 * JSONArray jsonArray = JSONUtil.getNamedJSONArray("images",
		 * imageJSObj); JSONObject paramObject =
		 * JSONUtil.getJSONObject("first_name", firstName, "last_name",
		 * lastName, "email", email, "dob", dob, "zip_code", zipCode, "pass",
		 * password, "gender", gender, "fb_id", fbID); try {
		 * paramObject.put("images", jsonArray != null ? jsonArray : "[]"); }
		 * catch (JSONException e) { e.printStackTrace(); }
		 */

		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("SignUp",
				paramObject);
		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");
		return requestString;
	}

	// MobileVerifyJSON(DEVICE_TYPE_ID, signUp.LoginSessionKey,
	// VERIFICATION_TYPE_ID);
	public String MobileVerifyJSON(String DeviceTypeID,
			String VerificationTypeID) {

		/*
		 * "{ ""Verification"": { ""DeviceTypeID"": 2, ""LoginSessionKey"":
		 * ""3b67eb5913c21ef42ed67c5822380adf"", ""VerificationTypeID"": 2 } }"
		 */

		JSONObject paramObject = JSONUtil.getJSONObject("DeviceTypeID",
				DeviceTypeID, "LoginSessionKey", LoginSessionKey,
				"VerificationTypeID", VerificationTypeID);
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("Verification",
				paramObject);

		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");

		return requestString;
	}

	public String saveVerifyJSON(String DeviceTypeID,
			String VerificationTypeID, String VerificationCode,
			String UserTypeID) {

		/*
		 * "{ ""SaveVerification"": { ""DeviceTypeID"": 2, ""LoginSessionKey"":
		 * ""ddfgdgdg"", ""VerificationTypeID"": 2, ""VerificationCode"":
		 * ""P0QsbGoK"" } }"
		 */

		JSONObject paramObject = JSONUtil.getJSONObject("DeviceTypeID",
				DeviceTypeID, "LoginSessionKey", LoginSessionKey,
				"VerificationTypeID", VerificationTypeID, "VerificationCode",
				VerificationCode, "UserTypeID", UserTypeID);
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject(
				"SaveVerification", paramObject);

		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");

		return requestString;
	}

	public String MobileEditJSON(String DeviceTypeID, String MobileNumber,
			String UserTypeID, String CountryCode) {

		/*
		 * " ""SaveMobileNumber"": { ""DeviceTypeID"": 2, ""LoginSessionKey"":
		 * ""bwpmuchNDwRB"", ""MobileNumber"": ""5455212215564655"",
		 * ""UserTypeID"": 3 }"
		 */

		JSONObject paramObject = JSONUtil.getJSONObject("DeviceTypeID",
				DeviceTypeID, "LoginSessionKey", LoginSessionKey,
				"MobileNumber", MobileNumber, "UserTypeID", UserTypeID,
				"CountryCode", CountryCode);

		JSONObject requestJSObj = JSONUtil.getNamedJSONObject(
				"SaveMobileNumber", paramObject);

		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");

		return requestString;
	}

	public String getSendDeviceTokenJSON(String userId, String deviceToken) {
		JSONObject paramObject = JSONUtil.getJSONObject("user_id", userId,
				"deviceToken", deviceToken, "device_type", "1");
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("deviceToken",
				paramObject);

		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");

		return requestString;
	}

	// Login Json
	public String getSignInJSON(String Username, String Password,
			String DeviceTypeID, String SourceID, String IPAddress,
			String DeviceID, String Latitude, String Longitude,
			String UserTypeID) {
		/*
		 * 
		 * ""SignIn"": { ""Username"": ""dollys@vinfotech.com"", ""Password"":
		 * ""e10adc3949ba59abbe56e057f20f883e"", ""DeviceTypeID"": 2,
		 * ""SourceID"": 1, ""IPAddress"": ""192.168.1.1"", ""DeviceID"":
		 * ""363wyhshw527247hsfh"", ""Latitude"": """", ""Longitude"": """",
		 * ""UserTypeID"": 3
		 */

		JSONObject paramObject = JSONUtil.getJSONObject("Username",
				Username.trim(), "Password", Password.trim(), "DeviceTypeID",
				DeviceTypeID.trim(), "SourceID", SourceID.trim(), "IPAddress",
				IPAddress.trim(), "DeviceID", DeviceID.trim(), "Latitude",
				Latitude.trim(), "Longitude", Longitude.trim(), "UserTypeID",
				UserTypeID);

		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("SignIn",
				paramObject);
		return null == requestJSObj ? "" : requestJSObj.toString();
	}

	// Sign Out Json
	public String getSingOutJson(String DeviceTypeID) {
		/*
		 * { "SignOut": { "DeviceTypeID": 2, "LoginSessionKey":
		 * "3b67eb5913c21ef42ed67c5822380adf" } }
		 */

		Log.v("LoginSessionKey", LoginSessionKey);
		JSONObject paramObject = JSONUtil.getJSONObject("DeviceTypeID",
				DeviceTypeID, "LoginSessionKey", LoginSessionKey);
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("SignOut",
				paramObject);
		return null == requestJSObj ? "" : requestJSObj.toString();

	}

	public String changePasswordJSON(String UserID, String Password,
			String PasswordNew, String DeviceTypeID) {

		/*
		 * { ""SavePassword"": { ""UserID"": ""297"", ""LoginSessionKey"":
		 * ""3b67eb5913c21ef42ed67c5822380adf"", ""Password"": ""1234567"",
		 * ""PasswordNew"": ""12345678"", ""DeviceTypeID"": 2, ""UserTypeID"":2
		 */
		JSONObject paramObject = JSONUtil.getJSONObject("UserID", UserID,
				"LoginSessionKey", "", "Password", Password, "PasswordNew",
				PasswordNew, "DeviceTypeID", DeviceTypeID, "UserTypeID",
				RegistrationActivity.USER_TYPE_ID);

		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("SavePassword",
				paramObject);

		String requestString = (null != requestJSObj ? requestJSObj.toString()
				: "");
		return requestString;
	}

	public static String getRecoverLoginJSON(String email, String DeviceTypeID) {

		/*
		 * " ""Recovery"": { ""Email"": ""dollys@vinfotech.com"",
		 * ""DeviceTypeID"": 2, ""UserTypeID"":2 }
		 */
		JSONObject requestObject = null;
		requestObject = JSONUtil.getJSONObject("Email", email, "DeviceTypeID",
				DeviceTypeID, "UserTypeID", RegistrationActivity.USER_TYPE_ID);
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject("Recovery",
				requestObject);
		return null == requestJSObj ? "" : requestJSObj.toString();
	}

	public String getUploadProfileJSON(String DeviceTypeID, String ImageType,
			String ImageString) {

		/*
		 * {"UploadProfilePicture":{"DeviceTypeID":"2","ImageString":
		 * "iVBORw0KGgoAAAANSUhEUgAAAFEAAABRCAYAAACqj0o2AAAAHGlET1QAAAACAAAA
		 * ,"ImageType":"png","LoginSessionKey":"PRdU0nJbHiZW"}}
		 */

		JSONObject requestObject = null;
		requestObject = JSONUtil.getJSONObject("DeviceTypeID", DeviceTypeID,
				"ImageType", ImageType, "LoginSessionKey", LoginSessionKey,
				"ImageString", ImageString);
		JSONObject requestJSObj = JSONUtil.getNamedJSONObject(
				"UploadProfilePicture", requestObject);
		return null == requestJSObj ? "" : requestJSObj.toString();
	}
}
