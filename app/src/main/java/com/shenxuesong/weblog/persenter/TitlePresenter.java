package com.shenxuesong.weblog.persenter;



import com.shenxuesong.weblog.IView.ITitle;
import com.shenxuesong.weblog.bean.ArticleTitle;
import com.shenxuesong.weblog.model.TitleModel;
import com.shenxuesong.weblog.net.SuccessAndFailure;

import java.util.List;

/**
 * Created by Dell on 2018/1/12.
 */

public class TitlePresenter {
    private ITitle iTitle;
    private TitleModel titleModel;

    public TitlePresenter(ITitle iTitle) {
        this.iTitle = iTitle;
        titleModel = new TitleModel();

    }

    public void getArticleTitle(){
        titleModel.getTitle(new SuccessAndFailure<List<ArticleTitle>>() {
            @Override
            public void getSuccess(List<ArticleTitle> articleTitles) {
                iTitle.showList(articleTitles);
            }
        });
    }
    public void getContent(){
        titleModel.getContent(new SuccessAndFailure<List<ArticleTitle>>() {
            @Override
            public void getSuccess(List<ArticleTitle> articleTitles) {
                iTitle.showList(articleTitles);
            }
        });
    }
    public void unBind(){
        iTitle=null;
        titleModel=null;
    }
}
