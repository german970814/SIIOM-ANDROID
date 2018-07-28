package com.ingeniartesoft.siiom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by german on 28/08/16.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private int[] icons = {
        R.mipmap.ic_account_box_white_18dp,
        R.mipmap.ic_add_white_24dp
    };

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
            MiembroFragment tab1 = new MiembroFragment();
            tab1.setArguments(bundle);
            return tab1;
        }
        else if (position == 1) {
            GrupoFragment grupoFragment = new GrupoFragment();
            grupoFragment.setArguments(bundle);
            return grupoFragment;
        } else {
            DiscipulosFragment tab3 = new DiscipulosFragment();
            tab3.setArguments(bundle);
            return tab3;
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
