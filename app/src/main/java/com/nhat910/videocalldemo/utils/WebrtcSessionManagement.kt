package com.nhat910.videocalldemo.utils

import android.util.Log
import com.quickblox.videochat.webrtc.QBRTCSession
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacksImpl
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionConnectionCallbacksImpl

object WebrtcSessionManagement:QBRTCClientSessionCallbacksImpl() {
    private val TAG = WebrtcSessionManagement::class.java.simpleName
    private var currentSession: QBRTCSession? = null
    fun getCurrentSession():QBRTCSession?{
        return  this.currentSession
    }
    fun setCurrentSession(qbrtcSession: QBRTCSession?){
        this.currentSession = qbrtcSession
    }
    override fun onUserNotAnswer(session: QBRTCSession?, userID: Int?) {
        super.onUserNotAnswer(session, userID)
    }

    override fun onSessionStartClose(session: QBRTCSession?) {
        super.onSessionStartClose(session)
    }

    override fun onReceiveHangUpFromUser(session: QBRTCSession?, userID: Int?, userInfo: MutableMap<String, String>?) {
        super.onReceiveHangUpFromUser(session, userID, userInfo)
    }

    override fun onCallAcceptByUser(session: QBRTCSession?, userID: Int?, userInfo: MutableMap<String, String>?) {
        super.onCallAcceptByUser(session, userID, userInfo)
    }

    override fun onReceiveNewSession(session: QBRTCSession?) {
        Log.d(TAG, "onReceiveNewSession to WebRtcSessionManager")

        if (currentSession == null) {
            setCurrentSession(session)
        }
    }

    override fun onUserNoActions(session: QBRTCSession?, userID: Int?) {
        super.onUserNoActions(session, userID)
    }

    override fun onSessionClosed(session: QBRTCSession?) {
        super.onSessionClosed(session)
    }

    override fun onCallRejectByUser(session: QBRTCSession?, userID: Int?, userInfo: MutableMap<String, String>?) {
        super.onCallRejectByUser(session, userID, userInfo)
    }
}