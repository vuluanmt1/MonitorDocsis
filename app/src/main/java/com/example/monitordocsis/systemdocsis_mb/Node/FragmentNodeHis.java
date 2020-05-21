package com.example.monitordocsis.systemdocsis_mb.Node;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.MainActivity;
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class FragmentNodeHis extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private AdapterNodeHis mAdapter;
    private ArrayList<ModelNodeHis> mList;
    TextView txt_start_date, txt_end_date, txt_end_time;
    EditText edt_min_fec,edt_max_fec,edt_min_unfec, edt_max_unfec, edt_min_snr, edt_max_snr,edt_min_mer,edt_max_mer,edt_min_tx,edt_max_tx,edt_min_rx, edt_max_rx;
    TextView txt_node_his, txt_time_title,txt_total_data;
    ImageView txt_search_ls,txt_search_his;
    private LinearLayout layoutSearch;
    private Toolbar toolbar;
    private Switch swt_warming;
    private Button btn_search;
    private Boolean checkErrors= false;
    private String cmstID, nodeID,donVi,usercode,version_docsis_mb,nodeName;
    private TextView txt_node, txt_interface, txt_branch,txt_snr, txt_mer,
            txt_fec,txt_unfec, txt_tx, txt_rx, txt_act, txt_total ,
            txt_reg,txt_fre,txt_with, txt_total_node, txt_time;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_node_his,container,false);
        initView();
        layoutSearch =view.findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.GONE);
        recyclerView = (RecyclerView)view.findViewById(R.id.recView);
        progBar = view.findViewById(R.id.progBar);
        txt_total_data = view.findViewById(R.id.txt_total_data);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        usercode = user.getEmail();
        if(usercode.isEmpty()){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        version_docsis_mb = user.getVersion_docsis_mb();
        final String url = urlData.url1+version_docsis_mb+"/api_load_upstream_his.php";
        Bundle bundle = getArguments();
        cmstID=bundle.getString("cmtsid");
        nodeID =bundle.getString("ifindex");
        nodeName =bundle.getString("nodename");
        txt_node_his.setText(nodeName);
        try {
            final JSONObject json_req = new JSONObject( );
            try {
                JSONObject json_data_item_key = new JSONObject( );
                json_data_item_key.put("cmtsid",cmstID);
                json_data_item_key.put("ifindex",nodeID);
                JSONArray json_arr_item = new JSONArray( );
                json_arr_item.put(json_data_item_key);
                JSONObject json_data_item = new JSONObject( );
                json_data_item.put("act", "load");
                json_data_item.put("item",json_arr_item);
                JSONArray json_data = new JSONArray( );
                json_data.put(json_data_item);
                json_req.put("user_name", "htvt");
                json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                json_req.put("action", "his_upstream");
                json_req.put("data", json_data);
            }
            catch (JSONException err) {
                alert_display("Cảnh báo", "Không thể lấy thông tin ");
            }
            Log.d("JSon:",">>>"+json_req);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        json_arr_result = response.getJSONArray("data");
                        txt_total_data.setText(response.getString("recordsTotal"));
                        mList = new ArrayList<ModelNodeHis>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mList.add(new ModelNodeHis(json_obj.getString("SNR"),
                                    json_obj.getString("MER"),json_obj.getString("FEC"),
                                    json_obj.getString("FECUN"), json_obj.getString("TXPOWER"),
                                    json_obj.getString("DSPOWER"),json_obj.getString("ACT"),json_obj.getString("TOTAL"),
                                    json_obj.getString("REG"),json_obj.getString("FRE"),
                                    json_obj.getString("WTH"),json_obj.getString("CREATEDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                        }
                        progBar = view.findViewById(R.id.progBar);
                        mAdapter = new AdapterNodeHis(getContext(),mList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(mAdapter);
                        progBar.setVisibility(View.GONE);
                    }catch (Exception ex){
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }catch (Exception erros){
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        txt_search_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        swt_warming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkErrors =true;
                }else {
                    checkErrors =false;
                }
            }
        });
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateStart();
            }
        });
        txt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateEnd();
            }
        });
        txt_search_ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                try {
                    String start_date = String.valueOf(txt_start_date.getText());
                    String end_date =String.valueOf(txt_end_date.getText());
                    System.out.println("start_date:"+start_date);
                    System.out.println("end_date:"+end_date);
                    String min_fec = String.valueOf(edt_min_fec.getText());
                    String max_fec =String.valueOf(edt_max_fec.getText());
                    String min_unfec =String.valueOf(edt_min_unfec.getText());
                    String max_unfec =String.valueOf(edt_max_unfec.getText());
                    String min_snr =String.valueOf(edt_min_snr.getText());
                    String max_snr= String.valueOf(edt_max_snr.getText());
                    String min_mer =String.valueOf(edt_min_mer.getText());
                    String max_mer=String.valueOf(edt_max_mer.getText());
                    String min_tx =String.valueOf(edt_min_tx.getText());
                    String max_tx =String.valueOf(edt_max_tx.getText());
                    String min_rx =String.valueOf(edt_min_rx.getText());
                    String max_rx =String.valueOf(edt_max_rx.getText());
                    final JSONObject json_api = new JSONObject();
                    try {
                        JSONArray json_arr_item = new JSONArray( );
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act","search");
                        json_data_item.put("item",json_arr_item);
                        JSONObject json_data_item_search = new JSONObject( );
                        json_data_item_search.put("cmtsid",cmstID);
                        json_data_item_search.put("ifindex",nodeID);
                        json_data_item_search.put("start_date",start_date);
                        json_data_item_search.put("end_date",end_date);
                        json_data_item_search.put("canhbao",checkErrors);
                        json_data_item_search.put("min_fec",min_fec);
                        json_data_item_search.put("max_fec",max_fec);
                        json_data_item_search.put("min_unfec",min_unfec);
                        json_data_item_search.put("max_unfec",max_unfec);
                        json_data_item_search.put("min_snr",min_snr);
                        json_data_item_search.put("max_snr",max_snr);
                        json_data_item_search.put("min_mer",min_mer);
                        json_data_item_search.put("max_mer",max_mer);
                        json_data_item_search.put("min_tx",min_tx);
                        json_data_item_search.put("max_tx",max_tx);
                        json_data_item_search.put("min_rx",min_rx);
                        json_data_item_search.put("max_rx",max_rx);
                        json_arr_item.put(json_data_item_search);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_api.put("user_name", "htvt");
                        json_api.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_api.put("action", "his_upstream");
                        json_api.put("donvi",donVi);
                        json_api.put("data", json_data);
                    }catch (Exception e){
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("JSon:",">>>"+json_api);
                    System.out.println("url:"+url);
                    JsonObjectRequest jsonObject_search  = new JsonObjectRequest(Request.Method.POST, url, json_api, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                txt_total_data.setText(response.getString("recordsTotal"));
                                mList = new ArrayList<ModelNodeHis>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mList.add(new ModelNodeHis(json_obj.getString("SNR"),
                                            json_obj.getString("MER"),json_obj.getString("FEC"),
                                            json_obj.getString("FECUN"), json_obj.getString("TXPOWER"),
                                            json_obj.getString("DSPOWER"),json_obj.getString("ACT"),json_obj.getString("TOTAL"),
                                            json_obj.getString("REG"),json_obj.getString("FRE"),
                                            json_obj.getString("WTH"),json_obj.getString("CREATEDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }
                                recyclerView.setVisibility(View.VISIBLE);
                                mAdapter = new AdapterNodeHis(getContext(),mList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(mAdapter);
                                progBar.setVisibility(View.GONE);
                            }catch (Exception er){

                                alert_display("Cảnh báo", "Không thể lấy thông tin aaaaaaaaaaaa"+er.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alert_display("Cảnh báo", "Không thể lấy thông tin sssssssss");
                            Log.e("Err",error.getMessage());
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(jsonObject_search);
                    jsonObject_search.setRetryPolicy(new DefaultRetryPolicy(30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
            }
        });
        txt_snr =view.findViewById(R.id.txt_snr);
        txt_snr.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionSNR();
                    check=false;
                }else{
                    sortListIncreaseSNR();
                    check=true;
                }
            }
        });
        txt_mer =view.findViewById(R.id.txt_mer);
        txt_mer.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMER();
                    check=false;
                }else{
                    sortListIncreaseMER();
                    check=true;
                }
            }
        });
        txt_fec =view.findViewById(R.id.txt_fec);
        txt_fec.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionFEC();
                    check=false;
                }else{
                    sortListIncreaseFEC();
                    check=true;
                }
            }
        });
        txt_unfec =view.findViewById(R.id.txt_unfec);
        txt_unfec.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionUNFEC();
                    check=false;
                }else{
                    sortListIncreaseUNFEC();
                    check=true;
                }
            }
        });
        txt_tx =view.findViewById(R.id.txt_txpw);
        txt_tx.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTxPower();
                    check=false;
                }else{
                    sortListIncreaseTxPower();
                    check=true;
                }
            }
        });
        txt_rx =view.findViewById(R.id.txt_rxpw);
        txt_rx.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionRxPower();
                    check=false;
                }else{
                    sortListIncreaseRxPower();
                    check=true;
                }
            }
        });
        txt_act =view.findViewById(R.id.txt_act);
        txt_act.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionAct();
                    check=false;
                }else{
                    sortListIncreaseAct();
                    check=true;
                }
            }
        });
        txt_total =view.findViewById(R.id.txt_total);
        txt_total.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTotal();
                    check=false;
                }else{
                    sortListIncreaseTotal();
                    check=true;
                }
            }
        });
        txt_reg =view.findViewById(R.id.txt_reg);
        txt_reg.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionReg();
                    check=false;
                }else{
                    sortListIncreaseReg();
                    check=true;
                }
            }
        });
        txt_fre =view.findViewById(R.id.txt_fre);
        txt_fre.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionFre();
                    check=false;
                }else{
                    sortListIncreaseFre();
                    check=true;
                }
            }
        });
        txt_with =view.findViewById(R.id.txt_wth);
        txt_with.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionWTH();
                    check=false;
                }else{
                    sortListIncreaseWTH();
                    check=true;
                }
            }
        });
        txt_time =view.findViewById(R.id.txt_time);
        txt_time.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTime();
                    check=false;
                }else{
                    sortListIncreaseTime();
                    check=true;
                }
            }
        });
        return view;
    }
    private void initView(){
        txt_search_ls = view.findViewById(R.id.txt_search_ls);
        txt_time_title = view.findViewById(R.id.txt_time);
        txt_start_date = view.findViewById(R.id.txt_start_date);
        txt_end_date =view.findViewById(R.id.txt_end_date);
        txt_search_his = view.findViewById(R.id.txt_search_his);
        layoutSearch =view.findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.GONE);
        toolbar =view.findViewById(R.id.toolbar);
        swt_warming =(Switch)view.findViewById(R.id.swt_warning);
        txt_node_his = view.findViewById(R.id.txt_node_his);
        edt_min_fec =view.findViewById(R.id.edt_min_fec);
        edt_max_fec =view.findViewById(R.id.edt_max_fec);
        edt_min_unfec =view.findViewById(R.id.edt_min_Unfec);
        edt_max_unfec =view.findViewById(R.id.edt_max_Unfec);
        edt_min_snr =view.findViewById(R.id.edt_min_Snr);
        edt_max_snr =view.findViewById(R.id.edt_max_Snr);
        edt_min_mer =view.findViewById(R.id.edt_min_Mer);
        edt_max_mer =view.findViewById(R.id.edt_max_Mer);
        edt_min_tx =view.findViewById(R.id.edt_min_Tx);
        edt_max_tx =view.findViewById(R.id.edt_max_Tx);
        edt_min_rx =view.findViewById(R.id.edt_min_Rx);
        edt_max_rx =view.findViewById(R.id.edt_max_Rx);
        btn_search =view.findViewById(R.id.btn_search);
        txt_start_date.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_end_date.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_fec.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_fec.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_unfec.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_unfec.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_snr.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_snr.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_mer.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_mer.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_tx.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_tx.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_rx.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_max_rx.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }
    private void pickerDateStart (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txt_start_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void pickerDateEnd (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txt_end_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void loadDate (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        calendar.set(nam,thang,ngay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txt_start_date.setText(simpleDateFormat.format(calendar.getTime()));
        txt_end_date.setText(simpleDateFormat.format(calendar.getTime()));
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    // Các hàm sắp xem theo Tiêu đề Time.
    private void sortListIncreaseTime (){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return o1.getModifileDate().compareTo(o2.getModifileDate());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTime (){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return o2.getModifileDate().compareTo(o1.getModifileDate());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề SNR.
    private void sortListIncreaseSNR (){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getSnr()).compareTo(Double.valueOf(o2.getSnr()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionSNR (){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getSnr()).compareTo(Double.valueOf(o1.getSnr()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề MER.
    private void sortListIncreaseMER(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getMer()).compareTo(Double.valueOf(o2.getMer()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMER(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getMer()).compareTo(Double.valueOf(o1.getMer()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề FEC.
    private void sortListIncreaseFEC(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getFec()).compareTo(Double.valueOf(o2.getFec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFEC(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getFec()).compareTo(Double.valueOf(o1.getFec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề UnFec.
    private void sortListIncreaseUNFEC(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getUnfec()).compareTo(Double.valueOf(o2.getUnfec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionUNFEC(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getUnfec()).compareTo(Double.valueOf(o1.getUnfec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề TxPw
    private void sortListIncreaseTxPower(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getTxpower()).compareTo(Double.valueOf(o2.getTxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTxPower(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getTxpower()).compareTo(Double.valueOf(o1.getTxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề RxPower
    private void sortListIncreaseRxPower(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getRxpower()).compareTo(Double.valueOf(o2.getRxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getRxpower()).compareTo(Double.valueOf(o1.getRxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề ACT.
    private void sortListIncreaseAct(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o1.getAct()).compareTo(Integer.valueOf(o2.getAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionAct(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o2.getAct()).compareTo(Integer.valueOf(o1.getAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Total.
    private void sortListIncreaseTotal(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o1.getTotal()).compareTo(Integer.valueOf(o2.getTotal()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTotal(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o2.getTotal()).compareTo(Integer.valueOf(o1.getTotal()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Reg
    private void sortListIncreaseReg(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getReg()).compareTo(Double.valueOf(o2.getReg()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionReg(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getReg()).compareTo(Double.valueOf(o1.getReg()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề FRE.
    private void sortListIncreaseFre(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o1.getFre()).compareTo(Integer.valueOf(o2.getFre()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFre(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Integer.valueOf(o2.getFre()).compareTo(Integer.valueOf(o1.getFre()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề WTH
    private void sortListIncreaseWTH(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o1.getWith()).compareTo(Double.valueOf(o2.getWith()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionWTH(){
        Collections.sort(mList, new Comparator<ModelNodeHis>() {
            @Override
            public int compare(ModelNodeHis o1, ModelNodeHis o2) {
                return Double.valueOf(o2.getWith()).compareTo(Double.valueOf(o1.getWith()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
