package com.vinfotech.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.vinfotech.demoapp.R;
import com.vinfotech.dialogs.MobileEditDialog.NumberUpdateListner;
import com.vinfotech.main.RegistrationActivity;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.parser.BaseParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.widget.FloatLabel;

public class MobileVerficationDialog extends Dialog implements HttpResponseListener, OnClickListener {
	private FloatLabel mVerificationCodeFl;
	private TextView mMobileTv, mCountryTv;
	private Button mEditNumberBtn, mResendBtn, mSubmitBtn;

	private Context mContext;

	private String mMobileNumber, mCountryCode, mVerficationCode;

	private NumberUpdateListner mNumberUpdateListner;

	private HTTPRequest mHttpRequest;

	/**
	 * Description: Creating dialog box to verify mobile number
	 * 
	 * @param context
	 * @param mobileNum
	 * @param countryCode
	 */
	public MobileVerficationDialog(Context context, String mobileNum, String countryCode, NumberUpdateListner listner) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.mobile_number_verification_dialog);

		this.mContext = context;
		mMobileNumber = mobileNum;
		mCountryCode = countryCode;
		this.mNumberUpdateListner = listner;

		mHttpRequest = new HTTPRequest();

		// Find Ids of Controls
		mVerificationCodeFl = (FloatLabel) findViewById(R.id.verification_code_et);
		mResendBtn = (Button) findViewById(R.id.resend_btn);
		mEditNumberBtn = (Button) findViewById(R.id.edit_number_btn);
		mSubmitBtn = (Button) findViewById(R.id.submit_btn);
		mMobileTv = (TextView) findViewById(R.id.mobileNumVerify_tv);
		mCountryTv = (TextView) findViewById(R.id.countryNumVerify_tv);

		mVerficationCode = mVerificationCodeFl.getEditText().getText().toString().trim();
		checkEnableSubmit();

		mVerificationCodeFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				mVerficationCode = mVerificationCodeFl.getEditText().getText().toString().trim();
				checkEnableSubmit();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		// Set the values in Controls
		mMobileTv.setText(mMobileNumber);
		mCountryTv.setText(mCountryCode);

		mResendBtn.setOnClickListener(this);
		mEditNumberBtn.setOnClickListener(this);
		mSubmitBtn.setOnClickListener(this);

		mVerificationCodeFl.getEditText().setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (mVerficationCode.length() > 1) {
						saveVerification();
					}

				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resend_btn:
			resendVerification();
			break;

		case R.id.edit_number_btn:
			dismiss();
			MobileEditDialog registerMobileActivity = new MobileEditDialog(mContext, mMobileNumber, mCountryCode, mNumberUpdateListner);
			registerMobileActivity.show();
			this.dismiss();
			break;

		case R.id.submit_btn:
			mVerficationCode = mVerificationCodeFl.getEditText().getText().toString().trim();
			saveVerification();
			break;

		}
	}

	/** Enable / Disable the submit button */
	private void checkEnableSubmit() {
		if (mVerficationCode.length() > 1) {
			mSubmitBtn.setEnabled(true);
		} else {
			mSubmitBtn.setEnabled(false);
		}
	}

	/**
	 * Description Gets json data and send it to the server.
	 * 
	 * @param verificationcode
	 */
	public void saveVerification() {
		String json = mHttpRequest.saveVerifyJSON(RegistrationActivity.DEVICE_TYPE_ID, RegistrationActivity.VERIFICATION_TYPE_ID,
				mVerficationCode, RegistrationActivity.USER_TYPE_ID);
		HttpConnector httpConnector = new HttpConnector(mContext, null, DialogUtil.createProgressDialog(mContext, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("SaveVerification/", HTTPRequest.REQ_CODE_SEND_VERIFICATIONCODE, "post", false, json, null,
				HTTPRequest.URLTYPE_SERVICE);
	}

	/**
	 * * Description Gets json data and send it to the server.
	 * 
	 * @param verificationcode
	 */
	public void resendVerification() {
		String json = mHttpRequest.MobileVerifyJSON(RegistrationActivity.DEVICE_TYPE_ID, RegistrationActivity.VERIFICATION_TYPE_ID);
		HttpConnector httpConnector = new HttpConnector(mContext, null, DialogUtil.createProgressDialog(mContext, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("Verification/", HTTPRequest.REQ_CODE_RESEND_VERIFICATIONCODE, "post", false, json, null,
				HTTPRequest.URLTYPE_SERVICE);

	}

	@Override
	public void onResponse(int reqCode, int statusCode, String response) {
		switch (reqCode) {
		case HTTPRequest.REQ_CODE_SEND_VERIFICATIONCODE:
			BaseParser baseParser = new BaseParser();
			boolean ret = baseParser.parse("SaveVerification", response);
			if (ret && baseParser.getResponseCode().equalsIgnoreCase("200")) {
				this.dismiss();
			} else {
				DialogUtil.showOkDialog(mContext, baseParser.getMessage());
			}
			break;

		case HTTPRequest.REQ_CODE_RESEND_VERIFICATIONCODE:
			baseParser = new BaseParser();
			ret = baseParser.parse("Verification", response);
			if (ret && baseParser.getResponseCode().equalsIgnoreCase("200")) {
				DialogUtil.showOkDialog(mContext, mContext.getString(R.string.code_sent));
			} else {
				DialogUtil.showOkDialog(mContext, "Please try again");
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.dismiss();
		((Activity) mContext).finish();
	}

}
