package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    boolean soundCheck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        Intent intentSetting = new Intent( this, SettingActivity.class);

        Button btnSound = findViewById(R.id.btnSound);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnSetting = findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentSetting);
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundCheck)
                {
                    btnSound.setBackground(getDrawable(R.drawable.sound));
                    soundCheck = false;
                }
                else
                {
                    btnSound.setBackground(getDrawable(R.drawable.sound_off));
                    soundCheck = true;
                }

            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}