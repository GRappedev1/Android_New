package com.vinfotech.module.ListSelection;

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
import com.vinfotech.utility.Utility;

public class SelectionListScreen extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mListview;
	private ArrayList<SelectionList> notificArrayList;
	private SelectionListAdapter homePageAdapter;
	private ArrayList<SelectionList> selectedList;

	// https://lh5.googleusercontent.com/-1mua82b93S0/AAAAAAAAAAI/AAAAAAAAACs/VPX6N-Mxv1g/photo.jpg
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notification_screenxml);
		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(R.drawable.icon_back, R.string.Notification,
				R.string.Done);
		headerLayout.setListener(this, this, this);
		registerElements();
	}

	private void registerElements() {
		mListview = (ListView) findViewById(R.id.homepage_list);
		homePageAdapter = new SelectionListAdapter(this);
		homePageAdapter.setList(setDashBoardList());
		mListview.setAdapter(homePageAdapter);
		mListview.setOnItemClickListener(this);
	}

	/**
	 * Create Demo List
	 * 
	 * @return
	 */
	private ArrayList<SelectionList> setDashBoardList() {
		notificArrayList = new ArrayList<SelectionList>();
		for (int i = 0; i < 10; i++) {
			notificArrayList
					.add(new SelectionList(
							"John Doe " + i,
							"" + i,
							" https://lh5.googleusercontent.com/-1mua82b93S0/AAAAAAAAAAI/AAAAAAAAACs/VPX6N-Mxv1g/photo.jpg",
							0));// (i % 2 == 0) ? 1 : 0)

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
		case R.id.right_tv:
			selectedList = homePageAdapter.getList();
			StringBuffer buffer = new StringBuffer();
			for (SelectionList selectionList : selectedList) {
				buffer.append(selectionList.getmTime() + " : ");
			}
			Utility.showToast(this, buffer.toString());
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SelectionList notification = (SelectionList) mListview
				.getItemAtPosition(position);

		Utility.showToast(this, notification.getmHeading());

	}
}
