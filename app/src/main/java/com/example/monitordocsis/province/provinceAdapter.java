package com.example.monitordocsis.province;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monitordocsis.R;

import java.util.ArrayList;

public class provinceAdapter extends ArrayAdapter {
    TextView txt_province;
    public provinceAdapter (Context context , ArrayList<provinceModel>provinceList){
        super(context,0,provinceList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return intview(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return intview(position, convertView, parent);
    }
    private View intview(int position, View convertView, ViewGroup parent){
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_province,parent,false);
            txt_province = convertView.findViewById(R.id.txt_province);
        }
        provinceModel item = (provinceModel) getItem(position);
        if(item!=null){
            txt_province.setText(item.getProvince());
        }
        return convertView;
    }
}
