package com.wyxh.repast.user.view.dialog;

import com.wyxh.repast.user.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class GetPicDialog extends Dialog{

	public interface INormalDialogSelect {
		int SELECT_NONE = -1; // 什么都没选
		int SELECT_LEFT = 0; // 选择左边的按钮
		int SELECT_RIGHT = 1; // 选择右边的按钮

		void OnSelectBT(int selectid);
	}
	
	public void SetNomalPopupWindowListener(INormalDialogSelect listener) {
		mNomalPopupWindowListener = listener;
	}
	
	private INormalDialogSelect mNomalPopupWindowListener;
	private LinearLayout mlinearone;
	private LinearLayout mlineartwo;
	private LinearLayout mlinearthree;

//	private TextView mtv1;
//	private TextView mtv2;
//	private TextView mtv3;

	private View rootview;
	private Context mcontext;
	
	@SuppressLint("InflateParams")
	public GetPicDialog(Context context) {
		super(context);
		mcontext = context;
		
		rootview = getLayoutInflater().inflate(R.layout.cust_dialog_getpic_view2, null, false);
		
		mlinearone = (LinearLayout) rootview.findViewById(R.id.cust_dialog_getpic2_linearlayout1);
		mlineartwo = (LinearLayout) rootview.findViewById(R.id.cust_dialog_getpic2_linearlayout2);
		mlinearthree = (LinearLayout) rootview.findViewById(R.id.cust_dialog_getpic2_linearlayout3);

//		mtv1 = (TextView) rootview.findViewById(R.id.cust_dialog_getpic2_tv1);
//		mtv2 = (TextView) rootview.findViewById(R.id.cust_dialog_getpic2_tv2);
//		mtv3 = (TextView) rootview.findViewById(R.id.cust_dialog_getpic2_tv3);

		mlinearone.setOnClickListener(mlistener);
		mlineartwo.setOnClickListener(mlistener);
		mlinearthree.setOnClickListener(mlistener);
	}
	
	public GetPicDialog(Context context, int theme) {
		super(context, theme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setCanceledOnTouchOutside(true);
		this.setContentView(rootview);
	}
	
	
	private View.OnClickListener mlistener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			dismiss();
			int id = v.getId();
			int selectid = -1;
			if (id == R.id.cust_dialog_getpic2_linearlayout1) {
				selectid = 0;
			} else if (id == R.id.cust_dialog_getpic2_linearlayout2) {
				selectid = 1;
			}

			if (selectid != -1 && mNomalPopupWindowListener != null) mNomalPopupWindowListener.OnSelectBT(selectid);
		}
	};

	
	public void ShowDialog() {
		if (isShowing()) return;

		Window rootwindow = this.getWindow();
		rootwindow.setGravity(Gravity.BOTTOM);
		rootwindow.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
		this.show();
		rootwindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rootview.setAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.dialog_enter));
	}
	
}
