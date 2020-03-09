package com.example.monitordocsis.congcu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.R;

import java.util.ArrayList;
import java.util.List;

public class lichsuAdapter extends RecyclerView.Adapter<lichsuAdapter.ViewHolder> {
    Context context;
    private List<lichsuModel> mList;
    private List<lichsuModel> newList;

    public lichsuAdapter(Context context, List<lichsuModel> mList) {
        this.context = context;
        this.mList = mList;
        newList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(context).inflate(R.layout.lichsu_onu_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        lichsuModel item  =mList.get(position);
        holder.maolt.setText(item.getMaolt());
        holder.maonu.setText(item.getMaonu());
        holder.port.setText(item.getPort());
        holder.onuid.setText(item.getOnuid());
        holder.status.setText(item.getStatus());
        holder.mode.setText(item.getMode());
        holder.profile.setText(item.getProfile());
        holder.firmware.setText(item.getFirmware());
        holder.rxpower.setText(item.getRx());
        holder.deact.setText(item.getDeactiveReason());
        holder.inacttime.setText(item.getInactTime());
        holder.model.setText(item.getModel());
        holder.distance.setText(item.getDistance());
        holder.time.setText(item.getDatetime());
        Integer status = Integer.parseInt(holder.status.getText().toString());
        Integer mode = Integer.parseInt(holder.mode.getText().toString());
        Double rx = Double.parseDouble(holder.rxpower.getText().toString());
        Integer deactive = Integer.parseInt(holder.deact.getText().toString());
        Integer inactive = Integer.parseInt(holder.inacttime.getText().toString());
        String model = holder.model.getText().toString();
        if(model.isEmpty()){
            holder.model.setText("-");
        }
        if(status==1){
            holder.status.setText("Inactive");
            holder.status.setBackgroundResource(R.color.colorRed);
            holder.status.setTextColor(Color.BLACK);
        }else{
            holder.status.setText("Active");
            holder.status.setBackgroundResource(R.color.colorGreen);
            holder.status.setTextColor(Color.BLACK);
        }
        if(mode==2){
            holder.mode.setText("Auto");
            holder.mode.setBackgroundResource(R.color.colorRed);
            holder.mode.setTextColor(Color.BLACK);
        }else{
            holder.mode.setText("Manual");
            holder.mode.setBackgroundResource(R.color.colorGreen);
            holder.mode.setTextColor(Color.BLACK);
        }
        if(rx <=-8 && rx >=-28.5){
            holder.rxpower.setBackgroundResource(R.color.colorGreen);
            holder.rxpower.setTextColor(Color.BLACK);
        }else if(rx >-8 || rx <-28.5){
            holder.rxpower.setBackgroundResource(R.color.colorRed);
            holder.rxpower.setTextColor(Color.WHITE);
        }
        switch (status){
            case 1:
                if(deactive==2){
                    holder.deact.setText("Mất Nguồn");
                }else if(deactive == 1){
                    holder.deact.setText("None");
                }else  if(deactive ==100){
                    holder.deact.setText("Mất quang|LOSS");
                }else if(deactive ==4){
                    holder.deact.setText("Lỗi quang|LOFI");
                }else  if(deactive == 3){
                    holder.deact.setText("Lỗi quang|LOSI");
                }else if(deactive ==21){
                    holder.deact.setText("Admin Omcc Down");
                }else if(deactive ==10){
                    holder.deact.setText("Admin reset");
                }
                break;
            default:
                holder.deact.setText("-");
        }
        int day  = (int) Math.floor(inactive/86400);
        inactive-=day*(86400);
        int house = (int) Math.floor(inactive/3600);
        inactive-=house*3600;
        int minute = (int) Math.floor(inactive/60);
        inactive-=minute*60;
        int second = (int) Math.floor(inactive);
        inactive-=second;
        holder.inacttime.setText(day+"d "+house+"h "+minute+"m "+second+"s");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView maolt;
        private TextView maonu;
        private TextView port;
        private TextView onuid;
        private TextView status;
        private TextView mode ;
        private TextView profile;
        private TextView firmware;
        private TextView rxpower;
        private TextView deact;
        private TextView inacttime;
        private TextView model;
        private TextView distance;
        private TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maolt =itemView.findViewById(R.id.txt_maolt);
            maonu =itemView.findViewById(R.id.txt_maonu);
            port =itemView.findViewById(R.id.txt_portpon);
            onuid =itemView.findViewById(R.id.txt_onuid);
            status =itemView.findViewById(R.id.txt_status);
            mode =itemView.findViewById(R.id.txt_mode);
            profile =itemView.findViewById(R.id.txt_profile);
            firmware =itemView.findViewById(R.id.txt_firmware);
            rxpower =itemView.findViewById(R.id.txt_rxpw);
            deact = itemView.findViewById(R.id.txt_deact);
            inacttime =itemView.findViewById(R.id.txt_intime);
            model =itemView.findViewById(R.id.txt_model);
            distance =itemView.findViewById(R.id.txt_distance);
            time =itemView.findViewById(R.id.txt_time);
        }
    }
}
