package com.vinfotech.model;

import java.util.ArrayList;
import java.util.List;


public class GetAllData {

	public List<Categories> categories=  new ArrayList<Categories>();;
	public List<Categories> getCategories() {
		return categories;
	}

	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}

	private static GetAllData instance;

	public static GetAllData getInstance() {

		if (instance == null)
			instance = new GetAllData();
		return instance;
	}

	public static void setInstance(GetAllData instance) {
		GetAllData.instance = instance;
	}

	



}
