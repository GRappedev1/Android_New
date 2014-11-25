package com.vinfotech.adapter;

import java.util.ArrayList;
import java.util.List;

import com.vinfotech.demoapp.R;
import com.vinfotech.server.fileutil.ImageLoader;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ViewPagerAdapter extends PagerAdapter {

	Activity activity;
	int imageArray[];
	private ImageLoader imageLoader;
	private List<String> imageUrlArray = new ArrayList<String>();

	public ViewPagerAdapter(Activity act, List<String> imgArra) {
		imageUrlArray = imgArra;
		activity = act;
		imageLoader = new ImageLoader(act);
	}

	public int getCount() {
		return imageUrlArray.size();
	}

	public Object instantiateItem(ViewGroup container, int position) {

		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(
				R.layout.adapter_project_images_view_pager_layout, container,
				false);

		ImageView mImageView = (ImageView) view
				.findViewById(R.id.image_display);
		mImageView.setScaleType(ScaleType.FIT_CENTER);
		imageLoader.displayImage(imageUrlArray.get(position), mImageView, 0);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}
