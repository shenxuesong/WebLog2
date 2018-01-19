package com.shenxuesong.weblog.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.shenxuesong.weblog.Adapter.MainRlvAdapter;
import com.shenxuesong.weblog.ArticleWebActivity;
import com.shenxuesong.weblog.Gilde.MyAPP;
import com.shenxuesong.weblog.GreenDao.ArticleContent;
import com.shenxuesong.weblog.IView.ITitle;
import com.shenxuesong.weblog.R;
import com.shenxuesong.weblog.Utils.DisplayUtil;
import com.shenxuesong.weblog.bean.ArticleTitle;
import com.shenxuesong.weblog.persenter.TitlePresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.usher.greendao_demo.greendao.gen.ArticleContentDao;

import java.util.List;
import java.util.Map;


/**
 * 书库
 */
public class SelefFragment extends Fragment implements ITitle {

    private RecyclerView rlv;
    private TitlePresenter titlePresenter;
    private PopupWindow pw;
    private View view;
    private String title;

    private String url;
    private int id=1;
    private ArticleContentDao articleContentDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        titlePresenter = new TitlePresenter(this);
             titlePresenter.getArticleTitle();
        rlv = view.findViewById(R.id.MainRlv);
        //PopupWindow
        initPopupWindow();

        articleContentDao = MyAPP.getInstances().getDaoSession().getArticleContentDao();


        return view;
    }

    @Override
    public void showList(final List<ArticleTitle> list) {
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
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
        //条目长点击进行收藏、分享
        mainRlvAdapter.setOnLongItemListenter(new MainRlvAdapter.OnLongItemClickListener() {


            @Override
            public void onLongItemClick(View v,int position) {
               ArticleTitle articleTitle = list.get(position);
                title = articleTitle.title;
                 url = articleTitle.url;
                showPopUpWindow(v);
                id++;
            }
        });

    }
    /**
     * 显示弹出窗口
     *
     *
     */
    private void showPopUpWindow(View view) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int w = DisplayUtil.dp2px(getContext(), 203);
        int h = DisplayUtil.dp2px(getContext(), 10);
        Point p = new Point();
        wm.getDefaultDisplay().getSize(p);
        int x = p.x - view.getMeasuredWidth() - w;
        pw.showAsDropDown(view, x, h);
        changeWindowAlpha(0.7f);


    }
    private void initPopupWindow() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.share_popupwindow, null);

        pw = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            TextView WX=  content.findViewById(R.id.wx);
            TextView QQ=  content.findViewById(R.id.share_kj);
          WX.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                 UMShareAPI.get(getActivity()).doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN,authListener);
                  new ShareAction(getActivity())
                          .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                          .withText("hello")//分享内容
                          .setCallback(shareListener)//回调监听器
                          .share();
              }
          });
          QQ.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                UMWeb web = new UMWeb("http://www.baidu.com");
                  new ShareAction(getActivity()).withMedia(web).setPlatform(SHARE_MEDIA.QZONE).setCallback((UMShareListener) shareListener).share();
              }
          });
        TextView mQxPop = content.findViewById(R.id.qx_pop);
       LinearLayout  sc = content.findViewById(R.id.sc);
       //点击收藏的时候，添加数据库
       sc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //向GreenDao添加数据(id为自增的不必要传)
              articleContentDao.insert(new ArticleContent(null,title,url));

               Toast.makeText(getActivity(), title+"收藏成功", Toast.LENGTH_SHORT).show();
               //对话框消失
               pw.dismiss();
           }
       });
        //给取消点击事件，取消对话框
        mQxPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
    }
    /**
     * 改变界面的透明度
     *
     * @param alpha
     */
    private void changeWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        titlePresenter.unBind();
        titlePresenter=null;
    }
    private UMShareListener shareListener = new UMShareListener() {
    /*    *
         * @descrption 分享开始的回调
         * @param platform 平台类型*/

        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "开始了", Toast.LENGTH_LONG).show();
        }

   /*  /   *
         * @descrption 分享成功的回调
         * @param platform 平台类型
       *  /*/
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

      /*  *
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

     /*   *
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();

        }
    };
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
            Toast.makeText(getActivity(), temp, Toast.LENGTH_LONG).show();


        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
        }
    };
    @Override
      public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode,resultCode,data);
    }
}
