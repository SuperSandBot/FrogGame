package com.example.froggame;

import android.os.Handler;
import android.util.Log;

import java.util.Random;

public class GameEvent {

    Frog frog;
    Platform[][] platforms;

    Random random;

    int num1,num2,eventNum;
    int MaxFly = 1;
    int CurrentFly = 0;

    public GameEvent()
    {
        random = new Random();

    }

    public synchronized Platform getRandomPlatform()
    {
        while(true)
        {
            num1 = random.nextInt(4);
            num2 = random.nextInt(4);
            if(platforms[num1][num2] == frog.currentplatform)
            {
                continue;
            }
            if(platforms[num1][num2].HadEvent)
            {
                continue;
            }

            return platforms[num1][num2];
        }
    }

    public synchronized void StartRamdomEvent()
    {
        Platform platform = getRandomPlatform();
        platform.HadEvent = true;
        if(CurrentFly < MaxFly)
        {
            platform.StartFlyEvent();
            CurrentFly++;
            return;
        }
        eventNum = random.nextInt(3);
        switch (eventNum)
        {
            case 0:
            {
                platform.StartLilyEvent();
                break;
            }
            case 1:
            {
                platform.StartRockEvent();
                break;
            }
            case 2:
            {
                platform.StartCoinEvent();
                break;
            }
        }
    }
}
