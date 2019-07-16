package com.nhat910.videocalldemo.holder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhat910.videocalldemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeUserHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.itemUserHome_imgName)
    public TextView imgName;
    @BindView(R.id.itemUserHome_tvUser)
    public TextView tvUser;
    @BindView(R.id.itemUserHome_imgOnline)
    public ImageView imgOnline;
    @BindView(R.id.itemUserHome_cl)
    public ConstraintLayout clBtn;

    public HomeUserHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
