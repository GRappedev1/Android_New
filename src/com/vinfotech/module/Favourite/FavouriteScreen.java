package com.vinfotech.module.Favourite;

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

public class FavouriteScreen extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mListview;
	private ArrayList<Favourite> notificArrayList;
	private FavouriteAdapter homePageAdapter;

	// https://lh5.googleusercontent.com/-1mua82b93S0/AAAAAAAAAAI/AAAAAAAAACs/VPX6N-Mxv1g/photo.jpg
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notification_screenxml);
		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(R.drawable.icon_back, R.string.Notification,
				0);
		headerLayout.setListener(this, this, this);
		registerElements();
	}

	private void registerElements() {
		mListview = (ListView) findViewById(R.id.homepage_list);
		homePageAdapter = new FavouriteAdapter(this);
		homePageAdapter.setList(setDashBoardList());
		mListview.setAdapter(homePageAdapter);
		mListview.setOnItemClickListener(this);
		mListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	/**
	 * Create Demo List
	 * 
	 * @return
	 */
	private ArrayList<Favourite> setDashBoardList() {
		notificArrayList = new ArrayList<Favourite>();
		for (int i = 0; i < 10; i++) {
			notificArrayList
					.add(new Favourite(
							"John Doew " + i,
							(i / 5),
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
		Favourite favourite = (Favourite) mListview.getItemAtPosition(position);

		Utility.showToast(this, favourite.getmHeading());

	}
}
