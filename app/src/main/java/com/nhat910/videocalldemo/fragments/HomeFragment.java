package com.nhat910.videocalldemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.adapters.HomeUserAdapter;
import com.nhat910.videocalldemo.base.BaseFragment;
import com.nhat910.videocalldemo.utils.AppUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragHome_rvUser)
    RecyclerView rvUser;
    HomeUserAdapter homeUserAdapter;
    List<QBUser> _data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        initView();
        return view;
    }

    private void initView() {
        _data = new ArrayList<>();
        getAllUser();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvUser.setLayoutManager(linearLayoutManager);
        homeUserAdapter = new HomeUserAdapter(_data);
        rvUser.setAdapter(homeUserAdapter);
    }

    private void getAllUser() {
        showLoading();
        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                _data.addAll(qbUsers);
                homeUserAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onError(QBResponseException e) {
                AppUtils.showDialogMessage(getContext(), getString(R.string.error), e.getMessage(), null);
            }
        });
    }

    private void refresh(){
        _data.clear();
        getAllUser();
    }

    @OnClick({R.id.fragHome_btnRefresh})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.fragHome_btnRefresh:
                refresh();
                break;
        }
    }
}
