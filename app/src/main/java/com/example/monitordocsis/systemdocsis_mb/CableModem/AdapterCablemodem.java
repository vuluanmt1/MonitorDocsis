package com.example.monitordocsis.systemdocsis_mb.CableModem;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.systemdocsis_mb.Node.FragmentNodeCurrent;
import com.example.monitordocsis.ui.gpon.fragment_canhbao_onu;

import java.util.ArrayList;
import java.util.List;

public class AdapterCablemodem extends RecyclerView.Adapter<AdapterCablemodem.ViewHolder>implements Filterable {
    private Context mContext;
    private List<ModelCablemodem>mList;
    private List<ModelCablemodem>mNewList;

    public AdapterCablemodem(Context mContext, List<ModelCablemodem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mNewList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        view= LayoutInflater.from(mContext).inflate(R.layout.cabmodem_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            ModelCablemodem item = mList.get(position);
            holder.txt_ls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.animator_button(v);
                    String mac = String.valueOf(holder.mac.getText());
                    String address =String.valueOf(holder.address.getText());
                    FragmentCMHis frag_cm_his = new FragmentCMHis();
                    FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    String tag = manager.getClass().getName();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fr_cablemodem,frag_cm_his ,frag_cm_his.getTag());
                    transaction.addToBackStack("frg_cmhis");
                    Bundle bundle = new Bundle();
                    bundle.putString("mac",mac);
                    bundle.putString("address", address);
                    frag_cm_his.setArguments(bundle);
                    transaction.commit();
                    ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Lịch sử CM");
                }
            });
            holder.txt_tt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.animator_button(v);
                    String mac = String.valueOf(holder.mac.getText());
                    String address =String.valueOf(holder.address.getText());
                    FragmentCMCurrent frag_cm_curr = new FragmentCMCurrent();
                    FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    String tag = manager.getClass().getName();
                    transaction.replace(R.id.fr_cablemodem,frag_cm_curr ,frag_cm_curr.getTag());
                    transaction.addToBackStack("frag_cm_crr");
                    Bundle bundle = new Bundle();
                    bundle.putString("mac",mac);
                    bundle.putString("address", address);
                    frag_cm_curr.setArguments(bundle);
                    transaction.commit();
                    ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Tức thời CM");
                }
            });
            holder.node.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.animator_button(v);
                    String cmtsid = String.valueOf(holder.cmts_id.getText());
                    String ifindex =String.valueOf(holder.ifindex.getText());
                    FragmentNodeCurrent frag_node_curr = new FragmentNodeCurrent();
                    FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(new fragment_canhbao_onu());
                    transaction.replace(R.id.fr_cablemodem,frag_node_curr ,frag_node_curr.getTag());
                    transaction.addToBackStack("frag_cm_node");
                    Bundle bundle = new Bundle();
                    bundle.putString("cmtsid",cmtsid);
                    bundle.putString("ifindex", ifindex);
                    frag_node_curr.setArguments(bundle);
                    transaction.commit();
                    ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Tức thời Node");
                }
            });
        holder.mac.setText(item.getMac());
        holder.address.setText(item.getAddress());
        holder.branch.setText(item.getBranch());
        holder.status.setText(item.getStatus());
        holder.namecmts.setText(item.getNameCMTS());
        holder.node.setText(item.getNode());
        holder.snr.setText(item.getSnr());
        holder.mer.setText(item.getMer());
        holder.fec.setText(item.getFec());
        holder.unfec.setText(item.getUnfec());
        holder.txpw.setText(item.getTxPower());
        holder.rxpw.setText(item.getRxPower());
        holder.time.setText(item.getTime());
        holder.cmts_id.setText(item.getCmtsID());
        holder.ifindex.setText(item.getIfindex());
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
            List<ModelCablemodem> list = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                list.addAll(mNewList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelCablemodem item :mNewList){
                    if(item.getNode().toLowerCase().contains(filterPattern) || item.getBranch().toLowerCase().contains(filterPattern)
                            || item.getMac().toLowerCase().contains(filterPattern)
                            || item.getNameCMTS().toLowerCase().contains(filterPattern)|| item.getAddress().toLowerCase().contains(filterPattern)){
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
        public ImageView txt_ls;
        public ImageView txt_tt;
        public TextView mac;
        public TextView address;
        public TextView branch;
        public TextView status;
        public TextView namecmts;
        public TextView node;
        public TextView snr;
        public TextView mer;
        public TextView fec;
        public TextView unfec;
        public TextView txpw;
        public TextView rxpw;
        public TextView time;
        public TextView cmts_id;
        public TextView ifindex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ls = itemView.findViewById(R.id.txt_ls);
            txt_tt = itemView.findViewById(R.id.txt_tt);
            mac =itemView.findViewById(R.id.txt_mac);
            address =itemView.findViewById(R.id.txt_address);
            status =itemView.findViewById(R.id.txt_status);
            branch =itemView.findViewById(R.id.txt_branch);
            namecmts =itemView.findViewById(R.id.txt_cmts);
            node =itemView.findViewById(R.id.txt_node);
            node.setPaintFlags(node.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            snr =itemView.findViewById(R.id.txt_snr);
            mer =itemView.findViewById(R.id.txt_mer);
            fec =itemView.findViewById(R.id.txt_fec);
            unfec =itemView.findViewById(R.id.txt_unfec);
            txpw =itemView.findViewById(R.id.txt_txpw);
            rxpw =itemView.findViewById(R.id.txt_rxpw);
            time =itemView.findViewById(R.id.txt_time);
            cmts_id = itemView.findViewById(R.id.txt_id_cmts);
            ifindex =itemView.findViewById(R.id.txt_ifindex);
        }
    }
}
