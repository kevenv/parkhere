package com.parkhere;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerActivity extends Fragment {
	
	private SupportMapFragment fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_timer, container, false);
        
        //MapFragment findViewById = (MapFragment) getFragmentManager().findFragmentById(R.id.mapTimer);
        ImageView payForTheSpot = (ImageView)rootView.findViewById(R.id.payForTheSpot);
        final TextView timer = (TextView)rootView.findViewById(R.id.textOfTimer);
        
        new CountDownTimer(1000 * 60 * 10, 1000) {
		     public void onTick(long millisUntilFinished) {
		         long minutes = millisUntilFinished / (1000 * 60);
		         if(millisUntilFinished == 1000 * 60 * 10) {
		        	 timer.setText("10:00");
			     }
		         
		         timer.setText("0" + minutes + ":" + padSeconds(millisUntilFinished % (1000 * 60)));
		     }
		     
		     public String padSeconds(long seconds){
		    	 String result = "" + seconds;
		    	 if(result.length()>2){
			         return result.substring(0, 2);
			     }
		    	 return result;
		     }
		     
		     public void onFinish() {
		         timer.setText("00:00");
		     }
		  }.start();
        
        payForTheSpot.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.frame_container, new ChooseActivity()).addToBackStack( "choose" ).commit();
			}
		});
        return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    FragmentManager fm = getChildFragmentManager();
	    fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapTimer);
	    if (fragment == null) {
	        fragment = SupportMapFragment.newInstance();
	        fm.beginTransaction().replace(R.id.mapTimer, fragment).commit();
	    }
	}
	
	@Override
	public void onResume() {
		super.onResume();
        LatLng position = new LatLng(45.504092,-73.619416);
        GoogleMap map = fragment.getMap();
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmall))
        .position(position));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
	}
	
	public void onDestroyView() {
	   super.onDestroyView();   
	   FragmentTransaction ft = getFragmentManager().beginTransaction();
	   ft.remove(fragment);
	   ft.commit();
	}
	
}
