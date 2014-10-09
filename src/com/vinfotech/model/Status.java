package com.vinfotech.model;

import org.json.JSONObject;

public class Status {
	public String oldStatus;
	public String newStatus;

	public Status(JSONObject jsonObject) {
		if (null != jsonObject) {
			this.oldStatus = jsonObject.optString("old_status");
			this.newStatus = jsonObject.optString("new_status");
		}
	}

	@Override
	public String toString() {
		return "Status [oldStatus=" + oldStatus + ", newStatus=" + newStatus + "]";
	}

}
