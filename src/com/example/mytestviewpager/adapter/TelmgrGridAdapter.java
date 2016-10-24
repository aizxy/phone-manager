package com.example.mytestviewpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseBaseAdapter;
import com.example.mytestviewpager.bean.ClassInfo;

public class TelmgrGridAdapter extends BaseBaseAdapter<ClassInfo> {
	LayoutInflater layoutInflater = null;

	public TelmgrGridAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater
					.inflate(R.layout.layout_mrg_item, null);
			vh.tv_mgr_text = (TextView) convertView
					.findViewById(R.id.tv_mrg_text);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_mgr_text.setText(getItem(position).getName());
		switch (position % 3) {
		case 0:
			convertView.setBackgroundResource(R.drawable.mgr_green);
			break;
		case 1:
			convertView.setBackgroundResource(R.drawable.mgr_red);
			break;
		case 2:
		default:
			convertView.setBackgroundResource(R.drawable.mgr_yellow);
			break;
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_mgr_text;
	}

}
