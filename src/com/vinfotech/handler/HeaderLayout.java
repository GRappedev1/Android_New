package com.vinfotech.handler;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinfotech.demoapp.R;

public class HeaderLayout {
	private ImageView mLeftIb, mLeftExtraIb, mRightIb;
	private TextView mTitleTv, mRightTv;
	private int mTextColor;

	public HeaderLayout(Context context) {
		mLeftIb = (ImageButton) ((Activity) context).findViewById(R.id.left_ib);
		mLeftExtraIb = (ImageButton) ((Activity) context)
				.findViewById(R.id.left_extra_ib);
		mRightIb = (ImageButton) ((Activity) context)
				.findViewById(R.id.right_ib);

		mTitleTv = (TextView) ((Activity) context).findViewById(R.id.title_tv);
		mRightTv = (TextView) ((Activity) context).findViewById(R.id.right_tv);
	}

	public void setHeaderITT(int leftResId, int titleResId, int rightResId) {
		mLeftIb.setImageResource(leftResId);
		mTitleTv.setText(titleResId);
		mRightTv.setText(rightResId);

		mLeftExtraIb.setVisibility(View.GONE);
		mRightIb.setVisibility(View.GONE);
	}

	public void setHeaderIITI(int leftResId, int leftExtraResId,
			int titleResId, int rightResId) {
		mLeftIb.setImageResource(leftResId);
		mLeftExtraIb.setImageResource(leftExtraResId);
		mTitleTv.setText(titleResId);
		mRightIb.setImageResource(rightResId);

		mRightTv.setVisibility(View.GONE);
	}

	public void setListener(OnClickListener left, OnClickListener leftExtra,
			OnClickListener right) {
		mLeftIb.setOnClickListener(left);
		mLeftExtraIb.setOnClickListener(leftExtra);
		mRightIb.setOnClickListener(right);
		mRightTv.setOnClickListener(right);
	}

	// Hide Left, Visible Right
	public void setHeaderHLVR(int leftResId, int titleResId, int rightResId) {
		mTitleTv.setText(titleResId);
		mRightTv.setText(rightResId);

		mLeftIb.setVisibility(View.GONE);
		mLeftExtraIb.setVisibility(View.GONE);
		mRightIb.setVisibility(View.GONE);
	}

	public void setTextColor(int color) {
		mRightTv.setTextColor(color);
		mTextColor = color;
	}

	public int getTextColor() {
		return mTextColor;
	}

	public ImageView getLeftView() {
		return mLeftIb;
	}
}
