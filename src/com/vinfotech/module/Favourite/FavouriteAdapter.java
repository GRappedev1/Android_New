package com.vinfotech.module.Favourite;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.server.fileutil.ImageLoader;
import com.vinfotech.utility.Utility;

public class FavouriteAdapter extends BaseAdapter {
	private ArrayList<Favourite> dashBoardList;
	Context mContext;
	private ImageLoader loader;

	private LayoutInflater minflater;

	public FavouriteAdapter(Context context) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		loader = new ImageLoader(mContext);
	}

	public void setList(ArrayList<Favourite> dashBoardList) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = minflater.inflate(R.layout.item_favourite, null,
					false);
			viewHolder.txtItemName = (TextView) convertView
					.findViewById(R.id.header_name);
			viewHolder.mRating = (RatingBar) convertView
					.findViewById(R.id.ratingBar1);
			viewHolder.mImagePro = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtItemName.setText(dashBoardList.get(position)
				.getmHeading());
		viewHolder.mRating.setRating(dashBoardList.get(position).getmRating());

		viewHolder.mRating
				.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						dashBoardList.get(position).setmRating(rating);
						Utility.showToast(mContext,
								("Rating: " + String.valueOf(rating)));
					}
				});
		loader.displayImage(dashBoardList.get(position).getmImage(),
				viewHolder.mImagePro, 2);
		return convertView;
	}

	public class ViewHolder {

		private TextView txtItemName;
		private RatingBar mRating;
		private ImageView mImagePro;
	}
}