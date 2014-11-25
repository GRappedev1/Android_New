package com.vinfotech.model;


import com.android.facebookslider.FacebookSlideView;
import com.vinfotech.handler.HeaderLayout;

import android.view.View;

public class FacebookSliderViews {
public View menuView, dynamicView;
public FacebookSlideView scrollView;

public HeaderLayout  headerView;


public FacebookSliderViews(View menuView, View dynamicView, HeaderLayout headerView, FacebookSlideView scrollView) {
	super();
	this.menuView = menuView;
	this.dynamicView = dynamicView;
	this.headerView = headerView;
	this.scrollView = scrollView;
}




}
