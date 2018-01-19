package com.shenxuesong.weblog;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;


import com.shenxuesong.weblog.bean.Tab;
import com.shenxuesong.weblog.fragment.DiscoveryFragment;
import com.shenxuesong.weblog.fragment.SelectFragment;
import com.shenxuesong.weblog.fragment.SelefFragment;
import com.shenxuesong.weblog.fragment.StackFragment;
import com.shenxuesong.weblog.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ImageView img;
    private TextView text;
    private MyToolbar mToolbar;


    private List<Tab> mTabs = new ArrayList<>(5);

    NavigationView mNavView;
    DrawerLayout mDrawerLayout;
    private PopupWindow pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        initView();

        initTab();
        //沉浸式
        initState();
        //对话框
        initPopupWindow();


    }


    /**
     * 初始化布局控件
     */
    private void initView() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,null,R.string.open, R.string.close
        );
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mToolbar.setTitle("阮一峰");
        mToolbar.setRightIcon(getResources().getDrawable(R.mipmap.ic_menu_search));

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(MainActivity.this, "你好", Toast.LENGTH_SHORT).show();
                //显示对话框
                showPopUpWindow(view);

            }
        });

        mToolbar.setLeftIcon(getResources().getDrawable(R.mipmap.drawer_menu_icon));
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);

                RelativeLayout rllRJ = mNavView.findViewById(R.id.profile_bottombar);//夜间模式
                RelativeLayout rllSC= mNavView.findViewById(R.id.profile_history);//我的收藏
                //点击夜间模式的切换
                rllRJ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //夜间模式
                        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if (mode == Configuration.UI_MODE_NIGHT_YES) {
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }

                        recreate();
                    }
                });
                //点击查看我的收藏，查询数据库
                rllSC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到我收藏的页面
                        startActivity(new Intent(MainActivity.this,FileManageActivity.class));
                    }
                });
            }
        });

    }

    private void initTab() {
        Tab home = new Tab(R.string.selef_book,R.drawable.selector_icon_selef,SelefFragment.class);
        Tab hot = new Tab(R.string.book_care,R.drawable.selector_icon_care, SelectFragment.class);
        Tab category = new Tab(R.string.book_stack,R.drawable.selector_icon_stack,StackFragment.class);
        Tab cart = new Tab(R.string.book_disc,R.drawable.selector_icon_discover,DiscoveryFragment.class);

        mTabs.add(home);
        mTabs.add(hot);
        mTabs.add(category);
        mTabs.add(cart);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab:mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(builderIndiator(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }

        if(Build.VERSION.SDK_INT >=11) {
            //去掉分割线
            mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
        mTabHost.setCurrentTab(0);
    }

    /**
     * 创建indiator
     *
     * @param tab
     * @return
     */
    private View builderIndiator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator, null);

        img = (ImageView) view.findViewById(R.id.icon_tab);
        text = (TextView) view.findViewById(R.id.text_indicator);
        img.setBackgroundResource(tab.getImage());
        text.setText(tab.getTitle());

        return view;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    /**
     * 显示弹出窗口
     *
     *
     */
    private void showPopUpWindow(View view) {

        pw.showAsDropDown(view);
        changeWindowAlpha(0.7f);


    }
    private void initPopupWindow() {
        View content = LayoutInflater.from(this).inflate(R.layout.dao_popupwindow, null);

        pw = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView dc=content.findViewById(R.id.daochu);
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this, "导出", Toast.LENGTH_SHORT).show();



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
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
}
