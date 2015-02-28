package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;

/**
 * Created by pndl on 2/20/15.
 */
public class SubscriptionAdapter extends BaseAdapter {

    private Context context;
    private List<Subscription> subscriptions;

    public SubscriptionAdapter(Context context, List<Subscription> subscriptions) {
        this.context = context;
        this.subscriptions = subscriptions;
    }

    @Override
    public int getCount() {
        return subscriptions.size();
    }

    @Override
    public Subscription getItem(int position) {
        return subscriptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_subscription, parent, false);
        }

        Subscription subscription = getItem(position);
        String imageUrl = subscription.getAnyImageUrl();

        ImageView imageView = (ImageView) convertView.findViewById(R.id.subscription_image);
        Picasso.with(context).load(imageUrl).into(imageView);

        TextView title = (TextView) convertView.findViewById(R.id.subscription_title);
        title.setText(subscription.getTitle());

        TextView website = (TextView) convertView.findViewById(R.id.subscription_website);
        website.setText(subscription.getWebsite());

        TextView categories = (TextView) convertView.findViewById(R.id.subscription_categories);
        categories.setText(subscription.getCategoriesAsText());

        return convertView;

    }
}
