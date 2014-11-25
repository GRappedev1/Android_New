package com.vinfotech.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.model.NewPassword;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.parser.NewPasswordParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.ValidationUtil;
import com.vinfotech.widget.FloatLabel;

public class ForgotPasswordActivity extends Activity implements HttpResponseListener {
	private RelativeLayout mResetPasswordRl;
	private FloatLabel mEmailEt;
	private HeaderLayout mHeaderLayout;

	private String mEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_password_activity);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
		mResetPasswordRl = (RelativeLayout) findViewById(R.id.reset_password_rl);
		mEmailEt = (FloatLabel) findViewById(R.id.email_et);
		mHeaderLayout = new HeaderLayout(this);
		mHeaderLayout.setHeaderITT(R.drawable.icon_32_left_arrow, R.string.Forgot_Password, R.string.Reset);
		mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
		mHeaderLayout.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		}, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ValidationUtil.isValidEmail(mEmailEt.getEditText().getText().toString())) {
					recoverLoginRequest(mEmailEt.getEditText().getText().toString());
				}
			}
		});

		mResetPasswordRl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mResetPasswordRl.getWindowToken(), 0);
			}
		});

		mEmailEt.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				mEmail = mEmailEt.getEditText().getText().toString();
				if (ValidationUtil.isValidEmail(mEmail)) {
					mHeaderLayout.setTextColor(getResources().getColor(R.color.White));
				} else {
					mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
				}
			}
		});

		mEmailEt.getEditText().setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					mEmail = mEmailEt.getEditText().getText().toString();
					if (ValidationUtil.isValidEmail(mEmail)) {
						recoverLoginRequest(mEmail);
					}
				}
				return false;
			}
		});

	}

	/***
	 * Description Gets json data and send it to the server.
	 * 
	 * @param emailId
	 */

	private void recoverLoginRequest(String emailId) {
		String json = HTTPRequest.getRecoverLoginJSON(emailId, RegistrationActivity.DEVICE_TYPE_ID);
		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));
		httpConnector.setHttpResponseListener(this);
		httpConnector.executeAsync("Recovery/", HTTPRequest.REQ_CODE_RECOVER_LOGIN, "post", false, json, null, HTTPRequest.URLTYPE_SERVICE);
	}

	@Override
	public void onResponse(int requestCode, int statusCode, String response) {
		switch (requestCode) {
		case HTTPRequest.REQ_CODE_RECOVER_LOGIN:
			NewPasswordParser newPasswordParser = new NewPasswordParser();
			if (!newPasswordParser.parse(response)) {
				DialogUtil.showOkDialog(this, newPasswordParser.getMessage());
			} else {
				NewPassword newPassword = newPasswordParser.getNewPassword();
				final String userId = newPassword.UserID;
				final String firstName = newPassword.FirstName;
				final String lastName = newPassword.LastName;

				DialogUtil.showOkDialog(this, R.string.Password_Reset, R.string.Please_check_your_email, new OnClickListener() {
					@Override
					public void onClick(View v) {
						((Dialog) v.getTag()).dismiss();
						Intent intent = new Intent(v.getContext(), SetTemporaryPasswordActivity.class);
						intent.putExtra("UserID", userId);
						intent.putExtra("UserName", firstName);
						intent.putExtra("LastName", lastName);
						intent.putExtra("EmailID", mEmail);
						v.getContext().startActivity(intent);
						finish();
					}
				});
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {
	}
}
