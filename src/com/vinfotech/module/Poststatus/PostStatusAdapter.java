package com.vinfotech.module.Poststatus;

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

public class PostStatusAdapter extends BaseAdapter {
	private ArrayList<Poststatus> postStatusList;
	Context mContext;
	private ImageLoader loader;

	private LayoutInflater minflater;

	public PostStatusAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		loader = new ImageLoader(mContext);
	}

	public void setList(ArrayList<Poststatus> dashBoardList) {
		this.postStatusList = dashBoardList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (postStatusList == null)
			return 0;
		return postStatusList.size();
	}

	@Override
	public Object getItem(int arg0) {

		return postStatusList.get(arg0);

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
			convertView = minflater.inflate(R.layout.item_post_status, null,
					false);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);
			viewHolder.txtDescription = (TextView) convertView
					.findViewById(R.id.description);
			viewHolder.txtLikes = (TextView) convertView
					.findViewById(R.id.txt_likes);
			viewHolder.txtChats = (TextView) convertView
					.findViewById(R.id.txt_chats);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtItemName
				.setText(postStatusList.get(position).getmTitle());
		viewHolder.txtDescription.setText(postStatusList.get(position)
				.getmDescription());
		viewHolder.txtLikes.setText(postStatusList.get(position).getmLikes());
		viewHolder.txtChats.setText(postStatusList.get(position).getmChats());

		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName, txtDescription, txtLikes, txtChats;

	}
}