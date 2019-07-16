package com.nhat910.videocalldemo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhat910.videocalldemo.R;
import com.nhat910.videocalldemo.holder.HomeUserHolder;
import com.nhat910.videocalldemo.interfaces.ItemClickListener;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class HomeUserAdapter extends RecyclerView.Adapter<HomeUserHolder> {
    private List<QBUser> _data;
    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public HomeUserAdapter(List<QBUser> _data) {
        this._data = _data;
    }

    @NonNull
    @Override
    public HomeUserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflater = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_home, viewGroup, false);
        return new HomeUserHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeUserHolder homeUserHolder, int i) {
        homeUserHolder.tvUser.setText(_data.get(i).getFullName());
        homeUserHolder.imgName.setText(String.valueOf(_data.get(i).getFullName().charAt(0)).toUpperCase());
        //if(_data.get(i).getLogin().equals(QBChatService.getInstance().getUser().getLogin()))
        homeUserHolder.imgOnline.setVisibility(View.VISIBLE);
        homeUserHolder.clBtn.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(homeUserHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data != null ? _data.size() : 0;
    }
}
