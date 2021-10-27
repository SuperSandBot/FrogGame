package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;

import java.util.ArrayList;

public class Frog extends GameObject {

    //tool
    Handler handler;
    Runnable runnable;

    //index
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    Platform currentplatform;
    GameActivity gameActivity;
    float avgSpeedX , avgSpeedY;
    float rotate = 0;
    int currentImage = 0;
    Boolean ismoving = false;

    public Frog()
    {
        super();

        //khởi tạo handler;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                PlayMoveAnimationAndMove();
            }
        };
    }

    //Vẽ cóc lên màng hình
    public void draw(Canvas canvas)
    {
        if(rotate == 90 || rotate == -90)
        {
            canvas.drawBitmap(getRotatedBm(),this.x - 70,this.y + 25,null);
        }
        else
        {
            canvas.drawBitmap(getRotatedBm(),this.x + 25 ,this.y - 70,null);
        }
    }

    public Bitmap getBm()
    {
        return this.Bitmaps.get(currentImage);
    }

    //khởi tạo hình ảnh
    public void setBitmaps(ArrayList<Bitmap> Bitmaps)
    {
        this.Bitmaps = Bitmaps;
        for(int i = 0 ; i < Bitmaps.size() ; i++)
        {
            this.Bitmaps.set(i,Bitmap.createScaledBitmap(this.Bitmaps.get(i),this.Width,this.Height,true));
        }
    }

    //xoay hình theo hướng di chuyển
    public Bitmap getRotatedBm()
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(getBm(),0,0,this.Width,this.Height,matrix,true);
    }

    //di chuyển qua vị trí khác
    public void move(Platform platform)
    {
        if(ismoving)
        {
            return;
        }
        switch (platform.platformtype)
        {
            case lilypad:
            {
                ismoving = true;
                gameActivity.health.takeDamge();
                currentplatform = platform;
                //xác định tóc độ trung bình của cóc trong 6 vòng lập để dến địa điểm
                // chia 5.5 cho nó lệch 1 tí nhìn ảo ảo
                avgSpeedX =((currentplatform.getX() - this.x) / 6);
                avgSpeedY =((currentplatform.getY() - this.y) / 6);
                handler.postDelayed(runnable,20);
                switch (platform.itemtype)
                {
                    case fly:
                    {
                        currentplatform.itemtype = Platform.platformType.nothing;
                        gameActivity.health.Heal(8);
                        gameActivity.Score += 5;
                        break;
                    }

                    case coin:
                    {
                        currentplatform.itemtype = Platform.platformType.nothing;
                        gameActivity.Score += 20;
                        break;
                    }
                }
                break;
            }
            case rock:
            {
                handler.postDelayed(runnable,20);
                //không đi đâu
                avgSpeedX =((0.0f) / 6);
                avgSpeedY =((0.0f) / 6);
                break;
            }
            case nothing:
            {
                ismoving = true;
                currentplatform = platform;
                avgSpeedX =((currentplatform.getX() - this.x) / 6);
                avgSpeedY =((currentplatform.getY() - this.y) / 6);
                handler.postDelayed(runnable,20);
                gameActivity.GameOverEvent();
                break;
            }
        }
    }

    //chơi animtion và nhảy đến dịa điểm
    public void PlayMoveAnimationAndMove()
    {
        //kiểm tra dừng vòng lập
        if(currentImage == Bitmaps.size()-1)
        {
            currentImage = 0;
            ismoving = false;
            handler.removeCallbacks(runnable);
            gameActivity.LoadGame();
            return;
        }
        currentImage++;

        //đi đến diểm dựa trên agvspeed
        this.x += avgSpeedX;
        this.y += avgSpeedY;

        //bảo nó gọi hàm lại
        handler.postDelayed(runnable,30);
    }

}
