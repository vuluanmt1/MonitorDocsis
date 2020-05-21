package com.example.monitordocsis.systemdocsis_mb.CableModem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
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

public class FragmentCMCurrent extends Fragment {
    View view;
    private ListView listItem;
    private JSONArray json_arr_result;
    private ProgressBar progBar;
    private JSONObject json_obj;
    private ImageView btn_refresh_mac, btn_clear_mac;
    private EditText edt_mac_current;
    private ArrayList<ModelCMCurrent>mList;
    private AdapterCMCurrent mAdapter;
    private String donVi,version_docsis_mb, macAddress, addressCustomer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cablemodem_current,container,false);
        initView();
        permissionUser permission = (permissionUser) getContext().getApplicationContext();
        version_docsis_mb = permission.getVersion_docsis_mb();
        donVi =permission.getUnit();
        final String url = urlData.url1+version_docsis_mb+"/api_load_cmCurrent.php";
        Bundle bundle = getArguments();
        macAddress=bundle.getString("mac");
        addressCustomer =bundle.getString("address");
        edt_mac_current.setText(macAddress);
        try {
            final JSONObject json_req = new JSONObject( );
            try {
                JSONObject json_data_item_key = new JSONObject( );
                json_data_item_key.put("mac",macAddress);
                JSONArray json_arr_item = new JSONArray( );
                json_arr_item.put(json_data_item_key);
                JSONObject json_data_item = new JSONObject( );
                json_data_item.put("act", "load");
                json_data_item.put("item",json_arr_item);
                JSONArray json_data = new JSONArray( );
                json_data.put(json_data_item);
                json_req.put("user_name", "htvt");
                json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                json_req.put("action", "current_cable");
                json_req.put("donvi", donVi);
                json_req.put("data", json_data);
            }
            catch (JSONException err) {
                alert_display("Cảnh báo", "Không thể lấy thông tin ");
            }
            Log.d("JSON_mac_Current",">>>"+json_req);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("response:"+response);
                    try {
                        json_arr_result = response.getJSONArray("data");
                        mList = new ArrayList<ModelCMCurrent>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mList.add(new ModelCMCurrent(json_obj.getString("STATUS"),json_obj.getString("SNR"),
                                    json_obj.getString("MER"),json_obj.getString("FEC"),
                                    json_obj.getString("UNFEC"),json_obj.getString("TX"),
                                    json_obj.getString("RX"), json_obj.getString("IPCM"),
                                    json_obj.getString("CPENUMBER"),json_obj.getString("CPEIP"),json_obj.getString("CPEMAC"),json_obj.getString("CMTS"),
                                    json_obj.getString("NODE"),json_obj.getString("IFDESC"),
                                    json_obj.getString("ADDRESS"),json_obj.getString("TIME")));
                        }
                        mAdapter = new AdapterCMCurrent(getContext(),mList);
                        listItem.setAdapter(mAdapter);
                        progBar.setVisibility(View.GONE);
                    }catch (Exception er){
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                }
            });
            Volley.newRequestQueue(getContext()).add(request);
            request.setRetryPolicy(new DefaultRetryPolicy(15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }catch (Exception err){
            alert_display("Cảnh báo", "Không thể lấy thông tin ");
        }
        btn_refresh_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                listItem.setVisibility(View.GONE);
                String mac  = String.valueOf(edt_mac_current.getText()).trim();
                try {
                    final JSONObject json_req = new JSONObject( );
                    try {
                        JSONObject json_data_item_key = new JSONObject( );
                        json_data_item_key.put("mac",mac);
                        JSONArray json_arr_item = new JSONArray( );
                        json_arr_item.put(json_data_item_key);
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act", "load");
                        json_data_item.put("item",json_arr_item);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_req.put("user_name", "htvt");
                        json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_req.put("action", "current_cable");
                        json_req.put("donvi", donVi);
                        json_req.put("data", json_data);
                    }
                    catch (JSONException err) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("JSON_mac_Current",">>>"+json_req);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("response:"+response);
                            try {
                                json_arr_result = response.getJSONArray("data");
                                mList = new ArrayList<ModelCMCurrent>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mList.add(new ModelCMCurrent(json_obj.getString("STATUS"),json_obj.getString("SNR"),
                                            json_obj.getString("MER"),json_obj.getString("FEC"),
                                            json_obj.getString("UNFEC"),json_obj.getString("TX"),
                                            json_obj.getString("RX"), json_obj.getString("IPCM"),
                                            json_obj.getString("CPENUMBER"),json_obj.getString("CPEIP"),json_obj.getString("CPEMAC"),json_obj.getString("CMTS"),
                                            json_obj.getString("NODE"),json_obj.getString("IFDESC"),
                                            json_obj.getString("ADDRESS"),json_obj.getString("TIME")));
                                }
                                listItem.setVisibility(View.VISIBLE);
                                mAdapter = new AdapterCMCurrent(getContext(),mList);
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
                    Volley.newRequestQueue(getContext()).add(request);
                    request.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                }
            }
        });
        btn_clear_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                progBar.setVisibility(View.VISIBLE);
                listItem.setVisibility(View.GONE);
                String mac  = String.valueOf(edt_mac_current.getText()).trim();
                try {
                    final JSONObject json_req = new JSONObject( );
                    try {
                        JSONObject json_data_item_key = new JSONObject( );
                        json_data_item_key.put("mac",mac);
                        JSONArray json_arr_item = new JSONArray( );
                        json_arr_item.put(json_data_item_key);
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act", "clear");
                        json_data_item.put("item",json_arr_item);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_req.put("user_name", "htvt");
                        json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_req.put("action", "current_cable");
                        json_req.put("donvi", donVi);
                        json_req.put("data", json_data);
                    }
                    catch (JSONException err) {
                        alert_display("Cảnh báo", "Không thể lấy thông tin ");
                    }
                    Log.d("JSON_mac_",">>>"+json_req);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json_req, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getContext(),"Đang clear Modem...",Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    Volley.newRequestQueue(getContext()).add(request);
                    request.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin");
                }
            }
        });
        return view;
    }
    private void initView(){
        listItem = view.findViewById(R.id.list_item);
        edt_mac_current =view.findViewById(R.id.edt_mac_current);
        edt_mac_current.setImeOptions(EditorInfo.IME_ACTION_DONE);
        progBar = view.findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
        btn_clear_mac = view.findViewById(R.id.btn_clear_mac);
        btn_refresh_mac = view.findViewById(R.id.btn_refesh_mac);
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
