package com.vinfotech.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.facebookslider.FacebookSlideView;
import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.main.HomePageActivity;
import com.vinfotech.main.LoginActivity;
import com.vinfotech.model.DashBoard;
import com.vinfotech.model.FacebookSliderViews;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.fileutil.ImageLoader;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.utility.KeyboardUtil;
import com.vinfotech.utility.Utility;

public class FacebookSliderActivity extends FragmentActivity implements
		OnClickListener, HttpResponseListener {

	// Sliding parameters
	static boolean menuOut = false;
	FacebookSlideView scrollView;
	public static View menu;
	private View dynamicPage;
	protected static LinearLayout mLayout_Quest;
	protected static LinearLayout mLayout_Home;

	private ImageView mBtnFacebook;
	private ImageView mBtnGooglePlus;
	private ImageView mBtnNativeSignup;
	public static ImageView mUserIv;
	RelativeLayout mHeaderProfileRl;
	private View mDrawerLoginHeaderView;
	private View mLoginBtn;
	public static TextView mPointsEarnedTv, mReputationPointsTv,
			mReputationPointsLastTv, mEstEarningTextTv,
			mReputationPointsTextTv, mPointsEarnedTextTv;
	private HTTPRequest mHttpRequest;
	private SignIn mSignIn;
	private LinearLayout mLogoutLl;
	public static TextView mNotificationTv;
	private HomePageActivity homePageActivity;
	private DashBoard dashBoard;
	private static ImageLoader imageLoader;
	private Activity context;
	public static TextView mUserNameTv;
	public static TextView mDesignationTv;
	public static TextView mCompanyNameTv;
	public static TextView mContactNameTv;
	private View mLogoutView;
	private Activity mActivityUsed;
	private ClickListenerForScrolling clickListenerForScrolling;


	@SuppressLint("NewApi")
	public FacebookSliderViews setLayouts(final Context context, int view,
			int title) {
		this.context = (Activity) context;
		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		mHttpRequest = new HTTPRequest();
		mSignIn = new SignIn(this);
		homePageActivity = new HomePageActivity();
		imageLoader = new ImageLoader(this);
		/*
		 * if(new SkyLinePreferences(this).getLoginSessionKey()!=null)
		 * getDashboardCount();
		 */
		

		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (FacebookSlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);

		menu = inflater.inflate(R.layout.menu_drawer_item_list, null);
		HeaderLayout headerLayout = new HeaderLayout(this);
		mHeaderProfileRl = (RelativeLayout) menu
				.findViewById(R.id.headerProfile_rl);

		mDrawerLoginHeaderView = inflater.inflate(R.layout.drawer_subheader,
				null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		mDrawerLoginHeaderView.setLayoutParams(lp);

		mHeaderProfileRl.addView(mDrawerLoginHeaderView);

		dynamicPage = inflater.inflate(view, null);
		setContentView(scrollView);
		initializedItem();
		final View[] children = new View[] { menu, dynamicPage };
		// Button to leave the width at right side for Menu slider
		Button btnTry = (Button) dynamicPage.findViewById(R.id.btnTry);
		int scrollToViewIdx = 1; // Scroll to app (view[1]) when layout
		// finished.
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnTry));

		/*
		 * if (context instanceof HomePageActivity)
		 * mLayout_Home.setSelected(true); if (context instanceof
		 * HomePageActivity) mLayout_Home.setSelected(true); if (context
		 * instanceof HomePageActivity) mLayout_Home.setSelected(true); if
		 * (context instanceof HomePageActivity) mLayout_Home.setSelected(true);
		 */

		return new FacebookSliderViews(menu, dynamicPage, headerLayout,
				scrollView);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	public void setobject(SignIn signIn, Context context) {
		mSignIn = signIn;
		if (mSignIn != null) {
			setElementonField(mSignIn, context);
		}
	}

	/**
	 * Get Notification Count
	 */
	/*
	 * private void getNotificationCount() {
	 * Utility.showLog("~~~~~~~~~~~~~Notification~~~~~~~~~~~~~~"); String
	 * jsonData = mHttpRequest.getNotificationCountJson( new
	 * SkyLinePreferences(this).getLoginSessionKey(), new
	 * SkyLinePreferences(this).getCurrentZipCode());
	 * 
	 * HttpConnector httpConnector = new HttpConnector(this, null,
	 * DialogUtil.createProgressDialog(this, 0));
	 * httpConnector.setHttpResponseListener(this);
	 * httpConnector.executeAsync("property/notificationcount/",
	 * HTTPRequest.REQ_CODE_GET_NOTIFICATIONS_COUNT, "post", false, jsonData,
	 * null, HTTPRequest.URLTYPE_SERVICE); }
	 */

	protected void setElementonField(SignIn signIn, Context context) {
		context = mActivityUsed.getApplicationContext();

	}

	public void initializedItem() {

		/*
		 * btn_facebook = (ImageView) menu.findViewById(R.id.imageViewfb);
		 * btn_googlePlus = (ImageView) menu.findViewById(R.id.imageViewgplus);
		 * btn_googlePlus.setOnClickListener(this);
		 * btn_facebook.setOnClickListener(this);
		 */
		// Menu Controls Initialization
		mLayout_Quest = (LinearLayout) menu.findViewById(R.id.layout_quest);
		mLayout_Home = (LinearLayout) menu.findViewById(R.id.layout_home);
		
	

		mLoginBtn = (Button) mDrawerLoginHeaderView
				.findViewById(R.id.btn_login);
		mBtnFacebook = (ImageView) mDrawerLoginHeaderView
				.findViewById(R.id.imageViewfb);
		mBtnGooglePlus = (ImageView) mDrawerLoginHeaderView
				.findViewById(R.id.imageViewgplus);
		mBtnNativeSignup = (ImageView) mDrawerLoginHeaderView
				.findViewById(R.id.imageView1inbox);
		// mLogoutLl.setVisibility(View.INVISIBLE);
		// mLogoutView.setVisibility(View.GONE);
		mLoginBtn.setOnClickListener(this);
		mBtnFacebook.setOnClickListener(this);
		mBtnGooglePlus.setOnClickListener(this);
		mBtnNativeSignup.setOnClickListener(this);
		mLayout_Quest.setOnClickListener(this);
		mLayout_Home.setOnClickListener(this);
	}

	/** Classes for Handling the sliding of Menu */

	/*
	 * Remembers the width of the 'slide' button, so that the 'slide' button
	 * remains in view, even when the menu is showing.
	 */
	static class SizeCallbackForMenu implements
			com.android.facebookslider.FacebookSlideView.SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}

	/* Helper for a HSV that should be scrolled by a menu View's width. */
	public class ClickListenerForScrolling implements OnClickListener {
		FacebookSlideView scrollView;
		View menu;
		EditText editText;
		boolean isMenuDrawer;
		Activity activity;

		/**
		 * Menu must NOT be out/shown to start with.
		 */
		// boolean menuOut = false;
		public ClickListenerForScrolling(FacebookSlideView scrollView,
				View menu, boolean isMenuDrawer, Activity activity) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
			this.editText = null;
			this.isMenuDrawer = isMenuDrawer;
			this.activity = activity;

		}

		@Override
		public void onClick(View v) {

			final int leftSpace = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 65, v.getContext()
							.getResources().getDisplayMetrics());
			final int menuWidth = menu.getMeasuredWidth();
			// Log.v("ClickListenerForScrolling", "onClick - menuWidth=" +
			// menuWidth + ", leftSpace=" + leftSpace + ", menuOut=" + menuOut
			// + ", isChat=" + isChat);

			// Ensure menu is visible
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {

				if (editText != null)
					editText.requestFocus();
				final int right;
				final int left;

				right = 0;
				left = 20;

				scrollView.smoothScrollTo(left, right);
				//
			} else {
				// Scroll to menuWidth so menu isn't on screen, Close Menu
				// Drawer
				int left = menuWidth;

				scrollView.smoothScrollTo(left, 0);
			}
			if (isMenuDrawer)
				menuOut = true;
			else
				checkAndCloseDrawer(menuWidth);
		}

		public void closeDrawer() {
			try {
				if (menu != null) {
					int left = menu.getMeasuredWidth();
					scrollView.smoothScrollTo(left, 0);
					scrollView.getWrapperView().setVisibility(View.GONE);
					if (isMenuDrawer) {
						menuOut = true;
					} else {
						menuOut = !menuOut;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void checkAndCloseDrawer(final int menuWidth) {
			menuOut = !menuOut;
			final View wrapperView = scrollView.getWrapperView();

			if (null != scrollView.getWrapperView()) {
				wrapperView.setVisibility(menuOut ? View.VISIBLE : View.GONE);
				wrapperView.setOnClickListener(!menuOut ? null
						: new OnClickListener() {
							@Override
							public void onClick(View v) {
								// Log.v("ClickListenerForScrolling",
								// "onClick - menuWidth=" + menuWidth +
								// ", leftSpace=" + leftSpace + ", menuOut=" +
								// menuOut
								// + ", isChat=" + isChat);
								// KeyboardUtil.hideSoftKeyboard(menu);
								scrollView.postDelayed(new Runnable() {
									@Override
									public void run() {
										int left = menuWidth;

										scrollView.smoothScrollTo(left, 0);
										wrapperView.setVisibility(View.GONE);
										if (isMenuDrawer) {
											menuOut = true;
										} else {
											menuOut = !menuOut;
										}
									}
								}, 150);
							}
						});
			}
		}
	}

	@Override
	public void onClick(View v) {
		// if(mLayout_Share==null ||mLayout_Share==null || mLayout_Share==null
		// ||mLayout_Share==null ||)
		mLayout_Home.setSelected(false);
		mLayout_Quest.setSelected(false);
		// mLogoutLl.setSelected(false);
		Intent loginIntent;

	}

	private void CloseDrawer() {
		if (menu != null) {
			clickListenerForScrolling = new ClickListenerForScrolling(
					scrollView, menu, false, this);
			clickListenerForScrolling.closeDrawer();
		}

	}

	HttpResponseListener httpResponseListener = new HttpResponseListener() {

		@Override
		public void onResponse(int reqCode, int statusCode, String response) {

		}

		@Override
		public void onCancel(boolean canceled) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onResponse(int reqCode, int statusCode, String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel(boolean canceled) {
		// TODO Auto-generated method stub

	}
}