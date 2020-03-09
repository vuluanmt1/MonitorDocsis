package com.example.monitordocsis.olt;

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

public class oltAdapter extends ArrayAdapter  {
    TextView txt_codeOlt;
    public oltAdapter(Context context, ArrayList<oltModel> oltList){
        super(context,0,oltList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_olt,parent,false);
            txt_codeOlt = convertView.findViewById(R.id.txt_oltID);

        }
        oltModel item = (oltModel) getItem(position);
        if(item!=null){
            txt_codeOlt.setText(item.getOltID());
        }
        return convertView;
    }
}
