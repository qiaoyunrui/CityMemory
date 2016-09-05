package com.juhezi.citymemory.map;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.data.DataResponse;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.other.Config;

public class MapActivity extends AppCompatActivity {

    private DrawerLayout mDLayout;
    private MapPresenter mPresenter;
    private MapFragment mFragment;
    private NavigationView mNView;
    private View mVHeader;
    private static final String TAG = "MapActivity";

    private ImageView mImgHeaderAvatar;
    private TextView mTvHeaderName;
    private ImageView mImgHeaderSign;

    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_act);
        initActionBar();
        initDrawLayout();

        mFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_map_frag);
        if (mFragment == null) {
            mFragment = new MapFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_map_frag, mFragment)
                    .commit();
        }
        mDataSource = DataResponse.getInstance(this);
        mPresenter = new MapPresenter(mFragment, this, mDataSource);
        AVAnalytics.trackAppOpened(getIntent());
    }

    private void initDrawLayout() {
        mDLayout = (DrawerLayout) findViewById(R.id.dl_layout);
        mNView = (NavigationView) findViewById(R.id.nav_view);
        mVHeader = mNView.getHeaderView(0);
        if (mVHeader != null) {
            mImgHeaderAvatar = (ImageView) mVHeader.findViewById(R.id.img_header_avatar);
            mTvHeaderName = (TextView) mVHeader.findViewById(R.id.tv_header_name);
            mImgHeaderSign = (ImageView) mVHeader.findViewById(R.id.img_header_sign);
            mVHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.turn2SignActivity();
                    mDLayout.closeDrawers();
                }
            });
        }
        mNView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_menu_setting:
                                if (mPresenter.getCurrUserData() != null) {
                                    mFragment.turn2SettingActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT)
                                            .show();
                                    mFragment.turn2SignActivity();
                                }
                                break;
                            case R.id.nav_menu_comment:
                                if (mPresenter.getCurrUserData() != null) {
                                    mFragment.turn2MessageActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT)
                                            .show();
                                    mFragment.turn2SignActivity();
                                }

                                break;
                        }
                        mDLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_map);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showUserInfo(AVUser user) {
        mVHeader.setClickable(false);
        mTvHeaderName.setText(user.get(Config.USER_PICK_NAME).toString());
        if ((int) user.get(Config.USER_TYPE) == User.USER_TYPE_GROUP) {
            mImgHeaderSign.setVisibility(View.VISIBLE);
        } else {
            mImgHeaderSign.setVisibility(View.INVISIBLE);
        }
        Glide.with(this)
                .load(user.get(Config.USER_AVATAR)
                        .toString())
                .error(R.drawable.ic_avatar)
                .into(mImgHeaderAvatar);
    }

    public void cleanUserInfo() {
        mVHeader.setClickable(true);
        mImgHeaderAvatar.setImageResource(R.drawable.ic_avatar);
        mTvHeaderName.setText("点击登录");
    }
}
