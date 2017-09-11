package com.wyxh.repast.user.view.grid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class CustomGridView extends GridView {

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomGridView(Context context) {
		super(context);

	}
	
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return true;
//	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
	
	float startY;
	float offsetY;
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		 switch (ev.getAction()) {
         case MotionEvent.ACTION_DOWN:
             startY = ev.getY();
//             Log.d("zzx", "zzx>>CustomGridView>>startY>="+startY);
             break;
         case MotionEvent.ACTION_MOVE:
        	 offsetY = ev.getY() - startY;
//        	 Log.d("zzx", "zzx>>CustomGridView>>offsetY>="+offsetY);
        	 break;
		 }
		 return super.onTouchEvent(ev);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//
//		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//			// 下面这句话是关键
//			return true;
//		}
//		return super.dispatchTouchEvent(ev);// 也有所不同哦
//	}
}
