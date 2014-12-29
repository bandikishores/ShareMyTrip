package com.bandi.sharemytrip;

import com.bandi.misc.Misc;
import com.bandi.sharemytrip.dao.FriendDataSource;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
	}
	
	/** Called when the user clicks the Add Friend Button */
	public void addFriend(View view) 
	{
		String friendName = ((EditText) findViewById(R.id.friendNameEntered)).getText().toString();
		String contactNumber = ((EditText) findViewById(R.id.contactNumberEntered)).getText().toString();
		String address = ((EditText) findViewById(R.id.addressEntered)).getText().toString();
		String emailAddr = ((EditText) findViewById(R.id.emailAddressEntered)).getText().toString();
		
		if(Misc.isNullTrimmedString(friendName))
		{
			EditText tripNameEdit = ((EditText) findViewById(R.id.friendNameEntered));
			tripNameEdit.setTextColor(Color.RED);
			tripNameEdit.setHint("Friend Name Cannot be Blank");
			return;
		}
	    
		FriendDataSource datasource = new FriendDataSource(this);
	    datasource.open();
	    // KISH - TODO : Contact Id retreived from DB
	    datasource.createFriend(friendName, contactNumber, address, emailAddr, 0); 
	    datasource.close();
	    
	    // Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    	//startActivity(intent);
	    NavUtils.navigateUpFromSameTask(this); // So the previous parent is shown to user
    	finish();
    	
    	Toast.makeText(getApplicationContext(), "Friend "+ friendName +" Added Successfully.", Toast.LENGTH_SHORT).show();
    	
    	return;
	}
}
