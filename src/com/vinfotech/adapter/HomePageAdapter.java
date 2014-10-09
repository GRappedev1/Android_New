package com.vinfotech.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.model.DashBoard;

public class HomePageAdapter extends BaseAdapter {
	private ArrayList<DashBoard> dashBoardList;
	Context mContext;

	private LayoutInflater minflater;

	public HomePageAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;

	}

	public void setList(ArrayList<DashBoard> dashBoardList) {
		this.dashBoardList = dashBoardList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (dashBoardList == null)
			return 0;
		return dashBoardList.size();
	}

	@Override
	public Object getItem(int arg0) {

		return dashBoardList.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = minflater.inflate(R.layout.item_search_result, null,
					false);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtItemName.setText(dashBoardList.get(position).getmTitle());

		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName;
	}
}