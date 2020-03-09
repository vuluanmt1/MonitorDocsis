package com.example.monitordocsis.congcu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monitordocsis.R;

import java.util.List;

public class hopdongAdapter extends BaseAdapter {
    private List<hopdongModel>mList;

    public hopdongAdapter(List<hopdongModel> mList) {
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
            convertView= View.inflate(parent.getContext(), R.layout.contract_dialog,null);
        }else {
            view=convertView;
        }
        TextView txt_nameCustomer = convertView.findViewById(R.id.txt_tenkh);
        TextView txt_branch = convertView.findViewById(R.id.txt_chinhanh);
        TextView txt_phone = convertView.findViewById(R.id.txt_sodt);
        TextView txt_address = convertView.findViewById(R.id.txt_diachi);
        TextView txt_typeContract = convertView.findViewById(R.id.txt_loai_hd);
        TextView txt_statusContract = convertView.findViewById(R.id.txt_tinhtrang);

        txt_nameCustomer.setText(mList.get(position).getNameCustomer());
        txt_branch.setText(mList.get(position).getBranch());
        txt_phone.setText(mList.get(position).getPhone());
        txt_address.setText(mList.get(position).getAddress());
        txt_typeContract.setText(mList.get(position).getTypeContract());
        txt_statusContract.setText(mList.get(position).getStatusContract());

        return convertView;
    }
}
