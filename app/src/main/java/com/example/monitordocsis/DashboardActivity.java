package com.example.monitordocsis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
            private ImageView btn_docsis;
            private ImageView btn_map_docsis;
            private ImageView btn_map_optical;
            private ImageView btn_gpon;
            private String system_gpon;
            private String system_docsis;
            private String usercode ;
            private String usergroup;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btn_docsis = findViewById(R.id.btn_docsis);
        btn_map_docsis = findViewById(R.id.btn_map_docsis);
        btn_gpon =findViewById(R.id.btn_gpon);
        btn_map_optical = findViewById(R.id.btn_map_optical);
        permissionUser permission = (permissionUser) getApplicationContext();
        usercode = permission.getEmail();
        usergroup = permission.getUserGroup();
        System.out.println("usercode:"+usercode);
        if(usercode == null){
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
        }
        system_gpon = permission.getSystemGpon();
        system_docsis =permission.getSystemDocsis();
        if(system_gpon.isEmpty()){
            btn_gpon.setVisibility(View.GONE);
        }
        if(system_docsis.isEmpty()){
            btn_docsis.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        btn_docsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.animator_button(view);
                if(!usergroup.contains("PHANMEM")){
                    Intent intent = new Intent(DashboardActivity.this, docsis_mb.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DashboardActivity.this, docsis_mb.class);
                    startActivity(intent);
                }
            }
        });
        btn_gpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                Intent intent = new Intent(DashboardActivity.this, gpon.class);
                startActivity(intent);
            }
        });
        btn_map_docsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(!usergroup.contains("PHANMEM")){
                    alert_display("Thông báo", "Hệ thống đang phát triển");
                }else{

                }
            }
        });
        btn_map_optical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.animator_button(v);
                if(!usergroup.contains("PHANMEM")){
                    Global.animator_button(v);
                    alert_display("Thông báo", "Hệ thống đang phát triển");
                }else{

                }
            }
        });
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(DashboardActivity.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }
}
