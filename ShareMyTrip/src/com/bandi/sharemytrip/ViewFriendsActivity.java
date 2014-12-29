package com.bandi.sharemytrip;

import java.util.List;

import com.bandi.misc.Category;
import com.bandi.misc.Constants;
import com.bandi.misc.Misc;
import com.bandi.sharemytrip.dao.FriendDataSource;
import com.bandi.sharemytrip.data.Friend;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewFriendsActivity extends ActionBarActivity {

	private FriendDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_friends);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		populateAllFriends();
	}

	private void populateAllFriends() 
	{
		datasource = new FriendDataSource(this);
	    datasource.open();

	    List<Friend> values = datasource.getAllFriends();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    
	    /*
	    ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this,
	        android.R.layout.simple_list_item_1, values);
	    */
	    
	    ListView listView = null;//new ListView(this);
	    
	    if(Misc.isNullList(values))
	    {
	    	LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
		   	 
		    // Create the text view
		    TextView textView = new TextView(this);
		    textView.setText("No Friends Added!!");
		    textView.setClickable(false);
		    
		    ll.addView(textView);
	    }
	    else
	    {
		    ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(this, android.R.layout.simple_list_item_1, values);
		    listView = (ListView)findViewById(R.id.triplist);
		    listView.setAdapter(adapter);
		    
		    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		    {
		        @Override
		        public void onItemClick(AdapterView<?> parent, final View view,
		            int position, long id) 
		        {
		         	final Friend friend = (Friend) parent.getItemAtPosition(position);
		         	
		          	try
		          	{
		          		addFriend(friend.getName());
		          	}
		          	catch (Exception e) 
		          	{
						Log.e(Category.VIEW_FRIENDS, " Object ", e);
					}
		        }
	
		      });
	    }
	    
	    datasource.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_friends, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.addFriend) {
			addFriend(Misc.EMPTY_STRING);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void addFriend(String friendName) 
	{
	    Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
	    intent.putExtra(Constants.FRIEND_NAME, friendName);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // So the new activity will not be kept in stack
    	startActivity(intent);
    	// NavUtils.navigateUpTo(this, intent);
    	// finish(); // To remove this activity from history.
	}
}
