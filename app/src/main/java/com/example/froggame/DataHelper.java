package com.example.froggame;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DataHelper extends SQLiteOpenHelper {
    public static final String key_id = "id";
    public static final String table_name = "Player_Best_Score";
    public static final String key_name = "Player_Name";
    public static final String key_score = "Best_Score";
    public static final String db_name = "froggame.db";
    public static final int version = 1;
    public static final String create_score_table = "CREATE TABLE " + table_name +
            "(" + key_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + key_name + " TEXT , "
            + key_score+ " INTEGER );";
    private static final String drop_score_table = "DROP TABLE IF EXISTS " + table_name;

    public DataHelper(Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_score_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(drop_score_table);
        onCreate(sqLiteDatabase);
    }
}
