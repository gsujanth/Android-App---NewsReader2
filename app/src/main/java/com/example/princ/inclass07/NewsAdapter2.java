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

public class NewsAdapter2 extends ArrayAdapter<NewsArticle> {

    NewsArticle newsArticle;
    //TextView tvTitle, tvDate;
    //ImageView imageView;
    Context ctx;

    public NewsAdapter2(@NonNull Context context, int resource, List<NewsArticle> objects) {
        super(context, resource, objects);
        this.ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        newsArticle = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageViewD);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //Log.d("demo3", "getView: " + newsArticle.title);
        viewHolder.tvTitle.setText(newsArticle.title);
        viewHolder.tvDate.setText(newsArticle.publishedAt);
        if (isConnected()) {
            Picasso.with(convertView.getContext()).load(newsArticle.urlToImage).into(viewHolder.imageView);
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

    private static class ViewHolder{
        TextView tvTitle, tvDate;
        ImageView imageView;
    }

}

