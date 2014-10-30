package com.vinfotech.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.model.Categories;

public class HomePageAdapter extends BaseAdapter implements Filterable {
	private List<Categories> dashBoardList;
	private List<Categories> tempCategoriesList;

	Context mContext;

	private LayoutInflater minflater;

	public HomePageAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;

	}

	public void setList(List<Categories> dashBoardList) {
		this.dashBoardList = dashBoardList;
		tempCategoriesList = dashBoardList;
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

		viewHolder.txtItemName
				.setText(dashBoardList.get(position).category_name);

		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName;
	}

	/**
	 * @param string
	 * @return filter list
	 */
	protected List<Categories> getFilteredList(String string) {
		List<Categories> temproryConatctList = tempCategoriesList;
		List<Categories> filterlist = new ArrayList<Categories>();
		for (int i = 0; i < temproryConatctList.size(); i++) {
			if (temproryConatctList.get(i).category_name.toLowerCase(
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
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.widget.Filter#performFiltering(java.lang.CharSequence)
			 */
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					List<Categories> filterlist = getFilteredList(constraint
							.toString());
					// Assign the data to the FilterResults
					// Assign the data to the FilterResults
					filterResults.values = filterlist;
					filterResults.count = filterlist.size();
				}

				return filterResults;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.widget.Filter#publishResults(java.lang.CharSequence,
			 * android.widget.Filter.FilterResults)
			 */
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				dashBoardList = (List<Categories>) results.values;
				notifyDataSetChanged();
			}
		};
		return filter;
	}
}