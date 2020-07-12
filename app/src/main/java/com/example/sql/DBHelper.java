package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NAMES_Table = "NAMES";
    public static final String USER_name = "Name";
    public static final String ID = "Id";

    public DBHelper(@Nullable Context context) {
        super(context, "name.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE " + NAMES_Table + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_name + " TEXT)";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean add(Model model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(USER_name,model.getTitle());
        long insert = sqLiteDatabase.insert(NAMES_Table, null, cv);
        if (insert==-1)return false;
        else return true;
    }

    public ArrayList<Model> show(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Model> models=new ArrayList<>();
        String display="SELECT * FROM " + NAMES_Table;
        Cursor cursor=sqLiteDatabase.rawQuery(display,null);
        if(cursor.moveToFirst()){
            do {
                String nametext=cursor.getString(1);
                Model model=new Model(nametext,true);
                models.add(model);

            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return models;
    }

    public void delete(Model model){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String deletes="DELETE FROM "+NAMES_Table+" WHERE "+USER_name+" = "+model.getTitle();
        //Cursor cursor = sqLiteDatabase.rawQuery(deletes, null);
        //if (cursor.moveToFirst()) {}
        sqLiteDatabase.execSQL(deletes);
        sqLiteDatabase.close();
    }
}
