package com.parkhere;

import java.util.List;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.android.gms.internal.bu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parkhere.entity.IConsumeParkingLots;
import com.parkhere.entity.ParkingLot;
import com.parkhere.parse.ParseConnector;

public class DriverActivity extends FragmentActivity implements LocationListener, IConsumeParkingLots{

	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;
	private ParseConnector parseConnector;
	private LatLng currentPosition;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver);
		
		// Get a handle to the Map Fragment
		SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
		map = fm.getMap(); 
        
		map.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				ParkingLot parkingLot = ParkingLot.fromJson(marker.getSnippet());
			    View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);
			    TextView description = (TextView) v.findViewById(R.id.description);
			    description.setText(parkingLot.getName());
			    
			    TextView title = (TextView) v.findViewById(R.id.title);
			    title.setText(parkingLot.getAddress());
			    
			    TextView snippet = (TextView) v.findViewById(R.id.price);
			    snippet.setText("Price/Day: " +parkingLot.getPricePerDay() +"$");

			    return v;
			}
			
			@Override
			public View getInfoContents(Marker arg0) {
				return null;
			}
		});
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent parkingSpotIntent = new Intent(DriverActivity.this, ParkingSpotActivity.class);
				parkingSpotIntent.putExtra("parkingLot", marker.getTitle());
				startActivity(parkingSpotIntent);
			}
		});
		
        //map.setMyLocationEnabled(true);
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, true);
        
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        
        if(lastKnownLocation!=null){
            onLocationChanged(lastKnownLocation);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 1, this);

        LatLng position = new LatLng(45.504092,-73.619416);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
        manageShowPanel();
        manageShowAvailability();
        
        parseConnector = new ParseConnector(getApplicationContext());
        parseConnector.getParkingLocations(this, null);
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
				parseConnector.getParkingLocations(DriverActivity.this, "Weekly");
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
		currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        setCurrentLocationMarker();
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
        locationManager.removeUpdates(this);
	}
	private void setCurrentLocationMarker() {
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinme))
        .title("Your current Location")
        .position(currentPosition));
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
	@Override
	public void onReceiveParkingLots(List<ParkingLot> parkingLots) {
		map.clear();
		setCurrentLocationMarker();
		System.out.println(parkingLots);
		for(ParkingLot parkingLot: parkingLots) {
	        LatLng position = new LatLng(parkingLot.getLatitude(),parkingLot.getLongitude());
	        map.addMarker(new MarkerOptions()
	                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
	                .title(parkingLot.getObjectId())
	                .snippet(parkingLot.toJson())
	                .position(position));
		}
	}

}
