package com.example.mytestviewpager.adapter;

import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseBaseAdapter;
import com.example.mytestviewpager.bean.PhoneInfo;

public class PhoneMgrAdapter extends BaseBaseAdapter<PhoneInfo> {
	LayoutInflater layoutInflater;

	public PhoneMgrAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.layout_phonemgr_item,
					null);
			vh = new ViewHolder();
			vh.iv_phoneImg = (ImageView) convertView
					.findViewById(R.id.iv_phone_infoPhoto);
			vh.tv_phoneContent = (TextView) convertView
					.findViewById(R.id.tv_phone_content);
			vh.tv_phoneTitle = (TextView) convertView
					.findViewById(R.id.tv_phone_title);
			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		switch (position % 3) {
		case 0:
			vh.iv_phoneImg
					.setBackgroundResource(R.drawable.notification_information_progress_red);
			break;
		case 1:
			vh.iv_phoneImg
					.setBackgroundResource(R.drawable.notification_information_progress_yellow);
			break;
		case 2:
		default:
			vh.iv_phoneImg
					.setBackgroundResource(R.drawable.notification_information_progress_green);
			break;
		}
		vh.iv_phoneImg.setImageDrawable(getItem(position).getIcon());
		vh.tv_phoneContent.setText(getItem(position).getContent());
		vh.tv_phoneTitle.setText(getItem(position).getTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView iv_phoneImg;
		TextView tv_phoneTitle, tv_phoneContent;
	}

}
