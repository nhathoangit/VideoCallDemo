package com.nhat910.videocalldemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.base.BaseFragment;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.nhat910.videocalldemo.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends BaseFragment {
    @BindView(R.id.fragSignUp_etUser)
    EditText etUser;
    @BindView(R.id.fragSignUp_etPass)
    EditText etPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup,container,false);
        setUnBinder(ButterKnife.bind(this,view));
        KeyboardUtils.setupUI(view,getActivity());
        return view;
    }

    @OnClick({R.id.fragSignUp_btnSignUp,R.id.fragSignUp_btnBack})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fragSignUp_btnSignUp:
                if (AppUtils.validateLogin(getContext(),getString(R.string.signup
                ), etUser.getText().toString().trim(), etPass.getText().toString().trim()))
                    Log.e("Login", "Success");
                break;
            case R.id.fragSignUp_btnBack:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }
}
