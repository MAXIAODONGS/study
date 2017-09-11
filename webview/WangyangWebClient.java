package com.wyxh.repast.user.view.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.util.LogUtils;
import com.wyxh.repast.user.http.GlobalSupport;

public class WangyangWebClient extends WebViewClient {
	
	Activity rootActivity;
	
	boolean isMainActivity;

	boolean loadFinish = false;

	public WangyangWebClient(Activity activity) {
		super();
		if (activity == null) return;
		this.rootActivity =  activity;
	}

	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		LogUtils.d("加载错误：" + description);
		   //用javascript隐藏系统定义的404页面信息  
	    String data = "Page NO FOUND！";  
	    view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");  
	}

	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		LogUtils.d("开始加载页面：" + url);
		if (rootActivity == null) return;
		if (loadFinish) {
			return;
		}
	}

	public void onPageFinished(WebView view, String url) {
		if (rootActivity == null) return;
		if (loadFinish) {
			return;
		}
		LogUtils.d("页面加载完毕：" + url);
		loadFinish = true;
		try {
			view.loadUrl("javascript:init('" + GlobalSupport.jsparam + "')");
		} catch (Exception e) {}
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
		super.onReceivedHttpAuthRequest(view, handler, host, realm);
	}

	@Override
	public void onReceivedSslError(final WebView view, SslErrorHandler handler, SslError error) {
		super.onReceivedSslError(view, handler, error);
		handler.proceed();
	}

}
