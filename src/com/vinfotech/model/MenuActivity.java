package com.vinfotech.model;

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.facebookslider.ClickListenerForScrolling;
import com.android.facebookslider.FacebookSlideView;
import com.vinfotech.demoapp.R;
import com.vinfotech.main.LoginActivity;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.utility.DialogUtil;

public class MenuActivity extends LinearLayout implements OnClickListener, HttpResponseListener {

	public static TextView mStatusTv, mDepartmentTv, mMenuUserNameTv, mDesignation;
	public static ImageView mUserImageIv;
	public LinearLayout mDashboardLl, mAddPatientLl, mTeamsLl, mTasksLl, mBadgesLl, mAccountLl, mProfileLl, mSwitchAcLl, mLogoutLl, mFeedbackLl;
	public RelativeLayout mHeaderProfile_rl;

	public static final int REQ_DEPARTMENT = 11;
	public static final int REQ_STATUS = 12;

	HTTPRequest mHttpRequest = new HTTPRequest();
	Activity context;
	FacebookSlideView scrollView;
	View menuView;

	boolean menuOut = false;

	Intent intent = null;

	public MenuActivity(final Context context, final FacebookSlideView scrollView, View menu, boolean menuOut) {
		super(context);
		this.context = (Activity) context;
		this.scrollView = scrollView;
		this.menuView = menu;
		this.menuOut = menuOut;

		((Activity) getContext()).getLayoutInflater().inflate(R.layout.menu_drawer_item_list, this);

		controlInitialization();

//		mAddPatientLl.setVisibility(GlobalVariables.isPermitted(Permission.AddPatient) ? View.VISIBLE : View.GONE);
//		
//		if (context instanceof TeamActivity) {
//			mTeamsLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mTeamsLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else
//			mTeamsLl.setOnClickListener(this);
//
//		if (context instanceof ProfileActivity) {
//			mProfileLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, !((ProfileActivity) context).menuOut, true));
//			mHeaderProfile_rl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mProfileLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else
//			mProfileLl.setOnClickListener(this);
//
//		if (context instanceof DashboardActivity) {
//			mDashboardLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mDashboardLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else
//			mDashboardLl.setOnClickListener(this);
//
//		if (context instanceof TasksActivity) {
//			mTasksLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mTasksLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else
//			mTasksLl.setOnClickListener(this);
//
//		if (context instanceof AddPatientActivity) {
//			mAddPatientLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mAddPatientLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else 
//			mAddPatientLl.setOnClickListener(this);
//		
//		if (context instanceof SettingsActivity) {
//			mAccountLl.setOnClickListener(new ClickListenerForScrolling(scrollView, this, true, true));
//			mAccountLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_corner_dashb));
//		} else 
//			mAccountLl.setOnClickListener(this);

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	void controlInitialization() {

		// Menu Controls Initialization
//		mDashboardLl = (LinearLayout) findViewById(R.id.dahsboard_ll);
//		mAddPatientLl = (LinearLayout) findViewById(R.id.addPatient_ll);
//		mTeamsLl = (LinearLayout) findViewById(R.id.teams_ll);
//		mTasksLl = (LinearLayout) findViewById(R.id.tasks_ll);
//		mBadgesLl = (LinearLayout) findViewById(R.id.badges_ll);
//		mAccountLl = (LinearLayout) findViewById(R.id.accountSetting_ll);
//		mProfileLl = (LinearLayout) findViewById(R.id.editProfile_ll);
//		mSwitchAcLl = (LinearLayout) findViewById(R.id.SwitchAc_ll);
//		mLogoutLl = (LinearLayout) findViewById(R.id.logout_ll);
//		mFeedbackLl = (LinearLayout) findViewById(R.id.feedback_ll);
//		
//		mHeaderProfile_rl = (RelativeLayout) findViewById(R.id.headerProfile_rl);
//		mUserImageIv = (ImageView) findViewById(R.id.userImageMenu_iv);
//
//		mMenuUserNameTv = (TextView) findViewById(R.id.UserNameMenu_tv);
//		mStatusTv = (TextView) findViewById(R.id.statusTv);
//		mDepartmentTv = (TextView) findViewById(R.id.departmentTv);
//		mDesignation = (TextView) findViewById(R.id.SpecialityMenu_tv);
//		
//
//		// Click Listeners
//		mStatusTv.setOnClickListener(this);
//		mDepartmentTv.setOnClickListener(this);

		/*
		 * mDashboardLl.setOnClickListener(this);
		 * mAddPatientLl.setOnClickListener(this);
		 */
		// mTeamsLl.setOnClickListener(this);
		// mTasksLl.setOnClickListener(this);
		
		//mAccountLl.setOnClickListener(this);
		// mEditProfileLl.setOnClickListener(this);
//		mSwitchAcLl.setOnClickListener(this);
//		mLogoutLl.setOnClickListener(this);
//		mFeedbackLl.setOnClickListener(this);
//		mHeaderProfile_rl.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

//		Intent intent = null;
//		if(DashboardActivity.dashboardInstance!=null && !(context instanceof DashboardActivity)){
//			DashboardActivity.dashboardInstance.finish();
//			DashboardActivity.dashboardInstance = null;
//		}
//		
//		switch (v.getId()) {
//
//			case R.id.headerProfile_rl:
//	
//				intent = new Intent(context, ProfileActivity.class);
//				context.startActivity(intent);
//				((Activity) context).finish();
//				break;
//	
//			case R.id.dahsboard_ll:
//				// Toast.makeText(context, "DashBoard is clicked",
//				// Toast.LENGTH_SHORT).show();
//				intent = new Intent(context, DashboardActivity.class);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.addPatient_ll:
//				// Toast.makeText(context, "DashBoard is clicked",
//				// Toast.LENGTH_SHORT).show();
//				intent = new Intent(context, AddPatientActivity.class);
//				intent.putExtra("isMenuActivity", true);
//				intent.putExtra("isEdit", false);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.teams_ll:
//				intent = new Intent(context, TeamActivity.class);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.tasks_ll:
//				intent = new Intent(context, TasksActivity.class);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.accountSetting_ll:
//				intent = new Intent(context, SettingsActivity.class);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.editProfile_ll:
//				intent = new Intent(context, ProfileActivity.class);
//				context.startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.statusTv:
//				intent = new Intent(context, StatusActivity.class);
//				intent.putExtra("status", 0); // 0 = Status , 1 = Department
//				intent.putExtra("title", R.string.Status);
//				intent.putExtra("ActivityName", "MenuActivity");
//				context.startActivityForResult(intent, REQ_STATUS);
//				break;
//	
//			case R.id.departmentTv:
//				intent = new Intent(context, StatusActivity.class);
//				intent.putExtra("status", 1); // 0 = Status , 1 = Department
//				intent.putExtra("title", R.string.Department);
//				intent.putExtra("ActivityName", "MenuActivity");
//				context.startActivityForResult(intent, REQ_DEPARTMENT);
//				break;
//	
//			case R.id.SwitchAc_ll:
//				intent = new Intent(context, HospitalListActivity.class);
//				intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//				((Activity) context).startActivity(intent);
//				context.finish();
//				break;
//	
//			case R.id.logout_ll:
//				signOutRequest();
//				break;
//				
//			case R.id.feedback_ll:
//				 Helpshift.showConversation(context);
//				break;	
//				
//			default:
//				break;
//			}
	}

	
	@Override
	public void onResponse(int requestCode, int statusCode, String response) {
		switch (requestCode) {

		case HTTPRequest.REQ_CODE_LOGOUT:
			//Log.d("MenuActivity", "onResponse - Logout " + baseParser.parse("SignOut", response));

			
			break;

		}
	}

	@Override
	public void onCancel(boolean canceled) {

	}

}
