package com.example.monitordocsis.systemdocsis_mb.Node;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.systemdocsis_mb.CableModem.FragmentCMNode;
import com.example.monitordocsis.ui.gpon.fragment_canhbao_onu;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterNodeCurrent extends BaseAdapter {
    View view ;
    private Context mContext;
    private List<ModelNodeCurrent> mList;
    private String version_docsis_mb;
    private JSONArray json_arr_result;
    private ProgressBar progBar;
    private JSONObject json_obj;
    private ArrayList<ModelNodeCurrent>mListNodeCurrten;
    private AdapterNodeCurrent mAdapter;
    private ListView listItem;
    private String donVi;
    public AdapterNodeCurrent(Context mContext, List<ModelNodeCurrent> mList) {
        this.mList = mList;
        this.mContext =mContext;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        permissionUser user = (permissionUser) ((AppCompatActivity)mContext).getApplicationContext();
        final String donVi = user.getUnit();
        version_docsis_mb = user.getVersion_docsis_mb();
        final String url = urlData.url1+version_docsis_mb+"/api_load_current_upstream.php";
        if(convertView==null){
            convertView= View.inflate(parent.getContext(), R.layout.upstream_current,null);
        }else {
            view=convertView;
        }
        FragmentNodeCurrent fragmentNodeCurrent = new FragmentNodeCurrent();
        ModelNodeCurrent item = (ModelNodeCurrent) getItem(position);
        ImageView btn_ls_node = convertView.findViewById(R.id.btn_ls_node);
        final TextView txt_node = convertView.findViewById(R.id.txt_node_current);
        TextView txt_ifdesc = convertView.findViewById(R.id.txt_ifdesc_current);
        txt_ifdesc.setPaintFlags(txt_ifdesc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView txt_snr = convertView.findViewById(R.id.txt_snr_current);
        TextView txt_mer = convertView.findViewById(R.id.txt_mer_current);
        TextView txt_fec = convertView.findViewById(R.id.txt_fec_current);
        TextView txt_unfec = convertView.findViewById(R.id.txt_unfec_current);
        TextView txt_tx = convertView.findViewById(R.id.txt_tx_current);
        TextView txt_rx = convertView.findViewById(R.id.txt_rx_current);
        TextView txt_act = convertView.findViewById(R.id.txt_act_curent);
        TextView txt_total = convertView.findViewById(R.id.txt_total_curent);
        TextView txt_reg = convertView.findViewById(R.id.txt_reg_curent);
        TextView txt_fre = convertView.findViewById(R.id.txt_fre_curent);
        TextView txt_with = convertView.findViewById(R.id.txt_with_curent);
        TextView txt_micref = convertView.findViewById(R.id.txt_micref_curent);
        TextView txt_mod = convertView.findViewById(R.id.txt_mode_curent);
        TextView txt_time = convertView.findViewById(R.id.txt_time_current);
        final TextView txt_cmtsid = convertView.findViewById(R.id.txt_cmtsid_current);
        final TextView txt_ifindex = convertView.findViewById(R.id.txt_ifindex_current);
        txt_node.setText(mList.get(position).getNode());
        txt_ifdesc.setText(mList.get(position).getIfdesc());
        txt_snr.setText(mList.get(position).getSnr());
        txt_mer.setText(mList.get(position).getMer());
        txt_fec.setText(mList.get(position).getFec());
        txt_unfec.setText(mList.get(position).getUnfec());
        txt_tx.setText(mList.get(position).getTx());
        txt_rx.setText(mList.get(position).getRx());
        txt_act.setText(mList.get(position).getAct());
        txt_total.setText(mList.get(position).getTotal());
        txt_reg.setText(mList.get(position).getReg());
        txt_fre.setText(mList.get(position).getFre());
        txt_with.setText(mList.get(position).getWth());
        txt_micref.setText(mList.get(position).getMicref());
        txt_mod.setText(mList.get(position).getMod());
        txt_time.setText(mList.get(position).getTime());
        txt_cmtsid.setText(mList.get(position).getCmtsid());
        txt_ifindex.setText(mList.get(position).getIfindex());
        Double snr = Double.parseDouble(mList.get(position).getSnr());
        Double mer = Double.parseDouble(mList.get(position).getMer());
        Double fec = Double.parseDouble(mList.get(position).getFec());
        Double unfec = Double.parseDouble(mList.get(position).getUnfec());
        Double tx = Double.parseDouble(mList.get(position).getTx());
        Double rx = Double.parseDouble(mList.get(position).getRx());
        Integer act = Integer.parseInt(mList.get(position).getAct());
        Integer total = Integer.parseInt(mList.get(position).getTotal());
        btn_ls_node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmst = String.valueOf(mList.get(position).getCmtsid());
                String ifindex = String.valueOf(mList.get(position).getIfindex());
                String node_name =String.valueOf(mList.get(position).getNode());
                FragmentNodeHis fragment_his = new FragmentNodeHis();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node_current,fragment_his ,fragment_his.getTag());
                transaction.addToBackStack(null);
                Bundle  bundle = new Bundle();
                bundle.putString("cmtsid",cmst);
                bundle.putString("ifindex", ifindex);
                bundle.putString("nodename", node_name);
                fragment_his.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Lịch sử Node");
            }
        });
        txt_ifdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                String cmst = String.valueOf(mList.get(position).getCmtsid());
                String ifindex = String.valueOf(mList.get(position).getIfindex());
                FragmentCMNode fragment_cm_node = new FragmentCMNode();
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(new fragment_canhbao_onu());
                transaction.replace(R.id.fr_node_current,fragment_cm_node ,fragment_cm_node.getTag());
                transaction.addToBackStack(null);
                Bundle  bundle = new Bundle();
                bundle.putString("cmtsid",cmst);
                bundle.putString("ifindex", ifindex);
                fragment_cm_node.setArguments(bundle);
                transaction.commit();
                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Thông tin CM ở Node");
            }
        });
        double peractive=0.0;
        if(total ==0){
            peractive =0.0;
        }else{
            peractive = (act*100/total);
        }
        if(snr<10){
            txt_snr.setBackgroundResource(R.color.colorRed);
        }else if(snr >=10 && snr <=18){
            txt_snr.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_snr.setBackgroundResource(R.color.colorLightGray);
        }
        if(mer >=0 && mer <=28 ){
            txt_mer.setBackgroundResource(R.color.colorRed);
        }else if(mer >28 && mer <=31){
            txt_mer.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_mer.setBackgroundResource(R.color.colorLightGray);
        }
        if(fec >=100){
            txt_fec.setBackgroundResource(R.color.colorRed);
        }else if(fec >=5 && fec <100){
            txt_fec.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_fec.setBackgroundResource(R.color.colorLightGray);
        }
        if(unfec >=80){
            txt_unfec.setBackgroundResource(R.color.colorRed);
        }else if(unfec >2 && unfec <80){
            txt_unfec.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_unfec.setBackgroundResource(R.color.colorLightGray);
        }
        if(tx >=60 || tx <18){
            txt_tx.setBackgroundResource(R.color.colorRed);
        }else if(tx >=58 && tx <60){
            txt_tx.setBackgroundResource(R.color.colorYellow);
        }else {
            txt_tx.setBackgroundResource(R.color.colorLightGray);
        }
        if(rx >40 || rx <-24){
            txt_rx.setBackgroundResource(R.color.colorRed);
        }else if(rx >=-24 && rx < -10){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else if(rx >10 && rx <=40){
            txt_rx.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_rx.setBackgroundResource(R.color.colorLightGray);
        }
        if(peractive<=30){
            txt_act.setBackgroundResource(R.color.colorRed);
        }else if(peractive >30 && peractive <=70){
            txt_act.setBackgroundResource(R.color.colorYellow);
        }else{
            txt_act.setBackgroundResource(R.color.colorLightGray);
        }
        return convertView;
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
