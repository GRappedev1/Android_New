package com.vinfotech.main;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.facebookslider.ClickListenerForScrolling;
import com.android.facebookslider.FacebookSlideView;
import com.android.facebookslider.SizeCallbackForMenu;
import com.google.android.gcm.GCMRegistrar;
import com.vinfotech.demoapp.R;
import com.vinfotech.dialogs.MobileEditDialog;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.main.GCMIntentService.GCMRegisterListener;
import com.vinfotech.model.DashBoard;
import com.vinfotech.model.FacebookSliderViews;
import com.vinfotech.model.GetAllData;
import com.vinfotech.model.MenuActivity;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.fileutil.ImageLoader;
import com.vinfotech.server.parser.BaseParser;
import com.vinfotech.server.parser.GetAllDataParser;
import com.vinfotech.server.parser.SignInParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.Utility;

public class HomePageActivity extends Activity implements OnClickListener,
		HttpResponseListener, GCMRegisterListener {

	// HomePage Widgets
	private ImageView mUserIv;
	private TextView mAverageRentText;
	View teamView, menuView, ChatDrawer;
	// Menu Widgets
	private TextView mStatusTv, mDepartmentTv;
	private LinearLayout mLayout_Quest, mLayout_Home, mLayout_Notification,
			mLayout_Setting, mLayout_Share;
	// Sliding parameters
	static boolean menuOut = false;
	FacebookSlideView scrollView;
	View menu;
	View homePage;

	private SignIn mSignIn;
	private ImageLoader mImageLoader;
	private MobileEditDialog mMobileEditDialog;

	private String mEditMobileNumber;
	private String mCaller;

	public static String mRegId = "123";
	private TextView welcomeTextView;
	private ImageView btn_facebook, btn_googlePlus, btn_inbox;
	private LinearLayout mLayout_averageRent;
	private Button mAverageRent;
	private EditText mSearchTxt;
	private Button mSearchBtn, mBtnLogin;
	private HTTPRequest mHttpRequest;
	private SignIn signIn;
	private Activity activity;
	public static String mZipCode, mAvgRent;
	public static double mCurLat = 0.0, mCurLog = 0.0;

	public static TextView mNotificationTv;
	private View mLoginBtn;
	public static TextView mPointsEarnedTv, mReputationPointsTv,
			mReputationPointsLastTv;
	private ImageLoader imageLoader;

	private static int z;
	HeaderLayout mHeaderLayout;
	ClickListenerForScrolling mChatClickListenerForScrolling;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		// define image size
		mSignIn = new SignIn(this);
		mImageLoader = new ImageLoader(this);
		mImageLoader.setImageSize(1000);
		mHttpRequest = new HTTPRequest();
		signIn = new SignIn(this);
		setLayout();
		mHeaderLayout = new HeaderLayout(this);
		mChatClickListenerForScrolling = new ClickListenerForScrolling(
				scrollView, ChatDrawer, menuOut, false, true);

		welcomeTextView = (TextView) findViewById(R.id.userWecome_tv);

		String reusableText = "<font color=\"#f54547\">Reusable </font><font color=\"#0aa0dd\">Component</font>";
		welcomeTextView.setText(Html.fromHtml(reusableText));
		getDataFromServer();
	}

	private void getDataFromServer() {

		HttpConnector httpConnector = new HttpConnector(this, null,
				DialogUtil.createProgressDialog(this, 0));

		httpConnector.setHttpResponseListener(this);
		httpConnector.executeAsync(HTTPRequest.GET_ALL_DATA_SERVER,
				HTTPRequest.REQ_CODE_GET_ALL_DATA, "post", false, null, null,
				HTTPRequest.URLTYPE_EXTERNAL);
	}

	@SuppressLint("NewApi")
	private void setLayout() {
		// TODO Auto-generated method stub
		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (FacebookSlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);

		menuView = new MenuActivity(this, scrollView, menuView, menuOut);
		teamView = inflater.inflate(R.layout.home_page_activity, null);

		setContentView(scrollView);
		ImageButton btnSlide = (ImageButton) teamView
				.findViewById(R.id.left_ib);

		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView,
				menuView, menuOut, false));

		final View[] children = new View[] { menuView, teamView };
		// final View[] children = new View[] { menuView, teamView};
		// Scroll to app (view[1]) when layout finished.
		int scrollToViewIdx = 1;

		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));

		// GlobalVariables.setDrawerData(TeamActivity.this);
	}

	@Override
	public void onRegistered(String gcmRegId) {
		Log.d("LoginActivity", "onRegistered - gcmRegId=" + gcmRegId);
		mRegId = gcmRegId;
	}

	@Override
	public void onErrored() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onClick(View v) {

		Intent loginIntent;
		mLayout_Setting.setSelected(false);
		mLayout_Notification.setSelected(false);
		mLayout_Home.setSelected(false);
		mLayout_Quest.setSelected(false);

	}

	@Override
	public void onResponse(int reqCode, int statusCode, String response) {
		switch (reqCode) {
		case HTTPRequest.REQ_CODE_GET_ALL_DATA:
			GetAllDataParser getDataparser = new GetAllDataParser();
			getDataparser.parse(response);
			if (getDataparser.getStaus()) {

				GetAllData getAllData = getDataparser.getAllData();

				Log.i("MIS", "" + getAllData.roles.size());
			}

			break;

		default:
			break;
		}
	}

	// private void setcurrentTrackZip() {
	// switch (z) {
	// case 0:
	// mZipCode = "90404";
	// z++;
	// break;
	// case 1:
	// mZipCode = "90067";
	// z++;
	// break;
	// case 2:
	// mZipCode = "90057";
	// z++;
	// break;
	// case 3:
	// mZipCode = "90808";
	// z++;
	// break;
	// case 4:
	// mZipCode = "22786";
	// z++;
	// break;
	// case 5:
	// mZipCode = "452001";
	// z++;
	// break;
	// default:
	// mZipCode = "60181";
	// z++;
	// break;
	// }
	// new SkyLinePreferences(this).setCurrentTrackZipCode(mZipCode);
	// }

	@Override
	public void onCancel(boolean canceled) {

	}

}
