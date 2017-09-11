package com.wyxh.repast.user.view.dialog;

import com.wyxh.repast.user.R;
import com.wyxh.repast.user.view.dialog.EditDialogFragment.EditDialogSelect;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseDialogFragment extends DialogFragment implements OnClickListener {

	protected ChooseDialogSelect mChooseDialogSelect;
	
	public interface ChooseDialogSelect {
		int SELECT_NONE = 0; // 什么都没选
		int SELECT_ONE = 1; //选择一个月 
		int SELECT_TWO = 2; //选择一个月 
		int SELECT_THREE = 3; //选择一个月 
		int SELECT_FOUR = 4; //选择一个月 
		void OnSelectBT(int selectid, String code);
	}
	
	public void SetDialogwListener(ChooseDialogSelect listener) {
		mChooseDialogSelect = listener;
	}
	
	
	
	private Button btn_choose1;
	private Button btn_choose2;
	private Button btn_choose3;
	private Button btn_choose4;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.choose_fragment_dialog, container);
		getDialog().setTitle(null);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));// 去掉黑边
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		btn_choose1 = (Button) view.findViewById(R.id.choose_btn_1);
		btn_choose2 = (Button) view.findViewById(R.id.choose_btn_2);
		btn_choose3 = (Button) view.findViewById(R.id.choose_btn_3);
		btn_choose4 = (Button) view.findViewById(R.id.choose_btn_4);
		
		addListener();
		
		return view;
	}

	private void addListener() {
		// TODO Auto-generated method stub
		btn_choose1.setOnClickListener(this);
		btn_choose2.setOnClickListener(this);
		btn_choose3.setOnClickListener(this);
		btn_choose4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int selectid = EditDialogSelect.SELECT_NONE;
		switch (v.getId()) {
		case R.id.choose_btn_1:
			selectid = ChooseDialogSelect.SELECT_ONE;
			btn_choose1.setSelected(true);
			break;
		case R.id.choose_btn_2:
			selectid = ChooseDialogSelect.SELECT_TWO;
			btn_choose2.setSelected(true);
			break;
		case R.id.choose_btn_3:
			selectid = ChooseDialogSelect.SELECT_THREE;
			btn_choose3.setSelected(true);
			break;
		case R.id.choose_btn_4:
			selectid = ChooseDialogSelect.SELECT_FOUR;
			btn_choose4.setSelected(true);
			break;
	
		default:
			break;
		}
		if (ChooseDialogSelect.SELECT_NONE != selectid) {
			if (mChooseDialogSelect != null) {
				mChooseDialogSelect.OnSelectBT(selectid, ""+selectid);
			}
		}
		this.dismiss();
	}

}
