package com.example.monitordocsis.ui.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;
import com.google.android.material.tabs.TabLayout;

public class layout_ticket extends AppCompatActivity {
    public static final String URL_TICKET = "http://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/gpon/api_load_ticket.php";
    private ViewPager pager ;
    private String donVi;
    private TextView txt_titel;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUser user = (permissionUser) getApplication().getApplicationContext();
        donVi = user.getUnit();
        Intent intent = getIntent();
        Bundle bundle =intent.getBundleExtra("data");
        setContentView(R.layout.activity_layout_ticket);
        pager = findViewById(R.id.view_pager);
        pager.setAdapter(new layoutTicketAdapter(getSupportFragmentManager(),bundle));
        TabLayout layout = findViewById(R.id.tabs);
        layout.setupWithViewPager(pager);
        final String maolt = bundle.getString("maolt");
        final String port = bundle.getString("port");
        final String area = bundle.getString("area");
        FragmentTicketPort fragTicketPort = new FragmentTicketPort();
        FragmentTicketOnu fragTicketOnu = new FragmentTicketOnu();
        Bundle bd = new Bundle();
        bd.putString("maolt",maolt);
        bd.putString("port",port);
        bd.putString("area",area);
        fragTicketPort.setArguments(bd);
        fragTicketOnu.setArguments(bd);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin Ticket");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }
}