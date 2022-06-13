package com.ysenetdigital.yesnetmassage.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ysenetdigital.yesnetmassage.Fregments.Counselling;
import com.ysenetdigital.yesnetmassage.Fregments.Members;
import com.ysenetdigital.yesnetmassage.Fregments.Teacher;
import com.ysenetdigital.yesnetmassage.R;

public class FragmentsAdapters extends FragmentPagerAdapter {

    public FragmentsAdapters(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {



        switch (position){
            case 0:
                return new Counselling();
            case 1:
                return new Members();
            case 2:
                return new Teacher();
            default:
                return new Counselling();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0){
            title = "Chats ";
        }if (position==1){
            title = "All Friends";
        }if (position==2){
            title = "My Friends";
        }
        return title;
    }



}
