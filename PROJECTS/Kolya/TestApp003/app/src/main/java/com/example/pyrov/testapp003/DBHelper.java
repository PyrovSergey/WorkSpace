package com.example.pyrov.testapp003;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.pyrov.testapp003.MainActivity.LOG_TAG;

// Класс для работы с БД
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");

        String nameDB = AddDataActivity.getNameDB();
        // создаем таблицу с полями
        sqLiteDatabase.execSQL("create table " + nameDB + " ("
                + "id integer primary key autoincrement,"
                + "date text,"
                + "data text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
