package com.vinfotech.server.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.vinfotech.model.Categories;
import com.vinfotech.model.GetAllData;
import com.vinfotech.model.ProjectBean;

public class GetAllDataParser extends BaseParser {

	public boolean parse(String json) {
		boolean ret = super.parse("all_data", json);
		if (getStaus()) {
			GetAllData.setInstance(null);
			getRoles(getDataObject().optJSONArray("categories"));
			getProjects(getDataObject().optJSONArray("projects"));
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	/*
	 * Retrive categories arraylist from all data params json Array
	 */
	private void getRoles(JSONArray jsonArray) {
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
		GetAllData.getInstance().setCategories(categories);
	}

	/*
	 * Retrive projects arraylist from all data params json Array
	 */
	private void getProjects(JSONArray jsonArray) {
		List<ProjectBean> project = new ArrayList<ProjectBean>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					project.add(new ProjectBean(jsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		GetAllData.getInstance().setProjects(project);
	}
}
