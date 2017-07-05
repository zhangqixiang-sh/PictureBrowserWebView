package com.zqx.picturebrowserwebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * Created by Zhang Qixiang on 2017/7/5.
 *
 * 点击图片可得到该页面所有图片的url的集合,以及当前点击图片在集合中的index
 * 用于ViewPager展示
 */

public class PictureBrowserWebview extends WebView {

    private static final String TAG = "PictureBrowserWebview";
    private ArrayList<String> mUrls = new ArrayList<>();

    public PictureBrowserWebview(Context context) {
        this(context, null);
    }

    public PictureBrowserWebview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureBrowserWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private OnClickImageListener mOnClickImageListener;

    public interface OnClickImageListener {
        void onClickImage(ArrayList<String> urlList, int index);
    }

    public void setOnClickImageListener(OnClickImageListener listener) {
        mOnClickImageListener = listener;
    }


    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new PBWebviewClient());
        addJavascriptInterface(this, TAG);
    }

    @JavascriptInterface
    public void openImage(String url) {

        if (mOnClickImageListener != null) {
            int index = mUrls.indexOf(url);
            mOnClickImageListener.onClickImage(mUrls, index);
        }

    }

    @JavascriptInterface
    public void addImageUrl(String url) {
        mUrls.add(url);
    }

    private class PBWebviewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl(
                    "javascript:(function(){" +
                            "var objs = document.getElementsByTagName(\"img\"); " +
                            "for(var i=0;i<objs.length;i++){" +
                            //添加url至android集合中
                            "window." + TAG + ".addImageUrl(objs[i].src);" +
                            //给每张图片添加点击事件
                            " objs[i].onclick=function() {  " +
                            " window." + TAG + ".openImage(this.src);  " +
                            "}" +
                            "}" +
                            "})()"

            );

        }

    }

}
