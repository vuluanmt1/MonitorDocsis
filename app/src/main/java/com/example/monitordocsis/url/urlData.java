package com.example.monitordocsis.url;

import android.app.Application;

import com.example.monitordocsis.permissionUser;

public class urlData  extends Application {

    public static final String url ="https://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/";
    public static final String url1 ="https://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/";
    public static final String url_docsis_mb ="https://noc.vtvcab.vn:8182/generalmanagementsystem/api/android/";
    permissionUser user = (permissionUser) getApplicationContext();
    String verison  = user.getVersion();
    public   final String URL_TELNET =url+verison+"/telnet/getTelnet.php";
    public final String URL_TELNET_ONU =url+verison+"/telnet/api_telnet_onu.php";
    public final String URL_LOAD_FW =url+verison+"/api_load_fw_onu.php";
    public  final String URL_LOAD_FW_OS =url+verison+"/telnet/api_load_fw_os.php";
    public  final String URL_UPGRADE_FW =url+verison+"/telnet/api_upgrade_fw.php";
    public  final String URL_BASE_AREA = url+verison+"/api_load_area.php";
    public  final String URL_BASE_OLT = url+verison+"/api_load_olt.php";
    public  final String URL_BASE = url+verison+"/api_load_onu.php";
    public  final String URL_FOLLOW_ONU = url+verison+"/api_follow_onu.php";
    public  final String URL_TICKET = url+verison+"/api_load_ticket.php";
}


