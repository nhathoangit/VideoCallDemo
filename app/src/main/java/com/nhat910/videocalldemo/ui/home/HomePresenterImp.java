package com.nhat910.videocalldemo.ui.home;

import android.os.Bundle;

import com.nhat910.videocalldemo.ui.base.BasePresenter;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class HomePresenterImp extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    @Override
    public void getAllUser() {
        getView().showLoading();
        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                getView().loadUserSuccess(qbUsers);
                getView().hideLoading();
            }

            @Override
            public void onError(QBResponseException e) {
                getView().loadError(e.getMessage());
                getView().hideLoading();
            }
        });
    }

    @Override
    public void createPrivateChat(QBUser qbUser) {
        getView().showLoading();
        QBChatDialog qbChatDialog = DialogUtils.buildPrivateDialog(qbUser.getId());
        qbChatDialog.setName(qbUser.getFullName());
        QBRestChatService.createChatDialog(qbChatDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                getView().createChatSuccess(qbChatDialog);
                getView().hideLoading();
            }

            @Override
            public void onError(QBResponseException e) {
                getView().loadError(e.getMessage());
                getView().hideLoading();
            }
        });
    }
}
