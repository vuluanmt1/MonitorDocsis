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

public class ticketPortAdapter extends RecyclerView.Adapter<ticketPortAdapter.ViewHolder> {
    private Context mContext;
    private List<ticketPortModel> mListTicketPort;
    private List<ticketPortModel> mNewListTicketPort;

    public ticketPortAdapter(Context mContext, List<ticketPortModel> mListTicketPort) {
        this.mContext = mContext;
        this.mListTicketPort = mListTicketPort;
        mNewListTicketPort = new ArrayList<>(mListTicketPort);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.ticket_port,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ticketPortModel item = mListTicketPort.get(position);
        holder.txt_onuActive.setText(item.getOnuActive());
        holder.txt_onuInActive.setText(item.getOnuInActive());
        holder.txt_onuTotal.setText(item.getOnuTotal());
        holder.txt_onuPercentInActive.setText(item.getOnuPercentInActive());
        holder.txt_onuAvgActive.setText(item.getOnuAvgActive());
        holder.txt_time.setText(item.getCreateDate());
        holder.txt_slOnuErr.setText(item.getSlOnuErr());
        int percent_inact = Integer.parseInt(holder.txt_onuPercentInActive.getText().toString());
        if(percent_inact >30 && percent_inact <50){
            holder.txt_onuPercentInActive.setBackgroundResource(R.color.colorYellow);
            holder.txt_onuPercentInActive.setTextColor(Color.BLACK);
            holder.txt_onuPercentInActive.setText(percent_inact+"%");
        }else if(percent_inact >=50 && percent_inact<70){
            holder.txt_onuPercentInActive.setBackgroundResource(R.color.colorOrange);
            holder.txt_onuPercentInActive.setTextColor(Color.BLACK);
            holder.txt_onuPercentInActive.setText(percent_inact+"%");
        }else if(percent_inact >=70){
            holder.txt_onuPercentInActive.setBackgroundResource(R.color.colorRed);
            holder.txt_onuPercentInActive.setTextColor(Color.BLACK);
            holder.txt_onuPercentInActive.setText(percent_inact+"%");
        }
        else{
            holder.txt_onuPercentInActive.setBackgroundResource(R.color.colorLightGray);
            holder.txt_onuPercentInActive.setTextColor(Color.BLACK);
            holder.txt_onuPercentInActive.setText(percent_inact+"%");
        }
        Double avgRx = Double.parseDouble(holder.txt_onuAvgActive.getText().toString());
        if(avgRx <=-8 && avgRx >=-28.5){
            holder.txt_onuAvgActive.setBackgroundResource(R.color.colorGreen);
            holder.txt_onuAvgActive.setTextColor(Color.BLACK);
        }else if(avgRx >-8 || avgRx <-28.5){
            holder.txt_onuAvgActive.setBackgroundResource(R.color.colorRed);
            holder.txt_onuAvgActive.setTextColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return mListTicketPort.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txt_onuActive;
            private TextView txt_onuInActive;
            private TextView txt_onuTotal;
            private TextView txt_onuPercentInActive;
            private TextView txt_onuAvgActive;
            private TextView txt_slOnuErr;
            private TextView txt_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_onuActive = itemView.findViewById(R.id.txt_onu_act);
            txt_onuInActive = itemView.findViewById(R.id.txt_onu_inact);
            txt_onuTotal = itemView.findViewById(R.id.txt_total_onu);
            txt_onuPercentInActive = itemView.findViewById(R.id.txt_percent_onu_inact);
            txt_onuAvgActive = itemView.findViewById(R.id.txt_Avg_rxpower);
            txt_slOnuErr = itemView.findViewById(R.id.txt_slOnuErr);
            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }
}
