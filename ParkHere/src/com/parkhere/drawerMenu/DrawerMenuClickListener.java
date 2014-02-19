package com.parkhere.drawerMenu;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.parkhere.ChooseActivity;

public class DrawerMenuClickListener implements OnItemClickListener {

	private static final int FIND_PARKING_BY_LOCATION = 1;
	private ISwitchFragments fragmentsSwitcher;
	
	public DrawerMenuClickListener(ISwitchFragments fragmentsSwitcher) {
		this.fragmentsSwitcher = fragmentsSwitcher;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		displayView(position);
	}
	
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
			case FIND_PARKING_BY_LOCATION:
				fragment = new ChooseActivity();
				break;
	
			default:
				break;
		}

		if (fragment != null) {
			fragmentsSwitcher.switchFragment(position, fragment);
		} else {
			Log.e("DrawerMenuClickListener", "Error in creating fragment");
		}
	}

}
