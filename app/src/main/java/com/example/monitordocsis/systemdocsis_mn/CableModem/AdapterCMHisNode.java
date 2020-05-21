package com.example.monitordocsis.systemdocsis_mn.CableModem;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCMHisNode extends RecyclerView.Adapter<AdapterCMHisNode.ViewHolder>implements Filterable {
    View view;
    private Context mContext;
    private List<ModelCMHisNode>mList;
    private List<ModelCMHisNode>mNewList;

    public AdapterCMHisNode(Context mContext, List<ModelCMHisNode> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mNewList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.cablemodem_his_node_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ModelCMHisNode item = mList.get(position);
        holder.mac.setText(item.getMac());
        holder.address.setText(item.getAddress());
        holder.status.setText(item.getStatus());
        holder.snr.setText(item.getSnr());
        holder.mer.setText(item.getMer());
        holder.fec.setText(item.getFec());
        holder.unfec.setText(item.getUnfec());
        holder.txpw.setText(item.getTxPower());
        holder.rxpw.setText(item.getRxPower());
        holder.time.setText(item.getTime());
        Integer status =Integer.parseInt(holder.status.getText().toString());
        Double snr = Double.parseDouble(holder.snr.getText().toString());
        Double mer = Double.parseDouble(holder.mer.getText().toString());
        Double fec = Double.parseDouble(holder.fec.getText().toString());
        Double unfec = Double.parseDouble(holder.unfec.getText().toString());
        Double tx = Double.parseDouble(holder.txpw.getText().toString());
        Double rx = Double.parseDouble(holder.rxpw.getText().toString());
        if(status==6){
            holder.status.setBackgroundResource(R.color.colorGreen);
            holder.status.setText("Online");
            holder.status.setTextColor(Color.BLACK);
        }else{
            holder.status.setBackgroundResource(R.color.colorRed);
            holder.status.setText("Offline");
            holder.status.setTextColor(Color.BLACK);
        }
        if(mer <22 && mer!=12.9){
            holder.mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >=22 && mer <31){
            holder.mer.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.mer.setBackgroundResource(R.color.colorLightGray);
        }
        if(snr <12 ){
            holder.snr.setBackgroundResource(R.color.colorRed);
        }else if(snr >=12 && snr <20){
            holder.snr.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.snr.setBackgroundResource(R.color.colorLightGray);
        }
        if(fec >=100){
            holder.fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            holder.fec.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.fec.setBackgroundResource(R.color.colorLightGray);
        }
        if(unfec >=100){
            holder.unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >=2 && unfec <100){
            holder.unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.unfec.setBackgroundResource(R.color.colorLightGray);
        }
        if(tx >=60 || (tx <18 && tx !=12.9)){
            holder.txpw.setBackgroundResource(R.color.colorRed);
        }else if(tx >=56 && tx <60){
            holder.txpw.setBackgroundResource(R.color.colorYellow);
        }else if(tx >=18 && tx <=40){
            holder.txpw.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.txpw.setBackgroundResource(R.color.colorLightGray);
        }
        if(rx >40 || rx <-24){
            holder.rxpw.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40 && rx !=12.9){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else if(rx >=-24 && rx <=-10){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.rxpw.setBackgroundResource(R.color.colorLightGray);
        }
        final ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        holder.mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mac = holder.mac.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy mã Mac:", mac);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy mã Mac:"+mac,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }
    private Filter exFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelCMHisNode> list = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                list.addAll(mNewList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelCMHisNode item :mNewList){
                    if( item.getMac().toLowerCase().contains(filterPattern)|| item.getAddress().toLowerCase().contains(filterPattern)){
                        list.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= list;
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
        public TextView mac;
        public TextView address;
        public TextView status;
        public TextView snr;
        public TextView mer;
        public TextView fec;
        public TextView unfec;
        public TextView txpw;
        public TextView rxpw;
        public TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mac =itemView.findViewById(R.id.txt_mac);
            address =itemView.findViewById(R.id.txt_address);
            status =itemView.findViewById(R.id.txt_status);
            snr =itemView.findViewById(R.id.txt_snr);
            mer =itemView.findViewById(R.id.txt_mer);
            fec =itemView.findViewById(R.id.txt_fec);
            unfec =itemView.findViewById(R.id.txt_unfec);
            txpw =itemView.findViewById(R.id.txt_txpw);
            rxpw =itemView.findViewById(R.id.txt_rxpw);
            time =itemView.findViewById(R.id.txt_time);
        }
    }
}
