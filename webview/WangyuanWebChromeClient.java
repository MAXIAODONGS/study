package com.wyxh.repast.user.view.webview;

import com.wyxh.repast.user.log.LogUtils;
import android.app.Activity;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WangyuanWebChromeClient extends WebChromeClient {
	
	Activity rootActivity;
	
	public WangyuanWebChromeClient() {
	}
	
	public WangyuanWebChromeClient(Activity rootActivity) {
		this.rootActivity = rootActivity;
	}
	
	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		LogUtils.d("WangyuanWebChromeClient.onConsoleMessage=>" + consoleMessage.message());
		return super.onConsoleMessage(consoleMessage);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
//		if (rootActivity != null) {
//			rootActivity.onReceivedTitle(title);
//		}
	}
	

}
