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

public class branchAdapter extends ArrayAdapter<branchModel> {
    public branchAdapter(Context context, List<branchModel> branchList){
        super(context,0,branchList);
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
    private View initView (int position , View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_branch,parent,false);
        }
        TextView txt_code = convertView.findViewById(R.id.txt_code);
        TextView txt_des =convertView.findViewById(R.id.txt_des);
        branchModel item  = getItem(position);
        if(convertView!=null){
            txt_code.setText(item.getCode());
            txt_des.setText(item.getDescription());
        }
        return convertView;
    }
}
