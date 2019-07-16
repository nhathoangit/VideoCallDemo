package com.nhat910.videocalldemo.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.adapters.HomeUserAdapter;
import com.nhat910.videocalldemo.interfaces.ItemClickListener;
import com.nhat910.videocalldemo.ui.base.BaseFragment;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeContract.HomeView, ItemClickListener {
    HomePresenterImp presenterImp;
    @BindView(R.id.fragHome_rvUser)
    RecyclerView rvUser;
    HomeUserAdapter homeUserAdapter;
    List<QBUser> _data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        presenterImp = new HomePresenterImp();
        presenterImp.onAttach(this);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        presenterImp.onDetach();
        super.onDestroy();
    }

    private void initView() {
        _data = new ArrayList<>();
        presenterImp.getAllUser();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvUser.setLayoutManager(linearLayoutManager);
        homeUserAdapter = new HomeUserAdapter(_data);
        homeUserAdapter.setListener(this);
        rvUser.setAdapter(homeUserAdapter);
    }

    @OnClick({R.id.fragHome_btnRefresh})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.fragHome_btnRefresh:
                _data.clear();
                presenterImp.getAllUser();
                break;
        }
    }

    @Override
    public void loadUserSuccess(List<QBUser> _data) {
        this._data.addAll(_data);
        homeUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String errorMessage) {
        AppUtils.showDialogMessage(getContext(), getString(R.string.error), errorMessage, null);
    }

    @Override
    public void createChatSuccess(QBChatDialog qbChatDialog) {
        Log.e("Chat room Id", qbChatDialog.getDialogId());
    }

    @Override
    public void onItemClick(int position) {
        presenterImp.createPrivateChat(_data.get(position));
    }
}
