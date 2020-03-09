package com.example.monitordocsis.congcu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monitordocsis.R;

import java.util.List;

public class signalAdapter extends BaseAdapter {
    private List<signalModel> mList;
    private Context mContext;

    public signalAdapter(List<signalModel> mList) {
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
        View view ;
        if(convertView==null){
            convertView= View.inflate(parent.getContext(), R.layout.signal_dialog,null);
        }else {
            view=convertView;
        }
        TextView txt_port_pon = convertView.findViewById(R.id.txt_port_pon);
        TextView txt_onu_id = convertView.findViewById(R.id.txt_onu_id);
        TextView txt_status = convertView.findViewById(R.id.txt_status);
        TextView txt_mode = convertView.findViewById(R.id.txt_mode);
        TextView txt_serial = convertView.findViewById(R.id.txt_serial);
        TextView txt_link_uptime = convertView.findViewById(R.id.txt_link_uptime);
        TextView txt_rx_power = convertView.findViewById(R.id.txt_rx_power);
        TextView txt_optical = convertView.findViewById(R.id.txt_optical);
        TextView txt_total_rf = convertView.findViewById(R.id.txt_total_rf);
        TextView txt_ping= convertView.findViewById(R.id.txt_ping);

        txt_port_pon.setText(mList.get(position).getPortpon());
        txt_onu_id.setText(mList.get(position).getOnuid());
        txt_status.setText(mList.get(position).getStatus());
        txt_mode.setText(mList.get(position).getMode());
        txt_serial.setText(mList.get(position).getSnonu());
        txt_link_uptime.setText(mList.get(position).getUptime());
        txt_rx_power.setText(mList.get(position).getRxpower());
        txt_optical.setText(mList.get(position).getOptical());
        txt_total_rf.setText(mList.get(position).getTotalrf());
        txt_ping.setText(mList.get(position).getPing());
        return convertView;
    }
}
