package com.example.monitordocsis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class docsis_mb extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String donVi;
    private String area;
    private String usercode;
    private String username;
    private String sysdocsic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUser user = (permissionUser) getApplication().getApplicationContext();
        donVi = user.getUnit();
        usercode =user.getEmail();
        username =user.getUsername();
        sysdocsic=user.getSystemDocsis();
        area =user.getArea();
        if(usercode.isEmpty()){
            Intent intent = new Intent(docsis_mb.this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_docsis_mb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem user_name = menu.findItem(R.id.id_user);
        user_name.setTitle(username);
        MenuItem user_branch = menu.findItem(R.id.id_branch_user);
        user_branch.setTitle(donVi);
        // Hiển thị thanh công cụ theo tài khoản.
        if(area.contains("1")&&(!donVi.contains("TTVT") && !donVi.contains("CALLCENTER")) ){
            MenuItem menu_mb = menu.findItem(R.id.id_menu_mb).setVisible(true);
            MenuItem menu_mn = menu.findItem(R.id.id_menu_mn).setVisible(false);
            System.out.println("Chi nhánh miền bắc");
        }else if(area.contains("3")){
            MenuItem menu_mb = menu.findItem(R.id.id_menu_mb).setVisible(false);
            MenuItem menu_mn = menu.findItem(R.id.id_menu_mn).setVisible(true);
            System.out.println("Chi nhánh miền nam");
        }else if(area.contains("1")&& (donVi.contains("TTVT") || donVi.contains("CALLCENTER")) ){
            MenuItem menu_mb = menu.findItem(R.id.id_menu_mb).setVisible(true);
            MenuItem menu_mn = menu.findItem(R.id.id_menu_mn).setVisible(true);
            System.out.println("Trung tâm HTVT và DVKH");
        }
        final MenuItem phone = menu.findItem(R.id.id_phone);
        phone.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","0869583341",null));
                startActivity(intent);
                return true;
            }
        });
        MenuItem signout = menu.findItem(R.id.nav_signOut);
        signout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(docsis_mb.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });
        MenuItem mail = menu.findItem(R.id.nav_email_support);
        mail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vanluan2@vtvcab.vn"});
                i.putExtra(Intent.EXTRA_CC ,new String[]{"dungha@vtvcab.vn"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi ứng dụng Mcab");
                i.putExtra(Intent.EXTRA_TEXT   , "body of mail");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    alert_display("Cảnh báo", "Lỗi trong quá trình gửi mail");
                }
                return true;
            }
        });
        MenuItem home = menu.findItem(R.id.id_home);
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(docsis_mb.this,DashboardActivity.class);
                startActivity(intent);
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_node_mb, R.id.nav_cm_mb,R.id.nav_node_mn,R.id.nav_cm_mn, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation_docsis_mb);
        if(area.contains("1")&&(!donVi.contains("TTVT") && !donVi.contains("CALLCENTER")) ){
            System.out.println("Chi nhánh miền bắc");
            navGraph.setStartDestination(R.id.nav_node_mb);
        }else if(area.contains("3")){
            navGraph.setStartDestination(R.id.nav_node_mn);
            System.out.println("Chi nhánh miền nam");

        }else if(area.contains("1")&& (donVi.contains("TTVT") || donVi.contains("CALLCENTER")) ){
            navGraph.setStartDestination(R.id.nav_node_mb);
            System.out.println("Trung tâm HTVT và DVKH");
        }
        navController.setGraph(navGraph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.docsis_mb, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void alert_display(String title, String content) {
        new AlertDialog.Builder(docsis_mb.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("Đồng ý", null)
                .show( );
    }

    @Override
    public void onBackPressed() {
        System.out.println("Nút back fragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }else {
            finish();
        }

    }
}
