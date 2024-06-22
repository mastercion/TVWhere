package com.mastercion.tvwhere;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        EdgeToEdge.enable(this);
        String url = getIntent().getStringExtra("url");
        String isType = getIntent().getStringExtra("isType");

        if ("livetv".equals(isType)) {
            Intent intent = new Intent(this, TVFetcher.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } else {
            Log.e(TAG, "Unsupported isType: " + isType);
            finish();
        }
    }
}
