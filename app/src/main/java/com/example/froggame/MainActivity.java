package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer backgroundMusic;
    private SoundPlayer soundPlayer;
    boolean soundCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent intentScore = new Intent(this, ScoreActivity.class);
        EditText edtPlayerName = findViewById(R.id.edtPlayerName);
        Button btnScore = findViewById(R.id.btnBestScore);
        Button btnSound = findViewById(R.id.btnSound);
        Button btnPlay = findViewById(R.id.btnPlay);
        soundPlayer = new SoundPlayer(this);

        backgroundMusic = MediaPlayer.create(this,R.raw.musiccolorbock);
        backgroundMusic.setLooping(true);

        btnScore.setOnClickListener(view ->{
            soundPlayer.playsfxbuttonclick();
            startActivity(intentScore);
        });

        btnSound.setOnClickListener(view -> {
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
            soundPlayer.playsfxbuttonclick();
        });

        btnPlay.setOnClickListener(view -> {
            if (String.valueOf(edtPlayerName.getText()).equals("")){
                Toast.makeText(this, "Bạn Chưa Nhập Player Name !!!", Toast.LENGTH_LONG).show();
            }else{
                Intent intentGame = new Intent(this, GameActivity.class);
                intentGame.putExtra("PlayerName", String.valueOf(edtPlayerName.getText()));
                startActivity(intentGame);
                onStop();
            }
            soundPlayer.playsfxbuttonclick();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!backgroundMusic.isPlaying()){
            backgroundMusic.start();
        }
    }
    @Override
    protected void onStop() {
        backgroundMusic.pause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}