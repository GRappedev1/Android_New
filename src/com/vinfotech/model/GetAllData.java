package com.vinfotech.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAllData {

	public List<Categories> roles;

	public GetAllData(JSONObject jsonObject) {
		super();

		this.roles = getRoles(jsonObject.optJSONArray("categories"));

	}

	private List<Categories> getRoles(JSONArray jsonArray) {
		List<Categories> categories = new ArrayList<Categories>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					categories.add(new Categories(jsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return categories;
	}

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
}
