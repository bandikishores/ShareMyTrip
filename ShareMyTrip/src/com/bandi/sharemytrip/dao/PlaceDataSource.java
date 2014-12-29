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
import com.bandi.sharemytrip.data.Place;
import com.bandi.sharemytrip.database.PlaceDBHelper;

public class PlaceDataSource 
{	
	// Database fields
	private SQLiteDatabase database;
	private PlaceDBHelper dbHelper;
	private String[] allColumns = 
		{ 
			PlaceDBHelper.COLUMN_PLACE_ID_PK,
			PlaceDBHelper.COLUMN_NAME, 
			PlaceDBHelper.COLUMN_TRIP_NAME,
			PlaceDBHelper.COLUMN_PLACE_DESC,
			PlaceDBHelper.COLUMN_CREATED_DTTM
		};

	public PlaceDataSource(Context context) 
	{
		dbHelper = new PlaceDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Place createPlace(String placeName, String tripName, String placeDesc) 
	{
		ContentValues values = new ContentValues();
		values.put(PlaceDBHelper.COLUMN_NAME, placeName);
		values.put(PlaceDBHelper.COLUMN_TRIP_NAME, tripName);
		values.put(PlaceDBHelper.COLUMN_PLACE_DESC, placeDesc);
		values.put(PlaceDBHelper.COLUMN_CREATED_DTTM, Misc.getCurrentDateAsString());
		
		database.insert(PlaceDBHelper.TABLE_PLACE, null, values);
		
		String whereClause = 
				PlaceDBHelper.COLUMN_NAME + " = '" + placeName + "'" +
				PlaceDBHelper.COLUMN_TRIP_NAME + " = '" + tripName + "'";
		
		Cursor cursor = database.query(PlaceDBHelper.TABLE_PLACE,
				allColumns, 
				whereClause, null,
				null, null, null);
		
		cursor.moveToFirst();
		Place newPlace = cursorToPlace(cursor);
		cursor.close();
		return newPlace;
	}

	public void deletePlace(String placeName) 
	{
		// System.out.println("Place deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"Place deleted with id: " + placeName);
		
		database.delete(PlaceDBHelper.TABLE_PLACE, 
				PlaceDBHelper.COLUMN_NAME + " = '" + placeName + "'", 
				null);
	}

	public void deletePlace(String placeName, String tripName) 
	{
		// System.out.println("Place deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"Place deleted with place Name: " + placeName + " trip name: " + tripName);
		
		String whereClause = 
				PlaceDBHelper.COLUMN_NAME + " = '" + placeName + "'" +
				PlaceDBHelper.COLUMN_TRIP_NAME + " = '" + tripName + "'";
		
		database.delete(PlaceDBHelper.TABLE_PLACE, 
				whereClause, 
				null);
	}

	public void deletePlace(Integer id) 
	{
		// System.out.println("Place deleted with id: " + id);
		Log.d(Category.DATA_SOURCE,"Place deleted with id: "+id);
		
		String whereClause = 
				PlaceDBHelper.COLUMN_PLACE_ID_PK + " = " + id;
		
		database.delete(PlaceDBHelper.TABLE_PLACE, 
				whereClause, 
				null);
	}

	public void deletePlace(Place place) 
	{
		deletePlace(place.getId());
	}

	public List<Place> getAllPlaces() 
	{
		List<Place> places = new ArrayList<Place>();

		Cursor cursor = database.query(PlaceDBHelper.TABLE_PLACE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) 
		{
			Place place = cursorToPlace(cursor);
			places.add(place);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return places;
	}

	private Place cursorToPlace(Cursor cursor) 
	{
		Place place = new Place();
		place.setId(cursor.getInt(0));
		place.setName(cursor.getString(1));
		place.setTripName(cursor.getString(2));
		place.setPlaceDesc(cursor.getString(3));
		place.setCreatedDttm(cursor.getString(4));
		return place;
	}
	
	public Place getPlace(String placeName) 
	{
		Place place = null;
		String selectQuery = "SELECT * FROM "+ PlaceDBHelper.TABLE_PLACE +" where "+PlaceDBHelper.COLUMN_NAME+"='" + placeName + "'";
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) 
		{
			place = cursorToPlace(cursor);
			cursor.close();
		}
		return place;
	}
	
	public Place getPlace(String placeName, String tripName) 
	{
		Place place = null;
		String selectQuery = "SELECT * FROM "+ PlaceDBHelper.TABLE_PLACE +
				" where "+PlaceDBHelper.COLUMN_NAME+"='" + placeName + "'" +
						  PlaceDBHelper.COLUMN_TRIP_NAME+"='" + tripName + "'" ;
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) 
		{
			place = cursorToPlace(cursor);
			cursor.close();
		}
		return place;
	}

	public void deleteAllPlaces() 
	{
		Log.d(Category.DATA_SOURCE,"Deleting All Places " );
		database.delete(PlaceDBHelper.TABLE_PLACE, 
				null, 
				null);
	}	

}
