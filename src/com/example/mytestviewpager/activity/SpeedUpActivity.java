package com.example.mytestviewpager.activity;

import java.util.List;
import java.util.Map;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.adapter.RunningAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.RunningAppInfo;
import com.example.mytestviewpager.util.AppInfoManager;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.MemoryManager;
import com.example.mytestviewpager.util.SystemManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SpeedUpActivity extends BaseActivity {
	private TextView tv_phoneName, tv_phoneModel, tv_phoneRam;
	private ProgressBar pb_progress;
	private ListView speedup_listView;
	private ProgressBar pb_progress_list;
	private Button btn_oneKeySpeed;
	private Button btn_show_process;
	private RunningAdapter adapter;
	private CheckBox ck_speedup;

	private Map<Integer, List<RunningAppInfo>> appInfos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speed_up);
		initActionBar("手机加速", R.drawable.btn_homeasup_default, -1, listener);
		speedup_listView = (ListView) findViewById(R.id.speedup_listView);
		adapter = new RunningAdapter(this);
		speedup_listView.setAdapter(adapter);
		initView();
		loadData();
	}

	private void initView() {
		tv_phoneName = (TextView) findViewById(R.id.tv_phoneName);
		tv_phoneModel = (TextView) findViewById(R.id.tv_phoneModel);
		tv_phoneRam = (TextView) findViewById(R.id.tv_phoneRam);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
		pb_progress_list = (ProgressBar) findViewById(R.id.pb_progress_list);
		btn_oneKeySpeed = (Button) findViewById(R.id.button_onekeyspeed);
		btn_show_process = (Button) findViewById(R.id.btn_show_progress);
		ck_speedup = (CheckBox) findViewById(R.id.ck_speedup);
		ck_speedup.setOnCheckedChangeListener(onCheckedChangeListener);
		btn_oneKeySpeed.setOnClickListener(listener);
		btn_show_process.setOnClickListener(onClick);
		initPhoneData();
		initRamData();

	}

	private void initPhoneData() {
		tv_phoneName.setText(SystemManager.getPhoneName());
		tv_phoneModel.setText(SystemManager.getPhoneModel());
	}

	private void initRamData() {
		float totalMemory = MemoryManager.getTotalMemoryPhoneRam();
		float freeMemory = MemoryManager.getFreeMemoryRam(SpeedUpActivity.this);
		float useMemory = totalMemory - freeMemory;
		float useP = useMemory / totalMemory;
		int use100 = (int) (useP * 100);
		pb_progress.setProgress(use100);
		tv_phoneRam.setText("可用内存" + CommonUtil.getFileInfo((long) freeMemory)
				+ "/" + CommonUtil.getFileInfo((long) totalMemory));

	}

	private void loadData() {
		speedup_listView.setVisibility(View.INVISIBLE);
		pb_progress_list.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				appInfos = AppInfoManager.getAppInfoManager(
						SpeedUpActivity.this).getRuningAppInfos();
				runOnUiThread(new Runnable() {
					public void run() {
						initRamData();
						adapter.setDataToAdapter(appInfos
								.get(AppInfoManager.USER_TYPE_FLAG));
						adapter.setState(RunningAdapter.USER_FLAG);
						adapter.notifyDataSetChanged();
						speedup_listView.setVisibility(View.VISIBLE);
						pb_progress_list.setVisibility(View.INVISIBLE);
					}
				});
			}
		}.start();
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.iv_left:
				startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.button_onekeyspeed:
				List<RunningAppInfo> listAllData = adapter.getDataList();
				for (RunningAppInfo run : listAllData) {
					if (run.isClear()) {
						String packageName = run.getPackageName();
						AppInfoManager.getAppInfoManager(SpeedUpActivity.this)
								.KillProcesses(packageName);
					}
				}
				loadData();
				ck_speedup.setChecked(false);
				break;

			}

		}
	};
	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn_show_progress:
				if (appInfos != null) {
					switch (adapter.getState()) {
					case RunningAdapter.USER_FLAG:
						adapter.setDataToAdapter(appInfos
								.get(AppInfoManager.SYSTEM_TYPE_FLAG));
						adapter.setState(RunningAdapter.SYSTEM_FLAG);
						btn_show_process.setText("显示系统程序");
						break;
					case RunningAdapter.SYSTEM_FLAG:
						adapter.setDataToAdapter(appInfos
								.get(AppInfoManager.USER_TYPE_FLAG));
						adapter.setState(RunningAdapter.USER_FLAG);
						btn_show_process.setText("显示应用程序");
						break;
					}

				}
				adapter.notifyDataSetChanged();
				break;
			}
		}
	};

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			List<RunningAppInfo> listAllData = adapter.getDataList();
			for (RunningAppInfo run : listAllData) {
				run.setClear(isChecked);
			}
			adapter.notifyDataSetChanged();
		}
	};
}
