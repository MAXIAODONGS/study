package com.wyxh.repast.user.view.dialog;

import java.util.HashMap;
import java.util.Map;

import com.lidroid.xutils.util.LogUtils;
import com.wyxh.repast.user.R;
import com.wyxh.repast.user.activity.account.SecurityCenterActivty;
import com.wyxh.repast.user.bean.ResultDto;
import com.wyxh.repast.user.fragment.main.account.paysetting.PaySetting1Fragment;
import com.wyxh.repast.user.fragment.main.account.paysetting.PaySettingListenner;
import com.wyxh.repast.user.http.HttpUtil;
import com.wyxh.repast.user.interfaces.http.AbstractCallback;
import com.wyxh.repast.user.utils.FunctionUtill;
import com.wyxh.repast.user.utils.WyxhViewUtil;
import com.wyxh.repast.user.utils.db.DbUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class PaySettingDialogFragment extends DialogFragment implements OnClickListener{

	private EditText mEditText;
	private Button mButton;
	private TextView mTextView;
	private PaySettingListenner mActivity;
	
	public static String PAY_SETTING_STATE = "open_state"; 
	private String openState;
	private Activity mBaseActivity;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (PaySettingListenner) activity;
		mBaseActivity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_setting_fragment_dialog,container);
		initView(view);
		initData();
		addListener();
		return view;
	}
	

	private void addListener() {
		mButton.setOnClickListener(this);
		mEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mButton.setClickable(false);
				mTextView.setVisibility(View.GONE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				mButton.setClickable(false);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				mButton.setClickable(true);
				mButton.setTextColor(Color.WHITE);
				mButton.setBackgroundResource(R.drawable.wyxh_button_select);
			}
		});
	}

	protected void checkPassWord() {
		HashMap<String, String> mSendparam = new HashMap<String, String>();
		mSendparam.put("key", DbUtils.getUser().getAccessToken());
		mSendparam.put("payment_password", mEditText.getText().toString().trim());
		HttpUtil.remoteRequestObj(HttpUtil.SEND_CHECK_PASSWORD, mSendparam,
				new AbstractCallback<ResultDto>() {
					@Override
					public void execute(ResultDto result) {
						LogUtils.e(result.toString());
						if ("".equals(result.getMsg())) {
							PaySettingDialogFragment.this.dismiss();
							mActivity.gotoPaySetting2(openState);
						} else if (result.getMsg().equals("密码错误"))  {
							mTextView.setVisibility(View.VISIBLE);
							mTextView.setText("密码错误，请重新输入...");
							mTextView.setClickable(false);
						}else if (result.getMsg().equals("没有设置支付密码")){
							mTextView.setVisibility(View.VISIBLE);
							mTextView.setText("暂未设置支付密码【点击设置】");
							mTextView.setClickable(true);
							mTextView.setOnClickListener(PaySettingDialogFragment.this);
//							PaySettingDialogFragment.this.dismiss();
//							FunctionUtill.ShowMyToastLong(mBaseActivity,result.getMsg());
						}
					}
				});
	}

	private void initData() {
		Bundle bundle = getArguments();
		if (bundle !=null) {
			openState = bundle.getString(PAY_SETTING_STATE);
		}
	}
	
	private void initView(View view) {
		mTextView = (TextView) view.findViewById(R.id.pay_setting_dialog_tv);
		mButton = (Button) view.findViewById(R.id.pay_setting_dialog_btn);
		mEditText = (EditText) view.findViewById(R.id.pay_setting_dialog_edt);
		mEditText.requestFocus();
		getDialog().setTitle(null);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));// 去掉黑边
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE); // 显示软键盘
		mEditText.setInputType(InputType.TYPE_CLASS_NUMBER); // 调用数字键盘
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_setting_dialog_btn:
			getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_HIDDEN); // 隐藏软键盘
			checkPassWord();
			break;

		case R.id.pay_setting_dialog_tv:
			PaySettingDialogFragment.this.dismiss();
			mTextView.setVisibility(View.GONE);
			mActivity.gotoSetPsw();
			break;
		default:
			break;
		}
	}

	
}
