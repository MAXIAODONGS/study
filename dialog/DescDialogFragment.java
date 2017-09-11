package com.wyxh.repast.user.view.dialog;

import com.wyxh.repast.user.R;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DescDialogFragment extends DialogFragment 
{
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.desc_fragment_dialog, container);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		return view;
	}

	
		
		

	
}
