package com.nhat910.videocalldemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.interfaces.ReceiveCallListener;
import com.nhat910.videocalldemo.others.Constant;
import com.nhat910.videocalldemo.ui.base.BaseActivity;
import com.nhat910.videocalldemo.ui.home.HomeFragment;
import com.nhat910.videocalldemo.ui.receivecall.ReceiveCallFragment;
import com.nhat910.videocalldemo.ui.videocall.VideoCallActivity;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.nhat910.videocalldemo.utils.WebrtcSessionManagement;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;

import java.util.HashMap;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ReceiveCallListener, MainContract.MainView {
    QBRTCSession qbrtcSession;
    MainPresenterImp presenterImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnBinder(ButterKnife.bind(this));
        presenterImp = new MainPresenterImp();
        presenterImp.onAttach(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterImp.onDetach();
    }

    private void init() {
        QBRTCClient qbrtcClient = QBRTCClient.getInstance(this);
        presenterImp.addSignaling(qbrtcClient);
        replaceFragment(new HomeFragment(), false);
    }

    @Override
    public void inComingCall(QBRTCSession qbrtcSession) {
        this.qbrtcSession = qbrtcSession;
        WebrtcSessionManagement.INSTANCE.setCurrentSession(qbrtcSession);
        addFragment(ReceiveCallFragment.newInstance(qbrtcSession.getCallerID(), this), true);
    }

    @Override
    public void closeReceiveCallFragment() {
        onBackPressed();
    }

    @Override
    public void createRoomChatSuccess(QBChatDialog qbChatDialog) {
        onBackPressed();
        Intent intent = new Intent(this, VideoCallActivity.class);
        intent.putExtra(Constant.DATA_SEND_CHAT_DIALOG,qbChatDialog);
        startActivity(intent);
    }

    @Override
    public void loadError(String error) {
        AppUtils.showDialogMessage(getBaseContext(), getString(R.string.error), error, null);
    }

    //After receivecall fragment callback
    @Override
    public void onAcceptCallListener() {
        qbrtcSession.acceptCall(new HashMap<String, String>());
        presenterImp.createPrivateDialog(qbrtcSession.getCallerID());
    }

    @Override
    public void onRejectCallListener() {
        qbrtcSession.rejectCall(new HashMap<String, String>());
        onBackPressed();
    }
}
