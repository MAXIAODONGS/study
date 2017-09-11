package com.wyxh.repast.user.view.dialog;

import com.wyxh.repast.user.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 消息弹出框<br>
 * 弹出消息提示，消息框的元素设置都是一样的<br>
 * 根据不同的展示样式设置不同的继承类来实现
 * 
 * @author Administrator
 *
 */
public abstract class CommonBaseDialog extends Dialog implements android.view.View.OnClickListener {

	public interface INormalDialogSelect {
		int SELECT_NONE = -1; // 什么都没选
		int SELECT_LEFT = 0; // 选择左边的按钮
		int SELECT_RIGHT = 1; // 选择右边的按钮
		void OnSelectBT(int selectid);
	}
	
	public interface DialogDismisListener{
		void onDismiss();
	}

	protected Activity mActivity;
	protected Context mContext;
	protected View rootview;
	protected View line;
	protected LinearLayout headerLine;
	protected TextView titleTV;
	protected Button exitbtn;
	protected TextView msgTV;
	protected View bottomView;
	protected LinearLayout buttonLine;
	protected LinearLayout cancelBlock;
	protected Button cancelbt;
	protected LinearLayout sureBlock;
	protected Button surebt;
	protected INormalDialogSelect mNomalPopupWindowListener;
	protected DialogDismisListener mDialogDismissListener;

	/**
	 * @param context
	 */
	@SuppressLint("InflateParams")
	public CommonBaseDialog(Activity activity) {
		super(activity);
		this.mActivity = activity;
		initView();
	}
	
	

	public void initView(){
		rootview = getLayoutInflater().inflate(R.layout.cust_dialog_common_base_layout, null);
		// 头部区域
		titleTV = (TextView) rootview.findViewById(R.id.cust_dialog_title_textView1);
		// 消息体
		msgTV = (TextView) rootview.findViewById(R.id.cust_dialog_notify_textView1);
		cancelbt = (Button) rootview.findViewById(R.id.cust_dialog_notify_button1);
		surebt = (Button) rootview.findViewById(R.id.cust_dialog_notify_button2);
		cancelbt.setOnClickListener(this);
		surebt.setOnClickListener(this);
		
//		bottomView = (View) rootview.findViewById(R.id.cust_dialog_notify_bottom_view);
//		buttonLine = (LinearLayout) rootview.findViewById(R.id.cust_dialog_notify_button_group);
//		cancelBlock = (LinearLayout) rootview.findViewById(R.id.cust_dialog_notify_button1_container1);
//		sureBlock = (LinearLayout) rootview.findViewById(R.id.cust_dialog_notify_button2_container1);
//		line = (View) rootview.findViewById(R.id.cust_dialog_view_line);
//		exitbtn.setOnClickListener(this);
//		if (!StringUtil.isBlank(title)) {
//			titleTV.setText(title);
//		} else {
//			headerLine.setVisibility(View.GONE);
//		}
//		if (!StringUtil.isBlank(msg)) {
//			msgTV.setText(msg);
//		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(rootview);
	}

	/**
	 * 设置标题文字
	 * @param visible
	 */
	public void SetNomalPopupWindowTitle(String text) {
		titleTV.setVisibility(View.VISIBLE);
		titleTV.setText(text);
	}

	/**
	 * 设置消息文字
	 * 
	 * @param visible
	 */
	public void SetNomalPopupWindowMsg(String text) {
		msgTV.setText(text);
	}

	/**
	 * 设置左边取消按钮文字
	 * 
	 * @param visible
	 */
	public void SetLeftBTtext(String text) {
		cancelbt.setText(text);
	}

	/**
	 * 设置左边取消按钮显示
	 * 
	 * @param visible
	 */
	public void SetLeftBTvisible(int visible) {
		cancelBlock.setVisibility(visible);
		SetButtonBackground(visible);
	}

	/**
	 * 设置右边确认按钮文字
	 * 
	 * @param visible
	 */
	public void SetRightBTtext(String text) {
		surebt.setText(text);
	}

	/**
	 * 设置右边确认按钮显示
	 * 
	 * @param visible
	 */
	public void SetRightBTvisible(int visible) {
		sureBlock.setVisibility(visible);
		SetButtonBackground(visible);
	}

	/**
	 * @param visible
	 * 设置背景
	 */
	public void SetButtonBackground(int visible){
//		line.setVisibility(visible);
//		if (visible == View.GONE || visible == View.INVISIBLE) {
//			cancelbt.setBackgroundResource(R.drawable.message_ios_dialog_button_corner2);
//			surebt.setBackgroundResource(R.drawable.message_ios_dialog_button_corner2);
//		}else {
//			cancelbt.setBackgroundResource(R.drawable.message_ios_dialog_button_corner);
//			surebt.setBackgroundResource(R.drawable.message_ios_dialog_button_corner1);
//		}
	}
	
	/**
	 * 设置按钮区域显示
	 * 
	 * @param visible
	 */
	public void SeBottomlayoutvisible(int visible) {
		buttonLine.setVisibility(visible);
		bottomView.setVisibility(visible);
	}
	
	public void SetNomalPopupWindowListener(INormalDialogSelect listener) {
		mNomalPopupWindowListener = listener;
	}
	
	public void SetDialogDismissListener(DialogDismisListener l){
		mDialogDismissListener = l;
	}

	public void showDialog() {
		if (isShowing()) return;
		try {
			this.show();
			Window rootwindow = this.getWindow();
			rootwindow.setBackgroundDrawableResource(android.R.color.transparent);
			
//			android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//			WindowManager m = mcontext.getWindowManager();
//			Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
//			// p.height = (int) (d.getHeight() * 0.75); // 高度设置为屏幕的0.8
//			p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
//			rootwindow.setAttributes(p);
			
			 Window dialogWindow = getWindow();
			 WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			 
			 DisplayMetrics d;
			 if (mActivity != null) {
				 d = mActivity.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
			 }else {
				 d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
			 }
			 lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
//			 lp.height = (int) (d.heightPixels * 0.3);//高度设置为屏幕的0.3
			 dialogWindow.setAttributes(lp);
		} catch (Exception e) {}
	}

	@Override
	public void onClick(View v) {
		int selectid = INormalDialogSelect.SELECT_NONE;
		switch (v.getId()) {
		case R.id.cust_dialog_notify_button1:
			selectid = INormalDialogSelect.SELECT_LEFT;
			break;
		case R.id.cust_dialog_notify_button2:
			selectid = INormalDialogSelect.SELECT_RIGHT;
			break;
		default:
			break;
		}
		
		if (INormalDialogSelect.SELECT_NONE != selectid) {
			if (mNomalPopupWindowListener != null) {
				mNomalPopupWindowListener.OnSelectBT(selectid);
			}
		}
		this.dismiss();
	}

	
	@Override
	public void dismiss() {
		super.dismiss();
		if (mDialogDismissListener != null) {
			mDialogDismissListener.onDismiss();
		}
	}
}
