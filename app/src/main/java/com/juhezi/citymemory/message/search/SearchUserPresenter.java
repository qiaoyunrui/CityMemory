package com.juhezi.citymemory.message.search;

import android.util.Log;

import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.search.SearchContract;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by qiaoyunrui on 16-9-5.
 */
public class SearchUserPresenter implements SearchUserContract.Presenter {

    private static final String TAG = "SearchUserPresenter";

    private SearchUserContract.View mView;

    private UserSource mUserSource;

    public SearchUserPresenter(SearchUserContract.View view, UserSource userSource) {
        mView = view;
        mView.setPresenter(this);
        mUserSource = userSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void search(String name) {
        mView.showProgressbar();
        mUserSource.queryUsers(name, new OperateCallback<Observable<List<User>>>() {
            @Override
            public void onOperate(Observable<List<User>> observable) {
                if (observable == null) {
                    mView.showData(new ArrayList<User>());
                    mView.hideProgressbar();
                    return;
                }
                observable.subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showData(new ArrayList<User>());
                        mView.hideProgressbar();
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mView.hideProgressbar();
                        mView.showData(users);
                    }
                }).unsubscribe();
            }
        });
    }
}
