package com.wyxh.repast.user.view.dialog;

import com.wyxh.repast.user.R;
import com.wyxh.repast.user.utils.ui.UiUtils;
import com.wyxh.repast.user.view.title.RepastTitleBar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class AgreementDialog extends PopupWindow  {

	 	private View conentView;  
	 	
	 	public Activity context;
	 	RelativeLayout addTaskLayout;
	 	ImageView ImageView1;
	 	ImageView ImageView2;
	 	FrameLayout mFrameLayout;
	 	
	 	private RepastTitleBar mTitleBar;
	  
	    public void setTitle(String title){
	    	if (mTitleBar != null) {
	    		mTitleBar.setTitle(title);
			}
	    }
	    
	    /** 
	     * 显示popupWindow 
	     *  
	     * @param parent 
	     */  
	    public void showPopupWindow(View parent) {  
	        if (!this.isShowing()) {  
	            // 以下拉方式显示popupwindow  
	        	this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//	            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);  
	        } else {  
	            this.dismiss();  
	        }  
	    }  
	    
	    @SuppressLint("InflateParams")
		@SuppressWarnings("deprecation")
		public AgreementDialog(final Activity context) { 
	    	this.context = context;
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	        conentView = inflater.inflate(R.layout.cust_agreement_fragment_dialog, null);  
	        int h = context.getWindowManager().getDefaultDisplay().getHeight();  
	        int w = context.getWindowManager().getDefaultDisplay().getWidth(); 
//	        Display display = context.getWindowManager().getDefaultDisplay();
			int height = h - UiUtils.getStatusBarHeight(context);
	        // 设置SelectPicPopupWindow的View  
	        this.setContentView(conentView);  
	        // 设置SelectPicPopupWindow弹出窗体的宽  
	        this.setWidth(w/*LayoutParams.MATCH_PARENTw / 2 + 50*/);  
	        // 设置SelectPicPopupWindow弹出窗体的高  
	        this.setHeight(height);  
	        // 设置SelectPicPopupWindow弹出窗体可点击  
	        this.setFocusable(true);  
	        this.setOutsideTouchable(true);  
	        // 刷新状态  
	        this.update();  
	        // 实例化一个ColorDrawable颜色为半透明  
	        ColorDrawable dw = new ColorDrawable(0000000000);  
	        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
	        this.setBackgroundDrawable(dw);  
	        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
	        // 设置SelectPicPopupWindow弹出窗体动画效果  
	        this.setAnimationStyle(R.style.AnimBottom);  
	        addTaskLayout = (RelativeLayout) conentView.findViewById(R.id.agreement_dialog_layout);  
	        ImageView1= (ImageView) conentView.findViewById(R.id.ImageView1);  
	        ImageView2 = (ImageView) conentView.findViewById(R.id.ImageView2);  
	        mFrameLayout = (FrameLayout) conentView.findViewById(R.id.FrameLayout);  
	        mTitleBar = (RepastTitleBar) conentView
	        		.findViewById(R.id.main_title);
	        mTitleBar.setBackButtonOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AgreementDialog.this.dismiss();  
				}
			});
	        addTaskLayout.setOnClickListener(new OnClickListener() {  
	            @Override  
	            public void onClick(View v) {  
	            	AgreementDialog.this.dismiss();  
	            }  
	        });  
	        
	        animation_viewGroup = createAnimLayout();
	        ImageView1.setOnClickListener(new OnClickListener() {
					
				@Override
				public void onClick(View v) {
					int[] start_location = new int[2];
					ImageView1.getLocationInWindow(start_location);//获取点击商品图片的位置
                    Drawable drawable = ImageView1.getDrawable();//复制一个新的商品图标
                    doAnim(drawable,start_location);
				}
			});
	    }  
	    
	    //动画时间
	    private int AnimationDuration = 1000;
	    //正在执行的动画数量
	    private int number = 0;
	  //是否完成清理
	    private boolean isClean = false;
	    private FrameLayout animation_viewGroup;
	    private Handler myHandler = new Handler(){
	        public void handleMessage(Message msg){
	            switch(msg.what){
	            case 0:
	                //用来清除动画后留下的垃圾
	                try{
	                    animation_viewGroup.removeAllViews();
	                    }catch(Exception e){
	                    }
	                           
	                    isClean = false;
	                     
	                break;
	             default:
	                    break;
	            }
	        }
	      };
	      
	      /**
	       * @Description: 创建动画层
	       * @param
	       * @return void
	       * @throws
	       */
	      private FrameLayout createAnimLayout(){
//	          ViewGroup rootView = (ViewGroup)context.getWindow().getDecorView();
	          FrameLayout animLayout = new FrameLayout(context);
	          FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
	          animLayout.setLayoutParams(lp);
	          animLayout.setBackgroundResource(android.R.color.transparent);
	          mFrameLayout.addView(animLayout);
	          return animLayout;
	           
	      }
	      
	    
	    private void doAnim(Drawable drawable,int[] start_location){
	        if(!isClean){
	            setAnim(drawable,start_location);
	        }else{
	            try{
	              animation_viewGroup.removeAllViews();
	              isClean = false;
	              setAnim(drawable,start_location);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	            finally{
	                isClean = true;
	            }
	        }
	    }
	    
	    
	    /**
	     * @deprecated 将要执行动画的view 添加到动画层
	     * @param vg
	     *        动画运行的层 这里是frameLayout
	     * @param view
	     *        要运行动画的View
	     * @param location
	     *        动画的起始位置
	     * @return
	     */
	    private View addViewToAnimLayout(ViewGroup vg,View view,int[] location){
	        int x = location[0];
	        int y = location[1];
	        vg.addView(view);
	        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
	                dip2px(context,90),dip2px(context,90));
	        lp.leftMargin = x;
	        lp.topMargin = y;
	        view.setPadding(5, 5, 5, 5);
	        view.setLayoutParams(lp);
	         
	        return view;
	    }
	    
	    /**
	     * dip，dp转化成px 用来处理不同分辨路的屏幕
	     * @param context
	     * @param dpValue
	     * @return
	     */
	    private int dip2px(Context context,float dpValue){
	        float scale = context.getResources().getDisplayMetrics().density;
	        return (int)(dpValue*scale +0.5f);
	    }
	    
	    /**
	     * 动画效果设置
	     * @param drawable
	     *       将要加入购物车的商品
	     * @param start_location
	     *        起始位置
	     */
	    private void setAnim(Drawable drawable,int[] start_location){
	        Animation mScaleAnimation = new ScaleAnimation(1.5f,0.0f,1.5f,0.0f,Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,0.1f);
	        mScaleAnimation.setDuration(AnimationDuration);
	        mScaleAnimation.setFillAfter(true);
	  
	        final ImageView iview = new ImageView(context);
	        iview.setImageDrawable(drawable);
	        final View view = addViewToAnimLayout(animation_viewGroup,iview,start_location);
	        view.setAlpha(0.6f);
	         
	        int[] end_location = new int[2];
	        ImageView2.getLocationInWindow(end_location);
	        int endX = end_location[0];
	        int endY = end_location[1]-start_location[1];
	         
	        Animation mTranslateAnimation = new TranslateAnimation(0,endX,0,endY);
	        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	        mRotateAnimation.setDuration(AnimationDuration);
	        mTranslateAnimation.setDuration(AnimationDuration);
	        AnimationSet mAnimationSet = new AnimationSet(true);
	  
	        mAnimationSet.setFillAfter(true);
	        mAnimationSet.addAnimation(mRotateAnimation);
	        mAnimationSet.addAnimation(mScaleAnimation);
	        mAnimationSet.addAnimation(mTranslateAnimation);
	         
	        mAnimationSet.setAnimationListener(new AnimationListener(){
	          
	         @Override
	         public void onAnimationStart(Animation animation) {
	             number++;
	         }
	  
	         @Override
	         public void onAnimationEnd(Animation animation) {
	            
	             number--;
	             if(number==0){
	                 isClean = true;
	                 myHandler.sendEmptyMessage(0);
	             }
	              
	         }
	  
	         @Override
	         public void onAnimationRepeat(Animation animation) {
	              
	         }
	             
	        });
	        view.startAnimation(mAnimationSet);
	        
	    }
}
