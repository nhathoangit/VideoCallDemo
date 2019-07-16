package com.nhat910.videocalldemo.ui.chat;

import android.os.Bundle;

import com.nhat910.videocalldemo.ui.base.BasePresenter;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import java.util.ArrayList;

public class ChatPresenterImp extends BasePresenter<ChatContract.ChatView> implements ChatContract.ChatPresenter {
    @Override
    public void getHistoryMessage(QBChatDialog qbChatDialog) {
        getView().showLoading();
        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(50);
        QBRestChatService.getDialogMessages(qbChatDialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                getView().loadSuccess(qbChatMessages);
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
