package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.drawable;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.util.AppInfoManager;
import com.example.mytestviewpager.util.MemoryManager;
import com.example.mytestviewpager.view.ActionBar;
import com.example.mytestviewpager.view.ArcView;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {
	private ArcView arcView;
	private TextView tv_score;
	private TextView tv_speed;
	private ImageView iv_home_score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initActionBar("管理大全",  R.drawable.btn_homeasup_default, -1, onClickListener);
		arcView=(ArcView) findViewById(R.id.arcView);
		tv_score=(TextView) findViewById(R.id.tv_score);
		tv_speed=(TextView) findViewById(R.id.tv_speed);
		iv_home_score=(ImageView) findViewById(R.id.iv_home_score);
		tv_speed.setOnClickListener(onClickListener);
		iv_home_score.setOnClickListener(onClickListener);
		initMemoryData();
		
	}
	private void initMemoryData() {
		float totalMemory=MemoryManager.getTotalMemoryPhoneRam();
		float freeMemory=MemoryManager.getFreeMemoryRam(HomeActivity.this);
		float useMemory=totalMemory-freeMemory;
		float useP=useMemory/totalMemory;
		int user100=(int)(useP*100);
		tv_score.setText(user100+"");
		int useAngle=(int)(useP*360);
		arcView.setAngle_new(useAngle);
	}
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
			case R.id.tv_speed:
			case R.id.iv_home_score:
				AppInfoManager.getAppInfoManager(HomeActivity.this).KillAllProcesses();
				initMemoryData();
				break;
			case R.id.iv_left:
				startActivity(SettingActivity.class);
				break;
			}
		}
	};

	public void hitHomeitem(View v){
		int id=v.getId();
		switch (id) {
		case R.id.tv_rocket:
//			showToast("提示......");
			startActivity(SpeedUpActivity.class);
			break;
		case R.id.tv_softmgr:
			startActivity(SoftMgrActivity.class);
			break;
		case R.id.tv_phonemgr:
			startActivity(PhoneMgrActivity.class);
			break;
		case R.id.tv_telmgr:
			startActivity(TelMgrActivity.class);
			break;
		case R.id.tv_filemgr:
			startActivity(FileMgrActivity.class);
			break;
		case R.id.tv_sdclean:
			startActivity(ClearActivity.class);
			break;
		}
	}
}
