package com.juhezi.citymemory.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessageFragment extends Fragment implements MessageContract.View {

    private static final String TAG = "MessageFragment";

    private View rootView;
    private MessageContract.Presenter mPresenter;
    private RecyclerView mRvList;
    private MessageAdapter mAdapter;
    private SwipeRefreshLayout mSrlRefresh;
    private RelativeLayout mRlEmptyView;
    private FloatingActionButton mFabAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.message_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_message);
        mSrlRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_message);
        mRlEmptyView = (RelativeLayout) rootView
                .findViewById(R.id.rl_message_empty_view);
        mFabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_message_add);
        initRecyclerView();

        initEvent();

        mPresenter.loadData();

        return rootView;
    }

    private void initEvent() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MessageActivity) getActivity()).addSearchUserFragment();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(layoutManager);
        mRvList.addItemDecoration(new DividerItemDecoration(
                getContext(), LinearLayoutManager.VERTICAL));
        mAdapter = new MessageAdapter();
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
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setData(List<Coversation> list) {
        mAdapter.setList(list);
    }

    @Override
    public void showProgressBar() {
        mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        mSrlRefresh.setRefreshing(false);
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
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
