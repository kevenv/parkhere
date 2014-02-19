package com.parkhere.drawerMenu;

import java.util.ArrayList;

import com.parkhere.R;
import com.parkhere.model.NavDrawerItem;

public class NavDrawerItems extends ArrayList<NavDrawerItem> {

	private static final long serialVersionUID = 1L;
	public NavDrawerItems(String[] navMenuTitles) {
		add(new NavDrawerItem(navMenuTitles[0], R.drawable.car));
		add(new NavDrawerItem(navMenuTitles[1], R.drawable.menu_item_1));
		add(new NavDrawerItem(navMenuTitles[2], R.drawable.menu_item_2));
		add(new NavDrawerItem(navMenuTitles[3], R.drawable.location));
		add(new NavDrawerItem(navMenuTitles[4], R.drawable.menu_item_3));
		add(new NavDrawerItem(navMenuTitles[5], R.drawable.menu_item_4));
		add(new NavDrawerItem(navMenuTitles[6], R.drawable.user));
		add(new NavDrawerItem(navMenuTitles[7], R.drawable.menu_item_5));
	}
	
}
