package com.juhezi.citymemory.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.message_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_message);

        initRecyclerView();

        initEvent();

        return rootView;
    }

    private void initEvent() {

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(layoutManager);
        mRvList.addItemDecoration(new DividerItemDecoration(
                getContext(), LinearLayoutManager.VERTICAL));
        mAdapter = new MessageAdapter();
        mRvList.setAdapter(mAdapter);

        //data
        List<Coversation> list = new ArrayList<>();
        Coversation coversation = new Coversation();
        coversation.setAvatar("http://www.iconpng.com/download/png/100996");
        coversation.setPickname("张全蛋");
        coversation.setContent("你好");
        for (int i = 0; i < 10; i++) {
            list.add(coversation);
        }
        mAdapter.setList(list);
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
}
