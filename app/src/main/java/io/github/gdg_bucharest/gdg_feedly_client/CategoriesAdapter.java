package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;

/**
 * Created by pndl on 2/20/15.
 */
public class CategoriesAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categories;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_category, parent, false);
        }

        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        categoryName.setText(getItem(position).label);

        return convertView;

    }
}
