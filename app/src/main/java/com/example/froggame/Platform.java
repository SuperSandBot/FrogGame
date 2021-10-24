package com.example.froggame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Platform extends GameObject{

    Lilypad lilypad;
    Platform right;
    Platform left;
    Platform top;
    Platform bot;

    public Platform()
    {
        super();
    }

    public void draw(Canvas canvas)
    {
        lilypad.draw(canvas);
    }

    //khởi tạo lục bình
    public void Setup(Resources resources)
    {
        lilypad = new Lilypad();
        lilypad.setX(this.x);
        lilypad.setY(this.y);
        lilypad.setWidth(260 * SCREEN.WIDTH / SCREEN.WIDTH);
        lilypad.setHeight(260 * SCREEN.HEIGHT / SCREEN.HEIGHT);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.lilypad_green));
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.lilypad_teal));
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.lilypad_yellow));
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.lilypad_orange));
        lilypad.setBitmaps(bitmaps);
    }
}
