package com.example.mytestviewpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseBaseAdapter;
import com.example.mytestviewpager.bean.RunningAppInfo;
import com.example.mytestviewpager.util.CommonUtil;

public class RunningAdapter extends BaseBaseAdapter<RunningAppInfo>{
	
	private LayoutInflater layoutInflater;
	private int state=0;
	public  static final int SYSTEM_FLAG=1;
	public static final int USER_FLAG=0;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public RunningAdapter(Context context) {
		super(context);
		layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		state=USER_FLAG;
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.layout_speedup_item, null);
			vh.tv_app_lable=(TextView) convertView.findViewById(R.id.tv_app_lable);
			vh.tv_app_packageName=(TextView) convertView.findViewById(R.id.tv_app_packageName);
			vh.tv_app_system=(TextView) convertView.findViewById(R.id.tv_app_system);
			vh.iv_app_icon=(ImageView) convertView.findViewById(R.id.iv_app_icon);
			vh.cb_app_clear=(CheckBox) convertView.findViewById(R.id.cb_app_clear);
			convertView.setTag(vh);
			
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.cb_app_clear.setTag(position);
		vh.cb_app_clear.setChecked(getItem(position).isClear());
		vh.cb_app_clear.setOnCheckedChangeListener(onCheckedChangeListener);
		vh.tv_app_lable.setText(getItem(position).getLableName());
		vh.tv_app_packageName.setText("内存"+CommonUtil.getFileInfo(getItem(position).getSize()));
		vh.tv_app_system.setText(getItem(position).isSystem()?"系统进程":"");
		vh.iv_app_icon.setImageDrawable(getItem(position).getIcon());
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_app_lable,tv_app_packageName,tv_app_system;
		ImageView iv_app_icon;
		CheckBox cb_app_clear;
	}
	
	private OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int position =(Integer) buttonView.getTag();
			getItem(position).setClear(isChecked);
		}
	};

}
