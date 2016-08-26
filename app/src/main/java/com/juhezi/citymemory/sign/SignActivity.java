package com.juhezi.citymemory.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_act);

        initActionBar();

    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.tb_sign);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
    }
}
