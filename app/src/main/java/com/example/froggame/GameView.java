package com.example.froggame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    //index
    Frog frog;
    Health health;
    Platform[][] platforms;
    int Score = 0;
    int MaxEvent = 10;
    int MaxBadEvent = 4;
    int MaxGoodEvent = 6;
    int CurrentBadvent = 0;
    int CurrentGoodEvent = 0;

    //tool
    Handler handler;
    Runnable runnable;
    Runnable runnable2;
    Random random;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetupGameView();
        SetupFrog();
        SetupGameControl();
        //Khởi tạo handler để cho runnable update liên tục.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //chạy lại hàm onDraw
                invalidate();
            }
        };
        runnable2 = new Runnable() {
            @Override
            public void run() {
                StartRamdomEvent();
            }
        };
        StartRamdomEvent();
    }

    //cập nhật điểm
    private void UpdateScore()
    {

    }
    //không xài nhưng cần
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    //Vẽ hình ảnh lên UI
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        for (int i = 0 ; i < platforms.length; i++ )
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                platforms[i][y].draw(canvas);
            }
        }
        frog.draw(canvas);
        health.draw(canvas);
        //gọi handler bảo nó update mỗi 0.01 giây
        handler.postDelayed(runnable,20 );
    }

    private void StartRamdomEvent()
    {
        if(CurrentBadvent + CurrentGoodEvent <= MaxEvent)
        {
            random = new Random();
            int num1 = random.nextInt(4);
            int num2 = random.nextInt(4);
            int eventNum = random.nextInt(4);

            if(platforms[num1][num2].hadEvent)
            {
                handler.postDelayed(runnable2,1000);
            }
            else
            {
                switch (eventNum)
                {
                    case 0:
                    {
                        platforms[num1][num2].hadEvent = true;
                        platforms[num1][num2].lilypad.StartEvent();
                        CurrentBadvent++;
                        break;
                    }
                    case 1:
                    case 2: {
                        platforms[num1][num2].hadEvent = true;
                        platforms[num1][num2].fly.StartEvent();
                        CurrentGoodEvent++;
                        break;
                    }
                    case 3:
                    {
                        platforms[num1][num2].hadEvent = true;
                        platforms[num1][num2].coin.StartEvent();
                        CurrentGoodEvent++;
                        break;
                    }
                }
                handler.postDelayed(runnable2,1000);
            }
        }
    }


    //Game Controler
    private void SetupGameControl()
    {
        this.setOnTouchListener(new GameControler(this.getContext())
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
                platform.gameView = this;
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
        frog.gameView = this;
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
