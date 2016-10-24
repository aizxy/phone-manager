package com.example.mytestviewpager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseBaseAdapter;
import com.example.mytestviewpager.bean.AppInfo;

public class AppAdapter extends BaseBaseAdapter<AppInfo> {

	private LayoutInflater layoutInflater;

	public AppAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_softshow_item,
					null);
			vh.tv_app_lable_softshow = (TextView) convertView
					.findViewById(R.id.tv_app_lable_softshow);
			vh.tv_app_packageName = (TextView) convertView
					.findViewById(R.id.tv_app_packageName);
			vh.tv_app_version_softshow = (TextView) convertView
					.findViewById(R.id.tv_app_clear_softshow);
			vh.iv_app_icon_softshow = (ImageView) convertView
					.findViewById(R.id.iv_app_icon_softshow);
			vh.cb_app_softshow = (CheckBox) convertView
					.findViewById(R.id.cb_app_softshow);
			
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String title = getItem(position).getPackageInfo().applicationInfo
				.loadLabel(context.getPackageManager()).toString();
		String packageName=getItem(position).getPackageInfo().packageName;
		String version = getItem(position).getPackageInfo().versionName;
		boolean isDel = getItem(position).isDel();
		Bitmap bitMap = ((BitmapDrawable) getItem(position).getPackageInfo().applicationInfo
				.loadIcon(context.getPackageManager())).getBitmap();
		vh.tv_app_lable_softshow.setText(title);
		vh.tv_app_packageName.setText(packageName);
		vh.tv_app_version_softshow.setText(version);
		vh.iv_app_icon_softshow.setImageBitmap(bitMap);
		vh.cb_app_softshow.setTag(position);
		vh.cb_app_softshow.setChecked(getItem(position).isDel());
		vh.cb_app_softshow.setOnCheckedChangeListener(onCheckedChangeListener);
		return convertView;
	}

	class ViewHolder {
		TextView tv_app_lable_softshow, tv_app_packageName,
				tv_app_version_softshow;
		ImageView iv_app_icon_softshow;
		CheckBox cb_app_softshow;
	}
	
	private OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int position =(Integer) buttonView.getTag();
			getDataList().get(position).setDel(isChecked);
		}
	};

}
