package com.juhezi.citymemory.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.Memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BrowseAdapter";

    private List<Memory> list = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Memory.MEMORY_TYPE_DISCUSS:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.discuss_item, parent, false);
                return new DiscussViewHolder(itemView);
            case Memory.MEMORY_TYPE_PIC:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memory_item, parent, false);
                return new MemoryViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Memory.MEMORY_TYPE_DISCUSS:
                ((DiscussViewHolder) holder).mTvDiscussPickname
                        .setText(list.get(position).getPickname());
                ((DiscussViewHolder) holder).mTvDiscussDiscuss
                        .setText(list.get(position).getDiscuss());
                Glide.with(((DiscussViewHolder) holder)
                        .mImgDiscussAvatar
                        .getContext())
                        .load(list.get(position).getAvatar())
                        .error(R.drawable.ic_avatar_primary)
                        .crossFade()
                        .into(((DiscussViewHolder) holder)
                                .mImgDiscussAvatar);
                break;
            case Memory.MEMORY_TYPE_PIC:
                ((MemoryViewHolder) holder).mTvMemoryPickname
                        .setText(list.get(position).getPickname());
                Glide.with(((MemoryViewHolder) holder)
                        .mImgMemoryAvatar
                        .getContext())
                        .load(list.get(position).getAvatar())
                        .error(R.drawable.ic_avatar_primary)
                        .crossFade()
                        .into(((MemoryViewHolder) holder)
                                .mImgMemoryAvatar);
                Glide.with(((MemoryViewHolder) holder)
                        .mImgMemoryMemory
                        .getContext())
                        .load(list.get(position).getPicture())
                        .error(R.drawable.ic_avatar_primary)
                        .crossFade()
                        .into(((MemoryViewHolder) holder)
                                .mImgMemoryMemory);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public List<Memory> getList() {
        return list;
    }

    public void setList(List<Memory> list) {
        this.list = list;
    }

    class MemoryViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgMemoryAvatar;
        TextView mTvMemoryPickname;
        ImageView mImgMemoryMemory;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            mImgMemoryAvatar = (ImageView)
                    itemView.findViewById(R.id.img_memory_item_avatar);
            mTvMemoryPickname = (TextView)
                    itemView.findViewById(R.id.tv_memory_item_pickname);
            mImgMemoryMemory = (ImageView)
                    itemView.findViewById(R.id.img_memory_item_memory);

        }
    }

    class DiscussViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgDiscussAvatar;
        TextView mTvDiscussPickname;
        TextView mTvDiscussDiscuss;

        public DiscussViewHolder(View itemView) {
            super(itemView);
            mImgDiscussAvatar = (ImageView)
                    itemView.findViewById(R.id.img_discuss_item_avatar);
            mTvDiscussPickname = (TextView)
                    itemView.findViewById(R.id.tv_discuss_item_pickname);
            mTvDiscussDiscuss = (TextView)
                    itemView.findViewById(R.id.tv_discuss_item_discuss);
        }
    }

}