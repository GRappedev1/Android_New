package com.vinfotech.module.Poststatus;

public class Poststatus {

	private String mTitle, mDescription, mLikes, mChats;

	public Poststatus(String mTitle, String mDescription, String mLikes,
			String mChats) {
		super();
		this.mTitle = mTitle;
		this.mDescription = mDescription;
		this.mLikes = mLikes;
		this.mChats = mChats;
	}

	/**
	 * @return the mTitle
	 */
	public String getmTitle() {
		return mTitle;
	}

	/**
	 * @return the mDescription
	 */
	public String getmDescription() {
		return mDescription;
	}

	/**
	 * @return the mLikes
	 */
	public String getmLikes() {
		return mLikes;
	}

	/**
	 * @return the mChats
	 */
	public String getmChats() {
		return mChats;
	}
}
