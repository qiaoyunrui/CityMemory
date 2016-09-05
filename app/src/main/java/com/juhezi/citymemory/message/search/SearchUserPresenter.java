package com.juhezi.citymemory.message.search;

import android.util.Log;

import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.search.SearchContract;

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
        Log.i(TAG, "start: Hello");
    }
}
