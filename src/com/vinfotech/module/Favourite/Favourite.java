package com.vinfotech.module.Favourite;

public class Favourite {

	private String mHeading, mImage;
	private float mRating;

	/**
	 * @param mRating
	 *            the mRating to set
	 */
	public void setmRating(float mRating) {
		this.mRating = mRating;
	}

	/**
	 * @return the mHeading
	 */
	public String getmHeading() {
		return mHeading;
	}

	/**
	 * @return the mRating
	 */
	public float getmRating() {
		return mRating;
	}

	/**
	 * @return the mImage
	 */
	public String getmImage() {
		return mImage;
	}

	public Favourite(String mHeading, float mRating, String mImage) {
		super();
		this.mHeading = mHeading;
		this.mRating = mRating;
		this.mImage = mImage;
	}
}
