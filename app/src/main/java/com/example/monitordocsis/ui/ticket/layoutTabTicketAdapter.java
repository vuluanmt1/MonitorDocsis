package com.example.monitordocsis.ui.ticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class layoutTabTicketAdapter extends FragmentStatePagerAdapter {
    private String []tab ={"Ticket Theo Dõi","Ticket Cảnh Báo"};
    private layoutTabTicketTheodoi mFrgTicketTheodoi;
    private layoutTabTicketCanhbao mFrgTicketCanhbao;
    private Bundle bundle;
    public layoutTabTicketAdapter(@NonNull FragmentManager fm, Bundle bundle) {
        super(fm);
        mFrgTicketTheodoi = new layoutTabTicketTheodoi();
        mFrgTicketCanhbao = new layoutTabTicketCanhbao();
        this.bundle =bundle;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            mFrgTicketTheodoi.setArguments(bundle);
            return mFrgTicketTheodoi;
        }else if(position==1){
            mFrgTicketCanhbao.setArguments(bundle);
            return mFrgTicketCanhbao;
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
