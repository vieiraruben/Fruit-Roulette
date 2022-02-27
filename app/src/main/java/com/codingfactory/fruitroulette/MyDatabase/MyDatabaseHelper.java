package com.codingfactory.fruitroulette.MyDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.codingfactory.fruitroulette.R;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.jar.Attributes;
//Class that allows to interact with the DB which contains the scores
public class MyDatabaseHelper extends SQLiteOpenHelper {
    //List of constants that define the names of the different DB elements
    private final String TABLE_SCORE ="high_scores";
    private final String COLUMN_SCORE_ID ="score_id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_SCORE = "score";
    private final String COLUMN_ROUND = "round";

    // Constructor for databaseHelper
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    //Initialisation of the table containing highScores, 1 Query to create the table with
    //needed columns, one to insert initial data.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_SCORE + "("
                + COLUMN_SCORE_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_SCORE + " INTEGER, " + COLUMN_ROUND + " INTEGER )";
        db.execSQL(script);
        String init ="INSERT INTO " + TABLE_SCORE + " (name, score, round ) "+ "VALUES" + "('Didier', 20, 4) , ('Tomy',15, 2)";
        db.execSQL(init);
    }

    @Override
    //Allows for an uprade of the DB version, supressing old one and recreating from onCreate()
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Recreate
        onCreate(db);
    }

    @SuppressLint("Range")
    //Function to return all data in table score
    public ArrayList getAllScores() {
        //Stocking the DB in a variable to call it after
        SQLiteDatabase db = this.getReadableDatabase();
        //Array to stock data retrieved from DB
        ArrayList<String> array_list = new ArrayList<>();
        //Query to select all data in dec order
        Cursor res = db.rawQuery( "select * from "+TABLE_SCORE+" order by "+COLUMN_SCORE+ " desc " , null );
        //Start the cursor at the begining of table score
        res.moveToFirst();
        while(!res.isAfterLast()) {
            //adding all 3 column values from a row
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex(COLUMN_SCORE)));
            array_list.add(res.getString(res.getColumnIndex(COLUMN_ROUND)));
            //moving to next row
            res.moveToNext();

        }
        //returning data
        return array_list;
    }

    //Method to add a new row to table sore
    public void addHighscore (String pseudo, int highscore, int round){
        SQLiteDatabase db = this.getWritableDatabase();
        String newScore = "INSERT INTO " + TABLE_SCORE + "("+ COLUMN_NAME +","+COLUMN_SCORE+"," +COLUMN_ROUND+")"+ " VALUES " + "("+ "'"+pseudo+"'"+ ","+ highscore+","+round+")";

        db.execSQL(newScore);

    }


}
