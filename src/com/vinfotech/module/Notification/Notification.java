package com.vinfotech.module.Notification;

public class Notification {

	private String mHeading, mTime, mImage;

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

	public Notification(String mHeading, String mTime, String mImage) {
		super();
		this.mHeading = mHeading;
		this.mTime = mTime;
		this.mImage = mImage;
	}
}
