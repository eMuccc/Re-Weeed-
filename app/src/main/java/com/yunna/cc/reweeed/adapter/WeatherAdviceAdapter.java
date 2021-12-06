package com.yunna.cc.reweeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liys.view.WaterWaveProView;
import com.yunna.cc.reweeed.R;
import com.yunna.cc.reweeed.bean.WeatherAdviceBean;

import java.util.List;

public class WeatherAdviceAdapter extends RecyclerView.Adapter<WeatherAdviceAdapter.ViewHolder> {

    private final List<WeatherAdviceBean.DailyDTO> mList;

    public WeatherAdviceAdapter(List<WeatherAdviceBean.DailyDTO> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        WaterWaveProView speed360;
        TextView tv_type;
        TextView tv_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_type = itemView.findViewById(R.id.tv_type);
            speed360 = itemView.findViewById(R.id.speed360);
        }
    }

    @NonNull
    @Override
    public WeatherAdviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_advice_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdviceAdapter.ViewHolder holder, int position) {
        if ("空气污染扩散条件指数".equals(mList.get(position).getName())){
            holder.tv_type.setText("空气污染指数");
            holder.tv_category.setText(mList.get(position).getCategory());
        }else{
            holder.tv_type.setText(mList.get(position).getName());
            holder.tv_category.setText(mList.get(position).getCategory());
        }


        switch (mList.get(position).getName()) {
            case "运动指数":
            case "钓鱼指数":
                holder.speed360.setMaxProgress(3);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;

            case "空调开启指数":
            case "洗车指数":
                holder.speed360.setMaxProgress(4);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;

            case "穿衣指数":
            case "感冒指数":
                holder.speed360.setMaxProgress(7);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;

            case "交通指数":
            case "防晒指数":
            case "空气污染扩散条件指数":
            case "旅游指数":
            case "花粉过敏指数":
            case "紫外线指数":
            case "太阳镜指数":
                holder.speed360.setMaxProgress(5);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;

            case "化妆指数":
                holder.speed360.setMaxProgress(8);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;

            case "晾晒指数":
                holder.speed360.setMaxProgress(6);
                holder.speed360.setProgress(Integer.parseInt(mList.get(position).getLevel()));
                break;


        }

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
}
