package com.example.monitordocsis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class nodeAdapter extends ArrayAdapter<nodeModel> {
    public nodeAdapter(Context context , List<nodeModel>list){
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView (int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_node,parent,false);
        }
        TextView txt_ifindex = convertView.findViewById(R.id.txt_ifindex);
        TextView txt_ifalias =convertView.findViewById(R.id.txt_ifalias);
        nodeModel item = getItem(position);
        if(convertView!=null){
            txt_ifindex.setText(item.getIfindex());
            txt_ifalias.setText(item.getIfalias());
        }
        return convertView;
    }
}
