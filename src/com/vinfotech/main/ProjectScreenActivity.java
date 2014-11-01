package com.vinfotech.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.vinfotech.adapter.ViewPagerAdapter;
import com.vinfotech.demoapp.R;

public class ProjectScreenActivity extends Activity {

	private ImageView imageView1, imageView2, imageView3, imageView4,
			imageView5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_screen_layout);

		imageArra
				.add("http://www.joomlaworks.net/images/demos/galleries/abstract/7.jpg");
		imageArra
				.add("http://www.wired.com/images_blogs/wiredscience/2012/10/kitten-kisses-dog.jpg");
		imageArra
				.add("http://www.joomlaworks.net/images/demos/galleries/abstract/7.jpg");
		imageArra
				.add("http://www.wired.com/images_blogs/wiredscience/2012/10/kitten-kisses-dog.jpg");
		imageView1 = (ImageView) findViewById(R.id.imageViewOne);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageArra);
		ViewPager myPager = (ViewPager) findViewById(R.id.projectViewpager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);

		myPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setSelectedImageInLayout(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void setSelectedImageInLayout(int position) {

		if (position == 0) {
			imageView1.setBackgroundResource(R.drawable.white_dot);
			imageView2.setBackgroundResource(R.drawable.red_dot);
			imageView3.setBackgroundResource(R.drawable.red_dot);
			imageView4.setBackgroundResource(R.drawable.red_dot);
		} else if (position == 1) {
			imageView1.setBackgroundResource(R.drawable.red_dot);
			imageView2.setBackgroundResource(R.drawable.white_dot);
			imageView3.setBackgroundResource(R.drawable.red_dot);
			imageView4.setBackgroundResource(R.drawable.red_dot);

		} else if (position == 2) {
			imageView1.setBackgroundResource(R.drawable.red_dot);
			imageView2.setBackgroundResource(R.drawable.red_dot);
			imageView3.setBackgroundResource(R.drawable.white_dot);
			imageView4.setBackgroundResource(R.drawable.red_dot);

		} else if (position == 3) {
			imageView1.setBackgroundResource(R.drawable.red_dot);
			imageView2.setBackgroundResource(R.drawable.red_dot);
			imageView3.setBackgroundResource(R.drawable.red_dot);
			imageView4.setBackgroundResource(R.drawable.white_dot);

		} 

	}

	private List<String> imageArra = new ArrayList<String>();

}
