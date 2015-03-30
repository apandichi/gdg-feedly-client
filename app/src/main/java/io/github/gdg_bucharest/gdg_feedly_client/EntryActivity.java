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
    private List<Entry> entries;

    public static Intent newIntent(Context context, List<Entry> entries) {
        Intent intent = new Intent(context, EntryActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelableArray(ENTRIES, entries.toArray(new Entry[] {}));
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
    }

    private void extractEntries(Bundle savedInstanceState) {
        Parcelable[] parcelables = savedInstanceState.getParcelableArray(ENTRIES);
        Entry[] entries = Arrays.copyOf(parcelables, parcelables.length, Entry[].class);
        this.entries = Arrays.asList(entries);
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (Entry entry : entries) {
            fragments.add(EntryPageFragment.newInstance(entry));
        }
        return fragments;
    }

}
