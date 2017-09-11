package com.wyxh.repast.user.view.viewgroup;

import com.wyxh.repast.user.R;
import com.wyxh.repast.user.utils.StringUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RepastBaseLayout extends RelativeLayout{
	
	 /** 
     * 一定一个接口 
     */  
    public interface ICoallBack{  
        public void onClickButton(String name);  
    }  
      
    /** 
     * 初始化接口变量 
     */  
    ICoallBack icallBack = null;  
      
    /** 
     * 自定义控件的自定义事件 
     * @param iBack 接口类型 
     */  
    public void setonClick(ICoallBack iBack)  
    {  
    	setFocusableLayout(true);
        icallBack = iBack;  
    }  
    
	private Context mContext;
	private RelativeLayout mBaseLayout;
	private TextView mDetailstv;
	private TextView mContentTv;
	private TextView mNumbertv;
	private ImageView mIcon;
	private int id;//控件的id
	
	public RepastBaseLayout(Context context) {
		super(context);
		initView(context, null);
	}

	public RepastBaseLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public RepastBaseLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	@SuppressLint("InflateParams")
	private void initView(Context context, AttributeSet attrs) {
		mContext = context;
		int resourceId = -1;
	    TypedArray typedArray = context.obtainStyledAttributes(attrs,
	            R.styleable.MyRepastBaseLayout);
	    
		// 获取控件的id属性id
		id = typedArray.getResourceId(R.styleable.MyRepastBaseLayout_layoutID, -1);
		// 设置控件id
		if (id != -1) {
			this.setId(id);
		}
	 		
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.repast_base_layout, null);
		mBaseLayout = (RelativeLayout) view.findViewById(R.id.repast_base_relativelayout);
		mIcon = (ImageView)view.findViewById(R.id.repast_base_icon);
		mDetailstv = (TextView)view.findViewById(R.id.repast_base_details_tv);
		mContentTv = (TextView)view.findViewById(R.id.repast_base_content);
		mNumbertv = (TextView)view.findViewById(R.id.repast_base_number_tv);
		
	    int N = typedArray.getIndexCount();
	    for (int i = 0; i < N; i++) {
	        int attr = typedArray.getIndex(i);
	        switch (attr) {
	        case R.styleable.MyRepastBaseLayout_detailsText:
	            resourceId = typedArray.getResourceId(
	                    R.styleable.MyRepastBaseLayout_detailsText, 0);
	            mDetailstv.setText(resourceId > 0 ? typedArray.getResources().getText(
	                    resourceId) : typedArray
	                    .getString(R.styleable.MyRepastBaseLayout_detailsText));
	            break;
	        case R.styleable.MyRepastBaseLayout_numberText:
	        	resourceId = typedArray.getResourceId(
	                    R.styleable.MyRepastBaseLayout_numberText, 0);
	        	mNumbertv.setText(resourceId > 0 ? typedArray.getResources().getText(
	                    resourceId) : typedArray
	                    .getString(R.styleable.MyRepastBaseLayout_numberText));
	            break;
	        case R.styleable.MyRepastBaseLayout_iconSrc:
	            resourceId = typedArray.getResourceId(
	                    R.styleable.MyRepastBaseLayout_iconSrc, 0);
	            mIcon.setImageResource(resourceId > 0 ?resourceId:R.drawable.ic_launcher);
	            mIcon.setVisibility(View.VISIBLE);
	            break;   
	        case R.styleable.MyRepastBaseLayout_layoutBackground:
	        	resourceId = typedArray.getResourceId(
	        			R.styleable.MyRepastBaseLayout_layoutBackground, 0);
	        	mBaseLayout.setBackgroundResource(resourceId > 0 ?resourceId:R.drawable.ic_launcher);
	        	break;   
	        case R.styleable.MyRepastBaseLayout_layoutID:
	        	resourceId = typedArray.getResourceId(
	        			R.styleable.MyRepastBaseLayout_layoutID, 0);
	        	break;   
	        }
	    }
	    
	    mBaseLayout.setFocusable(true);
	    mBaseLayout.setOnClickListener(new OnClickListener() {  
            
            @Override  
            public void onClick(View v) {  
                // 返回这个自定义控件中计算出的值，使用回调实现  
            	if (icallBack == null) return;
                icallBack.onClickButton(mDetailstv.getText().toString());  
            }  
        });  
	    
	    addView(view);
	    typedArray.recycle();
	}
	
	public void setBackButtonOnClickListener(OnClickListener listener) {
		mBaseLayout.setVisibility(VISIBLE);
		mBaseLayout.setOnClickListener(listener);
	}
	
	public void setLayoutOnClickListener(OnClickListener onclick){
		mBaseLayout.setVisibility(VISIBLE);
		mBaseLayout.setOnClickListener(onclick);
	}
	
	public void setFocusableLayout(boolean b){
		mBaseLayout.setFocusable(b);
	}
	
	public void setClickableLayout(boolean b){
		mBaseLayout.setClickable(b);
	}
	
	public void setNumberText(String text){
		mNumbertv.setText(text);
	}

	public String getNumberText(){
		return mNumbertv.getText().toString();
	}
	
	public void setContentText(String text){
		if (StringUtil.isBlank(text)) {
			mContentTv.setVisibility(View.GONE);
		}else {
			mContentTv.setVisibility(View.VISIBLE);
			mContentTv.setText(text);
		}
	}
	
	public String getContentText(){
		return mContentTv.getText().toString();
	}
	
	public void setNumberTextColor(int color){
		mNumbertv.setTextColor(getResources().getColor(color));
	}

	
}
