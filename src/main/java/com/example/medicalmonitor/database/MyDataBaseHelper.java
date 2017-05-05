package com.example.medicalmonitor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by tiejiang on 17-5-5.
 */
// 已经通过assets文件夹加载数据库，此类废弃
public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    public static final String data = "create table data ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "pulse text) ";

//    public static final String CLASS_11 = "create table class_11 ("
//            + "id integer primary key autoincrement, "
//            + "start_year integer, "
//            + "college integer, "
//            + "class integer, "
//            + "student_id integer, "
//            + "student_name text) ";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(data);
//        sqLiteDatabase.execSQL(CLASS_11);
        Toast.makeText(mContext, "database create succeed !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}