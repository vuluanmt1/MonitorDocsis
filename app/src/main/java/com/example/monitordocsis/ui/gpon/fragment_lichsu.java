package com.example.monitordocsis.ui.gpon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.monitordocsis.congcu.lichsuAdapter;
import com.example.monitordocsis.congcu.lichsuModel;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class fragment_lichsu extends Fragment {
    View view;
//    private static final String URL_TELNET ="http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/telnet/getTelnet.php";
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private lichsuAdapter adapter;
    private List<lichsuModel> mListHis;
    private TextView txt_status, txt_rx,  txt_datetime;
    private String donVi,branch,usercode,version;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lichsu_onu,container,false);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        branch =user.getBranch();
        usercode = user.getEmail();
        version =user.getVersion();
        String url = urlData.url+version+"/telnet/getTelnet.php";
        Bundle bundle = getArguments();
        String maolt =bundle.getString("maolt");
        String maonu =bundle.getString("maonu");
        String donVi =bundle.getString("donVi");
        String start_date =bundle.getString("start_date");
        String end_date =bundle.getString("end_date");
        final JSONObject json_req = new JSONObject( );
        final JSONObject json_data_item = new JSONObject( );
        final JSONObject json_data_item_search = new JSONObject( );
        try {
            JSONArray json_arr_item = new JSONArray( );
            json_data_item_search.put("maolt",maolt);
            json_data_item_search.put("maonu",maonu);
            json_data_item_search.put("donVi",donVi);
            json_data_item_search.put("start_date",start_date);
            json_data_item_search.put("end_date",end_date);
            json_arr_item.put(json_data_item_search);
            JSONArray json_data = new JSONArray( );
            json_data_item.put("item", json_arr_item);
            json_data.put(json_data_item);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "tools");
            json_req.put("action", "getdb");
            json_req.put("data", json_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        Log.d("json_req",">>"+json_req);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    mListHis = new ArrayList<lichsuModel>();
                    for(int i=0; i < json_arr_result.length(); i++){
                        json_obj =json_arr_result.getJSONObject(i);
                        mListHis.add(new lichsuModel(json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),json_obj.getString("INACTIVE_TIME"),
                                json_obj.getString("MODEL"),json_obj.getString("DISTANCE"),
                                json_obj.getString("CREATEDATE")));
                    }
                    recyclerView = view.findViewById(R.id.recView);
                    progBar = view.findViewById(R.id.progBar);
                    adapter = new lichsuAdapter(getActivity(),mListHis);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
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
        txt_status =view.findViewById(R.id.txt_status);
        txt_status.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
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
        txt_rx =view.findViewById(R.id.txt_rxpw);
        txt_rx.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionRxpower();
                    check=false;
                }else{
                    sortListIncreaseRxpower();
                    check=true;
                }
            }
        });
        txt_datetime =view.findViewById(R.id.txt_time);
        txt_datetime.setOnClickListener(new View.OnClickListener() {
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
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    // Các hàm sắp xem theo Tiêu đề Mã Status.
    private void sortListIncreaseStatus (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return o1.getStatus().compareTo(o2.getStatus());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionStatus (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return o2.getStatus().compareTo(o1.getStatus());
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã RxPower.
    private void sortListIncreaseRxpower (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return Double.valueOf(o1.getRx()).compareTo(Double.valueOf(o2.getRx()));
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionRxpower (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return Double.valueOf(o2.getRx()).compareTo(Double.valueOf(o1.getRx()));
            }
        });
        adapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã Time.
    private void sortListIncreaseTime (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return o1.getDatetime().compareTo(o2.getDatetime());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReductionTime (){
        Collections.sort(mListHis, new Comparator<lichsuModel>() {
            @Override
            public int compare(lichsuModel o1, lichsuModel o2) {
                return o2.getDatetime().compareTo(o1.getDatetime());
            }
        });
        adapter.notifyDataSetChanged();
    }
}
