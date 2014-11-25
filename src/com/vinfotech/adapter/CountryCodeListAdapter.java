package com.vinfotech.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.utility.MCCUtil.MCC;

public class CountryCodeListAdapter extends BaseAdapter {
	private List<MCC> mMCCListFiltered, mMCCList;
	private LayoutInflater minflater;

	public CountryCodeListAdapter(Context context) {
		minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return null == mMCCListFiltered ? 0 : mMCCListFiltered.size();
	}

	@Override
	public Object getItem(int position) {
		return mMCCListFiltered.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		CountryViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new CountryViewHolder();
			convertView = minflater.inflate(R.layout.country_code_row, null, false);
			viewHolder.mCountryNameTv = (TextView) convertView.findViewById(R.id.country_name_tv);
			viewHolder.mCountryCodeTv = (TextView) convertView.findViewById(R.id.country_code_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (CountryViewHolder) convertView.getTag();
		}

		viewHolder.mCountryNameTv.setText(mMCCListFiltered.get(position).country);
		viewHolder.mCountryCodeTv.setText(mMCCListFiltered.get(position).code);

		return convertView;
	}

	public void setMCCList(List<MCC> list) {
		this.mMCCList = list;

		setFilter(null);
	}

	public class CountryViewHolder {
		private TextView mCountryNameTv, mCountryCodeTv;
	}

	public void setFilter(String text) {
		if (null != mMCCList) {
			mMCCListFiltered = new ArrayList<MCC>();
			if (null != text) {
				for (MCC mcc : mMCCList) {
					if (mcc.country.toLowerCase().startsWith(text.toLowerCase())
							|| (mcc.code.length() > 0 && mcc.code.substring(text.startsWith("+") ? 0 : 1).startsWith(text))) {
						mMCCListFiltered.add(mcc);
					}
				}
			} else {
				mMCCListFiltered.addAll(mMCCList);
			}
			notifyDataSetChanged();
		}
	}
}
