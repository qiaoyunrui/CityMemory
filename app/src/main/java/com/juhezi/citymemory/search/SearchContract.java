package com.juhezi.citymemory.search;

import com.amap.api.services.poisearch.PoiResult;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Location;

import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public interface SearchContract {

    interface View extends BaseView<SearchContract.Presenter> {

        void refresh(List<Location> observable);

        void showProgressbar();

        void hideProgressbar();

    }

    interface Presenter extends BasePresenter {

        void search(String keyWord, String cityCode);

        void handleSearchResult(PoiResult poiResult);
    }

}
