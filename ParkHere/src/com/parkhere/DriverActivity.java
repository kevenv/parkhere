package com.parkhere;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverActivity extends FragmentActivity implements LocationListener{

	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver);
		
		// Get a handle to the Map Fragment
		SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
		map = fm.getMap(); 
        
        //map.setMyLocationEnabled(true);
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, true);
        
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        
        if(lastKnownLocation!=null){
            onLocationChanged(lastKnownLocation);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 1, this);
        
        LatLng hec = new LatLng(45.504092,-73.619416);
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(hec));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(hec, 16));
        manageShowPanel();
        manageShowAvailability();
	}
	//#dont try this at home
	public void manageShowAvailability() {
		final boolean menuIsOpen = false;;
		ImageView selector = (ImageView) findViewById(R.id.selector);
		final ImageView menuOpen = (ImageView) findViewById(R.id.selector_menu_open);
		final ImageView selectedOption = (ImageView) findViewById(R.id.selector_selected);
		selector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuOpen.setVisibility(View.VISIBLE);
			}
		});
		
		menuOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuOpen.setVisibility(View.INVISIBLE);
				selectedOption.setVisibility(View.VISIBLE);
			}
		});
	}
	
	public void manageShowPanel() {
        final RelativeLayout filterPanel = (RelativeLayout) findViewById(R.id.hidden_panel);
        ImageView filterImage = (ImageView) findViewById(R.id.filterButton);
        filterImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
			            R.anim.bottom_up);

				filterPanel.setAnimation(bottomUp);
				filterPanel.setVisibility(View.VISIBLE);
			}
		});
        
        ImageView applyImage = (ImageView)findViewById(R.id.apply_filter);
        applyImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation bottomDown = AnimationUtils.loadAnimation(getApplicationContext(),
			            R.anim.bottom_down);

				filterPanel.setAnimation(bottomDown);
				filterPanel.setVisibility(View.INVISIBLE);
			}
		});
	}

	/** Register for the updates when Activity is in foreground */
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/** Stop the updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onLocationChanged(Location location) {
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinme))
        .title("Your current Location")
        .position(currentPosition));
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
        locationManager.removeUpdates(this);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		System.out.println("Status Changed");
	}

}
