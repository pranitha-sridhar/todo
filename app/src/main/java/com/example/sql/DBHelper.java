package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NAMES_Table = "TASKS";
    public static final String Title_name = "Title";
    public static final String DESC_name = "Description";
    public static final String DATE = "Date";
    public static final String Time = "Time";
    public static final String ID = "Id";

    public DBHelper(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE " + NAMES_Table + "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + Title_name + " TEXT, " +
                "" + DESC_name + " TEXT, " +
                ""+DATE+" TEXT, "+
                ""+Time+" TEXT ) ";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAMES_Table);
        onCreate(db);
    }

    public boolean add(Model model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(Title_name,model.getTitle());
        cv.put(DESC_name,model.getDesc());
        cv.put(DATE,model.getDate());
        cv.put(Time,model.getTime());
        long insert = sqLiteDatabase.insert(NAMES_Table, null, cv);
        sqLiteDatabase.close();
        if (insert==-1)return false;
        else return true;
    }

    public boolean update(Model model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(Title_name,model.getTitle());
        cv.put(DESC_name,model.getDesc());
        cv.put(DATE,model.getDate());
        cv.put(Time,model.getTime());
        long update = sqLiteDatabase.update(NAMES_Table, cv,ID+" = "+model.getId(),null);
        sqLiteDatabase.close();
        if (update==-1)return false;
        else return true;
    }

    public ArrayList<Model> show(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Model> models=new ArrayList<>();
        String display="SELECT * FROM " + NAMES_Table;
        Cursor cursor=sqLiteDatabase.rawQuery(display,null);
        if(cursor.moveToFirst()){
            do {
                models.add(new Model(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(0)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return models;
    }

    public void delete(Model model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(NAMES_Table,ID+"=?", new String[]{String.valueOf(model.getId())});
        sqLiteDatabase.close();
    }
}
