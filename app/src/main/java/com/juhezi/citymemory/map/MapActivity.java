package com.juhezi.citymemory.map;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juhezi.citymemory.R;

public class MapActivity extends AppCompatActivity {

    private DrawerLayout mDLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_act);

    }

}
