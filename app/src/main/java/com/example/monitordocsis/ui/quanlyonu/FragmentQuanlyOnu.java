package com.example.monitordocsis.ui.quanlyonu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.MainActivity;
import com.example.monitordocsis.R;
import com.example.monitordocsis.khuvuc.areaAdapter;
import com.example.monitordocsis.khuvuc.areaModel;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentQuanlyOnu extends Fragment {
    private LinearLayout layoutSearch;
    private RecyclerView recView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private JSONArray json_arr_area;
//    public static final String URL_BASE_AREA = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_area.php";
//    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_onu.php";
      private ArrayList<areaModel> mAreaList;
      private ArrayList<quanlyOnuModel>mONUList;
      private quanlyOnuAdapter mOnuAdapter;
      private areaAdapter mAdapter;
      private String maKhuVu;
      private Switch swt_signal, swt_status, swt_tr069;
      private Boolean checkErrSignal =false,checkStatus =false,checkTR069= false;
      private String donVi;
        private String version;
        private String usercode ;
      private Button btn_search;
      private EditText edt_maonu;
      private ImageView txt_search,txt_search_onu;
    private View root;
    Spinner spinnerArea;
    private TextView txt_maolt, txt_maonu, txt_port,txt_onuid,txt_status, txt_mode,
            txt_profile,txt_firmware, txt_rx, txt_deactive, txt_inactive, txt_model, txt_total_onu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        version =user.getVersion();
        final String url_base_area = urlData.url+version+"/api_load_area.php";
        final String url_base= urlData.url+version+"/api_load_onu.php";
        donVi = user.getUnit();
        usercode = user.getEmail();
        if(usercode.isEmpty()){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        loadArea(url_base_area);
        root = inflater.inflate(R.layout.fragment_quanly_onu, container, false);
        txt_total_onu = root.findViewById(R.id.txt_total_onu);
        recView = root.findViewById(R.id.recView);
        progBar = root.findViewById(R.id.progBar);
        layoutSearch =root.findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.VISIBLE);
        progBar.setVisibility(View.INVISIBLE);
        txt_search = root.findViewById(R.id.txt_search);
        btn_search = root.findViewById(R.id.btn_search);
        txt_search_onu = root.findViewById(R.id.txt_search_onu);
        spinnerArea = root.findViewById(R.id.cb_khuvuc);
        swt_signal =root.findViewById(R.id.swt_tinhieu);
        swt_status =root.findViewById(R.id.swt_status);
        swt_tr069 =root.findViewById(R.id.swt_tr09);
        edt_maonu =root.findViewById(R.id.edt_search_snonu);
        edt_maonu.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.animator_button(view);
                if(layoutSearch.getVisibility()==View.GONE){
                    layoutSearch.setVisibility(View.VISIBLE);
                    recView.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.GONE);
                    recView.setVisibility(View.VISIBLE);
                }
            }
        });
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaModel model = (areaModel) mAdapter.getItem(position);
                maKhuVu = model.getCodeArea();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        swt_signal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkErrSignal =true;
                }else {
                    checkErrSignal =false;
                }
            }
        });
        swt_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkStatus =true;
                }else {
                    checkStatus =false;
                }
            }
        });
        swt_tr069.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkTR069 =true;
                }else {
                    checkTR069 =false;
                }
            }
        });
        txt_search_onu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                recView.setVisibility(View.GONE);
                progBar.setVisibility(View.VISIBLE);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                try {
                    String maonu = String.valueOf(edt_maonu.getText());
                    final JSONObject json_api = new JSONObject();
                    try {
                        JSONArray json_arr_item = new JSONArray( );
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("item",json_arr_item);
                        JSONObject json_data_item_search = new JSONObject( );
                        json_data_item_search.put("khuVuc",maKhuVu);
                        json_data_item_search.put("maONU",maonu);
                        json_data_item_search.put("checkSignal",checkErrSignal);
                        json_data_item_search.put("checkStatus",checkStatus);
                        json_data_item_search.put("checkTR069",checkTR069);
                        json_arr_item.put(json_data_item_search);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_api.put("user_name", "htvt");
                        json_api.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_api.put("action", "searchONU");
                        json_api.put("donvi",donVi);
                        json_api.put("data", json_data);
                    }catch (Exception err){
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("json_api",">>>"+json_api);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_base, json_api, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                txt_total_onu.setText(response.getString("recordsTotal"));
                                mONUList = new ArrayList<quanlyOnuModel>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mONUList.add(new quanlyOnuModel(json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                            json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                            json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                            json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                            json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),json_obj.getString("INACTIVE_TIME"),
                                            json_obj.getString("MODEL"),json_obj.getString("DISTANCE"),
                                            json_obj.getString("CREATEDATE"),json_obj.getString("ADDRESS")));
                                }
                                recView.setVisibility(View.VISIBLE);
                                progBar = root.findViewById(R.id.progBar);
                                mOnuAdapter = new quanlyOnuAdapter(getContext(),mONUList);
                                recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recView.setAdapter(mOnuAdapter);
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
                    Volley.newRequestQueue(getActivity()).add(request);
                    request.setRetryPolicy(new DefaultRetryPolicy(5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin 3!\n"+ err.getMessage( ));
                }
            }
        });
        txt_maolt =root.findViewById(R.id.txt_maolt);
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
        txt_maonu =root.findViewById(R.id.txt_maonu);
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
        txt_port =root.findViewById(R.id.txt_portpon);
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
        txt_onuid =root.findViewById(R.id.txt_onuid);
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
        txt_status =root.findViewById(R.id.txt_status);
        txt_status.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionStatus();
                    check=false;
                }else{
                    sortListIncreaseStatus();
                    check=true;
                }
            }
        });
        txt_mode =root.findViewById(R.id.txt_mode);
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
        txt_profile =root.findViewById(R.id.txt_profile);
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
        txt_firmware =root.findViewById(R.id.txt_firmware);
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
        txt_rx =root.findViewById(R.id.txt_rxpw);
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
        txt_deactive =root.findViewById(R.id.txt_deact);
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
        txt_model =root.findViewById(R.id.txt_model);
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
        return root;
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
                mOnuAdapter.getFilter().filter(s);
                System.out.println("Input"+s);
                return false;
            }
        });
    }

    public void loadArea(String url){
        final JSONObject json_search1 = new JSONObject();
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
                    mAreaList = new ArrayList<areaModel>();
                    for(int i=0; i < json_arr_area.length(); i++){
                        json_obj =json_arr_area.getJSONObject(i);
                        if(json_obj.getString("AREA").equals("1")){
                            mAreaList.add(new areaModel("1","Miền Bắc"));
                        }else if(json_obj.getString("AREA").equals("2")){
                            mAreaList.add(new areaModel("2","Miền Trung"));
                        }else{
                            mAreaList.add(new areaModel("3","Miền Nam"));
                        }
                    }
                    mAdapter = new areaAdapter(getActivity(),mAreaList);
                    spinnerArea.setAdapter(mAdapter);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin ");
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
    // Các hàm sắp xem theo Tiêu đề Mã OLT.
    private void sortListIncreaseMaolt (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getMaolt().compareTo(o2.getMaolt());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaolt (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getMaolt().compareTo(o1.getMaolt());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã ONU.
    private void sortListIncreaseMaonu (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getMaonu().compareTo(o2.getMaonu());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaonu (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getMaonu().compareTo(o1.getMaonu());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreasePort (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getPort().compareTo(o2.getPort());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPort (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getPort().compareTo(o1.getPort());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề OnuID.
    private void sortListIncreaseOnuID (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getOnuid().compareTo(o2.getOnuid());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuID (){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getOnuid().compareTo(o1.getOnuid());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Profile.
    private void sortListIncreaseProfile(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getProfile().compareTo(o2.getProfile());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionProfile(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getProfile().compareTo(o1.getProfile());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Firmware.
    private void sortListIncreaseFirmware(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getFirmware().compareTo(o2.getFirmware());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFirmware(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getFirmware().compareTo(o1.getFirmware());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseMode(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getMode().compareTo(o2.getMode());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMode(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getMode().compareTo(o1.getMode());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề RxPower.
    private void sortListIncreaseRxPower(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return Double.valueOf(o1.getRx()).compareTo(Double.valueOf(o2.getRx()));
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return Double.valueOf(o2.getRx()).compareTo(Double.valueOf(o1.getRx()));
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Deactive Reason.
    private void sortListIncreaseDeactiveReason(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getDeactiveReason().compareTo(o2.getDeactiveReason());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionDeactiveReason(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getDeactiveReason().compareTo(o1.getDeactiveReason());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Model.
    private void sortListIncreaseModel(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getModel().compareTo(o2.getModel());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionModel(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getModel().compareTo(o1.getModel());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseStatus(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o1.getStatus().compareTo(o2.getStatus());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionStatus(){
        Collections.sort(mONUList, new Comparator<quanlyOnuModel>() {
            @Override
            public int compare(quanlyOnuModel o1, quanlyOnuModel o2) {
                return o2.getStatus().compareTo(o1.getStatus());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }

}