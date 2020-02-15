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

public class cmtsAdapter extends ArrayAdapter<cmtsModel> {
    public cmtsAdapter (Context context, List<cmtsModel>cmtsList){
        super(context,0,cmtsList);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_cmts,parent,false);
        }
        TextView txt_cmtsid = convertView.findViewById(R.id.txt_cmtsid);
        TextView txt_cmtsName =convertView.findViewById(R.id.txt_cmtsName);
        cmtsModel item = getItem(position);
        if(convertView!=null){
            txt_cmtsid.setText(item.getCmtsID());
            txt_cmtsName.setText(item.getCmtsName());
        }
        return convertView;
    }
}
