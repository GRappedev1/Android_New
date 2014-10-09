package com.vinfotech.server.parser;

import com.vinfotech.model.SignIn;

public class SignupParser extends BaseParser {
	private SignIn signIn;

	public boolean parse(String json) {
		boolean ret = super.parse("SignUp", json);
		if (ret && getResponseCode().equalsIgnoreCase("200")) {
			signIn = new SignIn(getDataObject());
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public SignIn getSignIn() {
		return signIn;
	}
}
