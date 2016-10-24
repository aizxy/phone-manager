package com.example.mytestviewpager.adapter;

import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.drawable;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter_gridView extends BaseAdapter{
	private Context context;
	int images[] ={R.drawable.ic_launcher, R.drawable.boy, R.drawable.xiaotu,
			R.drawable.lufei};
	public MyAdapter_gridView(Context context){
		super();
		this.context=context;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder vh;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.ok_buju, null);
			vh=new viewHolder();
			vh.iv=(ImageView) convertView.findViewById(R.id.imageVok);
			convertView.setTag(vh);
					
		}
		else{
			vh=(viewHolder) convertView.getTag();
		}
		vh.iv.setImageResource(images[position]);
		return convertView;
	}
	class viewHolder{
		private ImageView iv;
	}

}
