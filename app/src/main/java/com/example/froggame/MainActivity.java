package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer backgroundMusic;
    boolean soundCheck = false;
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent intentGame = new Intent(this, GameActivity.class);
        Intent intentScore = new Intent(this, ScoreActivity.class);

        EditText edtPlayerName = findViewById(R.id.edtPlayerName);
        Button btnScore = findViewById(R.id.btnBestScore);
        Button btnSound = findViewById(R.id.btnSound);
        Button btnPlay = findViewById(R.id.btnPlay);

        backgroundMusic=MediaPlayer.create(this,R.raw.nhac);
        backgroundMusic.setLooping(true);



        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentScore);
            }
        });

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundCheck)
                {
                    btnSound.setBackground(getDrawable(R.drawable.sound));
                    backgroundMusic.setLooping(true);
                    backgroundMusic.start();
                    soundCheck = false;
                }
                else
                {
                    backgroundMusic.pause();
                    btnSound.setBackground(getDrawable(R.drawable.sound_off));
                    soundCheck = true;
                }

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerScore player = dataSource.getPlayer(String.valueOf(edtPlayerName.getText()));

                if (player == null) {
                    PlayerScore newPlayer = new PlayerScore();
                    newPlayer.setPlayerName(String.valueOf(edtPlayerName.getText()));
                    newPlayer.setBestScore(0);
                    dataSource.addPlayer(newPlayer);
                }
                startActivity(intentGame);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        dataSource = DataSource.getInstance(this);
        dataSource.open();
       /* if(!backgroundMusic.isPlaying()){
            backgroundMusic.start();
        }*/
    }
    @Override
    protected void onStop() {
        super.onStop();
        backgroundMusic.pause();
    }

    @Override
    protected void onDestroy() {
        if (dataSource != null)
            dataSource.close();
        super.onDestroy();
    }
}