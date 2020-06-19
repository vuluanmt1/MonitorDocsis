package com.example.monitordocsis.mapping_docsis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monitordocsis.R;
import com.example.monitordocsis.permissionUser;

public class FragmentCapnhatCM extends Fragment {
    private WebView webView;
    private String donVi;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mapping_capnhat_cm, container, false);
        permissionUser user = (permissionUser) getContext().getApplicationContext();
        donVi = user.getUnit();
        webView =root.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://noc.vtvcab.vn:8182/mapping/hethong/mobi/mapping_docsis_cm.php?unit="+donVi);
        return root;
    }
}
