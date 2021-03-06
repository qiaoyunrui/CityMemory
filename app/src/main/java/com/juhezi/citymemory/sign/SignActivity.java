package com.juhezi.citymemory.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.sign.signin.SigninContract;
import com.juhezi.citymemory.sign.signin.SigninFragment;
import com.juhezi.citymemory.sign.signin.SigninPresenter;
import com.juhezi.citymemory.sign.signup.SignupContract;
import com.juhezi.citymemory.sign.signup.SignupFragment;
import com.juhezi.citymemory.sign.signup.SignupPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    private List<String> tabNames = new ArrayList<>();

    private List<Fragment> mFragments = new ArrayList<>();

    private SigninContract.View mSigninFragment;
    private SigninPresenter mSigninPresenter;
    private SignupContract.View mSignupFragment;
    private SignupPresenter mSignupPresenter;

    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private TabLayout mTLayout;
    private ViewPager mVPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_act);

        initActionBar();

        initView();

        initFragment();

        initTablayout();

    }

    private void initActionBar() {

        mToolbar = (Toolbar) findViewById(R.id.tb_sign);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("账户");
    }

    private void initView() {
        mTLayout = (TabLayout) findViewById(R.id.tl_layout);
        mVPager = (ViewPager) findViewById(R.id.vp_layout);
    }

    private void initFragment() {
        mSigninFragment = new SigninFragment();
        mFragments.add((Fragment) mSigninFragment);
        mSigninPresenter = new SigninPresenter(mSigninFragment);
        mSignupFragment = new SignupFragment();
        mFragments.add((Fragment) mSignupFragment);
        mSignupPresenter = new SignupPresenter(mSignupFragment);
    }

    private void initTablayout() {
        tabNames.add("登录");
        tabNames.add("注册");
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(),
                tabNames, mFragments);
        mVPager.setAdapter(vpAdapter);
        mTLayout.setupWithViewPager(mVPager);
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void turnSignIn() {
        mVPager.setCurrentItem(0);
    }
}
