package com.example.monitordocsis.ui.theodoionu;

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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.monitordocsis.MainActivity;
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentFollowOnu extends Fragment {
    View view;
    private String donVi;
    private String branch;
    private String usercode;
    private String version;
//    public static final String URL_FOLLOW_ONU = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_follow_onu.php";
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private followOnuAdapter mAdapter;
    private List<followOnuModel> mListFollowOnu;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private TextView txt_point, txt_status_point, txt_maolt, txt_maonu, txt_port,txt_onuid, txt_mode,
            txt_profile,txt_firmware, txt_rx, txt_deactive, txt_inactive, txt_model, txt_total_onu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_follow_onu,container,false);
        txt_total_onu = view.findViewById(R.id.txt_total_onu);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        branch =user.getBranch();
        usercode = user.getEmail();
        version =user.getVersion();
        final String url_follow_onu = urlData.url+version+"/api_follow_onu.php";
        if(usercode.isEmpty()){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        final JSONObject json_req = new JSONObject( );
        try {
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "followOnu");
            json_req.put("donvi",donVi);
            json_req.put("branch",branch);
        }
        catch (JSONException err) {
            Log.d("Cảnh báo", "Không thể lấy thông tin");
        }
        Log.d("json_req",">>"+json_req);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_follow_onu, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    txt_total_onu.setText(response.getString("recordsTotal"));
                    try {
                        mListFollowOnu = new ArrayList<followOnuModel>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mListFollowOnu.add(new followOnuModel(json_obj.getString("REPEAT_ERRORS"),json_obj.getString("RANK_TRASITION"),json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                    json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                    json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                    json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                    json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),json_obj.getString("INACTIVE_TIME"),
                                    json_obj.getString("MODEL"),json_obj.getString("DISTANCE"),
                                    json_obj.getString("CREATEDATE"), json_obj.getString("ADDRESS")));
                        }
                        recyclerView = (RecyclerView)view.findViewById(R.id.recView);
                        progBar = view.findViewById(R.id.progBar);
                        mAdapter = new followOnuAdapter(getContext(),mListFollowOnu);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(mAdapter);
                        progBar.setVisibility(View.GONE);
                    }catch (Exception err){
                        alert_display("Cảnh báo", "Không thể lấy thông tin");
                        Log.d("err",">>>>"+err);
                    }
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    Log.d("JsonObjectRequest",">>>>"+err);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
        txt_point =view.findViewById(R.id.txt_point);
        txt_point.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPoint();
                    check=false;
                }else{
                    sortListIncreasePoint();
                    check=true;
                }
            }
        });
        txt_status_point =view.findViewById(R.id.txt_status_point);
        txt_status_point.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPointStatus();
                    check=false;
                }else{
                    sortListIncreasePointStatus();
                    check=true;
                }
            }
        });
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
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    // Các hàm sắp xem theo Tiêu đề POint.
    private void sortListIncreasePoint (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return Integer.valueOf(o1.getPoint()).compareTo(Integer.valueOf(o2.getPoint()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPoint (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return Integer.valueOf(o2.getPoint()).compareTo(Integer.valueOf(o1.getPoint()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề POint.
    private void sortListIncreasePointStatus (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getStatusPoint().compareTo(o2.getStatusPoint());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPointStatus (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getStatusPoint().compareTo(o1.getStatusPoint());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã OLT.
    private void sortListIncreaseMaolt (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getMaolt().compareTo(o2.getMaolt());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaolt (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getMaolt().compareTo(o1.getMaolt());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã ONU.
    private void sortListIncreaseMaonu (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getMaonu().compareTo(o2.getMaonu());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaonu (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getMaonu().compareTo(o1.getMaonu());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreasePort (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getPort().compareTo(o2.getPort());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPort (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getPort().compareTo(o1.getPort());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseOnuID (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getOnuid().compareTo(o2.getOnuid());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuID (){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getOnuid().compareTo(o1.getOnuid());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseProfile(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getProfile().compareTo(o2.getProfile());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionProfile(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getProfile().compareTo(o1.getProfile());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Port.
    private void sortListIncreaseFirmware(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getFirmware().compareTo(o2.getFirmware());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFirmware(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getFirmware().compareTo(o1.getFirmware());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseMode(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getMode().compareTo(o2.getMode());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMode(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getMode().compareTo(o1.getMode());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseRxPower(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return Double.valueOf(o1.getRx()).compareTo(Double.valueOf(o2.getRx()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return Double.valueOf(o2.getRx()).compareTo(Double.valueOf(o1.getRx()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseDeactiveReason(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getDeactiveReason().compareTo(o2.getDeactiveReason());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionDeactiveReason(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getDeactiveReason().compareTo(o1.getDeactiveReason());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mode.
    private void sortListIncreaseModel(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o1.getModel().compareTo(o2.getModel());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionModel(){
        Collections.sort(mListFollowOnu, new Comparator<followOnuModel>() {
            @Override
            public int compare(followOnuModel o1, followOnuModel o2) {
                return o2.getModel().compareTo(o1.getModel());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
