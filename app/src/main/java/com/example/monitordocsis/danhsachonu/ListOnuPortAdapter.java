package com.example.monitordocsis.danhsachonu;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.monitordocsis.firmware.firmwareAdapter;
import com.example.monitordocsis.firmware.firmwareModel;
import com.example.monitordocsis.firmware.osAdapter;
import com.example.monitordocsis.firmware.osModel;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.ui.gpon.fragment_lichsu;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListOnuPortAdapter extends RecyclerView.Adapter<ListOnuPortAdapter.ViewHolder> implements Filterable {
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private List<pppoeModel>mListPPPoE;
    private pppoeAdapter pppoeadapter;
    private List<signalModel>mListSignal;
    private signalAdapter signaladapter;
    private List<hopdongModel>mListContract;
    private hopdongAdapter hopdongadapter;
    private Context mContext;
    private List<ListOnuPortModel> mListOnu;
    private List<ListOnuPortModel>mNewListOnu;
    private ListView listItem;
    private ImageButton img_pppoe_close,img_signal_close,img_contract_close,img_ls_close,img_reset_onu_close,img_upgrade_fw_close;
    private Button btn_start_date,btn_end_date, btn_ls_search;
    private ImageView txt_close, txt_confim;
    private JSONArray json_arr_fw;
    private JSONArray json_arr_os;
    private ArrayList<firmwareModel>mListFW;
    private firmwareAdapter mFwAdapter;
    private ArrayList<osModel>mListOS;
    private osAdapter mOsAdapter;
    private Spinner sp_ListFw;
    private TextView txt_maonu, txt_model;
    private ImageView txt_ls_search;
    private boolean checkFTP = false;
    private String checkOS ="os1" ;
    private String nameFW;
    private TextView pppoe_dialog_status;

    public ListOnuPortAdapter(Context mContext, List<ListOnuPortModel> mListOnu) {
        this.mContext = mContext;
        this.mListOnu = mListOnu;
        mNewListOnu = new ArrayList<>(mListOnu);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_onu_port_table,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        permissionUser user = (permissionUser) ((AppCompatActivity)mContext).getApplicationContext();
        final String donVi = user.getUnit();
        final String version = user.getVersion();
        final String url_telnet= urlData.url+version+"/telnet/getTelnet.php";
        final String url_telnet_onu =urlData.url+version+"/telnet/api_telnet_onu.php";
        final String url_load_fw =urlData.url+version+"/api_load_fw_onu.php";
        final String url_load_os =urlData.url+version+"/telnet/api_load_fw_os.php";
        final String url_upgrade_fw =urlData.url+version+"/telnet/api_upgrade_fw.php";
        ListOnuPortModel item  =mListOnu.get(position);
        holder.tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                final String maolt = String.valueOf(holder.maolt.getText());
                final String maonu = String.valueOf(holder.maonu.getText());
                final String portpon = String.valueOf(holder.port.getText());
                final String onuid = String.valueOf(holder.onuid.getText());
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
                    alert_display("Cảnh báo", "Không thể lấy thông tin");
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
                                pppoe_dialog_status =view.findViewById(R.id.pppoe_dialog_status);
                                builder.setView(view);
                                final  AlertDialog dialog = builder.create();
                                try {
                                    json_req.put("action", "telnet");
                                    json_data_item.put("act", "pppoe");
                                    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url_telnet, json_req, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                if(response.getString("code").contains("1")){
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
                                                }else{
                                                    pppoe_dialog_status.setVisibility(View.VISIBLE);
                                                    pppoe_dialog_status.setText("Không thể lấy thông tin");
                                                    progBar.setVisibility(View.GONE);
                                                }

                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin");
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
                                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                                }
                                break;
                            case R.id.tinhieu:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                View view1 = LayoutInflater.from(mContext).inflate(R.layout.signal_gpon,null);
                                listItem =view1.findViewById(R.id.list_item);
                                progBar =view1.findViewById(R.id.progBar);
                                img_signal_close=view1.findViewById(R.id.signal_dialog_close);
                                pppoe_dialog_status =view1.findViewById(R.id.pppoe_dialog_status);
                                builder1.setView(view1);
                                final  AlertDialog dialog1 = builder1.create();
                                try {
                                    json_req.put("action", "telnet");
                                    json_data_item.put("act", "signal");
                                    Log.d("json",">>"+json_req);
                                    JsonObjectRequest  request = new JsonObjectRequest(Request.Method.POST, url_telnet, json_req, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                if(response.getString("code").contains("1")){
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
                                                }else {
                                                    pppoe_dialog_status.setVisibility(View.VISIBLE);
                                                    pppoe_dialog_status.setText("Không thể lấy thông tin");
                                                    progBar.setVisibility(View.GONE);
                                                }
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin ");
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
                                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
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
                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_telnet, json_req, new Response.Listener<JSONObject>() {
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
                                                            json_obj.getString("loaihd"),json_obj.getString("tinhtrang"),json_obj.getString("shd")));
                                                }
                                                hopdongadapter = new hopdongAdapter(mListContract);
                                                listItem.setAdapter(hopdongadapter);
                                                progBar.setVisibility(View.GONE);
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin ");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin");
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
                                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                                }
                                break;
                            case R.id.lichsu:
                                AlertDialog.Builder builder_ls = new AlertDialog.Builder(mContext);
                                View view_ls = LayoutInflater.from(mContext).inflate(R.layout.lichsu_gpon,null);
                                final View view_ls1 = LayoutInflater.from(mContext).inflate(R.layout.fragment_canhbaoonu,null);
                                img_ls_close=view_ls.findViewById(R.id.ls_dialog_close);
                                btn_start_date =view_ls.findViewById(R.id.btn_start_date);
                                btn_end_date =view_ls.findViewById(R.id.btn_end_date);
                                txt_ls_search = view_ls.findViewById(R.id.txt_ls_search);
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
                                    txt_ls_search.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                String start_date = String.valueOf(btn_start_date.getText());
                                                String end_date = String.valueOf(btn_end_date.getText());
                                                dialog_ls.dismiss();
                                                fragment_lichsu fr_ls = new fragment_lichsu();
                                                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                transaction.replace(R.id.fr_list_onu_port,fr_ls ,fr_ls.getTag());
                                                transaction.addToBackStack(null);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("maolt",maolt);
                                                bundle.putString("maonu", maonu);
                                                bundle.putString("donVi",donVi);
                                                bundle.putString("start_date",start_date);
                                                bundle.putString("end_date",end_date);
                                                fr_ls.setArguments(bundle);
                                                transaction.commit();
                                                ((AppCompatActivity)mContext).getSupportActionBar().setTitle("Lịch sử ONU");
                                            }catch (Exception err){
                                                alert_display("Cảnh báo", "Không thể lấy thông tin");
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
                            case R.id.reset_onu:
                                if(!donVi.contains("TTVT")){
                                    alert_display("Thông báo", "Chức năng đang tạm khóa");
                                    return true;
                                }
                                AlertDialog.Builder builder_reset = new AlertDialog.Builder(mContext);
                                View view_reset = LayoutInflater.from(mContext).inflate(R.layout.reset_onu_gpon,null);
                                txt_close = view_reset.findViewById(R.id.txt_close);
                                txt_confim = view_reset.findViewById(R.id.txt_confim);
                                builder_reset.setView(view_reset);
                                final  AlertDialog dialog_reset = builder_reset.create();
                                dialog_reset.show();
                                txt_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.animator_button(v);
                                        dialog_reset.dismiss();
                                    }
                                });
                                txt_confim.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.animator_button(v);
                                        final JSONObject json_req = new JSONObject( );
                                        final JSONObject json_data_item_search = new JSONObject( );
                                        try {
                                            JSONArray json_arr_item = new JSONArray( );
                                            json_data_item_search.put("maolt",maolt);
                                            json_data_item_search.put("maonu",maonu);
                                            json_data_item_search.put("portpon",portpon);
                                            json_data_item_search.put("onuid",onuid);
                                            json_arr_item.put(json_data_item_search);
                                            json_req.put("user_name", "htvt");
                                            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                                            json_req.put("action", "ResetOnu");
                                            json_req.put("data", json_arr_item);
                                        }
                                        catch (JSONException err) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin ");
                                        }
                                        Log.d("json_reset:",">>>"+json_req);
                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_telnet_onu, json_req, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(mContext,"Đang Reset ONU...",Toast.LENGTH_LONG).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        Volley.newRequestQueue(mContext).add(request);
                                    }
                                });
                                break;
                            case R.id.upgrade_firmware:
                                if(!donVi.contains("TTVT")){
                                    alert_display("Thông báo", "Chức năng đang tạm khóa");
                                    return true;
                                }
                                final   Timer timer = new Timer();
                                AlertDialog.Builder builder_update = new AlertDialog.Builder(mContext);
                                View view_update = LayoutInflater.from(mContext).inflate(R.layout.upgrade_firmware,null);
                                img_upgrade_fw_close = view_update.findViewById(R.id.fw_dialog_close);
                                txt_confim = view_update.findViewById(R.id.txt_confim);
                                CheckBox cb_ftp = view_update.findViewById(R.id.cb_download_ftp);
                                cb_ftp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (buttonView.isChecked()){
                                            checkFTP =true;
                                        }else {
                                            checkFTP =false;
                                        }
                                    }
                                });
                                RadioButton rd_os1 = view_update.findViewById(R.id.rd_os1_fw);
                                RadioButton rd_os2 = view_update.findViewById(R.id.rd_os2_fw);
                                rd_os1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkOS="os1";
                                    }
                                });
                                rd_os2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkOS="os2";
                                    }
                                });
                                ImageView txt_download_fw = view_update.findViewById(R.id.txt_download_fw);
                                ImageView txt_commit_os = view_update.findViewById(R.id.txt_commit_os);
                                txt_maonu =view_update.findViewById(R.id.txt_modem_onu);
                                txt_model = view_update.findViewById(R.id.txt_model_onu);
                                sp_ListFw = view_update.findViewById(R.id.sp_list_fw);
                                listItem = view_update.findViewById(R.id.list_item);
                                sp_ListFw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        firmwareModel item = (firmwareModel) mFwAdapter.getItem(position);
                                        nameFW = item.getNameFirmware();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                builder_update.setView(view_update);
                                final  AlertDialog dialog_update = builder_update.create();
                                builder_update.setCancelable(false);
                                dialog_update.show();
                                final String model = String.valueOf(holder.model.getText()) ;
                                final String maolt = String.valueOf(holder.maolt.getText());
                                final String portpon = String.valueOf(holder.port.getText());
                                final String onuid = String.valueOf(holder.onuid.getText());
                                txt_maonu.setText(holder.maonu.getText());
                                txt_model.setText(holder.model.getText());
                                loadFW(url_load_fw,model);
                                loadFirmOS(url_load_os,maolt,portpon,onuid);
                                txt_download_fw.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.animator_button(v);
                                        System.out.println("checkFTP:"+ checkFTP);
                                        final JSONObject json_download_fw = new JSONObject( );
                                        try {
                                            JSONArray json_arr_data = new JSONArray( );
                                            JSONObject json_data_item = new JSONObject();
                                            json_data_item.put("maolt",maolt);
                                            json_data_item.put("portpon",portpon);
                                            json_data_item.put("onuid",onuid);
                                            json_data_item.put("nameFW",nameFW);
                                            json_data_item.put("checkFTP", checkFTP);
                                            json_arr_data.put(json_data_item);
                                            json_download_fw.put("user_name", "htvt");
                                            json_download_fw.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                                            json_download_fw.put("action", "downloadFW");
                                            json_download_fw.put("data",json_arr_data);
                                        }
                                        catch (JSONException err) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin ");
                                        }
                                        Toast.makeText(mContext,"Đang download Firmware...",Toast.LENGTH_LONG).show();
                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_upgrade_fw, json_download_fw, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    if (Integer.valueOf(response.getString("code")) == 1) {
                                                        Toast.makeText(mContext,"Đang download Firmware...",Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Toast.makeText(mContext," Download Firmware error...",Toast.LENGTH_LONG).show();
                                                    }
                                                }catch (Exception err){
                                                    Toast.makeText(mContext," Download Firmware error...",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        });
                                        Volley.newRequestQueue(mContext).add(request);
                                        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                loadFirmOS(url_load_os,maolt,portpon,onuid);
                                            }
                                        },0,15000);
                                    }
                                });
                                txt_commit_os.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Global.animator_button(v);
                                        final JSONObject json_commit_os = new JSONObject( );
                                        try {
                                            JSONArray json_arr_data = new JSONArray( );
                                            JSONObject json_data_item = new JSONObject();
                                            json_data_item.put("maolt",maolt);
                                            json_data_item.put("portpon",portpon);
                                            json_data_item.put("onuid",onuid);
                                            json_data_item.put("OS", checkOS);
                                            json_arr_data.put(json_data_item);
                                            json_commit_os.put("user_name", "htvt");
                                            json_commit_os.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                                            json_commit_os.put("action", "commitOS");
                                            json_commit_os.put("data",json_arr_data);
                                        }
                                        catch (JSONException err) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin ");
                                        }
                                        Toast.makeText(mContext,"Đang Commit OS...",Toast.LENGTH_LONG).show();
                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_upgrade_fw, json_commit_os, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    if (Integer.valueOf(response.getString("code")) == 1) {
                                                        Toast.makeText(mContext,"Đang Commit OS...",Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Toast.makeText(mContext," Commit error...",Toast.LENGTH_LONG).show();
                                                    }
                                                }catch (Exception err){
                                                    Toast.makeText(mContext," Commit error...",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        });
                                        Volley.newRequestQueue(mContext).add(request);
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                loadFirmOS(url_load_os,maolt,portpon,onuid);
                                            }
                                        },0,15000);
                                    }
                                });
                                img_upgrade_fw_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_update.dismiss();
                                        timer.cancel();
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
        holder.address.setText(item.getAddress());
        Integer status = Integer.parseInt(holder.status.getText().toString());
        Integer mode = Integer.parseInt(holder.mode.getText().toString());
        Double rx = Double.parseDouble(holder.rxpower.getText().toString());
        Integer deactive = Integer.parseInt(holder.deact.getText().toString());
        Integer inactive = Integer.parseInt(holder.inacttime.getText().toString());
        String model = holder.model.getText().toString();
        if(model.isEmpty()){
            holder.model.setText("-");
        }
        if(status==1){
            holder.status.setText("Inactive");
            holder.status.setBackgroundResource(R.color.colorRed);
            holder.status.setTextColor(Color.BLACK);
        }else{
            holder.status.setText("Active");
            holder.status.setBackgroundResource(R.color.colorGreen);
            holder.status.setTextColor(Color.BLACK);
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
        if(rx <=-8 && rx >=-28.5){
            holder.rxpower.setBackgroundResource(R.color.colorGreen);
            holder.rxpower.setTextColor(Color.BLACK);
        }else if(rx >-8 || rx <-28.5){
            holder.rxpower.setBackgroundResource(R.color.colorRed);
            holder.rxpower.setTextColor(Color.WHITE);
        }
        switch (status){
            case 1:
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
        final ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        holder.maonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maonu = holder.maonu.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy mã ONU:", maonu);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy mã ONU:"+maonu,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.maolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maolt = holder.maolt.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy mã OLT:", maolt);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy mã OLT:"+maolt,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.firmware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firmware = holder.firmware.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy Firmware:", firmware);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy Firmware:"+firmware,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String model = holder.model.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy Model:", model);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy Model:"+model,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = holder.mode.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy Mode:", mode);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy Mode:"+mode,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profile = holder.profile.getText().toString();
                ClipData clip = ClipData.newPlainText("Copy Profile:", profile);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy Profile:"+profile,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOnu.size();
    }

    @Override
    public Filter getFilter() {
        return exFilter;
    }
    private Filter exFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ListOnuPortModel> filteredList = new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                filteredList.addAll(mNewListOnu);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ListOnuPortModel item :mNewListOnu){
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
            mListOnu.clear();
            mListOnu.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView tools;
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
        private TextView address;
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
            address =itemView.findViewById(R.id.txt_address);

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                btn_end_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void loadFW(String url, String model){
        final JSONObject json_search1 = new JSONObject( );
        try {
            JSONArray json_arr_data = new JSONArray( );
            JSONObject json_data_item = new JSONObject();
            json_data_item.put("model",model);
            json_arr_data.put(json_data_item);
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("action", "firmware");
            json_search1.put("data",json_arr_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        System.out.println("json_search1:"+json_search1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_fw =response.getJSONArray("data");
                    mListFW = new ArrayList<firmwareModel>();
                    for(int i=0; i < json_arr_fw.length(); i++){
                        json_obj =json_arr_fw.getJSONObject(i);
                        mListFW.add(new firmwareModel(json_obj.getString("NAME")));
                    }
                    mFwAdapter = new firmwareAdapter(mContext,mListFW);
                    sp_ListFw.setAdapter(mFwAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(mContext).add(request);
    }
    private void loadFirmOS (String url , String maolt, String portpon , String onuid){
        final JSONObject json_search1 = new JSONObject( );
        try {
            JSONArray json_arr_data = new JSONArray( );
            JSONObject json_data_item = new JSONObject();
            json_data_item.put("maolt",maolt);
            json_data_item.put("portpon",portpon);
            json_data_item.put("onuid",onuid);
            json_arr_data.put(json_data_item);
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("action", "loadOS");
            json_search1.put("data",json_arr_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        System.out.println("json_search1:"+json_search1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_os =response.getJSONArray("data");
                    mListOS = new ArrayList<osModel>();
                    for(int i=0; i < json_arr_os.length(); i++){
                        json_obj =json_arr_os.getJSONObject(i);
                        mListOS.add(new osModel(json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                json_obj.getString("STATUS"),
                                json_obj.getString("OS1"),json_obj.getString("OS2")));
                    }
                    mOsAdapter = new osAdapter(mListOS);
                    listItem.setAdapter(mOsAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n1. " + error.getMessage( ));
            }
        });
        Volley.newRequestQueue(mContext).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
