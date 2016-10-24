package com.example.mytestviewpager.activity;

import java.text.DecimalFormat;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.MemoryManager;
import com.example.mytestviewpager.view.PieChartView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView.OnCloseListener;
import android.widget.TextView;

public class SoftMgrActivity extends BaseActivity {
	private PieChartView pieView;
	private ProgressBar pb_soft_phone;
	private ProgressBar pb_soft_sdCard;
	private TextView tv_soft_phoneSize;
	private TextView tv_soft_sdCardSize;
	private ImageView iv_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_mgr);
		initActionBar("内存管理", R.drawable.btn_homeasup_default, -1, onClickListener);
		initUI();
		initLoadData();
	}

	private void initUI() {
		pieView = (PieChartView) findViewById(R.id.pieView);
		pb_soft_phone = (ProgressBar) findViewById(R.id.pb_soft_phone);
		pb_soft_sdCard = (ProgressBar) findViewById(R.id.pb_soft_sdCard);
		tv_soft_phoneSize = (TextView) findViewById(R.id.tv_soft_phoneSize);
		tv_soft_sdCardSize = (TextView) findViewById(R.id.tv_soft_sdSize);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(HomeActivity.class);
			}
		});
	}

	private void initLoadData() {
		long phoneSelfTotal = MemoryManager.getPhoneSelfSize();
		long phoneSelfUnuse = MemoryManager.getPhoneSelfFreeSize();
		long phoneSelfUse = phoneSelfTotal - phoneSelfUnuse;

		long phoneSelfSdCardTotal = MemoryManager.getPhoneSelfSDCardSize();
		long phoneSelfSdCardUnuse = MemoryManager.getPhoneSelfSDCardFreeSize();
		long phoneSelfSdCardUse = phoneSelfSdCardTotal - phoneSelfSdCardUnuse;

		long phoneOutSdCardTotal = MemoryManager
				.getPhoneOutSDCardSize(getApplicationContext());
		long phoneOutSdCardUnuse = MemoryManager
				.getPhoneOutSDCardFreeSize(getApplicationContext());
		long phoneOutSdCardUse = phoneOutSdCardTotal - phoneOutSdCardUnuse;

		long phoneTotalMemory = phoneSelfTotal + phoneSelfSdCardTotal
				+ phoneOutSdCardTotal;

		long phoneInTotalSpace = phoneSelfTotal + phoneSelfSdCardTotal;
		long phoneInUnuseSpace = phoneSelfUnuse + phoneSelfSdCardUnuse;
		long phonInUseSpace=phoneInTotalSpace-phoneInUnuseSpace;

		float phoneSpaceF = (phoneSelfTotal + phoneSelfSdCardTotal)
				/ phoneTotalMemory;
		float sdCardSpaceF = phoneOutSdCardTotal / phoneTotalMemory;

		DecimalFormat df = new DecimalFormat("#.00");
		phoneSpaceF = Float.parseFloat(df.format(phoneSpaceF));
		sdCardSpaceF = Float.parseFloat(df.format(sdCardSpaceF));

		pieView.setPercentAnim(phoneSpaceF, sdCardSpaceF);

		tv_soft_phoneSize.setText("可用:"
				+ CommonUtil.getFileInfo(phoneInUnuseSpace)+ "/"
				+ CommonUtil.getFileInfo(phoneInTotalSpace));
		tv_soft_sdCardSize.setText("可用:"
				+ CommonUtil.getFileInfo(phoneOutSdCardUnuse) + "/"
				+ CommonUtil.getFileInfo(phoneOutSdCardTotal));
		pb_soft_phone.setMax((int)(phoneInTotalSpace/1024));
		pb_soft_phone.setProgress((int)(phoneInUnuseSpace/1024));
		pb_soft_sdCard.setMax((int)(phoneOutSdCardTotal/1024));
		pb_soft_sdCard.setProgress((int)(phoneOutSdCardUse/1024));
	}
	
	public void hitListItem(View v){
		int viewId=v.getId();
		switch (viewId) {
		case R.id.file_classlist_all:
		case R.id.file_classlist_system:
		case R.id.file_classlist_phone:
			Bundle bundle=new Bundle();
			bundle.putInt("viewId", viewId);
			startActivity(SoftShowActivity.class, bundle);
			break;
		}
	}
	
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(HomeActivity.class);
		}
	};
}
