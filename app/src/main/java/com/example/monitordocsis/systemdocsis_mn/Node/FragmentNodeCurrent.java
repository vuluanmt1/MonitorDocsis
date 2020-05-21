package com.example.monitordocsis.systemdocsis_mn.Node;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monitordocsis.Global;
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.example.monitordocsis.systemdocsis_mn.cmts.cmtsAdapter;
import com.example.monitordocsis.systemdocsis_mn.cmts.cmtsModel;
import com.example.monitordocsis.systemdocsis_mn.cmts.nodeAdapter;
import com.example.monitordocsis.systemdocsis_mn.cmts.nodeModel;
import com.example.monitordocsis.url.urlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentNodeCurrent  extends Fragment {
    View view;
    private JSONArray json_arr_result;
    private JSONArray json_arr_cmts;
    private JSONArray json_arr_node;
    private ProgressBar progBar;
    private JSONObject json_obj;
    private cmtsAdapter mCmtsAdapter;
    private nodeAdapter mNodeAdapter;
    private Spinner spinner_cmts,spinner_node;
    private ImageView btn_refresh_node, btn_search_node;
    private ListView listItem;
    private ArrayList<nodeModel>mListNode;
    private ArrayList<cmtsModel>mListCMTS;
    private ArrayList<ModelNodeCurrent>mListNodeCurrten;
    private AdapterNodeCurrent mAdapter;
    private String donVi;
    private String cmstID;
    private String nodeID;
    private String version_docsis_mn;
    private String cmst_ID;
    private String node_ID;
    private TextView txt_cmts_id, txt_ifindex;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_node_current,container, false);
        permissionUser permission = (permissionUser) getContext().getApplicationContext();
        version_docsis_mn = permission.getVersion_docsis_mn();
        final String url = urlData.url1+version_docsis_mn+"/api_load_current_upstream.php";
        final String url_cmts=urlData.url1+version_docsis_mn+"/api_loadbranch.php";
        donVi =permission.getUnit();
        loadCMTS(url_cmts);
        initView();
        Bundle bundle = getArguments();
        cmstID=bundle.getString("cmtsid");
        nodeID =bundle.getString("ifindex");
        try {
            final JSONObject json_req = new JSONObject( );
            try {
                JSONObject json_data_item_key = new JSONObject( );
                json_data_item_key.put("cmtsid",cmstID);
                json_data_item_key.put("ifindex",nodeID);
                JSONArray json_arr_item = new JSONArray( );
                json_arr_item.put(json_data_item_key);
                JSONObject json_data_item = new JSONObject( );
                json_data_item.put("act", "refesh");
                json_data_item.put("item",json_arr_item);
                JSONArray json_data = new JSONArray( );
                json_data.put(json_data_item);
                json_req.put("user_name", "htvt");
                json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                json_req.put("action", "current_upstream");
                json_req.put("donvi", donVi);
                json_req.put("data", json_data);
            }
            catch (JSONException err) {
                alert_display("Cảnh báo", "Không thể lấy thông tin ");
            }
            Log.d("Node_current_url",">>"+url);
            Log.d("Node_current",">>"+json_req);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        json_arr_result = response.getJSONArray("data");
                        mListNodeCurrten = new ArrayList<ModelNodeCurrent>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mListNodeCurrten.add(new ModelNodeCurrent(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                    json_obj.getString("SNR"),json_obj.getString("MER"),
                                    json_obj.getString("FEC"),json_obj.getString("UNFEC"),
                                    json_obj.getString("TXPOWER"), json_obj.getString("RXPOWER"),
                                    json_obj.getString("ACTIVE"),json_obj.getString("TOTAL"),json_obj.getString("REG"),
                                    json_obj.getString("FRE"),json_obj.getString("WITH"),
                                    json_obj.getString("MICREF"),json_obj.getString("MOD"),json_obj.getString("CURRTIME"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                        }
                        mAdapter = new AdapterNodeCurrent(getContext(),mListNodeCurrten);
                        listItem.setAdapter(mAdapter);
                        progBar.setVisibility(View.GONE);
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
            Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }catch (Exception erros){
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        spinner_cmts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               cmtsModel cmts = (cmtsModel) mCmtsAdapter.getItem(position);
                cmstID =cmts.getCmtsID();
                final JSONObject json_node = new JSONObject( );
                try {
                    JSONObject json_data_item = new JSONObject( );
                    json_data_item.put("cmtsid", cmstID);
                    JSONArray json_data = new JSONArray( );
                    json_data.put(json_data_item);
                    json_node.put("user_name", "htvt");
                    json_node.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                    json_node.put("donvi",donVi);
                    json_node.put("action", "node");
                    json_node.put("data",json_data);
                }
                catch (JSONException err) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
                Log.d("json_node:",">>>"+json_node);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_cmts, json_node, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            json_arr_node =response.getJSONArray("data");
                            mListNode = new ArrayList<nodeModel>();
                            for(int i=0; i < json_arr_node.length(); i++){
                                json_obj =json_arr_node.getJSONObject(i);
                                mListNode.add(new nodeModel(json_obj.getString("IFINDEX"),json_obj.getString("IFALIAS")));
                            }
                            mNodeAdapter = new nodeAdapter(getContext(),mListNode);
                            spinner_node.setAdapter(mNodeAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(jsonObjectRequest);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_node.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nodeModel item  = mNodeAdapter.getItem(position);
                nodeID =item.getIfindex();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btn_search_node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                listItem.setVisibility(View.GONE);
                try {
                    final JSONObject json_req = new JSONObject( );
                    try {
                        JSONObject json_data_item_key = new JSONObject( );
                        json_data_item_key.put("cmtsid",cmstID);
                        json_data_item_key.put("ifindex",nodeID);
                        JSONArray json_arr_item = new JSONArray( );
                        json_arr_item.put(json_data_item_key);
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act", "refesh");
                        json_data_item.put("item",json_arr_item);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_req.put("user_name", "htvt");
                        json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_req.put("action", "current_upstream");
                        json_req.put("donvi", donVi);
                        json_req.put("data", json_data);
                    }
                    catch (JSONException err) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("Node_current_url",">>"+url);
                    Log.d("Node_current",">>"+json_req);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                mListNodeCurrten = new ArrayList<ModelNodeCurrent>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mListNodeCurrten.add(new ModelNodeCurrent(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                            json_obj.getString("SNR"),json_obj.getString("MER"),
                                            json_obj.getString("FEC"),json_obj.getString("UNFEC"),
                                            json_obj.getString("TXPOWER"), json_obj.getString("RXPOWER"),
                                            json_obj.getString("ACTIVE"),json_obj.getString("TOTAL"),json_obj.getString("REG"),
                                            json_obj.getString("FRE"),json_obj.getString("WITH"),
                                            json_obj.getString("MICREF"),json_obj.getString("MOD"),json_obj.getString("CURRTIME"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }
                                listItem.setVisibility(View.VISIBLE);
                                mAdapter = new AdapterNodeCurrent(getContext(),mListNodeCurrten);
                                listItem.setAdapter(mAdapter);
                                progBar.setVisibility(View.GONE);
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
                    Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception erros){
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
            }
        });
        btn_refresh_node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < mListNodeCurrten.size();i++){
                    ModelNodeCurrent item = mListNodeCurrten.get(i);
                    cmst_ID=item.getCmtsid();
                    node_ID=item.getIfindex();
                }
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                listItem.setVisibility(View.GONE);
                try {
                    final JSONObject json_req = new JSONObject( );
                    try {
                        JSONObject json_data_item_key = new JSONObject( );
                        json_data_item_key.put("cmtsid",cmst_ID);
                        json_data_item_key.put("ifindex",node_ID);
                        JSONArray json_arr_item = new JSONArray( );
                        json_arr_item.put(json_data_item_key);
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act", "refesh");
                        json_data_item.put("item",json_arr_item);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_req.put("user_name", "htvt");
                        json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_req.put("action", "current_upstream");
                        json_req.put("donvi", donVi);
                        json_req.put("data", json_data);
                    }
                    catch (JSONException err) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("refresh_url",">>"+url);
                    Log.d("refresh_data",">>"+json_req);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("response:"+response);
                            try {
                                json_arr_result = response.getJSONArray("data");
                                mListNodeCurrten = new ArrayList<ModelNodeCurrent>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mListNodeCurrten.add(new ModelNodeCurrent(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                            json_obj.getString("SNR"),json_obj.getString("MER"),
                                            json_obj.getString("FEC"),json_obj.getString("UNFEC"),
                                            json_obj.getString("TXPOWER"), json_obj.getString("RXPOWER"),
                                            json_obj.getString("ACTIVE"),json_obj.getString("TOTAL"),json_obj.getString("REG"),
                                            json_obj.getString("FRE"),json_obj.getString("WITH"),
                                            json_obj.getString("MICREF"),json_obj.getString("MOD"),json_obj.getString("CURRTIME"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }
                                listItem.setVisibility(View.VISIBLE);
                                mAdapter = new AdapterNodeCurrent(getContext(),mListNodeCurrten);
                                listItem.setAdapter(mAdapter);
                                progBar.setVisibility(View.GONE);
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
                    Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception erros){
                    alert_display("Cảnh báo", "Không thể lấy thông tin ");
                }
            }
        });
        return view;
    }

    private void initView(){
        listItem = view.findViewById(R.id.list_item);
        spinner_cmts = view.findViewById(R.id.cb_cmts);
        spinner_node =view.findViewById(R.id.cb_node);
        progBar = view.findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
        btn_search_node = view.findViewById(R.id.btn_search);
        btn_refresh_node = view.findViewById(R.id.btn_refesh);
        txt_cmts_id = view.findViewById(R.id.txt_cmtsid_current);
        txt_ifindex =view.findViewById(R.id.txt_ifindex_current);
    }
    public void loadCMTS (String url){
        final JSONObject json_search1 = new JSONObject( );
        try {
            json_search1.put("user_name", "htvt");
            json_search1.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search1.put("donvi",donVi);
            json_search1.put("action", "cmts");
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_cmts =response.getJSONArray("data");
                    mListCMTS = new ArrayList<cmtsModel>();
                    for(int i=0; i < json_arr_cmts.length(); i++){
                        json_obj =json_arr_cmts.getJSONObject(i);
                        mListCMTS.add(new cmtsModel(json_obj.getString("CMTSID"),json_obj.getString("TITLE")));
                    }
                    mCmtsAdapter = new cmtsAdapter(getContext(),mListCMTS);
                    spinner_cmts.setAdapter(mCmtsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin ");
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
