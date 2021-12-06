package com.yunna.cc.reweeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunna.cc.reweeed.R;
import com.yunna.cc.reweeed.bean.HourlyWeatherBean;
import com.yunna.cc.reweeed.util.ResourceUtil;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private final List<HourlyWeatherBean.HourlyDTO> mList;

    public HourlyWeatherAdapter(List<HourlyWeatherBean.HourlyDTO> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tempR;
        TextView tv_timeR;
        ImageView iv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tempR = itemView.findViewById(R.id.tv_temp_view);
            tv_timeR = itemView.findViewById(R.id.tv_time_view);
            iv_status = itemView.findViewById(R.id.iv_status);
        }
    }

    @NonNull
    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherAdapter.ViewHolder holder, int position) {
        holder.tv_tempR.setText(" " + mList.get(position).getTemp() + "°C");
        holder.tv_timeR.setText(mList.get(position).getFxTime().substring(11, 16));
        holder.iv_status.setImageResource(ResourceUtil.getIconResource(mList.get(position).getIcon()));
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

}
