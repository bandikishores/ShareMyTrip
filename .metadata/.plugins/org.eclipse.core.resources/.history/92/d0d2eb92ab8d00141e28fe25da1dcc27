package com.sharemytrip;

import com.bandi.misc.Misc;
import com.bandi.sharemytrip.HomeActivity;
import com.bandi.sharemytrip.dao.TripDataSource;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddTripActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_trip);
	}
	
	/** Called when the user clicks the Add Trip Button */
	public void sendMessage(View view) 
	{
		String tripName = ((EditText) findViewById(R.id.tripNameEntered)).getText().toString();
		String tripDesc = ((EditText) findViewById(R.id.tripDescEntered)).getText().toString();
		String estTripCost = ((EditText) findViewById(R.id.estTripCostEntered)).getText().toString();
		
		if(Misc.isNullTrimmedString(tripName))
		{
			EditText tripNameEdit = ((EditText) findViewById(R.id.tripNameEntered));
			tripNameEdit.setTextColor(Color.RED);
			tripNameEdit.setHint("Trip Name Cannot be Blank");
			return;
		}
	    
	    float tripCost = 0.0F;
	    if(Misc.isFloat(estTripCost))
	    	tripCost = Float.parseFloat(estTripCost);
		
		TripDataSource datasource = new TripDataSource(this);
	    datasource.open();
	    
	    datasource.createTrip(tripName, tripDesc, tripCost);
	    
	    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    	startActivity(intent);
    	return;
	}
}
