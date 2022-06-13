package com.ysenetdigital.yesnetmassage.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ysenetdigital.yesnetmassage.Fregments.FriendRequest;
import com.ysenetdigital.yesnetmassage.Fregments.addFriends;
import com.ysenetdigital.yesnetmassage.Fregments.requestTo;

public class MemberFregmentAdapter extends FragmentPagerAdapter {
    public MemberFregmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new addFriends();

            case 1:
                return new FriendRequest();
            case 2:
                return new requestTo();

            default:
                return new addFriends();

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
            title = "";
        }if (position==1){
            title = "Request From ";
        }if (position==2){
            title = "Request To";
        }
        return title;
    }
}
