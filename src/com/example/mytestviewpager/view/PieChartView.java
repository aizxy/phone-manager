package com.example.mytestviewpager.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.mytestviewpager.R;

public class PieChartView extends View {

	private Paint paint;
	private RectF oval;
	private int baseColor;
	private int phoneColor;
	private int sdCardColor;
	private float pieChartPhoneAngle = 0;
	private float pieChartSDChartAngle = 0;

	private float percentPhone = 0;
	private float percentSdCard = 0;

	public PieChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		baseColor = context.getResources().getColor(R.color.piechar_base);
		phoneColor = context.getResources().getColor(R.color.piechar_phone);
		sdCardColor = context.getResources().getColor(R.color.piechar_sdcard);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		oval = new RectF(0, 0, viewWidth, viewHeight);
		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setColor(baseColor);
		canvas.drawArc(oval, -90, 360, true, paint);

		paint.setColor(phoneColor);
		canvas.drawArc(oval, -90, pieChartPhoneAngle, true, paint);

		paint.setColor(sdCardColor);
		canvas.drawArc(oval, -90, pieChartSDChartAngle, true, paint);
	}

	public void setPercentAnim(float f1, float f2) {
		percentPhone = f1;
		percentSdCard = f2;
		final float phoneTargetAngle = percentPhone * 360;
		final float sdCardTargetAngle = percentSdCard * 360;
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				pieChartPhoneAngle += 1;
				pieChartSDChartAngle += 1;
				postInvalidate();
				if (pieChartPhoneAngle >= phoneTargetAngle) {
					pieChartPhoneAngle = phoneTargetAngle;
				}
				if (pieChartSDChartAngle >= sdCardTargetAngle) {
					pieChartSDChartAngle = sdCardTargetAngle;
				}
				if (pieChartPhoneAngle >= phoneTargetAngle
						&& pieChartSDChartAngle >= sdCardTargetAngle) {
					timer.cancel();
				}
			}
		};
		timer.schedule(task, 25, 25);
	}
}
