package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.adapter.MyAdapter_gridView;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

public class OKActivity extends Activity {
	private GridView gridView;
	private GridView gridView1;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ok);
		image=(ImageView) findViewById(R.id.imageVok);
		gridView=(GridView) findViewById(R.id.gridView_ok);
		gridView1=(GridView) findViewById(R.id.gridView_ok);
		MyAdapter_gridView adapter=new MyAdapter_gridView(this);
		gridView.setAdapter(adapter);
		MyAdapter_gridView adapter1=new MyAdapter_gridView(this);
		gridView1.setAdapter(adapter1);
		
	}

	
}
