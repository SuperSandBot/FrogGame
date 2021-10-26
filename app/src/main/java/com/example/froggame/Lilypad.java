package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class Lilypad extends GameObject {

    //tool
    Handler handler;
    Runnable runnable;

    //index
    Platform currentplatform;
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    float rotate = 0;
    int currentImage = 0;
    boolean wait = false;

    public Lilypad()
    {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Decaying();
            }
        };
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(getRotatedBm(),this.x,this.y,null);
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

    //xoay hình theo hướng di chuyển
    public Bitmap getRotatedBm()
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(getBm(),0,0,this.Width,this.Height,matrix,true);
    }

    public void StartEvent()
    {
        handler.postDelayed(runnable,20);
    }

    public void Decaying()
    {
        //kiểm tra dừng vòng lập
        if(currentImage == Bitmaps.size()-1)
        {

            currentplatform.platformtype = Platform.platformType.nothing;
            if(wait)
            {
                Random random = new Random();
                int num = random.nextInt(2);
                if(num == 0)
                {
                    currentplatform.rock.StartEvent();
                }
                else
                {
                    currentplatform.ResetBadEvent();
                }
                handler.removeCallbacks(runnable);
            }
            handler.postDelayed(runnable,2000);
            wait = true;
            return;
        }
        currentImage++;

        wait = false;
        //bảo nó gọi hàm lại
        handler.postDelayed(runnable,1500);
    }
}
