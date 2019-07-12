package com.nhat910.videocalldemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.activities.AuthActivity;
import com.nhat910.videocalldemo.activities.MainActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * Created by NhatHoang on 12/07/2019.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnBinder;

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        clearAllBackStack();
        super.onDestroy();
    }

    public void addFragment(BaseFragment fragment, boolean isAddToBackStack) {
        addReplaceFragment(fragment, false, isAddToBackStack);
    }

    public void replaceFragment(BaseFragment fragment, boolean isAddToBackStack) {
        addReplaceFragment(fragment, true, isAddToBackStack);
    }

    private void addReplaceFragment(BaseFragment fragment, boolean isReplace, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null && fragment != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (isReplace)
                fragmentTransaction.replace(this instanceof AuthActivity ? R.id.act_frContent : R.id.act_frContent, fragment);
            else {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(this instanceof AuthActivity ? R.id.act_frContent : this instanceof MainActivity ?
                        R.id.act_frContent : R.id.act_frContent);
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment);
                }
                fragmentTransaction.add(this instanceof AuthActivity ? R.id.act_frContent : this instanceof MainActivity ?
                        R.id.act_frContent : R.id.act_frContent, fragment, fragment.getClass().getSimpleName());
            }
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            fragmentTransaction.commit();

        }
    }

    public void clearAllBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }
}