package com.bandi.sharemytrip.database;

import com.bandi.misc.Category;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PlaceDBHelper extends DBHelper
{
	public static final String TABLE_PLACE = "PLACE";
	public static final String COLUMN_PLACE_ID_PK = "_id";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_TRIP_NAME = "TRIP_NAME";
	public static final String COLUMN_PLACE_DESC = "PLACE_DESC";
	public static final String COLUMN_CREATED_DTTM = "CREATED_DTTM";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_PLACE + "(" 
			+ COLUMN_PLACE_ID_PK + " integer primary key, " 
			+ COLUMN_NAME + " text, "
			+ COLUMN_TRIP_NAME + " text, " 
			+ COLUMN_PLACE_DESC + " text, " 
			+ COLUMN_CREATED_DTTM + " DATETIME );";

	public PlaceDBHelper(Context context) 
	{
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.w(Category.DB_HELPER, PlaceDBHelper.class.getName()+
				" Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
		onCreate(db);
	}
}
