package com.example.mytestviewpager;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.drawable;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.activity.AnimationActivity;
import com.example.mytestviewpager.activity.SettingActivity;
import com.example.mytestviewpager.adapter.MyPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private ViewPager viewPager;
	private MyPagerAdapter adapter;
	private TextView lead_skip;
	private TextView tv_use;
	private ImageView[] images=new ImageView[3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sp=getSharedPreferences("main", Context.MODE_PRIVATE);
		boolean isFirst=sp.getBoolean("isFirst", true);
		if(!isFirst){
			Intent intent=new Intent(MainActivity.this,AnimationActivity.class);
			startActivity(intent);
			finish();
		}else{
			setContentView(R.layout.activity_main);
			initUI();
			init();
			initData();
			saveSharedference();
		}
		
	}
	private void init(){
		adapter=new MyPagerAdapter(this);
		viewPager.setAdapter(adapter);
		lead_skip.setOnClickListener(this);
		viewPager.setOnPageChangeListener(listener);
		tv_use.setOnClickListener(this);
	}
	/**
	 * 初始化控件
	 */
	private void initUI(){
		images[0]=(ImageView) findViewById(R.id.imageV1);
		images[1]=(ImageView) findViewById(R.id.imageV2);
		images[2]=(ImageView) findViewById(R.id.imageV3);
		viewPager=(ViewPager) findViewById(R.id.viewPager);
		tv_use=(TextView) findViewById(R.id.tv_use);
		lead_skip=(TextView) findViewById(R.id.textView);
		images[0].setBackgroundResource(R.drawable.selected);
		lead_skip.setVisibility(View.VISIBLE);
		tv_use.setVisibility(View.INVISIBLE);
		
	}
	/**
	 * 初始化数据
	 */
	private void initData(){
		ImageView imageView=null;
		imageView=(ImageView) getLayoutInflater().inflate(R.layout.buju, null);
		imageView.setBackgroundResource(R.drawable.ic_launcher);
		adapter.addToAdapterView(imageView);
		imageView=(ImageView) getLayoutInflater().inflate(R.layout.buju, null);
		imageView.setBackgroundResource(R.drawable.applist);
		adapter.addToAdapterView(imageView);
		imageView=(ImageView) getLayoutInflater().inflate(R.layout.buju, null);
		imageView.setBackgroundResource(R.drawable.banner);
		adapter.addToAdapterView(imageView);
		adapter.notifyDataSetChanged();//将adapter进行一个刷新
		
	}
	public void saveSharedference(){
		SharedPreferences spf=getSharedPreferences("main",Context.MODE_PRIVATE);
		Editor editor=spf.edit();
		editor.putBoolean("isFirst", false);
		editor.commit();
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch(id){
		case R.id.textView:
			String fromClass=getIntent().getStringExtra("ClassName");
			if(fromClass==null){
				Intent intent=new Intent(MainActivity.this,AnimationActivity.class);
				startActivity(intent);
				finish();
			}else{
				Intent in=new Intent(MainActivity.this,SettingActivity.class);
				startActivity(in);
				finish();
			}
			break;
		case R.id.tv_use:
			Intent intent=new Intent(MainActivity.this,AnimationActivity.class);
			startActivity(intent);
		}
		
	}
	private OnPageChangeListener listener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			lead_skip.setVisibility(View.VISIBLE);
			tv_use.setVisibility(View.INVISIBLE);
			if(arg0>=2){
				lead_skip.setVisibility(View.INVISIBLE);
				tv_use.setVisibility(View.VISIBLE);
			}
			for(int i=0;i<images.length;i++){
				images[i].setBackgroundResource(R.drawable.ddefault);
			}
			images[arg0].setBackgroundResource(R.drawable.selected);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
