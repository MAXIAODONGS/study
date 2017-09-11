package com.wyxh.repast.user.view.dialog;

import java.io.File;

import com.wyxh.repast.user.R;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class FragmentShowImg extends DialogFragment {

	public static FragmentShowImg newinstence(String uri) {
		FragmentShowImg fragment = new FragmentShowImg();
		Bundle bd = new Bundle();
		bd.putString("uri", uri);
		fragment.setArguments(bd);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Activity activity = getActivity();
		final ImageView img = new ImageView(activity);
		img.setAdjustViewBounds(true);
		
		ViewGroup.LayoutParams param = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		img.setLayoutParams(param);
		
		
		Bundle bd = getArguments();
		String tem = "";
		tem = bd.getString("uri");
		Uri uri = Uri.parse(tem);

		if ("".equals(tem))
			img.setImageResource(R.drawable.net_main_bg);
		else {
			File file = new File(tem);
			if (file.exists()) {

				img.setImageURI(uri);
			}
		}
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		
		return img;
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// Theme_Black_NoTitleBar_Fullscreen

		setStyle(DialogFragment.STYLE_NORMAL,
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);//黑背景主题的没有标题栏的样式

//		STYLE_NORMAL：会显示一个普通的dialog
//		STYLE_NO_TITLE：不带标题的dialog
//		STYLE_NO_FRAME：无框的dialog
//		STYLE_NO_INPUT：无法输入内容的dialog，即不接收输入的焦点，而且触摸无效。
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		// Activity activity = getActivity();
		// DisplayMetrics dm = new DisplayMetrics();
		// activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// dialog.getWindow().setLayout(dm.widthPixels, dm.heightPixels);
		return dialog;
	}
	
}
