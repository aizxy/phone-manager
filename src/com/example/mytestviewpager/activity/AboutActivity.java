package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.view.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends BaseActivity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initActionBar("关于我们", R.drawable.btn_homeasup_default, -1,
				onClickListener);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.iv_left:
				String fromClass = getIntent().getStringExtra("aboutOur");
				if (fromClass == null) {
					startActivity(BarActivity.class);
				} else {
					startActivity(SettingActivity.class);
				}
				break;
			case R.id.iv_right:
				startActivity(SettingActivity.class);
				break;
			}
		}
	};
}
