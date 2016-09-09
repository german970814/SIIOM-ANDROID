package com.ingeniartesoft.siiom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by german on 28/08/16.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[];
    int NumbOfTabs;
    Bundle bundle;

    public ViewPageAdapter (FragmentManager fm, CharSequence mTitles[], int nNumbOfTabsumb, int ID_MIEMBRO) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = nNumbOfTabsumb;
        bundle = new Bundle();
        bundle.putInt("ID_MIEMBRO", ID_MIEMBRO);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Tab1 tab1 = new Tab1();
            tab1.setArguments(bundle);
            return tab1;
        }
        else {
            Tab2 tab2 = new Tab2();
            return tab2;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
