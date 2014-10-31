package com.vinfotech.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProjectBean {

	public int projectId;
	public String project_name;
	public int file_upload_id;
	public String projectonelinedescription;
	public String project_description;
	public String display_url;
	public String redirect_url;
	public String technology_used;
	public String company_name;
	public String genre;
	public String status;
	public String last_updated_date;
	public String file_name;
	public List<ProjectCategoriesBean> pCategories = new ArrayList<ProjectCategoriesBean>();
	public List<ProjectScreenBean> pScreen = new ArrayList<ProjectScreenBean>();;

	public ProjectBean(int projectId, String project_name, int file_upload_id,
			String projectonelinedescription, String project_description,
			String display_url, String redirect_url, String technology_used,
			String company_name, String genre, String status,
			String last_updated_date, String file_name) {
		super();
		this.projectId = projectId;
		this.project_name = project_name;
		this.file_upload_id = file_upload_id;
		this.projectonelinedescription = projectonelinedescription;
		this.project_description = project_description;
		this.display_url = display_url;
		this.redirect_url = redirect_url;
		this.technology_used = technology_used;
		this.company_name = company_name;
		this.genre = genre;
		this.status = status;
		this.last_updated_date = last_updated_date;
		this.file_name = file_name;

	}

	public ProjectBean(JSONObject jsonObject) {
		super();
		if (null != jsonObject) {
			projectId = jsonObject.optInt("project_id");
			project_name = jsonObject.optString("project_name");
			file_upload_id = jsonObject.optInt("file_upload_id");
			projectonelinedescription = jsonObject
					.optString("project_one_line_description");
			project_description = jsonObject.optString("project_description");
			display_url = jsonObject.optString("display_url");
			redirect_url = jsonObject.optString("redirect_url");
			technology_used = jsonObject.optString("technology_used");
			company_name = jsonObject.optString("company_name");
			genre = jsonObject.optString("genre");
			status = jsonObject.optString("status");
			last_updated_date = jsonObject.optString("last_updated_date");
			file_name = jsonObject.optString("file_name");
			getProjectsCategories(jsonObject.optJSONArray("project_categories"));
			getProjectsScreen(jsonObject.optJSONArray("project_screens"));

		}
	}

	/*
	 * Retrive projects categories arraylist from all data params json Array
	 */
	private void getProjectsCategories(JSONArray jsonArray) {
		List<ProjectCategoriesBean> projectCategory = new ArrayList<ProjectCategoriesBean>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					projectCategory.add(new ProjectCategoriesBean(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			setpCategories(projectCategory);
		}
	}

	/*
	 * Retrive projects categories arraylist from all data params json Array
	 */
	private void getProjectsScreen(JSONArray jsonArray) {
		List<ProjectScreenBean> projectScreen = new ArrayList<ProjectScreenBean>();
		if (null != jsonArray) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					projectScreen.add(new ProjectScreenBean(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			setpScreen(projectScreen);
		}
	}

	public List<ProjectCategoriesBean> getpCategories() {
		return pCategories;
	}

	public void setpCategories(List<ProjectCategoriesBean> pCategories) {
		this.pCategories = pCategories;
	}

	public List<ProjectScreenBean> getpScreen() {
		return pScreen;
	}

	public void setpScreen(List<ProjectScreenBean> pScreen) {
		this.pScreen = pScreen;
	}
}
