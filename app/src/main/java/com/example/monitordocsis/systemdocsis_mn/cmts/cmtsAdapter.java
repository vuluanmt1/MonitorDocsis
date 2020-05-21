package com.example.monitordocsis.systemdocsis_mn.cmts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monitordocsis.R;

import java.util.List;

public class cmtsAdapter extends ArrayAdapter {
    public cmtsAdapter(@NonNull Context context, List<cmtsModel>mListCMTS) {
        super(context, 0, mListCMTS);
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
        cmtsModel item = (cmtsModel) getItem(position);
        if(convertView!=null){
            txt_cmtsid.setText(item.getCmtsID());
            txt_cmtsName.setText(item.getCmtsTitle());
        }
        return convertView;
    }
}
