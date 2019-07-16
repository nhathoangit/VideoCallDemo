package com.nhat910.videocalldemo.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.adapters.ChatAdapter;
import com.nhat910.videocalldemo.others.Constant;
import com.nhat910.videocalldemo.ui.base.BaseFragment;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatFragment extends BaseFragment implements ChatContract.ChatView {
    @BindView(R.id.fragChat_tvOppnentNameTitle)
    TextView tvOppnentTitle;
    @BindView(R.id.fragChat_rv)
    RecyclerView rvChat;

    ChatPresenterImp presenterImp;
    QBChatDialog qbChatDialog;
    ChatAdapter chatAdapter;
    List<QBChatMessage> _data;

    public static ChatFragment newInstance(QBChatDialog qbChatDialog) {
        Bundle args = new Bundle();
        args.putSerializable(Constant.DATA_SEND_CHAT_DIALOG, qbChatDialog);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        presenterImp = new ChatPresenterImp();
        presenterImp.onAttach(this);
        initView();
        return view;
    }

    private void initView() {
        _data = new ArrayList<>();
        if (getArguments() != null) {
            qbChatDialog = (QBChatDialog) getArguments().getSerializable(Constant.DATA_SEND_CHAT_DIALOG);
            if (qbChatDialog != null) {
                tvOppnentTitle.setText(qbChatDialog.getName());
                presenterImp.getHistoryMessage(qbChatDialog);
            }
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvChat.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter((ArrayList<QBChatMessage>) _data);
        rvChat.setAdapter(chatAdapter);
    }

    @Override
    public void onDestroy() {
        presenterImp.onDetach();
        super.onDestroy();
    }

    @OnClick({R.id.fragChat_btnBack})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.fragChat_btnBack:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void loadSuccess(ArrayList<QBChatMessage> qbChatMessages) {
        _data.addAll(qbChatMessages);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String errorMessage) {
        AppUtils.showDialogMessage(getContext(), getString(R.string.unauthorize), errorMessage, null);
    }
}
