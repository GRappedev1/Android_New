package com.vinfotech.main;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.facebookslider.ClickListenerForScrolling;
import com.android.facebookslider.FacebookSlideView;
import com.android.facebookslider.SizeCallbackForMenu;
import com.vinfotech.adapter.HomePageAdapter;
import com.vinfotech.adapter.HomeScreenAdapter;
import com.vinfotech.demoapp.R;
import com.vinfotech.dialogs.MobileEditDialog;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.main.GCMIntentService.GCMRegisterListener;
import com.vinfotech.model.GetAllData;
import com.vinfotech.model.MenuActivity;
import com.vinfotech.model.ProjectBean;
import com.vinfotech.model.SignIn;
import com.vinfotech.server.HTTPRequest;
import com.vinfotech.server.HttpConnector;
import com.vinfotech.server.HttpConnector.HttpResponseListener;
import com.vinfotech.server.fileutil.ImageLoader;
import com.vinfotech.server.parser.GetAllDataParser;
import com.vinfotech.utility.DialogUtil;
import com.vinfotech.widgets.listner.SelectedItemInterface;

/*
 * @author yogesht
 * 
 * This is main page of entire app having function sliding and get all data for show.
 */
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
	private ListView sideItemList;
	private HeaderLayout mHeaderLayout;
	private ClickListenerForScrolling mChatClickListenerForScrolling;
	private HomePageAdapter adapter;
	private ArrayList<ProjectBean> pCategoryList;
	private HomeScreenAdapter homeScreenAdapter;
	private ListView mainList;
	private RelativeLayout mainRelativeLayout;
	private Intent pDetailIntent;

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

	private SelectedItemInterface itemClickListner = new SelectedItemInterface() {

		@Override
		public void onCategorySelect(int categoryId) {
			// TODO Auto-generated method stub

			pCategoryList = new ArrayList<ProjectBean>();

			for (int i = 0; i < GetAllData.getInstance().getProjects().size(); i++) {

				for (int j = 0; j < GetAllData.getInstance().getProjects()
						.get(i).getpCategories().size(); j++) {

					if (GetAllData.getInstance().getProjects().get(i)
							.getpCategories().get(j).category_id == categoryId)
						pCategoryList.add(GetAllData.getInstance()
								.getProjects().get(i));
				}

			}
			if (pCategoryList.size() > 0) {
				mainList.setVisibility(View.VISIBLE);
				homeScreenAdapter = new HomeScreenAdapter(activity);
				mainList.setAdapter(homeScreenAdapter);
				homeScreenAdapter.setList(pCategoryList);
				mainRelativeLayout.setVisibility(View.GONE);

			} else {
				mainRelativeLayout.setVisibility(View.VISIBLE);
				mainList.setVisibility(View.INVISIBLE);

				Toast.makeText(getApplicationContext(),
						"Sorry no project found.", 1).show();
			}

		}
	};

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
		mainList = (ListView) teamView.findViewById(R.id.homepage_list);
		mainRelativeLayout = (RelativeLayout) teamView
				.findViewById(R.id.main_layout);

		setContentView(scrollView);
		ImageButton btnSlide = (ImageButton) teamView
				.findViewById(R.id.left_ib);
		sideItemList = (ListView) menuView
				.findViewById(R.id.homepage_side_list);

		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView,
				menuView, false, false));

		final View[] children = new View[] { menuView, teamView };
		int scrollToViewIdx = 1;
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));

		adapter = new HomePageAdapter(this);
		sideItemList.setAdapter(adapter);

		EditText etSeachCategories = (EditText) menuView
				.findViewById(R.id.search_categories_edittext);
		etSeachCategories.addTextChangedListener(addTextListner);
		// menuOut = false;
		sideItemList.setOnItemClickListener(new ClickListenerForScrolling(
				scrollView, menuView, true, true, itemClickListner));

		mainList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (pCategoryList.size() > 0)

					pDetailIntent = new Intent(HomePageActivity.this,
							ProjectDetailActivity.class);
				pDetailIntent.putExtra("object", pCategoryList.get(position));

				startActivity(pDetailIntent);

			}

		});

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

				adapter.setList(GetAllData.getInstance().getCategories());

				GetAllData.getInstance().getProjects();

			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {

	}

	private Handler reloadHandler = new Handler();
	private final TextWatcher addTextListner = new TextWatcher() {

		@Override
		public void onTextChanged(final CharSequence search, int arg1,
				int arg2, int arg3) {
			reloadHandler.removeCallbacksAndMessages(null);
			reloadHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// addFriendAdapter.setFilter(search.toString().trim());
					adapter.getFilter().filter(
							search.toString().trim().toLowerCase());
				}
			}, 400);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}
	};
	private final TextWatcher searchTextListner = new TextWatcher() {

		@Override
		public void onTextChanged(final CharSequence search, int arg1,
				int arg2, int arg3) {
			reloadHandler.removeCallbacksAndMessages(null);
			reloadHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// addFriendAdapter.setFilter(search.toString().trim());
					adapter.getFilter().filter(
							search.toString().trim().toLowerCase());
				}
			}, 400);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}
	};
}
