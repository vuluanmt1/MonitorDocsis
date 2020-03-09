package com.example.monitordocsis.congcu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monitordocsis.R;

import java.util.List;

public class pppoeAdapter extends BaseAdapter {
    private List<pppoeModel> mList;
    private Context mContext;

    public pppoeAdapter(List<pppoeModel> mList) {
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
            convertView= View.inflate(parent.getContext(), R.layout.pppoe_dialog,null);
        }else {
            view=convertView;
        }
        TextView txt_admin = convertView.findViewById(R.id.txt_admin_state);
        TextView txt_status = convertView.findViewById(R.id.txt_status);
        TextView txt_auth = convertView.findViewById(R.id.txt_auth_type);
        TextView txt_username = convertView.findViewById(R.id.txt_username);
        TextView txt_password = convertView.findViewById(R.id.txt_password);
        TextView txt_mac = convertView.findViewById(R.id.txt_macaddress);
        TextView txt_ip_host1 = convertView.findViewById(R.id.txt_ip_host1);
        TextView txt_ip_host2 = convertView.findViewById(R.id.txt_ip_host2);
        TextView txt_config_mask = convertView.findViewById(R.id.txt_config_mask);
        TextView txt_config_getwaye = convertView.findViewById(R.id.txt_config_getway);
        TextView txt_config_primary = convertView.findViewById(R.id.txt_config_primary);
        TextView txt_config_secondary = convertView.findViewById(R.id.txt_config_secondary);
        txt_admin.setText(mList.get(position).getAdminstate());
        txt_status.setText(mList.get(position).getStatus());
        txt_auth.setText(mList.get(position).getAuthtype());
        txt_username.setText(mList.get(position).getUsername());
        txt_password.setText(mList.get(position).getPassword());
        txt_mac.setText(mList.get(position).getMacaddress());
        txt_ip_host1.setText(mList.get(position).getHost1());
        txt_ip_host2.setText(mList.get(position).getHost2());
        txt_config_mask.setText(mList.get(position).getConfigmask());
        txt_config_getwaye.setText(mList.get(position).getConfiggetway());
        txt_config_primary.setText(mList.get(position).getConfigprimarydns());
        txt_config_secondary.setText(mList.get(position).getConfigsecondarydns());

        return convertView;
    }

}
