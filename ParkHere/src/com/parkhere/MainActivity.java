package com.parkhere;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parkhere.adapter.NavDrawerListAdapter;
import com.parkhere.model.NavDrawerItem;



public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;


	// slide menu items
	private String[] navMenuTitles;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*Intent i = new Intent(MainActivity.this, ChooseActivity.class);
        startActivity(i);
        finish();*/

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Looking for Parking?
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.car));
		// By Location
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], R.drawable.menu_item_1));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], R.drawable.menu_item_2));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], R.drawable.location));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], R.drawable.menu_item_3));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], R.drawable.menu_item_4));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], R.drawable.user));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], R.drawable.menu_item_5));
		
		
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);


		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.burger_button, //nav menu toggle icon
				R.string.empty, // nav drawer open - description for accessibility
				R.string.empty // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
			//	getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.navigationbar) );
		getActionBar().setIcon(R.drawable.logo);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(false);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 1:
			fragment = new ChooseActivity();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager =	getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}


	
	}
	
}
