package com.example.mytestviewpager.adapter;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseBaseAdapter;
import com.example.mytestviewpager.bean.MClearInfo;
import com.example.mytestviewpager.util.CommonUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ClearMgrAdapter extends BaseBaseAdapter<MClearInfo> {

	LayoutInflater layoutInflater;

	public ClearMgrAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_clear_item,
					null);
			vh.cb_clear=(CheckBox) convertView.findViewById(R.id.cb_clearmgr);
			vh.iv_clear_icon=(ImageView) convertView.findViewById(R.id.iv_clear_icon);
			vh.tv_clear_lable=(TextView) convertView.findViewById(R.id.tv_clear_lable);
			vh.tv_clear_size=(TextView) convertView.findViewById(R.id.tv_clear_size);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.tv_clear_lable.setText(getItem(position).getSoftChineseName());
		vh.iv_clear_icon.setImageDrawable(getItem(position).getIcon());
		vh.tv_clear_size.setText(CommonUtil.getFileInfo(getItem(position).getSize()));
		return convertView;
	}

	class ViewHolder {
		TextView tv_clear_lable,tv_clear_size;
		ImageView iv_clear_icon;
		CheckBox cb_clear;
		
	}

}
