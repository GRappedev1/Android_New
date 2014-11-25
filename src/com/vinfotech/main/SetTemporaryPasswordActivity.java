package com.vinfotech.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.fileutil.ImageLoader;
import com.vinfotech.server.parser.BaseParser;
import com.vinfotech.server.parser.SignInParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.KeyboardUtil;
import com.vinfotech.utility.Utility;
import com.vinfotech.utility.ValidationUtil;
import com.vinfotech.widget.FloatLabel;

public class SetTemporaryPasswordActivity extends Activity implements HttpResponseListener, OnFocusChangeListener {
	private LinearLayout mResetTemporaryPasswordLl;
	private FloatLabel mTempPasswordFl, mPasswordFl;
	private TextView mUserNameTV, mEmailIdTv;
	private ImageView mUserImageIv;

	private HeaderLayout mHeaderLayout;

	private HTTPRequest mHttpRequest;
	private ImageLoader mImageLoader;
	private SignIn mSignIn;

	private String mTemppassword = "", mPassword = "";
	private boolean mTempPassValid, mPasswordValid;
	// To show the error either flotlable is
	private boolean isEditText = false;
	private String mUserID, mLastName, mUserName, mEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.reset_temporary_password_activity);

		mHttpRequest = new HTTPRequest();

		mUserID = getIntent().getExtras().getString("UserID");
		mUserName = getIntent().getExtras().getString("UserName");
		mLastName = getIntent().getExtras().getString("LastName");
		mEmail = getIntent().getExtras().getString("EmailID");

		controlInitialization();

		mImageLoader = new ImageLoader(this);
		mImageLoader.setImageSize(1000);
		mSignIn = new SignIn(this);
		mSignIn.UserID = mUserID;

		// imageLoader.removeImage(signIn.getProfileImage(2));
		mImageLoader.displayImage(mSignIn.getProfileImage(2), mUserImageIv, 2);

		mUserNameTV.setText(mUserName + " " + mLastName);

		mEmailIdTv.setText(mEmail);

		mResetTemporaryPasswordLl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyboardUtil.hideSoftKeyboard(mTempPasswordFl.getEditText());
			}
		});

		mHeaderLayout = new HeaderLayout(this);
		mHeaderLayout.setHeaderITT(R.drawable.icon_32_left_arrow, R.string.Reset_Password, R.string.Save);
		mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
		mHeaderLayout.setListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		}, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTemppassword = mTempPasswordFl.getEditText().getText().toString().trim();
				mPassword = mPasswordFl.getEditText().getText().toString().trim();

				if (mHeaderLayout.getTextColor() == getResources().getColor(R.color.White)) {
					doChangePassword(mTemppassword, mPassword);
				}
			}
		});
	}

	/** controls initialization */
	private void controlInitialization() {
		mUserNameTV = (TextView) findViewById(R.id.user_nameTemp_tv);
		mEmailIdTv = (TextView) findViewById(R.id.userEmilTemp_tv);

		mResetTemporaryPasswordLl = (LinearLayout) findViewById(R.id.reset_temp_pwd_ll);
		mTempPasswordFl = (FloatLabel) findViewById(R.id.temp_pwd_et);
		mPasswordFl = (FloatLabel) findViewById(R.id.new_pwd_et);
		mUserImageIv = (ImageView) findViewById(R.id.userResetPass_iv);
		// mRetypePasswordFl = (FloatLabel) findViewById(R.id.retype_pwd_et);

		mTempPasswordFl.getEditText().setOnFocusChangeListener(this);
		mPasswordFl.getEditText().setOnFocusChangeListener(this);
		// mRetypePasswordFl.getEditText().setOnFocusChangeListener(this);

		mTempPasswordFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mTempPasswordFl);
					mTempPasswordFl.getTextView().setText(getString(R.string.Temporary_Password));
				}

				mTemppassword = mTempPasswordFl.getEditText().getText().toString().trim();
				mTempPassValid = !mTemppassword.equals("");

				handleButtonEnable();
			}
		});

		mPasswordFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mPasswordFl);
					mPasswordFl.getTextView().setText(getString(R.string.New_Password));
				}

				mPassword = mPasswordFl.getEditText().getText().toString().trim();
				mPasswordValid = ValidationUtil.isValidPassword(mPassword) && !mPassword.contains(mUserName);
				handleButtonEnable();
			}
		});

		mPasswordFl.getEditText().setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {

				if (actionId == EditorInfo.IME_ACTION_DONE) {

					mPassword = mPasswordFl.getEditText().getText().toString().trim();
					mPasswordValid = ValidationUtil.isValidPassword(mPassword) && !mPassword.contains(mUserName);
					boolean valid = mTempPassValid && mPasswordValid;

					if (valid) {
						mPasswordFl.getEditText().setError(null);
						doChangePassword(mTemppassword, mPassword);
					}
				}
				return false;
			}
		});
	}

	/** Handle the Enable and disable condition of Register button */
	private void handleButtonEnable() {
		boolean valid = mTempPassValid && mPasswordValid;
		Log.v("valid", "TempPass : " + mTemppassword + "new Pass : " + mPassword);

		if (valid) {
			mHeaderLayout.setTextColor(getResources().getColor(R.color.White));
			mPasswordFl.getEditText().setError(null);
		} else {
			mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
		}
	}

	/** Remove the error from TextView */
	private void removeError(FloatLabel floatLabel) {
		if (null != floatLabel && null != floatLabel.getEditText()) {
			floatLabel.getTextView().setTextColor(getResources().getColor(R.color.LtBlue));
		}

	}

	/** on Focus change of the Edit Texts */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			if (v == mPasswordFl.getEditText() && !mPassword.equals("")) {
				mPasswordValid = mPassword.length() >= 8 && ValidationUtil.isValidPassword(mPassword);
				if (!mPasswordValid) {
					showError(mPasswordFl, R.string.password_Invalid);
					handleButtonEnable();
				}
			}
		}
	}

	private void showError(FloatLabel floatLabel, int errorResId) {
		if (null != floatLabel && null != floatLabel.getEditText()) {
			floatLabel.getTextView().setText(getString(errorResId));
			floatLabel.getTextView().setTextColor(getResources().getColor(R.color.LtRed));
		}
	}

	/**
	 * Description Gets json data and send it to the server.
	 * 
	 * @param tempPassword
	 * @param Password
	 * @param retypePassword
	 */

	private void doChangePassword(String tempPassword, String Password) {
		String json = mHttpRequest.changePasswordJSON(mUserID, Utility.md5(mTemppassword), Utility.md5(mPassword),
				RegistrationActivity.DEVICE_TYPE_ID);
		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));
		httpConnector.setHttpResponseListener(this);
		httpConnector.executeAsync("SavePassword/", HTTPRequest.REQ_CODE_CHANGE_PASSWORD, "post", false, json, null,
				HTTPRequest.URLTYPE_SERVICE);
	}

	private void doLoginServerRequest(String emailAddress, String password) {
		String jsonData = mHttpRequest.getSignInJSON(emailAddress, Utility.md5(password), RegistrationActivity.DEVICE_TYPE_ID,
				RegistrationActivity.SOURCE_ID, String.valueOf(LoginActivity.mIpAddress), String.valueOf(LoginActivity.mGCMRegId),
				String.valueOf(LoginActivity.mCurLat), String.valueOf(LoginActivity.mCurLog), RegistrationActivity.USER_TYPE_ID);

		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));

		httpConnector.setHttpResponseListener(this);
		httpConnector.executeAsync("SignIn/", HTTPRequest.REQ_CODE_LOGIN, "post", false, jsonData, null, HTTPRequest.URLTYPE_SERVICE);
	}

	@Override
	public void onResponse(int requestCode, int statusCode, String response) {
		switch (requestCode) {
		case HTTPRequest.REQ_CODE_CHANGE_PASSWORD:
			BaseParser baseParser = new BaseParser();
			boolean ret = baseParser.parse("SavePassword", response);
			if (ret && baseParser.getResponseCode().equalsIgnoreCase("200")) {
				doLoginServerRequest(mEmail, mPassword);
			} else {
				DialogUtil.showOkDialog(this, baseParser.getMessage());
			}
			break;

		case HTTPRequest.REQ_CODE_LOGIN:
			SignInParser signInParcer = new SignInParser();
			if (!signInParcer.parse(response)) {
				DialogUtil.showOkDialog(this, signInParcer.getMessage());
			} else {
				mSignIn = signInParcer.getSignIn();
				HTTPRequest.setLoginSessionKey(mSignIn.LoginSessionKey);
				mSignIn.persist(this);

				Intent intent = new Intent(this, HomePageActivity.class);
				intent.putExtra("caller", "SetTemporaryPasswordActivity");
				startActivity(intent);
				this.finish();
			}
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {

	}

}
