package com.example.monitordocsis.firmware;

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

public class firmwareAdapter extends ArrayAdapter {
    TextView txt_firmware_name ;
   public firmwareAdapter(Context mContext, ArrayList<firmwareModel> mListFW){
       super(mContext,0,mListFW);
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
    private View intview(int position, View convertView, ViewGroup parent){
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_firmware,parent,false);
            txt_firmware_name = convertView.findViewById(R.id.txt_name_firmware);
        }
        firmwareModel item = (firmwareModel) getItem(position);
        if(item!=null){
            txt_firmware_name.setText(item.getNameFirmware());
        }
        return convertView;
    }
}
