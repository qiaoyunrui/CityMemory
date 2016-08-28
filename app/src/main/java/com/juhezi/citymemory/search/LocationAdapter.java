package com.juhezi.citymemory.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private static final String TAG = "LocationAdapter";

    private List<Location> list = new ArrayList<>();
    private ClickItemListener listener;

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        holder.mTvTitle.setText(list.get(position).getTitle());
        holder.mTvCityName.setText(list.get(position).getCityName());
        holder.mTvDistance.setText(list.get(position).getDistance() + "m");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickItem(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickItemLIstsner(ClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    public void refresh(List<Location> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        TextView mTvCityName;
        TextView mTvDistance;

        public LocationViewHolder(View itemView) {
            super(itemView);
            mTvCityName = (TextView) itemView.findViewById(R.id.tv_location_city);
            mTvDistance = (TextView) itemView.findViewById(R.id.tv_location_duration);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_location_title);
        }
    }


    interface ClickItemListener {

        void onClickItem(Location location);

    }

}
