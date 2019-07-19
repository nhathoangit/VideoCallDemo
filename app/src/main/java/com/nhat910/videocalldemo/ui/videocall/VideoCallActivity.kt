package com.nhat910.videocalldemo.activites

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import com.nhat910.videocalldemo.R
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.auth.session.QBSettings
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.QBSignaling
import com.quickblox.chat.QBWebRTCSignaling
import com.quickblox.chat.connections.tcp.QBTcpChatConnectionFabric
import com.quickblox.chat.connections.tcp.QBTcpConfigurationBuilder
import com.quickblox.chat.listeners.QBVideoChatSignalingListener
import com.quickblox.chat.listeners.QBVideoChatSignalingManagerListener
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.model.QBUser
import com.quickblox.videochat.webrtc.*
import com.quickblox.videochat.webrtc.callbacks.*
import com.quickblox.videochat.webrtc.exception.QBRTCSignalException
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack
import kotlinx.android.synthetic.main.activity_video_call.*
import org.webrtc.EglBase
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoFileRenderer
import org.webrtc.voiceengine.WebRtcAudioManager

class VideoCallActivity  : AppCompatActivity(),
        View.OnClickListener,
        QBRTCClientVideoTracksCallbacks<QBRTCSession>,
        QBRTCClientSessionCallbacks,
        QBRTCSessionStateCallback<QBRTCSession>,
        QBRTCSessionConnectionCallbacks,
        QBRTCSignalingCallback{

    override fun onSuccessSendingPacket(p0: QBSignalingSpec.QBSignalCMD?, p1: Int?) {
        Toast.makeText(this,"Connect Success",Toast.LENGTH_SHORT).show()
    }

    override fun onErrorSendingPacket(p0: QBSignalingSpec.QBSignalCMD?, p1: Int?, p2: QBRTCSignalException?) {
        Toast.makeText(this,"Connect Fail",Toast.LENGTH_SHORT).show()
    }

    override fun onStartConnectToUser(p0: QBRTCSession?, p1: Int?) {
        Log.e("KKK","onStartConnectToUser : ${p0.toString()}")
    }
    override fun onDisconnectedTimeoutFromUser(p0: QBRTCSession?, p1: Int?) {
        Log.e("KKK","onDisconnectedTimeoutFromUser : ${p0.toString()}")
    }
    override fun onConnectionFailedWithUser(p0: QBRTCSession?, p1: Int?) {
        Log.e("KKK","onConnectionFailedWithUser : ${p0.toString()}")
    }

    val TAG = "DEMO"
    private var isLocalVideoFullScreen: Boolean = false
    lateinit var localVideoView : QBRTCSurfaceView
    var remoteFullScreenVideoView : QBRTCSurfaceView? = null
    lateinit var  qbrtcChatService: QBChatService
    lateinit var toggle_startChat : ToggleButton
    lateinit var toggle_hangUpCall : ToggleButton
    lateinit var sendChatButton: ImageView
    lateinit var chatBox : EditText
    lateinit var localVideoTrack: QBRTCVideoTrack
    private var isCurrentCameraFront: Boolean = false
    lateinit var currentsession: QBRTCSession
    private lateinit var rtcClient: QBRTCClient
    var userid = 94902557
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        QBSettings.getInstance().init(this,"77788","AtebGG6JBEGn9hn", "V-vFWqG3nxkpvU9")
        QBSettings.getInstance().accountKey ="bWPcsTNHBuzhTbpH41Cx"
        createChatService()
        login()
        init()

    }
    fun init(){
        localVideoView = findViewById(R.id.localView)
        toggle_startChat = findViewById(R.id.toggle_start_chat)
        remoteFullScreenVideoView =findViewById(R.id.opponentView)
        toggle_hangUpCall = findViewById(R.id.button_hangup_call)
        chatBox = findViewById(R.id.et_chatbox)
        sendChatButton = findViewById(R.id.iv_sendChat)

        //initOnClickListener
        sendChatButton.setOnClickListener(this)
        toggle_startChat.setOnClickListener(this)
        toggle_hangUpCall.setOnClickListener(this)
        iv_cancel_chatView.setOnClickListener(this)
        localVideoView.setZOrderMediaOverlay(true)
        val eglContext :  EglBase  = QBRTCClient.getInstance(this).getEglContext();
        localVideoView.init(eglContext.eglBaseContext,null)
        localVideoView.release()
        localVideoView.requestLayout()
        initCorrectSizeForLocalView()
    }

    override fun onPause() {
        super.onPause()
        releaseView()
        releaseCurrentSession()
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.toggle_start_chat->{
                view_action_button.visibility = View.GONE
                rl_chat_container.visibility = View.VISIBLE
            }
            R.id.iv_cancel_chatView->{
                view_action_button.visibility = View.VISIBLE
                rl_chat_container.visibility = View.GONE
            }
            R.id.button_hangup_call->{
                currentsession.hangUp(HashMap())
            }
            R.id.iv_sendChat->{
                userid = chatBox.text.toString().toInt()
                Toast.makeText(this,"Connected to $userid",Toast.LENGTH_SHORT).show()
                startCall()
            }
        }
    }
    fun login(){
        val login ="cccc"
        val password = "123456789"
        var user  : QBUser  = QBUser(login,password)
        QBAuth.createSession(user).performAsync(object:QBEntityCallback<QBSession>{
            override fun onSuccess(p0: QBSession?, p1: Bundle?) {
                Log.e("HHH","isLogined ${p0?.token} ")
                user.id = p0!!.userId
                qbrtcChatService.login(user,object :QBEntityCallback<QBUser>{
                    override fun onSuccess(p0: QBUser?, p1: Bundle?) {
                        Log.e("HHH","login onSuccess")
                        signallingManager()
                    }
                    override fun onError(p0: QBResponseException?) {

                    }
                })
            }

            override fun onError(p0: QBResponseException?) {

            }

        })
    }
    fun createChatService(){
        val configurationBuilder = QBTcpConfigurationBuilder()
        configurationBuilder.socketTimeout = 0
        QBChatService.setConnectionFabric(QBTcpChatConnectionFabric(configurationBuilder))
        QBChatService.setDebugEnabled(true)
        qbrtcChatService = QBChatService.getInstance()
    }
    fun addListener(){
        currentsession.addVideoTrackCallbacksListener(this)
        currentsession.addSessionCallbacksListener(this)
        currentsession.addSignalingCallback(this)
    }
    fun releaseCurrentSession() {
        currentsession.removeSignalingCallback(this)
        currentsession.removeSessionCallbacksListener(this)
        rtcClient.removeSessionsCallbacksListener(this)
        currentsession.removeVideoTrackCallbacksListener(this)

    }
    fun releaseView(){
        localVideoView.release()
        remoteFullScreenVideoView!!.release()
    }
    fun signallingManager(){
        rtcClient = QBRTCClient.getInstance(applicationContext)

        qbrtcChatService.videoChatWebRTCSignalingManager.addSignalingManagerListener { qbSignaling, p1 ->
            rtcClient.addSignaling(qbSignaling as QBWebRTCSignaling) }
        QBRTCConfig.setDebugEnabled(true)
        rtcClient.addSessionCallbacksListener(this)
        rtcClient.prepareToProcessCalls()
    }
    fun startCall(){
        val qbConferenceType: QBRTCTypes.QBConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
        var opponents : ArrayList<Int> = ArrayList()
        opponents.add(userid)

        val userInfo : Map<String,String> = HashMap()
        val qbrtcClient = QBRTCClient.getInstance(applicationContext)
        val session  : QBRTCSession = qbrtcClient.createNewSessionWithOpponents(opponents,qbConferenceType)
        currentsession = session
        addListener()
        currentsession.startCall(userInfo)
        Log.e("CALL","Session was Created : $currentsession")

    }
    fun initCorrectSizeForLocalView() {
        val params = localVideoView.layoutParams
        val displaymetrics = resources.displayMetrics
        val screenWidthPx = displaymetrics.widthPixels
        Log.d("LCVD", "screenWidthPx $screenWidthPx")
        val width = (screenWidthPx * 0.3).toInt()
        val height = width / 2 * 3
        params?.width = width
        params?.height = height
        localVideoView.layoutParams = params
    }

    fun fillVideoView(videoView: QBRTCSurfaceView, videoTrack: QBRTCVideoTrack,
                      remoteRenderer: Boolean) {
        videoTrack.removeRenderer(videoTrack.renderer)
        videoTrack.addRenderer(videoView)
        if (!remoteRenderer) {
            updateVideoView(videoView, isCurrentCameraFront,RendererCommon.ScalingType.SCALE_ASPECT_FILL)
        }
        Log.d(TAG, (if (remoteRenderer) "remote" else "local") + " Track is rendering")
    }
    fun updateVideoView(videoView: SurfaceViewRenderer, mirror: Boolean,scalingType: RendererCommon.ScalingType) {
        Log.i(TAG, "updateVideoView mirror:$mirror, scalingType = $scalingType")
        videoView.setScalingType(scalingType)
        videoView.setMirror(mirror)
        videoView.requestLayout()
    }
    override fun onLocalVideoTrackReceive(p0: QBRTCSession?, viewTrack: QBRTCVideoTrack?) {
        Log.d(TAG, "onLocalVideoTrackReceive() run")
        isLocalVideoFullScreen = true
        localVideoTrack = viewTrack!!
        localVideoTrack.let { fillVideoView(localVideoView,it,false) }
        isLocalVideoFullScreen = false
    }
    override fun onRemoteVideoTrackReceive(p0: QBRTCSession?, p1: QBRTCVideoTrack?, p2: Int?) {
        fillVideoView(remoteFullScreenVideoView!!, p1!!, true)
        updateVideoView(remoteFullScreenVideoView!!, false,RendererCommon.ScalingType.SCALE_ASPECT_FILL)
    }

    override fun onUserNotAnswer(p0: QBRTCSession?, p1: Int?) {


    }

    override fun onSessionStartClose(p0: QBRTCSession?) {

    }

    override fun onReceiveHangUpFromUser(p0: QBRTCSession?, userID: Int?, p2: MutableMap<String, String>?) {
        if(p0 == currentsession){
            if(userID == p0.callerID){
                currentsession.let {
                    it.hangUp(HashMap())
                    releaseCurrentSession()
                    releaseView()
                }
            }
        }else{
            releaseCurrentSession()
            releaseView()
        }
    }

    override fun onCallAcceptByUser(p0: QBRTCSession?, p1: Int?, p2: MutableMap<String, String>?) {
        Log.e("HHH","onCallAcceptByUser : ${p0.toString()}")

    }

    override fun onReceiveNewSession(p0: QBRTCSession?) {
        Log.e("KKK","onReceiveNewSession : $p0")
        if (p0 != null) {
            currentsession = p0
            addListener()
            currentsession.acceptCall(p0.userInfo)
        }
    }

    override fun onUserNoActions(p0: QBRTCSession?, p1: Int?) {

    }

    override fun onSessionClosed(p0: QBRTCSession?) {

    }

    override fun onCallRejectByUser(p0: QBRTCSession?, p1: Int?, p2: MutableMap<String, String>?) {

    }
    override fun onDisconnectedFromUser(p0: QBRTCSession?, p1: Int?) {

    }

    override fun onConnectedToUser(p0: QBRTCSession?, p1: Int?) {
        Log.e("KKK","onConnectedToUser : ${p0.toString()}")
    }

    override fun onConnectionClosedForUser(p0: QBRTCSession?, p1: Int?) {

    }

    override fun onStateChanged(p0: QBRTCSession?, p1: BaseSession.QBRTCSessionState?) {

    }
}