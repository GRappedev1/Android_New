package com.vinfotech.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.utility.DialogUtil;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderIITI(R.drawable.icon_32_left_arrow, R.drawable.icon_32_left_arrow, R.string.app_name,
				R.drawable.icon_32_left_arrow);
		headerLayout.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.showOkDialog(v.getContext(), R.string.Mobile_Number_Already, R.string.has_already_been_verified);
			}
		}, null, null);
	}
}
