package com.vinfotech.module.ImageGridView;

public class GridModel {

	private String mHeading, mImage;

	/**
	 * @return the mHeading
	 */
	public String getmHeading() {
		return mHeading;
	}

	/**
	 * @return the mImage
	 */
	public String getmImage() {
		return mImage;
	}

	public GridModel(String mHeading, String mImage) {
		super();
		this.mHeading = mHeading;

		this.mImage = mImage;
	}
}
