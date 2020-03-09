package com.example.monitordocsis.khuvuc;

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

public class areaAdapter extends ArrayAdapter {
    TextView txt_codeArea,txt_nameArea;
    public areaAdapter(Context context, ArrayList<areaModel> areaList){
        super(context,0,areaList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_khuvuc,parent,false);
             txt_codeArea = convertView.findViewById(R.id.txt_makhuvuc);
             txt_nameArea = convertView.findViewById(R.id.txt_khuvuc);
        }
        areaModel item = (areaModel) getItem(position);
        if(item!=null){
            txt_codeArea.setText(item.getCodeArea());
            txt_nameArea.setText(item.getNameArea());
        }
        return convertView;
    }
}
