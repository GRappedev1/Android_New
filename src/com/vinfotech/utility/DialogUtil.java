package com.vinfotech.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.main.WhiteProgressDialog;

public class DialogUtil {

	public static void showOkDialog(final Context context, int titleResId, int msgResId) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		dialog.setContentView(R.layout.ok_dialog);

		TextView titleTv = (TextView) dialog.findViewById(R.id.title_tv);
		titleTv.setText(titleResId);

		TextView messageTv = (TextView) dialog.findViewById(R.id.message_tv);
		messageTv.setText(msgResId);

		Button okBtn = (Button) dialog.findViewById(R.id.ok_btn);
		okBtn.setTag(dialog);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static void showOkDialog(final Context context, int titleResId, int msgResId, OnClickListener okListener) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		dialog.setContentView(R.layout.ok_dialog);

		TextView titleTv = (TextView) dialog.findViewById(R.id.title_tv);
		titleTv.setText(titleResId);

		TextView messageTv = (TextView) dialog.findViewById(R.id.message_tv);
		messageTv.setText(msgResId);

		Button okBtn = (Button) dialog.findViewById(R.id.ok_btn);
		okBtn.setTag(dialog);
		okBtn.setOnClickListener(okListener);

		dialog.show();
	}

	public static Dialog createProgressDialog(Context context, int msgResId) {
		WhiteProgressDialog dialog = new WhiteProgressDialog(context, msgResId);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	public static void showOkDialog(final Context context, String message) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		dialog.setContentView(R.layout.ok_dialog);

		TextView titleTv = (TextView) dialog.findViewById(R.id.title_tv);
		titleTv.setVisibility(View.GONE);

		TextView messageTv = (TextView) dialog.findViewById(R.id.message_tv);
		messageTv.setText(message);

		Button okBtn = (Button) dialog.findViewById(R.id.ok_btn);
		okBtn.setTag(dialog);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

}
