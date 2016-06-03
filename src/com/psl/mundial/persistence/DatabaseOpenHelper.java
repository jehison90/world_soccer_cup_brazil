package com.psl.mundial.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper { 
	
	public static String DATABASE_NAME = "mundial.db";
	
	public static final String TABLE_SHEET = "sheet";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_COLOR = "color";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_SHEET + "(" + COLUMN_ID
	      + " INTEGER PRIMARY KEY, " + COLUMN_COLOR
	      + " TEXT NOT NULL)";
	
	public DatabaseOpenHelper(Context context) {
	    super(context, DATABASE_NAME, null, 1);
	  }


	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		// TODO Auto-generated method stub		
		dataBase.execSQL(DATABASE_CREATE);
		Log.d("create db", DATABASE_CREATE);
		
		for (int i = 0; i < 640; i++) {
			String insertSheet = "INSERT INTO " + TABLE_SHEET + " VALUES (" + i + ", 'WHITE')";
			dataBase.execSQL(insertSheet);
		}
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
