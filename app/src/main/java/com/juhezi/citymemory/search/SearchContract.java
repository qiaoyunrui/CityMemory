package com.juhezi.citymemory.search;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.Location;
import com.juhezi.citymemory.map.MapContract;

import java.util.List;

import rx.Observable;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public interface SearchContract {

    interface View extends BaseView<SearchContract.Presenter> {

        void refresh(List<Location> observable);

    }

    interface Presenter extends BasePresenter {

        void search(String keyWord, String cityCode);

        void handleSearchResult(PoiResult poiResult);
    }

}
