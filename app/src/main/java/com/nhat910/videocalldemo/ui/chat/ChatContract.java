package com.nhat910.videocalldemo.ui.chat;

import com.nhat910.videocalldemo.ui.base.MvpView;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;

public interface ChatContract {
    interface ChatView extends MvpView {
        void loadSuccess(ArrayList<QBChatMessage> qbChatMessages);

        void loadError(String errorMessage);
    }

    interface ChatPresenter{
        void getHistoryMessage(QBChatDialog qbChatDialog);
    }
}
