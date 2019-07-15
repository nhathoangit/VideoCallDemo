package com.nhat910.videocalldemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.base.BaseActivity;
import com.nhat910.videocalldemo.fragments.HomeFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnBinder(ButterKnife.bind(this));
        replaceFragment(new HomeFragment(), false);
    }
}
