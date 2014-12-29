package com.bandi.sharemytrip;

import java.util.List;

import com.bandi.misc.Category;
import com.bandi.misc.Constants;
import com.bandi.misc.Misc;
import com.bandi.sharemytrip.dao.TripDataSource;
import com.bandi.sharemytrip.data.Trip;
import com.bandi.sharemytrip.R;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private TripDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		// getAllTrips();
	}
	
	private void populateAllTrips() 
	{
		datasource = new TripDataSource(this);
	    datasource.open();

	    List<Trip> values = datasource.getAllTrips();

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
		    textView.setText("No Trips Available!!");
		    textView.setClickable(false);
		    
		    ll.addView(textView);
	    }
	    else
	    {
		    ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_1, values);
		    listView = (ListView)findViewById(R.id.triplist);
		    listView.setAdapter(adapter);
		    
		    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		    {
		        @Override
		        public void onItemClick(AdapterView<?> parent, final View view,
		            int position, long id) 
		        {
		         	final Trip trip = (Trip) parent.getItemAtPosition(position);
		         	
		          	try
		          	{
		            	Intent intent = new Intent(getApplicationContext(), TripDetailsActivity.class);
		            	intent.putExtra(Constants.TRIP_NAME, trip.getName());
		            	// intent.putExtra("UserView", userView);
		            	// EventBus.getDefault().postSticky(userView); // java.lang.NoClassDefFoundError: de.greenrobot.event.EventBus
		            	intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		            	startActivity(intent);
		          	}
		          	catch (Exception e) 
		          	{
						Log.e(Category.HOME_PAGE, " Object ", e);
					}
		        }
	
		      });
		    // setContentView(listView);
		    
		   // this.addContentView(view, params)
	    }
	    
	    datasource.close();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// setContentView(R.layout.activity_home);

		/*mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));*/
		
		populateAllTrips();
	}
	
	public void addTrip()
	{
		Intent intent = new Intent(getApplicationContext(), AddTripActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	startActivity(intent);
	}
	
	/*public void onClick(View view) 
	{
	    TripDataSource datasource = new TripDataSource(this);
	    datasource.open();
	   
	    switch (view.getId()) 
	    {
	    case R.id.add:
	    	addTrip();
        	return;
	    case R.id.delete:
	    	
	    	datasource.deleteAllTrips();
	      break;
	    }
	    

	    List<Trip> values = datasource.getAllTrips();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_1, values);
	    ListView listView = (ListView)findViewById(R.id.triplist);
	    listView.setAdapter(adapter);
	    datasource.close();
	  }*/

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.all_trips);
			break;
		case 2:
			mTitle = getString(R.string.all_friends);
			viewFriends();
			break;
		}
	}

	private void viewFriends() 
	{
		Intent intent = new Intent(getApplicationContext(), ViewFriendsActivity.class);
    	// intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	startActivity(intent);
    	// NavUtils.navigateUpTo(this, intent);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.home, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.addTrip) {
			addTrip();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		
		

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		
		@Override
		public void onResume()
		{
			super.onResume();
		}
	}

}
