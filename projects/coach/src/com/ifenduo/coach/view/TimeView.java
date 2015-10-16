package com.ifenduo.coach.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class TimeView extends View {
	private Paint			paint;
	private int				mHeight, mWidth, mRadius;
	private Canvas			mCanvas;
	private ArrayList<Arc>	list;

	public TimeView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.RED);
		list = new ArrayList<Arc>();
		list.add(new Arc(1, 3));
		list.add(new Arc(4, 6));
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mCanvas = canvas;
		mHeight = getHeight();
		mWidth = getWidth();
		mRadius = mWidth / 2;
		if (mWidth > mHeight) {
			mRadius = mHeight / 2;
		}
		getWidth();
		getHeight();
		paint.setColor(Color.GRAY);
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, mRadius, paint);
		RectF rectF = new RectF();
		rectF.left = 0 + 20;
		rectF.top = 20;
		rectF.right = getWidth() - 20;
		rectF.bottom = 2 * mRadius - 20;
		paint.setColor(Color.WHITE);
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, mRadius - 5, paint);
		paint.setColor(Color.GRAY);
		canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, 20, paint);
		canvas.drawLine(2 * mRadius, mRadius, mRadius * 2 - 20, mRadius, paint);
		canvas.drawLine(0, mRadius, 20, mRadius, paint);
		canvas.drawLine(mRadius, 2 * mRadius, mRadius, 2 * mRadius - 20, paint);
		// 第一象限
		canvas.drawLine((float) Math.sin(30 * Math.PI / 180) * mRadius + mRadius, (float) (mRadius - Math.cos(30 * Math.PI / 180) * mRadius), (float) Math.sin(30 * Math.PI / 180)
				* (mRadius - 20) + mRadius, (float) (mRadius - Math.cos(30 * Math.PI / 180) * (mRadius - 20)), paint);
		canvas.drawLine((float) Math.sin(60 * Math.PI / 180) * mRadius + mRadius, (float) (mRadius - Math.cos(60 * Math.PI / 180) * mRadius), (float) Math.sin(60 * Math.PI / 180)
				* (mRadius - 20) + mRadius, (float) (mRadius - Math.cos(60 * Math.PI / 180) * (mRadius - 20)), paint);
		// 第四象限
		canvas.drawLine((float) Math.sin(60 * Math.PI / 180) * mRadius + mRadius, (float) (mRadius + Math.cos(60 * Math.PI / 180) * mRadius), (float) Math.sin(60 * Math.PI / 180)
				* (mRadius - 20) + mRadius, (float) (mRadius + Math.cos(60 * Math.PI / 180) * (mRadius - 20)), paint);
		canvas.drawLine((float) Math.sin(30 * Math.PI / 180) * mRadius + mRadius, (float) (mRadius + Math.cos(30 * Math.PI / 180) * mRadius), (float) Math.sin(30 * Math.PI / 180)
				* (mRadius - 20) + mRadius, (float) (mRadius + Math.cos(30 * Math.PI / 180) * (mRadius - 20)), paint);
		// 第三象限
		canvas.drawLine((float) (mRadius - Math.sin(60 * Math.PI / 180) * mRadius), (float) (mRadius + Math.cos(60 * Math.PI / 180) * mRadius),
				(float) (mRadius - Math.sin(60 * Math.PI / 180) * (mRadius - 20)), (float) (mRadius + Math.cos(60 * Math.PI / 180) * (mRadius - 20)), paint);
		canvas.drawLine((float) (mRadius - Math.sin(30 * Math.PI / 180) * mRadius), (float) (mRadius + Math.cos(30 * Math.PI / 180) * mRadius),
				(float) (mRadius - Math.sin(30 * Math.PI / 180) * (mRadius - 20)), (float) (mRadius + Math.cos(30 * Math.PI / 180) * (mRadius - 20)), paint);
		// 第二象限
		canvas.drawLine((float) (mRadius - Math.sin(30 * Math.PI / 180) * mRadius), (float) (mRadius - Math.cos(30 * Math.PI / 180) * mRadius),
				(float) (mRadius - Math.sin(30 * Math.PI / 180) * (mRadius - 20)), (float) (mRadius - Math.cos(30 * Math.PI / 180) * (mRadius - 20)), paint);
		canvas.drawLine((float) (mRadius - Math.sin(60 * Math.PI / 180) * mRadius), (float) (mRadius - Math.cos(60 * Math.PI / 180) * mRadius),
				(float) (mRadius - Math.sin(60 * Math.PI / 180) * (mRadius - 20)), (float) (mRadius - Math.cos(60 * Math.PI / 180) * (mRadius - 20)), paint);
		// canvas.drawArc(rectF, 0, 160, true, paint);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(getWidth() / 2, (mHeight - mWidth) / 2 + mRadius, 24, paint);
		paint.setColor(Color.RED);
		// canvas.drawArc(rectF, 0, 360, true, paint);
		paint.setColor(Color.BLUE);
		 canvas.drawArc(rectF, 0, 90, true, paint);
		// canvas.drawArc(rectF, 0, 60, true, paint);
//		for (Arc arc : list) {
//			int divide = arc.end - arc.start;
//			int startAngle = arc.start * 30;
//			int endAngle = arc.end * 30;
//			canvas.drawArc(rectF, startAngle, endAngle, true, paint);
//		}
		paint.setColor(Color.WHITE);
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, 20, paint);
		super.onDraw(canvas);
	}

	public void drawTime(int start, int end) {
		RectF rectF = new RectF();
		rectF.left = 0;
		rectF.top = (mHeight - mWidth) / 2;
		rectF.right = getWidth();
		rectF.bottom = (mHeight - mWidth) / 2 + 2 * mRadius;
		Paint paint_01 = new Paint();
		paint_01.setColor(Color.BLUE);
		if (mCanvas != null) {
			mCanvas.drawArc(rectF, 0, 30, true, paint_01);
		}

	}

	class Arc {
		int	start;
		int	end;

		public Arc(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public Arc() {

		}
	}
}
