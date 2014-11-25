package com.vinfotech.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vinfotech.demoapp.R;

public class MyWebView extends Fragment {
	
	private WebView webView;
	 
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// get the url to open
		Bundle args = getArguments();
		String url = args.getString("url");
		// set up the WebView
		webView = (WebView) getView().findViewById(R.id.webView);
		webView.setWebViewClient(new MyBrowser());
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.loadUrl(url);
 	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.web_view, container, false);
	    return view;		
	}	

	private class MyBrowser extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	       view.loadUrl(url);
	       return true;
	    }
	}

}
