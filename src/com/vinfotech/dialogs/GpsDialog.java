package com.vinfotech.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.vinfotech.demoapp.R;

public class GpsDialog extends Dialog implements android.view.View.OnClickListener {
	private Button mOkBtn, mNoBtn;

	public GpsDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.gps_dialog);

		// Find Ids
		mOkBtn = (Button) findViewById(R.id.yesGps_btn);
		mNoBtn = (Button) findViewById(R.id.noGps_btn);

		// Listener assigned
		mOkBtn.setOnClickListener(this);
		mNoBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yesGps_btn:
			v.getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			this.cancel();
			break;
		case R.id.noGps_btn:
			this.cancel();
			break;

		default:
			break;
		}
	}
}
