package com.example.mytestviewpager.activity;

import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.adapter.TelShowAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.TableClass;
import com.example.mytestviewpager.util.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TelmgrShowActivity extends BaseActivity {
	private ListView lv_telShow;
	private TelShowAdapter adapter;
	List<TableClass> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telmgr_show);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		int idx = intent.getIntExtra("idx",1);
		initActionBar(title, R.drawable.btn_homeasup_default, -1, onClickListener);
		lv_telShow = (ListView) findViewById(R.id.lv_telshow);
		adapter = new TelShowAdapter(this);
		lv_telShow.setAdapter(adapter);
		lv_telShow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String number=adapter.getItem(position).getNumber()+"";
				Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));
//				intent.setData(Uri.parse("tel:"+number));
				startActivity(intent);
			}
		});
		asyncTaskLoadData(idx);
	}

	private void asyncTaskLoadData(final int idx) {
		new Thread() {
			public void run() {
				 list = DBManager.readTableClass("table" + idx);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.setDataToAdapter(list);
						adapter.notifyDataSetChanged();
					}
				});
			};
		}.start();
	}
	
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(TelMgrActivity.class);
		}
	};

}
