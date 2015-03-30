package io.github.gdg_bucharest.gdg_feedly_client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

        TextView author = (TextView) view.findViewById(R.id.entry_page_author);
        author.setText(entry.getAuthor());

        String content = null;
        if (entry.getContent() != null) {
            content = entry.getContent().getContent();
        } else if (entry.getSummary() != null) {
            content = entry.getSummary().getContent();
        }

        final TextView contentTextView = (TextView) view.findViewById(R.id.entry_page_content);
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                new LoadImage(contentTextView).execute(source, d);

                return d;
            }
        };
        contentTextView.setText(Html.fromHtml(content, imageGetter, null));

        return view;

    }




    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private final TextView textView;
        private LevelListDrawable mDrawable;

        public LoadImage(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];

            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = textView.getText();
                textView.setText(t);
            }
        }
    }
}
