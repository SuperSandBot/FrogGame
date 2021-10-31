package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    //Class for using
    GameView gameView;
    GameEvent gameEvent;
    MediaPlayer backgroundMusic;
    DataSource dataSource;
    TextView ScoreView;
    SoundPlayer soundPlayer;

    //UI element for using
    LinearLayout gameOverView;
    Button btnA, btnB;
    TextView ScoreOnView;

    //index for using
    String playerName;
    Frog frog;
    Health health;
    Platform[][] platforms;
    int Score = 0;
    boolean isgameover;

    //tool for using
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN.WIDTH = dm.widthPixels;
        SCREEN.HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_game);
        dataSource = DataSource.getInstance(this);

        Bundle bundle = getIntent().getExtras();
        playerName = bundle.getString("PlayerName");
        ScoreView = findViewById(R.id.ScoreView);
        gameView = findViewById(R.id.GameView);
        gameOverView = findViewById(R.id.GameOverView);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        ScoreOnView = findViewById(R.id.ScoreV);

        gameOverView.setVisibility(View.GONE);
        backgroundMusic = MediaPlayer.create(this,R.raw.musiccomputergag);
        backgroundMusic.setLooping(true);
        handler = new Handler();
        runnable = this::LoadGame;
        gameEvent = new GameEvent();
        soundPlayer = new SoundPlayer(this);

        SetupGameView();
        SetupFrog();
        gameView.platforms = platforms;
        gameView.frog = frog;
        gameView.health = health;
        gameEvent.frog = frog;
        gameEvent.platforms = platforms;
        SetupGameControl();
        LoadGame();
        isgameover = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataSource = DataSource.getInstance(this);
        dataSource.open();
        if(!backgroundMusic.isPlaying()){
            backgroundMusic.start();
        }
    }

    public void UpdateScore()
    {
       ScoreView.setText("Score :" + Score);
    }

    public void LoadGame()
    {
        if(isgameover)
        {
            return;
        }
        if(health.CurrentHeart == 0)
        {
            GameOverEvent();
        }
        for (int i = 0 ; i < platforms.length; i++)
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                platforms[i][y].LoadGame();
                if(frog.currentplatform == platforms[i][y] && platforms[i][y].platformtype == Platform.platformType.nothing)
                {
                    GameOverEvent();
                }
            }
        }
        Score++;
        UpdateScore();
        gameEvent.StartRamdomEvent();
    }
    public void GameOverEvent()
    {
        isgameover = true;
        backgroundMusic.stop();
        soundPlayer.playsfxgameover();
        ScoreOnView.setText("Score :" + Score);
        dataSource.open();
        PlayerScore player = dataSource.getPlayer(playerName);
        if (player == null) {
            PlayerScore newPlayer = new PlayerScore(playerName, Score);
            dataSource.addPlayer(newPlayer);
            dataSource.close();
        }
        else {
            if ( player.getBestScore() - Score < 0 ){
                player.setBestScore(Score);
                dataSource.updatePlayerScore(player);
                dataSource.close();
            }
        }
        gameOverView.setVisibility(View.VISIBLE);
    }

    public void onRetryAccept(View view)
    {
        soundPlayer.playsfxbuttonclick();
        Intent intentGame = new Intent(this, GameActivity.class);
        intentGame.putExtra("PlayerName", playerName);
        startActivity(intentGame);
        this.finish();
    }

    public void onRetryCancel(View view)
    {
        soundPlayer.playsfxbuttonclick();
        if (dataSource != null)
            dataSource.close();
        this.finish();
    }

    //Game Controller
    @SuppressLint("ClickableViewAccessibility")
    private void SetupGameControl()
    {
        gameView.setOnTouchListener(new GameControler(this)
        {

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                //move to right
                if(isgameover)
                {
                    return;
                }
                if(frog.currentplatform.right != null)
                {
                    frog.move(frog.currentplatform.right);
                    frog.rotate = 90;
                }
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                //move to left
                if(isgameover)
                {
                    return;
                }
                if(frog.currentplatform.left != null)
                {
                    frog.move(frog.currentplatform.left);
                    frog.rotate = -90;
                }
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                //move to top
                if(isgameover)
                {
                    return;
                }
                if(frog.currentplatform.top != null)
                {
                    frog.move(frog.currentplatform.top);
                    frog.rotate = 0;
                }
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                //move to bot
                if(isgameover)
                {
                    return;
                }
                if(frog.currentplatform.bot != null)
                {
                    frog.move(frog.currentplatform.bot);
                    frog.rotate = 180;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if(!backgroundMusic.isPlaying()){
            backgroundMusic.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        backgroundMusic.stop();
        super.onStop();
    }

    //Setup game khi mới vô
    private void SetupGameView()
    {
        // khởi tạo 4 * 4 lá sen lên màn hình
        platforms = new Platform[4][4];
        for (int i = 0 ; i < platforms.length; i++ )
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                Platform platform = new Platform();
                platform.gameEvent = this.gameEvent;
                platform.setX(SCREEN.WIDTH/9 + i * SCREEN.WIDTH/5);
                platform.setY(SCREEN.HEIGHT/3 + y * SCREEN.HEIGHT/7);
                platform.Setup(this.getResources());
                platforms[i][y] = platform;
            }
        }

        // Xác định vị trí có thể đi được cho từng phần tử trong mảng
        for(int i = 0; i < platforms.length ; i++)
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                if(ifSafe(i,y-1,platforms))
                {
                    //top
                    platforms[i][y].top = platforms[i][y-1];
                }
                if(ifSafe(i,y+1,platforms))
                {
                    //bot
                    platforms[i][y].bot = platforms[i][y+1];
                }
                if(ifSafe(i-1,y,platforms))
                {
                    //left
                    platforms[i][y].left = platforms[i-1][y];
                }
                if(ifSafe(i+1,y,platforms))
                {
                    //right
                    platforms[i][y].right = platforms[i+1][y];
                }
            }
        }

        //Setup Health for frog
        health = new Health();
        health.setX(SCREEN.WIDTH/9);
        health.setY(SCREEN.HEIGHT/13);
        health.setWidth(SCREEN.WIDTH/10);
        health.setHeight(SCREEN.WIDTH/10);
        health.setBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.heart));
    }

    //kiểm tra DFS
    private boolean ifSafe(int i, int y, Platform[][] p)
    {
        return i >= 0 && i < p.length && y >= 0 && y < p[0].length && p[i][y] != null;
    }

    //khởi tạo con cóc tại cái lục bình đầu tiên
    private void SetupFrog()
    {
        frog = new Frog();
        frog.gameActivity = this;
        frog.soundPlayer = this.soundPlayer;
        frog.currentplatform = platforms[3][3];
        frog.setX(platforms[3][3].getX());
        frog.setY(platforms[3][3].getY());
        frog.setWidth(SCREEN.HEIGHT/11);
        frog.setHeight(SCREEN.HEIGHT/7);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog1));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog2));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog3));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog4));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog5));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog6));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog7));
        frog.setBitmaps(bitmaps);
    }
}