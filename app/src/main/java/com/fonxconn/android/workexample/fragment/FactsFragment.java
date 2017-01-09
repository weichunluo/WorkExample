package com.fonxconn.android.workexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fonxconn.android.workexample.R;
import com.fonxconn.android.workexample.bean.Facts;
import com.fonxconn.android.workexample.util.ImageLoader;
import com.fonxconn.android.workexample.util.OkHttpUtils;
import com.fonxconn.android.workexample.util.Urls;

import java.util.List;

/**
 * Created by Aaron on 2017/1/9.
 */

public class FactsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View mView;
    private RecyclerView mRecyclerView;
    private Facts mFacts;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_facts, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sr_fragment);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycle_facts);//实现刷新
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent
                , R.color.colorPrimary
                , R.color.colorAccent
                , R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initView();
        return mView;
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initData();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void initData() {
        //获取数据
        OkHttpUtils.get(Urls.URL, new OkHttpUtils.ResultCallback<Facts>() {

            @Override
            public void onSuccess(Facts response) {
                mFacts = response;
                setTitleActionBar();
                mRecyclerView.setAdapter(new FactsAdapter(getActivity(), mFacts.getRows()));
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //将标题设置成ActionBar
    private void setTitleActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mFacts.getTitle());
    }

    @Override
    public void onRefresh() {
        //下拉刷新
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class FactsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;//图像
        public TextView mTitle;//标题
        public TextView mDes;//描述

        public FactsViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.item_img_facts);
            mTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
            mDes = (TextView) itemView.findViewById(R.id.item_tv_des);
        }

        public void bindImage(Context context, String imgUrl) {
            ImageLoader.loadImage(context, imgUrl, mImage);
        }
    }

    public class FactsAdapter extends RecyclerView.Adapter<FactsViewHolder> {
        private Context mContext;
        private List<Facts.Row> mRowsList;

        public FactsAdapter(Context context, List<Facts.Row> rowList) {
            this.mContext = context;
            this.mRowsList = rowList;
        }

        @Override
        public FactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_listview_item, parent, false);
            return new FactsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FactsViewHolder holder, int position) {
            justifyView(holder, position);
            justifyView(holder, position);
            holder.mTitle.setText(mRowsList.get(position).getTitle());
            holder.mDes.setText(mRowsList.get(position).getDescription());
            holder.bindImage(mContext, mRowsList.get(position).getImageHref());

        }

        //clip null
        private void justifyView(FactsViewHolder holder, int position) {
            holder.mDes.setVisibility(mRowsList.get(position).getDescription() == null ? View.INVISIBLE : View.VISIBLE);
            holder.mTitle.setVisibility(mRowsList.get(position).getTitle() == null ? View.INVISIBLE : View.VISIBLE);
            holder.mImage.setVisibility(mRowsList.get(position).getImageHref() == null ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return mRowsList.size();
        }
    }

}
