package com.vinfotech.module.ListSelection;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.server.fileutil.ImageLoader;

public class SelectionListAdapter extends BaseAdapter {
	private ArrayList<SelectionList> dashBoardList;
	Context mContext;
	private ImageLoader loader;

	private LayoutInflater minflater;

	public SelectionListAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		loader = new ImageLoader(mContext);
	}

	public void setList(ArrayList<SelectionList> dashBoardList) {
		this.dashBoardList = dashBoardList;
		notifyDataSetChanged();
	}

	public ArrayList<SelectionList> getList() {
		return this.dashBoardList;

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
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = minflater.inflate(R.layout.item_selected_list, null,
					false);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);
			viewHolder.txtTime = (TextView) convertView
					.findViewById(R.id.description);
			viewHolder.mImagePro = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dashBoardList.get(position).getIsSelected() == 0) {
					viewHolder.checkBox.setChecked(false);
					dashBoardList.get(position).setIsSelected(1);
				} else {
					viewHolder.checkBox.setChecked(true);
					dashBoardList.get(position).setIsSelected(0);
				}
				notifyDataSetChanged();

			}
		});
		viewHolder.txtItemName.setText(dashBoardList.get(position)
				.getmHeading());
		viewHolder.txtTime.setText(dashBoardList.get(position).getmTime());
		loader.displayImage(dashBoardList.get(position).getmImage(),
				viewHolder.mImagePro, 2);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dashBoardList.get(position).getIsSelected() == 0) {
					viewHolder.checkBox.setChecked(false);
					dashBoardList.get(position).setIsSelected(1);
				} else {
					viewHolder.checkBox.setChecked(true);
					dashBoardList.get(position).setIsSelected(0);
				}
				notifyDataSetChanged();

			}
		});
		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName, txtTime;
		private ImageView mImagePro;
		private CheckBox checkBox;
	}
}