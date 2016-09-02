package com.juhezi.citymemory.setting.avatar;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-2.
 */
public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarHolder> {

    private static final String TAG = "AvatarAdapter";

    private List<String> list = new ArrayList<>();

    @Override
    public AvatarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_item, parent, false);
        return new AvatarHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvatarHolder holder, int position) {
        Glide.with(holder.mImgAvatar.getContext())
                .load(list.get(position))
                .error(R.drawable.error)
                .crossFade()
                .placeholder(R.drawable.ic_load)
                .into(holder.mImgAvatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class AvatarHolder extends RecyclerView.ViewHolder {

        public ImageView mImgAvatar;

        public AvatarHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar_select);
        }
    }

}
