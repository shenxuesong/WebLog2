package com.shenxuesong.weblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.shenxuesong.weblog.Gilde.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class WelcomePagerActivity extends AppCompatActivity {

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pager);
        banner = findViewById(R.id.banner);
        TextView start_tv=findViewById(R.id.start_tv);
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePagerActivity.this,MainActivity.class));
            }
        });
        /**
         * 轮播
         */
        playBanner();
    }

    private void playBanner() {
        List<Integer> images=new ArrayList<>();
        images.add(R.mipmap.one);
        images.add(R.mipmap.two);
        images.add(R.mipmap.three);
        images.add(R.mipmap.four);

        banner.setDelayTime(2000);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.isAutoPlay(true);
        banner.start();
    }
}
