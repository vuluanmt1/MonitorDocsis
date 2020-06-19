package com.example.monitordocsis.ui.ticket.hoanthanh;

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
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentTicketComplete extends Fragment {
    private RecyclerView recView;
    private ProgressBar progBar;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private View view;
    private String donVi;
    private String version;
    private ArrayList<ModelTicketComplete>mList;
    private AdapterTicketComplete mAdapter;
    private TextView txt_codeTicket, txt_leve, txt_timeErr,txt_timeRecieve,txt_timeComplete,txt_timeClose,
            txt_branch, txt_deactive_reason, txt_maolt,
            txt_port_pon, txt_onu_act, txt_onu_inact,txt_total_onu,txt_percent_inact,
            txt_avg_rx_onu_act,txt_node_quang ,txt_result, txt_total_ticket;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_complete,container,false);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        version =user.getVersion();
        final String url_ticket = urlData.url+version+"/api_load_ticket.php";
        txt_total_ticket = view.findViewById(R.id.txt_total_ticket);
        donVi = user.getUnit();
        final JSONObject json_req = new JSONObject( );
        try {
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "ticket_complete");
            json_req.put("donvi",donVi);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        Log.d("API_ticket",">>"+json_req);
        Log.d("API_ticket_url",">>"+url_ticket);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url_ticket, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    txt_total_ticket.setText(response.getString("recordsTotal"));
                    mList = new ArrayList<ModelTicketComplete>();
                    for(int i=0; i < json_arr_result.length(); i++){
                        json_obj =json_arr_result.getJSONObject(i);
                        mList.add(new ModelTicketComplete(json_obj.getString("RANK_ERROR"),json_obj.getString("ENDDATE"),
                                json_obj.getString("SENTDATE"),json_obj.getString("PROCESSING_UNIT"),
                                json_obj.getString("OLTID"),json_obj.getString("PORT"),
                                json_obj.getString("ACTIVE"),json_obj.getString("INACTIVE"),
                                json_obj.getString("TOTAL_ONU"), json_obj.getString("SL_ONU_ERR"),
                                json_obj.getString("PERCENT_INACT"),json_obj.getString("AVG_RX"),
                                json_obj.getString("DEACTIVE_REASON"),json_obj.getString("NODE_QUANG"),
                                json_obj.getString("REPEAT_TIMES"), json_obj.getString("TICKET"),
                                json_obj.getString("DONERESULT"),json_obj.getString("DONENOTE"),
                                json_obj.getString("DONETIME"),json_obj.getString("CLOSETIME"),
                                json_obj.getString("CREATEDATE"),
                                json_obj.getString("AREA"),json_obj.getString("PROVINCE")));
                    }
                    recView = (RecyclerView)view.findViewById(R.id.recView);
                    progBar = view.findViewById(R.id.progBar);
                    mAdapter = new AdapterTicketComplete(getContext(),mList);
                    recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recView.setAdapter(mAdapter);
                    progBar.setVisibility(View.GONE);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin Ticket Recieve"+err.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(request);
        txt_maolt =view.findViewById(R.id.txt_maolt);
        txt_maolt.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMaOLT();
                    check=false;
                }else{
                    sortListIncreaseMaOLT();
                    check=true;
                }
            }
        });
        txt_leve =view.findViewById(R.id.txt_leve);
        txt_leve.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionLeve();
                    check=false;
                }else{
                    sortListIncreaseLeve();
                    check=true;
                }
            }
        });
        txt_timeRecieve =view.findViewById(R.id.txt_time_recieve_ticket);
        txt_timeRecieve.setOnClickListener(new View.OnClickListener() {
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
        txt_timeComplete =view.findViewById(R.id.txt_time_complete_ticket);
        txt_timeComplete.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTimeComplete();
                    check=false;
                }else{
                    sortListIncreaseTimeComplete();
                    check=true;
                }
            }
        });
        txt_timeErr =view.findViewById(R.id.txt_time_errors_ticket);
        txt_timeErr.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTimeErr();
                    check=false;
                }else{
                    sortListIncreaseTimeErr();
                    check=true;
                }
            }
        });
        txt_timeClose =view.findViewById(R.id.txt_time_close_ticket);
        txt_timeClose.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTimeClose();
                    check=false;
                }else{
                    sortListIncreaseTimeClose();
                    check=true;
                }
            }
        });
        txt_deactive_reason =view.findViewById(R.id.txt_nguyennhan);
        txt_deactive_reason.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
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
        txt_branch =view.findViewById(R.id.txt_unit_process);
        txt_branch.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionBranch();
                    check=false;
                }else{
                    sortListIncreaseBranch();
                    check=true;
                }
            }
        });
        txt_port_pon =view.findViewById(R.id.txt_port_pon);
        txt_port_pon.setOnClickListener(new View.OnClickListener() {
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
        txt_onu_act =view.findViewById(R.id.txt_onu_act);
        txt_onu_act.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionOnuAct();
                    check=false;
                }else{
                    sortListIncreaseOnuAct();
                    check=true;
                }
            }
        });
        txt_onu_inact =view.findViewById(R.id.txt_onu_inact);
        txt_onu_inact.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
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
        txt_total_onu =view.findViewById(R.id.txt_total_onu);
        txt_total_onu.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTotalOnu();
                    check=false;
                }else{
                    sortListIncreaseTotalOnu();
                    check=true;
                }
            }
        });
        txt_percent_inact =view.findViewById(R.id.txt_percent_onu_inact);
        txt_percent_inact.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentOnuInAct();
                    check=false;
                }else{
                    sortListIncreasePercentOnuInAct();
                    check=true;
                }
            }
        });
        txt_avg_rx_onu_act =view.findViewById(R.id.txt_Avg_rxpower);
        txt_avg_rx_onu_act.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentAvgRxOnuAct();
                    check=false;
                }else{
                    sortListIncreasePercentAvgRxOnuAct();
                    check=true;
                }
            }
        });
        txt_node_quang =view.findViewById(R.id.txt_node_quang);
        txt_node_quang.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentNodeQuang();
                    check=false;
                }else{
                    sortListIncreasePercentNodeQuang();
                    check=true;
                }
            }
        });
        txt_result =view.findViewById(R.id.txt_result);
        txt_result.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentResult();
                    check=false;
                }else{
                    sortListIncreasePercentResult();
                    check=true;
                }
            }
        });
        txt_codeTicket =view.findViewById(R.id.txt_code_ticket);
        txt_codeTicket.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionPercentCodeTicket();
                    check=false;
                }else{
                    sortListIncreasePercentCodeTicket();
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

    // Các hàm sắp xem theo Tiêu đề LEVE.
    private void sortListIncreaseLeve (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getLeve().compareTo(o2.getLeve());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionLeve (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getLeve().compareTo(o1.getLeve());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Time.
    private void sortListIncreaseTime (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getTimeRecieve().compareTo(o2.getTimeRecieve());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTime (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getTimeRecieve().compareTo(o1.getTimeRecieve());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Thời điểm hoàn thành.
    private void sortListIncreaseTimeComplete (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getTimeComplete().compareTo(o2.getTimeComplete());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTimeComplete (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getTimeComplete().compareTo(o1.getTimeComplete());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Thời điểm đóng.
    private void sortListIncreaseTimeClose (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getTimeCloseTicket().compareTo(o2.getTimeCloseTicket());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTimeClose (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getTimeCloseTicket().compareTo(o1.getTimeCloseTicket());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Thời điểm xảy ra lỗi.
    private void sortListIncreaseTimeErr (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getTimeErrors().compareTo(o2.getTimeErrors());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTimeErr (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getTimeErrors().compareTo(o1.getTimeErrors());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    // Các hàm sắp xem theo Tiêu đề Chi nhánh.
    private void sortListIncreaseBranch (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getUnitProcess().compareTo(o2.getUnitProcess());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionBranch (){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getUnitProcess().compareTo(o1.getUnitProcess());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Nguyên nhân.
    private void sortListIncreaseDeactReason(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getDeact_time().compareTo(o2.getDeact_time());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionDeactReason(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getDeact_time().compareTo(o1.getDeact_time());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Mã OLT.
    private void sortListIncreaseMaOLT(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getMaolt().compareTo(o2.getMaolt());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMaOLT(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getMaolt().compareTo(o1.getMaolt());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Port.
    private void sortListIncreasePort(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getPortpon().compareTo(o2.getPortpon());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPort(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getPortpon().compareTo(o1.getPortpon());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Active ONU.
    private void sortListIncreaseOnuAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o1.getOnuActive()).compareTo(Integer.valueOf(o2.getOnuActive()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o2.getOnuActive()).compareTo(Integer.valueOf(o1.getOnuActive()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề InActive ONU.
    private void sortListIncreaseOnuInAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o1.getOnuInactive()).compareTo(Integer.valueOf(o2.getOnuInactive()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionOnuInAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o2.getOnuInactive()).compareTo(Integer.valueOf(o1.getOnuInactive()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Total ONU.
    private void sortListIncreaseTotalOnu(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o1.getTotalONU()).compareTo(Integer.valueOf(o2.getTotalONU()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTotalOnu(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o2.getTotalONU()).compareTo(Integer.valueOf(o1.getTotalONU()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Tỉ lệ ONU InActive.
    private void sortListIncreasePercentOnuInAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o1.getPercentOnuInAct()).compareTo(Integer.valueOf(o2.getPercentOnuInAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentOnuInAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Integer.valueOf(o2.getPercentOnuInAct()).compareTo(Integer.valueOf(o1.getPercentOnuInAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề AVG Rx ONU Active.
    private void sortListIncreasePercentAvgRxOnuAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Double.valueOf(o1.getAvgRxOnuAct()).compareTo(Double.valueOf(o2.getAvgRxOnuAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentAvgRxOnuAct(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return Double.valueOf(o2.getAvgRxOnuAct()).compareTo(Double.valueOf(o1.getAvgRxOnuAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Node quang ảnh hưởng.
    private void sortListIncreasePercentNodeQuang(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getNameNodeQuang().compareTo(o2.getNameNodeQuang());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentNodeQuang(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getNameNodeQuang().compareTo(o1.getNameNodeQuang());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Kết quả.
    private void sortListIncreasePercentResult(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getResult().compareTo(o2.getResult());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentResult(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getResult().compareTo(o1.getResult());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề mã Ticket.
    private void sortListIncreasePercentCodeTicket(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o1.getCodeTicket().compareTo(o2.getCodeTicket());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionPercentCodeTicket(){
        Collections.sort(mList, new Comparator<ModelTicketComplete>() {
            @Override
            public int compare(ModelTicketComplete o1, ModelTicketComplete o2) {
                return o2.getCodeTicket().compareTo(o1.getCodeTicket());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
