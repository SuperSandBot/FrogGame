package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class Lilypad extends GameObject {


    //index
    Platform currentplatform;
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    int Turn = 5;
    int currentImage = 0;
    boolean HadEvent = false;

    public Lilypad()
    {

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

    public void Decaying()
    {
        if(HadEvent)
        {

            //kiểm tra dừng
            if(currentImage == Bitmaps.size()-1)
            {
                currentplatform.platformtype = Platform.platformType.nothing;
                if(Turn == 0)
                {
                    Turn = 5;
                    currentImage = 0;
                    currentplatform.ResetLilyEvent();
                }
                Turn--;
                return;
            }
            Turn--;
            currentImage++;
        }
    }
}
