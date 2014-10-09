package com.vinfotech.model;

public class DashBoard {

	public String mTitle, mSubTilte;
	private int mIds;

	public DashBoard(String mTitle, String mSubTilte, int mIds) {
		super();
		this.mTitle = mTitle;
		this.mSubTilte = mSubTilte;
		this.setmIds(mIds);
	}

	/**
	 * @return the mTitle
	 */
	public String getmTitle() {
		return mTitle;
	}

	/**
	 * @return the mSubTilte
	 */
	public String getmSubTilte() {
		return mSubTilte;
	}

	/**
	 * @return the mIds
	 */
	public int getmIds() {
		return mIds;
	}

	/**
	 * @param mIds
	 *            the mIds to set
	 */
	public void setmIds(int mIds) {
		this.mIds = mIds;
	}

}
