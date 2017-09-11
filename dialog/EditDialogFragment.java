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

public class EditDialogFragment extends DialogFragment/* implements OnEditorActionListener*/ implements OnClickListener
{
	
	protected EditDialogSelect mEditDialogSelect;
	
	public interface EditDialogSelect {
		int SELECT_NONE = -1; // 什么都没选
		int SELECT_LEFT = 0; // 选择左边的按钮
		int SELECT_RIGHT = 1; // 选择右边的按钮
		void OnSelectBT(int selectid, String code);
	}
	
	public void SetNomalDialogwListener(EditDialogSelect listener) {
		mEditDialogSelect = listener;
	}
	
//	public interface EditNameDialogListener {  
//  void onFinishEditDialog(String inputText);  
//	}
	
	protected EditText mEditText;
	protected Button cancelbt;
	protected Button surebt;
  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cust_fragment_dialog, container);
		mEditText = (EditText) view.findViewById(R.id.id_txt_username);
//		getDialog().setTitle("Hello");
		getDialog().setTitle(null);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));//去掉黑边
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		mEditText.requestFocus();                       // EditText获得焦点  
//        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE); // 显示软键盘  
//        mEditText.setOnEditorActionListener(this);      // 设置Action监听器 
		
		cancelbt = (Button) view.findViewById(R.id.cust_dialog_notify_button1);
		surebt = (Button) view.findViewById(R.id.cust_dialog_notify_button2);
		cancelbt.setOnClickListener(this);
		surebt.setOnClickListener(this);
		return view;
	}

	
	@Override
	public void onClick(View v) {

		int selectid = EditDialogSelect.SELECT_NONE;
		switch (v.getId()) {
		case R.id.cust_dialog_notify_button1:
			selectid = EditDialogSelect.SELECT_LEFT;
			break;
		case R.id.cust_dialog_notify_button2:
			selectid = EditDialogSelect.SELECT_RIGHT;
			break;
		default:
			break;
		}
		
		
		
		if (EditDialogSelect.SELECT_NONE != selectid) {
			if (mEditDialogSelect != null) {
				mEditDialogSelect.OnSelectBT(selectid, getEditTextCode());
			}
		}
		this.dismiss();
	
	}

	public String getEditTextCode(){
		if (mEditText == null) return "";
		return mEditText.getText().toString();
	}

	
	public void setEditTextHint(String str){
		mEditText.setHint(str);
	}
	
//	@Override  
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
//        if (EditorInfo.IME_ACTION_DONE == actionId) {  
//            // Return input text to activity  
//            EditNameDialogListener activity = (EditNameDialogListener) getActivity();  
//            activity.onFinishEditDialog(mEditText.getText().toString());  
//            this.dismiss();  
//            return true;  
//        }  
//        return false;  
//    }  
	
}
