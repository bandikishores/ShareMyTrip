package com.bandi.sharemytrip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bandi.misc.Category;

public abstract class DBHelper extends SQLiteOpenHelper 
{
	private static final String DATABASE_NAME = "trip.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(TripDBHelper.DATABASE_CREATE);
		database.execSQL(FriendDBHelper.DATABASE_CREATE);
		database.execSQL(PlaceDBHelper.DATABASE_CREATE);
		database.execSQL(ExpenditureDBHelper.DATABASE_CREATE);
		database.execSQL(PaymentSummaryDBHelper.DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.w(Category.DB_HELPER, TripDBHelper.class.getName() +
				" Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY);
		db.execSQL("DROP TABLE IF EXISTS " + ExpenditureDBHelper.TABLE_EXPENDITURE);
		db.execSQL("DROP TABLE IF EXISTS " + PlaceDBHelper.TABLE_PLACE);
		db.execSQL("DROP TABLE IF EXISTS " + FriendDBHelper.TABLE_FRIEND);
		db.execSQL("DROP TABLE IF EXISTS " + TripDBHelper.TABLE_TRIP);
		
		onCreate(db);
	}
}
