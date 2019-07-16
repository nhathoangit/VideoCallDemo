package com.nhat910.videocalldemo.ui.auth.login;

import android.os.Bundle;

import com.nhat910.videocalldemo.ui.base.BasePresenter;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class LoginPresenterImp extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter{
    @Override
    public void loginAuth(String userName, String password) {
        getView().showLoading();
        QBUser qbUser = new QBUser(userName,password);
        QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                getView().loginSuccess(qbUser);
                getView().hideLoading();
            }

            @Override
            public void onError(QBResponseException e) {
                getView().loginError(e.getMessage());
                getView().hideLoading();
            }
        });
    }
}
