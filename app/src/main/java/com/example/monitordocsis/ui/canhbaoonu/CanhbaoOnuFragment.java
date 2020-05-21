package com.example.monitordocsis.ui.canhbaoonu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CanhbaoOnuFragment extends Fragment {
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private RecyclerView recView;
    private canhbaoonuAdapter listAdapter;
    private ArrayList<canhbaoonuModel>listModel;
    private ProgressBar progBar;
    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_canhbaoonu.php";
    private String donVi;
    private String branch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUser permi = (permissionUser) getActivity().getApplication();
        donVi  = permi.getUnit();
        branch = permi.getBranch();
        final JSONObject json_req = new JSONObject( );
        try {
            JSONObject json_data_item = new JSONObject( );
            json_data_item.put("act", "load");
            JSONArray json_data = new JSONArray( );
            json_data.put(json_data_item);
            json_req.put("user_name", "htvt");
            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_req.put("action", "canhbao");
            json_req.put("donvi",donVi);
            json_req.put("branch",branch);
            json_req.put("data", json_data);
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n"+ err.getMessage( ));
        }
        Log.d("json",">>>"+json_req);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_BASE, json_req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_result = response.getJSONArray("data");
                    try {
                        listModel = new ArrayList<canhbaoonuModel>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            listModel.add(new canhbaoonuModel(json_obj.getString("SNOLT"),json_obj.getString("SNONU"),
                                    json_obj.getString("PORTPON"),json_obj.getString("ONUID"),
                                    json_obj.getString("STATUS"),json_obj.getString("MODEREG"),
                                    json_obj.getString("PROFILEREG"), json_obj.getString("FIRMWARE"),
                                    json_obj.getString("RXPOWER"),json_obj.getString("DEACTIVE_REASON"),json_obj.getString("INACTIVE_TIME"),
                                    json_obj.getString("MODEL"),json_obj.getString("DISTANCE"),
                                    json_obj.getString("CREATEDATE")));
                        }
                        listAdapter.addData(listModel);
                    }catch (Exception err){
                        alert_display("Cảnh báo", "Không thể lấy thông tin !\n"+ err.getMessage( ));
                            Log.d("err",">>>>"+err);
                    }
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin !\n"+ err.getMessage( ));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n"+ error.getMessage( ));
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_canhbaoonu, container, false);
        recView = root.findViewById(R.id.recView);
        progBar = root.findViewById(R.id.progBar);
        listAdapter  = new canhbaoonuAdapter(getContext(),listModel);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(listAdapter);
        return root;
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }

}