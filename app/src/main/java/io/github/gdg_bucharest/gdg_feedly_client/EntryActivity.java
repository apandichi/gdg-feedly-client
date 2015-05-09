package io.github.gdg_bucharest.gdg_feedly_client;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Entry;

/**
 * Created by pndl on 3/29/15.
 */
public class EntryActivity extends ActionBarActivity {

    public static final String ENTRIES = "entries";
    public static final String POSITION = "POSITION";
    private List<Entry> entries;
    private ViewPager pager;

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
        pager = (ViewPager) findViewById(R.id.viewpager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entry_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            EntryPageAdapter adapter = (EntryPageAdapter) pager.getAdapter();
            Entry entry = adapter.getItem(pager.getCurrentItem()).getEntry();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, entry.getTitle());
            intent.putExtra(Intent.EXTRA_TEXT, "Check this out! " + entry.getOriginUrl());
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (Entry entry : entries) {
            fragments.add(EntryPageFragment.newInstance(entry));
        }
        return fragments;
    }

}
