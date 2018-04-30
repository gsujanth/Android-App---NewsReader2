package com.example.princ.inclass07;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView tvdTitle,tvdDate,tvdDesc;
    ImageView ivdImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Detail Activity");

        tvdTitle=(TextView) findViewById(R.id.titleTVD);
        tvdDate=(TextView) findViewById(R.id.dateTVD);
        tvdDesc=(TextView) findViewById(R.id.descTVD);
        ivdImageView=(ImageView) findViewById(R.id.imageViewD);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("detailedNews")) {
                NewsArticle presentArticle = (NewsArticle) getIntent().getExtras().getSerializable("detailedNews");
                tvdTitle.setText(presentArticle.getTitle());
                tvdDate.setText(presentArticle.getPublishedAt());
                tvdDesc.setText(presentArticle.getDescription());
                if(isConnected()) {
                    Picasso.with(DetailActivity.this).load(presentArticle.getUrlToImage()).into(ivdImageView);
                }else{
                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
}
