package com.example.mytestviewpager.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.mytestviewpager.bean.FileInfo;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.FileTypeUtil;

public class FileAdapter extends BaseBaseAdapter<FileInfo> {

	private LayoutInflater layoutInflater;

	public FileAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.layout_fileshow_item,
					null);
			vh=new ViewHolder();
			vh.iv_fileshow=(ImageView) convertView.findViewById(R.id.iv_fileshow);
			vh.cb_fileshow=(CheckBox) convertView.findViewById(R.id.cb_fileshow);
			vh.tv_fileshow_name=(TextView) convertView.findViewById(R.id.tv_fileshow_name);
			vh.tv_fileshow_time=(TextView) convertView.findViewById(R.id.tv_fileshow_time);
			vh.tv_fshow_size=(TextView) convertView.findViewById(R.id.tv_fshow_size);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		FileInfo fileInfo=getItem(position);
		String name=fileInfo.getFile().getName();
		String size=CommonUtil.getFileInfo(fileInfo.getFile().length());
		String lastTime=CommonUtil.getStrTime(fileInfo.getFile().lastModified());
		boolean isCheck=fileInfo.isSelect();
		vh.tv_fileshow_name.setText(name);
		vh.tv_fileshow_time.setText(lastTime);
		vh.tv_fshow_size.setText(size);
		vh.cb_fileshow.setChecked(isCheck);
		vh.cb_fileshow.setTag(position);
		vh.cb_fileshow.setOnCheckedChangeListener(onCheckedChangeListener);
		Bitmap bitmap=getBitmap(fileInfo);
		vh.iv_fileshow.setImageBitmap(bitmap);
		return convertView;

	}
	
	public Bitmap getBitmap(FileInfo fileInfo){
		Bitmap bitmap=null;
		if(fileInfo.getFileType().equals(FileTypeUtil.TYPE_IMAGE)){
			//BitmapFactory.decodeFile可以将一个图片路径转化成bitmap对象
			bitmap=BitmapFactory.decodeFile(fileInfo.getFile().getPath());
			return bitmap;
		}
		Resources resources=context.getResources();
		int icon =resources.getIdentifier(fileInfo.getIcon(), "drawable", context.getPackageName());
		if(icon<=0){
			icon=R.drawable.icon_file;
		}
		bitmap=BitmapFactory.decodeResource(context.getResources(), icon);
		return bitmap;
	}
	
	class ViewHolder{
		CheckBox cb_fileshow;
		ImageView iv_fileshow;
		TextView tv_fileshow_name,tv_fileshow_time,tv_fshow_size;
	}
	
	private OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int position=(Integer) buttonView.getTag();
			getItem(position).setSelect(isChecked);
		}
	};

}
