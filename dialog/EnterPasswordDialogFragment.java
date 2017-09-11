package com.wyxh.repast.user.view.dialog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.wyxh.repast.user.R;
import com.wyxh.repast.user.activity.ShoppingInformationActivity;
import com.wyxh.repast.user.activity.account.SecurityCenterActivty;
import com.wyxh.repast.user.activity.scan.PayMentActivity;
import com.wyxh.repast.user.activity.scan.PayResultActivity;
import com.wyxh.repast.user.bean.ResultDto;
import com.wyxh.repast.user.http.HttpUtil;
import com.wyxh.repast.user.interfaces.http.AbstractCallback;
import com.wyxh.repast.user.pay.repast.PayActivity;
import com.wyxh.repast.user.utils.FunctionUtill;
import com.wyxh.repast.user.utils.WyxhViewUtil;
import com.wyxh.repast.user.utils.db.DbUtils;
import com.wyxh.repast.user.view.dialog.CommonBaseDialog.INormalDialogSelect;
import com.wyxh.repast.user.wxapi.WXPayEntryActivity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EnterPasswordDialogFragment extends DialogFragment implements
		OnEditorActionListener ,OnClickListener{

	// protected EditDialogSelect mEditDialogSelect;

	// public interface EditDialogSelect {
	// int SELECT_NONE = -1; // 什么都没选
	// int SELECT_LEFT = 0; // 选择左边的按钮
	// int SELECT_RIGHT = 1; // 选择右边的按钮
	// void OnSelectBT(int selectid, String code);
	// }

	// public void SetNomalDialogwListener(EditDialogSelect listener) {
	// mEditDialogSelect = listener;
	// }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity=activity;
		mDialogListener = (DialogListener) activity;
	}
	

	public interface DialogListener {
		void showSetPasswordDialog();
		void removePasswordDialog();
	}

	private Activity mActivity;
	private EnterPasswordDialogFragment fm;
	private DialogListener mDialogListener;
	private CustomProgressDialog mLoadingDialog;
	private EditText mEditPayMent;
	private TextView mTextName;
	private TextView mTextMoney;
	private TextView mTextXinbi;
	private String storeName;
	private String payMoney;
	private String store_id;
	private BigDecimal bd;
	private InputMethodManager inputManager;
	private Button mButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cust_enter_fragment_dialog,
				container);
		mLoadingDialog = CustomProgressDialog.createDialog(getActivity());
		mEditPayMent = (EditText) view
				.findViewById(R.id.cust_enter_fragment_dialog_payment);
		mTextName = (TextView) view
				.findViewById(R.id.cust_enter_fragment_dialog_name);
		mTextMoney = (TextView) view
				.findViewById(R.id.cust_enter_fragment_dialog_money);
		mTextXinbi = (TextView) view
				.findViewById(R.id.cust_enter_fragment_dialog_xinbi);
		mButton = (Button) view.findViewById(R.id.cust_enter_fragment_dialog_btn);
		mButton.setOnClickListener(this);
		Bundle bundle = getArguments();
		if (bundle != null) {
			storeName = bundle.getString("storeName");
			payMoney = bundle.getString("payMoney");
			store_id = bundle.getString("store_id");
			  bd = new BigDecimal(Double.parseDouble(payMoney)+"");
			 bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			mTextName.setText("向" + storeName + "付款");
			mTextMoney.setText("￥" + bd);
		}
		initData();
		getDialog().setTitle(null);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));// 去掉黑边
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		mEditPayMent.requestFocus(); // EditText获得焦点
//		mEditPayMent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {  
//	          @Override  
//	          public void onGlobalLayout() {  
//	              InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);  
//	              manager.showSoftInput(mEditPayMent, 0);  
//	          }  
//	    });  
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE); // 显示软键盘
		mEditPayMent.setInputType(InputType.TYPE_CLASS_NUMBER); // 调用数字键盘
		mEditPayMent.setOnEditorActionListener(this); // 设置Action监听器
		mEditPayMent.setTransformationMethod(PasswordTransformationMethod.getInstance());
		mEditPayMent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mButton.setClickable(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				mButton.setClickable(false);
			}

			@Override
			public void afterTextChanged(Editable s) {
				mButton.setClickable(true);
				mButton.setTextColor(Color.WHITE);
				mButton.setBackgroundResource(R.drawable.wyxh_button_select);
			}
		});
		return view;
	}

	// @Override
	// public void onClick(View v) {
	//
	// int selectid = EditDialogSelect.SELECT_NONE;
	// switch (v.getId()) {
	// case R.id.cust_dialog_notify_button1:
	// selectid = EditDialogSelect.SELECT_LEFT;
	// break;
	// case R.id.cust_dialog_notify_button2:
	// selectid = EditDialogSelect.SELECT_RIGHT;
	// break;
	// default:
	// break;
	// }

	//
	// if (EditDialogSelect.SELECT_NONE != selectid) {
	// if (mEditDialogSelect != null) {
	// mEditDialogSelect.OnSelectBT(selectid, getEditTextCode());
	// }
	// }
	// this.dismiss();

	// }

	protected void checkPassWord() {
		mLoadingDialog.show();
		HashMap<String, String> mSendparam = new HashMap<String, String>();
		mSendparam.put("key", DbUtils.getUser().getAccessToken());
		mSendparam.put("payment_password", mEditPayMent.getText().toString()
				.trim());
		HttpUtil.remoteRequestObj(HttpUtil.SEND_CHECK_PASSWORD, mSendparam,
				new AbstractCallback<ResultDto>() {
					@Override
					public void execute(ResultDto result) {
						mLoadingDialog.dismiss();
						if (result.getSuccess() == 1) {
							sendBuyGoods();
						} else if (result.getMsg().equals("密码错误"))  {
							// mDialogListener.removePasswordDialog();
							EnterPasswordDialogFragment.this.dismiss();
							mDialogListener.showSetPasswordDialog();

						}else{
							FunctionUtill.ShowMyToast(mActivity,
									result.getMsg());
							EnterPasswordDialogFragment.this.dismiss();
						}
					}
				});
	}

	protected void sendBuyGoods() {
		mLoadingDialog.show();
		HashMap<String, String> mSendparam = new HashMap<String, String>();
		mSendparam.put("key", DbUtils.getUser().getAccessToken());
		mSendparam.put("store_id", store_id);// 商户ID
		mSendparam.put("money", bd+"");// 金额
		mSendparam.put("password", mEditPayMent.getText().toString());// 密码

		HttpUtil.remoteRequestObj(HttpUtil.SCAN_PAY_RESULT_URL, mSendparam,
				new AbstractCallback<ResultDto>() {
					@Override
					public void execute(ResultDto result) {
						mLoadingDialog.dismiss();
						Log.e("tag",result+"" );
						if ("".equals(result.getMsg())) {
//							Log.e("tag", result.getObj().toString());
							Intent intent = new Intent(mActivity,PayActivity.class);
							Bundle bd = new Bundle();
							bd.putBoolean(PayActivity.INTENT_PAT_STATE, true);
							// bd.putString("paySn", "paySn");
							// bd.putInt("result", 1);
							intent.putExtras(bd);
							startActivity(intent);
							mActivity.finish();
						}else {
							Log.e("tag","失败！！！！" );
							FunctionUtill.ShowMyToast(mActivity,
									result.getMsg());
							EnterPasswordDialogFragment.this.dismiss();

						}
					}
				});
	}

	/**
	 * 是否修改支付密码
	 */
	public void changeIsPassWord(final Activity activity) {
		mLoadingDialog.show();
		HashMap<String, String> mSendparam = new HashMap<String, String>();
		mSendparam.put("key", DbUtils.getUser().getAccessToken());
		mSendparam.put("password", mEditPayMent.getText().toString());// 密码

		HttpUtil.remoteRequestObj(HttpUtil.SEND_IS_CHANGE_PASSWORD, mSendparam,
				new AbstractCallback<ResultDto>() {
					@Override
					public void execute(ResultDto result) {
						mLoadingDialog.dismiss();
						if (result.getSuccess() == 1) {
							Map<String, String> map = new HashMap<String, String>();
							map.put("change_type", "setting_pay_password");
							WyxhViewUtil.jumpParamViewMap(activity,
									SecurityCenterActivty.class, map);
						} else {
							// Map<String, String> map = new HashMap<String,
							// String>();
							// map.put("change_type", "setting_pay_password");
							// WyxhViewUtil.jumpParamViewMap(getActivity(),SecurityCenterActivty.class,
							// map);
							FunctionUtill.ShowMyToast(activity, "请设置支付密码");
						}
					}
				});
	}

	private void initData() {
		HashMap<String, String> mSendparam = new HashMap<String, String>();
		mSendparam.put("key", DbUtils.getInstance(null).getAccessToken());
		HttpUtil.remoteRequest(HttpUtil.GET_USER_INFORMATION_URL, mSendparam,
				new AbstractCallback<ResultDto>() {
					@Override
					public void execute(ResultDto result) {
						if ("".equals(result.getMsg())) {
							JSONObject ret = result.getObj();

							try {
								JSONObject datas = ret.getJSONObject("datas");
								JSONObject member_info = datas
										.getJSONObject("member_info");
								String vMoney = member_info
										.getString("member_v_prestore");
								String money = member_info.getString("point");
								mTextXinbi.setText("通用鑫币" + money + "，专项鑫币"
										+ vMoney);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				});
		
	}

	public String getEditTextCode() {
		if (mEditPayMent == null)
			return "";
		return mEditPayMent.getText().toString();
	}

	public void setEditTextHint(String str) {
		mEditPayMent.setHint(str);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cust_enter_fragment_dialog_btn:
			checkPassWord();
			break;

		default:
			break;
		}
	}

	// @Override
	// public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	// if (EditorInfo.IME_ACTION_DONE == actionId) {
	// // Return input text to activity
	// EditNameDialogListener activity = (EditNameDialogListener) getActivity();
	// activity.onFinishEditDialog(mEditPayMent.getText().toString());
	// this.dismiss();
	// return true;
	// }
	// return false;
	// }

	// @Override
	// public void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// (new Handler()).postDelayed(new Runnable() {
	// public void run() {
	// InputMethodManager inManager = (InputMethodManager) mEditPayMent
	// .getContext().getSystemService(
	// Context.INPUT_METHOD_SERVICE);
	// inManager
	// .toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	// }
	// }, 500);
	// }

}
