package com.shenxuesong.weblog.net;

import com.github.florent37.retrojsoup.annotations.Select;
import com.shenxuesong.weblog.bean.ArticleTitle;


import io.reactivex.Observable;

/**
 * Created by Dell on 2018/1/12.
 */

public interface ServiceApi {
   @Select("div#beta-inner li")
   Observable<ArticleTitle> getContent();
    @Select("div.module-categories li")
    Observable<ArticleTitle> getTitle();
}
