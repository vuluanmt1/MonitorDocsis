package com.example.monitordocsis.ui.ticket.hoanthanh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTicketComplete extends RecyclerView.Adapter<AdapterTicketComplete.ViewHolder>implements Filterable {
    private Context mContext;
    private List<ModelTicketComplete>mList;
    private List<ModelTicketComplete>mNewList;

    public AdapterTicketComplete(Context mContext, List<ModelTicketComplete> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mNewList= new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ticket_complete_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelTicketComplete item = mList.get(position);
        holder.txt_leve.setText(item.getLeve());
        holder.txt_timeErr.setText(item.getTimeErrors());
        holder.txt_timeRecieve.setText(item.getTimeRecieve());
        holder.txt_timeComplete.setText(item.getTimeComplete());
        holder.txt_timeClose.setText(item.getTimeCloseTicket());
        holder.txt_unit_process.setText(item.getUnitProcess());
        holder.txt_deactive_reason.setText(item.getDeact_time());
        holder.txt_maolt.setText(item.getMaolt());
        holder.txt_portpon.setText(item.getPortpon());
        holder.txt_onu_inact.setText(item.getOnuInactive());
        holder.txt_onu_act.setText(item.getOnuActive());
        holder.txt_total_onu.setText(item.getTotalONU());
        holder.txt_percent_inact.setText(item.getPercentOnuInAct());
        holder.txt_avg_rx_onu.setText(item.getAvgRxOnuAct());
        holder.txt_node_quang.setText(item.getNameNodeQuang());
        holder.txt_slONU.setText(item.getSlONU());
        holder.txt_time_repeat.setText(item.getTimeRepeat());
        holder.txt_code_ticket.setText(item.getCodeTicket());
        holder.txt_hoursComplete.setText(item.getHoursComplete());
        holder.txt_result.setText(item.getResult());
        holder.txt_note.setText(item.getNote());
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
        int time_repeat = Integer.parseInt(holder.txt_time_repeat.getText().toString());
        if(time_repeat==1){
            holder.txt_time_repeat.setBackgroundResource(R.color.colorGreen);
            holder.txt_time_repeat.setTextColor(Color.BLACK);
        }else if(time_repeat==2){
            holder.txt_time_repeat.setBackgroundResource(R.color.colorYellow);
            holder.txt_time_repeat.setTextColor(Color.BLACK);
        }else {
            holder.txt_time_repeat.setBackgroundResource(R.color.colorRed);
            holder.txt_time_repeat.setTextColor(Color.BLACK);
        }
        int percent_inact = Integer.parseInt(holder.txt_percent_inact.getText().toString());
        if(percent_inact >30 && percent_inact <50){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorYellow);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
            holder.txt_percent_inact.setText(percent_inact+"%");
        }else if(percent_inact >=50 && percent_inact<70){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorOrange);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
            holder.txt_percent_inact.setText(percent_inact+"%");
        }else if(percent_inact >=70){
            holder.txt_percent_inact.setBackgroundResource(R.color.colorRed);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
            holder.txt_percent_inact.setText(percent_inact+"%");
        }else{
            holder.txt_percent_inact.setBackgroundResource(R.color.colorLightGray);
            holder.txt_percent_inact.setTextColor(Color.BLACK);
            holder.txt_percent_inact.setText(percent_inact+"%");
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
                    case "5":
                        reason+="Lỗi quang(SFI)|";
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
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }
    private Filter exFilter  = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelTicketComplete> filteredList = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                filteredList.addAll(mNewList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelTicketComplete item :mNewList){
                    if(item.getMaolt().toLowerCase().contains(filterPattern) || item.getLeve().contains(filterPattern)
                            || item.getDeact_time().toLowerCase().contains(filterPattern) || item.getUnitProcess().toLowerCase().contains(filterPattern)
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
            mList.clear();
            mList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_leve;
        private TextView txt_code_ticket;
        private TextView txt_timeErr;
        private TextView txt_timeRecieve;
        private TextView txt_timeComplete;
        private TextView txt_timeClose;
        private TextView txt_unit_process;
        private TextView txt_deactive_reason;
        private TextView txt_maolt;
        private TextView txt_portpon;
        private TextView txt_onu_act;
        private TextView txt_onu_inact;
        private TextView txt_total_onu;
        private TextView txt_slONU;
        private TextView txt_percent_inact;
        private TextView txt_avg_rx_onu;
        private TextView txt_node_quang;
        private TextView txt_time_repeat;
        private TextView txt_result;
        private TextView txt_note;
        private TextView txt_hoursComplete;
        private TextView txt_area;
        private TextView txt_province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_leve =itemView.findViewById(R.id.txt_leve);
            txt_timeErr =itemView.findViewById(R.id.txt_time_errors_ticket);
            txt_timeRecieve =itemView.findViewById(R.id.txt_time_recieve_ticket);
            txt_timeComplete =itemView.findViewById(R.id.txt_time_complete_ticket);
            txt_timeClose =itemView.findViewById(R.id.txt_time_close_ticket);
            txt_unit_process =itemView.findViewById(R.id.txt_unit_process);
            txt_deactive_reason =itemView.findViewById(R.id.txt_nguyennhan);
            txt_maolt =itemView.findViewById(R.id.txt_maolt);
            txt_portpon =itemView.findViewById(R.id.txt_port_pon);
            txt_onu_act =itemView.findViewById(R.id.txt_onu_act);
            txt_onu_inact =itemView.findViewById(R.id.txt_onu_inact);
            txt_total_onu =itemView.findViewById(R.id.txt_total_onu);
            txt_percent_inact =itemView.findViewById(R.id.txt_percent_onu_inact);
            txt_avg_rx_onu =itemView.findViewById(R.id.txt_Avg_rxpower);
            txt_node_quang =itemView.findViewById(R.id.txt_node_quang);
            txt_slONU =itemView.findViewById(R.id.txt_sl_onu);
            txt_code_ticket =itemView.findViewById(R.id.txt_code_ticket);
            txt_time_repeat=itemView.findViewById(R.id.txt_repeat_time);
            txt_result=itemView.findViewById(R.id.txt_result);
            txt_note=itemView.findViewById(R.id.txt_note);
            txt_hoursComplete=itemView.findViewById(R.id.txt_hours_complete);
            txt_area =itemView.findViewById(R.id.txt_area);
            txt_province =itemView.findViewById(R.id.txt_province);
        }
    }
}
