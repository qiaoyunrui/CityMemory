package com.juhezi.citymemory.message.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.conversation.ConversationActivity;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.PersonDialog;

import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-5.
 */
public class SearchUserFragment extends Fragment implements SearchUserContract.View {

    private static final String TAG = "SearchUserFragment";

    private SearchUserContract.Presenter mPresenter;

    private ProgressBar mPbSearchUser;
    private RecyclerView mRvList;
    private SUAdapter mAdapter;

    private Intent mCovIntent;

    private View rootView;

    private PersonDialog mPersonDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_user_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_search_user_list);
        mPbSearchUser = (ProgressBar) rootView.findViewById(R.id.pb_search_user);
        mPersonDialog = new PersonDialog(getContext());

        initRecyclerView();

        initEvent();

        return rootView;
    }

    private void initEvent() {
        mPersonDialog.setClickListener(new PersonDialog.ClickListener() {
            @Override
            public void onMessageBtnClicked(User user) {
                turn2CovActivity(user);
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new SUAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(linearLayoutManager);
        mRvList.setAdapter(mAdapter);
        mAdapter.setClickListener(new SUAdapter.ItemClickListener() {
            @Override
            public void onItemClick(User user) {
                mPersonDialog.setData(user);
                mPersonDialog.show();
            }
        });
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
    public void setPresenter(SearchUserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressbar() {
        mPbSearchUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mPbSearchUser.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showData(List<User> list) {
        mAdapter.setData(list);
    }

    @Override
    public void turn2CovActivity(User user) {
        if (null == mCovIntent) {
            mCovIntent = new Intent(getContext(), ConversationActivity.class);
        }
        mCovIntent.putExtra(Config.USER_KEY, user);
        startActivity(mCovIntent);
    }
}
