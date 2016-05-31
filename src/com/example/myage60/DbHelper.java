package com.example.myage60;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	static String DATABASE_NAME="alarmdata";
	public static final String TABLE_NAME="dailyalarms";
	public static final String C_MED="medicine";
	public static final String C_DES="description";
	public static final String C_HRS="t_hours";
	public static final String C_MIN="t_minutes";
	public static final String C_REP="t_repeat";
	public static final String C_INT="t_interval";
	public static final String KEY_ID="id";
	
	public static final String TABLE_NAME_AT="attendance";
	public static final String C_ID="_id";
	public static final String C_DAY="t_day";
	public static final String C_MNT="t_month";
	public static final String C_YR="t_year";
	public static final String C_VAL="value";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+C_MED+" TEXT, "+C_DES+" TEXT, "+C_HRS+" INT, "+C_MIN+" INT, "+C_REP+" INT, "+C_INT+" INT)";
		String CREATE_TABLE_AT="CREATE TABLE "+TABLE_NAME_AT+" ("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+C_DAY+" INT, "+C_MNT+" INT, "+C_YR+" INT, "+C_VAL+" TEXT DEFAULT NO)";
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_TABLE_AT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_AT);
		onCreate(db);

	}

}

/*
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "myalarms.db"; 
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "alarms";
	public static final String C_ID = "_id";
	public static final String C_MED = "medicine";
	public static final String C_DES = "description";
	public static final String C_HRS = "t_hours";
	public static final String C_MIN = "t_minutes";
	public static final String C_REP = "t_repeat";
	public static final String C_INT = "t_interval";

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = String.format("create table %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INT, %s INT, %s INT, %s INT)", TABLE_NAME, C_ID, C_MED, C_DES, C_HRS, C_MIN, C_REP, C_INT);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		db.execSQL("drop table if exists" + TABLE_NAME);
		this.onCreate(db);
		
	}

}
*/