package com.example.monitordocsis.showportonu;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.congcu.hopdongAdapter;
import com.example.monitordocsis.congcu.hopdongModel;
import com.example.monitordocsis.congcu.pppoeAdapter;
import com.example.monitordocsis.congcu.pppoeModel;
import com.example.monitordocsis.congcu.signalAdapter;
import com.example.monitordocsis.congcu.signalModel;
import com.example.monitordocsis.danhsachonu.FragmentListOnuPort;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.ui.gpon.fragment_lichsu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowPortOnuAdapter extends RecyclerView.Adapter<ShowPortOnuAdapter.ViewHolder> implements Filterable {
    private ProgressBar progBar;
    private static final String URL_TELNET ="http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/telnet/getTelnet.php";
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private List<pppoeModel>mListPPPoE;
    private pppoeAdapter pppoeadapter;
    private List<signalModel>mListSignal;
    private signalAdapter signaladapter;
    private List<hopdongModel>mListContract;
    private hopdongAdapter hopdongadapter;
    private ListView listItem;
    private ImageButton img_pppoe_close,img_signal_close,img_contract_close,img_ls_close;
    private Button btn_start_date,btn_end_date, btn_ls_search;
    private Context mContext;
    private List<ShowPortOnuModel>mListPortOnu;
    private List<ShowPortOnuModel>mNewPortOnu;

    public ShowPortOnuAdapter(Context mContext, List<ShowPortOnuModel> mListPortOnu) {
        this.mContext = mContext;
        this.mListPortOnu = mListPortOnu;
        mNewPortOnu = new ArrayList<>(mListPortOnu);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.showport_onu_table,parent,false);
            ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ShowPortOnuModel item  =mListPortOnu.get(position);
        permissionUser user = (permissionUser) ((AppCompatActivity)mContext).getApplicationContext();
        final String donVi = user.getUnit();
        holder.tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                final String maolt = String.valueOf(holder.maolt.getText());
                final String maonu = String.valueOf(holder.maonu.getText());
                String portpon = String.valueOf(holder.port.getText());
                String onuid = String.valueOf(holder.onuid.getText());
                final JSONObject json_req = new JSONObject( );
                final JSONObject json_data_item = new JSONObject( );
                final JSONObject json_data_item_search = new JSONObject( );
                try {
                    JSONArray json_arr_item = new JSONArray( );
                    json_data_item_search.put("maolt",maolt);
                    json_data_item_search.put("maonu",maonu);
                    json_data_item_search.put("portpon",portpon);
                    json_data_item_search.put("onuid",onuid);
                    json_arr_item.put(json_data_item_search);
                    JSONArray json_data = new JSONArray( );
                    json_data_item.put("item", json_arr_item);
                    json_data.put(json_data_item);
                    json_req.put("user_name", "htvt");
                    json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                    json_req.put("action", "tools");
                    json_req.put("data", json_data);
                }
                catch (JSONException err) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
                }
                PopupMenu popupMenu = new PopupMenu(mContext,holder.tools);
                popupMenu.getMenuInflater().inflate(R.menu.popup_tool,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pppoe:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                final View view = LayoutInflater.from(mContext).inflate(R.layout.pppoe_gpon,null);
                                listItem =view.findViewById(R.id.list_item);
                                progBar =view.findViewById(R.id.progBar);
                                img_pppoe_close =view.findViewById(R.id.pppoe_dialog_close);
                                builder.setView(view);
                                final  AlertDialog dialog = builder.create();
                                try {
                                    json_req.put("action", "telnet");
                                    json_data_item.put("act", "pppoe");
                                    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, URL_TELNET, json_req, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("response",">>>"+response);
                                            try {
                                                json_arr_result = response.getJSONArray("data");
                                                mListPPPoE = new ArrayList<pppoeModel>();
                                                for(int i=0; i < json_arr_result.length(); i++){
                                                    json_obj =json_arr_result.getJSONObject(i);
                                                    mListPPPoE.add(new pppoeModel(json_obj.getString("admin"),json_obj.getString("status"),
                                                            json_obj.getString("auth"),json_obj.getString("user"),
                                                            json_obj.getString("pass"),json_obj.getString("mac"),
                                                            json_obj.getString("host1"), json_obj.getString("host2"),
                                                            json_obj.getString("config_mask"),json_obj.getString("config_getwaye"),
                                                            json_obj.getString("config_primary"),json_obj.getString("config_secondary")));
                                                }
                                                pppoeadapter = new pppoeAdapter(mListPPPoE);
                                                listItem.setAdapter(pppoeadapter);
                                                progBar.setVisibility(View.GONE);
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin từ onResponse!\n1. " + err.getMessage( ));
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n" + error.getMessage( ));
                                        }
                                    });
                                    Volley.newRequestQueue(mContext).add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    dialog.show();
                                    img_pppoe_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    alert_display("Cảnh báo", "Không thể lấy thông tin từ onMenuItemClick!\n PPPoE. " + e.getMessage( ));
                                }
                                break;
                            case R.id.tinhieu:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                View view1 = LayoutInflater.from(mContext).inflate(R.layout.signal_gpon,null);
                                listItem =view1.findViewById(R.id.list_item);
                                progBar =view1.findViewById(R.id.progBar);
                                img_signal_close=view1.findViewById(R.id.signal_dialog_close);
                                builder1.setView(view1);
                                final  AlertDialog dialog1 = builder1.create();
                                try {
                                    json_req.put("action", "telnet");
                                    json_data_item.put("act", "signal");
                                    Log.d("json",">>"+json_req);
                                    JsonObjectRequest  request = new JsonObjectRequest(Request.Method.POST, URL_TELNET, json_req, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("response",">>"+response);
                                            try{
                                                json_arr_result = response.getJSONArray("data");
                                                mListSignal = new ArrayList<signalModel>();
                                                for(int i=0; i < json_arr_result.length(); i++){
                                                    json_obj =json_arr_result.getJSONObject(i);
                                                    mListSignal.add(new signalModel(json_obj.getString("Port"),json_obj.getString("OnuID"),
                                                            json_obj.getString("Status"),json_obj.getString("mode"),
                                                            json_obj.getString("SN"),json_obj.getString("linkuptime"),
                                                            json_obj.getString("Rxpower"), json_obj.getString("Optical"),
                                                            json_obj.getString("TotalRF"),json_obj.getString("Ping")));
                                                }
                                                signaladapter = new signalAdapter(mListSignal);
                                                listItem.setAdapter(signaladapter);
                                                progBar.setVisibility(View.GONE);
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin từ onResponse!\n1. " + err.getMessage( ));
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n" + error.getMessage( ));
                                        }
                                    });
                                    Volley.newRequestQueue(mContext).add(request);
                                    request.setRetryPolicy(new DefaultRetryPolicy(26000,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    dialog1.show();
                                    img_signal_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog1.dismiss();
                                        }
                                    });
                                } catch (JSONException e) {
                                    alert_display("Cảnh báo", "Không thể lấy thông tin từ onMenuItemClick!\n Signal" + e.getMessage( ));
                                }
                                break;
                            case R.id.hopdong:
                                AlertDialog.Builder builder_contract = new AlertDialog.Builder(mContext);
                                View view_contract = LayoutInflater.from(mContext).inflate(R.layout.contract_gpon,null);
                                listItem =view_contract.findViewById(R.id.list_item);
                                progBar =view_contract.findViewById(R.id.progBar);
                                img_contract_close=view_contract.findViewById(R.id.contract_dialog_close);
                                builder_contract.setView(view_contract);
                                final  AlertDialog dialog_contract = builder_contract.create();
                                try {
                                    json_req.put("action", "api");
                                    json_data_item.put("act", "subnum");
                                    Log.d("json_req",">>>"+json_req);
                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_TELNET, json_req, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("response",">>>"+response);
                                            try{
                                                json_arr_result = response.getJSONArray("data");
                                                mListContract = new ArrayList<hopdongModel>();
                                                for(int i=0; i < json_arr_result.length(); i++){
                                                    json_obj =json_arr_result.getJSONObject(i);
                                                    mListContract.add(new hopdongModel(json_obj.getString("tenkhach"),json_obj.getString("chinhanh"),
                                                            json_obj.getString("phone"),json_obj.getString("diachi"),
                                                            json_obj.getString("loaihd"),json_obj.getString("tinhtrang")));
                                                }
                                                hopdongadapter = new hopdongAdapter(mListContract);
                                                listItem.setAdapter(hopdongadapter);
                                                progBar.setVisibility(View.GONE);
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin từ onResponse!\n1. " + err.getMessage( ));
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n" + error.getMessage( ));
                                        }
                                    });
                                    Volley.newRequestQueue(mContext).add(request);
                                    request.setRetryPolicy(new DefaultRetryPolicy(5000,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    dialog_contract.show();
                                    img_contract_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog_contract.dismiss();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    alert_display("Cảnh báo", "Không thể lấy thông tin từ onMenuItemClick!\n1. " + e.getMessage( ));
                                }
                                break;
                            case R.id.lichsu:
                                AlertDialog.Builder builder_ls = new AlertDialog.Builder(mContext);
                                View view_ls = LayoutInflater.from(mContext).inflate(R.layout.lichsu_gpon,null);
                                final View view_ls1 = LayoutInflater.from(mContext).inflate(R.layout.fragment_canhbaoonu,null);
                                img_ls_close=view_ls.findViewById(R.id.ls_dialog_close);
                                btn_start_date =view_ls.findViewById(R.id.btn_start_date);
                                btn_end_date =view_ls.findViewById(R.id.btn_end_date);
                                btn_ls_search = view_ls.findViewById(R.id.btn_ls_search);
                                builder_ls.setView(view_ls);
                                final  AlertDialog dialog_ls = builder_ls.create();
                                try {
                                    json_req.put("action", "getdb");
                                    btn_start_date.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pickerDateStart();
                                        }
                                    });
                                    btn_end_date.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pickerDateEnd();
                                        }
                                    });
                                    btn_ls_search.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                String start_date = String.valueOf(btn_start_date.getText());
                                                String end_date = String.valueOf(btn_end_date.getText());
                                                dialog_ls.dismiss();
                                                fragment_lichsu fr_ls = new fragment_lichsu();
                                                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                transaction.remove(new FragmentListOnuPort());
                                                transaction.replace(R.id.fr_showport_onu,fr_ls ,fr_ls.getTag());
                                                Bundle bundle = new Bundle();
                                                bundle.putString("maolt",maolt);
                                                bundle.putString("maonu", maonu);
                                                bundle.putString("donVi",donVi);
                                                bundle.putString("start_date",start_date);
                                                bundle.putString("end_date",end_date);
                                                fr_ls.setArguments(bundle);
                                                transaction.commit();
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin từ !\n1. " + err.getMessage( ));
                                            }
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dialog_ls.show();
                                img_ls_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_ls.dismiss();
                                    }
                                });
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        holder.maolt.setText(item.getMaolt());
        holder.maonu.setText(item.getMaonu());
        holder.port.setText(item.getPort());
        holder.onuid.setText(item.getOnuid());
        holder.status.setText(item.getStatus());
        holder.mode.setText(item.getMode());
        holder.profile.setText(item.getProfile());
        holder.firmware.setText(item.getFirmware());
        holder.rxpower.setText(item.getRx());
        holder.deact.setText(item.getDeactiveReason());
        holder.inacttime.setText(item.getInactTime());
        holder.model.setText(item.getModel());
        holder.distance.setText(item.getDistance());
        holder.time.setText(item.getDatetime());
        Integer mode = Integer.parseInt(holder.mode.getText().toString());
        if(!holder.rxpower.getText().toString().isEmpty()){
            Double rx = Double.parseDouble(holder.rxpower.getText().toString());
            if(rx <=-8 && rx >=-28.5){
                holder.rxpower.setBackgroundResource(R.color.colorGreen);
                holder.rxpower.setTextColor(Color.BLACK);
            }else if(rx >-8 || rx <-28.5){
                holder.rxpower.setBackgroundResource(R.color.colorRed);
                holder.rxpower.setTextColor(Color.WHITE);
            }
        }else{
            holder.rxpower.setText("-");
            holder.rxpower.setBackgroundResource(R.color.colorRed);
        }

        Integer deactive = Integer.parseInt(holder.deact.getText().toString());
        Integer inactive = Integer.parseInt(holder.inacttime.getText().toString());
        String model = holder.model.getText().toString();
        if(holder.status.getText().toString().contains("Inactive")){
            holder.status.setBackgroundResource(R.color.colorRed);
        }else {
            holder.status.setBackgroundResource(R.color.colorGreen);
        }
        if(model.isEmpty()){
            holder.model.setText("-");
        }
        if(mode==2){
            holder.mode.setText("Auto");
            holder.mode.setBackgroundResource(R.color.colorRed);
            holder.mode.setTextColor(Color.BLACK);
        }else{
            holder.mode.setText("Manual");
            holder.mode.setBackgroundResource(R.color.colorGreen);
            holder.mode.setTextColor(Color.BLACK);
        }

        switch (item.getStatus()){
            case "Inactive":
                if(deactive==2){
                    holder.deact.setText("Mất Nguồn");
                }else if(deactive == 1){
                    holder.deact.setText("None");
                }else  if(deactive ==100){
                    holder.deact.setText("Mất quang|LOSS");
                }else if(deactive ==4){
                    holder.deact.setText("Lỗi quang|LOFI");
                }else  if(deactive == 3){
                    holder.deact.setText("Lỗi quang|LOSI");
                }else if(deactive ==21){
                    holder.deact.setText("Admin Omcc Down");
                }else if(deactive ==10){
                    holder.deact.setText("Admin reset");
                }
                break;
            default:
                holder.deact.setText("-");
        }
        int day  = (int) Math.floor(inactive/86400);
        inactive-=day*(86400);
        int house = (int) Math.floor(inactive/3600);
        inactive-=house*3600;
        int minute = (int) Math.floor(inactive/60);
        inactive-=minute*60;
        int second = (int) Math.floor(inactive);
        inactive-=second;
        holder.inacttime.setText(day+"d "+house+"h "+minute+"m "+second+"s");
    }

    @Override
    public int getItemCount() {
        return mListPortOnu.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }
    private Filter exFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ShowPortOnuModel> filteredList = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                filteredList.addAll(mNewPortOnu);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ShowPortOnuModel item :mNewPortOnu){
                    if(item.getMaolt().toLowerCase().contains(filterPattern) || item.getMaonu().toLowerCase().contains(filterPattern)|| item.getPort().toLowerCase().contains(filterPattern)
                            || item.getOnuid().toLowerCase().contains(filterPattern) || item.getStatus().toLowerCase().contains(filterPattern)
                            || item.getMode().toLowerCase().contains(filterPattern)|| item.getFirmware().toLowerCase().contains(filterPattern)|| item.getRx().toLowerCase().contains(filterPattern)
                            || item.getProfile().toLowerCase().contains(filterPattern)|| item.getStatus().toLowerCase().contains(filterPattern)|| item.getDeactiveReason().toLowerCase().contains(filterPattern)
                            || item.getInactTime().toLowerCase().contains(filterPattern)|| item.getModel().toLowerCase().contains(filterPattern)|| item.getDatetime().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values= filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListPortOnu.clear();
            mListPortOnu.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tools;
        private TextView maolt;
        private TextView maonu;
        private TextView port;
        private TextView onuid;
        private TextView status;
        private TextView mode ;
        private TextView profile;
        private TextView firmware;
        private TextView rxpower;
        private TextView deact;
        private TextView inacttime;
        private TextView model;
        private TextView distance;
        private TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tools = itemView.findViewById(R.id.txt_tool);
            maolt =itemView.findViewById(R.id.txt_maolt);
            maonu =itemView.findViewById(R.id.txt_maonu);
            port =itemView.findViewById(R.id.txt_portpon);
            onuid =itemView.findViewById(R.id.txt_onuid);
            status =itemView.findViewById(R.id.txt_status);
            mode =itemView.findViewById(R.id.txt_mode);
            profile =itemView.findViewById(R.id.txt_profile);
            firmware =itemView.findViewById(R.id.txt_firmware);
            rxpower =itemView.findViewById(R.id.txt_rxpw);
            deact = itemView.findViewById(R.id.txt_deact);
            inacttime =itemView.findViewById(R.id.txt_intime);
            model =itemView.findViewById(R.id.txt_model);
            distance =itemView.findViewById(R.id.txt_distance);
            time =itemView.findViewById(R.id.txt_time);
        }
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    private void pickerDateStart (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                btn_start_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void pickerDateEnd (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                btn_end_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}
