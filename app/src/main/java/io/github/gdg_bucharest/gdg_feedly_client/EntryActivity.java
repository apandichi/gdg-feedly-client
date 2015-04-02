package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Entry;

/**
 * Created by pndl on 3/29/15.
 */
public class EntryActivity extends FragmentActivity {

    public static final String ENTRIES = "entries";
    public static final String POSITION = "POSITION";
    private List<Entry> entries;

    public static Intent newIntent(Context context, List<Entry> entries, int position) {
        Intent intent = new Intent(context, EntryActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelableArray(ENTRIES, entries.toArray(new Entry[]{}));
        extras.putInt(POSITION, position);
        intent.putExtras(extras);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        extractEntries(savedInstanceState);

        List<Fragment> fragments = getFragments();
        EntryPageAdapter pageAdapter = new EntryPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        pager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    private void extractEntries(Bundle savedInstanceState) {
        Parcelable[] parcelables = savedInstanceState.getParcelableArray(ENTRIES);
        Entry[] entries = Arrays.copyOf(parcelables, parcelables.length, Entry[].class);
        this.entries = Arrays.asList(entries);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(ENTRIES, entries.toArray(new Entry[] {}));
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        outState.putInt(POSITION, pager.getCurrentItem());
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (Entry entry : entries) {
            fragments.add(EntryPageFragment.newInstance(entry));
        }
        return fragments;
    }

}
