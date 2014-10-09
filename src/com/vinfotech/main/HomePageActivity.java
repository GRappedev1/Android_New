package com.vinfotech.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.facebookslider.FacebookSlideView;
import com.android.facebookslider.FacebookSlideView.SizeCallback;
import com.vinfotech.demoapp.R;
import com.vinfotech.dialogs.MobileEditDialog;
import com.vinfotech.dialogs.MobileEditDialog.NumberUpdateListner;
import com.vinfotech.dialogs.MobileVerficationDialog;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.fileutil.ImageLoader;

public class HomePageActivity extends Activity implements OnClickListener {

	// HomePage Widgets
	private ImageView mUserIv;
	private TextView mUserNameTv;

	// Menu Widgets
	private TextView mStatusTv, mDepartmentTv;
	private LinearLayout mDashboardLl, mAddPatientLl, mTeamsLl, mTasksLl, mBadgesLl, mAccountLl, mEditProfileLl, mSwitchAcLl, mLogoutLl;

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

	private Intent mIntent;

	private  final int REQ_MENU_LOGOUT = 10;
	private  final int REQ_DEPARTMENT = 11;
	private  final int REQ_STATUS = 12;

	private String[] statusArr = new String[] { "On Call", "Available", "Unavailable" };
	private String[] departmentArr = new String[] { "Surgery", "Physiotherapy", "ICU", "NICU" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// define image size
		mSignIn = new SignIn(this);
		mImageLoader = new ImageLoader(this);
		mImageLoader.setImageSize(1000);

		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (FacebookSlideView) inflater.inflate(R.layout.screen_scroll_with_list_menu, null);

		menu = inflater.inflate(R.layout.menu_drawer_item_list, null);
		homePage = inflater.inflate(R.layout.home_page_activity, null);

		setContentView(scrollView);

		controlInitialization();

		mCaller = getIntent().getStringExtra("caller");

		if (mCaller != null && mCaller.equalsIgnoreCase("RegistrationActivity")) {
			if (HTTPRequest.mbitmap != null) {
				mUserIv.setImageBitmap(ImageLoader.getCircledBitmap(HTTPRequest.mbitmap));
			} else {
				mUserIv.setBackgroundResource(R.drawable.default_picture);
			}
		} else {
			mImageLoader.removeImage(mSignIn.getProfileImage(2));
			mImageLoader.displayImage(mSignIn.getProfileImage(2), mUserIv, 2);
		}
		
		
		final View[] children = new View[] { menu, homePage };
		// Button to leave the width at right side for Menu slider
		Button btnTry = (Button) homePage.findViewById(R.id.btnTry);
		int scrollToViewIdx = 1; // Scroll to app (view[1]) when layout
									// finished.
		scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnTry));

		

		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderIITI(R.drawable.icon_menu, R.drawable.icon_bell, R.string.Welcome, R.drawable.icon_chat);
		headerLayout.setListener(new ClickListenerForScrolling(scrollView, menu), null, null);

		checkDialogStatus();
	}

	// Checks the status of mobile number verification and show the dialog
	// accordingly
