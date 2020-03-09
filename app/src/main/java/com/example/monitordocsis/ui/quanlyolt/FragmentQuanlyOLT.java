package com.example.monitordocsis.ui.quanlyolt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.khuvuc.areaAdapter;
import com.example.monitordocsis.khuvuc.areaModel;
import com.example.monitordocsis.olt.oltAdapter;
import com.example.monitordocsis.olt.oltModel;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.province.provinceAdapter;
import com.example.monitordocsis.province.provinceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentQuanlyOLT extends Fragment {
    public static final String URL_BASE_AREA = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_area.php";
    public static final String URL_BASE_OLT = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_olt.php";
    private LinearLayout layoutSearch;
    private RecyclerView recView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private JSONArray json_arr_area;
    private JSONArray json_arr_province;
    private JSONArray json_arr_olt;
    private ArrayList<quanlyOltModel>mList;
    private quanlyOltAdapter mAdapter;
    private ArrayList<areaModel> mListArea;
    private ArrayList<provinceModel> mListProvince;
    private ArrayList<oltModel> mListOlt;
    private areaAdapter mAreaAdapter;
    private provinceAdapter mProvinceAdapter;
    private oltAdapter mOltAdapter;
    private Spinner spinnerArea, spinnerProvince, spinnerOlt;
    private String donVi;
    private Button btn_search ;
    private TextView txt_search;
    View root;
    private String codeArea;
    private String codeProvince;
    private String codeOlt;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        loadArea(URL_BASE_AREA);
        root = inflater.inflate(R.layout.fragment_quanly_olt, container, false);
        layoutSearch =root.findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.VISIBLE);
        progBar = root.findViewById(R.id.progBar);
        progBar.setVisibility(View.INVISIBLE);
        spinnerArea = root.findViewById(R.id.cb_khuvuc);
        spinnerProvince = root.findViewById(R.id.cb_province);
        spinnerOlt = root.findViewById(R.id.cb_olt);
        btn_search =root.findViewById(R.id.btn_search);
        txt_search = root.findViewById(R.id.txt_search);
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.animator_button(view);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    areaModel item = (areaModel) mAreaAdapter.getItem(position);
                    codeArea = item.getCodeArea();
                    loadProvince(URL_BASE_AREA,codeArea);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceModel provinceItem = (provinceModel) mProvinceAdapter.getItem(position);
                codeProvince = provinceItem.getProvince();
                loadOlt(URL_BASE_AREA,codeProvince);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerOlt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oltModel oltItem = (oltModel) mOltAdapter.getItem(position);
                codeOlt =oltItem.getOltID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                final JSONObject json_search_olt = new JSONObject( );
                try {
                    JSONArray json_arr_data = new JSONArray( );
                    JSONObject json_data_item = new JSONObject();
                    json_data_item.put("area",codeArea);
                    json_data_item.put("province",codeProvince);
                    json_data_item.put("oltid",codeOlt);
                    json_arr_data.put(json_data_item);
                    json_search_olt.put("user_name", "htvt");
                    json_search_olt.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                    json_search_olt.put("donvi",donVi);
                    json_search_olt.put("action", "loadPort");
                    json_search_olt.put("data",json_arr_data);
                }
                catch (JSONException err) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
                }
                Log.d("json_search_olt",">>>"+json_search_olt);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_BASE_OLT, json_search_olt, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            json_arr_result = response.getJSONArray("data");
                            mList = new ArrayList<quanlyOltModel>();
                            for(int i=0; i < json_arr_result.length(); i++){
                                json_obj =json_arr_result.getJSONObject(i);
                                mList.add(new quanlyOltModel(json_obj.getString("PORTPON"),json_obj.getString("ACTONU"),
                                        json_obj.getString("INACTONU"),json_obj.getString("TOTALONU"),
                                        json_obj.getString("SNOLT"),json_obj.getString("AREA")));
                            }
                            recView = (RecyclerView)root.findViewById(R.id.recView);
                            progBar = root.findViewById(R.id.progBar);
                            mAdapter = new quanlyOltAdapter(getContext(),mList);
                            recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recView.setAdapter(mAdapter);
                            progBar.setVisibility(View.GONE);
                        }catch (Exception err){
                            alert_display("Cảnh báo", "Không thể lấy thông tin 2!\n"+ err.getMessage( ));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
                    }
                });
                Volley.newRequestQueue(getActivity()).add(request);
            }
        });
        return root;
    }
    private void loadArea(String url){
        final JSONObject json_search1 = new JSONObject( );
        try {

            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("donvi",donVi);
            json_search1.put("action", "area");
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        Log.d("json_search1",">>>"+json_search1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_area =response.getJSONArray("data");
                    mListArea = new ArrayList<areaModel>();
                    for(int i=0; i < json_arr_area.length(); i++){
                        json_obj =json_arr_area.getJSONObject(i);
                        if(json_obj.getString("AREA").equals("1")){
                            mListArea.add(new areaModel("1","Miền Bắc"));
                        }else if(json_obj.getString("AREA").equals("2")){
                            mListArea.add(new areaModel("2","Miền Trung"));
                        }else{
                            mListArea.add(new areaModel("3","Miền Nam"));
                        }
                    }
                    mAreaAdapter = new areaAdapter(getActivity(),mListArea);
                    spinnerArea.setAdapter(mAreaAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
    }
    private void loadProvince(String url,String codeArea){
        final JSONObject json_search1 = new JSONObject( );
        try {
            JSONArray json_arr_data = new JSONArray( );
            JSONObject json_data_item = new JSONObject();
            json_data_item.put("area",codeArea);
            json_arr_data.put(json_data_item);
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("donvi",donVi);
            json_search1.put("action", "province");
            json_search1.put("data",json_arr_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        Log.d("json_province",">>>"+json_search1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_province =response.getJSONArray("data");
                    mListProvince = new ArrayList<provinceModel>();
                    for(int i=0; i < json_arr_province.length(); i++){
                        json_obj =json_arr_province.getJSONObject(i);
                        mListProvince.add(new provinceModel(json_obj.getString("PROVINCE")));
                    }
                    mProvinceAdapter = new provinceAdapter(getActivity(),mListProvince);
                    spinnerProvince.setAdapter(mProvinceAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
    }
    private void loadOlt(String url,String codeProvince){
        final JSONObject json_search1 = new JSONObject( );
        try {
            JSONArray json_arr_data = new JSONArray( );
            JSONObject json_data_item = new JSONObject();
            json_data_item.put("province",codeProvince);
            json_arr_data.put(json_data_item);
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("donvi",donVi);
            json_search1.put("action", "olt");
            json_search1.put("data",json_arr_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        Log.d("json_olt",">>>"+json_search1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_olt =response.getJSONArray("data");
                    mListOlt = new ArrayList<oltModel>();
                    for(int i=0; i < json_arr_olt.length(); i++){
                        json_obj =json_arr_olt.getJSONObject(i);
                        mListOlt.add(new oltModel(json_obj.getString("OLTID")));
                    }
                    mOltAdapter = new oltAdapter(getActivity(),mListOlt);
                    spinnerOlt.setAdapter(mOltAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}