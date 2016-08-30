package com.juhezi.citymemory.browse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.sign.SignActivity;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Observer;

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
    private TextView mTvCurrentAddress;
    private TextView mTvCreater;
    private TextInputLayout mTILDiscuss;
    private ImageView mImgSend;
    private SwipeRefreshLayout mSrlRefresh;

    private AVUser currentUser;

    private MemoryStream mMemoryStream;
    private LatLng mLatLng;

    private Intent mSignIntent;


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
        mTvCurrentAddress = (TextView) rootView.findViewById(R.id.tv_current_address);
        mTvCreater = (TextView) rootView.findViewById(R.id.tv_creater);
        mTILDiscuss = (TextInputLayout) rootView.findViewById(R.id.til_browse_discuss);
        mImgSend = (ImageView) rootView.findViewById(R.id.img_browse_send);
        mSrlRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_browse_refresh);
        mSrlRefresh.setColorSchemeColors(R.color.colorAccent);
        initRecyclerView();

        initEvent();

        initData();

        return rootView;
    }

    /**
     * 获取列表数据
     */
    private void initData() {
        currentUser = mPresenter.getCurrentUser();
        if (currentUser == null) {
            unenableTIL();
            unenableSendButton();
        }
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return;
        }
        mLatLng = (LatLng) intent
                .getParcelableExtra(Config.MEMORY_STREAM_LATLNG);
        if (mLatLng != null) {
            mPresenter.getAddressByLatLng(mLatLng, new OperateCallback<String>() {
                @Override
                public void onOperate(String s) {
                    showAddress(s);
                }
            });
            mPresenter.getStreamInfo(mLatLng, new OperateCallback<MemoryStream>() {
                @Override
                public void onOperate(MemoryStream memoryStream) {
                    mMemoryStream = memoryStream;
                    if (mMemoryStream == null) {    //此地没有回忆
                        showEmptyView();
                        hideProgressbar();
                        if (mPresenter.getCurrentUser() != null) {
                            mMemoryStream = mPresenter.createNewMemory(mLatLng);
                        }
                    } else {    //此地有回忆
                        showCreater(mMemoryStream.getOwner());
                        mPresenter.getAllMemories(mMemoryStream.getId()
                                , new OperateCallback<Observable<List<Memory>>>() {
                                    @Override
                                    public void onOperate(Observable<List<Memory>> listObservable) {
                                        listObservable.subscribe(new Observer<List<Memory>>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(List<Memory> memories) {
                                                mAdapter.setList(memories);
                                                hideProgressbar();
                                                if (memories.size() == 0) {
                                                    showEmptyView();
                                                } else {
                                                    hideEmptyView();
                                                }
                                            }
                                        })
                                                .unsubscribe();
                                    }
                                });
                    }
                }
            });
        }
    }

    private void initEvent() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {   //未登录
                    showSnackBar("未登录无法添加回忆,请先登录.", "登录", new Action() {
                        @Override
                        public void onAction() {
                            turn2SignActivity();
                            getActivity().finish();
                        }
                    });

                } else {    //已经登录
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Config.MEMORY_STREAM_TAG, mMemoryStream);
                    ((BrowseActivity) getActivity()).openDiscussFragment(bundle);
                }
            }
        });
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllMemories(mMemoryStream.getId()
                        , new OperateCallback<Observable<List<Memory>>>() {
                            @Override
                            public void onOperate(Observable<List<Memory>> listObservable) {
                                listObservable.subscribe(new Observer<List<Memory>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(List<Memory> memories) {
                                        mAdapter.setList(memories);
                                        hideProgressbar();
                                        if (memories.size() == 0) {
                                            showEmptyView();
                                        } else {
                                            hideEmptyView();
                                        }
                                    }
                                })
                                        .unsubscribe();
                                mSrlRefresh.setRefreshing(false);
                            }
                        });
            }
        });
        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discuss = mTILDiscuss.getEditText().getText().toString().trim();
                AVUser user = mPresenter.getCurrentUser();
                if (discuss.length() != 0 && user != null) {
                    final Memory memory = new Memory();
                    memory.setType(Memory.MEMORY_TYPE_DISCUSS);
                    memory.setDiscuss(discuss);
                    memory.setId(UUID.randomUUID().toString());
                    memory.setStreamId(mMemoryStream.getId());
                    memory.setCreater(user.getUsername());
                    memory.setAvatar(user.getString(Config.USER_AVATAR));
                    memory.setPickname(user.getString(Config.USER_PICK_NAME));
                    mPresenter.uploadDiscuss(memory, new Action() {
                        @Override
                        public void onAction() {
                            mAdapter.addItem(memory, new Action() {
                                @Override
                                public void onAction() {
                                    hideEmptyView();
                                }
                            });
                            mTILDiscuss.getEditText().setText("");
                        }
                    }, new Action() {
                        @Override
                        public void onAction() {
                            showToast("评论失败");
                        }
                    });
                } else {
                    showToast("评论失败");
                }

            }
        });
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

    @Override
    public void showSnackBar(String message, String actionName, final Action action) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
                .setAction(actionName, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        action.onAction();
                    }
                })
                .show();
    }

    @Override
    public void turn2SignActivity() {
        mSignIntent = new Intent(getContext(), SignActivity.class);
        startActivity(mSignIntent);
    }

    @Override
    public void showAddress(String address) {
        mTvCurrentAddress.setText(address);
    }

    @Override
    public void showCreater(String creater) {
        mTvCreater.setText(creater);
    }

    @Override
    public void unenableTIL() {
        mTILDiscuss.setHint("未登录无法发表评论");
        mTILDiscuss.getEditText().setFocusable(false);
    }

    @Override
    public void unenableSendButton() {
        mImgSend.setClickable(false);
    }

    @Override
    public void enableSendButton() {
        mImgSend.setClickable(true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

