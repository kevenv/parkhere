package com.parkhere;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.internal.ac;
import com.parkhere.adapter.NavDrawerListAdapter;
import com.parkhere.model.NavDrawerItem;



public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;


	// slide menu items
	private String[] navMenuTitles;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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


		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.burger_button,  /* nav drawer icon to replace 'Up' caret */
                R.string.empty,  /* "open drawer" description */
                R.string.empty  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        
        ActionBar actionBar = getActionBar();
        //try to remove the green icon
        //actionBar.setDisplayGreenShit(false);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.navigationbar) );
        actionBar.setIcon(R.drawable.fuckinblue);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_title, null);
        ImageView burgerIcon = (ImageView)v.findViewById(R.id.burgerIcon);
        burgerIcon.setImageResource(R.drawable.logo);

        actionBar.setCustomView(v);
        
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getFragmentManager().beginTransaction().replace(R.id.frame_container, new ChooseActivity()).addToBackStack( "choose" ).commit();
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
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
					.replace(R.id.frame_container, fragment).addToBackStack( "choose2" ).commit();

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
