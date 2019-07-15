package com.nhat910.videocalldemo.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    private Unbinder mUnBinder;

    public void addFragment(BaseFragment fragment, boolean isAddToBackStack) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addFragment(fragment, isAddToBackStack);
        }
    }

    public void replaceFragment(BaseFragment fragment, boolean isAddToBackStack) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).replaceFragment(fragment, isAddToBackStack);
        }
    }

    public void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public void onDestroyView() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroyView();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

}
