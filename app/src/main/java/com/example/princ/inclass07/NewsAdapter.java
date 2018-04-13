package com.example.princ.inclass07;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsArticle> {

    NewsArticle newsArticle;
    TextView tvTitle, tvDate;
    ImageView imageView;
    Context ctx;

    public NewsAdapter(@NonNull Context context, int resource, List<NewsArticle> objects, Context ctx) {
        super(context, resource, objects);
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        newsArticle = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_listview, parent, false);
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        imageView = (ImageView) convertView.findViewById(R.id.imageViewD);
        Log.d("demo3", "getView: " + newsArticle.title);
        tvTitle.setText(newsArticle.title);
        tvDate.setText(newsArticle.publishedAt);
        if (isConnected()) {
            Picasso.with(convertView.getContext()).load(newsArticle.urlToImage).into(imageView);
        } else {
            Toast.makeText(ctx, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return convertView;

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
