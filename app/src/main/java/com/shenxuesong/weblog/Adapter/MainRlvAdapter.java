package com.shenxuesong.weblog.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shenxuesong.weblog.R;
import com.shenxuesong.weblog.bean.ArticleTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2018/1/12.
 */

public class MainRlvAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
     private List<ArticleTitle> list;

    public MainRlvAdapter() {

    }
    public void setFeeds(List<ArticleTitle> list) {
        if (this.list == null) {
            this.list= new ArrayList<>();
        }
        int size=getItemCount();
        this.list.addAll(list);
        notifyItemRangeChanged(size,list.size());

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainitemrlv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ArticleTitle articleTitle = list.get(position);
        if(holder instanceof MyViewHolder){
            MyViewHolder vh= (MyViewHolder) holder;

            String title = articleTitle.title;
            final String url = articleTitle.url;
            Log.i("TITLE",title);
            vh.tv.setText(title);
            vh.ll.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onItemListener.onItemClick(url);

            }
         });

            vh.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongItemListener.onLongItemClick(v,position);
                    return false;
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
           tv=itemView.findViewById(R.id.item_tv);
           ll=itemView.findViewById(R.id.ll);
        }
    }
    public interface OnItemListener{
        void onItemClick(String url);
    }
    private OnItemListener onItemListener;
    public void setOnItemListenter(OnItemListener onItemListenter){
        this.onItemListener=onItemListenter;
    }
    public interface OnLongItemClickListener{
        void onLongItemClick(View v, int position);
    }
    private OnLongItemClickListener onLongItemListener;
    public void setOnLongItemListenter(OnLongItemClickListener onLongItemListener){
        this.onLongItemListener=onLongItemListener;
    }

}

