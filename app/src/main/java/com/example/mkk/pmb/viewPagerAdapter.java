package com.example.mkk.pmb;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class viewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context context;

    public viewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        //return ListAktivitasSaya.newInstance(position+1);
        switch (position){
            case 0 : return new InfoKampusFragment();
        }
        return null;

    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0 :
                return "Info Kampus";
            case 1 :
                return "Info Akademik";
            case 2 :
                return "Kemahasiswaa";
        }
        return null;
    }
}
