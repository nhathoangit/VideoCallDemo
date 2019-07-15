package com.nhat910.videocalldemo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.activities.MainActivity;
import com.nhat910.videocalldemo.base.BaseFragment;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.nhat910.videocalldemo.utils.KeyboardUtils;
import com.nhat910.videocalldemo.utils.ValidateUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment{
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


    @OnClick({R.id.fragSignIn_btnLogin, R.id.fragSignIn_btnSignUp})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fragSignIn_btnLogin:
                if (ValidateUtils.validateLogin(getContext(), etUser.getText().toString().trim(), etPass.getText().toString().trim()))
                    authencationSignIn();
                break;
            case R.id.fragSignIn_btnSignUp:
                addFragment(new SignUpFragment(), true);
                break;
            default:
                break;
        }
    }

    private void authencationSignIn() {
        showLoading();
        QBUser qbUser = new QBUser(etUser.getText().toString().trim(), etPass.getText().toString().trim());
        QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("login", qbUser.getLogin());
                hideLoading();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }

            @Override
            public void onError(QBResponseException e) {
                hideLoading();
                AppUtils.showDialogMessage(getContext(), getString(R.string.unauthorize), e.getLocalizedMessage(), null);
            }
        });
    }
}
