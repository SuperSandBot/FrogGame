package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class Frog extends GameObject {

    Handler handler;
    Runnable runnable;
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    Platform currentplatform;

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

    //lấy danh sách hình ảnh
    public ArrayList<Bitmap> getBitmaps()
    {
        return this.Bitmaps;
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
        if( ismoving == false && platform != null)
        {
            ismoving = true;
            currentplatform = platform;
            handler.postDelayed(runnable,10);
            //xác định tóc độ trung bình của cóc trong 6 vòng lập để dến địa điểm
            avgSpeedX = (currentplatform.getX() - this.x) / 6;
            avgSpeedY = (currentplatform.getY() - this.y) / 6;
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
            return;
        }
        //đi đến diểm dựa trên agvspeed
        this.x += avgSpeedX;
        this.y += avgSpeedY;

        currentImage++;

        //bảo nó gọi hàm lại
        handler.postDelayed(runnable,50);
    }
}
