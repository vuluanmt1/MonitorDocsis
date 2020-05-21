package com.example.monitordocsis.systemdocsis_mn.Node;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.systemdocsis_mn.CableModem.FragmentCMHisNode;
import com.example.monitordocsis.ui.gpon.fragment_canhbao_onu;

import java.util.ArrayList;
import java.util.List;

public class AdapterNodeHis extends RecyclerView.Adapter<AdapterNodeHis.ViewHolder> {
    View view;
    private Context mContext;
    private List<ModelNodeHis>mListNodeHis;
    private List<ModelNodeHis>mNewList;

    public AdapterNodeHis(Context mContext, List<ModelNodeHis> mListNodeHis) {
        this.mContext = mContext;
        this.mListNodeHis = mListNodeHis;
        mNewList = new ArrayList<>(mListNodeHis);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.upstream_his_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ModelNodeHis hisModel = mListNodeHis.get(position);
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmtsid =String.valueOf(holder.cmts_id.getText());
                String ifindex =String.valueOf(holder.ifindex.getText());
                String time =String.valueOf(holder.time.getText());
                FragmentCMHisNode fragment_CMHis_Node = new FragmentCMHisNode();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node_his,fragment_CMHis_Node ,fragment_CMHis_Node.getTag());
                transaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("cmtsid",cmtsid);
                bundle.putString("ifindex", ifindex);
                bundle.putString("time", time);
                fragment_CMHis_Node.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Lịch sử CM ở Node");
            }
        });
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
        return mListNodeHis.size();
    }


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
            time.setPaintFlags(time.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            cmts_id = itemView.findViewById(R.id.txt_id_cmts);
            ifindex =itemView.findViewById(R.id.txt_ifindex);
        }
    }
}

