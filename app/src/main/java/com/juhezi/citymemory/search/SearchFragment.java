package com.juhezi.citymemory.search;

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

import com.amap.api.services.core.PoiItem;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.Location;
import com.juhezi.citymemory.other.Config;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class SearchFragment extends Fragment implements SearchContract.View {

    private static final String TAG = "SearchFragment";

    private SearchContract.Presenter mPresenter;
    private RecyclerView mRvList;
    private View rootView;
    private LocationAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_frag, container, false);
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_list);
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        mAdapter = new LocationAdapter();
        mAdapter.setClickItemLIstsner(new LocationAdapter.ClickItemListener() {
            @Override
            public void onClickItem(Location location) {
                Intent intent = new Intent();
                intent.putExtra(Config.LOCATION_KEY, location);
                getActivity().setResult(Config.SEARCH_CODE, intent);
                getActivity().finish();
            }
        });
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void refresh(List<Location> list) {
        mAdapter.refresh(list);
    }
}
