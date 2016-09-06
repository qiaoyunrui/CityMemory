package com.juhezi.citymemory.message.search;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-6.
 */
public class SUAdapter extends RecyclerView.Adapter<SUAdapter.AUViewHolder> {

    private static final String TAG = "SUAdapter";

    private List<User> list = new ArrayList<>();

    @Override
    public AUViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_user_item, parent, false);
        return new AUViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AUViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getAvatar())
                .crossFade()
                .error(R.drawable.ic_avatar_primary)
                .into(holder.mImgAvatar);
        holder.mTvName.setText(list.get(position).getPickName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<User> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class AUViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgAvatar;
        public TextView mTvName;

        public AUViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_search_user_avatar);
            mTvName = (TextView) itemView.findViewById(R.id.tv_search_user_name);
        }
    }

}
