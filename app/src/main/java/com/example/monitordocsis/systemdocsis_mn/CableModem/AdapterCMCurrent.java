package com.example.monitordocsis.systemdocsis_mn.CableModem;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monitordocsis.R;

import java.util.List;

public class AdapterCMCurrent extends BaseAdapter {
    View view;
    private Context mContext;
    private List<ModelCMCurrent>mList;
    private String version_docsis_mb;

    public AdapterCMCurrent(Context mContext, List<ModelCMCurrent> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        if(convertView==null){
            convertView= View.inflate(parent.getContext(), R.layout.cablemodem_current,null);
        }else {
            view=convertView;
        }
        TextView txt_status = convertView.findViewById(R.id.txt_status_current);
        TextView txt_snr = convertView.findViewById(R.id.txt_snr_current);
        TextView txt_mer = convertView.findViewById(R.id.txt_mer_current);
        TextView txt_fec = convertView.findViewById(R.id.txt_fec_current);
        TextView txt_unfec = convertView.findViewById(R.id.txt_unfec_current);
        TextView txt_tx = convertView.findViewById(R.id.txt_tx_current);
        TextView txt_rx = convertView.findViewById(R.id.txt_rx_current);
        TextView txt_cm_ip_curent = convertView.findViewById(R.id.txt_cm_ip_curent);
        TextView txt_number_cpe_curent = convertView.findViewById(R.id.txt_number_cpe_curent);
        TextView txt_cpe_ip_curent = convertView.findViewById(R.id.txt_cpe_ip_curent);
        TextView txt_cpe_address_curent = convertView.findViewById(R.id.txt_cpe_address_curent);
        TextView txt_address_current = convertView.findViewById(R.id.txt_address_current);
        TextView txt_cmts_curent = convertView.findViewById(R.id.txt_cmts_curent);
        TextView txt_node_curent = convertView.findViewById(R.id.txt_node_curent);
        TextView txt_ifdesc_curent = convertView.findViewById(R.id.txt_ifdesc_curent);
        TextView txt_time = convertView.findViewById(R.id.txt_time_curent);
        txt_ifdesc_curent.setText(mList.get(position).getIfdecs());
        txt_snr.setText(mList.get(position).getSnr());
        txt_mer.setText(mList.get(position).getMer());
        txt_fec.setText(mList.get(position).getFec());
        txt_unfec.setText(mList.get(position).getUnfec());
        txt_tx.setText(mList.get(position).getTxpower());
        txt_rx.setText(mList.get(position).getRxpower());
        txt_status.setText(mList.get(position).getStatus());
        txt_cm_ip_curent.setText(mList.get(position).getCmIPaddress());
        txt_number_cpe_curent.setText(mList.get(position).getNumberOfCPE());
        txt_cpe_ip_curent.setText(mList.get(position).getCPEIp());
        txt_cpe_address_curent.setText(mList.get(position).getCPEAddress());
        txt_address_current.setText(mList.get(position).getAddress());
        txt_cmts_curent.setText(mList.get(position).getNameCMTS());
        txt_time.setText(mList.get(position).getTime());
        txt_node_curent.setText(mList.get(position).getNameNode());
        String status = (mList.get(position).getStatus());
        Double snr = Double.parseDouble(mList.get(position).getSnr());
        Double mer = Double.parseDouble(mList.get(position).getMer());
        Double fec = Double.parseDouble(mList.get(position).getFec());
        Double unfec = Double.parseDouble(mList.get(position).getUnfec());
        Double tx = Double.parseDouble(mList.get(position).getTxpower());
        Double rx = Double.parseDouble(mList.get(position).getRxpower());
        if(status.contains("online")){
            txt_status.setBackgroundResource(R.color.colorGreen);
            txt_status.setText("Online");
            txt_status.setTextColor(Color.BLACK);
        }else if(status.contains("offline")){
            txt_status.setBackgroundResource(R.color.colorRed);
            txt_status.setText("Offline");
            txt_status.setTextColor(Color.BLACK);
        }else if(status.contains("init(io)")){
            txt_status.setBackgroundResource(R.color.colorOrange);
            txt_status.setText("Init (IO)");
            txt_status.setTextColor(Color.BLACK);
        }else if(status.contains("init(d)")){
            txt_status.setBackgroundResource(R.color.colorOrange);
            txt_status.setText("Init (D)");
            txt_status.setTextColor(Color.BLACK);
        }else if(status.contains("init(o)")){
            txt_status.setBackgroundResource(R.color.colorOrange);
            txt_status.setText("Init (O)");
            txt_status.setTextColor(Color.BLACK);
        }else if(status.contains("init(t)")){
            txt_status.setBackgroundResource(R.color.colorOrange);
            txt_status.setText("Init (T)");
            txt_status.setTextColor(Color.BLACK);
        }
        if(mer <22 && mer!=12.9){
            txt_mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >=22 && mer <31){
            txt_mer.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_mer.setBackgroundResource(R.color.colorLightGray);
        }
        if(snr <12 ){
            txt_snr.setBackgroundResource(R.color.colorRed);
        }else if(snr >=12 && snr <20){
            txt_snr.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_snr.setBackgroundResource(R.color.colorLightGray);
        }
        if(fec >=100){
            txt_fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            txt_fec.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_fec.setBackgroundResource(R.color.colorLightGray);
        }
        if(unfec >=100){
            txt_unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >=2 && unfec <100){
            txt_unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_unfec.setBackgroundResource(R.color.colorLightGray);
        }
        if(tx >=60 || (tx <18 && tx !=12.9)){
            txt_tx.setBackgroundResource(R.color.colorRed);
        }else if(tx >=56 && tx <60){
            txt_tx.setBackgroundResource(R.color.colorYellow);
        }else if(tx >=18 && tx <=40){
            txt_tx.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_tx.setBackgroundResource(R.color.colorLightGray);
        }
        if(rx >40 || rx <-24){
            txt_rx.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40 && rx !=12.9){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else if(rx >=-24 && rx <=-10){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_rx.setBackgroundResource(R.color.colorLightGray);
        }
        return convertView;
    }
}
