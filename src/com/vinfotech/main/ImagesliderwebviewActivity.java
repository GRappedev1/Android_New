package com.vinfotech.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.vinfotech.demoapp.R;
import com.vinfotech.widget.MyWebView;

public class ImagesliderwebviewActivity extends FragmentActivity {
	
	// A helper class to keep a list of the Fragments and titles.
	class MyPageAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;
		private List<String> titles;
		  
		public MyPageAdapter(FragmentManager fm) {
		       super(fm);
		       this.fragments = new ArrayList<Fragment>();
		       this.titles    = new ArrayList<String>();
		}
		  
		public void addItem(String url, String title) {
			Fragment myFragment = new MyWebView();
		    Bundle args = new Bundle();
		    args.putString("url", url);
		    myFragment.setArguments(args);
		    this.fragments.add(myFragment);
		    this.titles.add(title);
		}
		  
		@Override 
		public Fragment getItem(int position) {
		       return this.fragments.get(position);
		}
		  
		public CharSequence getPageTitle(int position) {
		       return this.titles.get(position);
		}
		  
		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}
	
	private MyPageAdapter pageAdapter  = null;
	private ViewPager pager            = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_slider);
		
		pageAdapter = new MyPageAdapter(getSupportFragmentManager());

		// Add any number of items to the list of your Fragment
		pageAdapter.addItem("http://renovatewebapp.elasticbeanstalk.com/items/imagetag", "WebView 1");
		pageAdapter.addItem("http://renovatewebapp.elasticbeanstalk.com/items/imagetag", "WebView 2");
		pageAdapter.addItem("http://renovatewebapp.elasticbeanstalk.com/items/imagetag", "WebView 3");

		pager = (ViewPager)findViewById(R.id.pager);

		// This gives the number of Fragments loaded outside the view. 
		// Here set to the number of Fragments minus one, i.e., all Fragments loaded.
		// This might not be a good idea if there are many Fragments.
		pager.setOffscreenPageLimit(pageAdapter.getCount() - 1);
		pager.setAdapter(pageAdapter);
	}
}
