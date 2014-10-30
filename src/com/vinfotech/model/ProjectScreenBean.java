package com.vinfotech.model;

import org.json.JSONObject;

public class ProjectScreenBean {
	public int project_screen_id;
	public String caption;
	public String last_updated_date;
	public String file_name;

	public ProjectScreenBean(int project_screen_id, String caption,
			String last_updated_date, String file_name) {
		super();
		this.project_screen_id = project_screen_id;
		this.caption = caption;
		this.last_updated_date = last_updated_date;
		this.file_name = file_name;

	}

	public ProjectScreenBean(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {
			project_screen_id = jsonObject.optInt("project_screen_id");
			caption = jsonObject.optString("caption");
			last_updated_date = jsonObject.optString("last_updated_date");
			file_name = jsonObject.optString("file_name");

		}
	}
}
