package com.vinfotech.main;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vinfotech.dialogs.MobileEditDialog;
import com.vinfotech.main.GCMIntentService.GCMRegisterListener;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.widget.FloatLabel;

public class LoginActivity extends Activity implements OnClickListener,
		HttpResponseListener, GCMRegisterListener {
	private FloatLabel mUsernameFl, mPasswordFl;
	private Button mLoginBtn, mLoginWithDoximityBtn;
	private TextView mForgotPasswordTv, mSignupTv;
	private LinearLayout mLoginpageLl, mContainerLl;
	private ImageView mLogoIv;

	private HTTPRequest mHttpRequest;
	// may be either Small, Normal or Large
	private String mDeviceDensity = "";
	private String username, password;

	public static double mCurLat = 0.0, mCurLog = 0.0;
	public static int mIpAddress = 0;
	public static String mGCMRegId = "123";

	public String editMobileNumber;

	private MobileEditDialog mobileEditDialog;

	// 192.168.0.123:81/gitprojects/455-medlinx-android.git
	private int mDeviceHeight, mDeviceWidth;

	private Handler handler = new Handler();

	@Override
	public void onRegistered(String deviceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrored() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(int reqCode, int statusCode, String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel(boolean canceled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.login_activity);
	//
	// getScreenSize();
	//
	// getIpAddress();
	//
	// mHttpRequest = new HTTPRequest();
	//
	// // mUsernameFl = (FloatLabel) findViewById(R.id.username_et);
	// // mPasswordFl = (FloatLabel) findViewById(R.id.password_et);
	// // mLoginpageLl = (LinearLayout) findViewById(R.id.loginpage_ll);
	// // mLoginBtn = (Button) findViewById(R.id.login_btn);
	// // mLoginWithDoximityBtn = (Button)
	// findViewById(R.id.login_with_doximity_btn);
	// // mForgotPasswordTv = (TextView) findViewById(R.id.forgot_password_tv);
	// // mSignupTv = (TextView) findViewById(R.id.signup_tv);
	//
	// username = mUsernameFl.getEditText().getText().toString().trim();
	// password = mPasswordFl.getEditText().getText().toString().trim();
	//
	// mLoginBtn.setOnClickListener(this);
	// mLoginWithDoximityBtn.setOnClickListener(this);
	// mForgotPasswordTv.setOnClickListener(this);
	// mSignupTv.setOnClickListener(this);
	// mLoginpageLl.setOnClickListener(this);
	//
	// mUsernameFl.requestFocus();
	//
	// initAndShowLogoAnim();
	//
	// /* GCM service variables */
	// try {
	//
	// GCMIntentService.addGCMRegisterListener(this);
	// GCMRegistrar.checkDevice(this);
	// GCMRegistrar.checkManifest(this);
	// final String regId = GCMRegistrar.getRegistrationId(this);
	// Log.d("LoginActivity", "onCreate - regId=" + regId);
	// if (regId.equals("")) {
	// GCMRegistrar.register(this, GCMIntentService.PROJECT_ID);
	// } else {
	// onRegistered(regId);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// /* GCM service variables */
	//
	// checkStateScreen();
	//
	// mPasswordFl.getEditText().setOnEditorActionListener(new
	// OnEditorActionListener() {
	//
	// @Override
	// public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2)
	// {
	// if (actionId == EditorInfo.IME_ACTION_DONE) {
	// username = mUsernameFl.getEditText().getText().toString().trim();
	// password = mPasswordFl.getEditText().getText().toString().trim();
	//
	// if (isLoginFieldValidated(username, password)) {
	// loginServerRequest(username, password);
	// }
	// }
	// return false;
	// }
	// });
	// }
	//
	// void checkStateScreen() {
	// SignIn signIn = new SignIn(this);
	// if (!signIn.isEmptyPreferance(this)) {
	// HTTPRequest.setLoginSessionKey(signIn.LoginSessionKey);
	// Log.v("checkStateScreen", "Empty shared Preference");
	// // 1 = not verified, 5 =
	// if (Integer.parseInt(signIn.MobileNumberStatus) != 5) {
	// } else {
	// Intent intent = new Intent(this, HomePageActivity.class);
	// intent.putExtra("caller", "LoginActivity");
	// startActivity(intent);
	// this.finish();
	// }
	// }
	// }
	//
	// @Override
	// public void onRegistered(String gcmRegId) {
	// Log.d("LoginActivity", "onRegistered - gcmRegId=" + gcmRegId);
	// mGCMRegId = gcmRegId;
	// }
	//
	// @Override
	// public void onErrored() {
	// Log.w("LoginActivity", "onErrored - Problem in registering for GCM");
	// }
	//
	// private void initAndShowLogoAnim() {
	// mContainerLl = (LinearLayout) findViewById(R.id.container_ll);
	// mLogoIv = (ImageView) findViewById(R.id.logo_iv);
	// mLogoIv.setVisibility(View.INVISIBLE);
	// Animation logoAnim = AnimationUtils.loadAnimation(LoginActivity.this,
	// R.anim.splash_logo_set);
	// logoAnim.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// handler.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// mLogoIv.setVisibility(View.VISIBLE);
	// }
	// }, 450);
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// Log.d("LoginActivity", "onAnimationRepeat - animation=" + animation);
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// mContainerLl.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this,
	// R.anim.fade_in));
	// mContainerLl.setVisibility(View.VISIBLE);
	// }
	// });
	// mLogoIv.startAnimation(logoAnim);
	// }
	//
	// // Get screen size to send on Login service
	// private void getScreenSize() {
	// DisplayMetrics displaymetrics = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	// int dens = displaymetrics.densityDpi;
	// mDeviceHeight = displaymetrics.heightPixels;
	// mDeviceWidth = displaymetrics.widthPixels;
	//
	// Log.v("Height - Width", "Height :" + mDeviceHeight + "      Width : " +
	// mDeviceWidth + "Density : " + dens);
	//
	// if (dens <= 120) {
	// mDeviceDensity = "Small";
	// } else if (dens > 120 && dens <= 160) {
	// mDeviceDensity = "Normal";
	// } else {
	// mDeviceDensity = "Large";
	// }
	// }
	//
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.loginpage_ll:
	// InputMethodManager imm = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// imm.hideSoftInputFromWindow(mLoginpageLl.getWindowToken(), 0);
	// break;
	//
	// case R.id.login_btn:
	// // Google speech Recognization
	// // Intent intent = new Intent(
	// // RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	// //
	// // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
	// //
	// // try {
	// // startActivityForResult(intent, 3);
	// // mUsernameFl.getEditText().setText("");
	// // } catch (ActivityNotFoundException a) {
	// // Toast t = Toast.makeText(getApplicationContext(),
	// // "Opps! Your device doesn't support Speech to Text",
	// // Toast.LENGTH_SHORT);
	// // t.show();
	// // }
	//
	//
	// username = mUsernameFl.getEditText().getText().toString().trim();
	// password = mPasswordFl.getEditText().getText().toString().trim();
	// if (isLoginFieldValidated(username, password)) {
	// loginServerRequest(username, password);
	// }
	// break;
	//
	// case R.id.login_with_doximity_btn:
	// break;
	//
	// case R.id.forgot_password_tv:
	// Intent reserPasswordIntent = new Intent(this,
	// ForgotPasswordActivity.class);
	// startActivity(reserPasswordIntent);
	// break;
	//
	// case R.id.signup_tv:
	// Intent signupIntent = new Intent(this, RegistrationActivity.class);
	// signupIntent.putExtra("mGCMRegId", mGCMRegId);
	// startActivity(signupIntent);
	// break;
	//
	// default:
	// break;
	// }
	// }
	//
	// /**
	// * Description Validates the input fields
	// *
	// * @param emailAddress
	// * @param password
	// * @return true if all fields are proper
	// */
	// private boolean isLoginFieldValidated(String username, String password) {
	// boolean isValidate = true;
	// if (TextUtils.isEmpty(username)) {
	// isValidate = false;
	// } else if (TextUtils.isEmpty(password)) {
	// isValidate = false;
	// }
	// return isValidate;
	// }
	//
	// // Get latitude and logitude of the device to send in Login service
	// private void getLocation() {
	// final LocationManager manager = (LocationManager)
	// getSystemService(Context.LOCATION_SERVICE);
	// if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	// GpsDialog gpsDialog = new GpsDialog(this);
	// gpsDialog.show();
	// } else {
	// Location location =
	// manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	// if (location == null) {
	// Log.d("signal", "week");
	// location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	// }
	//
	// if (location != null) {
	// mCurLat = (double) (location.getLatitude());
	// mCurLog = (double) (location.getLongitude());
	// }
	//
	// String strLat = String.valueOf(mCurLat);
	// String strLon = String.valueOf(mCurLog);
	// Log.e("LoginActivity", "strLat=" + strLat + ", strLon=" + strLon);
	// }
	// }
	//
	// // Get IP address of network to send in Login service
	// private int getIpAddress() {
	// WifiManager myWifiManager = (WifiManager)
	// getSystemService(Context.WIFI_SERVICE);
	// WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
	// mIpAddress = myWifiInfo.getIpAddress();
	// Log.v("mIpAddress", "" + mIpAddress);
	// return mIpAddress;
	// }
	//
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// KeyboardUtil.hideSoftKeyboard(mPasswordFl.getEditText());
	//
	// switch (requestCode) {
	// // Country code selection for"Edit Mobile" dialog
	// case RegistrationActivity.REQ_CODE_COUNTRY_DIALOG:
	// if (resultCode == RESULT_OK) {
	// /*
	// * mCountryCodeEt.getEditText().setText(data.getStringExtra(
	// * "countryCode")); mCountryCodeEt.getEditText().clearFocus();
	// */
	// mobileEditDialog = new MobileEditDialog(this, editMobileNumber,
	// data.getStringExtra("countryCode"),
	// new NumberUpdateListner() {
	//
	// @Override
	// public void onNumberUpdate(String number) {
	// Log.d("LoginActivity", "onNumberUpdate - number=" + number);
	// editMobileNumber = number;
	// }
	// });
	// mobileEditDialog.show();
	// }
	// break;
	// case 3:
	//
	//
	// ArrayList<String> text = data
	// .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	//
	// mUsernameFl.getEditText().setText(text.get(0));
	//
	// break;
	//
	// }
	// }
	//
	// /**
	// * Description Gets json data and send it to the server.
	// */
	//
	// private void loginServerRequest(String emailAddress, String password) {
	//
	// String jsonData = mHttpRequest.getSignInJSON(emailAddress,
	// Utility.md5(password), RegistrationActivity.DEVICE_TYPE_ID,
	// RegistrationActivity.SOURCE_ID, String.valueOf(mIpAddress), mGCMRegId,
	// String.valueOf(mCurLat), String.valueOf(mCurLog),
	// RegistrationActivity.USER_TYPE_ID);
	//
	// HttpConnector httpConnector = new HttpConnector(this, null,
	// DialogUtil.createProgressDialog(this, 0));
	//
	// httpConnector.setHttpResponseListener(this);
	// httpConnector.executeAsync("SignIn/", HTTPRequest.REQ_CODE_LOGIN, "post",
	// false, jsonData, null, HTTPRequest.URLTYPE_SERVICE);
	// }
	//
	// @Override
	// public void onResponse(int requestCode, int statusCode, String response)
	// {
	// switch (requestCode) {
	// case HTTPRequest.REQ_CODE_LOGIN:
	// SignInParser signInParcer = new SignInParser();
	// // if (!signInParcer.parse(response)) {
	// // if (false) {
	// // DialogUtil.showOkDialog(LoginActivity.this,
	// signInParcer.getMessage());
	// // } else {
	// // SignIn signIn = signInParcer.getSignIn();
	// // HTTPRequest.setLoginSessionKey(signIn.LoginSessionKey);
	// // signIn.persist(this);
	//
	// Intent intent = new Intent(this, HomePageActivity.class);
	// intent.putExtra("caller", "LoginActivity");
	// startActivity(intent);
	// this.finish();
	// // }
	// break;
	//
	// case HTTPRequest.REQ_CODE_LOGIN_WITH_DOXIMITY:
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	//
	// @Override
	// public void onCancel(boolean canceled) {
	//
	// }

}