private void checkDialogStatus() {
		if (!mSignIn.isEmptyPreferance(this)) {
			Log.v("checkStateScreen", "Empty shared Preference");
			// 1 = not verified, 5 ==verified
			if (Integer.parseInt(mSignIn.MobileNumberStatus) != 5) {
				MobileVerficationDialog verifyDialog = new MobileVerficationDialog(this, mSignIn.MobileNumber, mSignIn.CountryCode,
						new NumberUpdateListner() {

							@Override
							public void onNumberUpdate(String number) {
								Log.d("LoginActivity", "MobileVerficationDialog.onNumberUpdate - number=" + number);
								mEditMobileNumber = number;
							}
						});
				verifyDialog.setCanceledOnTouchOutside(false);
				verifyDialog.show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
		
			switch (requestCode) {
			
				// Country code selection for"Edit Mobile" dialog
				case RegistrationActivity.REQ_CODE_COUNTRY_DIALOG:
					
					mMobileEditDialog = new MobileEditDialog(this, mEditMobileNumber, data.getStringExtra("countryCode"),
							new NumberUpdateListner() {
		
								@Override
								public void onNumberUpdate(String number) {
									Log.d("LoginActivity", "onNumberUpdate - number=" + number);
									mEditMobileNumber = number;
								}
							});
					mMobileEditDialog.setCanceledOnTouchOutside(false);
					mMobileEditDialog.show();
				
					break;
	
			case REQ_STATUS:
				mStatusTv.setText(data.getStringExtra("status"));
				break;
	
			case REQ_DEPARTMENT:
				mDepartmentTv.setText(data.getStringExtra("status"));
				break;
	
			case REQ_MENU_LOGOUT:
				/*
				 * if (mSignIn.isEmptyPreferance(this)) { this.finish(); }
				 */
				break;
		}
	} }

	private void controlInitialization() {

		// HomePage Controls Initialization
		mUserIv = (ImageView) homePage.findViewById(R.id.user_iv);
		mUserNameTv = (TextView) homePage.findViewById(R.id.userWecome_tv);

		mUserNameTv.setText(Html.fromHtml(getString(R.string.welcome_user_first) + "<b> " + mSignIn.FirstName + " " + mSignIn.LastName
				+ "</b> <br>" + getString(R.string.welcome_user_second) + "<br>" + getString(R.string.welcome_user_third)));

		// Menu Controls Initialization
		mDashboardLl = (LinearLayout) menu.findViewById(R.id.dahsboard_ll);
		mAddPatientLl = (LinearLayout) menu.findViewById(R.id.addPatient_ll);
		mTeamsLl = (LinearLayout) menu.findViewById(R.id.teams_ll);
		mTasksLl = (LinearLayout) menu.findViewById(R.id.tasks_ll);
		mBadgesLl = (LinearLayout) menu.findViewById(R.id.badges_ll);
		mAccountLl = (LinearLayout) menu.findViewById(R.id.accountSetting_ll);
		mEditProfileLl = (LinearLayout) menu.findViewById(R.id.editProfile_ll);
		mSwitchAcLl = (LinearLayout) menu.findViewById(R.id.SwitchAc_ll);
		mLogoutLl = (LinearLayout) menu.findViewById(R.id.logout_ll);

		mStatusTv = (TextView) menu.findViewById(R.id.statusTv);
		mDepartmentTv = (TextView) menu.findViewById(R.id.departmentTv);

		mStatusTv.setOnClickListener(this);
		mDepartmentTv.setOnClickListener(this);

		mDashboardLl.setOnClickListener(this);
		mAddPatientLl.setOnClickListener(this);
		mTeamsLl.setOnClickListener(this);
		mTasksLl.setOnClickListener(this);
		mBadgesLl.setOnClickListener(this);
		mAccountLl.setOnClickListener(this);
		mEditProfileLl.setOnClickListener(this);
		mSwitchAcLl.setOnClickListener(this);
		mLogoutLl.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		/*case R.id.editProfile_ll:
			startActivity(new Intent(v.getContext(), DashboardActivity.class));
			break;

		case R.id.dahsboard_ll:
			startActivity(new Intent(v.getContext(), DashboardActivity.class));
			break;*/

		case R.id.logout_ll:
			Toast.makeText(this, "Logout Pressed", Toast.LENGTH_SHORT).show();
			// signOutRequest();
			break;

		/*case R.id.statusTv:

			mIntent = new Intent(this, StatusActivity.class);
			mIntent.putExtra("status", statusArr);
			startActivityForResult(mIntent, REQ_STATUS);
			break;

		case R.id.departmentTv:
			mIntent = new Intent(this, StatusActivity.class);
			mIntent.putExtra("status", departmentArr);

			startActivityForResult(mIntent, REQ_DEPARTMENT);
			break;
*/
		default:
			break;
		}

	}

	
	
	/** Classes for Handling the sliding of Menu */

	/*
	 * Remembers the width of the 'slide' button, so that the 'slide' button
	 * remains in view, even when the menu is showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
			System.out.println("btnWidth=" + btnWidth);
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
	static class ClickListenerForScrolling implements OnClickListener {
		FacebookSlideView scrollView;
		View menu;

		/**
		 * Menu must NOT be out/shown to start with.
		 */
		// boolean menuOut = false;

		public ClickListenerForScrolling(FacebookSlideView scrollView, View menu) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
		}

		@Override
		public void onClick(View v) {
			Context context = menu.getContext();

			int menuWidth = menu.getMeasuredWidth();

			// Ensure menu is visible
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				// Scroll to 0 to reveal menu
				Log.d("===slide==", "Scroll to right");
				Log.d("===clicked==", "clicked");
				int left = 20;
				scrollView.smoothScrollTo(left, 0);
			} else {
				// Scroll to menuWidth so menu isn't on screen.
				Log.d("===slide==", "Scroll to left");
				Log.d("===clicked==", "clicked");
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}
	}

}
