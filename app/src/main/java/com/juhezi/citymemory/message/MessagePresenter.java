package com.juhezi.citymemory.message;

import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessagePresenter implements MessageContract.Presenter {

    private static final String TAG = "MessagePresenter";
    private MessageContract.View mView;

    private DataSource mDataSource;
    private UserSource mUserSource;

    public MessagePresenter(MessageContract.View view, DataSource dataSource
            , UserSource userSource) {
        mView = view;
        mDataSource = dataSource;
        mUserSource = userSource;
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void loadData() {
        mView.showProgressBar();
        AVUser user = mUserSource.getCurrentUser();
        if (null == user) {
            mView.showToast("数据加载失败");
            mView.showEmptyView();
            return;
        }
        mDataSource.getCoverRecords(user.getUsername(), new OperateCallback<Observable<List<Coversation>>>() {
            @Override
            public void onOperate(Observable<List<Coversation>> observable) {
                if (null == observable) {
                    mView.hideProgressBar();
                    mView.showEmptyView();
                    return;
                }
                observable.subscribe(new Observer<List<Coversation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgressBar();
                        mView.showEmptyView();
                        mView.showToast("数据加载失败");
                    }

                    @Override
                    public void onNext(List<Coversation> coversations) {
                        mView.setData(coversations);
                        mView.hideEmptyView();
                        mView.hideProgressBar();
                    }
                }).unsubscribe();
            }
        });
    }

    @Override
    public void getUser(String username, OperateCallback<User> callback) {
        mView.showProgressBar();
        mUserSource.queryUserByUsername(username, new OperateCallback<Observable<List<User>>>() {
            @Override
            public void onOperate(Observable<List<User>> observable) {
                if (observable == null) {
                    mView.showToast("用户信息加载失败。");
                    mView.hideProgressBar();
                }
                observable
                        .elementAt(0)
                        .subscribe(new Action1<List<User>>() {
                            @Override
                            public void call(List<User> users) {
                                mView.hideProgressBar();
                                callback.onOperate(users.get(0));
                            }
                        }).unsubscribe();
            }
        });
    }
}
