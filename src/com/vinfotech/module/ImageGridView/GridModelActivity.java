package com.vinfotech.module.ImageGridView;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.module.Notification.Notification;
import com.vinfotech.utility.Utility;

public class GridModelActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private GridView mListview;
	private ArrayList<GridModel> notificArrayList;
	private GridModelViewAdapter homePageAdapter;
	private int mType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_image_grid_screen);
		mType = getIntent().getIntExtra("TYPE", 0);
		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(R.drawable.icon_back, R.string.imagegrid, 0);
		headerLayout.setListener(this, this, this);
		registerElements();

	}

	private void registerElements() {
		mListview = (GridView) findViewById(R.id.billboard_grid);
		homePageAdapter = new GridModelViewAdapter(this, mType);
		homePageAdapter.setList(setDashBoardList());
		mListview.setAdapter(homePageAdapter);
		mListview.setOnItemClickListener(this);
	}

	/**
	 * Create Demo List
	 * 
	 * @return
	 */
	private ArrayList<GridModel> setDashBoardList() {
		notificArrayList = new ArrayList<GridModel>();
		for (int i = 0; i < 50; i++) {
			notificArrayList
					.add(new GridModel("Interior " + i, setImageURL(i)));

		}

		return notificArrayList;
	}

	// Checks the status of mobile number verification and show the dialog
	// accordingly

	private String setImageURL(int i) {
		switch (i % 5) {
		case 0:
			return "http://3.imimg.com/data3/JB/FN/MY-6382165/residential-interior-services-250x250.jpg";
		case 1:
			return "http://pimg.tradeindia.com/01136815/b/1/Bedroom-Interior-Designing.jpg";
		case 2:
			return "http://www.leointerior.com/images/interior-designers-for-corporate-office-in-jayanagar.jpg";
		case 3:
			return "http://www.jagranjosh.com/imported/images/E/interior_designing.jpg";
		case 4:
			return "http://101interiorideas.info/wp-content/uploads/interior-decoration-services.jpg";
		default:
			return "http://3.imimg.com/data3/PN/NQ/MY-9782667/living-room-interior-design-250x250.jpg";
		}

	}

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
