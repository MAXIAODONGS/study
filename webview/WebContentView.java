package com.wyxh.repast.user.view.webview;

import com.wyxh.repast.user.activity.MainActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebContentView extends WebView {
	
	private Context rootContext;
	
	private Activity rootActivity;
	
	public WebContentView(Context context) {
		super(context);
		initWebView(context);
	}

	public WebContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWebView(context);
	}

	public WebContentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWebView(context);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void initWebView(Context context) {
		if(context instanceof MainActivity){
			this.rootActivity = (MainActivity) context;
		}
		this.rootContext = context;
		setWebChromeClient(new WangyuanWebChromeClient(rootActivity));
		setWebViewClient(new WangyangWebClient(rootActivity));
		setInitialScale(100);
		setNetworkAvailable(true);
		setScrollContainer(true);
		setVerticalScrollBarEnabled(true);
//		addJavascriptInterface(this, "xdh");
		WebSettings settings = getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setSaveFormData(false);
		settings.setDomStorageEnabled(true);
		settings.setAllowContentAccess(true);
		settings.setAllowFileAccess(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	/**
	 * 跳转到登录界面
	 */
	@JavascriptInterface
	public void jump2Login() {
	}

	
}
