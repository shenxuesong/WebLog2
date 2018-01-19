package com.shenxuesong.weblog.model;





import com.shenxuesong.weblog.bean.ArticleTitle;
import com.shenxuesong.weblog.net.JsoupHelper;
import com.shenxuesong.weblog.net.SuccessAndFailure;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dell on 2018/1/12.
 */

public class TitleModel {
   public void getTitle(final SuccessAndFailure<List<ArticleTitle>> successAndFailure){
       Observable<ArticleTitle> observable = JsoupHelper.getBlogFeed().getTitle();
       observable.toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleTitle>>() {
                    @Override
                    public void accept(List<ArticleTitle> articleTitles) throws Exception {
                        successAndFailure.getSuccess(articleTitles);
                    }
                });
    }
    public void getContent(final SuccessAndFailure<List<ArticleTitle>> successAndFailure){
        Observable<ArticleTitle> observable = JsoupHelper.getBlogFeed().getContent();
        observable.toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleTitle>>() {
                    @Override
                    public void accept(List<ArticleTitle> articleTitles) throws Exception {
                        successAndFailure.getSuccess(articleTitles);
                    }
                });
    }
}
