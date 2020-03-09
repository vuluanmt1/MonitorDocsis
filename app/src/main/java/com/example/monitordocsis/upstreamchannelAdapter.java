package com.example.monitordocsis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class upstreamchannelAdapter extends RecyclerView.Adapter<upstreamchannelAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<upstreamchannelModel> mUpstream;
    private List<upstreamchannelModel> newList;

public upstreamchannelAdapter(List<upstreamchannelModel> mUpstream,final Context mContext){
    this.mUpstream =mUpstream;
    this.mContext =mContext;
    newList =new ArrayList<>(mUpstream);

}
    @NonNull
    @Override
    public upstreamchannelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.upstream_table,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        upstreamchannelModel model = (upstreamchannelModel) mUpstream.get(position);
        holder.imgBtn_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmtsid = String.valueOf(holder.cmts_id.getText());
                String ifindex =String.valueOf(holder.ifindex.getText());
                Intent intent = new Intent(v.getContext(),UpstreamCurrentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cmtsid",cmtsid);
                bundle.putString("ifindex",ifindex);
                intent.putExtra("data",bundle);
                v.getContext().startActivities(new Intent[]{intent});
            }
        });
        holder.imgBtn_ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmst = String.valueOf(holder.cmts_id.getText());
                String ifindex = String.valueOf(holder.ifindex.getText());
                String node_name =String.valueOf(holder.node.getText());
                Intent intent = new Intent(v.getContext(), UpstreamHisActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cmtsid",cmst);
                bundle.putString("ifindex",ifindex);
                bundle.putString("nodename",node_name);
                intent.putExtra("data",bundle);
//               mContext.getApplicationContext().startActivities(new Intent[]{intent});
                v.getContext().startActivities(new Intent[]{new Intent(intent)});
                Log.d("Post",">>"+cmst +"---"+ifindex);
            }
        });
        holder.node.setText(model.getNode());
        holder.inteface.setText(model.getInteface());
        holder.branch.setText(model.getBranch());
        holder.snr.setText(model.getSnr());
        holder.mer.setText(model.getMer());
        holder.fec.setText(model.getFec());
        holder.unfec.setText(model.getUnfec());
        holder.txpw.setText(model.getTxpower());
        holder.rxpw.setText(model.getRxpower());
        holder.act.setText(model.getAct());
        holder.total.setText(model.getTotal());
        holder.reg.setText(model.getReg());
        holder.fre.setText(model.getFre());
        holder.wth.setText(model.getWith());
        holder.namecmts.setText(model.getNameCMTS());
        holder.time.setText(model.getModifileDate());
        holder.cmts_id.setText(model.getCmtsid());
        holder.ifindex.setText(model.getIfindex());
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
        return mUpstream.size();
    }
    @Override
    public Filter getFilter(){
        return  exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<upstreamchannelModel> filteredList = new ArrayList<>();
            if(charSequence==null ||charSequence.length()==0){
                filteredList.addAll(newList);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(upstreamchannelModel item :newList){
                    if(item.getNode().toLowerCase().contains(filterPattern) || item.getBranch().toLowerCase().contains(filterPattern)|| item.getInteface().toLowerCase().contains(filterPattern)|| item.getNameCMTS().toLowerCase().contains(filterPattern)){
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
            mUpstream.clear();
            mUpstream.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
            public ImageButton imgBtn_ls;
            public ImageButton imgBtn_tt;
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
            imgBtn_ls = itemView.findViewById(R.id.btn_ls);
            imgBtn_tt = itemView.findViewById(R.id.btn_tt);
            node =itemView.findViewById(R.id.txt_node);
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
    public void clear (){
        mUpstream.clear();
        notifyDataSetChanged();
    }
}
