package com.nhat910.videocalldemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.activites.VideoCallActivity;
import com.nhat910.videocalldemo.interfaces.ReceiveCallListener;
import com.nhat910.videocalldemo.others.Constant;
import com.nhat910.videocalldemo.ui.base.BaseActivity;
import com.nhat910.videocalldemo.ui.home.HomeFragment;
import com.nhat910.videocalldemo.ui.receivecall.ReceiveCallFragment;
import com.nhat910.videocalldemo.utils.WebrtcSessionManagement;
import com.quickblox.chat.QBChatService;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCConfig;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements QBRTCClientSessionCallbacks, ReceiveCallListener {
    QBRTCSession qbrtcSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnBinder(ButterKnife.bind(this));
        addSignaling();
        replaceFragment(new HomeFragment(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addSignaling() {
        if (QBChatService.getInstance().isLoggedIn()) {
            QBChatService.getInstance().getVideoChatWebRTCSignalingManager().addSignalingManagerListener((qbSignaling, b) -> QBRTCClient.getInstance(getApplicationContext()).addSignaling(qbSignaling));
            QBRTCConfig.setDebugEnabled(true);
            QBRTCClient.getInstance(this).addSessionCallbacksListener(this);
            QBRTCClient.getInstance(this).prepareToProcessCalls();
        }
    }


    @Override
    public void onReceiveNewSession(QBRTCSession qbrtcSession) {
        this.qbrtcSession = qbrtcSession;
        WebrtcSessionManagement.INSTANCE.setCurrentSession(qbrtcSession);
        Log.e("session id:", qbrtcSession.getSessionID());
        Log.e("caller Id:", qbrtcSession.getCallerID().toString());
        addFragment(ReceiveCallFragment.newInstance(qbrtcSession.getCallerID(), this), true);
    }

    @Override
    public void onUserNoActions(QBRTCSession qbrtcSession, Integer integer) {
    }

    @Override
    public void onSessionStartClose(QBRTCSession qbrtcSession) {
    }

    @Override
    public void onUserNotAnswer(QBRTCSession qbrtcSession, Integer integer) {
        onBackPressed();
    }

    @Override
    public void onCallRejectByUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
        Log.e("Reject", "rejected");
        //onBackPressed();
    }

    @Override
    public void onCallAcceptByUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
        Log.e("Accepted", "accept");
//        this.onBackPressed();
//        Intent intent = new Intent(this, VideoCallActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onReceiveHangUpFromUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
        Log.e("Receive hang up",qbrtcSession.getSessionID());
    }

    @Override
    public void onSessionClosed(QBRTCSession qbrtcSession) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //After receivecall fragment callback
    @Override
    public void onAcceptCallListener() {
        qbrtcSession.acceptCall(new HashMap<String, String>());
        onBackPressed();
        Intent intent = new Intent(this, VideoCallActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRejectCallListener() {
        qbrtcSession.rejectCall(new HashMap<String, String>());
    }
}
