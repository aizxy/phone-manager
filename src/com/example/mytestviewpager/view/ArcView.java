package com.example.mytestviewpager.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ArcView extends View{
	private Paint paint=new Paint();
	private RectF ovel;
	private int arcColour=0xFFFF8C00;
	private static int START_ANGLE=-90	;
	private int sweep_angle=0;
	private int state=0;
	
	private int[] back={-5,-5,-10,-9,-12,-12,-5};
	private int backIndex;
	
	private int[] go={10,10,6,6,4,4,2};
	private int goIndex;
	
	private boolean isRunning=false;
	
	public ArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAngle(360);
	}
	public void setAngle(int angle){
		sweep_angle=angle;
		postInvalidate();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=MeasureSpec.getSize(widthMeasureSpec);
		int height=MeasureSpec.getSize(heightMeasureSpec);
		ovel=new RectF(0,0,width,height);
		setMeasuredDimension(width, height);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(arcColour);
		paint.setAntiAlias(true);
		canvas.drawArc(ovel, START_ANGLE, sweep_angle,true, paint);
	}
	public void setAngle_new(final int angle){
		if(isRunning){
			return;
		}
		isRunning=true;
		state=0;
		final Timer  timer=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				switch(state){
				case 0:
					sweep_angle+=back[backIndex++];
					if(backIndex>=back.length){
						backIndex=back.length-1;
					}
					postInvalidate();
					if(sweep_angle<=0){
						sweep_angle=0;
						state=1;
						backIndex=0;
					}
					break;
				case 1:
					sweep_angle+=go[goIndex++];
					if(goIndex>=go.length){
						goIndex=go.length-1;
					}
					postInvalidate();
					if(sweep_angle>=angle){
						sweep_angle=angle;
						goIndex=0;
						isRunning=false;
						timer.cancel();
					}
					break;
				}
			}
		};
		timer.schedule(task,30, 40);
	}
}
