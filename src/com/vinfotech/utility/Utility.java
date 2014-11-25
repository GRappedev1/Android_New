package com.vinfotech.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Utility {

	/**
	 * Hides the soft input keyboard
	 * 
	 * @param editText
	 *            the container edit text for which soft keyboard is shown
	 */
	public static void hideSoftKeyboard(EditText editText) {
		if (null != editText) {
			InputMethodManager imm = (InputMethodManager) editText.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	public static void showDialog(Context mContext, String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);
		alertDialogBuilder.setTitle(title);

		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public static void showDialog(Context mContext, String title, int message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);
		alertDialogBuilder.setTitle(title);

		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	// Encripted Password
	public static final String md5(final String plainString) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(plainString.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < messageDigest.length; i++) {

				String h = Integer.toHexString(0xFF & messageDigest[i]);

				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}

			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return "";
	}

	// Declare the length of Mobile edit text
	public static void setMaxLength(EditText editText, int length) {
		// Create a new InputFilter to define the maximum length
		InputFilter maxLengthFilter = new InputFilter.LengthFilter(length);
		// Apply the filter to the EditText. The array can contain other
		// filters.
		editText.setFilters(new InputFilter[] { maxLengthFilter });
	}

	public static void showToast(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_LONG).show();

	}

}
