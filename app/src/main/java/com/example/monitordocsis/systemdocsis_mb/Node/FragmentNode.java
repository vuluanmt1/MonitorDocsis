package com.example.monitordocsis.systemdocsis_mb.Node;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.MainActivity;
import com.example.monitordocsis.R;
import com.example.monitordocsis.branchAdapter;
import com.example.monitordocsis.branchModel;
import com.example.monitordocsis.cmtsAdapter;
import com.example.monitordocsis.cmtsModel;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentNode  extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ProgressBar progBar;
    private AdapterNode mAdapter;
    private List<ModelNode> mList;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private JSONArray json_arr_branch;
    private JSONArray json_arr_cmts;
    ArrayList<ModelNode>mListNode;
    ArrayList<branchModel>mListBranch;
    ArrayList<cmtsModel>mListCMTS;
    private branchAdapter mAdapterBranch;
    private cmtsAdapter mAdapterCMTS;
    private Spinner spinner_branch,spinner_cmts;
    private String donVi;
    private String branch;
    private String usercode;
    private String version_docsis_mb;
    private LinearLayout layoutSearch;
    private TextView txt_node, txt_interface, txt_branch,txt_snr, txt_mer,
                     txt_fec,txt_unfec, txt_tx, txt_rx, txt_act, txt_total ,
                     txt_reg,txt_fre,txt_with, txt_total_node, txt_cmts;
    private ImageView txt_search,txt_search_node;
    private Switch swt_warming;
    private EditText edit_search_node;
    private EditText edt_min_fec,edt_max_fec,edt_min_unfec,edt_max_unfec,
                     edt_min_snr,edt_max_snr,edt_min_mer,edt_max_mer,edt_min_tx,
                     edt_max_tx,edt_min_rx,edt_max_rx;
    private String cmstID;
    private String branchID;
    private Boolean checkErrors= false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_node,container, false);
        bindViews();
        layoutSearch =view.findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.GONE);
        txt_total_node = view.findViewById(R.id.txt_total_node);
        txt_search = view.findViewById(R.id.txt_search);
        txt_search_node = view.findViewById(R.id.txt_search_node);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        usercode = user.getEmail();
        if(usercode.isEmpty()){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        version_docsis_mb = user.getVersion_docsis_mb();
        final String url = urlData.url1+version_docsis_mb+"/api_loaddata.php";
        String url_branch = urlData.url1+version_docsis_mb+"/api_loadbranch.php";
        String url_cmts = urlData.url1+version_docsis_mb+"/api_loadbranch.php";
        loadBranch(url_branch);
        loadCMTS(url_cmts);
        final JSONObject json_req = new JSONObject( );
        try {
            JSONObject json_data_item = new JSONObject( );
            json_data_item.put("act", "load");
            JSONArray json_data = new JSONArray( );
            json_data.put(json_data_item);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "upstream");
            json_req.put("donvi",donVi);
            json_req.put("data", json_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        Log.d("upstream",">>"+json_req);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    txt_total_node.setText(response.getString("recordsTotal"));
                    try {
                        mList = new ArrayList<ModelNode>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mList.add(new ModelNode(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                    json_obj.getString("MERCHANTCODE"),json_obj.getString("IFSIGQSNR"),
                                    json_obj.getString("AVGONLINECMDSSNR"),json_obj.getString("FECCORRECTED"),
                                    json_obj.getString("FECUNCORRECTABLE"), json_obj.getString("AVGONLINECMUSPOWER"),
                                    json_obj.getString("AVGONLINECMDSPOWER"),json_obj.getString("UPCHANNELCMACTIVE"),json_obj.getString("UPCHANNELCMTOTAL"),
                                    json_obj.getString("UPCHANNELCMREGISTERED"),json_obj.getString("UPCHANNELFREQUENCY"),
                                    json_obj.getString("UPCHANNELWIDTH"),json_obj.getString("TITLE"),json_obj.getString("MODIFIEDDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                        }
                        recyclerView = (RecyclerView)view.findViewById(R.id.recView);
                        progBar = view.findViewById(R.id.progBar);
                        mAdapter = new AdapterNode(getContext(),mList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(mAdapter);
                        progBar.setVisibility(View.GONE);
                    }catch (Exception er){
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                }catch (Exception er){
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
        spinner_cmts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmtsModel cmts = mAdapterCMTS.getItem(position);
                cmstID =cmts.getCmtsID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchModel branch = mAdapterBranch.getItem(position);
                branchID = branch.getCode();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(layoutSearch.getVisibility()==View.GONE){
                    layoutSearch.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        txt_search_node.setOnClickListener(new View.OnClickListener() {
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
                    String name_node = String.valueOf(edit_search_node.getText());
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
                        json_data_item_search.put("branch",branchID);
                        json_data_item_search.put("cmtsID",cmstID);
                        json_data_item_search.put("name_node",name_node);
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
                        json_api.put("action", "upstream");
                        json_api.put("donvi",donVi);
                        json_api.put("data", json_data);

                    }catch (Exception e){
                        alert_display("Cảnh báo", "Không thể lấy thông tin");
                    }
                    Log.d("JSon:",">>>"+json_api);
                    JsonObjectRequest jsonObject_search  = new JsonObjectRequest(Request.Method.POST, url, json_api, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                txt_total_node.setText(response.getString("recordsTotal"));
                                mListNode = new ArrayList<ModelNode>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mListNode.add(new ModelNode(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                            json_obj.getString("MERCHANTCODE"),json_obj.getString("IFSIGQSNR"),
                                            json_obj.getString("AVGONLINECMDSSNR"),json_obj.getString("FECCORRECTED"),
                                            json_obj.getString("FECUNCORRECTABLE"), json_obj.getString("AVGONLINECMUSPOWER"),
                                            json_obj.getString("AVGONLINECMDSPOWER"),json_obj.getString("UPCHANNELCMACTIVE"),json_obj.getString("UPCHANNELCMTOTAL"),
                                            json_obj.getString("UPCHANNELCMREGISTERED"),json_obj.getString("UPCHANNELFREQUENCY"),
                                            json_obj.getString("UPCHANNELWIDTH"),json_obj.getString("TITLE"),json_obj.getString("MODIFIEDDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView = (RecyclerView)view.findViewById(R.id.recView);
                                progBar = view.findViewById(R.id.progBar);
                                mAdapter = new AdapterNode(getContext(),mListNode);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(mAdapter);
                                progBar.setVisibility(View.GONE);

                            }catch (Exception er){
                                alert_display("Cảnh báo", "Không thể lấy thông tin");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alert_display("Cảnh báo", "Không thể lấy thông tin");
                            Log.e("Err",error.getMessage());
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(jsonObject_search);

                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                }
            }
        });
        txt_node =view.findViewById(R.id.txt_node);
        txt_node.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionNode();
                    check=false;
                }else{
                    sortListIncreaseNode();
                    check=true;
                }
            }
        });
        txt_interface =view.findViewById(R.id.txt_inteface);
        txt_interface.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionInterface();
                    check=false;
                }else{
                    sortListIncreaseInterface();
                    check=true;
                }
            }
        });
        txt_branch =view.findViewById(R.id.txt_branch);
        txt_branch.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
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
        txt_cmts =view.findViewById(R.id.txt_cmts);
        txt_cmts.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionCMTS();
                    check=false;
                }else{
                    sortListIncreaseCMTS();
                    check=true;
                }
            }
        });
        return view;
    }
    public void loadBranch (String url){
        final JSONObject json_search = new JSONObject( );
        try {
            json_search.put("user_name", "htvt");
            json_search.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search.put("donvi",donVi);
            json_search.put("action", "branch");
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_branch =response.getJSONArray("data");
                    mListBranch = new ArrayList<branchModel>();
                    if(donVi.equals("TTVT") || donVi.equals("CALLCENTER") ||donVi.equals("CN5")){
                        mListBranch.add(new branchModel("all","Tất Cả"));
                        for(int i=0; i < json_arr_branch.length(); i++){
                            json_obj =json_arr_branch.getJSONObject(i);
                            mListBranch.add(new branchModel(json_obj.getString("CODE_"),json_obj.getString("DESCRIPTION")));
                        }
                    }else{
                        for(int i=0; i < json_arr_branch.length(); i++){
                            json_obj =json_arr_branch.getJSONObject(i);
                            mListBranch.add(new branchModel(json_obj.getString("CODE_"),json_obj.getString("DESCRIPTION")));
                        }
                    }
                    mAdapterBranch = new branchAdapter(getActivity(),mListBranch);
                    spinner_branch.setAdapter(mAdapterBranch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }
    public void loadCMTS(String url){
        final JSONObject json_search1 = new JSONObject( );
        try {
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("donvi",donVi);
            json_search1.put("action", "cmts");
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        Log.d("json_search1",">>>"+json_search1);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_cmts =response.getJSONArray("data");
                    mListCMTS = new ArrayList<cmtsModel>();
                    mListCMTS.add(new cmtsModel("all","Tất Cả"));
                    for(int i=0; i < json_arr_cmts.length(); i++){
                        json_obj =json_arr_cmts.getJSONObject(i);
                        mListCMTS.add(new cmtsModel(json_obj.getString("CMTSID"),json_obj.getString("TITLE")));
                    }
                    mAdapterCMTS = new cmtsAdapter(getActivity(),mListCMTS);
                    spinner_cmts.setAdapter(mAdapterCMTS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + error.getMessage( ));
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
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
    // Các hàm sắp xem theo Tiêu đề NODE.
    private void sortListIncreaseNode (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o1.getNode().compareTo(o2.getNode());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionNode (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o2.getNode().compareTo(o1.getNode());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề INTERFACE.
    private void sortListIncreaseInterface (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o1.getInteface().compareTo(o2.getInteface());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionInterface (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o2.getInteface().compareTo(o1.getInteface());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Chi nhánh.
    private void sortListIncreaseBranch (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o1.getBranch().compareTo(o2.getBranch());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionBranch (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o2.getBranch().compareTo(o1.getBranch());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề SNR.
    private void sortListIncreaseSNR (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getSnr()).compareTo(Double.valueOf(o2.getSnr()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionSNR (){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getSnr()).compareTo(Double.valueOf(o1.getSnr()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề MER.
    private void sortListIncreaseMER(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getMer()).compareTo(Double.valueOf(o2.getMer()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMER(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getMer()).compareTo(Double.valueOf(o1.getMer()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề FEC.
    private void sortListIncreaseFEC(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getFec()).compareTo(Double.valueOf(o2.getFec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFEC(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getFec()).compareTo(Double.valueOf(o1.getFec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề UnFec.
    private void sortListIncreaseUNFEC(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getUnfec()).compareTo(Double.valueOf(o2.getUnfec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionUNFEC(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getUnfec()).compareTo(Double.valueOf(o1.getUnfec()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề TxPw
    private void sortListIncreaseTxPower(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getTxpower()).compareTo(Double.valueOf(o2.getTxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTxPower(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getTxpower()).compareTo(Double.valueOf(o1.getTxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề RxPower
    private void sortListIncreaseRxPower(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getRxpower()).compareTo(Double.valueOf(o2.getRxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRxPower(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getRxpower()).compareTo(Double.valueOf(o1.getRxpower()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề ACT.
    private void sortListIncreaseAct(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o1.getAct()).compareTo(Integer.valueOf(o2.getAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionAct(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o2.getAct()).compareTo(Integer.valueOf(o1.getAct()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Total.
    private void sortListIncreaseTotal(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o1.getTotal()).compareTo(Integer.valueOf(o2.getTotal()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTotal(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o2.getTotal()).compareTo(Integer.valueOf(o1.getTotal()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề Reg
    private void sortListIncreaseReg(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getReg()).compareTo(Double.valueOf(o2.getReg()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionReg(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getReg()).compareTo(Double.valueOf(o1.getReg()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề FRE.
    private void sortListIncreaseFre(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o1.getFre()).compareTo(Integer.valueOf(o2.getFre()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFre(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Integer.valueOf(o2.getFre()).compareTo(Integer.valueOf(o1.getFre()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề WTH
    private void sortListIncreaseWTH(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o1.getWith()).compareTo(Double.valueOf(o2.getWith()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionWTH(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return Double.valueOf(o2.getWith()).compareTo(Double.valueOf(o1.getWith()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    // Các hàm sắp xem theo Tiêu đề CMTS
    private void sortListIncreaseCMTS(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o1.getNameCMTS().compareTo(o2.getNameCMTS());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void sortListReductionCMTS(){
        Collections.sort(mList, new Comparator<ModelNode>() {
            @Override
            public int compare(ModelNode o1, ModelNode o2) {
                return o2.getNameCMTS().compareTo(o1.getNameCMTS());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    private void bindViews(){
        spinner_branch = view.findViewById(R.id.cb_branch);
        spinner_cmts = view.findViewById(R.id.cb_cmts);
        swt_warming =view.findViewById(R.id.swt_warning);
        edit_search_node =view.findViewById(R.id.edt_search_node);
        edit_search_node.setImeOptions(EditorInfo.IME_ACTION_DONE);
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
}
