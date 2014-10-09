package com.vinfotech.main;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.vinfotech.demoapp.R;
import com.vinfotech.dialogs.MobileEditDialog;
import com.vinfotech.dialogs.MobileEditDialog.NumberUpdateListner;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.fileutil.FileUploadUtil;
import com.vinfotech.server.parser.SignupParser;
import com.vinfotech.server.parser.UploadImageParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.ImageUtil;
import com.vinfotech.utility.KeyboardUtil;
import com.vinfotech.utility.MCCUtil;
import com.vinfotech.utility.MCCUtil.MCC;
import com.vinfotech.utility.Utility;
import com.vinfotech.utility.ValidationUtil;
import com.vinfotech.widget.FloatLabel;

public class RegistrationActivity extends Activity implements OnClickListener, HttpResponseListener, OnFocusChangeListener {
	private Button mRegisterBtn, mCancelBtn;
	private ImageView mUserImageIv;
	private FloatLabel mFirstNameFl, mLastNameFl, mCountryCodeFl, mMobileFl, mEmailFl, mPasswordFl, mConfPasswordFl;

	private boolean validEntries;
	private String mFirstName = "", mLastName = "", mCountryCode = "", mMobile = "", mEmail = "", mPassword = "", mConfPassword = "";
	private boolean mFirstNameValid, mLastNameValid, mCountryCodeValid, mMobileValid, mEmailValid, mPasswordValid, mConfPasswordValid;
	// To show the error either flotlable is editText or TextView;
	private boolean isEditText = false;
	private HTTPRequest mHttpRequest;
	private HeaderLayout mHeaderLayout;

	private Bitmap mPhotoBitmap;

	// Byte array for storing picture bitmap into byte
	private FileUploadUtil mFileUploadUtil;

	private String mGCMRegId = null;

	// Declared for onActivityResult()
	public static final int REQ_CODE_COUNTRYID = 1;
	public static final int REQ_CODE_COUNTRY_DIALOG = 2;
	public static final int REQ_CODE_GALARY = 4;
	public static final int CAPTURE_FILE_FROM_CAMERA = 5;
	public static final int REQ_CODE_CONFIRMATION = 6;

	// 2 = Mobile Verification
	public static final String VERIFICATION_TYPE_ID = "2";

