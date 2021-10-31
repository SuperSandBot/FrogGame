package com.example.froggame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    DataSource dataSource;
    ScoreAdapter adapter;
    ListView listView;
    Button btnClose;
    Button btnReset;
    SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        listView = findViewById(R.id.lvScore);
        btnClose = findViewById(R.id.btnClose);
        btnReset = findViewById(R.id.btnReset);
        soundPlayer = new SoundPlayer(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        dataSource = DataSource.getInstance(this);
        if(dataSource != null)
        {
            dataSource.open();
            List<PlayerScore> playerList = dataSource.getAllPlayer();
            if(playerList != null)
            {
                playerList.sort(this::comparator);
                adapter = new ScoreAdapter(this, playerList);
                listView.setAdapter(adapter);
            }
            btnReset.setOnClickListener(view -> {
                dataSource.clearALlPlayer();
                finish();
            });
            soundPlayer.playsfxbuttonclick();
        }
        btnClose.setOnClickListener(view ->
        {
            soundPlayer.playsfxbuttonclick();
            finish();
        });
    }

    private int comparator(PlayerScore p1, PlayerScore p2){
        return p2.getBestScore() - p1.getBestScore();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (dataSource != null)
            dataSource.close();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}