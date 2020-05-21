package com.example.monitordocsis.systemdocsis_mb.Node;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.systemdocsis_mb.CableModem.FragmentCMNode;
import com.example.monitordocsis.ui.gpon.fragment_canhbao_onu;

import java.util.ArrayList;
import java.util.List;

public class AdapterNode extends RecyclerView.Adapter<AdapterNode.ViewHolder> implements Filterable {
    Context mContext;
    private List<ModelNode> mList;
    private List<ModelNode>mNewList;

    public AdapterNode(Context mContext, List<ModelNode> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mNewList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(mContext).inflate(R.layout.upstream_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ModelNode item =mList.get(position);
        holder.txt_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmtsid = String.valueOf(holder.cmts_id.getText());
                String ifindex =String.valueOf(holder.ifindex.getText());
                FragmentNodeCurrent fragmentNodeCurrent = new FragmentNodeCurrent();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node,fragmentNodeCurrent ,fragmentNodeCurrent.getTag());
                transaction.addToBackStack(null);
                Bundle  bundle = new Bundle();
                bundle.putString("cmtsid",cmtsid);
                bundle.putString("ifindex", ifindex);
                fragmentNodeCurrent.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Tức thời Node");
            }
        });
        holder.txt_ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmst = String.valueOf(holder.cmts_id.getText());
                String ifindex = String.valueOf(holder.ifindex.getText());
                String node_name =String.valueOf(holder.node.getText());
                FragmentNodeHis fragment_his = new FragmentNodeHis();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node,fragment_his ,fragment_his.getTag());
                transaction.addToBackStack(null);
                Bundle  bundle = new Bundle();
                bundle.putString("cmtsid",cmst);
                bundle.putString("ifindex", ifindex);
                bundle.putString("nodename", node_name);
                fragment_his.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Lịch sử Node");
            }
        });
        holder.node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmst = String.valueOf(holder.cmts_id.getText());
                String ifindex = String.valueOf(holder.ifindex.getText());
                FragmentCMNode fragment_CM_Node = new FragmentCMNode();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node,fragment_CM_Node ,fragment_CM_Node.getTag());
                transaction.addToBackStack(null);
                Bundle  bundle = new Bundle();
                bundle.putString("cmtsid",cmst);
                bundle.putString("ifindex", ifindex);
                fragment_CM_Node.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Thông tin CM ở Node");
            }
        });
        holder.node.setText(item.getNode());
        holder.inteface.setText(item.getInteface());
        holder.branch.setText(item.getBranch());
        holder.snr.setText(item.getSnr());
        holder.mer.setText(item.getMer());
        holder.fec.setText(item.getFec());
        holder.unfec.setText(item.getUnfec());
        holder.txpw.setText(item.getTxpower());
        holder.rxpw.setText(item.getRxpower());
        holder.act.setText(item.getAct());
        holder.total.setText(item.getTotal());
        holder.reg.setText(item.getReg());
        holder.fre.setText(item.getFre());
        holder.wth.setText(item.getWith());
        holder.namecmts.setText(item.getNameCMTS());
        holder.time.setText(item.getModifileDate());
        holder.cmts_id.setText(item.getCmtsid());
        holder.ifindex.setText(item.getIfindex());
        // Set background in recycle item
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
            holder.snr.setBackgroundResource(R.color.colorLightGray);
        }
        if(mer >=0 && mer <=28 ){
            holder.mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >28 && mer <=31){
            holder.mer.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.mer.setBackgroundResource(R.color.colorLightGray);
        }
        if(fec >=100){
            holder.fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            holder.fec.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.fec.setBackgroundResource(R.color.colorLightGray);
        }
        if(unfec >=80){
            holder.unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >2 && unfec <80){
            holder.unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.unfec.setBackgroundResource(R.color.colorLightGray);
        }
        if(tx >=60 || tx <18){
            holder.txpw.setBackgroundResource(R.color.colorRed);
        }else if(tx >=58 && tx <60){
            holder.txpw.setBackgroundResource(R.color.colorYellow);
        }else {
            holder.txpw.setBackgroundResource(R.color.colorLightGray);
        }
        if(rx >40 || rx <-24){
            holder.rxpw.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40){
            holder.rxpw.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.rxpw.setBackgroundResource(R.color.colorLightGray);
        }
        if(peractive<=30){
            holder.act.setBackgroundResource(R.color.colorRed);
        }else if(peractive >30 && peractive <=70){
            holder.act.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.act.setBackgroundResource(R.color.colorLightGray);
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
    private Filter exFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelNode> modelNodes = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                modelNodes.addAll(mNewList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelNode item :mNewList){
                    if(item.getNode().toLowerCase().contains(filterPattern) || item.getBranch().toLowerCase().contains(filterPattern)|| item.getInteface().toLowerCase().contains(filterPattern)|| item.getNameCMTS().toLowerCase().contains(filterPattern)){
                        modelNodes.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= modelNodes;
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
        public TextView node;
        public TextView inteface;
        public TextView branch;
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
        public TextView namecmts;
        public TextView time;
        public TextView cmts_id;
        public TextView ifindex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ls = itemView.findViewById(R.id.txt_ls);
            txt_tt = itemView.findViewById(R.id.txt_tt);
            node =itemView.findViewById(R.id.txt_node);
            node.setPaintFlags(node.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            inteface =itemView.findViewById(R.id.txt_inteface);
            branch =itemView.findViewById(R.id.txt_branch);
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
            namecmts =itemView.findViewById(R.id.txt_cmts);
            time =itemView.findViewById(R.id.txt_time);
            cmts_id = itemView.findViewById(R.id.txt_id_cmts);
            ifindex =itemView.findViewById(R.id.txt_ifindex);
        }
    }

}
