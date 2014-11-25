package com.vinfotech.model;

import org.json.JSONException;
import org.json.JSONObject;


public class Categories {
	public int CategoriesID;
	public String category_name;
	public int categoryOrderNumber;

	public Categories(int CategoriesID, String category_name) {
		super();
		this.CategoriesID = CategoriesID;
		this.category_name = category_name;
	}

	public Categories(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {
			CategoriesID = jsonObject.optInt("category_id");
			categoryOrderNumber = jsonObject
					.optInt("category_order_number");
			category_name = jsonObject.optString("category_name");
		}
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("CategoriesID", CategoriesID);
			jsonObject.put("UCategoriess", category_name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
