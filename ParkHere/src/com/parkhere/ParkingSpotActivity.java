package com.parkhere;

import java.util.List;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parkhere.entity.IConsumeParkingLots;
import com.parkhere.entity.ParkingLot;
import com.parkhere.parse.ParseConnector;

public class ParkingSpotActivity extends Fragment implements IConsumeParkingLots{

	private GoogleMap map;
	private View rootView;
	private String objectId;
	private MapFragment fm;
    
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_parkingspot, container, false);
		
		ParseConnector parseConnector = new ParseConnector(getActivity().getApplicationContext());
		parseConnector.getParkingLocationById(this, objectId);
		
		ImageView bookItButton = (ImageView) rootView.findViewById(R.id.bookItButton);
		bookItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
				.remove(fm)
				.replace(R.id.frame_container, new TimerActivity()).commit();
			}
		});
		return rootView;
	}



	@Override
	public void onReceiveParkingLots(List<ParkingLot> parkingLots) {
		ParkingLot parkingLot = parkingLots.get(0);
		fillViewWithParkingLot(parkingLot);
	}


	private void fillViewWithParkingLot(ParkingLot parkingLot) {
		TextView title = (TextView)rootView.findViewById(R.id.title);
		title.setText(parkingLot.getName());
		
		TextView availability = (TextView)rootView.findViewById(R.id.availability);
		availability.setText(parkingLot.getRentTerm());
		
		TextView price = (TextView)rootView.findViewById(R.id.price);
		price.setText(parkingLot.getPricePerDay() +" Dollars/day");
		
		TextView detail = (TextView)rootView.findViewById(R.id.details);
		detail.setText(parkingLot.getDetail());
		
		ImageView picture = (ImageView)rootView.findViewById(R.id.picture);
		byte[] decodedString = Base64.decode(parkingLot.getImageBase64(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
		picture.setImageBitmap(decodedByte);
		
		fm = (MapFragment)  getFragmentManager().findFragmentById(R.id.map_detail);
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
