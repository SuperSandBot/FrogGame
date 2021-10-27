package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;

import java.util.ArrayList;

public class Fly extends GameObject{

    //tool
    Handler handler;
    Runnable runnable;

    //index
    Platform currentplatform;
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    int currentImage = 0;
    boolean HadEvent = false;

    public Fly()
    {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                PlayAnimation();
            }
        };
        handler.postDelayed(runnable,20);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(getBm(),this.x,this.y,null);
    }

    public Bitmap getBm()
    {
        return this.getBitmaps().get(currentImage);
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
            this.Bitmaps.set(i,Bitmap.createScaledBitmap(Bitmaps.get(i),this.Width,this.Height,true));
        }
    }
    public void StartEvent()
    {
        if(HadEvent)
        {
            if(currentplatform.itemtype != Platform.platformType.fly)
            {
                currentplatform.ResetFlyEvent();
            }
        }
    }

    public void PlayAnimation()
    {
        if(currentImage == Bitmaps.size() - 1)
        {
            currentImage = 0;
        }
        else
        {
            currentImage++;
        }
        handler.postDelayed(runnable,50);
    }
}
