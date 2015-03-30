package io.github.gdg_bucharest.gdg_feedly_client;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pndl on 3/29/15.
 */
public class EntryPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public EntryPageAdapter(FragmentManager fm,  List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
