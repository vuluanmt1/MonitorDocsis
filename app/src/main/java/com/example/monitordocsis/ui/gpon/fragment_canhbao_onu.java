package com.example.monitordocsis.ui.gpon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class fragment_canhbao_onu extends Fragment  {
    View view;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private canhbao_onu_adapter adapter;
    private List<canhbao_onu_model> canhbao_onu_models;
    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_canhbaoonu.php";
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private fragment_canhbao_onu f_canhbao;
    private String donVi;
    private String branch;
    private TextView txt_maolt, txt_maonu, txt_port,txt_onuid, txt_mode,
            txt_profile,txt_firmware, txt_rx, txt_deactive, txt_inactive, txt_model;
    Spinner dynamicSpinner;
    public  static fragment_canhbao_onu newIntance(){
        fragment_canhbao_onu item = new fragment_canhbao_onu();
        return item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_canhbaoonu,container,false);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        branch =user.getBranch();
        final JSONObject json_req = new JSONObject( );
        try {
            JSONObject json_data_item = new JSONObject( );
            json_data_item.put("act", "load");
            JSONArray json_data = new JSONArray( );
            json_data.put(json_data_item);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "canhbao");
            json_req.put("donvi",donVi);
            json_req.put("branch",branch);
            json_req.put("data", json_data);
        }
        catch (JSONException err) {
            Log.d("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_BASE, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    try {
                        canhbao_onu_models = new ArrayList<canhbao_onu_model>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            canhbao_onu_models.add(new canhbao_onu_model(json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                    json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                    json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                    json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                    json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),json_obj.getString("INACTIVE_TIME"),
                                    json_obj.getString("MODEL"),json_obj.getString("DISTANCE"),
                                    json_obj.getString("CREATEDATE")));
                        }
                        recyclerView = (RecyclerView)view.findViewById(R.id.recView);
                        progBar = view.findViewById(R.id.progBar);
                        adapter = new canhbao_onu_adapter(getContext(),canhbao_onu_models, f_canhbao);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        progBar.setVisibility(View.GONE);

                    }catch (Exception err){
                        alert_display("Cảnh báo", "Không thể lấy thông tin 2!\n"+ err.getMessage( ));
                        Log.d("err",">>>>"+err);
                    }
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin 3!\n"+ err.getMessage( ));
                    Log.d("JsonObjectRequest",">>>>"+err);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        txt_maolt =view.findViewById(R.id.txt_maolt);
        txt_maolt.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMaolt();
                    check=false;
                }else{
                    sortListIncreaseMaolt();
                    check=true;
                }
            }
        });
        txt_maonu =view.findViewById(R.id.txt_maonu);
        txt_maonu.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMaonu();
                    check=false;
                }else{
                    sortListIncreaseMaonu();
                    check=true;
                }
            }
        });
        txt_port =view.findViewById(R.id.txt_portpon);
        txt_port.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPort();
                    check=false;
                }else{
                    sortListIncreasePort();
                    check=true;
                }
            }
        });
        txt_onuid =view.findViewById(R.id.txt_onuid);
        txt_onuid.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionOnuID();
                    check=false;
                }else{
                    sortListIncreaseOnuID();
                    check=true;
                }
            }
        });
        txt_mode =view.findViewById(R.id.txt_mode);
        txt_mode.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMode();
                    check=false;
                }else{
                    sortListIncreaseMode();
                    check=true;
                }
            }
        });
        txt_profile =view.findViewById(R.id.txt_profile);
        txt_profile.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionProfile();
                    check=false;
                }else{
                    sortListIncreaseProfile();
                    check=true;
                }
            }
        });
        txt_firmware =view.findViewById(R.id.txt_firmware);
        txt_firmware.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionFirmware();
                    check=false;
                }else{
                    sortListIncreaseFirmware();
                    check=true;
                }
            }
        });
        txt_rx =view.findViewById(R.id.txt_rxpw);
        txt_rx.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
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
        txt_deactive =view.findViewById(R.id.txt_deact);
        txt_deactive.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionDeactiveReason();
                    check=false;
                }else{
                    sortListIncreaseDeactiveReason();
                    check=true;
                }
            }
        });
        txt_model =view.findViewById(R.id.txt_model);
        txt_model.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionModel();
                    check=false;
                }else{
                    sortListIncreaseModel();
                    check=true;
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
    // Các hàm sắp xem theo Tiêu đề Mã OLT.
    private void sortListIncreaseMaolt (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getMaolt().compareTo(o2.getMaolt());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionMaolt (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getMaolt().compareTo(o1.getMaolt());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã ONU.
    private void sortListIncreaseMaonu (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getMaonu().compareTo(o2.getMaonu());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionMaonu (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getMaonu().compareTo(o1.getMaonu());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreasePort (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getPort().compareTo(o2.getPort());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionPort (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getPort().compareTo(o1.getPort());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseOnuID (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getOnuid().compareTo(o2.getOnuid());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuID (){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getOnuid().compareTo(o1.getOnuid());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseProfile(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getProfile().compareTo(o2.getProfile());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionProfile(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getProfile().compareTo(o1.getProfile());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseFirmware(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getFirmware().compareTo(o2.getFirmware());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionFirmware(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getFirmware().compareTo(o1.getFirmware());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseMode(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getMode().compareTo(o2.getMode());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionMode(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getMode().compareTo(o1.getMode());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseRxPower(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return Double.valueOf(o1.getRx()).compareTo(Double.valueOf(o2.getRx()));
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return Double.valueOf(o2.getRx()).compareTo(Double.valueOf(o1.getRx()));
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseDeactiveReason(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getDeactiveReason().compareTo(o2.getDeactiveReason());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionDeactiveReason(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getDeactiveReason().compareTo(o1.getDeactiveReason());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseModel(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o1.getModel().compareTo(o2.getModel());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionModel(){
        Collections.sort(canhbao_onu_models, new Comparator<canhbao_onu_model>() {
            @Override
            public int compare(canhbao_onu_model o1, canhbao_onu_model o2) {
                return o2.getModel().compareTo(o1.getModel());
            }
        });
        adapter.notifyDataSetChanged();
    }
}
