package com.juhezi.citymemory.browse.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class ViewFragment extends Fragment implements ViewContract.View {

    private static final String TAG = "ViewFragment";

    private ViewContract.Presenter mPresenter;

    private View rootView;
    private ProgressBar mPbView;
    private ImageView mImgShow;
    private FloatingActionButton mFabSave;
    private String url;
    private Bitmap mBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_frag, container, false);
        mPbView = (ProgressBar) rootView.findViewById(R.id.pb_view);
        mImgShow = (ImageView) rootView.findViewById(R.id.img_view_show);
        mFabSave = (FloatingActionButton) rootView.findViewById(R.id.fab_save);
        initData();

        initEvent();

        return rootView;
    }

    private void initEvent() {
        mFabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + mImgShow.getResources());
                if (mBitmap != null) {
                    showProgressbar();
                    mPresenter.saveImage(mBitmap, new Action() {
                        @Override
                        public void onAction() {
                            showToast("保存成功");
                            hideProgressbar();
                        }
                    }, new Action() {
                        @Override
                        public void onAction() {
                            showToast("保存失败");
                            hideProgressbar();
                        }
                    });
                } else {
                    Log.i(TAG, "onClick: Bitmap is null!");
                }
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString(Config.IMAGE_URL);
            showImage(url);
        }
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
        super.onDestroy();
    }

    @Override
    public void setPresenter(ViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressbar() {
        mPbView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mPbView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showImage(String url) {
        Glide.with(getContext())
                .load(url)
                .error(R.drawable.error)
                .crossFade()
                .into(mImgShow);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
