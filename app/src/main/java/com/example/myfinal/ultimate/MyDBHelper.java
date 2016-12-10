package com.example.myfinal.ultimate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nithya on 09-Dec-16.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "mydb.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "employees";
    public static final String ID = "_id";
    public static final String First_NAME = "firstname";
    public static final String Last_NAME = "lastname";
    public static final String Salary = "salary";
    public static final String Address = "address";


   private SQLiteDatabase db;



    public MyDBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//BE CAREFUL FOR THE SYNTAX
        String query =  "CREATE TABLE " + TABLE_NAME +
                " (" +
                 ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                First_NAME + " TEXT NOT NULL, " +
                 Last_NAME +" TEXT NOT NULL, " +
                Salary + " REAL NOT NULL, " +
                Address + " TEXT NOT NULL " +
                ")" ;


        sqLiteDatabase.execSQL(query);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB(){
        db = getWritableDatabase();


    }

    public void closeDB() {

        if (db != null && db.isOpen()) {
            db.close();

        }
    }

    public long insert(int id,String fname,String lname,double salary,String address){

        ContentValues values = new ContentValues();
        if(id!=-1)
            values.put(ID,id);

        values.put(First_NAME,fname);
        values.put(Last_NAME,lname);
        values.put(Salary,salary);
        values.put(Address,address);


       return db.insert(TABLE_NAME,null,values);


    }public long update(int id,String fname,String lname,double salary,String address){

        ContentValues values = new ContentValues();

        values.put(First_NAME,fname);
        values.put(Last_NAME,lname);
        values.put(Salary,salary);
        values.put(Address,address);


        String where = ID + "  -   " + id;

        return db.update(TABLE_NAME,values,where,null);


    }
    public long delete(int id){


        String where = ID + " = " + id;

        return db.delete(TABLE_NAME,where,null);


    }

    public Cursor getAllRecords(){


//       return db.query(TABLE_NAME,null,null,null,null,null,null);



        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }




}


