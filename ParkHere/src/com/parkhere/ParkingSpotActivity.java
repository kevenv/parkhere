package com.parkhere;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parkhere.entity.IConsumeParkingLots;
import com.parkhere.entity.ParkingLot;
import com.parkhere.parse.ParseConnector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingSpotActivity extends FragmentActivity implements IConsumeParkingLots{

	private GoogleMap map;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parkingspot);
		
		String objectId = (String) getIntent().getExtras().get("parkingLot");
		ParseConnector parseConnector = new ParseConnector(getApplicationContext());
		parseConnector.getParkingLocationById(this, objectId);
		
		ImageView bookItButton = (ImageView) findViewById(R.id.bookItButton);
		bookItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onReceiveParkingLots(List<ParkingLot> parkingLots) {
		ParkingLot parkingLot = parkingLots.get(0);
		fillViewWithParkingLot(parkingLot);
	}


	private void fillViewWithParkingLot(ParkingLot parkingLot) {
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(parkingLot.getName());
		
		TextView availability = (TextView)findViewById(R.id.availability);
		availability.setText(parkingLot.getRentTerm());
		
		TextView price = (TextView)findViewById(R.id.price);
		price.setText(parkingLot.getPricePerDay() +" Dollars/day");
		
		TextView detail = (TextView)findViewById(R.id.details);
		detail.setText(parkingLot.getDetail());
		
		ImageView picture = (ImageView)findViewById(R.id.picture);
		byte[] decodedString = Base64.decode(parkingLot.getImageBase64(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
		picture.setImageBitmap(decodedByte);
		
		SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
		map = fm.getMap();
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setZoomGesturesEnabled(false);
		
		LatLng position = new LatLng(parkingLot.getLatitude(),parkingLot.getLongitude());
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                .title(parkingLot.getName())
                .position(position));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
	}

}
