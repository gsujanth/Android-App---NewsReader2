package com.example.princ.inclass07;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements GetNewsTask.INewsArticle {

    ArrayList<NewsArticle> newsArticles=new ArrayList<>();
    ListView newsListView;
    NewsAdapter2 newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListView = (ListView)findViewById(R.id.newsListView);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("category")){
                String sCategory=getIntent().getExtras().getString("category");
                Log.d("demoNA", "onCreate: "+sCategory);
                setTitle(sCategory);
                if(isConnected()) {
                    new GetNewsTask(NewsActivity.this,NewsActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&apiKey=bbee98d8505449b6ae2f0f5e4bdb22b3&category=" + sCategory);
                }else{
                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),DetailActivity.class);
                intent.putExtra("detailedNews",newsArticles.get(i));
                startActivity(intent);
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handleNewsArticle(ArrayList<NewsArticle> s) {
        newsArticles.clear();
        if(s!=null&&!s.isEmpty()) {
            newsArticles = s;
            newsAdapter = new NewsAdapter2(this, R.layout.news_listview, newsArticles);
            newsListView.setAdapter(newsAdapter);
        }else{
            Toast.makeText(this, "No News Found", Toast.LENGTH_SHORT).show();
            newsAdapter = new NewsAdapter2(this, R.layout.news_listview, newsArticles);
            newsListView.setAdapter(newsAdapter);
        }
    }
}
