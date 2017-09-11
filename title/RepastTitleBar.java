package com.wyxh.repast.user.view.title;

import com.wyxh.repast.user.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RepastTitleBar extends RelativeLayout implements OnClickListener{
	private Context mContext;
	private ViewGroup mBackButton;
	private ViewGroup mSearchButton;
	private EditText mSearchEdit;
	private ViewGroup mRightButton;
	
	private TextView mTitle;
	private ImageView mTitleLeft;
	private ImageView mTitleLeftSao;
	private TextView mTitleRight;
	private RelativeLayout mTitleRelativeLayout;
	
	private TitleBarListener mTitleListener = null; 
	
	public interface TitleBarListener{
		void onTitleBarListener(View v);
	}

	public RepastTitleBar(Context context) {
		super(context);
		init(context);
	}

	public RepastTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RepastTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@SuppressLint("InflateParams")
	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.repast_base_title, null);
		addView(view);
		mBackButton = (ViewGroup) view.findViewById(R.id.title_back_btn);
		mSearchButton = (ViewGroup) view.findViewById(R.id.title_search_btn);
		mRightButton = (ViewGroup) view.findViewById(R.id.title_right_btn);
		
		mTitleRelativeLayout = (RelativeLayout) view.findViewById(R.id.main_title);
		mTitle = (TextView) view.findViewById(R.id.title_text);
		mTitleLeft = (ImageView) view.findViewById(R.id.title_back_btn_s);
		mTitleLeftSao = (ImageView) view.findViewById(R.id.title_sao_btn);
		mTitleRight = (TextView) view.findViewById(R.id.title_right_tv);
		mSearchEdit = (EditText) view.findViewById(R.id.title_search_edt);
		
		mBackButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);
		hideSearchButton();
		hideBackButton();
		hideRightutton();
	}

	
	@Override
	public void onClick(View view) {
		if (mTitleListener != null) {
			mTitleListener.onTitleBarListener(view);
		}
	}
	public void showSaoImage(){
		mTitleLeftSao.setVisibility(VISIBLE);
		mTitleLeft.setVisibility(GONE);
	}
	public void showBackButton() {
		mBackButton.setVisibility(VISIBLE);
	}

	public void showSearchButton() {
		mSearchButton.setVisibility(VISIBLE);
	}
	
	public void showSearchEdit(){
		mSearchEdit.setVisibility(VISIBLE);
	}
	
		public void showRightButton() {
		mRightButton.setVisibility(VISIBLE);
	}
	
	public void setSearchEditHintText(String text){
		mSearchEdit.setHint(text);
	}
	
	public void setSearchEditText(String text){
		mSearchEdit.setText(text);
	}


	public void setBackButtonFocusable(int focusable) {
		if (focusable == 0) {
			mBackButton.setFocusable(true);
			mBackButton.setFocusableInTouchMode(true);
			mBackButton.setEnabled(true);
		} else {
			mBackButton.setFocusable(false);
			mBackButton.setFocusableInTouchMode(false);
			mBackButton.setEnabled(false);
		}

	}
	
	public void setSearchButtonFocusable(int focusable) {
		if (focusable == 0) {
			mSearchButton.setFocusable(true);
			mSearchButton.setFocusableInTouchMode(true);
			mSearchButton.setEnabled(true);
		} else {
			mSearchButton.setFocusable(false);
			mSearchButton.setFocusableInTouchMode(false);
			mSearchButton.setEnabled(false);
		}

	}

	public void setBackButtonClickable(boolean clickable) {
		mBackButton.setClickable(clickable);
	}

	public void setSearchButtonClickable(boolean clickable) {
		mSearchButton.setClickable(clickable);
	}
	
	public void setRightButtonClickable(boolean clickable) {
		mRightButton.setClickable(clickable);
	}
	
	public void hideBackButton() {
		mBackButton.setVisibility(GONE);
	}

	public void hideSearchButton() {
		mSearchButton.setVisibility(GONE);
	}
	
	public void hideSearchEdit(){
		mSearchEdit.setVisibility(GONE);
	}
	
	public void hideRightutton() {
		mRightButton.setVisibility(GONE);
	}
	
	public void setRightTitleVisible(int visible){
		mRightButton.setVisibility(visible);
	}
	
	public void setBackButtonOnClickListener(OnClickListener listener) {
		mBackButton.setVisibility(VISIBLE);
		mBackButton.setOnClickListener(listener);
	}

	public void setSearchButtonOnClickListener(OnClickListener listener) {
//		mSearchButton.setVisibility(VISIBLE);
		mSearchButton.setOnClickListener(listener);
	}
	
	public void setTitleButtonOnclickListener(TitleBarListener listener){
		this.mTitleListener = listener;
	}
	
	public void setRightButtonOnClickListener(OnClickListener listener) {
		mRightButton.setVisibility(VISIBLE);
		mRightButton.setOnClickListener(listener);
	}
	
	public void setTitle(String title) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(title);
	}
	
	public void setRightTitle(String title) {
		mTitleRight.setVisibility(View.VISIBLE);
		mTitleRight.setText(title);
	}

	public void setTitleBackground(int id) {
		mTitleRelativeLayout.setBackgroundColor(getResources().getColor(id));
		mTitle.setTextColor(getResources().getColor(R.color.bg_black));
	}
	public void setSaoMiaoTitleBackground(int id) {
		mTitleRelativeLayout.setBackgroundColor(getResources().getColor(id));
	}

	public void setLeftButtonBackground(int id) {
		mTitleLeft.setBackgroundResource(id);
	}
	
	public String getSearchEditText(){
		return mSearchEdit.getText().toString();
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
