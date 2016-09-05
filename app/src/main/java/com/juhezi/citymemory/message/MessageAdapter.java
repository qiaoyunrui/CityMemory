package com.juhezi.citymemory.message;

import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Coversation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private static final String TAG = "MessageAdapter";

    private List<Coversation> list = new ArrayList<>();

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getAvatar())
                .crossFade()
                .into(holder.mImgAvatar);
        holder.mTvName.setText(list.get(position).getPickname());
        holder.mTvContent.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Coversation> list) {
        this.list = list;
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        public ImageView mImgAvatar;
        public TextView mTvName;
        public TextView mTvContent;

        public MessageHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_message_avatar);
            mTvName = (TextView) itemView.findViewById(R.id.tv_message_name);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_message_content);
        }
    }

}
