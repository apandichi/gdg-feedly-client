package io.github.gdg_bucharest.gdg_feedly_client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Entry;

/**
 * Created by pndl on 3/29/15.
 */
public class EntryPageFragment extends Fragment {

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

        TextView author = (TextView) view.findViewById(R.id.entry_page_author);
        author.setText(entry.getAuthor());

        if (entry.getContent() != null) {
            TextView content = (TextView) view.findViewById(R.id.entry_page_content);
            content.setText(entry.getContent().getContent());
        } else if (entry.getSummary() != null) {
            TextView content = (TextView) view.findViewById(R.id.entry_page_content);
            content.setText(entry.getSummary().getContent());
        }

        return view;

    }
}
