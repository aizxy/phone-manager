package com.example.mytestviewpager.view;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBar extends LinearLayout{
	private ImageView iv_left;
	private ImageView iv_rignt;
	private TextView tv_title;

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.bar_buju, this);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_rignt=(ImageView) findViewById(R.id.iv_right);
		tv_title=(TextView) findViewById(R.id.tv_title);
		
	}
	public void initBar(String title,int leftId,int rightId,OnClickListener onClickListener){
		tv_title.setText(title);
		if(leftId!=-1){
			iv_left.setBackgroundResource(leftId);
			iv_left.setOnClickListener(onClickListener);
		}
		if(rightId!=-1){
			iv_rignt.setBackgroundResource(rightId);
			iv_rignt.setOnClickListener(onClickListener);
		}
	}
	
}
