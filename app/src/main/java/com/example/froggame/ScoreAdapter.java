package com.example.froggame;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    private final List<PlayerScore> players;
    private final Activity activity;
    public ScoreAdapter(Activity activity, List<PlayerScore> players){
        this.activity = activity;
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int i) {
        return players.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.customlayout, null);
        TextView txtName = view.findViewById(R.id.txtPlayer);
        String txt = players.get(i).getPlayerName() + "     " +players.get(i).getBestScore();
        txtName.setText(txt);
        return view;
    }
}
