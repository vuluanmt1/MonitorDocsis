package com.example.monitordocsis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class upstreamHisAdapter extends RecyclerView.Adapter<upstreamHisAdapter.ViewHolder> implements Filterable {
    private List<upstreamHisModel>mList;
    private Context mContext;
    private List<upstreamHisModel>mNewList;


    public upstreamHisAdapter(List<upstreamHisModel>mList, Context mContext){
        this.mList =mList;
        this.mContext =mContext;
        mNewList = new ArrayList<upstreamHisModel>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.upstream_his_table,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        upstreamHisModel hisModel = mList.get(position);
        holder.time.setText(hisModel.getModifileDate());
        holder.snr.setText(hisModel.getSnr());
        holder.mer.setText(hisModel.getMer());
        holder.fec.setText(hisModel.getFec());
        holder.unfec.setText(hisModel.getUnfec());
        holder.txpw.setText(hisModel.getTxpower());
        holder.rxpw.setText(hisModel.getRxpower());
        holder.act.setText(hisModel.getAct());
        holder.total.setText(hisModel.getTotal());
        holder.reg.setText(hisModel.getReg());
        holder.fre.setText(hisModel.getFre());
        holder.wth.setText(hisModel.getWith());
        holder.cmts_id.setText(hisModel.getCmtsid());
        holder.ifindex.setText(hisModel.getIfindex());
        Double srn = Double.parseDouble(holder.snr.getText().toString());
        Double mer = Double.parseDouble(holder.mer.getText().toString());
        Double fec = Double.parseDouble(holder.fec.getText().toString());
        Double unfec = Double.parseDouble(holder.unfec.getText().toString());
        Double tx = Double.parseDouble(holder.txpw.getText().toString());
        Double rx = Double.parseDouble(holder.rxpw.getText().toString());
        Integer total = Integer.parseInt(holder.total.getText().toString());
        Integer active = Integer.parseInt(holder.act.getText().toString());
        double peractive=0.0;
        if(total ==0){
            peractive =0.0;
        }else{
            peractive = (active*100/total);
        }
        if(srn <10){
            holder.snr.setBackgroundResource(R.color.colorRed);
        }else if(srn >=10 && srn <=18){
            holder.snr.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.snr.setBackgroundResource(R.color.colorWhite);
        }
        if(mer >=0 && mer <=28 ){
            holder.mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >28 && mer <=31){
            holder.mer.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.mer.setBackgroundResource(R.color.colorWhite);
        }
        if(fec >=100){
            holder.fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            holder.fec.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.fec.setBackgroundResource(R.color.colorWhite);
        }
        if(unfec >=80){
            holder.unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >2 && unfec <80){
            holder.unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.unfec.setBackgroundResource(R.color.colorWhite);
        }
        if(tx >=60 || tx <18){
            holder.txpw.setBackgroundResource(R.color.colorRed);
        }else if(tx >=58 && tx <60){
            holder.txpw.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.txpw.setBackgroundResource(R.color.colorWhite);
        }
        if(rx >40 || rx <-24){
            holder.rxpw.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.rxpw.setBackgroundResource(R.color.colorWhite);
        }
        if(peractive<=30){
            holder.act.setBackgroundResource(R.color.colorRed);
        }else if(peractive >30 && peractive <=70){
            holder.act.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.act.setBackgroundResource(R.color.colorWhite);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<upstreamHisModel> filteredList = new ArrayList<>();
            if(charSequence==null ||charSequence.length()==0){
                filteredList.addAll(mNewList);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(upstreamHisModel item :mNewList){
                    if(item.getModifileDate().toLowerCase().contains(filterPattern) || item.getSnr().toLowerCase().contains(filterPattern)|| item.getMer().toLowerCase().contains(filterPattern)|| item.getTxpower().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView snr;
        public TextView mer;
        public TextView fec;
        public TextView unfec;
        public TextView txpw;
        public TextView rxpw;
        public TextView act;
        public TextView total;
        public TextView reg;
        public TextView fre;
        public TextView wth;
        public TextView time;
        public TextView cmts_id;
        public TextView ifindex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            snr =itemView.findViewById(R.id.txt_snr);
            mer =itemView.findViewById(R.id.txt_mer);
            fec =itemView.findViewById(R.id.txt_fec);
            unfec =itemView.findViewById(R.id.txt_unfec);
            txpw =itemView.findViewById(R.id.txt_txpw);
            rxpw =itemView.findViewById(R.id.txt_rxpw);
            act =itemView.findViewById(R.id.txt_act);
            total =itemView.findViewById(R.id.txt_total);
            reg =itemView.findViewById(R.id.txt_reg);
            fre =itemView.findViewById(R.id.txt_fre);
            wth =itemView.findViewById(R.id.txt_wth);
            time =itemView.findViewById(R.id.txt_time);
            cmts_id = itemView.findViewById(R.id.txt_id_cmts);
            ifindex =itemView.findViewById(R.id.txt_ifindex);
        }
    }
}
