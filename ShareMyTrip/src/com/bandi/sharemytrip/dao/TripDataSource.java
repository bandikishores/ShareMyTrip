package com.bandi.sharemytrip.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bandi.sharemytrip.data.Trip;
import com.bandi.sharemytrip.database.TripDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TripDataSource 
{
	private static final String LOGCAT = null;
	
	// Database fields
	private SQLiteDatabase database;
	private TripDBHelper dbHelper;
	private String[] allColumns = 
		{ 
			TripDBHelper.COLUMN_TRIP_NAME_PK,
			TripDBHelper.COLUMN_TRIP_DESC, 
			TripDBHelper.COLUMN_EST_TRIP_COST 
		};

	public TripDataSource(Context context) 
	{
		dbHelper = new TripDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Trip createTrip(String tripName, String tripDesc, double estCost) 
	{
		ContentValues values = new ContentValues();
		values.put(TripDBHelper.COLUMN_TRIP_DESC, tripDesc);
		values.put(TripDBHelper.COLUMN_TRIP_NAME_PK, tripName);
		values.put(TripDBHelper.COLUMN_EST_TRIP_COST, estCost);
		
		long insertId = database.insert(TripDBHelper.TABLE_TRIP, null, values);
		Cursor cursor = database.query(TripDBHelper.TABLE_TRIP,
				allColumns, TripDBHelper.COLUMN_TRIP_NAME_PK + " = '" + tripName + "'", null,
				null, null, null);
		cursor.moveToFirst();
		Trip newTrip = cursorToTrip(cursor);
		cursor.close();
		return newTrip;
	}

	public void deleteTrip(Trip trip) 
	{
		String id = trip.getId();
		// System.out.println("Trip deleted with id: " + id);
		Log.d(LOGCAT,"Trip deleted with id: " + id);
		
		database.delete(TripDBHelper.TABLE_TRIP, 
				TripDBHelper.COLUMN_TRIP_NAME_PK + " = '" + id + "'", 
				null);
	}

	public List<Trip> getAllTrips() 
	{
		List<Trip> trips = new ArrayList<Trip>();

		Cursor cursor = database.query(TripDBHelper.TABLE_TRIP,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Trip trip = cursorToTrip(cursor);
			trips.add(trip);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return trips;
	}

	private Trip cursorToTrip(Cursor cursor) 
	{
		Trip trip = new Trip();
		trip.setId(cursor.getString(0));
		trip.setDescription(cursor.getString(1));
		trip.setEstTripCost(cursor.getDouble(2));
		return trip;
	}
	
	public Trip getTrip(String tripName) 
	{
		String selectQuery = "SELECT * FROM Trip where "+TripDBHelper.COLUMN_TRIP_NAME_PK+"='" + tripName + "'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) 
		{
			Trip trip = cursorToTrip(cursor);
			cursor.close();
			return trip;
		}
		return null;
	}

	public void deleteAllTrips() 
	{
		Log.d(LOGCAT,"Deleting All Trips " );
		database.delete(TripDBHelper.TABLE_TRIP, 
				null, 
				null);
	}	

}
