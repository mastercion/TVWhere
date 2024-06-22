package com.mastercion.tvwhere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridLayout cardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        cardContainer = findViewById(R.id.card_container);

        // Set up the static search button
        LinearLayout searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            // Handle search button click
            SearchDialogFragment dialog = new SearchDialogFragment();
            dialog.show(getSupportFragmentManager(), "SearchDialog");
        });

        // Ensure search button is focusable
        searchButton.setFocusable(true);
        searchButton.setFocusableInTouchMode(true);
        searchButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.setBackgroundResource(R.drawable.card_outline_background);
            } else {
                v.setBackgroundResource(R.drawable.card_background);
            }
        });

        // List of card names
        String[] cardNames = {"QVC", "QVC Style", "QVC Zwei", "HSE Extra", "HSE Trend", "HSE", "Channel 21"};

        // List of corresponding URLs
        String[] cardUrls = {
                "https://qvcde-live.akamaized.net/hls/live/2097104/qvc/master.m3u8",
                "https://qvcde-live.akamaized.net/hls/live/2097104/qby/master.m3u8",
                "https://qvcde-live.akamaized.net/hls/live/2097104/qps/master.m3u8",
                "https://hse24extra.akamaized.net/hls/live/2006596/hse24extra/master_576p25.m3u8",
                "https://hse24trend.akamaized.net/hls/live/2006597/hse24trend/master_432p25.m3u8",
                "https://hse24.akamaized.net/hls/live/2006663/hse24/master_576p25.m3u8",
                "https://www.youtube.com/watch?v=0Vz3bK6K0f4"

        };

        // Adding specific card items programmatically
        for (int i = 0; i < cardNames.length; i++) {
            String cardName = cardNames[i];
            String cardUrl = cardUrls[i];
            String isType;

            if (i >= 0 && i <= 5) {
                isType = "livetv";
            } else {
                isType = "youtube";
            }


            TextView card = new TextView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            card.setLayoutParams(params);
            card.setText(cardName);
            card.setBackgroundResource(R.drawable.card_background);
            card.setPadding(20, 20, 20, 20);
            card.setGravity(View.TEXT_ALIGNMENT_CENTER);
            card.setFocusable(true);
            card.setFocusableInTouchMode(true);
            cardContainer.addView(card);

            card.setOnClickListener(v -> {
                // Handle card item click
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", cardName);
                intent.putExtra("title", "Some Title"); // Replace with actual title if needed
                intent.putExtra("url", cardUrl); // Pass the URL
                intent.putExtra("isType", isType); // Pass the isType
                startActivity(intent);
            });

            card.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.card_outline_background);
                } else {
                    v.setBackgroundResource(R.drawable.card_background);
                }
            });
        }
    }
}
