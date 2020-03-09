package com.example.monitordocsis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpstreamCurrentActivity extends AppCompatActivity {
    public static final String URL_SEARCH ="http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/docsis/api_loadbranch.php";
    public static final String URL_CURRENT_UPS ="http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/docsis/api_load_current_upstream.php";
    private JSONArray json_arr_result;
        private JSONArray json_arr_cmts;
        private JSONArray json_arr_node;
        private ProgressBar progBar;
        private JSONObject json_obj;
        private cmtsAdapter cmtsAdapter;
        private nodeAdapter nodeAdapter;
        private ArrayList<nodeModel>arrNode;
        private upstreamCurrentAdapter currentAdapter;
        private ArrayList<cmtsModel>arrCmtsModel;
        private Spinner spinner_cmts,spinner_node;
        private ListView listItem;
        private ArrayList<upstreamCurrentModel>mListItem;
        private String cmstID;
        private String nodeID;
        private String donVi;
        private Button btn_ls_node;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upstream_current);
        permissionUser permission = (permissionUser) getApplicationContext();
        donVi =permission.getUnit();
        initView();
        loadCMTS(URL_SEARCH);
        Intent intent = getIntent();
        Bundle bundle =intent.getBundleExtra("data");
       final String cmtsid = bundle.getString("cmtsid");
       final String ifindex = bundle.getString("ifindex");
       try {
           final JSONObject json_req = new JSONObject( );
           try {
               JSONObject json_data_item_key = new JSONObject( );
               json_data_item_key.put("cmtsid",cmtsid);
               json_data_item_key.put("ifindex",ifindex);
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
               alert_display("Cảnh báo", "Không thể lấy thông tin Data truyền vào API!\n1. " + err.getMessage( ));
           }

           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_CURRENT_UPS, json_req, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                   try {
                       json_arr_result = response.getJSONArray("data");
                       mListItem = new ArrayList<upstreamCurrentModel>();
                       for(int i=0; i < json_arr_result.length(); i++){
                           json_obj =json_arr_result.getJSONObject(i);
                           mListItem.add(new upstreamCurrentModel(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                   json_obj.getString("SNR"),json_obj.getString("MER"),
                                   json_obj.getString("FEC"),json_obj.getString("UNFEC"),
                                   json_obj.getString("TXPOWER"), json_obj.getString("RXPOWER"),
                                   json_obj.getString("ACTIVE"),json_obj.getString("TOTAL"),json_obj.getString("REG"),
                                   json_obj.getString("FRE"),json_obj.getString("WITH"),
                                   json_obj.getString("MICREF"),json_obj.getString("MOD"),json_obj.getString("CURRTIME"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                       }
                       currentAdapter = new upstreamCurrentAdapter(mListItem);
                       listItem.setAdapter(currentAdapter);
                       progBar.setVisibility(View.GONE);
                   }catch (Exception er){
                       alert_display("Cảnh báo", "Không thể lấy thông tin từ response!\n1. " + er.getMessage( ));
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n1. " + error.getMessage( ));
               }
           });
           RequestQueue queue = Volley.newRequestQueue(UpstreamCurrentActivity.this);
           queue.add(jsonObjectRequest);

       }catch (Exception erros){
           alert_display("Cảnh báo", "Không thể lấy thông tin jsonObjectRequest!\n1. " + erros.getMessage( ));
       }
       spinner_cmts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               cmtsModel cmts = cmtsAdapter.getItem(position);
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
                   alert_display("Cảnh báo", "Không thể lấy thông tin API!\n1. " + err.getMessage( ));
               }
               Log.d("json_node:",">>>"+json_node);
               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SEARCH, json_node, new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           json_arr_node =response.getJSONArray("data");
                           arrNode = new ArrayList<nodeModel>();
                           for(int i=0; i < json_arr_node.length(); i++){
                               json_obj =json_arr_node.getJSONObject(i);
                               arrNode.add(new nodeModel(json_obj.getString("IFINDEX"),json_obj.getString("IFALIAS")));
                           }
                           nodeAdapter = new nodeAdapter(UpstreamCurrentActivity.this,arrNode);
                           spinner_node.setAdapter(nodeAdapter);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n1. " + error.getMessage( ));
                   }
               });
               RequestQueue queue = Volley.newRequestQueue(UpstreamCurrentActivity.this);
               queue.add(jsonObjectRequest);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

    }
    private void initView(){
        listItem = findViewById(R.id.list_item);
        spinner_cmts = findViewById(R.id.cb_cmts);
        spinner_node =findViewById(R.id.cb_node);
        progBar = findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
        btn_ls_node = findViewById(R.id.btn_ls_node);
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(UpstreamCurrentActivity.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
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
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_cmts =response.getJSONArray("data");
                    arrCmtsModel = new ArrayList<cmtsModel>();
                    for(int i=0; i < json_arr_cmts.length(); i++){
                        json_obj =json_arr_cmts.getJSONObject(i);
                        arrCmtsModel.add(new cmtsModel(json_obj.getString("CMTSID"),json_obj.getString("TITLE")));
                    }
                    cmtsAdapter = new cmtsAdapter(UpstreamCurrentActivity.this,arrCmtsModel);
                    spinner_cmts.setAdapter(cmtsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n1. " + error.getMessage( ));
            }
        });
        RequestQueue queue = Volley.newRequestQueue(UpstreamCurrentActivity.this);
        queue.add(jsonObjectRequest);
    }
}
