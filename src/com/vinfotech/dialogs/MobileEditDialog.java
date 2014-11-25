package com.vinfotech.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;

import com.vinfotech.demoapp.R;
import com.vinfotech.main.CountryCodeListActivity;
import com.vinfotech.main.RegistrationActivity;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.parser.BaseParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.Utility;
import com.vinfotech.widget.FloatLabel;

public class MobileEditDialog extends Dialog implements HttpResponseListener, OnClickListener {
	private Button mCancelBtn, mSaveBtn;
	private FloatLabel mCountryCodeFl, mMobileNumberFl;

	private Context mContext;

	private String mMobileNo, mCountryCode;

	private NumberUpdateListner mNumberUpdateListner;
	private HTTPRequest mHttpRequest;

	public MobileEditDialog(Context context, String mobNumber, String countrycode, NumberUpdateListner listener) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.mobile_edit_dialog);

		this.mContext = context;
		mMobileNo = mobNumber;
		mCountryCode = countrycode;
		this.mNumberUpdateListner = listener;

		mHttpRequest = new HTTPRequest();

		mCountryCodeFl = (FloatLabel) findViewById(R.id.country_code_et);
		mMobileNumberFl = (FloatLabel) findViewById(R.id.mobile_number_et);
		mCancelBtn = (Button) findViewById(R.id.cancel_btn);
		mSaveBtn = (Button) findViewById(R.id.save_btn);
		mCancelBtn.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
		mCountryCodeFl.getEditText().setOnClickListener(this);
		mMobileNumberFl.getEditText().setText(mobNumber);

		Utility.setMaxLength(mMobileNumberFl.getEditText(), 15);

		mMobileNumberFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				String mobileNo = mMobileNumberFl.getEditText().getText().toString().trim();
				Log.d("MobileEditDialog", "onTextChanged - numberUpdateListner=" + mNumberUpdateListner + ", mobileNo=" + mobileNo);

				if (mobileNo.length() >= 8 && mobileNo.length() <= 15) {
					mSaveBtn.setEnabled(true);

					if (null != mNumberUpdateListner) {
						mNumberUpdateListner.onNumberUpdate(mobileNo);
					}
				} else
					mSaveBtn.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		mCountryCodeFl.getEditText().setText(mCountryCode);

		// Listener to select the country code from list
		mCountryCodeFl.getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean bool) {
				if (bool) {
					Intent intent = new Intent(mContext, CountryCodeListActivity.class);
					((Activity) mContext).startActivityForResult(intent, RegistrationActivity.REQ_CODE_COUNTRY_DIALOG);
					MobileEditDialog.this.dismiss();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.cancel_btn:
			MobileVerficationDialog mobileVerficationActivity = new MobileVerficationDialog(mContext, mMobileNo, mCountryCode,
					mNumberUpdateListner);
			mobileVerficationActivity.show();
			dismiss();
			break;
		case R.id.save_btn:
			mMobileNo = mMobileNumberFl.getEditText().getText().toString().trim();
			if (mMobileNo.length() >= 8 && mMobileNo.length() <= 15) {
				onSaveMobileNumberRequest(mCountryCode, mMobileNo);
			} else {
				mSaveBtn.setEnabled(false);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Description Gets json data and send it to the server.
	 * 
	 * @param mCountryCode
	 * @param mMobileNo
	 */
	public void onSaveMobileNumberRequest(String mCountryCode, String mMobileNo) {
		String json = mHttpRequest.MobileEditJSON(RegistrationActivity.DEVICE_TYPE_ID, mMobileNo, RegistrationActivity.USER_TYPE_ID,
				mCountryCode);
		HttpConnector httpConnector = new HttpConnector(mContext, null, DialogUtil.createProgressDialog(mContext, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("SaveMobileNumber/", HTTPRequest.REQ_CODE_SAVE_MOBILE_NUMBER, "post", false, json, null,
				HTTPRequest.URLTYPE_SERVICE);

	}

	@Override
	public void onResponse(int reqCode, int statusCode, String response) {
		switch (reqCode) {
		case HTTPRequest.REQ_CODE_SAVE_MOBILE_NUMBER:

			BaseParser baseParser = new BaseParser();
			boolean ret = baseParser.parse("SaveMobileNumber", response);

			if (ret && baseParser.getResponseCode().equalsIgnoreCase("200")) {

				MobileVerficationDialog mobileVerficationDialog = new MobileVerficationDialog(mContext, mMobileNo, mCountryCode,
						mNumberUpdateListner);
				mobileVerficationDialog.show();
				this.dismiss();

			} else {
				DialogUtil.showOkDialog(mContext, baseParser.getMessage());
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {
	}

	public interface NumberUpdateListner {

		void onNumberUpdate(String number);
	}

	@Override
	public void onBackPressed() {
	}

}
