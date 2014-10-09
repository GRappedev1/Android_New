package com.vinfotech.utility;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {

	/**
	 * Hide opened soft keyboard
	 * */
	public static void hideSoftKeyboard(EditText editText) {
		if (null != editText) {
			InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}
}
