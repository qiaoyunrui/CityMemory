package com.juhezi.citymemory.search;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.juhezi.citymemory.data.Location;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = "SearchPresenter";

    private SearchContract.View mView;
    private PoiSearch.Query mQuery;
    private Context mContext;

    public SearchPresenter(SearchContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String keyWord, String cityCode) {
        mQuery = new PoiSearch.Query(keyWord, "", cityCode);
        mQuery.setPageSize(100);
        mQuery.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(mContext, mQuery);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                handleSearchResult(poiResult);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void handleSearchResult(PoiResult poiResult) {
        Observable<PoiItem> observable = Observable.from(poiResult.getPois());
        observable.map(new Func1<PoiItem, Location>() {
            @Override
            public Location call(PoiItem poiItem) {

                return new Location(poiItem.getLatLonPoint().getLongitude(),
                        poiItem.getLatLonPoint().getLatitude(),
                        poiItem.getCityName(),
                        poiItem.getDistance(),
                        poiItem.getTitle());
            }
        })
                .toList()
                .subscribe(new Observer<List<Location>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        mView.refresh(locations);
                    }
                })
                .unsubscribe();
    }


}
