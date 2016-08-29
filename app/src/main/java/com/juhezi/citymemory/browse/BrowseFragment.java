package com.juhezi.citymemory.browse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowseFragment extends Fragment implements BrowseContract.View {

    private static final String TAG = "BrowseFragment";

    private BrowseContract.Presenter mPresenter;

    private View rootView;

    private RecyclerView mRvList;
    private FloatingActionButton mFabAdd;
    private BrowseAdapter mAdapter;
    private RelativeLayout mRlEmptyView;
    private ProgressBar mPbBrowse;

    private MemoryStream mMemoryStream;
    private LatLng mLatLng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.browse_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_browse_list);
        mFabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_browse_add);
        mRlEmptyView = (RelativeLayout) rootView
                .findViewById(R.id.rl_browse_empty_view);
        mPbBrowse = (ProgressBar) rootView.findViewById(R.id.pb_browse);

        initRecyclerView();

        initEvent();

        initData();

        return rootView;
    }

    /**
     * 获取列表数据
     */
    private void initData() {
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return;
        }
        mLatLng = (LatLng) intent
                .getParcelableExtra(Config.MEMORY_STREAM_LATLNG);
        if (mLatLng != null) {
            mPresenter.getStreamInfo(mLatLng, new OperateCallback<MemoryStream>() {
                @Override
                public void onOperate(MemoryStream memoryStream) {
                    mMemoryStream = memoryStream;
                    if (mMemoryStream == null) {    //此地没有回忆
                        showEmptyView();
                        hideProgressbar();  //此地有回忆
                    } else {

                    }
                }
            });
        }
    }

    private void initEvent() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BrowseActivity) getActivity()).openDiscussFragment();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        mAdapter = new BrowseAdapter();
        //data
        Memory m1 = new Memory();
        m1.setPickname("居合子");
        m1.setType(Memory.MEMORY_TYPE_DISCUSS);
        m1.setDiscuss("AVUser 作为 AVObject 的子类，同样允许子类化，你可以定义自己的 User 对象" +
                "，不过比起 AVObject 子类化会更简单一些，只要继承 AVUser 就可以了");
        m1.setAvatar("http://www.iconpng.com/download/png/100984");
        Memory m2 = new Memory();
        m2.setPickname("张全蛋");
        m2.setType(Memory.MEMORY_TYPE_PIC);
        m2.setPicture("http://cn.bing.com/hpwp/a4bcdfeaa76fd70b2d6eb227e5ee9362");
        m2.setAvatar("http://www.iconpng.com/download/png/100990");
        List<Memory> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(m1);
            list.add(m2);
        }
        mAdapter.setList(list);
        mAdapter.setListener(new BrowseAdapter.Listener() {
            @Override
            public void onItemClicked(String memory) {
                ((BrowseActivity) getActivity()).openViewFragment();
            }
        });
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(BrowseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showEmptyView() {
        mRlEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        mRlEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressbar() {
        mPbBrowse.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mPbBrowse.setVisibility(View.INVISIBLE);

    }
}

