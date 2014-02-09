package com.parkhere.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parkhere.R;
import com.parkhere.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


    		if(position == 0 ){
                convertView = mInflater.inflate(R.layout.drawer_list_item_first, null);
    			ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
    			imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
    			TextView textView = (TextView)convertView.findViewById(R.id.title);
    			textView.setText(navDrawerItems.get(position).getTitle());	
    			
    			ListView listView = (ListView) convertView.findViewById(R.id.list_slidermenu);
    			int[] colors = {0, 0x00000000, 0}; 
    			//listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
    			
    			ColorDrawable colorDrawable = new ColorDrawable(android.graphics.Color.WHITE ) ;
//    			listView.setDivider( colorDrawable );
            
    		}else if( position == 3 || position == 6){
                convertView = mInflater.inflate(R.layout.drawer_list_item_white, null);
    			ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
    			imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
    			TextView textView = (TextView)convertView.findViewById(R.id.title);
    			textView.setText(navDrawerItems.get(position).getTitle());	
    			
    			ListView listView = (ListView) convertView.findViewById(R.id.list_slidermenu);
    			int[] colors = {0, 0x00000000, 0}; 
    			//listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
    			
    			ColorDrawable colorDrawable = new ColorDrawable(android.graphics.Color.WHITE ) ;
//    			listView.setDivider( colorDrawable );
    			

    		}else{
    			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
    			TextView textView = (TextView)convertView.findViewById(R.id.title);
    			textView.setText(navDrawerItems.get(position).getTitle());	
    			
    			ListView listView = (ListView) convertView.findViewById(R.id.list_slidermenu);
    			int[] colors = {0, 0xFFFFFF00, 0}; // red for the example
    			//listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));

    			//  			listView.setDivider(new ColorDrawable(android.graphics.Color.BLACK ) );

    		}
			
			

		}

        return convertView;
	}

}
