package com.vinfotech.module.ListSelection;

public class SelectionList {

	private String mHeading, mTime, mImage;
	private int isSelected;

	/**
	 * @return the mHeading
	 */
	public String getmHeading() {
		return mHeading;
	}

	/**
	 * @return the mTime
	 */
	public String getmTime() {
		return mTime;
	}

	/**
	 * @return the mImage
	 */
	public String getmImage() {
		return mImage;
	}

	public SelectionList(String mHeading, String mTime, String mImage,
			int isSelected) {
		super();
		this.mHeading = mHeading;
		this.mTime = mTime;
		this.mImage = mImage;
		this.isSelected = isSelected;
	}

	/**
	 * @return the isSelected
	 */
	public int getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            the isSelected to set
	 */
	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}
}
