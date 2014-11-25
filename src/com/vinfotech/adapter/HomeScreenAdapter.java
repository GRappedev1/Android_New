package com.vinfotech.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.model.ProjectBean;
import com.vinfotech.model.ProjectCategoriesBean;

public class HomeScreenAdapter extends BaseAdapter {
	private List<ProjectBean> dashBoardList;
	private List<ProjectBean> tempProjectCategoriesBeanList;

	Context mContext;

	private LayoutInflater minflater;

	public HomeScreenAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;

	}

	public void setList(List<ProjectBean> dashBoardList) {
		this.dashBoardList = dashBoardList;
		tempProjectCategoriesBeanList = dashBoardList;
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
			convertView = minflater.inflate(R.layout.home_screen_item_result, null,
					false);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);
			viewHolder.platform = (TextView) convertView
					.findViewById(R.id.platform);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.txtItemName.setTextColor(Color.parseColor("#1f1f1f"));
		viewHolder.txtItemName
				.setText(dashBoardList.get(position).project_name);
		viewHolder.platform.setText(dashBoardList.get(position).technology_used);

		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName,platform;
	}

	/**
	 * @param string
	 * @return filter list
	 */
	protected List<ProjectBean> getFilteredList(String string) {
		List<ProjectBean> temproryConatctList = tempProjectCategoriesBeanList;
		List<ProjectBean> filterlist = new ArrayList<ProjectBean>();
		for (int i = 0; i < temproryConatctList.size(); i++) {
			if (temproryConatctList.get(i).project_name.toLowerCase(
					Locale.ENGLISH).contains(string.toLowerCase())) {
				filterlist.add(temproryConatctList.get(i));
			}
		}
		return filterlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Filterable#getFilter()
	 */

}