package com.nhat910.videocalldemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.base.BaseFragment;
import com.nhat910.videocalldemo.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.fragSignIn_etUser)
    EditText etUser;
    @BindView(R.id.fragSignIn_etPass)
    EditText etPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        KeyboardUtils.setupUI(view, getActivity());
        return view;
    }


    @OnClick({R.id.fragSignIn_btnLogin})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fragSignIn_btnLogin:
                break;
        }
    }
}
