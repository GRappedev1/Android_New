package com.vinfotech.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vinfotech.demoapp.R;

/**
 * This Activity for showing bottom popup and select answer
 * */
public class ConfirmationActivity extends Activity implements OnClickListener {
	private Button mTakePictureBtn, mTakeFromGalleryBtn, mCancelBtn;
	private TextView mHeadingTv;
	private LinearLayout mContainerLl;
	private RelativeLayout mContainerRl;

	private String mAskFor;

	private final String TAKE_PHOTO = "TAKE_PHOTO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_screen_activity);

		mAskFor = getIntent().getStringExtra("ASK_FOR");
		mHeadingTv = (TextView) findViewById(R.id.heading_confirmation_tv);
		mTakePictureBtn = (Button) findViewById(R.id.take_picture_btn);
		mTakeFromGalleryBtn = (Button) findViewById(R.id.take_from_gallery_btn);
		mCancelBtn = (Button) findViewById(R.id.cancel_btn);
		mContainerRl = (RelativeLayout) findViewById(R.id.confirmation_parent_rl);
		mContainerLl = (LinearLayout) findViewById(R.id.view_contaniner_layout_confirm_rl);

		setInitialSetUp();

		mTakePictureBtn.setOnClickListener(this);
		mTakeFromGalleryBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		mContainerRl.setOnClickListener(this);
		mContainerLl.setOnClickListener(null);

		startAnimation();
	}

	/**
	 * Animation from bottom to top
	 * */
	private void startAnimation() {
		// Cancels any animations for this container.
		mContainerLl.clearAnimation();
		mContainerRl.clearAnimation();

		Animation animation = AnimationUtils.loadAnimation(ConfirmationActivity.this, R.anim.slide_up_dialog);
		mContainerLl.startAnimation(animation);

		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(500);
		mContainerRl.startAnimation(alphaAnimation);
	}

	/**
	 * Animation from top to bottom
	 * */
	private void reverseAnimation(final Intent intent) {
		mContainerLl.clearAnimation();
		Animation animation = AnimationUtils.loadAnimation(ConfirmationActivity.this, R.anim.slide_down_dialog);
		mContainerLl.startAnimation(animation);
		mContainerLl.setVisibility(View.GONE);

		AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation.setDuration(500);
		mContainerRl.startAnimation(alphaAnimation);
		mContainerRl.setVisibility(View.GONE);

		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				setResult(RESULT_OK, intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
	}

	/**
	 * set resource to button and title
	 * */
	private void setInitialSetUp() {
		if (TAKE_PHOTO.equals(mAskFor)) {
			mHeadingTv.setText(R.string.How_would_you);
			mTakePictureBtn.setText(R.string.Take_Picture);
			mTakeFromGalleryBtn.setText(R.string.Load_from_library);
			mCancelBtn.setText(android.R.string.cancel);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.take_picture_btn:
			Intent intent = new Intent();
			if (TAKE_PHOTO.equals(mAskFor)) {
				intent.putExtra("result", "CAMERA");
			}
			reverseAnimation(intent);
			break;

		case R.id.take_from_gallery_btn:
			intent = new Intent();
			if (TAKE_PHOTO.equals(mAskFor)) {
				intent.putExtra("result", "GALLERY");
			}
			reverseAnimation(intent);
			break;

		case R.id.cancel_btn:
			intent = new Intent();
			intent.putExtra("result", "Cancel");
			reverseAnimation(intent);
			break;

		case R.id.confirmation_parent_rl:
			intent = new Intent();
			intent.putExtra("result", "Cancel");
			reverseAnimation(intent);
			break;

		default:
			break;
		}
	}

}