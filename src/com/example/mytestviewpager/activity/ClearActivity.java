package com.example.mytestviewpager.activity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.adapter.ClearMgrAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.MClearInfo;
import com.example.mytestviewpager.util.ClearManager;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.FileManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClearActivity extends BaseActivity {
	private ListView lv_clear;
	private ClearMgrAdapter adapter;
	private ProgressBar pb_clear;
	private long total_size=0;
	private TextView tv_clear_file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);
		initActionBar("垃圾清理", R.drawable.btn_homeasup_default, -1,
				onClickListener);
		initMainUI();
		adapter=new ClearMgrAdapter(this);
		try {
			asyncTaskLoadData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		lv_clear.setAdapter(adapter);
	}
	
	private void initMainUI(){
		lv_clear=(ListView) findViewById(R.id.lv_clear);
		pb_clear=(ProgressBar) findViewById(R.id.pb_clear);
		tv_clear_file=(TextView) findViewById(R.id.tv_clear_file);
	}
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.iv_left:
				startActivity(HomeActivity.class);
				break;

			default:
				break;
			}
		}
	};

	private void asyncTaskLoadData() throws IOException {
		total_size=0;
		lv_clear.setVisibility(View.VISIBLE);
		pb_clear.setVisibility(View.INVISIBLE);
		ClearManager.readClearDB(getResources().getAssets()
				.open("clearpath.db"));
		final List<MClearInfo> listData = ClearManager
				.getPhoneSoftDetail(ClearActivity.this);
		System.out.println("==============="+listData);
		new Thread(){
			public void run() {
				for(MClearInfo cb:listData){
					File file=new File(cb.getFilePath());
					long size=FileManager.getFileSize(file);
					cb.setSize(size);
					
					Message msg=handler.obtainMessage();
					msg.what=1;
					msg.obj=size;
					handler.sendMessage(msg);
				}
				Message msg=handler.obtainMessage();
				msg.what=2;
				msg.obj=listData;
				handler.sendMessage(msg);
				
			};
		}.start();
		
	}
	
	protected void myHandlerMessage(Message msg){
		switch (msg.what) {
		case 1:
			long size=(Long) msg.obj;
			total_size+=size;
			tv_clear_file.setText(CommonUtil.getFileInfo(total_size));
			break;
		case 2:
			List<MClearInfo> listViewData=(List<MClearInfo>) msg.obj;
			showToast("没有找到任何软件。。。");
			adapter.setDataToAdapter(listViewData);
			adapter.notifyDataSetChanged();
			lv_clear.setVisibility(View.VISIBLE);
			pb_clear.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}
}
