package com.wyxh.repast.user.view.edit;

import java.util.ArrayList;
import com.wyxh.repast.user.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("NewApi")
/**
 * 下拉列表框控件
 * @author caizhiming
 *
 */
public class XCDropDownListView extends LinearLayout{

	public XCoClickListener mListener;
	
	public interface XCoClickListener {
		void XConClick(View v);
	}
	
	public void setXCoClickListener(XCoClickListener mListener){
		this.mListener = mListener;
	}
	
	private TextView editText;
	private ImageView imageView;
	private PopupWindow popupWindow = null;
	private ArrayList<String> dataList =  new ArrayList<String>();
	private ArrayList<String> dataKeyList =  new ArrayList<String>();
	private View mView;
	public XCDropDownListView(Context context) {
		this(context,null);
	}
	public XCDropDownListView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public XCDropDownListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	public void initView(){
		String infServie = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater layoutInflater;
		layoutInflater =  (LayoutInflater) getContext().getSystemService(infServie);
		mView = layoutInflater.inflate(R.layout.dropdownlist_view, this,true);
		editText= (TextView)mView.findViewById(R.id.text);
		imageView = (ImageView)mView.findViewById(R.id.btn);
//		imageView.setVisibility(View.INVISIBLE);
//		compound = (RelativeLayout)view.findViewById(R.id.compound);
		mView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) mListener.XConClick(v);
				if (dataList == null || dataList.size() == 0) return;
				if(popupWindow == null ){
					showPopWindow();
				}else{
					closePopWindow();
				}
			}
		});
	}
	
	public void setViewClickable(boolean b){
		mView.setClickable(b);
	}
	
	
	/**
	 * 打开下拉列表弹窗
	 */
	@SuppressLint("InflateParams")
	private void showPopWindow() {  
        // 加载popupWindow的布局文件  
		String infServie = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater layoutInflater;
		layoutInflater =  (LayoutInflater) getContext().getSystemService(infServie);
		View contentView  = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null,false);
		ListView listView = (ListView)contentView.findViewById(R.id.listView);
		
		listView.setAdapter(new XCDropDownListAdapter(getContext(), dataList, dataKeyList));
		popupWindow = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(this);
    }
	/**
	 * 关闭下拉列表弹窗
	 */
	public void closePopWindow(){
		if (popupWindow != null) {
			 popupWindow.dismiss();
			    popupWindow = null;
		}
	}
	
	
	
	public boolean isItemsData(){
		if (dataList.size() == 0) return false;
		return true;
	}
	
	/**
	 * 设置数据
	 * @param list
	 */
	public void setItemsData(ArrayList<String> keylist, ArrayList<String> list){
		dataKeyList = keylist;
		dataList = list;
//		editText.setText("11"/*list.get(0).toString()*/);
	}
	
	public String getItemsData(String key){
		for (int i = 0; i < dataKeyList.size(); i++) {
			if (dataKeyList.get(i).equals(key)) {
				return dataList.get(i);
			}
		}
		return "";
	}

	public void setItemsDataValue(ArrayList<String> list){
		dataList = list;
	}
	
	
	public void clearData(){
		dataKeyList.clear();
		dataList.clear();
	}
	
	
	public void setEditText(String str){
		editText.setText(str);
		if ("".equals(str)) {
			closePopWindow();
		}
	}
	
	public String getEditText(){
		return editText.getText().toString();
	}
	
	public String getEditTag(){
		if (editText.getTag() != null) {
			return editText.getTag().toString();
		}
		return "";
	}
	
	public void setDropIconVisible(int visible){
		imageView.setVisibility(visible);
	}
	
	/**
	 * 数据适配器
	 * @author caizhiming
	 *
	 */
	class XCDropDownListAdapter extends BaseAdapter{

		Context mContext;
		ArrayList<String> mData;
		ArrayList<String> mDataKeyList;
		LayoutInflater inflater;
		public XCDropDownListAdapter(Context ctx, ArrayList<String> data, ArrayList<String> dataKeyList){
			mContext  = ctx;
			mData = data;
			mDataKeyList = dataKeyList;
			inflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// 自定义视图
			ListItemView listItemView = null;
			if (convertView == null) {
				// 获取list_item布局文件的视图
				convertView = inflater.inflate(R.layout.dropdown_list_item, null);
				
				listItemView = new ListItemView();
				// 获取控件对象
				listItemView.tv = (TextView) convertView
						.findViewById(R.id.tv);

				listItemView.layout = (LinearLayout) convertView.findViewById(R.id.layout_container);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			
			// 设置数据
			listItemView.tv.setText(mData.get(position).toString());
			final String text = mData.get(position).toString();
			
			listItemView.layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mDataKeyList.size()>0 && mDataKeyList != null) {
						editText.setTag(mDataKeyList.get(position).toString());
					}
					editText.setText(text);
					closePopWindow();
				}
			});
			return convertView;
		}
	
	}
	private static class ListItemView{
		TextView tv;
		LinearLayout layout;
	}

}
