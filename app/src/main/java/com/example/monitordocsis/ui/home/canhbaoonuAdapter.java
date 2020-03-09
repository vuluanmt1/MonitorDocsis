package com.example.monitordocsis.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.R;

import java.util.List;

public class canhbaoonuAdapter extends RecyclerView.Adapter<canhbaoonuAdapter.ViewHolder> {
    private Context mContext;
    private List<canhbaoonuModel> models;

    public canhbaoonuAdapter(Context mContext, List<canhbaoonuModel> models) {
        this.mContext = mContext;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.canhbaoonu_table,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         canhbaoonuModel model = models.get(position);
        holder.maolt.setText(model.getMaolt());
                holder.maonu.setText(model.getMaonu());
                holder.portpon.setText(model.getPort());
                holder.onuid.setText(model.getOnuid());
                holder.status.setText(model.getStatus());
                holder.profile.setText(model.getProfile());
                holder.firmware.setText(model.getFirmware());
                holder.rxpower.setText(model.getRx());
                holder.deactReason.setText(model.getDeactiveReason());
                holder.inactTime.setText(model.getInactTime());
                holder.model.setText(model.getModel());
                holder.distance.setText(model.getDistance());
                holder.datetime.setText(model.getDatetime());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView maolt;
        public TextView maonu;
        public TextView portpon;
        public TextView onuid;
        public TextView status;
        public TextView mode;
        public TextView profile;
        public TextView firmware;
        public TextView rxpower;
        public TextView deactReason;
        public TextView inactTime;
        public TextView model;
        public TextView distance;
        public TextView datetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maolt =itemView.findViewById(R.id.txt_maolt);
            maonu =itemView.findViewById(R.id.txt_maonu);
            portpon =itemView.findViewById(R.id.txt_portpon);
            onuid =itemView.findViewById(R.id.txt_onuid);
            status =itemView.findViewById(R.id.txt_status);
            mode =itemView.findViewById(R.id.txt_mode);
            profile =itemView.findViewById(R.id.txt_profile);
            firmware =itemView.findViewById(R.id.txt_firmware);
            rxpower =itemView.findViewById(R.id.txt_rxpw);
            deactReason =itemView.findViewById(R.id.txt_deact);
            inactTime =itemView.findViewById(R.id.txt_intime);
            model =itemView.findViewById(R.id.txt_model);
            distance =itemView.findViewById(R.id.txt_distance);
            datetime =itemView.findViewById(R.id.txt_time);

        }
    }
    public void addData(List<canhbaoonuModel> data) {
        models.addAll(data);
        notifyDataSetChanged();
    }
}
