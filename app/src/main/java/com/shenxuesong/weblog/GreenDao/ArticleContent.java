package com.shenxuesong.weblog.GreenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ArticleContent {

    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String url;
    @Generated(hash = 1998179012)
    public ArticleContent(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
    @Generated(hash = 949142728)
    public ArticleContent() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

  
}