	// Declared for Web service parameters.
	// (1=Web, 2=IPhone,3=AndroidPhone, 4=Ipad,5=AndroidTablet,
	// 6=OtherMobileDevice)
	public static final String DEVICE_TYPE_ID = "3";
	// (3=Staff, 4=Patient)
	public static final String USER_TYPE_ID = "3";
	// StatusID (1=Pending, 2=Approved, 3=Deleted, 5=Verified)
	public static final String STATUS_ID = "2";
	// SourceID (1=Registration from Web, 2 = Mobile)
	public static final String SOURCE_ID = "2";
	private SignIn signIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_activity);

		mHeaderLayout = new HeaderLayout(this);

		mGCMRegId = getIntent().getStringExtra("mGCMRegId");
		Log.d("RegistrationActivity", "onCreate - mGCMRegId=" + mGCMRegId);

		controlInitialization();

		mHttpRequest = new HTTPRequest();

		// Set Phone Number from sim and show in edit text
		String phoneNumber = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getLine1Number();
		Log.v("Mobile Number", "M " + phoneNumber);
		if (phoneNumber != null && !phoneNumber.equals("")) {
			mMobileValid = true;
			mMobileFl.getEditText().setText(phoneNumber);
		}

		// Set Country Code from sim and show in edit text
		String simCountryIso = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getSimCountryIso();
		Log.v("RegistrationActivity", "onCreate - simCountryIso=" + simCountryIso);

		if (simCountryIso != null && !simCountryIso.equals("")) {
			for (MCC mcc : MCCUtil.getInstance().getMCCList()) {
				if (mcc.twoChar.equalsIgnoreCase(simCountryIso) || mcc.threeChar.equalsIgnoreCase(simCountryIso)) {
					mCountryCodeFl.getEditText().setText(mcc.code);
					mCountryCodeValid = true;
				}
			}
		}

		mHeaderLayout.setHeaderITT(R.drawable.icon_32_left_arrow, R.string.Signup, R.string.Done);
		mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
		
		mHeaderLayout.setListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		}, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRegisterBtn.isEnabled()) {
					getFormValues();

					signUpRequest();
				}
			}
		});
	}

	/** Get the initialization of the Xml variables */
	private void controlInitialization() {
		// Initialization of the Widgets
		mRegisterBtn = (Button) findViewById(R.id.register_btn);
		mCancelBtn = (Button) findViewById(R.id.cancelReg_btn);
		mUserImageIv = (ImageView) findViewById(R.id.user_image_registration_iv);
		mFirstNameFl = (FloatLabel) findViewById(R.id.firstNameReg_et);
		mLastNameFl = (FloatLabel) findViewById(R.id.lastname_et);
		mCountryCodeFl = (FloatLabel) findViewById(R.id.countryCode_et);
		mMobileFl = (FloatLabel) findViewById(R.id.mobile_et);
		mEmailFl = (FloatLabel) findViewById(R.id.emailReg_et);
		mPasswordFl = (FloatLabel) findViewById(R.id.password_et);
		mConfPasswordFl = (FloatLabel) findViewById(R.id.confirmPassword_et);

		Utility.setMaxLength(mMobileFl.getEditText(), 15);

		mFileUploadUtil = new FileUploadUtil(RegistrationActivity.this);

		// attach any listener
		mFirstNameFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mFirstNameFl);
					mFirstNameFl.getTextView().setText(getString(R.string.First_Name));
				}
				mFirstName = mFirstNameFl.getEditText().getText().toString().trim();
				mFirstNameValid = ValidationUtil.isValidFirstname(mFirstName);

				handleButtonEnable();
			}
		});

		mLastNameFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mLastNameFl);
					mLastNameFl.getTextView().setText(getString(R.string.Last_Name));
				}
				mLastName = mLastNameFl.getEditText().getText().toString().trim();
				mLastNameValid = mLastName.length() > 0;

				handleButtonEnable();
			}
		});

		mMobileFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mMobileFl);
					mMobileFl.getTextView().setText(getString(R.string.Mobile));
				}
				mMobile = mMobileFl.getEditText().getText().toString().trim();
				mMobileValid = mMobile.length() >= 8 && mMobile.length() <= 15;

				handleButtonEnable();
			}
		});

		mEmailFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mEmailFl);
					mEmailFl.getTextView().setText(getString(R.string.Email));
				}
				mEmail = mEmailFl.getEditText().getText().toString().trim();
				mEmailValid = ValidationUtil.isValidEmail(mEmail);

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
					mPasswordFl.getTextView().setText(getString(R.string.Password));
				}
				mPassword = mPasswordFl.getEditText().getText().toString().trim();
				mPasswordValid = mPassword.length() >= 8 && ValidationUtil.isValidPassword(mPassword);

				handleButtonEnable();
			}
		});

		mConfPasswordFl.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isEditText) {
					removeError(mConfPasswordFl);
					mConfPasswordFl.getTextView().setText(getString(R.string.Confirm_Password));
				}
				mConfPassword = mConfPasswordFl.getEditText().getText().toString().trim();
				mPassword = mPasswordFl.getEditText().getText().toString().trim();

				mConfPasswordValid = mConfPassword.length() >= 8 && mPassword.equals(mConfPassword);

				handleButtonEnable();
			}
		});

		// Click event of SoftKeyboard "Done" button
		mConfPasswordFl.getEditText().setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE && validEntries) {
					getFormValues();
					signUpRequest();
				}
				return false;
			}
		});

		mFirstNameFl.getEditText().setOnFocusChangeListener(this);
		mMobileFl.getEditText().setOnFocusChangeListener(this);
		mEmailFl.getEditText().setOnFocusChangeListener(this);
		mConfPasswordFl.getEditText().setOnFocusChangeListener(this);
		mPasswordFl.getEditText().setOnFocusChangeListener(this);
		mCountryCodeFl.getEditText().setOnFocusChangeListener(this);

		mRegisterBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		mUserImageIv.setOnClickListener(this);
	}

	/** Check the Values of the EditTexts */
	private void getFormValues() {
		mFirstName = mFirstNameFl.getEditText().getText().toString().trim();
		mLastName = mLastNameFl.getEditText().getText().toString().trim();
		mCountryCode = mCountryCodeFl.getEditText().getText().toString().trim();
		mMobile = mMobileFl.getEditText().getText().toString().trim();
		mEmail = mEmailFl.getEditText().getText().toString().trim();
		mPassword = mPasswordFl.getEditText().getText().toString().trim();
		mConfPassword = mConfPasswordFl.getEditText().getText().toString().trim();
	}

	/** Handle the Enable and disable condition of Register button */
	private void handleButtonEnable() {
		validEntries = false;

		boolean valid = mFirstNameValid && mLastNameValid && mCountryCodeValid && mMobileValid && mEmailValid && mPasswordValid
				&& mConfPasswordValid;
		Log.v("valid", "FN : " + mFirstNameValid + " LN : " + mLastNameValid + " mCountryCode : " + mCountryCodeValid + " mMobile : "
				+ mMobileValid + " mEmail : " + mEmailValid + " mPassword : " + mPasswordValid + "  mConfPassword" + mConfPasswordValid);

		validEntries = valid && mPassword.equals(mConfPassword);
		mRegisterBtn.setEnabled(valid);
		if (validEntries) {
			mHeaderLayout.setTextColor(getResources().getColor(R.color.White));
		} else {
			mHeaderLayout.setTextColor(getResources().getColor(R.color.LtGrey));
			mPasswordFl.getEditText().setError(null);
			mConfPasswordFl.getEditText().setError(null);
		}
	}

	/** Remove the error from TextView */
	private void removeError(FloatLabel floatLabel) {
		if (null != floatLabel && null != floatLabel.getEditText()) {
			floatLabel.getTextView().setTextColor(getResources().getColor(R.color.LtBlue));
		}

	}

	private void showError(FloatLabel floatLabel, int errorResId) {
		if (null != floatLabel && null != floatLabel.getEditText()) {
			floatLabel.getTextView().setText(getString(errorResId));
			floatLabel.getTextView().setTextColor(getResources().getColor(R.color.LtRed));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		KeyboardUtil.hideSoftKeyboard(mConfPasswordFl.getEditText());

		switch (requestCode) {
		case REQ_CODE_COUNTRYID: // For selection the country code
			if (resultCode == RESULT_OK) {
				mCountryCodeFl.getEditText().setText(data.getStringExtra("countryCode"));
				mCountryCodeFl.getEditText().clearFocus();
				mCountryCodeValid = true;
			}
			break;

		case REQ_CODE_COUNTRY_DIALOG: // Country code selection for
										// "Edit Mobile" dialog
			if (resultCode == RESULT_OK) {
				MobileEditDialog mobileEditDialog = new MobileEditDialog(RegistrationActivity.this, mMobile,
						data.getStringExtra("countryCode"), new NumberUpdateListner() {

							@Override
							public void onNumberUpdate(String number) {
							}
						});
				mobileEditDialog.show();
			}
			break;

		case REQ_CODE_CONFIRMATION: // To select the event of any button from
			// Confirmation Activity
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				if ("CAMERA".equalsIgnoreCase(result)) {
					// For taking the Picture from camera
					if (isIntentAvailable(RegistrationActivity.this)) {
						mFileUploadUtil.dispatchTakePictureIntent(FileUploadUtil.CAPTURE_FILE_FROM_CAMERA);
					} else {
						Toast.makeText(RegistrationActivity.this, "Permission denied.", Toast.LENGTH_SHORT).show();
					}
				} else if ("GALLERY".equalsIgnoreCase(result)) {
					// To select the image from gallery
					Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, REQ_CODE_GALARY);
				}
			}
			break;

		case REQ_CODE_GALARY: // Fetch the Image from Gallery
			if (data != null && resultCode == RESULT_OK) {
				Uri selectedImage = data.getData();
				mFileUploadUtil.startCropImageActivity(selectedImage);
			} else {
				Log.d("Status:", "Photopicker canceled");
			}
			break;

		case FileUploadUtil.CAPTURE_FILE_FROM_CAMERA:
			// Captrure the Image from Camera
			mFileUploadUtil.startCropImageActivity(mFileUploadUtil.getCaptureImageUri());
			break;

		case FileUploadUtil.CROP_IMAGE_FROM_URI:
			// After cropping picture set bitmap to imageview
			if (data != null && data.getExtras() != null) {
				Bundle extras = data.getExtras();
				mPhotoBitmap = (Bitmap) extras.get("data");

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// compress to which format you want.
				mPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);

				mUserImageIv.setImageBitmap(ImageUtil.getCircledBitmap(mPhotoBitmap));
			}
			break;

		default:
			break;
		}
	}

	// Is device camera Available..
	private boolean isIntentAvailable(Context context) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_image_registration_iv:
			Intent intent = new Intent(RegistrationActivity.this, ConfirmationActivity.class);
			intent.putExtra("ASK_FOR", "TAKE_PHOTO");
			startActivityForResult(intent, REQ_CODE_CONFIRMATION);
			break;

		case R.id.register_btn:
			getFormValues();
			signUpRequest();
			break;

		case R.id.cancelReg_btn:
			finish();
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			if (v == mFirstNameFl.getEditText() && !mFirstName.equals("")) {
				mFirstName = mFirstNameFl.getEditText().getText().toString().trim();
				mFirstNameValid = ValidationUtil.isValidFirstname(mFirstName);
				if (!mFirstNameValid) {
					showError(mFirstNameFl, R.string.Invalid_userid);
					handleButtonEnable();
				}
			} else if (v == mLastNameFl.getEditText() && !mLastNameFl.equals("")) {
				mLastName = mLastNameFl.getEditText().getText().toString().trim();
				mLastNameValid = mLastName.length() > 0;
				if (!mLastNameValid) {
					showError(mLastNameFl, R.string.Please_Enter_Lastname);
					handleButtonEnable();
				}
			} else if (v == mMobileFl.getEditText() && !mMobile.equals("")) {
				mMobile = mMobileFl.getEditText().getText().toString().trim();
				mMobileValid = mMobile.length() >= 8 && mMobile.length() <= 15;
				if (!mMobileValid) {
					showError(mMobileFl, R.string.Mobile_Invalid);
					handleButtonEnable();
				}
			} else if (v == mEmailFl.getEditText() && !mEmail.equals("")) {
				mEmail = mEmailFl.getEditText().getText().toString().trim();
				mEmailValid = ValidationUtil.isValidEmail(mEmail);
				if (!mEmailValid) {
					showError(mEmailFl, R.string.Email_Invalid);
					handleButtonEnable();
				}
			} else if (v == mPasswordFl.getEditText() && !mPassword.equals("")) {
				mPassword = mPasswordFl.getEditText().getText().toString().trim();
				mConfPassword = mConfPasswordFl.getEditText().getText().toString().trim();
				// mPasswordValid = mPassword.length() >= 6;
				mPasswordValid = ValidationUtil.isValidPassword(mPassword) && !mPassword.contains(mFirstName)
						&& !mPassword.contains(mLastName);
				if (!mPasswordValid) {
					showError(mPasswordFl, R.string.password_Invalid);
					handleButtonEnable();
				}
			} else if (v == mConfPasswordFl.getEditText() && !mConfPassword.equals("")) {
				mConfPassword = mConfPasswordFl.getEditText().getText().toString().trim();
				mPassword = mPasswordFl.getEditText().getText().toString().trim();
				mConfPasswordValid = mConfPassword.length() >= 8 && !mPasswordFl.equals("") && mPassword.equals(mConfPassword);

				if (!mConfPasswordValid) {
					showError(mConfPasswordFl, R.string.password_does_not);
					handleButtonEnable();
				}
			}
		} else {
			if (v == mCountryCodeFl.getEditText()) {
				Intent intent = new Intent(RegistrationActivity.this, CountryCodeListActivity.class);
				startActivityForResult(intent, REQ_CODE_COUNTRYID);
			}
		}
	}

	// get Json of SignUp
	public void signUpRequest() {
		String json = mHttpRequest.registrationJSON(Utility.md5(mPassword), mFirstName, mLastName, mMobile, mEmail, STATUS_ID,
				USER_TYPE_ID, SOURCE_ID, DEVICE_TYPE_ID, mCountryCode, String.valueOf(LoginActivity.mIpAddress), LoginActivity.mGCMRegId);

		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("SignUp/", HTTPRequest.REQ_CODE_SIGNUP, "post", false, json, null, HTTPRequest.URLTYPE_SERVICE);

	}

	@Override
	public void onResponse(int requestCode, int statusCode, String response) {
		switch (requestCode) {
		case HTTPRequest.REQ_CODE_SIGNUP:
			SignupParser signUpParcer = new SignupParser();

			if (signUpParcer.parse(response)) {

				signIn = signUpParcer.getSignIn();
				signIn.FirstName = mFirstName;
				signIn.LastName = mLastName;
				signIn.Email = mEmail;

				signIn.MobileNumber = mMobile;
				signIn.CountryCode = mCountryCode;
				signIn.MobileNumberStatus = "1";
				signIn.persist(this);
				Log.v("Session Key : ", "" + signIn.LoginSessionKey);
				HTTPRequest.setLoginSessionKey(signIn.LoginSessionKey);

				Intent intent = new Intent(this, HomePageActivity.class);
				intent.putExtra("caller", "RegistrationActivity");
				startActivity(intent);
				this.finish();

				/**
				 * Calling service in Background : 1. Image upload 2. Send
				 * Verification code on mobile
				 */
				if (mPhotoBitmap != null) {
					UploadProfile(signUpParcer);
				}
			} else {
				DialogUtil.showOkDialog(this, signUpParcer.getMessage());
			}
			break;
		case HTTPRequest.REQ_CODE_UPLOAD_IMAGE:
			UploadImageParser uploadImageParser = new UploadImageParser();
			if (uploadImageParser.parse(response) && null != uploadImageParser.getProfileImage()) {
				Log.d("RegistrationActivity", "Profile image uploaded successfully, Url=" + uploadImageParser.getProfileImage());
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {

	}

	void callMobileVerificaiton() {
		String json = mHttpRequest.MobileVerifyJSON(DEVICE_TYPE_ID, VERIFICATION_TYPE_ID);
		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("Verification/", HTTPRequest.REQ_CODE_UPLOAD_IMAGE, "post", true, json, null,
				HTTPRequest.URLTYPE_SERVICE);
	}

	void UploadProfile(SignupParser signUpParcer) {
		String encoded = ImageUtil.encodeTobase64(mPhotoBitmap);

		HTTPRequest.mbitmap = mPhotoBitmap;

		String json = mHttpRequest.getUploadProfileJSON(DEVICE_TYPE_ID, "jpg", encoded);
		HttpConnector httpConnector = new HttpConnector(this, null, DialogUtil.createProgressDialog(this, 0));
		httpConnector.setHttpResponseListener(this);

		httpConnector.executeAsync("UploadProfilePicture/", HTTPRequest.REQ_CODE_UPLOAD_IMAGE, "post", true, json, null,
				HTTPRequest.URLTYPE_SERVICE);
	}

}
