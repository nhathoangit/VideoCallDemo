package com.nhat910.videocalldemo.activities;

import android.os.Bundle;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.base.BaseActivity;
import com.nhat910.videocalldemo.fragments.LoginFragment;

import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setUnBinder(ButterKnife.bind(this));
        replaceFragment(new LoginFragment(), false);
    }
}
