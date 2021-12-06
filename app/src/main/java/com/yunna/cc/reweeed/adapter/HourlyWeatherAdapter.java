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

/**

        switch (mList.get(position).getIcon()) {
            case "100":
                holder.iv_status.setImageResource(R.drawable.ic_weather_100);
                break;
            case "101":
                holder.iv_status.setImageResource(R.drawable.ic_weather_101);
                break;
            case "102":
                holder.iv_status.setImageResource(R.drawable.ic_weather_102);
                break;
            case "103":
                holder.iv_status.setImageResource(R.drawable.ic_weather_103);
                break;
            case "104":
            case "154":
                holder.iv_status.setImageResource(R.drawable.ic_weather_104);
                break;
            case "150":
                holder.iv_status.setImageResource(R.drawable.ic_weather_150);
                break;
            case "151":
                holder.iv_status.setImageResource(R.drawable.ic_weather_151);
                break;
            case "152":
                holder.iv_status.setImageResource(R.drawable.ic_weather_152);
                break;
            case "153":
                holder.iv_status.setImageResource(R.drawable.ic_weather_153);
                break;

            case "300":
                holder.iv_status.setImageResource(R.drawable.ic_weather_300);
                break;
            case "301":
                holder.iv_status.setImageResource(R.drawable.ic_weather_301);
                break;
            case "302":
                holder.iv_status.setImageResource(R.drawable.ic_weather_302);
                break;
            case "303":
                holder.iv_status.setImageResource(R.drawable.ic_weather_303);
                break;
            case "304":
                holder.iv_status.setImageResource(R.drawable.ic_weather_304);
                break;
            case "305":
                holder.iv_status.setImageResource(R.drawable.ic_weather_305);
                break;
            case "306":
                holder.iv_status.setImageResource(R.drawable.ic_weather_306);
                break;
            case "307":
                holder.iv_status.setImageResource(R.drawable.ic_weather_307);
                break;
            case "308":
                holder.iv_status.setImageResource(R.drawable.ic_weather_308);
                break;
            case "309":
                holder.iv_status.setImageResource(R.drawable.ic_weather_309);
                break;
            case "310":
                holder.iv_status.setImageResource(R.drawable.ic_weather_310);
                break;
            case "311":
                holder.iv_status.setImageResource(R.drawable.ic_weather_311);
                break;
            case "312":
                holder.iv_status.setImageResource(R.drawable.ic_weather_312);
                break;
            case "313":
                holder.iv_status.setImageResource(R.drawable.ic_weather_313);
                break;
            case "314":
                holder.iv_status.setImageResource(R.drawable.ic_weather_314);
                break;
            case "315":
                holder.iv_status.setImageResource(R.drawable.ic_weather_315);
                break;
            case "316":
                holder.iv_status.setImageResource(R.drawable.ic_weather_316);
                break;
            case "317":
                holder.iv_status.setImageResource(R.drawable.ic_weather_317);
                break;
            case "318":
                holder.iv_status.setImageResource(R.drawable.ic_weather_318);
                break;

            case "350":
                holder.iv_status.setImageResource(R.drawable.ic_weather_350);
                break;
            case "351":
                holder.iv_status.setImageResource(R.drawable.ic_weather_351);
                break;

            case "399":
                holder.iv_status.setImageResource(R.drawable.ic_weather_399);
                break;
            case "400":
                holder.iv_status.setImageResource(R.drawable.ic_weather_400);
                break;
            case "401":
                holder.iv_status.setImageResource(R.drawable.ic_weather_401);
                break;
            case "402":
                holder.iv_status.setImageResource(R.drawable.ic_weather_402);
                break;
            case "403":
                holder.iv_status.setImageResource(R.drawable.ic_weather_403);
                break;
            case "404":
                holder.iv_status.setImageResource(R.drawable.ic_weather_404);
                break;
            case "405":
                holder.iv_status.setImageResource(R.drawable.ic_weather_405);
                break;
            case "406":
                holder.iv_status.setImageResource(R.drawable.ic_weather_406);
                break;
            case "407":
                holder.iv_status.setImageResource(R.drawable.ic_weather_407);
                break;
            case "408":
                holder.iv_status.setImageResource(R.drawable.ic_weather_408);
                break;
            case "409":
                holder.iv_status.setImageResource(R.drawable.ic_weather_409);
                break;
            case "410":
                holder.iv_status.setImageResource(R.drawable.ic_weather_410);
                break;

            case "456":
                holder.iv_status.setImageResource(R.drawable.ic_weather_456);
                break;
            case "457":
                holder.iv_status.setImageResource(R.drawable.ic_weather_457);
                break;

            case "499":
                holder.iv_status.setImageResource(R.drawable.ic_weather_499);
                break;
            case "500":
                holder.iv_status.setImageResource(R.drawable.ic_weather_500);
                break;
            case "501":
                holder.iv_status.setImageResource(R.drawable.ic_weather_501);
                break;
            case "502":
                holder.iv_status.setImageResource(R.drawable.ic_weather_502);
                break;
            case "503":
                holder.iv_status.setImageResource(R.drawable.ic_weather_503);
                break;
            case "504":
                holder.iv_status.setImageResource(R.drawable.ic_weather_504);
                break;
            case "507":
                holder.iv_status.setImageResource(R.drawable.ic_weather_507);
                break;
            case "508":
                holder.iv_status.setImageResource(R.drawable.ic_weather_508);
                break;
            case "509":
                holder.iv_status.setImageResource(R.drawable.ic_weather_509);
                break;
            case "510":
                holder.iv_status.setImageResource(R.drawable.ic_weather_510);
                break;
            case "511":
                holder.iv_status.setImageResource(R.drawable.ic_weather_511);
                break;
            case "512":
                holder.iv_status.setImageResource(R.drawable.ic_weather_512);
                break;
            case "513":
                holder.iv_status.setImageResource(R.drawable.ic_weather_513);
                break;
            case "514":
                holder.iv_status.setImageResource(R.drawable.ic_weather_514);
                break;
            case "515":
                holder.iv_status.setImageResource(R.drawable.ic_weather_515);
                break;


            case "800":
                holder.iv_status.setImageResource(R.drawable.ic_weather_800);
                break;
            case "801":
                holder.iv_status.setImageResource(R.drawable.ic_weather_801);
                break;
            case "802":
                holder.iv_status.setImageResource(R.drawable.ic_weather_802);
                break;
            case "803":
                holder.iv_status.setImageResource(R.drawable.ic_weather_803);
                break;
            case "804":
                holder.iv_status.setImageResource(R.drawable.ic_weather_804);
                break;
            case "805":
                holder.iv_status.setImageResource(R.drawable.ic_weather_805);
                break;
            case "806":
                holder.iv_status.setImageResource(R.drawable.ic_weather_806);
                break;
            case "807":
                holder.iv_status.setImageResource(R.drawable.ic_weather_807);
                break;

            case "900":
                holder.iv_status.setImageResource(R.drawable.ic_weather_900);
                break;
            case "901":
                holder.iv_status.setImageResource(R.drawable.ic_weather_901);
                break;
            case "999":
                holder.iv_status.setImageResource(R.drawable.ic_weather_999);
                break;
            default:
                holder.iv_status.setImageResource(R.drawable.ic_launcher_foreground);


        }

*/

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

}
