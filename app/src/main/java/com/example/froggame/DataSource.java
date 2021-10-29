package com.example.froggame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private SQLiteDatabase database;
    private final DataHelper dbHelper;
    private final String[] allColumns = {DataHelper.key_id, DataHelper.key_name, DataHelper.key_score};
    private static DataSource INSTANCE = null;

    private DataSource(Context context){
        dbHelper = new DataHelper(context);
    }

    public static DataSource getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new DataSource(context);
            INSTANCE.open();
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void addPlayer(PlayerScore player) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.key_name, player.getPlayerName());
        values.put(DataHelper.key_score, player.getBestScore());

        database.insert(DataHelper.table_name, null, values);
    }

    public PlayerScore getPlayer(String playerName) {

        try (Cursor cursor = database.query(DataHelper.table_name, null, DataHelper.key_name + " = ?",
                new String[] { String.valueOf(playerName) },null, null, null)){
            if(cursor == null || cursor.getCount() <= 0)
                return null;
            cursor.moveToFirst();
            return cursorToPlayerScore(cursor);
        }
    }

    public List<PlayerScore> getAllPlayer() {
        List<PlayerScore>  playerScoreList = new ArrayList<>();
        Cursor cursor = database.query(DataHelper.table_name, allColumns,
                null, null, null, null,null );
        if (cursor == null)
            return null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PlayerScore playerScore = cursorToPlayerScore(cursor);
            playerScoreList.add(playerScore);
            cursor.moveToNext();
        }
        cursor.close();
        return playerScoreList;
    }

    public void clearALlPlayer(){
        database.delete(DataHelper.table_name, null, null);
    }

    private PlayerScore cursorToPlayerScore(Cursor cursor){
        PlayerScore playerScore = new PlayerScore();
        playerScore.setPlayerName(cursor.getString(1));
        playerScore.setBestScore(cursor.getInt(2));
        return playerScore;
    }

    public void updatePlayerScore(PlayerScore playerScore) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.key_score, playerScore.getBestScore());
        values.put(DataHelper.key_name, playerScore.getPlayerName());
        database.update(DataHelper.table_name, values, DataHelper.key_name + " = ?",
                new String[]{String.valueOf(playerScore.getPlayerName())});
    }
}
