package com.example.monitordocsis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private EditText txt_username;
    private EditText txt_password;
    private TextView txt_welcome;
    private CheckBox cb_remember_login;
    private Button btn_login;
    private Button btn_logout;
    private Boolean login;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
//    public static final String URL_BASE = "http://10.103.25.62/dev/generalmanagementsystem/api/android/index.php";
    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/index.php";
    SharedPreferences sharedPreferences;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_username =findViewById(R.id.txt_email);
        txt_password =findViewById(R.id.txt_password);
        txt_welcome = findViewById(R.id.txt_welcome);
        btn_login =findViewById(R.id.btn_login);
        cb_remember_login = findViewById(R.id.cb_remember);
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        login = sharedPreferences.getBoolean("login", false);
        txt_username.setText(sharedPreferences.getString("user_name",""));
        txt_password.setText(sharedPreferences.getString("password",""));
        cb_remember_login.setChecked(sharedPreferences.getBoolean("checked",false));
    }
    @Override
    protected void onStart() {
        super.onStart();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.animator_button(view);
                    try {
                        final String user_name = String.valueOf(txt_username.getText( ));
                        final String password = String.valueOf(txt_password.getText( ));
                        //Nạp thông tin đường dẫn thông qua volley
                        //Tạo Json
                        JSONObject json_req = new JSONObject( );
                        try {
                            JSONObject json_data_item = new JSONObject( );
                            json_data_item.put("user_name", user_name);
                            json_data_item.put("password", password);
                            JSONArray json_data = new JSONArray( );
                            json_data.put(json_data_item);
                            json_req.put("user_name", "htvt");
                            json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                            json_req.put("action", "login");
                            json_req.put("data", json_data);
                        }
                        catch (JSONException err) {
                            alert_display("Cảnh báo", "Không thể lấy thông tin!\n1. " + err.getMessage( ));
                        }
                        Log.d("json_req",">>"+json_req);
                        //Truyền thông tin qua volley và nhận về kết quả
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, URL_BASE, json_req, new Response.Listener<JSONObject>( ) {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("response",">>"+response);
                                        try {
                                            if (Integer.valueOf(response.getString("code")) == 0) {
                                                SharedPreferences.Editor editor = sharedPreferences.edit( );
                                                json_arr_result = response.getJSONArray("data");
                                                for(int i=0; i < json_arr_result.length(); i++){
                                                    json_obj =json_arr_result.getJSONObject(i);
                                                    permissionUser permission = (permissionUser) getApplicationContext();
                                                    permission.setUserGroup(json_obj.getString("USERGROUP"));
                                                    permission.setUnit(json_obj.getString("UNIT"));
                                                    permission.setPosition(json_obj.getString("POSITION"));
                                                    permission.setUsername(json_obj.getString("USERNAME"));
                                                    permission.setPrivilege(json_obj.getString("PRIVILEGE"));
                                                    permission.setArea(json_obj.getString("AREA"));
                                                    permission.setBranch(json_obj.getString("BRANCH"));
                                                }
                                                if(cb_remember_login.isChecked()){
                                                    editor.putBoolean("login", true);
                                                    editor.putString("user_name", user_name);
                                                    editor.putString("password",password);
                                                    editor.putBoolean("checked",true);
                                                }else{
                                                    editor.remove("user_name");
                                                    editor.remove("password");
                                                    editor.remove("checked");
                                                }
                                                if (editor.commit( ) == true) {
                                                    afterLogin( );
                                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    alert_display("Thông báo", "Có lỗi xảy ra!\nKhông thể thực thi lệnh!");
                                                }
                                            }
                                            else {
                                                alert_display("Thông báo", "Thông tin đăng nhập không chính xác!");
                                            }
                                        }
                                        catch (JSONException err) {
                                            alert_display("Cảnh báo", "Không thể lấy thông tin!\n" + err.getMessage( ));
                                        }
                                    }
                                }, new Response.ErrorListener( ) {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        alert_display("Cảnh báo", "Không thể lấy thông tin!\n" + error.getMessage( ));
                                    }
                                });
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(jsonObjectRequest);
                    }
                    catch (Exception err) {
                        alert_display("Cảnh báo", "Lỗi: " + err.getMessage( ));
                    }
            }
        });
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    private void afterLogin() {
        txt_username.setVisibility(View.GONE);
        txt_password.setVisibility(View.GONE);
        txt_welcome.setVisibility(View.VISIBLE);
    }

    private void afterLogout() {
        txt_username.setVisibility(View.VISIBLE);
        txt_password.setVisibility(View.VISIBLE);
        txt_welcome.setVisibility(View.GONE);
    }

}
