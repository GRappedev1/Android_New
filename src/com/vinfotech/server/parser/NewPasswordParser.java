package com.vinfotech.server.parser;

import com.vinfotech.model.NewPassword;

public class NewPasswordParser extends BaseParser {

	private NewPassword newPassword;

	public boolean parse(String json) {
		boolean ret = super.parse("Recovery", json);
		if (ret && getResponseCode().equalsIgnoreCase("200")) {
			newPassword = new NewPassword(getDataObject());
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public NewPassword getNewPassword() {
		return newPassword;
	}
}
