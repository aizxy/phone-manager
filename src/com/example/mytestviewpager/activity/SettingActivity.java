package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.MainActivity;
import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.drawable;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.util.NotificationUtil;
import com.example.mytestviewpager.view.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class SettingActivity extends BaseActivity {
	private ActionBar actionBar;
	private ToggleButton tb_notification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		tb_notification=(ToggleButton) findViewById(R.id.tb_message);
		actionBar=(ActionBar) findViewById(R.id.actionBar_setting);
		actionBar.initBar("设置", R.drawable.ic_child_configs, -1, onClickListener);
		tb_notification.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				int id=buttonView.getId();
				if(isChecked){
					NotificationUtil.startNotification(SettingActivity.this);
				}else{
					NotificationUtil.cancleNotification(SettingActivity.this);
				}
			}
		});
	}
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
			case R.id.iv_left:
				startActivity(BarActivity.class);
			}
		}
	};
	public void hitSettingItem(View v){
		int id=v.getId();
		switch(id){
		case R.id.linear6:
			SharedPreferences spf=getSharedPreferences("main",Context.MODE_PRIVATE);
			Editor editor=spf.edit();
			editor.putBoolean("isFirst", true);
			editor.commit();
			Intent intent =new Intent(SettingActivity.this,MainActivity.class);
			intent.putExtra("ClassName", SettingActivity.this.getClass().getSimpleName());
			startActivity(intent);
			break;
		case R.id.linear3:
			Intent in=new Intent(SettingActivity.this,AboutActivity.class);
			in.putExtra("aboutOur",SettingActivity.this.getClass().getSimpleName() );
			startActivity(in);
			break;
		case R.id.linear7:
			Intent in7=new Intent(SettingActivity.this,HomeActivity.class);
			in7.putExtra("管理大全",SettingActivity.this.getClass().getSimpleName() );
			startActivity(in7);
			break;
		}
		
	}
	
}
