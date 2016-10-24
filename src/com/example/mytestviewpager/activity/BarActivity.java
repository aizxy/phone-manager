package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.drawable;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.view.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class BarActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar);
		initActionBar("Ê×Ò³", R.drawable.nav_i,R.drawable.ic_child_configs,onClickListener);
	}
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
				case R.id.iv_left:
					startActivity(AboutActivity.class);
				break;
				case R.id.iv_right:
					startActivity(SettingActivity.class);
				break;
			}
		}
	}; 

}
