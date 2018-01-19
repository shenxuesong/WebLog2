package com.shenxuesong.weblog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.shenxuesong.weblog.Adapter.ManagerRlvAdapter;
import com.shenxuesong.weblog.Gilde.MyAPP;
import com.shenxuesong.weblog.GreenDao.ArticleContent;
import com.usher.greendao_demo.greendao.gen.ArticleContentDao;


import java.util.List;

/***
 * 我的收藏的页面
 */
public class FileManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manage);

        initState();
        TextView tv_fh=findViewById(R.id.fh);
         //获取数据（查询数据库）
        final ArticleContentDao articleContentDao = MyAPP.getInstances().getDaoSession().getArticleContentDao();

        final List<ArticleContent> list = articleContentDao.loadAll();
        Log.i("List",list.size()+"");
        final RecyclerView managerRlv=findViewById(R.id.manger_rlv);

        managerRlv.setLayoutManager(new LinearLayoutManager(this));
        final ManagerRlvAdapter managerRlvAdapter = new ManagerRlvAdapter();
              managerRlvAdapter .setFeeds(list);
             managerRlv.setAdapter(managerRlvAdapter);
        //条目点击跳转，加载webView
       managerRlvAdapter.setOnItemListenter(new ManagerRlvAdapter.OnItemListener() {
            @Override
            public void onItemClick(String url) {

                Intent intent = new Intent(FileManageActivity.this, ArticleWebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

       managerRlvAdapter.setOnLongItemListenter(new ManagerRlvAdapter.OnLongItemClickListener() {
           @Override
           public void onLongItemClick(View v, int position) {

           articleContentDao.deleteByKey(Long.valueOf(position));

           }
       });
        //点击返回MainActivity
       tv_fh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(FileManageActivity.this,MainActivity.class));
           }
       });
    }


    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
