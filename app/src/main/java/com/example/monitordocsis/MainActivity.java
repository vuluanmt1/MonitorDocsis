package com.example.monitordocsis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText txt_username;
    private EditText txt_password;
    private CheckBox cb_remember_login;
    private ImageView btn_login;
    private Button btn_logout;
    private Boolean login;
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/index.php";
    private SharedPreferences sharedPreferences;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private FakeAppUpdateManager fakeAppUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private CoordinatorLayout drawerLayout;

    //    SharedPreferences sharedPreferences;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForAppUpdate();
        txt_username =findViewById(R.id.txt_email);
        txt_password =findViewById(R.id.txt_password);
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
                            alert_display("Cảnh báo", "Không thể lấy thông tin");
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
                                                    permission.setSystemGpon(json_obj.getString("SYSTEM_G"));
                                                    permission.setSystemDocsis(json_obj.getString("SYSTEM_DOCSIS"));
                                                    permission.setEmail(json_obj.getString("MAIL"));
                                                    permission.setVersion(json_obj.getString("VERSION"));
                                                    permission.setVersion_docsis_mb(json_obj.getString("VERSION_DOCSIS_MB"));
                                                    permission.setVersion_docsis_mn(json_obj.getString("VERSION_DOCSIS_MN"));
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
                                            alert_display("Cảnh báo", "Không thể lấy thông tin");
                                        }
                                    }
                                }, new Response.ErrorListener( ) {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        alert_display("Cảnh báo", "Không thể lấy thông tin");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_VERSION_UPDATE:
                if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                    Log.d("Update flow failed!",">>" + resultCode);
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    unregisterInstallStateUpdListener();
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    private void checkForAppUpdate(){
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);
        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = fakeAppUpdateManager.getAppUpdateInfo();
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED){
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
                }
            }
        };
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo ->{
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }
        });
    }
    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MainActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MainActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });

    }
    private void popupSnackbarForCompleteUpdateAndUnregister(){
        Snackbar snackbar =
                Snackbar.make(findViewById(android.R.id.content), "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Restart", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
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
    }

    private void afterLogout() {
        txt_username.setVisibility(View.VISIBLE);
        txt_password.setVisibility(View.VISIBLE);
    }
    private boolean web_update(){
        try {
            int curVersion  = BuildConfig.VERSION_CODE;
            System.out.println("curVersion:"+curVersion);
            int newVersion = curVersion;
            String package_name ="htvt.vtvcab.monitorsystem";
            newVersion = Integer.parseInt(Jsoup.connect("https://play.google.com/store/apps/details?id=" + package_name + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText());
            return (value(curVersion) < value(newVersion)) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private long value(int string) {
        return Long.valueOf( string );
    }
    public class VersionChecker extends AsyncTask<String,String,String>{
        private String newVersion;
        @Override
        protected String doInBackground(String... strings) {
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
    }

}
