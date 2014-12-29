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
import com.bandi.sharemytrip.data.PaymentSummary;
import com.bandi.sharemytrip.database.PaymentSummaryDBHelper;

public class PaymentSummaryDataSource 
{	
	// Database fields
	private SQLiteDatabase database;
	private PaymentSummaryDBHelper dbHelper;
	private String[] allColumns = 
		{ 
			PaymentSummaryDBHelper.COLUMN_PAYMENT_SUMMARY_ID_PK,
			PaymentSummaryDBHelper.COLUMN_EXPENDITURE_ID, 
			PaymentSummaryDBHelper.COLUMN_PAID_FOR_FRIEND_NAME,
			PaymentSummaryDBHelper.COLUMN_CREATED_DTTM
		};

	public PaymentSummaryDataSource(Context context) 
	{
		dbHelper = new PaymentSummaryDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public PaymentSummary createPaymentSummary(Integer expenditureId, String friendName) 
	{
		ContentValues values = new ContentValues();
		values.put(PaymentSummaryDBHelper.COLUMN_EXPENDITURE_ID, expenditureId);
		values.put(PaymentSummaryDBHelper.COLUMN_PAID_FOR_FRIEND_NAME, friendName);
		values.put(PaymentSummaryDBHelper.COLUMN_CREATED_DTTM, Misc.getCurrentDateAsString());
		
		database.insert(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY, null, values);
		
		String whereClause = 
				PaymentSummaryDBHelper.COLUMN_EXPENDITURE_ID + " = " + expenditureId + " " +
						PaymentSummaryDBHelper.COLUMN_PAID_FOR_FRIEND_NAME + " = '" + friendName + "'";
		
		Cursor cursor = database.query(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY,
				allColumns, 
				whereClause, null,
				null, null, null);
		
		
		
		cursor.moveToFirst();
		PaymentSummary newPaymentSummary = cursorToPaymentSummary(cursor);
		cursor.close();
		return newPaymentSummary;
	}

	public void deletePaymentSummary(Integer expenditureId, String friendName) 
	{
		// System.out.println("PaymentSummary deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"PaymentSummary deleted with Expenditure id: " + expenditureId + " friend name : " + friendName);
		
		String whereClause = 
				PaymentSummaryDBHelper.COLUMN_EXPENDITURE_ID + " = " + expenditureId + " " +
						PaymentSummaryDBHelper.COLUMN_PAID_FOR_FRIEND_NAME + " = '" + friendName + "'";
		
		database.delete(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY, 
				whereClause, 
				null);
	}

	public void deletePaymentSummary(Integer id) 
	{
		// System.out.println("PaymentSummary deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"PaymentSummary deleted with id: "+id);
		
		String whereClause = 
				PaymentSummaryDBHelper.COLUMN_PAYMENT_SUMMARY_ID_PK + " = " + id;
		
		database.delete(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY, 
				whereClause, 
				null);
	}

	public void deletePaymentSummary(PaymentSummary paymentSummary) 
	{
		deletePaymentSummary(paymentSummary.getId());
	}

	public List<PaymentSummary> getAllPaymentSummarys() 
	{
		List<PaymentSummary> paymentSummarys = new ArrayList<PaymentSummary>();

		Cursor cursor = database.query(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			PaymentSummary paymentSummary = cursorToPaymentSummary(cursor);
			paymentSummarys.add(paymentSummary);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return paymentSummarys;
	}

	private PaymentSummary cursorToPaymentSummary(Cursor cursor) 
	{
		PaymentSummary paymentSummary = new PaymentSummary();
		paymentSummary.setId(cursor.getInt(0));
		paymentSummary.setExpenditureId(cursor.getInt(1));
		paymentSummary.setPaidForFriendName(cursor.getString(2));
		paymentSummary.setCreatedDttm(cursor.getString(3));
		return paymentSummary;
	}
	
	public PaymentSummary getPaymentSummary(Integer expenditureId, String friendName) 
	{
		PaymentSummary paymentSummary = null;
		
		String selectQuery = "SELECT * FROM "+ PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY +
				" where "+PaymentSummaryDBHelper.COLUMN_EXPENDITURE_ID+"=" + expenditureId + " " +
						  PaymentSummaryDBHelper.COLUMN_PAID_FOR_FRIEND_NAME+"='" + friendName + "'" ;
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) 
		{
			paymentSummary = cursorToPaymentSummary(cursor);
			cursor.close();
		}
		return paymentSummary;
	}

	public void deleteAllPaymentSummarys() 
	{
		Log.d(Category.DATA_SOURCE,"Deleting All PaymentSummarys " );
		database.delete(PaymentSummaryDBHelper.TABLE_PAYMENT_SUMMARY, 
				null, 
				null);
	}	

}
