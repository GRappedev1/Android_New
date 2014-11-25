package com.vinfotech.model;

import java.io.Serializable;

import org.json.JSONObject;

public class ProjectCategoriesBean implements Serializable {
	public int category_id;
	public String category_name;
	public String last_updated_date;

	public ProjectCategoriesBean(int category_id, String category_name,
			String last_updated_date) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
		this.last_updated_date = last_updated_date;

	}

	public ProjectCategoriesBean(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {
			category_id = jsonObject.optInt("category_id");
			category_name = jsonObject.optString("category_name");
			last_updated_date = jsonObject.optString("last_updated_date");

		}
	}
}
