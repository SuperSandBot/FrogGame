package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        DataSource dataSource = DataSource.getInstance(this);
        List<PlayerScore> playerList = dataSource.getAllPlayer();
        ListView listView = findViewById(R.id.lvScore);
        ScoreAdapter adapter = new ScoreAdapter(this, playerList);
        listView.setAdapter(adapter);
        Button btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    public void close(){
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}