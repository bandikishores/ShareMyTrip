package com.bandi.sharemytrip;

import com.bandi.misc.Constants;
import com.bandi.sharemytrip.dao.TripDataSource;
import com.bandi.sharemytrip.data.Trip;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_details);
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();

		populateTripDetails();
	}

	private void populateTripDetails() 
	{
		Intent intent = getIntent();
		String tripName = intent.getStringExtra(Constants.TRIP_NAME);
		
		TripDataSource datasource = new TripDataSource(this);
	    datasource.open();
	    Trip trip = datasource.getTrip(tripName);
	    datasource.close();
	    
	    ((TextView) findViewById(R.id.tripName)).setText(trip.getName());
	    ((TextView) findViewById(R.id.tripDesc)).setText(trip.getDescription());
	    ((TextView) findViewById(R.id.estCost)).setText(trip.getEstTripCost()+"");
	   // ((CalendarView) findViewById(R.id.createdDttm)).setDate(trip.getCreatedDttmAsDate().getTime());
	    ((TextView) findViewById(R.id.createdDttmAsString)).setText(trip.getCreatedDttm());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.addFriend) 
		{
			addFriend();
			return true;
		}
		else*/ 
		if (id == R.id.deleteTrip) 
		{
			deleteTrip();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called when the user clicks the Delete Trip Button */
	private void deleteTrip() 
	{
		String tripName = ((TextView) findViewById(R.id.tripName)).getText().toString();
		
		TripDataSource datasource = new TripDataSource(this);
	    datasource.open();
	    datasource.deleteTrip(tripName);
	    datasource.close();
	    
	    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    	// startActivity(intent);
    	NavUtils.navigateUpTo(this, intent);
    	finish(); // To remove this activity from history.
    	
    	Toast.makeText(getApplicationContext(), "Trip " + tripName + " Deleted Successfully.", Toast.LENGTH_SHORT).show();
    	
    	return;
	}
}
