package com.shenxuesong.weblog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载文章链接的类
 */
public class ArticleWebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    LinearLayout linearLayout;

    AgentWeb agentWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web1);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");


        agentWeb = AgentWeb.with(this)//
                .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .createAgentWeb()//
                .ready()
                .go(url);
    }


}
