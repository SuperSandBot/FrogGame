package com.example.froggame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    TextView ScoreView;
    GameView gameView;

    Frog frog;
    Health health;
    Platform[][] platforms;

    int num1,num2,eventNum;
    int Score = 0;
    int MaxDecay = 5;
    int MaxRock = 2;
    int MaxFly = 1;
    int MaxCoin = 2;
    int CurrentDecay = 0;
    int CurrentRock = 0;
    int CurrentFly = 0;
    int CurrentCoin = 0;

    Random random;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN.WIDTH = dm.widthPixels;
        SCREEN.HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_game);

        ScoreView = findViewById(R.id.ScoreView);

        gameView = findViewById(R.id.GameView);

        random = new Random();

        SetupGameView();
        SetupFrog();
        gameView.platforms = platforms;
        gameView.frog = frog;
        gameView.health = health;
        SetupGameControl();
        LoadGame();

    }

    public void UpdateScore()
    {
       ScoreView.setText("Score :" + Score);
    }

    public void LoadGame()
    {
        Score++;
        UpdateScore();

        thread = new Thread(new Runnable(){
            @Override
            public void run(){
                StartRamdomEvent();
                for (int i = 0 ; i < platforms.length; i++ )
                {
                    for (int y = 0 ; y < platforms[0].length ; y++)
                    {
                        platforms[i][y].LoadGame();
                    }
                }
            }
        });
        thread.start();
    }

    public void GameOverEvent()
    {

    }

    // trả về ngẫy nhiên platfrom
    public Platform getRandomPlatform()
    {
       while(true)
       {
           num1 = random.nextInt(4);
           num2 = random.nextInt(4);
           if(platforms[num1][num2] == frog.currentplatform)
           {
               continue;
           }
           if(platforms[num1][num2].HadEvent)
           {
               continue;
           }
           return platforms[num1][num2];
       }
    }

    public void StartRamdomEvent()
    {
        if(CurrentFly + CurrentRock + CurrentDecay + CurrentCoin < 10)
        {
            thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    Platform platform = getRandomPlatform();
                    platform.HadEvent = true;
                    if(CurrentFly < MaxFly)
                    {
                        platform.StartFlyEvent();
                        CurrentFly++;
                        return;
                    }
                    eventNum = random.nextInt(3);
                    switch (eventNum)
                    {
                        case 0:
                        {
                            if(CurrentDecay != MaxDecay)
                            {
                                platform.StartLilyEvent();
                                CurrentDecay++;
                            }
                            break;
                        }
                        case 1:
                        {
                            if(CurrentRock != MaxRock)
                            {
                                platform.StartRockEvent();
                                CurrentRock++;
                            }
                            break;
                        }
                        case 2:
                        {
                            if(CurrentCoin != MaxCoin)
                            {
                                platform.StartCoinEvent();
                                CurrentCoin++;
                            }
                            break;
                        }
                    }
                }
            });
            thread.start();
        }
    }
    //Game Controler
    private void SetupGameControl()
    {
        gameView.setOnTouchListener(new GameControler(this)
        {

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                //move to right
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
                if(frog.currentplatform.bot != null)
                {
                    frog.move(frog.currentplatform.bot);
                    frog.rotate = 180;
                }
            }
        });
    }

    //Setup game khi mới vô
    private void SetupGameView()
    {
        // khởi tạo 4 * 4 lục bình lên màn hình
        platforms = new Platform[4][4];
        for (int i = 0 ; i < platforms.length; i++ )
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                Platform platform = new Platform();
                platform.gameActivity = this;
                platform.setX(100 * SCREEN.WIDTH/ 950 + i * 300);
                platform.setY(100 * SCREEN.HEIGHT/ 290 + y * 380);
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
        health.setX(100 * SCREEN.WIDTH/ SCREEN.WIDTH);
        health.setY(100 * SCREEN.HEIGHT/ SCREEN.HEIGHT);
        health.setWidth(100*SCREEN.WIDTH / SCREEN.WIDTH);
        health.setHeight(100*SCREEN.HEIGHT / SCREEN.HEIGHT);
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
        frog.currentplatform = platforms[3][3];
        frog.setX(platforms[3][3].getX());
        frog.setY(platforms[3][3].getY());
        frog.setWidth(220*SCREEN.WIDTH / SCREEN.WIDTH);
        frog.setHeight(400*SCREEN.HEIGHT / SCREEN.HEIGHT);
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