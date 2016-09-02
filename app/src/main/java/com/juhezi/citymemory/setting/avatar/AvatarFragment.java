package com.juhezi.citymemory.setting.avatar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juhezi.citymemory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class AvatarFragment extends Fragment implements AvatarContract.View {

    private static final String TAG = "AvatarFragment";

    private AvatarContract.Presenter mPresenter;

    private String basePath = "http://www.iconpng.com/download/png/1009";

    private List<String> mList = new ArrayList<>();
    private View rootView;
    private RecyclerView mRvList;

    private AvatarAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.avatar_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_avatar);
        initData();
        initRecyclerView();

        return rootView;
    }

    private void initData() {
        for (int i = 0; i < 18; i++) {
            mList.add(basePath + (i + 80));
        }
    }

    private void initRecyclerView() {
        mAdapter = new AvatarAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRvList.setAdapter(mAdapter);
        mRvList.setLayoutManager(layoutManager);
        mAdapter.setList(mList);
    }

    @Override
    public void setPresenter(AvatarContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }
}
