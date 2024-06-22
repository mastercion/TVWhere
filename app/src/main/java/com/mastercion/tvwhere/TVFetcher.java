package com.mastercion.tvwhere;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class TVFetcher extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    private ExoPlayer player;
    private PlayerView playerView;
    private long lastBackPressTime = 0;
    private static final long BACK_PRESS_INTERVAL = 2000; // 2 seconds
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        EdgeToEdge.enable(this);

        playerView = findViewById(R.id.player_view);
        String url = getIntent().getStringExtra("url");
        Log.d("TVFetcher", "Fetching TV data from URL: " + url);

        if (url == null || url.isEmpty()) {
            Log.e(TAG, "Invalid or missing video URL");
            finish();
            return;
        }

        initializePlayer(url);
        setupFocusChangeListener();
    }

    private void initializePlayer(String videoUrl) {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            Uri uri = Uri.parse(videoUrl);
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);

            player.prepare();
            player.play();
        }
    }

    private void setupFocusChangeListener() {
        View[] controls = {
                findViewById(R.id.exo_play),
                findViewById(R.id.exo_pause),
                findViewById(R.id.exo_ffwd),
                findViewById(R.id.exo_rew),
                findViewById(R.id.exo_progress),
                findViewById(R.id.exo_position),
                findViewById(R.id.exo_duration)
        };

        for (View control : controls) {
            control.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    v.setBackgroundColor(Color.parseColor("#474747")); // Example hex color (red)
                } else {
                    v.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(getIntent().getStringExtra("video_url"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            initializePlayer(getIntent().getStringExtra("video_url"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressTime < BACK_PRESS_INTERVAL) {
            super.onBackPressed(); // Exit the activity
        } else {
            lastBackPressTime = currentTime;
            if (playerView.getUseController()) {
                playerView.hideController();
            } else {
                playerView.showController();
            }
            // Reset the back press time after a delay
            handler.postDelayed(() -> lastBackPressTime = 0, BACK_PRESS_INTERVAL);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Handle remote control key events
        if (playerView.dispatchKeyEvent(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
