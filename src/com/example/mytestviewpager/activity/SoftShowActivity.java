package com.example.mytestviewpager.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.adapter.AppAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.AppInfo;
import com.example.mytestviewpager.util.AppInfoManager;

public class SoftShowActivity extends BaseActivity {
	private int id;
	private String title;
	private ListView lv_soft_show;
	private ProgressBar pb_clear_soft;
	private CheckBox ck_soft_show;
	private Button btn_show_processsoft;
	private AppAdapter adapter;

	private AppInfoReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_show);
		int id = getIntent().getIntExtra("viewId", R.id.file_classlist_all);
		switch (id) {
		case R.id.file_classlist_all:
			title = "全部软件";
			break;
		case R.id.file_classlist_system:
			title = "系统软件";
			break;
		case R.id.file_classlist_phone:
			title = "用户软件";
			break;
		}
		initActionBar(title, R.drawable.btn_homeasup_default, -1,
				onClickListener);
		this.id = id;
		initUI();
		adapter = new AppAdapter(this);
		lv_soft_show.setAdapter(adapter);
		initLoadData();
	}

	private void initUI() {
		lv_soft_show = (ListView) findViewById(R.id.lv_softList_show);
		pb_clear_soft = (ProgressBar) findViewById(R.id.pb_softshow);
		ck_soft_show = (CheckBox) findViewById(R.id.ck_soft_show);
		ck_soft_show.setOnCheckedChangeListener(listener);
		btn_show_processsoft = (Button) findViewById(R.id.btn_progressClear_show);
		btn_show_processsoft.setOnClickListener(onClickListener);
		receiver = new AppInfoReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(AppInfoReceiver.APP_DEL);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);
	}

	private List<AppInfo> listInfo = null;

	private void initLoadData() {
		pb_clear_soft.setVisibility(View.VISIBLE);
		lv_soft_show.setVisibility(View.INVISIBLE);
		new Thread() {
			@Override
			public void run() {
				switch (id) {
				case R.id.file_classlist_all:
					listInfo = AppInfoManager.getAppInfoManager(
							SoftShowActivity.this).getAllPackageInfos(true);
					break;
				case R.id.file_classlist_system:
					listInfo = AppInfoManager.getAppInfoManager(
							SoftShowActivity.this).getSystemPackageInfo(true);
					break;
				case R.id.file_classlist_phone:
					listInfo = AppInfoManager.getAppInfoManager(
							SoftShowActivity.this).getUsePackageInfo(true);
					break;
				}
				runOnUiThread(new Runnable() {
					public void run() {
						adapter.setDataToAdapter(listInfo);
						adapter.notifyDataSetChanged();
						pb_clear_soft.setVisibility(View.INVISIBLE);
						lv_soft_show.setVisibility(View.VISIBLE);
					};
				});
			}
		}.start();

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_left:
				startActivity(SoftMgrActivity.class);
				finish();
				break;
			case R.id.btn_progressClear_show:
				List<AppInfo> appList = adapter.getDataList();
				for (AppInfo ai : appList) {
					String packageName = ai.getPackageInfo().packageName;
					if (ai.isDel()) {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_DELETE);
						intent.setData(Uri.parse("package:" + packageName));
						startActivity(intent);
					}
				}
				break;
			}

		}
	};

	public class AppInfoReceiver extends BroadcastReceiver {

		private static final String APP_DEL = "com.example.mytestviewpager";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_PACKAGE_REMOVED)
					|| action.equals(APP_DEL)) {
				initLoadData();
			}
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			List<AppInfo> listAll = adapter.getDataList();
			for (AppInfo ai : listAll) {
				ai.setDel(isChecked);
			}
			adapter.notifyDataSetChanged();
		}
	};
}
