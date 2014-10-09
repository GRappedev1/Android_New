package com.vinfotech.handler;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SearchHandler {
	private EditText mSearchEt;
	private Handler mHandler = new Handler();

	public SearchHandler(EditText editText) {
		this.mSearchEt = editText;
	}

	private SearchListener mSearchListener;

	public void setSearchListener(SearchListener listener) {
		this.mSearchListener = listener;

		if (null != mSearchEt) {
			mSearchEt.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					mHandler.removeCallbacksAndMessages(null);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							if (null != mSearchListener) {
								mSearchListener.onSearch(mSearchEt.getText().toString().trim());
							}
						}
					}, 350);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
				}
			});
		}
	}

	public interface SearchListener {
		void onSearch(String text);
	}
}
