package com.bandi.sharemytrip.database;

import com.bandi.misc.Category;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PaymentSummaryDBHelper extends DBHelper 
{
	public static final String TABLE_PAYMENT_SUMMARY = "PAYMENT_SUMMARY";
	public static final String COLUMN_PAYMENT_SUMMARY_ID_PK = "_id";
	public static final String COLUMN_EXPENDITURE_ID = "EXPENDITURE_ID";
	public static final String COLUMN_PAID_FOR_FRIEND_NAME = "PAID_FOR_FRIEND_NAME";
	public static final String COLUMN_CREATED_DTTM = "CREATED_DTTM";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_PAYMENT_SUMMARY + "(" 
			+ COLUMN_PAYMENT_SUMMARY_ID_PK + " integer primary key, " 
			+ COLUMN_EXPENDITURE_ID + " integer, "
			+ COLUMN_PAID_FOR_FRIEND_NAME + " text, "
			+ COLUMN_CREATED_DTTM + " DATETIME );";

	public PaymentSummaryDBHelper(Context context) 
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
		Log.w(Category.DB_HELPER, PaymentSummaryDBHelper.class.getName() +
				" Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_SUMMARY);
		onCreate(db);
	}
}
