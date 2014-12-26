package com.bandi.sharemytrip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TripDBHelper extends SQLiteOpenHelper 
{
	public static final String TABLE_TRIP = "TRIP";
	public static final String COLUMN_TRIP_NAME_PK = "_id";
	public static final String COLUMN_EST_TRIP_COST = "EST_TRIP_COST";
	public static final String COLUMN_TRIP_DESC = "TRIP_DESCRIPTION";

	private static final String DATABASE_NAME = "trip.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TRIP + "(" 
			+ COLUMN_TRIP_NAME_PK + " text primary key, " 
			+ COLUMN_TRIP_DESC + " text, " 
			+ COLUMN_EST_TRIP_COST + " real );";

	public TripDBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.w(TripDBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
		onCreate(db);
	}
}
