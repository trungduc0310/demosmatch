package com.example.phamtrungduc.smatch.adapter;

import com.example.phamtrungduc.smatch.fragment.NewFeedFragment;
import com.example.phamtrungduc.smatch.fragment.MatchFragment;
import com.example.phamtrungduc.smatch.fragment.NotificationFragment;
import com.example.phamtrungduc.smatch.fragment.OptionFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new NewFeedFragment();
                break;
            case 1:
                fragment = new MatchFragment();
                break;
            case 2:
                fragment = new NotificationFragment();
                break;
            case 3:
                fragment = new OptionFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }




}
