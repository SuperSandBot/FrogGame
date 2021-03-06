package com.example.froggame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Switch;

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
    GameEvent gameEvent;
    Rock rock;
    Lilypad lilypad;
    Fly fly;
    Coin coin;
    boolean HadEvent = false;

    //xác định vị trí
    Platform right;
    Platform left;
    Platform top;
    Platform bot;

    //tool
    Handler handler;


    public Platform()
    {
        super();
        handler = new Handler();
    }

    public void LoadGame()
    {
        lilypad.Decaying();
        rock.StartEvent();
        fly.StartEvent();
        coin.StartEvent();
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

    public void StartLilyEvent()
    {
        lilypad.HadEvent = true;
        HadEvent = true;
        platformtype = platformType.lilypad;
    }
    public void StartRockEvent()
    {
        rock.HadEvent = true;
        HadEvent = true;
    }
    public void StartFlyEvent()
    {
        fly.HadEvent = true;
        HadEvent = true;
        itemtype = platformType.fly;
    }
    public void StartCoinEvent()
    {
        coin.HadEvent = true;
        HadEvent = true;
        itemtype = platformType.coin;
    }
    public void ResetLilyEvent()
    {
        HadEvent = false;
        lilypad.HadEvent = false;
        platformtype = platformType.lilypad;
    }
    public void ResetRockEvent()
    {
        HadEvent = false;
        rock.HadEvent = false;
        platformtype = platformType.lilypad;
    }
    public void ResetFlyEvent()
    {
        HadEvent = false;
        fly.HadEvent = false;
        itemtype = platformType.nothing;
        gameEvent.CurrentFly--;
        gameEvent.StartRamdomEvent();
    }
    public void ResetCoinEvent()
    {
        HadEvent = false;
        coin.HadEvent = false;
        itemtype = platformType.nothing;
    }

    //khởi tạo các vật thể
    public void Setup(Resources resources)
    {
        lilypad = new Lilypad();
        lilypad.currentplatform = this;
        lilypad.setX(this.x);
        lilypad.setY(this.y);
        lilypad.setWidth(SCREEN.HEIGHT/10);
        lilypad.setHeight(SCREEN.HEIGHT/10);
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
        rock.setWidth(SCREEN.HEIGHT/9);
        rock.setHeight(SCREEN.HEIGHT/9);
        rock.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.rock));
        platformtype = platformType.lilypad;

        fly = new Fly();
        fly.currentplatform = this;
        fly.setX(this.x);
        fly.setY(this.y);
        fly.setWidth(SCREEN.HEIGHT/16);
        fly.setHeight(SCREEN.HEIGHT/16);
        bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.fly1));
        bitmaps.add(BitmapFactory.decodeResource(resources,R.drawable.fly2));
        fly.setBitmaps(bitmaps);

        coin = new Coin();
        coin.currentplatform = this;
        coin.setX(this.x);
        coin.setY(this.y);
        coin.setWidth(SCREEN.HEIGHT/16);
        coin.setHeight(SCREEN.HEIGHT/16);
        coin.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.coin));
        itemtype = platformType.nothing;
    }
}
