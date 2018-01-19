package com.shenxuesong.weblog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shenxuesong.weblog.Adapter.MainRlvAdapter;
import com.shenxuesong.weblog.ArticleWebActivity;
import com.shenxuesong.weblog.IView.ITitle;
import com.shenxuesong.weblog.R;
import com.shenxuesong.weblog.bean.ArticleTitle;
import com.shenxuesong.weblog.persenter.TitlePresenter;

import java.util.List;

/*
   精选
 */
public class SelectFragment extends Fragment implements ITitle {

    private TitlePresenter titlePresenter;
    private RecyclerView rlv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container,false);
        titlePresenter = new TitlePresenter(this);
        titlePresenter.getContent();
        rlv = view.findViewById(R.id.JXRlv);

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void showList(List<ArticleTitle> list) {
        MainRlvAdapter mainRlvAdapter = new MainRlvAdapter();
        mainRlvAdapter.setFeeds(list);
        rlv.setAdapter(mainRlvAdapter);
        //条目点击进入webview
        mainRlvAdapter.setOnItemListenter(new MainRlvAdapter.OnItemListener() {
            @Override
            public void onItemClick(String url) {
                Intent intent = new Intent(getContext(), ArticleWebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }

    /**
     * 进行解绑
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        titlePresenter.unBind();
        titlePresenter=null;
    }
}
