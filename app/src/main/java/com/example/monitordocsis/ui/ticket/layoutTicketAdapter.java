package com.example.monitordocsis.ui.ticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class layoutTicketAdapter extends FragmentStatePagerAdapter {
    private String []tab ={"Lịch sử Port","Danh sách ONU"};
    private FragmentTicketOnu mFrgTicketONU;
    private FragmentTicketPort mFrgTicketPort;
    private Bundle bundle;

    public layoutTicketAdapter(@NonNull FragmentManager fm,Bundle bundle) {
        super(fm);
        mFrgTicketONU = new FragmentTicketOnu();
        mFrgTicketPort = new FragmentTicketPort();
        this.bundle =bundle;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            mFrgTicketPort.setArguments(bundle);
            return mFrgTicketPort;
        }else if(position==1){
            mFrgTicketONU.setArguments(bundle);
            return mFrgTicketONU;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}
