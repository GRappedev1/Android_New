package com.vinfotech.module.Notification;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.server.fileutil.ImageLoader;

public class NotificationAdapter extends BaseAdapter {
	private ArrayList<Notification> dashBoardList;
	Context mContext;
	private ImageLoader loader;

	private LayoutInflater minflater;

	public NotificationAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		loader = new ImageLoader(mContext);
	}

	public void setList(ArrayList<Notification> dashBoardList) {
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
			convertView = minflater.inflate(R.layout.item_notification, null,
					false);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);
			viewHolder.txtTime = (TextView) convertView.findViewById(R.id.description);
			viewHolder.mImagePro = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtItemName.setText(dashBoardList.get(position)
				.getmHeading());
		viewHolder.txtTime.setText(dashBoardList.get(position).getmTime());
		loader.displayImage(dashBoardList.get(position).getmImage(),
				viewHolder.mImagePro, 2);
		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName, txtTime;
		private ImageView mImagePro;
	}
}