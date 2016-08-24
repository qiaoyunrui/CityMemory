package com.juhezi.citymemory.map;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;

public class MapActivity extends AppCompatActivity {

    private DrawerLayout mDLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_act);

        initActionBar();

        initDrawLayout();

    }

    private void initDrawLayout() {
        mDLayout = (DrawerLayout) findViewById(R.id.dl_layout);
        //设置navgationView的点击事件
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
}
