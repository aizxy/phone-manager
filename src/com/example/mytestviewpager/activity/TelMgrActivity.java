package com.example.mytestviewpager.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.adapter.TelmgrGridAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.ClassInfo;
import com.example.mytestviewpager.util.DBManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class TelMgrActivity extends BaseActivity {
	private GridView gv_telmgr_view;
	private TelmgrGridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_mgr);
		initActionBar("通讯大全", R.drawable.btn_homeasup_default, -1, onClickListener);
		gv_telmgr_view = (GridView) findViewById(R.id.gv_telmgr_view);
		adapter = new TelmgrGridAdapter(this);
		gv_telmgr_view.setAdapter(adapter);
		gv_telmgr_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int idx=adapter.getDataList().get(position).getIdx();
				String name=adapter.getDataList().get(position).getName();
				Bundle bundle=new Bundle();
				bundle.putString("title", name);
				bundle.putInt("idx", idx);
				startActivity(TelmgrShowActivity.class,bundle);
			}
			
		});
		asyncTaskLoadData();
	}

	private void asyncTaskLoadData() {

		new Thread() {
			public void run() {
				try {
					DBManager.readUpdataDB(getResources().getAssets().open(
							"commonnum.db"));
					final List<ClassInfo> list = DBManager.readClassListTable();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							adapter.setDataToAdapter(list);
							adapter.notifyDataSetChanged();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}

			};
		}.start();

	}
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(HomeActivity.class);
		}
	};
}
