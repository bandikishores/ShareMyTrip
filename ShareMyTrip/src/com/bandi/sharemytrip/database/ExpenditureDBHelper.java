package com.bandi.sharemytrip.database;

import com.bandi.misc.Category;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExpenditureDBHelper extends DBHelper 
{
	public static final String TABLE_EXPENDITURE = "EXPENDITURE";
	public static final String COLUMN_EXPENDITURE_ID_PK = "_id";
	public static final String COLUMN_PLACE_ID = "PLACE_ID";
	public static final String COLUMN_FRIEND_NAME = "FRIEND_NAME";
	public static final String COLUMN_COST = "COST";
	public static final String COLUMN_REASON = "REASON";
	public static final String COLUMN_CREATED_DTTM = "CREATED_DTTM";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_EXPENDITURE + "(" 
			+ COLUMN_EXPENDITURE_ID_PK + " integer primary key, " 
			+ COLUMN_PLACE_ID + " integer, "
			+ COLUMN_FRIEND_NAME + " text, "
			+ COLUMN_COST + " real, "
			+ COLUMN_REASON + " text, " 
			+ COLUMN_CREATED_DTTM + " DATETIME );";

	public ExpenditureDBHelper(Context context) 
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
		Log.w(Category.DB_HELPER, ExpenditureDBHelper.class.getName() +
				" Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENDITURE);
		onCreate(db);
	}
}
