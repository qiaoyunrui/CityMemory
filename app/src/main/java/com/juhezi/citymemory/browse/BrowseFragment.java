package com.juhezi.citymemory.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.Memory;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.browse_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_browse_list);
        mFabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_browse_add);

        initRecyclerView();

        return rootView;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        mAdapter = new BrowseAdapter();
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
}

/*//data
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
        mAdapter.setList(list);*/