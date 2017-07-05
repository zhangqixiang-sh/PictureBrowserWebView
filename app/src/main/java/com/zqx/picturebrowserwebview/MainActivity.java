package com.zqx.picturebrowserwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PictureBrowserWebview.OnClickImageListener {

    @BindView(R.id.webview)
    PictureBrowserWebview mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mWebview.setOnClickImageListener(this);
        mWebview.loadUrl("http://www.sohu.com/a/154427844_691070");
    }

    @Override
    public void onClickImage(ArrayList<String> urlList, int index) {
        PictureBrowserActivity.launch(this,urlList,index);
    }
}
