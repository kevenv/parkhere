package com.parkhere.main;

import java.util.UUID;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.parkhere.ChooseActivity;
import com.parkhere.R;
import com.parkhere.adapter.NavDrawerListAdapter;
import com.parkhere.drawerMenu.DrawerMenuClickListener;
import com.parkhere.drawerMenu.ISwitchFragments;
import com.parkhere.drawerMenu.NavDrawerItems;

public class MainActivity extends FragmentActivity implements ISwitchFragments{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private NavDrawerListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        configureDrawerMenu();
        configureDrawerMenuContent();
		
        if (savedInstanceState == null) {
            //selectItem(0);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ChooseActivity()).addToBackStack( "choose" ).commit();
	}


	private void configureDrawerMenuContent() {
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        
		NavDrawerItems navDrawerItems = new NavDrawerItems(getResources().getStringArray(R.array.nav_drawer_items));
		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerMenuClickListener(this));
	}


	private void configureDrawerMenu() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.burger_button);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.navigationbar));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_title, null);
        ImageView logoIcon = (ImageView)v.findViewById(R.id.logo);
        logoIcon.setImageResource(R.drawable.logo);

        getActionBar().setCustomView(v);
	}

	
	/*
	 * This method is Very FUCKING important
	 * If you don't implement it, the drawer button in the title
	 * doesn't work. be aware 
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {

        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


	@Override
	public void switchFragment(int position, Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment)
				.addToBackStack( UUID.randomUUID().toString())
				.commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
}
