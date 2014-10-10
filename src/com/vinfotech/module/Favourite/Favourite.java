package com.vinfotech.module.Favourite;

public class Favourite {

	private String mHeading, mImage;
	private int mRating;

	/**
	 * @return the mHeading
	 */
	public String getmHeading() {
		return mHeading;
	}

	/**
	 * @return the mRating
	 */
	public int getmRating() {
		return mRating;
	}

	/**
	 * @return the mImage
	 */
	public String getmImage() {
		return mImage;
	}

	public Favourite(String mHeading, int mRating, String mImage) {
		super();
		this.mHeading = mHeading;
		this.mRating = mRating;
		this.mImage = mImage;
	}
}
