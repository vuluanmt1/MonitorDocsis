package com.example.monitordocsis;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class UpstreamchannelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private JSONArray json_arr_branch;
    private JSONArray json_arr_cmts;
    private Boolean login;
    private Spinner spinner_branch,spinner_cmts;
//    public static final String URL_BASE = "http://10.103.25.62/dev/generalmanagementsystem/api/android/docsis/api_loaddata.php";
    public static final String URL_BASE = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/docsis/api_loaddata.php";
    public static final String URL_SEARCH ="http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/docsis/api_loadbranch.php";
    SharedPreferences sharedpreferences;
    private  Bundle extras;
    private String module;
    private RecyclerView recView;
    private ProgressBar progBar;
    private upstreamchannelAdapter upsAdapter;
    private branchAdapter branchAdapter;
    private cmtsAdapter cmtsAdapter;
    private ArrayList<upstreamchannelModel> upsModel;
    private ArrayList<branchModel>arrBranchModel;
    private ArrayList<cmtsModel>arrCmtsModel;

    private ArrayList<upstreamchannelModel> upsList;
    private LinearLayout layoutSearch;
    private ScrollView scrollView;
    private Switch swt_warming;
    private EditText edit_search_node;
    private EditText edt_min_fec,edt_max_fec,edt_min_unfec,edt_max_unfec,edt_min_snr,edt_max_snr,edt_min_mer,edt_max_mer,edt_min_tx,edt_max_tx,edt_min_rx,edt_max_rx;
    private Toolbar toolbar;
    private Button btn_search;
    private String cmstID;
    private String branchID;
    private Boolean checkErrors= false;
    private ImageButton imgBtn_ls, imgBtn_tt;
    private TextView txt_node_title,txt_interface_title,txt_branch_title, txt_snr_title,txt_mer_title,txt_fec_title,
                     txt_unfec_title, txt_tx_title,txt_rx_title,txt_act_title,txt_total_title;
    private String donVi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upstreamchannel);
        bindViews();
        permissionUser permission = (permissionUser) getApplicationContext();
        donVi =permission.getUnit();
        toolbar =findViewById(R.id.toolbar);
        spinner_branch = (Spinner) findViewById(R.id.cb_branch);
        spinner_cmts = (Spinner) findViewById(R.id.cb_cmts);
        swt_warming =(Switch)findViewById(R.id.swt_warning);
        setSupportActionBar(toolbar);
        recView = findViewById(R.id.recView);
        progBar = findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
        layoutSearch =findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.GONE);
        btn_search =findViewById(R.id.btn_search);
        //Load branch and cmts on tab Search.
        loadBranch(URL_SEARCH);
        loadCMTS(URL_SEARCH);
        TextView txt_search = (TextView) findViewById(R.id.txt_search);
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.animator_button(view);
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        spinner_cmts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmtsModel cmts = cmtsAdapter.getItem(position);
                cmstID =cmts.getCmtsID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchModel branch = branchAdapter.getItem(position);
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
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
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
                        alert_display("Cảnh báo", "Không thể lấy thông tin từ Post Data!\n1. " + e.getMessage( ));
                    }
                    Log.d("JSon:",">>>"+json_api);
                    JsonObjectRequest jsonObject_search  = new JsonObjectRequest(Request.Method.POST, URL_BASE, json_api, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                upsModel = new ArrayList<upstreamchannelModel>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    upsModel.add(new upstreamchannelModel(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                            json_obj.getString("MERCHANTCODE"),json_obj.getString("IFSIGQSNR"),
                                            json_obj.getString("AVGONLINECMDSSNR"),json_obj.getString("FECCORRECTED"),
                                            json_obj.getString("FECUNCORRECTABLE"), json_obj.getString("AVGONLINECMUSPOWER"),
                                            json_obj.getString("AVGONLINECMDSPOWER"),json_obj.getString("UPCHANNELCMACTIVE"),json_obj.getString("UPCHANNELCMTOTAL"),
                                            json_obj.getString("AVGONLINECMMICREF"),json_obj.getString("UPCHANNELFREQUENCY"),
                                            json_obj.getString("UPCHANNELWIDTH"),json_obj.getString("TITLE"),json_obj.getString("MODIFIEDDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }

                                upsAdapter = new upstreamchannelAdapter(upsModel,UpstreamchannelActivity.this);
                                Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(UpstreamchannelActivity.this);
                                recView.setAdapter(upsAdapter);
                                recView.setLayoutManager(linearLayoutManager);
                                recView.setHasFixedSize(true);
                                progBar.setVisibility(View.GONE);

                            }catch (Exception er){
                                alert_display("Cảnh báo", "Không thể lấy thông tin từ JsonObjectRequest!\n1. " + er.getMessage( ));
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alert_display("Cảnh báo", "Không thể lấy thông tin từ onErrorResponse!\n1. " + error.getMessage( ));
                            Log.e("Err",error.getMessage());
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(UpstreamchannelActivity.this);
                    queue.add(jsonObject_search);
                }catch (Exception err){
                    alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
                }
            }
        });
        txt_node_title.setOnClickListener(new View.OnClickListener() {
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
        txt_interface_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionInteface();
                    check=false;
                }else{
                    sortListIncreaseInteface();
                    check=true;
                }
            }
        });
        txt_branch_title.setOnClickListener(new View.OnClickListener() {
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
        txt_snr_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionSnr();
                    check=false;
                }else{
                    sortListIncreaseSnr();
                    check=true;
                }
            }
        });
        txt_mer_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionMer();
                    check=false;
                }else{
                    sortListIncreaseMer();
                    check=true;
                }
            }
        });
        txt_fec_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionFec();
                    check=false;
                }else{
                    sortListIncreaseFec();
                    check=true;
                }
            }
        });
        txt_unfec_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionUnFec();
                    check=false;
                }else{
                    sortListIncreaseUnFec();
                    check=true;
                }
            }
        });
        txt_tx_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionTx();
                    check=false;
                }else{
                    sortListIncreaseTx();
                    check=true;
                }
            }
        });
        txt_rx_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReductionRx();
                    check=false;
                }else{
                    sortListIncreaseRx();
                    check=true;
                }
            }
        });
        txt_act_title.setOnClickListener(new View.OnClickListener() {
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
        txt_total_title.setOnClickListener(new View.OnClickListener() {
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
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
                upsAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
    private void bindViews() {
        edit_search_node =findViewById(R.id.edt_search_node);
        edit_search_node.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_min_fec =findViewById(R.id.edt_min_fec);
        edt_max_fec =findViewById(R.id.edt_max_fec);
        edt_min_unfec =findViewById(R.id.edt_min_Unfec);
        edt_max_unfec =findViewById(R.id.edt_max_Unfec);
        edt_min_snr =findViewById(R.id.edt_min_Snr);
        edt_max_snr =findViewById(R.id.edt_max_Snr);
        edt_min_mer =findViewById(R.id.edt_min_Mer);
        edt_max_mer =findViewById(R.id.edt_max_Mer);
        edt_min_tx =findViewById(R.id.edt_min_Tx);
        edt_max_tx =findViewById(R.id.edt_max_Tx);
        edt_min_rx =findViewById(R.id.edt_min_Rx);
        edt_max_rx =findViewById(R.id.edt_max_Rx);
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
        imgBtn_ls = findViewById(R.id.btn_ls);
        imgBtn_tt =findViewById(R.id.btn_tt);
        txt_node_title = findViewById(R.id.txt_node);
        txt_interface_title = findViewById(R.id.txt_inteface);
        txt_branch_title = findViewById(R.id.txt_branch);
        txt_snr_title = findViewById(R.id.txt_snr);
        txt_mer_title = findViewById(R.id.txt_mer);
        txt_fec_title = findViewById(R.id.txt_fec);
        txt_unfec_title = findViewById(R.id.txt_unfec);
        txt_tx_title = findViewById(R.id.txt_txpw);
        txt_rx_title = findViewById(R.id.txt_rxpw);
        txt_act_title = findViewById(R.id.txt_act);
        txt_total_title = findViewById(R.id.txt_total);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
                }
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_BASE, json_req, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    json_arr_result = response.getJSONArray("data");
                                    try {
                                        upsModel = new ArrayList<upstreamchannelModel>();
                                        for(int i=0; i < json_arr_result.length(); i++){
                                            json_obj =json_arr_result.getJSONObject(i);
                                            upsModel.add(new upstreamchannelModel(json_obj.getString("IFALIAS"),json_obj.getString("IFDESC"),
                                                    json_obj.getString("MERCHANTCODE"),json_obj.getString("IFSIGQSNR"),
                                                    json_obj.getString("AVGONLINECMDSSNR"),json_obj.getString("FECCORRECTED"),
                                                    json_obj.getString("FECUNCORRECTABLE"), json_obj.getString("AVGONLINECMUSPOWER"),
                                                    json_obj.getString("AVGONLINECMDSPOWER"),json_obj.getString("UPCHANNELCMACTIVE"),json_obj.getString("UPCHANNELCMTOTAL"),
                                                    json_obj.getString("AVGONLINECMMICREF"),json_obj.getString("UPCHANNELFREQUENCY"),
                                                    json_obj.getString("UPCHANNELWIDTH"),json_obj.getString("TITLE"),json_obj.getString("MODIFIEDDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                        }
                                        upsAdapter = new upstreamchannelAdapter(upsModel,UpstreamchannelActivity.this);
                                        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                                        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(UpstreamchannelActivity.this);
                                        recView.setAdapter(upsAdapter);
                                        recView.setLayoutManager(linearLayoutManager);
                                        recView.setHasFixedSize(true);
                                        progBar.setVisibility(View.GONE);
                                    }catch (Exception er){
                                        alert_display("Cảnh báo", "Không thể lấy thông tin 2!\n"+ er.getMessage( ));
                                    }
                                }catch (Exception er){
                                    alert_display("Cảnh báo", "Không thể lấy thông tin 3!\n"+ er.getMessage( ));
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alert_display("Cảnh báo", "Không thể lấy thông tin 4!\n"+ error.getMessage( ));
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(UpstreamchannelActivity.this);
                        queue.add(jsonObjectRequest);
            }
        }).start();
    }

    public void alert_display(String title, String content) {
        new AlertDialog.Builder(UpstreamchannelActivity.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
    void ListenRotate(){
        OrientationEventListener orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int i) {

            }
        };
    }
    // Mang cac ky tu goc co dau
    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự',};

    // Mang cac ky tu thay the khong dau
    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u',};

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }
    public void loadBranch(String url){
        final JSONObject json_search = new JSONObject( );
        try {
            json_search.put("user_name", "htvt");
            json_search.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
            json_search.put("donvi",donVi);
            json_search.put("action", "branch");
        }
        catch (JSONException err) {
            alert_display("Cảnh báo", "Không thể lấy thông tin 1!\n1. " + err.getMessage( ));
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_branch =response.getJSONArray("data");
                    arrBranchModel = new ArrayList<branchModel>();
                    if(donVi.equals("TTVT") || donVi.equals("CALLCENTER") ||donVi.equals("CN5")){
                        arrBranchModel.add(new branchModel("all","Tất Cả"));
                        for(int i=0; i < json_arr_branch.length(); i++){
                            json_obj =json_arr_branch.getJSONObject(i);
                            arrBranchModel.add(new branchModel(json_obj.getString("CODE_"),json_obj.getString("DESCRIPTION")));
                        }
                    }else{
                        for(int i=0; i < json_arr_branch.length(); i++){
                            json_obj =json_arr_branch.getJSONObject(i);
                            arrBranchModel.add(new branchModel(json_obj.getString("CODE_"),json_obj.getString("DESCRIPTION")));
                        }
                    }
                    branchAdapter = new branchAdapter(UpstreamchannelActivity.this,arrBranchModel);
                    spinner_branch.setAdapter(branchAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(UpstreamchannelActivity.this);
        queue.add(jsonObjectRequest);
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
        Log.d("json_search1",">>>"+json_search1);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_search1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    json_arr_cmts =response.getJSONArray("data");
                    arrCmtsModel = new ArrayList<cmtsModel>();
                    arrCmtsModel.add(new cmtsModel("all","Tất Cả"));
                    for(int i=0; i < json_arr_cmts.length(); i++){
                        json_obj =json_arr_cmts.getJSONObject(i);
                        arrCmtsModel.add(new cmtsModel(json_obj.getString("CMTSID"),json_obj.getString("TITLE")));
                    }
                    cmtsAdapter = new cmtsAdapter(UpstreamchannelActivity.this,arrCmtsModel);
                    spinner_cmts.setAdapter(cmtsAdapter);
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
        RequestQueue queue = Volley.newRequestQueue(UpstreamchannelActivity.this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String txt = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),txt,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String txt = parent.getSelectedItem().toString();

    }
    // Các hàm sắp xem theo Tiêu đề của bảng.
    private void sortListIncreaseNode (){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o1.getNode().compareTo(o2.getNode());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionNode (){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o2.getNode().compareTo(o1.getNode());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseInteface (){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o1.getInteface().compareTo(o2.getInteface());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionInteface (){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o2.getInteface().compareTo(o1.getInteface());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseBranch(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o1.getBranch().compareTo(o2.getBranch());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionBranch(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return o2.getBranch().compareTo(o1.getBranch());
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseSnr(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getSnr()).compareTo(Double.valueOf(o2.getSnr()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionSnr(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getSnr()).compareTo(Double.valueOf(o1.getSnr()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseMer(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getMer()).compareTo(Double.valueOf(o2.getMer()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionMer(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getMer()).compareTo(Double.valueOf(o1.getMer()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseFec(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getFec()).compareTo(Double.valueOf(o2.getFec()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionFec(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getFec()).compareTo(Double.valueOf(o1.getFec()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseUnFec(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getUnfec()).compareTo(Double.valueOf(o2.getUnfec()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionUnFec(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getUnfec()).compareTo(Double.valueOf(o1.getUnfec()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseTx(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getTxpower()).compareTo(Double.valueOf(o2.getTxpower()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTx(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getTxpower()).compareTo(Double.valueOf(o1.getTxpower()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseRx(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o1.getRxpower()).compareTo(Double.valueOf(o2.getRxpower()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionRx(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Double.valueOf(o2.getRxpower()).compareTo(Double.valueOf(o1.getRxpower()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseAct(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Integer.valueOf(o1.getAct()).compareTo(Integer.valueOf(o2.getAct()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionAct(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Integer.valueOf(o2.getAct()).compareTo(Integer.valueOf(o1.getAct()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListIncreaseTotal(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Integer.valueOf(o1.getTotal()).compareTo(Integer.valueOf(o2.getTotal()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
    private void sortListReductionTotal(){
        Collections.sort(upsModel, new Comparator<upstreamchannelModel>() {
            @Override
            public int compare(upstreamchannelModel o1, upstreamchannelModel o2) {
                return Integer.valueOf(o2.getTotal()).compareTo(Integer.valueOf(o1.getTotal()));
            }
        });
        upsAdapter.notifyDataSetChanged();
    }
}
