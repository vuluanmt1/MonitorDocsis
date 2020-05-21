package com.example.monitordocsis.ui.ticket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;

import java.util.ArrayList;
import java.util.List;

public class ticketAdapter extends RecyclerView.Adapter<ticketAdapter.ViewHolder>implements Filterable {
    Context mContext;
   private List<ticketModel>mListTicket;
   private List<ticketModel>mNewListTicket;

    public ticketAdapter(Context mContext, List<ticketModel> mListTicket) {
        this.mContext = mContext;
        this.mListTicket = mListTicket;
        mNewListTicket = new ArrayList<>(mListTicket);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mContext).inflate(R.layout.ticket_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ticketModel item  = mListTicket.get(position);
        holder.txt_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String maolt = String.valueOf(holder.txt_maolt.getText());
                String port = String.valueOf(holder.txt_portpon.getText());
                String area = String.valueOf(holder.txt_area.getText());
                Intent intent = new Intent(mContext,layout_ticket.class);
                FragmentTicketOnu fragTicketOnu = new FragmentTicketOnu();
                FragmentTicketPort fragTicketPort = new FragmentTicketPort();
                Bundle bundle = new Bundle();
                bundle.putString("maolt",maolt);
                bundle.putString("port",port);
                bundle.putString("area",area);
                intent.putExtra("data",bundle);
                fragTicketOnu.setArguments(bundle);
                fragTicketPort.setArguments(bundle);
                v.getContext().startActivities(new Intent[]{intent});
            }
        });
        holder.txt_leve.setText(item.getLeve());
        holder.txt_time.setText(item.getTime());
        holder.txt_banch.setText(item.getBranch());
        holder.txt_deactive_reason.setText(item.getDeact_time());
        holder.txt_maolt.setText(item.getMaolt());
        holder.txt_portpon.setText(item.getPortpon());
        holder.txt_onu_inact.setText(item.getOnuInactive());
        holder.txt_onu_act.setText(item.getOnuActive());
        holder.txt_total_onu.setText(item.getTotalONU());
        holder.txt_percent_inact.setText(item.getPercentOnuInAct());
        holder.txt_avg_rx_onu.setText(item.getAvgRxOnuAct());
        holder.txt_node_quang.setText(item.getNameNodeQuang());
        holder.txt_maticket.setText(item.getMaTicket());
        holder.txt_area.setText(item.getArea());
        holder.txt_province.setText(item.getProvince());
        int leve = Integer.parseInt(holder.txt_leve.getText().toString());
        switch (leve){
            case 1:
                holder.txt_leve.setBackgroundResource(R.color.colorGreen);
                holder.txt_leve.setTextColor(Color.BLACK);
                break;
            case 2:
                holder.txt_leve.setBackgroundResource(R.color.colorYellow);
                holder.txt_leve.setTextColor(Color.BLACK);
                break;
            case 3:
                holder.txt_leve.setBackgroundResource(R.color.colorRed);
                holder.txt_leve.setTextColor(Color.BLACK);
                break;
        }
        int percent_inact = Integer.parseInt(holder.txt_percent_inact.getText().toString());
        if(percent_inact >30 && percent_inact <50){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorYellow);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
        }else if(percent_inact >=50 && percent_inact<70){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorOrange);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
        }else if(percent_inact >=70){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorRed);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
        }else{
            holder.txt_percent_inact.setBackgroundResource(R.color.colorLightGray);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
        }
        String deact_reason = holder.txt_deactive_reason.getText().toString();
        String reason="";
        if(deact_reason!=null){
            String []split_deact = deact_reason.split("|");
            for (int i =0; i < split_deact.length-1; i ++){
                switch (split_deact[i]){
                    case "1":
                        reason+="None|";
                        break;
                    case "2":
                        reason+="Mất điện|";
                        break;
                    case "3":
                        reason+="Lỗi quang(LOSI)|";
                        break;
                    case "4":
                        reason+="Lỗi quang(LOFI)|";
                        break;
                    case "10":
                        reason+="Admin reset|";
                        break;
                    case "19":
                        reason+="Admin reset|";
                        break;
                    case "21":
                        reason+="Admin Occ Down|";
                        break;
                    case "100":
                        reason+="Mất quang(LOS)|";
                        break;
                    default:
                        reason="";
                        break;
                }
            }
            holder.txt_deactive_reason.setText(reason);
        }else{
            reason="";
            holder.txt_deactive_reason.setText(reason);
        }
        String node_quang = holder.txt_node_quang.getText().toString();
        if(node_quang.contains("null")){
            holder.txt_node_quang.setText("-");
        }
    }
    @Override
    public int getItemCount() {
        return mListTicket.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }
    private Filter exFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ticketModel> filteredList = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                filteredList.addAll(mNewListTicket);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ticketModel item :mNewListTicket){
                    if(item.getMaolt().toLowerCase().contains(filterPattern) || item.getLeve().contains(filterPattern)
                            || item.getDeact_time().toLowerCase().contains(filterPattern) || item.getBranch().toLowerCase().contains(filterPattern)
                            || item.getPortpon().toLowerCase().contains(filterPattern)||  item.getNameNodeQuang().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListTicket.clear();
            mListTicket.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
    public static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txt_tools;
            private TextView txt_leve;
            private TextView txt_time;
            private TextView txt_banch;
            private TextView txt_deactive_reason;
            private TextView txt_maolt;
            private TextView txt_portpon;
            private TextView txt_onu_act;
            private TextView txt_onu_inact;
            private TextView txt_total_onu;
            private TextView txt_percent_inact;
            private TextView txt_avg_rx_onu;
            private TextView txt_node_quang;
            private TextView txt_maticket;
            private TextView txt_area;
            private TextView txt_province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tools =itemView.findViewById(R.id.txt_tool);
            txt_leve =itemView.findViewById(R.id.txt_leve);
            txt_time =itemView.findViewById(R.id.txt_time_start_ticket);
            txt_banch =itemView.findViewById(R.id.txt_chinhanh);
            txt_deactive_reason =itemView.findViewById(R.id.txt_nguyennhan);
            txt_maolt =itemView.findViewById(R.id.txt_maolt);
            txt_portpon =itemView.findViewById(R.id.txt_port_pon);
            txt_onu_act =itemView.findViewById(R.id.txt_onu_act);
            txt_onu_inact =itemView.findViewById(R.id.txt_onu_inact);
            txt_total_onu =itemView.findViewById(R.id.txt_total_onu);
            txt_percent_inact =itemView.findViewById(R.id.txt_percent_onu_inact);
            txt_avg_rx_onu =itemView.findViewById(R.id.txt_Avg_rxpower);
            txt_node_quang =itemView.findViewById(R.id.txt_node_quang);
            txt_maticket =itemView.findViewById(R.id.txt_maticket);
            txt_area =itemView.findViewById(R.id.txt_area);
            txt_province =itemView.findViewById(R.id.txt_province);
        }
    }
}
