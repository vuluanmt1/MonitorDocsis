package com.example.monitordocsis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
            private ImageButton btn_docsis;
            private ImageButton btn_gpon;
            private ImageButton btn_docsis_maping;
            private ImageButton btn_gpon_maping;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btn_docsis = findViewById(R.id.btn_docsis);
        btn_gpon =findViewById(R.id.btn_gpon);
        btn_docsis_maping =findViewById(R.id.btn_docsis_maping);
        btn_gpon_maping =findViewById(R.id.btn_gpon_maping);
        permissionUser permission = (permissionUser) getApplicationContext();

        Log.d("Unit",">>"+permission.getUnit());
    }
    @Override
    protected void onStart() {
        super.onStart();
        btn_docsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, UpstreamchannelActivity.class);
                startActivity(intent);
            }
        });
        btn_gpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, gpon.class);
                startActivity(intent);


            }
        });
    }
}
