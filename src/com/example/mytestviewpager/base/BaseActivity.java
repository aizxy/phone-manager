package com.example.mytestviewpager.base;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.view.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseActivity extends Activity{
	private static ArrayList<BaseActivity> onLineActivityList=new ArrayList<BaseActivity>();
	
	public void finishAllActivity(){
		Iterator<BaseActivity> iterator=onLineActivityList.iterator();
		while(iterator.hasNext()){
			iterator.next().finish();
		}
	}
	
	protected void initActionBar(String title,int leftId,int rightId,OnClickListener onClickListener){
		ActionBar actionBar=(ActionBar) findViewById(R.id.actionBar);
		actionBar.initBar(title, leftId, rightId, onClickListener);
	}
	
	protected void startActivity(Class<?> targetClass){
		Intent intent =new Intent(this,targetClass);
		startActivity(intent);
	}
	
	protected void startActivity(Class<?> targetclass,Bundle bundle){
		Intent intent =new Intent(this,targetclass);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	protected void showToast(String context){
		Toast.makeText(this, context, Toast.LENGTH_SHORT).show();
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		onLineActivityList.add(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		onLineActivityList.remove(this);
	}
	
	protected Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg){
			myHandlerMessage(msg);
		}
	};
	
	protected  void myHandlerMessage(Message msg){
		
	}
}
