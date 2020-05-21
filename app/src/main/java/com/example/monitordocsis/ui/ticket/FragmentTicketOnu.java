package com.example.monitordocsis.ui.ticket;

import android.os.Bundle;
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

public class FragmentTicketOnu extends Fragment {
    private RecyclerView recView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private View view;
    private String donVi;
    private String version;
//    public static final String URL_TICKET = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_ticket.php";
    private ArrayList<ticketOnuModel> mListTicketOnu;
    private ticketOnuAdapter mTicketOnuAdapter;
    private TextView txt_maonu, txt_status, txt_rxpower,txt_inact_time, txt_deactive,txt_statusSubnum, txt_time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_ticket_onu,container,false);
        permissionUser user = (permissionUser) getActivity().getApplicationContext();
        donVi = user.getUnit();
        version =user.getVersion();
        final String url_ticket = urlData.url+version+"/api_load_ticket.php";
        Bundle bundle = getArguments();
        final String maolt = bundle.getString("maolt");
        final String port = bundle.getString("port");
        final String area = bundle.getString("area");
        loadTicketOnu(url_ticket,maolt,port,area);
        txt_maonu = view.findViewById(R.id.txt_onu_serial);
        txt_maonu.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMaOnu();
                    check=false;
                }else{
                    sortListIncreaseMaOnu();
                    check=true;
                }
            }
        });
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
        txt_rxpower =view.findViewById(R.id.txt_rxpw);
        txt_rxpower.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
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
        txt_inact_time =view.findViewById(R.id.txt_inact_time);
        txt_inact_time.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionInactTime();
                    check=false;
                }else{
                    sortListIncreaseInactTime();
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
                    sortListReductionDeactReason();
                    check=false;
                }else{
                    sortListIncreaseDeactReason();
                    check=true;
                }
            }
        });
        txt_statusSubnum =view.findViewById(R.id.txt_status_subnum);
        txt_statusSubnum.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionStatusHD();
                    check=false;
                }else{
                    sortListIncreaseStatusHD();
                    check=true;
                }
            }
        });
        return view;
    }
    private void loadTicketOnu(String url,String maolt, String port, String area){
        final JSONObject json_req = new JSONObject( );
        JSONObject  json_data = new JSONObject();
        JSONArray json_arr = new JSONArray();
        try {
            json_data.put("maolt",maolt);
            json_data.put("port",port);
            json_data.put("area",area);
            json_arr.put(json_data);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "loadONU");
            json_req.put("donvi",donVi);
            json_req.put("data",json_arr);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    mListTicketOnu = new ArrayList<ticketOnuModel>();
                    for(int i=0; i < json_arr_result.length(); i++){
                        json_obj =json_arr_result.getJSONObject(i);
                        mListTicketOnu.add(new ticketOnuModel(json_obj.getString("SNONU"),json_obj.getString("STATUS"),
                                json_obj.getString("RXPOWER"),
                                json_obj.getString("ADDRESS"),json_obj.getString("SERVICESTATUS"),
                                json_obj.getString("CREATEDATE"),json_obj.getString("INACTIVE_TIME"),
                                json_obj.getString("DEACTIVE_REASON")));
                    }
                    recView = (RecyclerView)view.findViewById(R.id.recView);
                    progBar = view.findViewById(R.id.progBar);
                    mTicketOnuAdapter = new ticketOnuAdapter(getContext(),mListTicketOnu);
                    recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recView.setAdapter(mTicketOnuAdapter);
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
    }
    // Các hàm sắp xem theo Tiêu đề STT
    private void sortListIncreaseMaOnu (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getMaonu().compareTo(o2.getMaonu());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaOnu (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getMaonu().compareTo(o1.getMaonu());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Status.
    private void sortListIncreaseStatus (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getStatus().compareTo(o2.getStatus());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionStatus (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getStatus().compareTo(o1.getStatus());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề RxPower.
    private void sortListIncreaseRxpower (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return Double.valueOf(o1.getRxpower()).compareTo(Double.valueOf(o2.getRxpower()));
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxpower (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return Double.valueOf(o2.getRxpower()).compareTo(Double.valueOf(o1.getRxpower()));
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề InActive Time.
    private void sortListIncreaseInactTime (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getInactTime().compareTo(o2.getInactTime());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionInactTime (){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getInactTime().compareTo(o1.getInactTime());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Nguyên nhân.
    private void sortListIncreaseDeactReason(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getDeactReason().compareTo(o2.getDeactReason());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionDeactReason(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getDeactReason().compareTo(o1.getDeactReason());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Trạng thái HĐ.
    private void sortListIncreaseStatusHD(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getServiceStatus().compareTo(o2.getServiceStatus());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionStatusHD(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getServiceStatus().compareTo(o1.getServiceStatus());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Time.
    private void sortListIncreaseTime(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTime(){
        Collections.sort(mListTicketOnu, new Comparator<ticketOnuModel>() {
            @Override
            public int compare(ticketOnuModel o1, ticketOnuModel o2) {
                return o2.getCreateDate().compareTo(o1.getCreateDate());
            }
        });
        mTicketOnuAdapter.notifyDataSetChanged();
    }

    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }


}
