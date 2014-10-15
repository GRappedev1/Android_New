package com.vinfotech.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.vinfotech.adapter.CountryCodeListAdapter;
import com.vinfotech.demoapp.R;
import com.vinfotech.handler.HeaderLayout;
import com.vinfotech.handler.SearchHandler;
import com.vinfotech.handler.SearchHandler.SearchListener;
import com.vinfotech.utility.MCCUtil;
import com.vinfotech.utility.MCCUtil.MCC;

public class CountryCodeListActivity extends Activity implements OnItemClickListener {
	private ListView mCountryCodeLv;
	private EditText mSearchEt;
	private CountryCodeListAdapter mCountryCodeListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_code_activity);

		HeaderLayout headerLayout = new HeaderLayout(this);
		headerLayout.setHeaderITT(R.drawable.icon_32_left_arrow, R.string.Country_Code, R.string.Empty_String);
		headerLayout.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		}, null, null);

		mCountryCodeListAdapter = new CountryCodeListAdapter(this);

		mSearchEt = (EditText) findViewById(R.id.search_et);
		mCountryCodeLv = (ListView) findViewById(R.id.country_code_lv);
		mCountryCodeLv.setAdapter(mCountryCodeListAdapter);
		mCountryCodeListAdapter.setMCCList(MCCUtil.getInstance().getMCCList());

		mCountryCodeLv.setOnItemClickListener(this);

		SearchHandler searchHandler = new SearchHandler(mSearchEt);
		searchHandler.setSearchListener(new SearchListener() {

			@Override
			public void onSearch(String text) {
				mCountryCodeListAdapter.setFilter(text);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		MCC mcc = (MCC) mCountryCodeListAdapter.getItem(position);
		Intent intent = new Intent();
		intent.putExtra("countryCode", mcc.code);
		setResult(RESULT_OK, intent);
		finish();
	}

}