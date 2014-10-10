package com.vinfotech.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.vinfotech.adapter.HomePageAdapter;
import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.model.DashBoard;
import com.vinfotech.module.Notification.NotificationScreen;
import com.vinfotech.module.Poststatus.PostStatusScreen;

public class HomePageActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mListview;
	private ArrayList<DashBoard> dashBoards;
	private HomePageAdapter homePageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home_page);
		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(0, R.string.HomeScreen, 0);
		registerElements();
	}

	private void registerElements() {
		mListview = (ListView) findViewById(R.id.homepage_list);
		homePageAdapter = new HomePageAdapter(this);
		homePageAdapter.setList(setDashBoardList());
		mListview.setAdapter(homePageAdapter);
		mListview.setOnItemClickListener(this);
	}

	/**
	 * Create Demo List
	 * 
	 * @return
	 */
	private ArrayList<DashBoard> setDashBoardList() {
		dashBoards = new ArrayList<DashBoard>();
		dashBoards.add(new DashBoard("Splash Screen", null, 1));
		dashBoards.add(new DashBoard("Login Screen", null, 2));
		dashBoards.add(new DashBoard("SignUp Screen", null, 3));
		dashBoards.add(new DashBoard("Profile Screen", null, 4));
		dashBoards.add(new DashBoard("MapScreen", null, 5));
		dashBoards.add(new DashBoard("Notification", null, 6));
		dashBoards.add(new DashBoard("PostStatus", null, 7));
		dashBoards.add(new DashBoard("C", null, 1));
		dashBoards.add(new DashBoard("D", null, 1));
		dashBoards.add(new DashBoard("E", null, 1));
		return dashBoards;
	}

	// Checks the status of mobile number verification and show the dialog
	// accordingly

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {

		case 1:

			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DashBoard dashBoard = (DashBoard) mListview.getItemAtPosition(position);
		Intent intent;
		switch (dashBoard.getmIds()) {
		case 1:

			break;
		case 2:
			intent = new Intent(HomePageActivity.this, LoginActivity.class);
			startActivity(intent);
			break;

		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:
			intent = new Intent(HomePageActivity.this, NotificationScreen.class);
			startActivity(intent);
			break;
		case 7:
			intent = new Intent(HomePageActivity.this, PostStatusScreen.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}
}
