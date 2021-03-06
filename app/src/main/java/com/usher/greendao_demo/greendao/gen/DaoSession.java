package com.usher.greendao_demo.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.shenxuesong.weblog.GreenDao.ArticleContent;

import com.usher.greendao_demo.greendao.gen.ArticleContentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig articleContentDaoConfig;

    private final ArticleContentDao articleContentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        articleContentDaoConfig = daoConfigMap.get(ArticleContentDao.class).clone();
        articleContentDaoConfig.initIdentityScope(type);

        articleContentDao = new ArticleContentDao(articleContentDaoConfig, this);

        registerDao(ArticleContent.class, articleContentDao);
    }
    
    public void clear() {
        articleContentDaoConfig.clearIdentityScope();
    }

    public ArticleContentDao getArticleContentDao() {
        return articleContentDao;
    }

}
