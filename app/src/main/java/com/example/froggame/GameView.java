package com.example.froggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class GameView extends View {

    Frog frog;
    Platform[][] platforms;
    Handler handler;
    Runnable runnable;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetupGameView();
        SetupFrog();
        SetupGameControl();

        //Khởi tạo hàm update
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

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

        //gọi handler bảo nó update mỗi 0.01 giây
        handler.postDelayed(runnable,10);
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
                }
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                //move to left
                if(frog.currentplatform.left != null)
                {
                    frog.move(frog.currentplatform.left);
                }
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                //move to top
                if(frog.currentplatform.top != null)
                {
                    frog.move(frog.currentplatform.top);
                }
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                //move to bot
                if(frog.currentplatform.bot != null)
                {
                    frog.move(frog.currentplatform.bot);
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
                platform.setX((int)(100 * SCREEN.WIDTH/ 900 + i * 300));
                platform.setY((int)(100 * SCREEN.HEIGHT/ 300 + y * 370));
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
        frog.currentplatform = platforms[0][0];
        frog.setX(platforms[0][0].getX());
        frog.setY(platforms[0][0].getY() - 40);
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
