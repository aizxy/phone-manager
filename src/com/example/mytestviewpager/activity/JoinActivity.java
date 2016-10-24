package com.example.mytestviewpager.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.base.BaseActivity;

public class JoinActivity extends BaseActivity {
	private Button button;
	private ImageView image_android;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		AlphaAnimation alp=new AlphaAnimation(0, 1);
		button=(Button) findViewById(R.id.button_login);
		image_android=(ImageView) findViewById(R.id.image_android);
		Animation ani1=AnimationUtils.loadAnimation(this, R.anim.rotate_demo);
		image_android.startAnimation(ani1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(BarActivity.class);
			}
		});
	}

	
}
