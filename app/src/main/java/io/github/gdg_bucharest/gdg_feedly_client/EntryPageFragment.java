package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Entry;

/**
 * Created by pndl on 3/29/15.
 */
public class EntryPageFragment extends Fragment {

    String TAG = EntryPageFragment.class.getName();

    public static final String ENTRY = "entry";
    private Entry entry;

    public static EntryPageFragment newInstance(Entry entry) {
        EntryPageFragment entryPageFragment = new EntryPageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ENTRY, entry);
        entryPageFragment.setArguments(bundle);
        return entryPageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        entry = savedInstanceState.getParcelable(ENTRY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ENTRY, entry);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_page, container, false);

        TextView title = (TextView) view.findViewById(R.id.entry_page_title);
        title.setText(entry.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = entry.getOriginUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView author = (TextView) view.findViewById(R.id.entry_page_author);
        author.setText(entry.getAuthor());

        TextView published = (TextView) view.findViewById(R.id.entry_page_published);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm");
        published.setText(format.format(entry.getPublished()));

        String content = null;
        if (entry.getContent() != null) {
            content = entry.getContent().getContent();
        } else if (entry.getSummary() != null) {
            content = entry.getSummary().getContent();
        }

        WebView webView = (WebView) view.findViewById(R.id.webview_content);
        webView.setBackgroundColor(getResources().getColor(R.color.background_material_light));
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadData(content, "text/html; charset=UTF-8", null);

        return view;

    }

}
