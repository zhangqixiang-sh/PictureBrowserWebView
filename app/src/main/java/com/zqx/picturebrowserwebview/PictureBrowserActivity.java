package com.zqx.picturebrowserwebview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PictureBrowserActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String KEY_URLS = "key_urls";
    private static final String KEY_INDEX = "index";
    private static final String TAG = "PictureBrowserActivity";

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.pb)
    ProgressBar mPb;
    private List<String> mUrls;
    private int mIndex;

    public static void launch(Context context, ArrayList<String> urlList, int indexClicked) {
        if (isEmpty(urlList)) {//如果给的urlList为空,则页面启动不了
            Log.e(TAG,"urlList is empty!");
            return;
        }
        Intent intent = new Intent(context, PictureBrowserActivity.class);
        intent.putStringArrayListExtra(KEY_URLS, urlList);
        intent.putExtra(KEY_INDEX, indexClicked);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_browser);
        ButterKnife.bind(this);
        mUrls = getIntent().getStringArrayListExtra(KEY_URLS);
        mIndex = getIntent().getIntExtra(KEY_INDEX, 0);
        mPager.setAdapter(new PictureBrowserAdapter());
        mPager.addOnPageChangeListener(this);
        mPager.setCurrentItem(mIndex);
    }

    private static boolean isEmpty(Collection c) {
        return c == null || c.size() == 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvIndex.setText((position+1)+"/"+mUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class PictureBrowserAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();
            PhotoView view = new PhotoView(context);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Glide.with(context).load(mUrls.get(position)).into(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
}
