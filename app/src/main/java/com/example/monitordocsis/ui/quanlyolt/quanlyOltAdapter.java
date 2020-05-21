package com.example.monitordocsis.ui.quanlyolt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.danhsachonu.FragmentListOnuPort;
import com.example.monitordocsis.showportonu.FragmentShowPortOnu;

import java.util.ArrayList;
import java.util.List;

public class quanlyOltAdapter extends RecyclerView.Adapter<quanlyOltAdapter.ViewHolder> {
    private Context mContext;
    private List<quanlyOltModel>mListOLT;
    private List<quanlyOltModel>mNewListOLT;

    public quanlyOltAdapter(Context mContext, List<quanlyOltModel> mListOLT) {
        this.mContext = mContext;
        this.mListOLT = mListOLT;
        mNewListOLT = new ArrayList<>(mListOLT);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.quanly_olt_table,parent, false);
        ViewHolder  holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        quanlyOltModel item = mListOLT.get(position);
        holder.txt_ds_onu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String area = String.valueOf(holder.area.getText());
                String maolt = String.valueOf(holder.nameOLT.getText());
                String portpon = String.valueOf(holder.portPon.getText());
                FragmentListOnuPort frListOnu = new FragmentListOnuPort();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fr_quanly_olt,frListOnu ,frListOnu.getTag());
                transaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("khuvuc",area);
                bundle.putString("maolt",maolt);
                bundle.putString("portpon", portpon);
                frListOnu.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Danh sách ONU");

            }
        });
        holder.txt_show_port.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String area = String.valueOf(holder.area.getText());
                String maolt = String.valueOf(holder.nameOLT.getText());
                String portpon = String.valueOf(holder.portPon.getText());
                FragmentShowPortOnu frShowPortOnu = new FragmentShowPortOnu();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fr_quanly_olt,frShowPortOnu ,frShowPortOnu.getTag());
                transaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("khuvuc",area);
                bundle.putString("maolt",maolt);
                bundle.putString("portpon", portpon);
                frShowPortOnu.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Show Port ONU");
            }
        });
        holder.portPon.setText(item.getPortPon());
        holder.onuAct.setText(item.getOnuActive());
        holder.onuInAct.setText(item.getOnuInActive());
        holder.onuTotal.setText(item.getOnuTotal());
        holder.nameOLT.setText(item.getNameOLT());
        holder.area.setText(item.getArea());

    }

    @Override
    public int getItemCount() {
        return mListOLT.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView txt_ds_onu;
        private ImageView txt_show_port;
        private TextView portPon;
        private TextView onuAct;
        private TextView onuInAct;
        private TextView onuTotal;
        private TextView nameOLT;
        private TextView area;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ds_onu = itemView.findViewById(R.id.txt_dsonu);
            txt_show_port = itemView.findViewById(R.id.txt_show_port);
            portPon = itemView.findViewById(R.id.txt_port);
            onuAct = itemView.findViewById(R.id.txt_active);
            onuInAct = itemView.findViewById(R.id.txt_inactive);
            onuTotal = itemView.findViewById(R.id.txt_total_onu);
            nameOLT = itemView.findViewById(R.id.txt_oltid);
            area = itemView.findViewById(R.id.txt_area);
        }
    }
}
