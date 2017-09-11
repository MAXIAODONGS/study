package com.wyxh.repast.user.view.webview;

import com.wyxh.repast.user.activity.web.WebActivity;
import android.content.Context;
import android.webkit.JavascriptInterface;

public class JavaScriptObject {
    Context mContext;
	OnSelectEventListener rootActivity;

    public interface OnSelectEventListener{
		public void selectEventType(String type, String name);
	}
    
    public JavaScriptObject(Context mContxt) {
        this.mContext = mContxt;
		if (mContxt instanceof WebActivity) {
		this.rootActivity = (WebActivity) mContxt;
		}
    }

	@JavascriptInterface
	public void openLoginActivity(){
	}
	
	@JavascriptInterface
	public void openRegisterActivity(){
	}
	
	@JavascriptInterface
	public void selectType(String type, String name){
		rootActivity.selectEventType(type, name);
//		Intent intent = new Intent(mContext, MainActivity.class);
//		mContext.startActivity(intent);
	}
	
//	@JavascriptInterface
//	public String getUserInfo(){
//		JSONObject jsonObject = new JSONObject();
//		try {
//			jsonObject.put("tokenId", "123");
//			jsonObject.put("userName", "zhaoshilu");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	   String json = jsonObject.toString();
//		return json;
//	}
//	@JavascriptInterface
//	public void sendUrl(String urlJsons){
//		JSONObject jsonObject;
//		try {
//			jsonObject = new JSONObject(urlJsons);
//			String url = jsonObject.getString("url");
//			Toast.makeText(mContext, url, 2000).show();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//		
//	}
	
    
}
