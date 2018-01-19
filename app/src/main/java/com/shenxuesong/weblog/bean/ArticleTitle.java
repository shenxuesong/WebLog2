package com.shenxuesong.weblog.bean;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * Created by Dell on 2018/1/12.
 * 文章标题的类
 */

public class ArticleTitle {
    @JsoupText("li")
    public String title;

    @JsoupHref("a")
    public String url;

}
