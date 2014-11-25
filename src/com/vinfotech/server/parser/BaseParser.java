package com.vinfotech.server.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base response parser
 * 
 * @author ramanands
 * 
 */
public class BaseParser {
	private String mMessage = "Can not connect to Medlinx server";
	private boolean mStatus = false;
	private String mResponseCode = "unknown";
	protected JSONObject responseObject = null;

	
	public boolean parse(String request, String json) {
		if (null != json) {
			try {
				responseObject = new JSONObject(json);
				if (null != request && request.trim().length() > 0) {
					responseObject = responseObject.optJSONObject(request);
				}
				if (null != responseObject) {
					mStatus = responseObject.optBoolean("status", false);
//					mResponseCode = responseObject.optString("ResponseCode", "failure");
					mMessage = responseObject.optString("msg", "Message not found");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 
	 * @return Received data object
	 */
	protected JSONObject getDataObject() {
		if (null == responseObject) {
			return null;
		}

		try {
			return responseObject.getJSONObject("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @return Received data array
	 */
	protected JSONArray getDataArray() {

		if (null == responseObject) {
			return null;
		}

		try {
			return responseObject.getJSONArray("Data");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @return Status code
	 */
	public boolean getStaus() {
		return this.mStatus;
	}

	/**
	 * 
	 * @return Response code
	 */
	public String getResponseCode() {
		return this.mResponseCode;
	}

	/**
	 * 
	 * @return Parse message. It may be error or success message
	 */
	public String getMessage() {
		return this.mMessage;
	}
}
