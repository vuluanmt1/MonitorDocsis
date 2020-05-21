package com.example.monitordocsis.ui.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.monitordocsis.R;
import com.google.android.material.tabs.TabLayout;

public class layoutTabTicket extends Fragment {
    private ViewPager pager ;
    private String donVi;
    private TextView txt_titel;
    private Toolbar toolbar;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_tab_ticket,container,false);
        pager = view.findViewById(R.id.view_pager);
        TabLayout layout = view.findViewById(R.id.tabs);
//        layout.setupWithViewPager();
        return  view;

    }
}
