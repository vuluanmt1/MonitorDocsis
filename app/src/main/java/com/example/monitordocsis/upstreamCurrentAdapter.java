package com.example.monitordocsis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class upstreamCurrentAdapter extends BaseAdapter {
    private List<upstreamCurrentModel> mList;
    private Context mContext;
    upstreamCurrentAdapter(List<upstreamCurrentModel>list){
        this.mList =list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        if(convertView==null){
            convertView= View.inflate(parent.getContext(),R.layout.upstream_current,null);
        }else {
            view=convertView;
        }
        upstreamCurrentModel item = (upstreamCurrentModel) getItem(position);
        Button btn_ls_node = convertView.findViewById(R.id.btn_ls_node);

        final TextView txt_node = convertView.findViewById(R.id.txt_node_current);
        TextView txt_ifdesc = convertView.findViewById(R.id.txt_ifdesc_current);
        TextView txt_snr = convertView.findViewById(R.id.txt_snr_current);
        TextView txt_mer = convertView.findViewById(R.id.txt_mer_current);
        TextView txt_fec = convertView.findViewById(R.id.txt_fec_current);
        TextView txt_unfec = convertView.findViewById(R.id.txt_unfec_current);
        TextView txt_tx = convertView.findViewById(R.id.txt_tx_current);
        TextView txt_rx = convertView.findViewById(R.id.txt_rx_current);
        TextView txt_act = convertView.findViewById(R.id.txt_act_curent);
        TextView txt_total = convertView.findViewById(R.id.txt_total_curent);
        TextView txt_reg = convertView.findViewById(R.id.txt_reg_curent);
        TextView txt_fre = convertView.findViewById(R.id.txt_fre_curent);
        TextView txt_with = convertView.findViewById(R.id.txt_with_curent);
        TextView txt_micref = convertView.findViewById(R.id.txt_micref_curent);
        TextView txt_mod = convertView.findViewById(R.id.txt_mode_curent);
        TextView txt_time = convertView.findViewById(R.id.txt_time_current);
        final TextView txt_cmtsid = convertView.findViewById(R.id.txt_cmtsid_current);
        final TextView txt_ifindex = convertView.findViewById(R.id.txt_ifindex_current);
        txt_node.setText(mList.get(position).getNode());
        txt_ifdesc.setText(mList.get(position).getIfdesc());
        txt_snr.setText(mList.get(position).getSnr());
        txt_mer.setText(mList.get(position).getMer());
        txt_fec.setText(mList.get(position).getFec());
        txt_unfec.setText(mList.get(position).getUnfec());
        txt_tx.setText(mList.get(position).getTx());
        txt_rx.setText(mList.get(position).getRx());
        txt_act.setText(mList.get(position).getAct());
        txt_total.setText(mList.get(position).getTotal());
        txt_reg.setText(mList.get(position).getReg());
        txt_fre.setText(mList.get(position).getFre());
        txt_with.setText(mList.get(position).getWth());
        txt_micref.setText(mList.get(position).getMicref());
        txt_mod.setText(mList.get(position).getMod());
        txt_time.setText(mList.get(position).getTime());
        txt_cmtsid.setText(mList.get(position).getCmtsid());
        txt_ifindex.setText(mList.get(position).getIfindex());
        Double snr = Double.parseDouble(mList.get(position).getSnr());
        Double mer = Double.parseDouble(mList.get(position).getMer());
        Double fec = Double.parseDouble(mList.get(position).getFec());
        Double unfec = Double.parseDouble(mList.get(position).getUnfec());
        Double tx = Double.parseDouble(mList.get(position).getTx());
        Double rx = Double.parseDouble(mList.get(position).getRx());
        Integer act = Integer.parseInt(mList.get(position).getAct());
        Integer total = Integer.parseInt(mList.get(position).getTotal());
        btn_ls_node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmtsid = String.valueOf(txt_cmtsid.getText());
                String ifindex = String.valueOf(txt_ifindex.getText());
                String node_name = String.valueOf(txt_node.getText());
                Intent intent = new Intent(v.getContext(), UpstreamHisActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cmtsid",cmtsid);
                bundle.putString("ifindex",ifindex);
                bundle.putString("nodename",node_name);
                intent.putExtra("data",bundle);
                v.getContext().startActivities(new Intent[]{new Intent(intent)});
            }
        });
        double peractive=0.0;
        if(total ==0){
            peractive =0.0;
        }else{
            peractive = (act*100/total);
        }
        if(snr<10){
            txt_snr.setBackgroundResource(R.color.colorRed);
        }else if(snr >=10 && snr <=18){
            txt_snr.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_snr.setBackgroundResource(R.color.colorWhite);
        }
        if(mer >=0 && mer <=28 ){
            txt_mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >28 && mer <=31){
            txt_mer.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_mer.setBackgroundResource(R.color.colorWhite);
        }
        if(fec >=100){
            txt_fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            txt_fec.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_fec.setBackgroundResource(R.color.colorWhite);
        }
        if(unfec >=80){
            txt_unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >2 && unfec <80){
            txt_unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_unfec.setBackgroundResource(R.color.colorWhite);
        }
        if(tx >=60 || tx <18){
            txt_tx.setBackgroundResource(R.color.colorRed);
        }else if(tx >=58 && tx <60){
            txt_tx.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_tx.setBackgroundResource(R.color.colorWhite);
        }
        if(rx >40 || rx <-24){
            txt_rx.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_rx.setBackgroundResource(R.color.colorWhite);
        }
        if(peractive<=30){
            txt_act.setBackgroundResource(R.color.colorRed);
        }else if(peractive >30 && peractive <=70){
            txt_act.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_act.setBackgroundResource(R.color.colorWhite);
        }
        return convertView;
    }
}
