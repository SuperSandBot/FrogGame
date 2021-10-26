package com.example.froggame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

public class Platform extends GameObject{

    //enum dùng để phân loại kiểm tra vật thể hiện tại là gì
    enum platformType{
        lilypad,
        rock,
        coin,
        fly,
        nothing
    };
    public platformType platformtype;
    public platformType itemtype;
    Rock rock;
    Lilypad lilypad;
    Fly fly;
    Coin coin;
    GameView gameView;
    boolean hadEvent = false;

    //xác định vị trí
    Platform right;
    Platform left;
    Platform top;
    Platform bot;

    //tool
    Random random;
    Handler handler;

    public Platform()
    {
        super();
        handler = new Handler();
    }

    public void draw(Canvas canvas)
    {
        switch (platformtype)
        {
            case rock:
            {
                rock.draw(canvas);
                break;
            }
            case lilypad:
            {
                lilypad.draw(canvas);
                break;
            }
        }
        switch (itemtype)
        {
            case fly:
            {
                fly.draw(canvas);
                break;
            }
            case coin:
            {
                coin.draw(canvas);
                break;
            }
        }
    }

    public void ResetBadEvent()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lilypad.currentImage = 0;
                hadEvent = false;
                platformtype = platformType.lilypad;
                gameView.CurrentBadvent--;
            }
        },3000);
    }
    public void ResetGoodEvent()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hadEvent = false;
                itemtype = platformType.nothing;
                gameView.CurrentGoodEvent--;
            }
        },2000);
    }
    //khởi tạo các vật thể
    public void Setup(Resources resources)
    {
        lilypad = new Lilypad();
        lilypad.currentplatform = this;
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

        rock = new Rock();
        rock.currentplatform = this;
        rock.setX(this.x);
        rock.setY(this.y);
        rock.setWidth(260 * SCREEN.WIDTH / SCREEN.WIDTH);
        rock.setHeight(260 * SCREEN.HEIGHT / SCREEN.HEIGHT);
        rock.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.rock));
        platformtype = platformType.lilypad;

        fly = new Fly();
        fly.currentplatform = this;
        fly.setX(this.x);
        fly.setY(this.y);
        fly.setWidth(160 * SCREEN.WIDTH / SCREEN.WIDTH);
        fly.setHeight(160 * SCREEN.HEIGHT / SCREEN.HEIGHT);
        bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.fly1));
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.fly2));
        fly.setBitmaps(bitmaps);

        coin = new Coin();
        coin.currentplatform = this;
        coin.setX(this.x);
        coin.setY(this.y);
        coin.setWidth(160 * SCREEN.WIDTH / SCREEN.WIDTH);
        coin.setHeight(160 * SCREEN.HEIGHT / SCREEN.HEIGHT);
        coin.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.coin));
        itemtype = platformType.nothing;
    }
}
