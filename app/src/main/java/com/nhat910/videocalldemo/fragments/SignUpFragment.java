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
import com.nhat910.videocalldemo.utils.ValidateUtils;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends BaseFragment {
    @BindView(R.id.fragSignUp_etUser)
    EditText etUser;
    @BindView(R.id.fragSignUp_etPass)
    EditText etPass;
    @BindView(R.id.fragSignUp_etPassConfirm)
    EditText etConfirmPass;
    @BindView(R.id.fragSignUp_etEmail)
    EditText etEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        KeyboardUtils.setupUI(view, getActivity());
        return view;
    }

    @OnClick({R.id.fragSignUp_btnSignUp, R.id.fragSignUp_btnBack})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fragSignUp_btnSignUp:
                if (ValidateUtils.validateSignUp(getContext(),
                        etUser.getText().toString().trim(),
                        etPass.getText().toString().trim(),
                        etConfirmPass.getText().toString().trim(),
                        etEmail.getText().toString().trim()))
                    authencationSignUp();
                break;
            case R.id.fragSignUp_btnBack:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void authencationSignUp() {
        showLoading();
        QBUser qbUser = new QBUser(etUser.getText().toString().trim(), etPass.getText().toString().trim(),etEmail.getText().toString().trim());
        QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("SignUp", qbUser.getLogin());
                hideLoading();
            }

            @Override
            public void onError(QBResponseException e) {
                hideLoading();
                AppUtils.showDialogMessage(getContext(), getString(R.string.unauthorize), e.getLocalizedMessage(), null);
            }
        });
    }

    private void registerSession(QBUser qbUser) {
        QBAuth.createSession(qbUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.e("Session", String.valueOf(qbSession.getUserId()));
            }

            @Override
            public void onError(QBResponseException e) {
                AppUtils.showDialogMessage(getContext(), getString(R.string.unauthorize), e.getLocalizedMessage(), null);
            }
        });
    }
}
