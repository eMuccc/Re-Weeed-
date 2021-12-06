package com.yunna.cc.reweeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunna.cc.reweeed.R;
import com.yunna.cc.reweeed.bean.SevenDayWeather;
import com.yunna.cc.reweeed.util.ResourceUtil;
import com.yunna.cc.reweeed.util.WeekUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    private final List<SevenDayWeather.DailyDTO> mList;

    public DailyWeatherAdapter(List<SevenDayWeather.DailyDTO> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_daily_week;
        TextView tv_text;
        ImageView iv_daily_img;
        TextView tv_daily_temp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_daily_week= itemView.findViewById(R.id.tv_daily_week_view);
            tv_text = itemView.findViewById(R.id.tv_daily_text_view);
            iv_daily_img = itemView.findViewById(R.id.iv_daily_img_weather);
            tv_daily_temp = itemView.findViewById(R.id.tv_daily_temp_view);

        }
    }

    @NonNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.ViewHolder holder, int position) {
        holder.tv_daily_week.setText(WeekUtil.getWeek(mList.get(position).getFxDate()));
        holder.tv_text.setText(mList.get(position).getTextDay());
        holder.tv_daily_temp.setText(mList.get(position).getTempMin()+"°/"+mList.get(position).getTempMax()+"°");
        holder.iv_daily_img.setImageResource(ResourceUtil.getIconResource(mList.get(position).getIconDay()));

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
}
