package com.example.monitordocsis.danhsachonu;

import android.os.Bundle;
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
import com.example.monitordocsis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentListOnuPort  extends Fragment {
    View view;
    public static final String URL_BASE_OLT = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_olt.php";
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private ArrayList<ListOnuPortModel>mListOnu;
    private ListOnuPortAdapter mOnuAdapter;
    private TextView  txt_maonu, txt_onuid,txt_status,txt_firmware, txt_rx, txt_model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_onu_port,container,false);
        Bundle bundle = getArguments();
        String khuvuc =bundle.getString("khuvuc");
        String maolt =bundle.getString("maolt");
        String portpon =bundle.getString("portpon");
        final JSONObject json_req = new JSONObject( );
        final JSONObject json_data_item_search = new JSONObject( );
        try {
            JSONArray json_arr_item = new JSONArray( );
            json_data_item_search.put("maolt",maolt);
            json_data_item_search.put("khuvuc",khuvuc);
            json_data_item_search.put("portpon",portpon);
            json_arr_item.put(json_data_item_search);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "loadOnuPort");
            json_req.put("data", json_arr_item);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_BASE_OLT, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    mListOnu = new ArrayList<ListOnuPortModel>();
                    for(int i=0; i < json_arr_result.length(); i++){
                        json_obj =json_arr_result.getJSONObject(i);
                        mListOnu.add(new ListOnuPortModel(json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),
                                json_obj.getString("INACTIVE_TIME"),json_obj.getString("MODEL"),
                                json_obj.getString("DISTANCE"),json_obj.getString("CREATEDATE")));
                    }
                    recyclerView = view.findViewById(R.id.recView);
                    progBar = view.findViewById(R.id.progBar);
                    mOnuAdapter = new ListOnuPortAdapter(getActivity(),mListOnu);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(mOnuAdapter);
                    progBar.setVisibility(View.GONE);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin từ onResponse!\n1. " + err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n1. " + error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
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
        txt_status =view.findViewById(R.id.txt_status);
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
                mOnuAdapter.getFilter().filter(s);
                System.out.println("Input:"+s);
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
    // Các hàm sắp xem theo Tiêu đề Mã ONU.
    private void sortListIncreaseMaonu (){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o1.getMaonu().compareTo(o2.getMaonu());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaonu (){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o2.getMaonu().compareTo(o1.getMaonu());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã ONUID.
    private void sortListIncreaseOnuID (){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o1.getOnuid().compareTo(o2.getOnuid());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuID (){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o2.getOnuid().compareTo(o1.getOnuid());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Status.
    private void sortListIncreaseStatus(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o1.getStatus().compareTo(o2.getStatus());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionStatus(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o2.getStatus().compareTo(o1.getStatus());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề RxPower.
    private void sortListIncreaseRxPower(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return Double.valueOf(o1.getRx()).compareTo(Double.valueOf(o2.getRx()));
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return Double.valueOf(o2.getRx()).compareTo(Double.valueOf(o1.getRx()));
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Model.
    private void sortListIncreaseModel(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o1.getModel().compareTo(o2.getModel());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionModel(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o2.getModel().compareTo(o1.getModel());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Firmware.
    private void sortListIncreaseFirmware(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o1.getFirmware().compareTo(o2.getFirmware());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFirmware(){
        Collections.sort(mListOnu, new Comparator<ListOnuPortModel>() {
            @Override
            public int compare(ListOnuPortModel o1, ListOnuPortModel o2) {
                return o2.getFirmware().compareTo(o1.getFirmware());
            }
        });
        mOnuAdapter.notifyDataSetChanged();
    }
}
