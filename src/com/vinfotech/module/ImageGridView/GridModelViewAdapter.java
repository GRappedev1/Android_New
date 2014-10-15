package com.vinfotech.module.ImageGridView;

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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.vinfotech.demoapp.R;

public class GridModelViewAdapter extends BaseAdapter {

	private ArrayList<GridModel> billBoradResult;
	Context mContext;
	private AQuery aq;
	private LayoutInflater minflater;
	private int mType;

	public GridModelViewAdapter(Context context, int type) {
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		this.mType = type;
		aq = new AQuery((Activity) mContext);
	}

	public void setList(ArrayList<GridModel> billBoradResult) {
		this.billBoradResult = billBoradResult;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (billBoradResult == null) {
			return 0;
		}
		return billBoradResult.size();
	}

	@Override
	public Object getItem(int arg0) {

		return billBoradResult.get(arg0);

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
			convertView = minflater.inflate(R.layout.item_billboard_view, null,
					false);
			viewHolder.txt_down = (TextView) convertView
					.findViewById(R.id.txt_header);
			viewHolder.txt_upr = (TextView) convertView
					.findViewById(R.id.text_upr);
			viewHolder.ivImageBig = (ImageView) convertView
					.findViewById(R.id.image_big);
			viewHolder.txt_down.setVisibility(View.GONE);
			viewHolder.txt_upr.setVisibility(View.GONE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txt_down
				.setText(billBoradResult.get(position).getmHeading());
		viewHolder.txt_upr.setText(billBoradResult.get(position).getmHeading());
		switch (mType) {
		case 0:// text hide

			break;
		case 1:// text show upr
			viewHolder.txt_down.setVisibility(View.VISIBLE);
			break;
		case 2:// text shown bottom
			viewHolder.txt_upr.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

		setImage(viewHolder.ivImageBig, billBoradResult.get(position)
				.getmImage(), 0);
		return convertView;
	}

	private void setImage(final ImageView ivImageView, String string, int type) {
		if (string != null && !string.equalsIgnoreCase("")) {
			File file = aq.getCachedFile(string);
			if (file != null) {
				aq.id(ivImageView).image(file, true, 300,
						new BitmapAjaxCallback() {
							@Override
							public void callback(String url, ImageView iv,
									Bitmap bm, AjaxStatus status) {
								ivImageView.setImageBitmap(bm);
							}
						});
			} else {
				Bitmap placeholder;
				if (type == 0) {
					placeholder = aq.getCachedImage(R.drawable.default_330);
				} else {
					placeholder = aq
							.getCachedImage(R.drawable.interior_slider_img4);
				}
				aq.id(ivImageView).image(string, true, true, 0, 0, placeholder,
						0, 0.75f);

			}

		} else {
			if (type == 0) {
				ivImageView.setImageResource(R.drawable.default_330);
			} else {
				ivImageView.setImageResource(R.drawable.interior_slider_img4);
			}
		}

	}

	public class ViewHolder {

		private TextView txt_upr, txt_down;
		private ImageView ivImageBig;
	}
}
