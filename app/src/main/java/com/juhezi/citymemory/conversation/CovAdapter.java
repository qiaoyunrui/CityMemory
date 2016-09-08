package com.juhezi.citymemory.conversation;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Cov;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-8.
 */
public class CovAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CovAdapter";

    private List<Cov> list = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Cov.COV_TYPE_RECE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cov_rece_item, parent, false);
                return new RMessageHolder(itemView);
            case Cov.COV_TYPE_SEND:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cov_send_item, parent, false);
                return new SMessageHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Cov.COV_TYPE_RECE:
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position)
                                .getAvatar())
                        .crossFade()
                        .error(R.drawable.ic_avatar_primary)
                        .into(((RMessageHolder) holder).mImgAvatar);
                ((RMessageHolder) holder).mTvMessage
                        .setText(list.get(position).getMessage());
                break;
            case Cov.COV_TYPE_SEND:
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position)
                                .getAvatar())
                        .crossFade()
                        .error(R.drawable.ic_avatar_primary)
                        .into(((SMessageHolder) holder).mImgAvatar);
                ((SMessageHolder) holder).mTvMessage
                        .setText(list.get(position).getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Cov> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 显示发送消息的View
     */
    class SMessageHolder extends RecyclerView.ViewHolder {

        public ImageView mImgAvatar;
        public TextView mTvMessage;

        public SMessageHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_cov_send_avatar);
            mTvMessage = (TextView) itemView.findViewById(R.id.tv_cov_send_message);
        }
    }

    /**
     * 显示接收信息的View
     */
    class RMessageHolder extends RecyclerView.ViewHolder {

        public ImageView mImgAvatar;
        public TextView mTvMessage;

        public RMessageHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_cov_rece_avatar);
            mTvMessage = (TextView) itemView.findViewById(R.id.tv_cov_rece_message);
        }
    }

}
