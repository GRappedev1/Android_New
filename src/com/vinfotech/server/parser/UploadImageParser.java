package com.vinfotech.server.parser;

import org.json.JSONObject;

public class UploadImageParser extends BaseParser {
	private ProfileImage profileImage;

	public boolean parse(String json) {
		boolean ret = super.parse("UploadProfilePicture", json);
		if (ret && getResponseCode().equalsIgnoreCase("200")) {
			profileImage = new ProfileImage(getDataObject());
		}

		return ret;
	}

	public ProfileImage getProfileImage() {
		return profileImage;
	}

	public class ProfileImage {
		public String ProfilePictureName;
		public String ProfilePictureURL;

		public ProfileImage(JSONObject jsonObject) {
			super();
			if (null != jsonObject) {
				this.ProfilePictureName = jsonObject.optString("ProfilePictureName");
				this.ProfilePictureURL = jsonObject.optString("ProfilePictureURL");
			}
		}

		@Override
		public String toString() {
			return "ProfileImage [ProfilePictureName=" + ProfilePictureName + ", ProfilePictureURL=" + ProfilePictureURL + "]";
		}

	}
}
