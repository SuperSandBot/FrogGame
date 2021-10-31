package com.example.froggame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    public SoundPool soundPool;
    private int sfxcoin;
    private int sfxfrog;
    private int sfxlilypad;
    private int sfxbuttonclick;
    private int sfxgameover;

    public SoundPlayer(Context context)
    {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        sfxcoin = soundPool.load(context,R.raw.sfxcoin,1);
        sfxfrog = soundPool.load(context,R.raw.sfxfrog,1);
        sfxlilypad = soundPool.load(context,R.raw.sfxwatersurfacehit,1);
        sfxbuttonclick =soundPool.load(context,R.raw.sfxbuttonclick,1);
        sfxgameover = soundPool.load(context,R.raw.sfxgameover,1);

    }

    public void playsfxcoin()
    {
        soundPool.play(sfxcoin,1.0f,1.0f,1,0,1.0f);
    }

    public void playsfxfrog()
    {
        soundPool.play(sfxfrog,1.0f,1.0f,1,0,1.0f);
    }

    public void playsfxlilypad()
    {
        soundPool.play(sfxlilypad,1.0f,1.0f,1,0,1.0f);
    }

    public void playsfxbuttonclick()
    {
        soundPool.play(sfxbuttonclick,1.0f,1.0f,1,0,1.0f);
    }

    public void playsfxgameover()
    {
        soundPool.play(sfxgameover,1.0f,1.0f,1,0,1.0f);
    }
}
