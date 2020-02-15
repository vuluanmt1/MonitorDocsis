package com.example.monitordocsis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class UpstreamHisActivity extends AppCompatActivity {
    private JSONArray json_arr_result;
    private JSONObject json_obj;
    private upstreamHisAdapter adapter;
    private ArrayList<upstreamHisModel>mList;
    private RecyclerView recView;
    private ProgressBar progBar;
    public static final String URL_HIS_UPS = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/docsis/api_load_upstream_his.php";
    TextView txt_start_date,txt_start_time, txt_end_date, txt_end_time;
    EditText edt_min_fec,edt_max_fec,edt_min_unfec, edt_max_unfec, edt_min_snr, edt_max_snr,edt_min_mer,edt_max_mer,edt_min_tx,edt_max_tx,edt_min_rx, edt_max_rx;
    TextView txt_search,txt_node_his, txt_time_title;
    private LinearLayout layoutSearch;
    private Toolbar toolbar;
    private Switch swt_warming;
    private Button btn_search;
    private Boolean chekcErrs = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upstream_his);
        initView();
        loadDate ();
        loadTime();
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        final String cmtsid = bundle.getString("cmtsid");
        final String ifindex = bundle.getString("ifindex");
        String node_name  =bundle.getString("nodename");
        txt_node_his.setText(node_name);
        try {
            final JSONObject json_req = new JSONObject( );
            try {
                JSONObject json_data_item_key = new JSONObject( );
                json_data_item_key.put("cmtsid",cmtsid);
                json_data_item_key.put("ifindex",ifindex);
                JSONArray json_arr_item = new JSONArray( );
                json_arr_item.put(json_data_item_key);
                JSONObject json_data_item = new JSONObject( );
                json_data_item.put("act", "load");
                json_data_item.put("item",json_arr_item);
                JSONArray json_data = new JSONArray( );
                json_data.put(json_data_item);
                json_req.put("user_name", "htvt");
                json_req.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                json_req.put("action", "his_upstream");
                json_req.put("data", json_data);
            }
            catch (JSONException err) {
                alert_display("Cảnh báo", "Không thể lấy thông tin Data truyền vào API!\n1. " + err.getMessage( ));
            }
            Log.d("JSon:",">>>"+json_req);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_HIS_UPS, json_req, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        json_arr_result = response.getJSONArray("data");
                        mList = new ArrayList<upstreamHisModel>();
                        for(int i=0; i < json_arr_result.length(); i++){
                            json_obj =json_arr_result.getJSONObject(i);
                            mList.add(new upstreamHisModel(json_obj.getString("SNR"),
                                    json_obj.getString("MER"),json_obj.getString("FEC"),
                                    json_obj.getString("FECUN"), json_obj.getString("TXPOWER"),
                                    json_obj.getString("DSPOWER"),json_obj.getString("ACT"),json_obj.getString("TOTAL"),
                                    json_obj.getString("MICREF"),json_obj.getString("FRE"),
                                    json_obj.getString("WTH"),json_obj.getString("CREATEDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                        }
                        adapter = new upstreamHisAdapter(mList,UpstreamHisActivity.this);
                        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(UpstreamHisActivity.this);
                        recView.setAdapter(adapter);
                        recView.setLayoutManager(linearLayoutManager);
                        recView.setHasFixedSize(true);
                        progBar.setVisibility(View.GONE);
                    }catch (Exception ex){
                        alert_display("Cảnh báo", "Không thể lấy thông tin từ JsonObjectRequest!\n1. " + ex.getMessage( ));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n1. " + error.getMessage( ));
                }
            });
            RequestQueue queue = Volley.newRequestQueue(UpstreamHisActivity.this);
            queue.add(jsonObjectRequest);
        }catch (Exception erros){
            alert_display("Cảnh báo", "Không thể lấy thông tin jsonObjectRequest!\n1. " + erros.getMessage( ));
        }
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
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateStart();
            }
        });
        txt_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerTimeStart();
            }
        });
        txt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDateEnd();
            }
        });
        txt_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerTimeEnd();
            }
        });
        swt_warming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chekcErrs =true;
                }else{
                    chekcErrs =false;
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutSearch.getVisibility()==View.VISIBLE){
                    layoutSearch.setVisibility(View.GONE);
                }else{
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                try {
                    String start_date= String.valueOf(txt_start_date.getText());
                    String start_time = String.valueOf(txt_start_time.getText());
                    String end_date = String.valueOf(txt_end_date.getText());
                    String end_time =String.valueOf(txt_end_time.getText());
                    String min_fec = String.valueOf(edt_min_fec.getText());
                    String max_fec = String.valueOf(edt_max_fec.getText());
                    String min_unfec =String.valueOf(edt_min_unfec.getText());
                    String max_unfec = String.valueOf(edt_max_unfec.getText());
                    String min_snr = String.valueOf(edt_min_snr.getText());
                    String max_snr = String.valueOf(edt_max_snr.getText());
                    String min_mer = String.valueOf(edt_min_mer.getText());
                    String max_mer = String.valueOf(edt_max_mer.getText());
                    String min_tx = String.valueOf(edt_min_tx.getText());
                    String max_tx = String.valueOf(edt_max_tx.getText());
                    String min_rx = String.valueOf(edt_min_rx.getText());
                    String max_rx = String.valueOf(edt_max_rx.getText());
                    final JSONObject json_api_his = new JSONObject();
                    try {
                        JSONObject json_data_item_search = new JSONObject( );
                        json_data_item_search.put("cmtsid",cmtsid);
                        json_data_item_search.put("ifindex",ifindex);
                        json_data_item_search.put("start_date",start_date);
                        json_data_item_search.put("start_time",start_time);
                        json_data_item_search.put("end_date",end_date);
                        json_data_item_search.put("end_time",end_time);
                        json_data_item_search.put("canhbao",chekcErrs);
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
                        JSONArray arr_item = new JSONArray();
                        arr_item.put(json_data_item_search);
                        JSONObject json_data_item = new JSONObject( );
                        json_data_item.put("act","search");
                        json_data_item.put("item",arr_item);
                        JSONArray json_data = new JSONArray( );
                        json_data.put(json_data_item);
                        json_api_his.put("user_name", "htvt");
                        json_api_his.put("token", "/KnJJ83aii24q2VZmbVMDCDceEzvPvrHPP4jPY2+Qfc=");
                        json_api_his.put("action", "his_upstream");
                        json_api_his.put("data", json_data);
                    }catch (Exception ers){
                        alert_display("Cảnh báo", "Không thể lấy thông tin từ Post Data!\n1. " + ers.getMessage( ));
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_HIS_UPS, json_api_his, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                json_arr_result = response.getJSONArray("data");
                                mList = new ArrayList<upstreamHisModel>();
                                for(int i=0; i < json_arr_result.length(); i++){
                                    json_obj =json_arr_result.getJSONObject(i);
                                    mList.add(new upstreamHisModel(json_obj.getString("SNR"),
                                            json_obj.getString("MER"),json_obj.getString("FEC"),
                                            json_obj.getString("FECUN"), json_obj.getString("TXPOWER"),
                                            json_obj.getString("DSPOWER"),json_obj.getString("ACT"),json_obj.getString("TOTAL"),
                                            json_obj.getString("MICREF"),json_obj.getString("FRE"),
                                            json_obj.getString("WTH"),json_obj.getString("CREATEDATE"),json_obj.getString("CMTSID"),json_obj.getString("IFINDEX")));
                                }
                                adapter = new upstreamHisAdapter(mList,UpstreamHisActivity.this);
                                Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(UpstreamHisActivity.this);
                                recView.setAdapter(adapter);
                                recView.setLayoutManager(linearLayoutManager);
                                recView.setHasFixedSize(true);
                                progBar.setVisibility(View.GONE);
                            }catch (Exception ers){
                                alert_display("Cảnh báo", "Không thể lấy thông tin từ JsonObjectRequest!\n1. " + ers.getMessage( ));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alert_display("Cảnh báo", "Không thể lấy thông tin onErrorResponse!\n1. " + error.getMessage( ));
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(UpstreamHisActivity.this);
                    queue.add(jsonObjectRequest);

                }catch (Exception ers){
                    alert_display("Cảnh báo", "Không thể lấy thông tin jsonObjectRequest!\n1. " + ers.getMessage( ));
                }
            }
        });
        // Sắp xếp theo cột thời gian .
        txt_time_title.setOnClickListener(new View.OnClickListener() {
            boolean check =false;
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(check == true){
                    sortListReduction();
                    check=false;
                }else{
                    sortListIncrease();
                    check=true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void pickerDateStart (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txt_start_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void pickerDateEnd (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txt_end_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    private void loadDate (){
        final Calendar calendar = Calendar.getInstance();
        int ngay  = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        calendar.set(nam,thang,ngay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txt_start_date.setText(simpleDateFormat.format(calendar.getTime()));
        txt_end_date.setText(simpleDateFormat.format(calendar.getTime()));
    }
    private void loadTime(){
        final  Calendar calendar = Calendar.getInstance();
        final  Calendar calendar1 =Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
            calendar.set(0,0,0,gio,phut);
            calendar1.set(0,0,0,0,00);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                txt_end_time.setText(simpleDateFormat.format(calendar.getTime()));
                txt_start_time.setText(simpleDateFormat.format(calendar1.getTime()));
    }
    private void pickerTimeStart(){
        final  Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,hourOfDay,minute);
                txt_start_time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio,phut,true);
        timePickerDialog.show();
    }
    private void pickerTimeEnd(){
        final  Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,hourOfDay,minute);
                txt_end_time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },gio, phut,true);
        timePickerDialog.show();
    }
    private void initView(){
        txt_time_title = findViewById(R.id.txt_time);
        txt_start_date = findViewById(R.id.txt_start_date);
        txt_start_time = findViewById(R.id.txt_start_time);
        txt_end_date =findViewById(R.id.txt_end_date);
        txt_end_time =findViewById(R.id.txt_end_time);
        txt_search = findViewById(R.id.txt_search);
        layoutSearch =findViewById(R.id.layout_search);
        layoutSearch.setVisibility(View.GONE);
        toolbar =findViewById(R.id.toolbar);
        swt_warming =(Switch)findViewById(R.id.swt_warning);
        recView = findViewById(R.id.recView);
        progBar = findViewById(R.id.progBar);
        txt_node_his = findViewById(R.id.txt_node_his);
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
        btn_search =findViewById(R.id.btn_search);
        txt_start_date.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_start_time.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_end_date.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_end_time.setImeOptions(EditorInfo.IME_ACTION_DONE);
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
    private void sortListIncrease (){
        Collections.sort(mList, new Comparator<upstreamHisModel>() {
            @Override
            public int compare(upstreamHisModel o1, upstreamHisModel o2) {
                return o1.getModifileDate().compareTo(o2.getModifileDate());
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void sortListReduction (){
        Collections.sort(mList, new Comparator<upstreamHisModel>() {
            @Override
            public int compare(upstreamHisModel o1, upstreamHisModel o2) {
                return o2.getModifileDate().compareTo(o1.getModifileDate());
            }
        });
        adapter.notifyDataSetChanged();
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(UpstreamHisActivity.this)
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
}
