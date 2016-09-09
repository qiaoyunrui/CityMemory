package com.juhezi.citymemory.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Cov;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class ConversationFragment extends Fragment implements ConversationContract.View {

    private static final String TAG = "ConversationFragment";

    private ConversationContract.Presenter mPresenter;

    private View rootView;
    private SwipeRefreshLayout mSrlCov;
    private RecyclerView mRvList;
    private CovAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.coversation_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_cov_list);
        mSrlCov = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_cov);

        initRecyclerView();

        return rootView;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(layoutManager);
        mAdapter = new CovAdapter();
        mRvList.setAdapter(mAdapter);
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
    public void setPresenter(ConversationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        mSrlCov.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        mSrlCov.setRefreshing(false);
    }

    @Override
    public void showDailog() {

    }
}
