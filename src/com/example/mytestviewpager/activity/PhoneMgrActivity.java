package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.adapter.PhoneMgrAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.PhoneInfo;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.MemoryManager;
import com.example.mytestviewpager.util.PhoneManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneMgrActivity extends BaseActivity {
	private ListView lv_listView;
	private PhoneMgrAdapter adapter;
	private ProgressBar pb_battery;
	private ProgressBar pb_phoneBattery;
	private TextView tv_batteryText;
	private int currentBattery;
	private int temperatureBattery;
	private BatteryReceiver onReceive;
	private LinearLayout ll_battery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_mgr);
		initActionBar("手机检测", R.drawable.btn_homeasup_default, -1,
				onClickListener);
		initMainUI();
		lv_listView = (ListView) findViewById(R.id.lv_listView);
		adapter = new PhoneMgrAdapter(this);
		lv_listView.setAdapter(adapter);
		new Thread() {
			public void run() {
				asyncLoadData();
			};
		}.start();
	}

	private void initMainUI() {
		pb_battery = (ProgressBar) findViewById(R.id.pb_battery);
		pb_phoneBattery = (ProgressBar) findViewById(R.id.pb_phone_battery);
		tv_batteryText = (TextView) findViewById(R.id.tv_phone_batteryText);
		ll_battery = (LinearLayout) findViewById(R.id.ll_phone_battery);
		ll_battery.setOnClickListener(onClickListener);
		onReceive = new BatteryReceiver();
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(onReceive, intentFilter);
	}

	private void asyncLoadData() {
		lv_listView.setVisibility(View.INVISIBLE);
		pb_battery.setVisibility(View.VISIBLE);
		String title;
		String content;
		Drawable icon;
		PhoneManager manager = PhoneManager.getPhoneManager(this);

		title = "设备名称：" + manager.getPhoneName1();
		content = "系统版本：" + manager.getPhoneSystemVersion();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_version);
		final PhoneInfo phoneInfo1 = new PhoneInfo(title, content, icon);

		title = "全部运行内存："
				+ CommonUtil
						.getFileInfo(MemoryManager.getTotalMemoryPhoneRam());
		content = "可用内存："
				+ CommonUtil.getFileInfo(MemoryManager
						.getFreeMemoryRam(PhoneMgrActivity.this));
		icon = getResources().getDrawable(R.drawable.setting_info_icon_space);
		final PhoneInfo phoneInfo2 = new PhoneInfo(title, content, icon);

		title = "CPU名称：" + manager.getPhoneCpuName();
		content = "CPU数量：" + manager.getPhoneCpuNumber();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_cpu);
		final PhoneInfo phoneInfo3 = new PhoneInfo(title, content, icon);

		title = "基带版本：" + manager.getPhoneSystemBasebandVersion();
		content = "是否ROOT：" + (manager.isRoot() ? "是" : "否");
		icon = getResources().getDrawable(R.drawable.setting_info_icon_root);
		final PhoneInfo phoneInfo4 = new PhoneInfo(title, content, icon);

		title = "手机分辨率：" + manager.getPhoneCpuName();
		content = "相机分辨率：" + manager.getPhoneCpuNumber();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
		final PhoneInfo phoneInfo5 = new PhoneInfo(title, content, icon);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.addDataToAdapter(phoneInfo1);
				adapter.addDataToAdapter(phoneInfo2);
				adapter.addDataToAdapter(phoneInfo3);
				adapter.addDataToAdapter(phoneInfo4);
				adapter.addDataToAdapter(phoneInfo5);
				adapter.notifyDataSetChanged();
				lv_listView.setVisibility(View.VISIBLE);
				pb_battery.setVisibility(View.INVISIBLE);
			}
		});

	}

	/**
	 * 四大组件之一：广播通知BroadcastReceiver
	 * 
	 * @author Administrator
	 * 
	 */
	public class BatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				Bundle bundle = intent.getExtras();
				int maxBattery = (Integer) bundle
						.get(BatteryManager.EXTRA_SCALE);
				currentBattery = (Integer) bundle
						.get(BatteryManager.EXTRA_LEVEL);
				temperatureBattery = (Integer) bundle
						.get(BatteryManager.EXTRA_TEMPERATURE);
				pb_phoneBattery.setMax(maxBattery);
				pb_phoneBattery.setProgress(currentBattery);
				int use100 = currentBattery * 100 / maxBattery;
				tv_batteryText.setText(use100+"%");
			}
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(onReceive);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_left:
				startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.ll_phone_battery:
				showDeatial();
				break;
			default:
				break;
			}

		}
	};

	private void showDeatial() {
		new AlertDialog.Builder(PhoneMgrActivity.this)
				.setTitle("电池详情")
				.setItems(
						new String[] { "当前电量" + currentBattery,
								" 手机温度" + temperatureBattery }, null).show();
	}
}
