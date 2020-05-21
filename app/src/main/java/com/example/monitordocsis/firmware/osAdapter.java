package com.example.monitordocsis.firmware;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monitordocsis.R;

import java.util.List;

public class osAdapter extends BaseAdapter  {
    private List<osModel>mListOS;

    public osAdapter(List<osModel> mListOS) {
        this.mListOS = mListOS;
    }

    @Override
    public int getCount() {
        return mListOS.size();
    }

    @Override
    public Object getItem(int position) {
        return mListOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        if(convertView==null){
            convertView= View.inflate(parent.getContext(), R.layout.os_table,null);
        }else {
            view=convertView;
        }
        TextView txt_portPon = convertView.findViewById(R.id.txt_port_pon_fw);
        TextView txt_onuID = convertView.findViewById(R.id.txt_onu_id_fw);
        TextView txt_status_upgrade = convertView.findViewById(R.id.txt_status_fw);
        TextView txt_status_os1 = convertView.findViewById(R.id.txt_os1_fw);
        TextView txt_status_os2 = convertView.findViewById(R.id.txt_os2_fw);

        txt_portPon.setText(mListOS.get(position).getPortPon());
        txt_onuID.setText(mListOS.get(position).getOnuID());
        txt_status_upgrade.setText(mListOS.get(position).getUpgradeStatus());
        txt_status_os1.setText(mListOS.get(position).getStatusOS1());
        txt_status_os2.setText(mListOS.get(position).getStatusOS2());
        return convertView;
    }
}
