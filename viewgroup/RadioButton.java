package com.wyxh.repast.user.view.viewgroup;

import com.wyxh.repast.user.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义的单选控件
 * 
 * @ClassName: RadioButton
 * 
 */
public class RadioButton extends RelativeLayout {
	// 显示文字的TextView
	private TextView tv_text;
	private ImageView mImageView;
	private boolean checked = false;// 设置是否选中,默认未false未选中
	// 声明数组保存背景颜色,其中int[0]--未选中时的背景颜色,int[1]选中时的背景颜色
	private int[] mTextViewBackg = new int[2];
	// 选中状态发生改变时的接口调用
	private OnCheckedChangedListener onCheckedChangedListener;
	private int id;//控件的id

	private Context mContext;
	public RadioButton(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * 自己写的自定义控件必须实现这个构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	@SuppressLint("InflateParams") public RadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 设置TextView的宽高为自适应
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		// 设置TextView显示在中间
//		params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		// 创建显示文字的TextView控件
//		tv_text = new TextView(context);
//		// 为TextView添加Params
//		tv_text.setLayoutParams(params);

		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.repast_base_radiobutton, null);
		tv_text = (TextView) view.findViewById(R.id.wyxh_regest_textview);
		mImageView = (ImageView) view.findViewById(R.id.wyxh_regest_imageview);

		addView(view);
//		addView(tv_text);// 添加文本控件到View

		// 引入自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyRadioButton);
		// 获取xml布局中设置的值
		// 获取属性id
		int resourceId = ta.getResourceId(R.styleable.MyRadioButton_radio_text, 0);
		// 根据判断是属性id,还是直接设置的属性值,设置显示的内容
		if (resourceId > 0) {// 设置的属性id(reference)
			tv_text.setText(ta.getResources().getString(resourceId));
		} else {// 文本内容
			tv_text.setText(ta.getString(R.styleable.MyRadioButton_radio_text));
		}
		// 设置字体大小
		tv_text.setTextSize(ta.getFloat(R.styleable.MyRadioButton_radio_textSize, 16));
		// 设置字体颜色
		tv_text.setTextColor(ta.getColor(R.styleable.MyRadioButton_radio_textColor, R.color.font_white));
		
		//获取控件的id属性id
		id = ta.getResourceId(R.styleable.MyRadioButton_radio_id, -1);
		//设置控件id
		if(id != -1){
			this.setId(id);
		}
		// 设置是否选中
		checked = ta.getBoolean(R.styleable.MyRadioButton_radio_checked, false);
		// 获取未选中时的背景
		mTextViewBackg[0] = ta.getResourceId(R.styleable.MyRadioButton_radio_unCheckedBg, R.color.transparent);
		// 获取选中时的背景
		mTextViewBackg[1] = ta.getResourceId(R.styleable.MyRadioButton_radio_checkedBg, R.color.transparent);
		// 根据选中状况设置背景颜色
		updateBg();

		// 设置当前控件的点击事件
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 更改选中状态
				checked = !checked;
				// 更改背景颜色
				updateBg();
				if (onCheckedChangedListener != null) {
					// 调用接口的选中状态改变事件
					onCheckedChangedListener.onCheckedChaged(id,checked);
				}
			}
		});
		
		ta.recycle();//回收资源
	}

	public RadioButton(Context context, AttributeSet attrs, int theme) {
		super(context, attrs, theme);
	}

	/**
	 * 设置TextView显示的文字
	 * 
	 * @param text
	 *            显示的文字
	 */
	public void setText(String text) {
		tv_text.setText(text);
	}

	/**
	 * 设置文本颜色
	 * 
	 * @param resource
	 */
	public void setTextColor(int resource) {
		tv_text.setTextColor(resource);
	}

	/**
	 * 设置文本字体大小
	 * 
	 * @param size
	 */
	public void setTextSize(float size) {
		tv_text.setTextSize(size);
	}

	/**
	 * 设置选中状态
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
		//更改背景色
		updateBg();
	}

	/**
	 * 设置选中时的背景颜色
	 * 
	 * @param bgId
	 */
	public void setCheckedBg(int bgId) {
		mTextViewBackg[1] = bgId;
	}

	/**
	 * 设置未选中时的背景
	 * 
	 * @param bgId
	 */
	public void setUnCheckedBg(int bgId) {
		mTextViewBackg[0] = bgId;
	}

	/* 更改背景颜色 */
	public void updateBg() {
		// 根据选中状况设置背景颜色
		if (checked) {
			this.setBackgroundResource(mTextViewBackg[1]);// 选中时的背景
			mImageView.setVisibility(View.VISIBLE);
			// 设置字体颜色
			tv_text.setTextColor(mContext.getResources().getColor(R.color.bg_black));//font_white
		} else {
			this.setBackgroundResource(mTextViewBackg[0]);// 未选中时的背景
			mImageView.setVisibility(View.INVISIBLE);
			tv_text.setTextColor(mContext.getResources().getColor(R.color.bg_black));
		}
	}

	/**
	 * 设置背景颜色,注意数组中的下标0表示未选中时的背景 下标1表示选中时的背景
	 * 
	 * @param bgs
	 */
	public void setBg(int[] bgs) {
		// 数组长度2位,不能少于2位,多余的也只取,数组中的前2位
		if (bgs.length >= 2) {
			this.mTextViewBackg[0] = bgs[0];
			this.mTextViewBackg[1] = bgs[1];
		}
	}

	/**
	 * 设置选中状态发生改变时的接口
	 * 
	 * @param onCheckedChangedListener
	 */
	public void setOnCheckChangedListener(
			OnCheckedChangedListener onCheckedChangedListener) {
		this.onCheckedChangedListener = onCheckedChangedListener;
	}

	/**
	 * 选中状态发生改变时的处理
	 * 
	 * @ClassName: setOnCheckedChangedListener
	 * @author haoran.shu
	 * @date 2014年6月4日 下午12:00:03
	 * @version 1.0
	 * 
	 */
	public interface OnCheckedChangedListener {
		/**
		 * 选中状态发生改变时调用
		 */
		void onCheckedChaged(int id, boolean checked);
	}
}
