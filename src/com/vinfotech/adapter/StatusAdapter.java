package com.vinfotech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinfotech.demoapp.R;

public class StatusAdapter extends BaseAdapter{

	private String []statusArr;
	Context mContext;
	private LayoutInflater minflater;
	
	public StatusAdapter(Context context, String[] statusArr) {
		
		minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mContext = context;
		this.statusArr = new String[statusArr.length];
		this.statusArr = statusArr;
	}

	@Override
	public int getCount() {
		
		return statusArr.length;
	}

	@Override
	public Object getItem(int arg0) {
		
		return statusArr[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		StatusViewHolder viewHolder;
		
		if (convertView == null) {
			viewHolder = new StatusViewHolder();
			convertView = minflater.inflate(R.layout.country_code_row, null, false);
			viewHolder.statusTypeTv = (TextView) convertView.findViewById(R.id.country_name_tv);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (StatusViewHolder) convertView.getTag();
		}

		viewHolder.statusTypeTv.setText(statusArr[position]);

		return convertView;
	}

	
	public class StatusViewHolder{
		
		private TextView statusTypeTv;
	}
}
