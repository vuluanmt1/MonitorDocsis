package com.example.monitordocsis.ui.ticket;

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

public class ticketOnuAdapter extends RecyclerView.Adapter<ticketOnuAdapter.ViewHolder> {
    private Context mContext;
    private List<ticketOnuModel> mListTicketOnu;
    private List<ticketOnuModel> mNewListTicketOnu;

    public ticketOnuAdapter(Context mContext, List<ticketOnuModel> mListTicketOnu) {
        this.mContext = mContext;
        this.mListTicketOnu = mListTicketOnu;
        mNewListTicketOnu = new ArrayList<>(mListTicketOnu);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.ticket_onu,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ticketOnuModel item = mListTicketOnu.get(position);
        holder.txt_maonu.setText(item.getMaonu());
        holder.txt_status.setText(item.getStatus());
        holder.txt_rxpower.setText(item.getRxpower());
        holder.txt_inact_time.setText(item.getInactTime());
        holder.txt_deatc_reason.setText(item.getDeactReason());
        holder.txt_address.setText(item.getAddress());
        holder.txt_status_subnum.setText(item.getServiceStatus());
        holder.txt_time.setText(item.getCreateDate());
        int status = Integer.parseInt(holder.txt_status.getText().toString());
        if(status==2){
            holder.txt_status.setText("Active");
            holder.txt_status.setBackgroundResource(R.color.colorGreen);
            holder.txt_status.setTextColor(Color.BLACK);
        }else{
            holder.txt_status.setText("Inactive");
            holder.txt_status.setBackgroundResource(R.color.colorRed);
            holder.txt_status.setTextColor(Color.BLACK);
        }
        Double rx = Double.parseDouble(holder.txt_rxpower.getText().toString());
        if(rx <=-8 && rx >=-28.5){
            holder.txt_rxpower.setBackgroundResource(R.color.colorGreen);
            holder.txt_rxpower.setTextColor(Color.BLACK);
        }else if(rx >-8 || rx <-28.5){
            holder.txt_rxpower.setBackgroundResource(R.color.colorRed);
            holder.txt_rxpower.setTextColor(Color.WHITE);
        }
        Integer deactive = Integer.parseInt(holder.txt_deatc_reason.getText().toString());
        switch (status){
            case 1:
                if(deactive==2){
                    holder.txt_deatc_reason.setText("Mất điện");
                }else if(deactive == 1){
                    holder.txt_deatc_reason.setText("None");
                }else  if(deactive ==100){
                    holder.txt_deatc_reason.setText("Mất quang|LOSS");
                }else if(deactive ==4){
                    holder.txt_deatc_reason.setText("Lỗi quang|LOFI");
                }else  if(deactive == 3){
                    holder.txt_deatc_reason.setText("Lỗi quang|LOSI");
                }else if(deactive ==21){
                    holder.txt_deatc_reason.setText("Admin Omcc Down");
                }else if(deactive ==10){
                    holder.txt_deatc_reason.setText("Admin reset");
                }
                else if(deactive ==19){
                    holder.txt_deatc_reason.setText("Admin reset");
                }
                break;
            default:
                holder.txt_deatc_reason.setText("-");
        }
        Integer inactive = Integer.parseInt(holder.txt_inact_time.getText().toString());
        int day  = (int) Math.floor(inactive/86400);
        inactive-=day*(86400);
        int house = (int) Math.floor(inactive/3600);
        inactive-=house*3600;
        int minute = (int) Math.floor(inactive/60);
        inactive-=minute*60;
        int second = (int) Math.floor(inactive);
        inactive-=second;
        holder.txt_inact_time.setText(day+"d "+house+"h "+minute+"m "+second+"s");
    }
    @Override
    public int getItemCount() {
        return mListTicketOnu.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_maonu;
        private TextView txt_status;
        private TextView txt_rxpower;
        private TextView txt_inact_time;
        private TextView txt_deatc_reason;
        private TextView txt_address;
        private TextView txt_status_subnum;
        private TextView txt_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maonu =itemView.findViewById(R.id.txt_onu_serial);
            txt_status =itemView.findViewById(R.id.txt_status);
            txt_rxpower =itemView.findViewById(R.id.txt_rxpw);
            txt_inact_time =itemView.findViewById(R.id.txt_inact_time);
            txt_deatc_reason =itemView.findViewById(R.id.txt_deact);
            txt_address =itemView.findViewById(R.id.txt_address);
            txt_status_subnum =itemView.findViewById(R.id.txt_status_subnum);
            txt_time =itemView.findViewById(R.id.txt_time);
        }
    }
}
