package com.example.princ.inclass07;
/*
  Author : Sujanth Babu Guntupalli
*/

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    //String[] categories = {"Business","Entertainment","General","Health","Science","Sports","Technology"};
    String[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("Categories");
        categories=getResources().getStringArray(R.array.string_array_category);
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, categories);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),NewsActivity.class);
                intent.putExtra("category",categories[i]);
                startActivity(intent);
            }
        });
    }

}
