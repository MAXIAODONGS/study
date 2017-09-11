package com.wyxh.repast.user.view.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wyxh.repast.user.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MorePopWindow extends PopupWindow {
	private View conentView;
	
	private Context context;
	
	private TextView mUserFeedback;
	/**
	 * 初始化Listview
	 */
	private Map<Integer, Boolean> isSelected;  
    
    private List<String> beSelectedData = new ArrayList<String>();    
      
    private ListAdapter adapter;  
      
    private List<String> cs = null;  
    
    private ListView listView;
    
    private LayoutInflater inflater;
    
    private PopDismissListener popdismisslistener = null; 
    
    private PopOnClickListener popclicklistener = null;  
    
    public interface PopDismissListener {
		public void ondismiss();	//添加菜单
	}
    
    public interface PopOnClickListener {
    	public void onclickCommit();	//点击
    }
    
    public MorePopWindow(){
    }
    
	public MorePopWindow(Activity context, boolean isFeedBack,
			PopOnClickListener popclicklistener) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.popclicklistener = popclicklistener;
		conentView = inflater.inflate(R.layout.cust_dialog_listview_layout,
				null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(conentView);
		setShowParam(context);
		initView();
		initListFooter();
		
		if (isFeedBack) {
			listView.setVisibility(View.GONE);
			mUserFeedback.setVisibility(View.VISIBLE);
		}else {
			listView.setVisibility(View.VISIBLE);
			mUserFeedback.setVisibility(View.GONE);
		}
	}

	public void setShowParam(Activity context){
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.AnimationPreview);
		
		LinearLayout pop_layout = (LinearLayout) conentView.findViewById(R.id.pop_layout);
		pop_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				popdismisslistener.ondismiss();
				MorePopWindow.this.dismiss();
			}
		});
	}
	
	private EditText user_feedback_edt;
	private Button user_feedback_commit_btn;
	
	/**
	 * 初始化底部布局
	 */
	private void initListFooter(){
		listView.addFooterView(inflater.inflate(R.layout.cust_dialog_listview_footer_view, null));
		user_feedback_edt = (EditText) conentView.findViewById(R.id.user_feedback_edt);
		user_feedback_commit_btn = (Button) conentView.findViewById(R.id.user_feedback_commit_btn);
		user_feedback_edt.setVisibility(View.GONE);
		
		//设置EditText的显示方式为多行文本输入  
		user_feedback_edt.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);  
		//文本显示的位置在EditText的最上方  
		user_feedback_edt.setGravity(Gravity.TOP);  
		//改变默认的单行模式  
		user_feedback_edt.setSingleLine(false);  
		//水平滚动设置为False  
		user_feedback_edt.setHorizontallyScrolling(false); 
		
		
		user_feedback_commit_btn.setVisibility(View.VISIBLE);
		user_feedback_commit_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popclicklistener.onclickCommit();
			}
		});
	}
	
	public void initView() {
		mUserFeedback = (TextView) conentView.findViewById(R.id.feedback_content);
		mUserFeedback.setVisibility(View.VISIBLE);
		listView = (ListView) conentView.findViewById(R.id.popwindow_listview);
		
		cs = new ArrayList<String>();
		cs.add("未收到货");
		cs.add("其他");
		initList();
		setItemListener();
	}

	@SuppressLint("UseSparseArrays")
	void initList() {
		if (cs == null || cs.size() == 0)
			return;
		if (isSelected != null)
			isSelected = null;
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < cs.size(); i++) {
			isSelected.put(i, false);
		}
		// 清除已经选择的项
		if (beSelectedData.size() > 0) {
			beSelectedData.clear();
		}
	}

	void setItemListener() {
		adapter = new ListAdapter(cs);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter.notifyDataSetChanged();
	}
    
	public int mPosition = 0;
	class ListAdapter extends BaseAdapter {

		private List<String> cs;

		private LayoutInflater inflater;

		public ListAdapter(List<String> data) {
			this.cs = data;
			initLayoutInflater();
		}

		void initLayoutInflater() {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return cs.size();
		}

		public Object getItem(int position) {
			return cs.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		ViewHolder holder = null;
		
		public View getView(int position1, View convertView, ViewGroup parent) {
			
			View view = null;
			final int position = position1;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.cust_dialog_listview_item, null);
				holder = new ViewHolder();
				holder.check = (CheckBox) convertView.findViewById(R.id.user_feedback_check);
				holder.name = (TextView) convertView.findViewById(R.id.user_feedback_event);
				
				holder.edt = (EditText) convertView.findViewById(R.id.user_feedback_edt);
				holder.btn = (Button) convertView.findViewById(R.id.user_feedback_commit_btn);
				convertView.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.check.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 当前点击的CB
					boolean cu = !isSelected.get(position);
					// 先将所有的置为FALSE
					for (Integer p : isSelected.keySet()) {
						isSelected.put(p, false);
					}
					// 再将当前选择CB的实际状态
					isSelected.put(position, cu);
					ListAdapter.this.notifyDataSetChanged();
					beSelectedData.clear();
					if (cu)
						beSelectedData.add(cs.get(position));
				}
			});
			holder.name.setText(cs.get(position).toString());
			holder.check.setChecked(isSelected.get(position));
			
			holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean check) {
					if(!buttonView.isPressed())return;	
					if ("其他".equals(cs.get(position))) {
						if (check) {
							user_feedback_edt.setVisibility(View.VISIBLE);
						}else {
							user_feedback_edt.setVisibility(View.GONE);
						}
					}else {
						user_feedback_edt.setVisibility(View.GONE);
					}
				}
			});
			return convertView;
		}
	}  
	      
	    class ViewHolder {  
	    	private CheckBox check;  
	        private TextView name;  
	        private EditText edt;
	    	private Button btn;
	    }  
	
	    
	/**
	 * 设置投诉内容
	 * 
	 * @param str
	 */
	public void setFeedbackContent(String str) {
		mUserFeedback.setText("投诉内容 :" + str);
	}
	
	public String getFeedbackContent(){
		return mUserFeedback.getText().toString();
	}
	    
	/**
	 * show pop
	 * @param parent
	 */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			this.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//			this.showAsDropDown(parent, 0, 0);// // 在底部显示
//			this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
		} else {
			this.dismiss();
		}
	}
}
