package com.vinfotech.module.Notification;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.utility.Utility;

public class NotificationScreen extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mListview;
	private ArrayList<Notification> notificArrayList;
	private NotificationAdapter homePageAdapter;

	// https://lh5.googleusercontent.com/-1mua82b93S0/AAAAAAAAAAI/AAAAAAAAACs/VPX6N-Mxv1g/photo.jpg
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notification_screen);
		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(R.drawable.icon_back, R.string.Notification,
				0);
		headerLayout.setListener(this, this, this);
		registerElements();
	}

	private void registerElements() {
		mListview = (ListView) findViewById(R.id.homepage_list);
		homePageAdapter = new NotificationAdapter(this);
		homePageAdapter.setList(setDashBoardList());
		mListview.setAdapter(homePageAdapter);
		mListview.setOnItemClickListener(this);
	}

	/**
	 * Create Demo List
	 * 
	 * @return
	 */
	private ArrayList<Notification> setDashBoardList() {
		notificArrayList = new ArrayList<Notification>();
		for (int i = 0; i < 10; i++) {
			notificArrayList
					.add(new Notification(
							"Please feel Happy and fantastic",
							"3 hrs Ago",
							" https://lh5.googleusercontent.com/-1mua82b93S0/AAAAAAAAAAI/AAAAAAAAACs/VPX6N-Mxv1g/photo.jpg"));

		}

		return notificArrayList;
	}

	// Checks the status of mobile number verification and show the dialog
	// accordingly

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {

		case R.id.left_ib:
			finish();
			break;
		case 2:

			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Notification notification = (Notification) mListview
				.getItemAtPosition(position);

		Utility.showToast(this, notification.getmHeading());

	}
}
