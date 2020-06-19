package com.example.monitordocsis.ui.ticket;

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
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentTicketPort extends Fragment {
    private RecyclerView recView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private View view;
    private String donVi;
    private String version;
//    public static final String URL_TICKET = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_ticket.php";
    private ArrayList<ticketPortModel> mListTicketPort;
    private ticketPortAdapter mTicketPortAdapter;
    private TextView txt_onuAct, txt_onuInAct, txt_onuPercentInAct, txt_onuAvgAct, txt_time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_ticket_port,container,false);
        permissionUser user = (permissionUser) getActivity().getApplicationContext();
        version =user.getVersion();
        final String url_ticket = urlData.url+version+"/api_load_ticket.php";
        donVi = user.getUnit();
        Bundle bundle = getArguments();
        final String maolt = bundle.getString("maolt");
        final String port = bundle.getString("port");
        loadTicketPort(url_ticket,maolt,port);
        txt_onuAct = view.findViewById(R.id.txt_onu_act);
        txt_onuAct.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionOnuActive();
                    check=false;
                }else{
                    sortListIncreaseOnuActive();
                    check=true;
                }
            }
        });
        txt_onuInAct =view.findViewById(R.id.txt_onu_inact);
        txt_onuInAct.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionOnuInAct();
                    check=false;
                }else{
                    sortListIncreaseOnuInAct();
                    check=true;
                }
            }
        });
        txt_time =view.findViewById(R.id.txt_time);
        txt_time.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
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
        txt_onuPercentInAct =view.findViewById(R.id.txt_percent_onu_inact);
        txt_onuPercentInAct.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentOnuInact();
                    check=false;
                }else{
                    sortListIncreasePercentOnuInact();
                    check=true;
                }
            }
        });
        txt_onuAvgAct =view.findViewById(R.id.txt_Avg_rxpower);
        txt_onuAvgAct.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionAvgRxOnuAct();
                    check=false;
                }else{
                    sortListIncreaseAvgRxOnuAct();
                    check=true;
                }
            }
        });
        return view;
    }
    private void loadTicketPort(String url,String maolt, String port){
        final JSONObject json_req = new JSONObject( );
        JSONObject  json_data = new JSONObject();
        JSONArray json_arr = new JSONArray();
        try {
            json_data.put("maolt",maolt);
            json_data.put("port",port);
            json_arr.put(json_data);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "loadHisPort");
            json_req.put("donvi",donVi);
            json_req.put("data",json_arr);
        }
        catch (JSONException err) {
            Log.d("Cảnh báo", "Không thể lấy thông tin ");
        }
        Log.d("json_TicketPort:",">>>"+json_req);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    mListTicketPort = new ArrayList<ticketPortModel>();
                    for(int i=0; i < json_arr_result.length(); i++){
                        json_obj =json_arr_result.getJSONObject(i);
                        mListTicketPort.add(new ticketPortModel(json_obj.getString("ACTONU"),json_obj.getString("INACTONU"),
                                json_obj.getString("TOTALONU"),
                                json_obj.getString("PERCENT_INACT"),json_obj.getString("AVG_RXPOWER"),
                                json_obj.getString("CREATEDATE"),json_obj.getString("SUB_TOTAL_ONU_RX_ERROR")));
                    }
                    recView = (RecyclerView)view.findViewById(R.id.recView);
                    progBar = view.findViewById(R.id.progBar);
                    mTicketPortAdapter = new ticketPortAdapter(getContext(),mListTicketPort);
                    recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recView.setAdapter(mTicketPortAdapter);
                    progBar.setVisibility(View.GONE);
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
        Volley.newRequestQueue(getContext()).add(request);
    }
    // Các hàm sắp xem theo Tiêu đề ONU Active.
    private void sortListIncreaseOnuActive (){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o1.getOnuActive()).compareTo(Integer.valueOf(o2.getOnuActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuActive (){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o2.getOnuActive()).compareTo(Integer.valueOf(o1.getOnuActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề InActive Time.
    private void sortListIncreaseOnuInAct (){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o1.getOnuInActive()).compareTo(Integer.valueOf(o2.getOnuInActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuInAct (){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o2.getOnuInActive()).compareTo(Integer.valueOf(o1.getOnuInActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Tỷ lệ ONU InActive.
    private void sortListIncreasePercentOnuInact(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o1.getOnuPercentInActive()).compareTo(Integer.valueOf(o2.getOnuPercentInActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentOnuInact(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Integer.valueOf(o2.getOnuPercentInActive()).compareTo(Integer.valueOf(o1.getOnuPercentInActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Avg Rx ONU Active.
    private void sortListIncreaseAvgRxOnuAct(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Double.valueOf(o1.getOnuAvgActive()).compareTo(Double.valueOf(o2.getOnuAvgActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    private void sortListReductionAvgRxOnuAct(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return Double.valueOf(o2.getOnuAvgActive()).compareTo(Double.valueOf(o1.getOnuAvgActive()));
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Time.
    private void sortListIncreaseTime(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTime(){
        Collections.sort(mListTicketPort, new Comparator<ticketPortModel>() {
            @Override
            public int compare(ticketPortModel o1, ticketPortModel o2) {
                return o2.getCreateDate().compareTo(o1.getCreateDate());
            }
        });
        mTicketPortAdapter.notifyDataSetChanged();
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
