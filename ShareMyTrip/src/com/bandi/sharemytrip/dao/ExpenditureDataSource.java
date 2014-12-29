package com.bandi.sharemytrip.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bandi.misc.Category;
import com.bandi.misc.Misc;
import com.bandi.sharemytrip.data.Expenditure;
import com.bandi.sharemytrip.database.ExpenditureDBHelper;

public class ExpenditureDataSource 
{	
	// Database fields
	private SQLiteDatabase database;
	private ExpenditureDBHelper dbHelper;
	private String[] allColumns = 
		{ 
			ExpenditureDBHelper.COLUMN_EXPENDITURE_ID_PK,
			ExpenditureDBHelper.COLUMN_PLACE_ID, 
			ExpenditureDBHelper.COLUMN_FRIEND_NAME,
			ExpenditureDBHelper.COLUMN_COST,
			ExpenditureDBHelper.COLUMN_REASON,
			ExpenditureDBHelper.COLUMN_CREATED_DTTM
		};

	public ExpenditureDataSource(Context context) 
	{
		dbHelper = new ExpenditureDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Expenditure createExpenditure(Integer placeId, String friendName, double cost, String reason) 
	{
		ContentValues values = new ContentValues();
		values.put(ExpenditureDBHelper.COLUMN_PLACE_ID, placeId);
		values.put(ExpenditureDBHelper.COLUMN_FRIEND_NAME, friendName);
		values.put(ExpenditureDBHelper.COLUMN_COST, cost);
		values.put(ExpenditureDBHelper.COLUMN_REASON, reason);
		values.put(ExpenditureDBHelper.COLUMN_CREATED_DTTM, Misc.getCurrentDateAsString());
		
		database.insert(ExpenditureDBHelper.TABLE_EXPENDITURE, null, values);
		
		String whereClause = 
				ExpenditureDBHelper.COLUMN_PLACE_ID + " = " + placeId + " " +
						ExpenditureDBHelper.COLUMN_FRIEND_NAME + " = '" + friendName + "'";
		
		Cursor cursor = database.query(ExpenditureDBHelper.TABLE_EXPENDITURE,
				allColumns, 
				whereClause, null,
				null, null, null);
		
		
		
		cursor.moveToFirst();
		Expenditure newExpenditure = cursorToExpenditure(cursor);
		cursor.close();
		return newExpenditure;
	}

	public void deleteExpenditure(Integer placeId, String friendName) 
	{
		// System.out.println("Expenditure deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"Expenditure deleted with place id: " + placeId + " friend name : " + friendName);
		
		String whereClause = 
				ExpenditureDBHelper.COLUMN_PLACE_ID + " = " + placeId + " " +
						ExpenditureDBHelper.COLUMN_FRIEND_NAME + " = '" + friendName + "'";
		
		database.delete(ExpenditureDBHelper.TABLE_EXPENDITURE, 
				whereClause, 
				null);
	}

	public void deleteExpenditure(Integer id) 
	{
		// System.out.println("Expenditure deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"Expenditure deleted with id: "+id);
		
		String whereClause = 
				ExpenditureDBHelper.COLUMN_EXPENDITURE_ID_PK + " = " + id;
		
		database.delete(ExpenditureDBHelper.TABLE_EXPENDITURE, 
				whereClause, 
				null);
	}

	public void deleteExpenditure(Expenditure expenditure) 
	{
		deleteExpenditure(expenditure.getId());
	}

	public List<Expenditure> getAllExpenditures() 
	{
		List<Expenditure> expenditures = new ArrayList<Expenditure>();

		Cursor cursor = database.query(ExpenditureDBHelper.TABLE_EXPENDITURE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Expenditure expenditure = cursorToExpenditure(cursor);
			expenditures.add(expenditure);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return expenditures;
	}

	private Expenditure cursorToExpenditure(Cursor cursor) 
	{
		Expenditure expenditure = new Expenditure();
		expenditure.setId(cursor.getInt(0));
		expenditure.setPlaceId(cursor.getInt(1));
		expenditure.setFriendName(cursor.getString(2));
		expenditure.setCost(cursor.getDouble(3));
		expenditure.setReason(cursor.getString(4));
		expenditure.setCreatedDttm(cursor.getString(5));
		return expenditure;
	}
	
	public Expenditure getExpenditure(Integer placeId, String friendName) 
	{
		Expenditure expenditure = null;
		
		String selectQuery = "SELECT * FROM "+ ExpenditureDBHelper.TABLE_EXPENDITURE +
				" where "+ExpenditureDBHelper.COLUMN_PLACE_ID+"=" + placeId + " " +
						  ExpenditureDBHelper.COLUMN_FRIEND_NAME+"='" + friendName + "'" ;
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) 
		{
			expenditure = cursorToExpenditure(cursor);
			cursor.close();
		}
		return expenditure;
	}

	public void deleteAllExpenditures() 
	{
		Log.d(Category.DATA_SOURCE,"Deleting All Expenditures " );
		database.delete(ExpenditureDBHelper.TABLE_EXPENDITURE, 
				null, 
				null);
	}	

}
