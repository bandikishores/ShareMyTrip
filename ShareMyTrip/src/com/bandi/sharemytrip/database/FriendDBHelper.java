package com.bandi.sharemytrip.database;

import com.bandi.misc.Category;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FriendDBHelper extends DBHelper
{
	public static final String TABLE_FRIEND = "FRIEND";
	public static final String COLUMN_FRIEND_NAME_PK = "_id";
	public static final String COLUMN_NUMBER = "NUMBER";
	public static final String COLUMN_ADDRESS = "ADDRESS";
	public static final String COLUMN_EMAIL = "EMAIL";
	public static final String COLUMN_CONTACT_ID = "CONTACT_ID";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_FRIEND + "(" 
			+ COLUMN_FRIEND_NAME_PK + " text primary key, " 
			+ COLUMN_NUMBER + " text, "
			+ COLUMN_ADDRESS + " text, "
			+ COLUMN_EMAIL + " text, "
			+ COLUMN_CONTACT_ID + " integer );";

	public FriendDBHelper(Context context) 
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
		Log.w(Category.DB_HELPER, FriendDBHelper.class.getName() +
				" Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
		onCreate(db);
	}
}
